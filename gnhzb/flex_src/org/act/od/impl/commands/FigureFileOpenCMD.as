package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.SequenceCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.UserNavigatorView;
	import org.act.od.impl.viewhelper.FileNavigatorVH;
	import org.act.od.impl.viewhelper.UserNavigatorViewVH;
	
	/**
	 * To open a figureFile
	 * 
	 * @author Zhaoxq
	 *
	 */
	public class FigureFileOpenCMD extends SequenceCommand{
		
		public var _fileID :String;
		public var _filePath :String;
		private var _fileName :String;
		private var _fileCategory :String;
		private var _figureEditorModel :FigureEditorModel;
		private var newUser:XML;
		private var username:String;
		private var bindEnable:Boolean;
		
		public function FigureFileOpenCMD(){
			super();
		}
		
		/**
		 * @param event {fileID, relativeBpelID, filePath, fileName}
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			//1. creat the figureEditorModel, if already exist, return the old one
			var feNavModel :FigureEditorNavigatorModel =
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			this._fileID = event.data.fileID;
			this._filePath = event.data.filePath;
			this._fileName = event.data.fileName;
			this._figureEditorModel = feNavModel.addFigureEditorModel(this._fileID, event.data.relativeBpelID);
			//			trace("");
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile(event.data.filePath);
			remote.addEventListener(ResultEvent.RESULT, openFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
			//			added by megnsong 2010-7-5 21:56:18
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_TotalArray.push(new Array());
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_TotalArray.push(new Array());
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().IDnum++;
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID = this._fileID;
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name = this._fileName;	
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path = this._filePath;		
			username = OrDesignerModelLocator.getInstance().getOrchestraDesigner().userName;
			
			this.check();
			this.dataGridBinding();
			
		}
		private function openFileResult(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var xml:XML=XML(str);
			
			//qu
			this._fileCategory=xml.@category;
			
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = xml;		
			
			if(this._figureEditorModel.canvasXML == null){
//				this._figureEditorModel.rootFigure.readInformationToFigure(xml); //不需要了，20120906。
				
				//add by lu 2009-09-24
				this._figureEditorModel.idManager.setAvailabelId(int(xml.@maxId));
				
				this._figureEditorModel.updateCanvasXML();
			}
			
			//2. active the figureEditorpage
			this.nextEvent = new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
				{ fileID:this._fileID, filePath:this._filePath,
					fileName:this._fileName, fileCategory:this._fileCategory ,  figureEditorModel:this._figureEditorModel }
			);
			
			this.executeNextCommand();
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		
		/**
		 *  Read the userdatagrid first,then add the add the new user to the userdatagrid,
		 *  at the end, put the xmllist to the provider and updata the userdatagrid 
		 */
		private function dataGridBinding():void{
			
			this.bindEnable = true
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
			if(bindEnable==true)
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
		
		private function check():void{
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult_checkEnable);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		
		private function openFileResult_checkEnable(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userListXml:XML=XML(str);
			
			for(var i:int = 0; userListXml.user[i]!=null;i++){
				if(String(userListXml.user[i].name)==String(username))
					bindEnable = false;
			}
		}
		
		
	}
}