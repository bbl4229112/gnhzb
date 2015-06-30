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
	 * @ author Likexin
	 * 
	 */
	public class AttributeChangeCMD extends AODCommand{
		
		
		public function AttributeChangeCMD(){
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
				Alert.show("newValue or rowIndex null in AttributeChangeCMD");
			}
			
			var obj:Object = model.attributes.getItemAt(rowIndex,0);
			
			obj.value = newValue;
			
			//update model
			model.updateAttribute();
			
			//update figure name in canvas
			if(model.editedFigure != null ) {
//				model.editedFigure.figureName = newValue;   //第三个属性（rowIndex为2）值变后，figureName不再跟着变。
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