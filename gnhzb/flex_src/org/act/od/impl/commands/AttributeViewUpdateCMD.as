package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import mx.controls.Alert;
	
	/**
	 * Initialize the AttributeView by a selected figure
	 * @ author Likexin
	 */ 
	public class AttributeViewUpdateCMD extends AODCommand{
				
		public function AttributeViewUpdateCMD(){
			super();
		}		
		/** 
		 * @param event {selectedFigure}
		 */
		override public function execute(event :OrDesignerEvent) :void{			
			var orDesModelLoc :OrDesignerModelLocator = OrDesignerModelLocator.getInstance();			
			var selectFig :AbstractFigure = event.data.selectedFigure as AbstractFigure;
			
			if(selectFig != null) {
//				orDesModelLoc.getOrchestraDesigner().getfigureAttributeNavigator().showFigureAttribute();
				orDesModelLoc.getAttributeViewModel().editedFigure = selectFig;
				orDesModelLoc.getAttributeViewModel().attributes = selectFig.getAttributeArray();
				orDesModelLoc.getOrchestraDesigner().getfigureAttributeNavigator().
					figureAttributeBox.label = "“" + selectFig.figureName + "”节点属性";
			}else {
				orDesModelLoc.getAttributeViewModel().editedFigure = null;
				orDesModelLoc.getAttributeViewModel().updateAttribute();
			
			}						
		}		
	}
}