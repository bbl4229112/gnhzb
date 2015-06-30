package org.act.od.impl.commands
{
	import flash.net.SharedObject;
	
	import mx.controls.Alert;
	import mx.messaging.Producer;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.viewhelper.BpelEditorVH;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	/**
	 * Save the active file
	 */
	public class FileSaveCMD extends AODCommand
	{
		private var figureEditorNavigatorVH :EditorNavigatorVH;
		private var figureEditorNavigatorModel :FigureEditorNavigatorModel;
		private var producer :Producer = new Producer;
		private var _fileID :String;
		private var _filePath :String;
		private var _fileName :String;
		private var xml:XML;
		public function FileSaveCMD(){
			figureEditorNavigatorVH = ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			figureEditorNavigatorModel = OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
		}
		/**
		 *
		 * @param event {}
		 *
		 */
		override public function execute(event :OrDesignerEvent) :void{
			var activeFeNavChild :EditorNavigatorChild = figureEditorNavigatorVH.getSelectedChildOfNavigator();

			if(activeFeNavChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE){
//				modifier  likexin
//				figureEditorNavigatorModel.activeFigureEditorModel.updateCanvasXML();
				figureEditorNavigatorModel.activeFigureEditorModel.saveCanvasXML();
				
				this.xml = ProcessFigure(figureEditorNavigatorModel.activeFigureEditorModel.rootFigure).outputAllInformation();
				
				//add by lu 2009-09-24
				this.xml.@maxId = figureEditorNavigatorModel.activeFigureEditorModel.idManager.getAvailabelId();


				this._fileID = figureEditorNavigatorModel.activeFigureEditorModel.fileID;
				var figureEditorVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(this._fileID)) as FigureEditorVH;
				this._filePath = figureEditorVH.filePath;
				this._fileName = this._filePath.substring(this._filePath.lastIndexOf("\\", this._filePath.length) + 1, this._filePath.length);
				//wanglei 2011/3/6
				var remote :RemoteObject = new RemoteObject();
				remote.destination = "navigator";
				remote.openFile(this._filePath);
				remote.addEventListener(ResultEvent.RESULT, openFileResult);
				remote.addEventListener(FaultEvent.FAULT, fault);
			
			}
			else if(activeFeNavChild.type == EditorNavigatorChild.BPEL_EDITOR_TYPE){

				figureEditorNavigatorModel.activeBpelEditorModel.bpelIsChange = false;

				this._fileID = figureEditorNavigatorModel.activeBpelEditorModel.fileID;
				var bpelEditorVH :BpelEditorVH = ViewLocator.getInstance().getViewHelper(BpelEditorVH.getViewHelperKey(this._fileID)) as BpelEditorVH;

				figureEditorNavigatorModel.activeBpelEditorModel.updateBpelContent(bpelEditorVH.getBpelContent());

				this._filePath = bpelEditorVH.filePath;

				var remoteBPEL :RemoteObject = new RemoteObject();
				remoteBPEL.destination = "navigator";
				remoteBPEL.saveFile(this._filePath, figureEditorNavigatorModel.activeBpelEditorModel.getcurrentbpelText());
				remoteBPEL.addEventListener(ResultEvent.RESULT, saveFileResult);
				remoteBPEL.addEventListener(FaultEvent.FAULT, fault);
			}
			this.saveRecoveryState();
		}
		
//		added by egeg 2010-6-11 14:01:33
//		save for recovery
		private function saveRecoveryState():void{
			var lso:SharedObject = SharedObject.getLocal("recovery_state");
			lso.data.recovery_state = 1;
		}
		
		private function saveFileResult(event :ResultEvent):void{
//			producer.destination = "cooperation";
//			var message :AsyncMessage = new AsyncMessage();
//			message.body.fileID = this._fileID;
//			message.body.filePath = this._filePath;
//			message.body.fileName = this._fileName;
//			producer.send(message);
		}
		private function fault(event :FaultEvent):void{
        	Alert.show("Remote invoke error: "+event.message);
        }
        //wanglei 2011/3/6
        private function openFileResult(event :ResultEvent):void{
        	var str:String=event.result.valueOf();
			var oldxml:XML=XML(str);
			
			this.xml.@category=oldxml.@category
			trace(this.xml.@category);
		
        	var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";	
        	remote.saveFile(this._filePath, this.xml.toString());
			remote.addEventListener(ResultEvent.RESULT, saveFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
        }
	}
}