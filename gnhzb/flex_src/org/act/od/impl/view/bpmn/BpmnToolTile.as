package org.act.od.impl.view.bpmn
{
	import mx.controls.LinkButton;
	import mx.controls.TileList;
	import mx.core.ClassFactory;
	
	/**
	 * Provide the title for the toolPanel.
	 * 
	 * @author Quyue
	 * 
	 */
	public class BpmnToolTile extends TileList
	{
		public static const START_EVENT:String = "START_EVENT";
		public static const END_EVENT:String = "END_EVENT";
		public static const INTERMEDIATE_EVENT:String = "INTERMEDIATE_EVENT";
		public static const ALL_EVENT:String = "ALL_EVENT";
		
		private var content :Array = new Array();
		
		private var xmlData :XMLList;
		
		
		public function BpmnToolTile(type:String=null)
		{
			super();
			
			this.percentHeight = 100;
			this.percentWidth = 100;
			if(type == null)
				this.itemRenderer = new ClassFactory(BpmnToolRenderer);
			else if(type == BpmnToolTile.START_EVENT)
				this.itemRenderer = new ClassFactory(BpmnStartEventToolRenderer);
			else if(type == BpmnToolTile.END_EVENT)
				this.itemRenderer = new ClassFactory(BpmnEndEventToolRenderer);
			else if(type == BpmnToolTile.INTERMEDIATE_EVENT)
				this.itemRenderer = new ClassFactory(BpmnIntermediateEventToolRenderer);
			
			
			//			this.direction = "vertical";
			//			this.explicitRowCount = 5;
			//			this.explicitColumnCount = 3;
			//this.rowHeight = 40;
			this.dataProvider = content;
			//this.maxColumns = 3;
			//this.rowHeight=50;
			this.columnWidth = 50;
			
			
			//			this.addEventListener(ListEvent.ITEM_CLICK,itemClickHandle);
		}
		/**
		 * Initialize the array for the buoon in the toolPanel.
		 */
		public function initArray() :void{
			
			var item:XML;
			for each(item in xmlData) {
				var button :LinkButton = new LinkButton();
				button.label = item.attribute("name");
				button.id = item.attribute("id");
				button.buttonMode = false;
				button.percentWidth = 100;
				button.percentHeight = 100;
				//button.height = 30;
				content.push(button);
			}
		}
		
		public function setXMLData(xmlList :XMLList) :void {
			this.xmlData = xmlList;
		}
		
	}
}