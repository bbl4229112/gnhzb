package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.SequenceCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.model.BpelEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * Open the selected BPEL file.
	 * 
	 * @ author Zhaoxq
	 * 
	 */ 
	public class BPELFileOpenCMD extends SequenceCommand{
		
		private var _fileID :String;
		private var _filePath :String;
		private var _fileName :String;
		private var _relativeFigureFileID :String;
		private var beModel :BpelEditorModel;
		
		/**
		 * Constructor,
		 * Call super class constructor. 
		 * 
		 */
		public function BPELFileOpenCMD(){
			super();
		}
		
		
		/**
		 * Execute commande to open the selected BPEL file.
		 * @param event {fileID, filePath, fileName, relativeFigureFileID}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			this._fileID = event.data.fileID;
			this._filePath = event.data.filePath;
			this._fileName = event.data.fileName;
			this._relativeFigureFileID = event.data.relativeFigureFileID;
			//1. creat the bpelEditorModel, if already exist, return the old one
			var feNavModel :FigureEditorNavigatorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			beModel = feNavModel.addBpelEditorModel(
				event.data.fileID, event.data.relativeFigureFileID);
			
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile(event.data.filePath);
			remote.addEventListener(ResultEvent.RESULT, openFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
			
		}
		private function openFileResult(event :ResultEvent):void{
			if(beModel.bpelText == null)
				beModel.bpelText = beModel.colorBpelText(XML(event.result.valueOf()).toString());
			else
				beModel.bpelText = beModel.colorBpelText(XML(beModel.bpelContent).toString());
			//2. active the bpelEditorpage
			this.nextEvent = new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_BPELEDITOR_PAGE,
				{ fileID :this._fileID, filePath :this._filePath, 
					fileName :this._fileName, bpelEditorModel:beModel }
			);
			this.executeNextCommand();
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		
	}
}