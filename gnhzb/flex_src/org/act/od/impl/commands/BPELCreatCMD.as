package org.act.od.impl.commands
{
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.commands.SequenceCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.business.BpelCreator;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.events.FileNavigatorViewAppEvent;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.BpelEditorModel;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.FileIDManager;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.BPELFileOverWriteWarning;
	
	/**
	 * Create a new BPEL file, bpelEditorModel and bpelEditorpage by the active activeFigureEditorModel
	 * 
	 * @ author Zhaoxq
	 * 
	 */ 
	public class BPELCreatCMD extends SequenceCommand{
		
		private var relBpelFileID :String;
		private var bpelFileName :String;
		private var bpelFilePath :String;
		private var beModel :BpelEditorModel;
		
		/**
		 * Constructor,
		 * Call supper class constructor. 
		 * 
		 */		
		public function BPELCreatCMD(){
			super();
		}
		/**
		 * Execute commande to 
		 *  - create a new <code>BPELfile</code> </br>
		 *  - create a new <code>bpelEditorModel</code> </br>
		 *  - create e new <code>bpelEditorpage</code>  </br>
		 *  by the active <code> activeFigureEditorModel </code>
		 * 
		 * @param event {figureFilePath}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var orDesModelLoc :OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			
			var feNavModel :FigureEditorNavigatorModel = orDesModelLoc.getFigureEditorNavigatorModel();
			
			var activeFEModel :FigureEditorModel = feNavModel.activeFigureEditorModel;
			
			relBpelFileID = activeFEModel.relativeBpelID;
			
			//1. creat bpel file
			if(relBpelFileID == null){
				relBpelFileID = FileIDManager.getAvailabelFileId();
				activeFEModel.relativeBpelID = relBpelFileID;
			}
			activeFEModel.updateCanvasXML();
			
			var figureFilePath :String = event.data.figureFilePath;
			
			bpelFilePath = figureFilePath.substring(0, figureFilePath.length-4) + ".bpel";
			
			bpelFileName = bpelFilePath.substring( bpelFilePath.lastIndexOf("\\")+1,  bpelFilePath.length);
			
			new FileNavigatorViewAppEvent(FileNavigatorViewAppEvent.NEW_BPEL_FILE,
				{fileID :relBpelFileID, filePath :bpelFilePath, fileName :bpelFileName}).dispatch();
			
			
			//2. creat the bpelEditorModel, if already exist, return the old one
			
			beModel = feNavModel.addBpelEditorModel(relBpelFileID, activeFEModel.fileID);
			
			//3. active the bpelEditorpage
			if(beModel.bpelText == null){
				beModel.updateBpelTextByActiveFEModel();
				new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_BPELEDITOR_PAGE,
					{fileID :relBpelFileID, filePath:bpelFilePath, fileName:bpelFileName, bpelEditorModel:beModel }
				).dispatch();
			}
			else{
				var currentBPELText :String = beModel.bpelText;
				var newBPELText :String;
				var bpelCreator :BpelCreator = new BpelCreator();
				
				newBPELText = bpelCreator.outBpelStirng(ProcessFigure(orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel.rootFigure) );
				if(currentBPELText.localeCompare(newBPELText) != 0){
					var isOverWrite :BPELFileOverWriteWarning = BPELFileOverWriteWarning(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), BPELFileOverWriteWarning,true));
					isOverWrite.setText(bpelFileName);
					PopUpManager.centerPopUp(isOverWrite);
					isOverWrite.addEventListener(CloseEvent.CLOSE, onBpelHandelResult);
					new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_BPELEDITOR_PAGE,
						{fileID :relBpelFileID, filePath:bpelFilePath, fileName:bpelFileName, bpelEditorModel:beModel }
					).dispatch();
				}
			}
		}
		private function onBpelHandelResult(event :CloseEvent) :void{
			beModel.updateBpelTextByActiveFEModel();
			beModel.bpelIsChange = true;
			new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_BPELEDITOR_PAGE,
				{fileID :relBpelFileID, filePath:bpelFilePath, fileName:bpelFileName, bpelEditorModel:beModel }
			).dispatch();
		}
	}
}