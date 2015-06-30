package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.framework.control.OrDesignerEvent;
	
	public class BestPracticeUpdateCMD extends AODCommand
	{
		var orDesModelLoc:OrDesignerModelLocator;
		
		public function BestPracticeUpdateCMD()
		{
			super();
		}
		
		override public function execute(event:OrDesignerEvent):void
		{
			orDesModelLoc = OrDesignerModelLocator.getInstance();	
			var selectFig:AbstractFigure = event.data.selectedFigure as AbstractFigure;
			
			if(selectFig != null) {
//				orDesModelLoc.getOrchestraDesigner().getfigureAttributeNavigator().showBestPractice();
				orDesModelLoc.getKnowledgeViewModel().editedFigure = selectFig;
			} else {
				//OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().hideBestPractice();
			}
		}
	}
}