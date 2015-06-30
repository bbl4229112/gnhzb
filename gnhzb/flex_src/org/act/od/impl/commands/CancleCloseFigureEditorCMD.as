package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FigureEditor;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.viewhelper.FileNavigatorVH;
	
	/**
	 * Close the FigureEditor page and then cancel it.
	 * 
	 * @ author Zhaoxq
	 * 
	 */ 
	public class CancleCloseFigureEditorCMD extends AODCommand
	{
		private var newUser:XML;
		private var username:String;
		public function CancleCloseFigureEditorCMD()
		{
			super();
		}
		
		/**
		 * 
		 * @param event {closedFigureEditor}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			var closedFigureEditor : FigureEditor = event.data.closedFigureEditor as FigureEditor;
			
			if(closedFigureEditor == null)
				Alert.show("closedFigureEditor null in CancleCloseFigureEditorCMD");
			else {
				var closedFEModel : FigureEditorModel = closedFigureEditor.figureEditorModel;
				
				//readd cancled-closing figure editor
				var viewLoc : ViewLocator =  ViewLocator.getInstance();
				var feNavModel :FigureEditorNavigatorModel = 
					OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				var feNavVH : EditorNavigatorVH = 
					ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
				feNavVH.createNewFigureEditor(closedFEModel, closedFigureEditor.filePath, closedFigureEditor.label,  closedFigureEditor.fileCategory);
				var feVH : FigureEditorVH = viewLoc.getViewHelper(FigureEditorVH.getViewHelperKey(closedFEModel.fileID)) as FigureEditorVH;
				
				username = OrDesignerModelLocator.getInstance().getOrchestraDesigner().userName;
				this.dataGridBinding();
				//reset current state and active figure editor model
				feNavModel.activeFigureEditorModel = closedFigureEditor.figureEditorModel;
				
				feNavVH.SwithToGivenFileID(closedFEModel.fileID);
			}
		}
		
		/**
		 *  Read the userdatagrid first,then add the add the new user to the userdatagrid,
		 *  at the end, put the xmllist to the provider and updata the userdatagrid 
		 */
		private function dataGridBinding():void{
			
			newUser = new XML(<user />);
			newUser.name =username ;
			newUser.token = " ";
			
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult_dataGrid);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
		}
		
		private function openFileResult_dataGrid(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userListXml:XML=XML(str);
			
			var FNVH:FileNavigatorVH = ViewLocator.getInstance().getViewHelper(FileNavigatorVH.VH_KEY) as FileNavigatorVH
			userListXml.appendChild(newUser);
			
			//			save the userdata to the userlist by remoting
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.saveFile("../database/userlist/projectuserlist.xml", userListXml.toString());
			remote.addEventListener(ResultEvent.RESULT, saveFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
			
			FNVH.fileNavigator.projectuserNavigatorView.dataProvider = userListXml.children();
			
			//			for update userListDataGrid
			var message:AsyncMessage = new AsyncMessage();   
			message.body.content = userListXml;
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer_projectuserlist.send(message);
		}		
		
		private function saveFileResult(event :ResultEvent):void{
			//			Alert.show("Update userDatagrid success!!");
		}
		
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}