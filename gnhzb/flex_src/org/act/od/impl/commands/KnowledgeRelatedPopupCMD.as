package org.act.od.impl.commands
{
	import flash.display.DisplayObject;
	
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.KnowledgeRelatedPopup;
	import mx.controls.Alert;
	
	public class KnowledgeRelatedPopupCMD extends AODCommand
	{
		private var orDesModelLoc:OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		
		public function KnowledgeRelatedPopupCMD()
		{
			super();
		}
		
		override public function execute(event:OrDesignerEvent):void
		{
			orDesModelLoc = OrDesignerModelLocator.getInstance();	
			var selectFig:AbstractFigure = event.data.selectedFigure as AbstractFigure;	
//			Alert.show("here 1");
			var pop:KnowledgeRelatedPopup;
			if(selectFig != null) {
				pop = new KnowledgeRelatedPopup();
				orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel.figureCanvas.addChild(pop);
				Alert.show("here 2");
//				orDesModelLoc.figureEditorNavigatorModel.activeFigureEditorModel.figureCanvas.addChild(pop);
//				pop = PopUpManager.createPopUp(this, KnowledgeRelatedPopup, false) as KnowledgeRelatedPopup;
//				PopUpManager.addPopUp(pop, this, false);
//				PopUpManager.centerPopUp(pop);
			}
		}
	}
}