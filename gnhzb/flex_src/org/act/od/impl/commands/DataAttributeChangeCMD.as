package org.act.od.impl.commands
{
	import mx.controls.Alert;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.GraphicalFigure;
	import org.act.od.impl.model.AttributeViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * Change the value of the AttributeView
	 * Change the value of a certain row
	 * 
	 * @author Mengsong
	 * 
	 */
	public class DataAttributeChangeCMD extends AODCommand{
		
		
		public function DataAttributeChangeCMD(){
			super();
		}
		
		/**
		 *
		 * @param event {newValue, rowIndex }
		 *
		 */
		override public function execute(event : OrDesignerEvent) :void{
			
			var orDesModelLoc:OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			
			var model:AttributeViewModel = orDesModelLoc.getAttributeViewModel();
			
			var newValue : String = event.data.newValue;
			
			var rowIndex : int = new int(event.data.rowIndex);
			
			if(newValue == null  ||  rowIndex == -1) {
				Alert.show("newValue or rowIndex null in Data AttributeChangeCMD");
			}
			
			var obj:Object = model.dataAttributes.getItemAt(rowIndex,0);
			
			obj.value = newValue;
			
			//update model
			model.updateAttribute();
			
			//update figure name in canvas
			if(model.editedFigure != null && rowIndex == 2) {
				model.editedFigure.figureName = newValue;
				model.editedFigure.updateDraw();
				if(model.editedFigure is GraphicalFigure){
					GraphicalFigure(model.editedFigure).setFigureSizeAccordingTolblNodeName();
				}
				model.editedFigure.updateDraw();
				new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
					{canvas :null}).dispatch();
			}
		}
	}
}