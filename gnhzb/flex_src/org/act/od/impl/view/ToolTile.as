package org.act.od.impl.view
{
	import mx.containers.HBox;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.LinkButton;
	import mx.controls.TileList;
	import mx.core.ClassFactory;
	
	import org.act.od.impl.other.Resource;
	import org.act.od.impl.view.bpmn.BpmnToolRenderer;
	import org.act.od.impl.figure.FigureFactory;
	
	/**
	 * Provide the title for the toolPanel.
	 * 
	 * @author Quyue
	 * 
	 */
	public class ToolTile extends TileList
	{
		
		private var content :Array = new Array();
		
		private var xmlData :XMLList;
		
		
		public function ToolTile()
		{
			super();
			
			this.percentHeight = 100;
			this.percentWidth = 100;
			
			this.itemRenderer = new ClassFactory(ToolRenderer);
			this.direction = "vertical";
			this.explicitRowCount = 5;
			this.explicitColumnCount = 1;
			this.rowHeight = 25;
			this.dataProvider = content;
			
			//			this.addEventListener(ListEvent.ITEM_CLICK,itemClickHandle);
		}
		/**
		 * Initialize the array for the buoon in the toolPanel.
		 */
		public function initArray() :void{
			
			var item:XML;
			for each(item in xmlData) {
				var hbox:HBox = new HBox();
				var button :LinkButton = new LinkButton();
				button.label = item.attribute("name");
				button.buttonMode = false;
				button.percentWidth = 100;
				button.height = 16;
				button.setStyle("icon",FigureFactory.start);
//				hbox.addChild(button);
//				button.visible = true;
//				button.percentWidth = 80;
				content.push(button);
//				content.push(hbox);
			}
		}
		
		public function setXMLData(xmlList :XMLList) :void {
			this.xmlData = xmlList;
		}
		
	}
}