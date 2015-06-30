package org.act.od.impl.view.bpmn
{
	
	import flash.events.MouseEvent;
	
	import mx.containers.Accordion;
	import mx.containers.Box;
	import mx.containers.VBox;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.LinkButton;
	import mx.events.ListEvent;
	
	import org.act.od.impl.events.ToolPanelAppEvent;
	import org.act.od.impl.figure.bpmn.BpmnFigureFactory;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.other.Localizator;
	import org.act.od.impl.state.SelectionState;
	
	
	/**
	 * Bpmn Tool Panel
	 * 
	 * @author Quyue
	 * 
	 */
	public class BpmnToolPanel extends Box{
		
		
		//view initilization
		/**
		 * The source for "palette" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/palette.gif")]
		public var palette :Class;
		
		/**
		 * The source for "folder" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/accordion/folder.gif")]
		public var folder :Class;
		
		private var accordion :Accordion = new Accordion();
		
		private var basicBox :VBox;
		private var basicToolTile :BpmnToolTile = new BpmnToolTile();
		private var basicXMLList :XMLList = new XMLList();
		
		private var startBox :VBox;
		private var startToolTile :BpmnToolTile = new BpmnToolTile(BpmnToolTile.START_EVENT);
		private var startEventXMLList :XMLList = new XMLList();
		
		private var endBox :VBox;
		private var endToolTile :BpmnToolTile = new BpmnToolTile(BpmnToolTile.END_EVENT);
		private var endEventXMLList :XMLList = new XMLList();
		
		private var intermediateBox :VBox;
		private var intermediateToolTile :BpmnToolTile = new BpmnToolTile(BpmnToolTile.INTERMEDIATE_EVENT);
		private var intermediateEventXMLList :XMLList = new XMLList();
		
		private var localizator :Localizator;
		
		private static var tools :XML =
			<tools>
				<basic name="" id="Activity"/>
				<basic name="" id="Pool"/>
				<basic name="" id="messageStart"/>
				<basic name="" id="messageIntermediate"/>
				<basic name="" id="noneEnd"/>
				<start name="" id="messageStart"/>
				<end name="" id="noneEnd"/>
				<intermediate name="" id="messageIntermediate"/>
			</tools>;
		
		private var isSelected:Boolean=false;
		
		public function BpmnToolPanel(){
			super();
			this.percentHeight = 100;
			this.percentWidth = 100;
			this.localizator = Localizator.getInstance(null,Localizator.BPMN_RESOURCE_BUNDLE);
			this.label = localizator.getText('toolpanel.label');
			this.icon = palette;
			
			initXMLList();
			
			this.initAccordion();
			
			this.addChild(accordion);
		}
		
		private function initAccordion() :void{
			
			accordion.percentHeight = 100;
			accordion.percentWidth = 100;
			accordion.buttonMode = true;
			
			initBasicBox();
			
			//initStartBox();
			//initEndBox();
			//initIntermediateBox();
			
			accordion.addChild(basicBox);
			//			accordion.addChild(startBox);
			//			accordion.addChild(endBox);
			//			accordion.addChild(intermediateBox);
			
			this.setHeader();
		}
		
		private function initXMLList() :void{
			
			for each(var item : XML in tools.descendants()) {
				var labelText : String = null;
				if(item.@id == "Start") {
					labelText = localizator.getText('toolpanel.basic.start');
					item.@name = labelText;
				}
				else if(item.@id == "End") {
					labelText = localizator.getText('toolpanel.basic.end');
					item.@name = labelText;
				}
				else if(item.@id == "Activity") {
					labelText = localizator.getText('toolpanel.basic.activity');
					item.@name = labelText;
				}
				else if(item.@id == "Pool") {
					labelText = localizator.getText('toolpanel.basic.pool');
					item.@name = labelText;
				}
				else if(item.@id == "messageStart") {
					labelText = localizator.getText('toolpanel.start.message');
					item.@name = labelText;
				}
				else if(item.@id == "noneEnd") {
					labelText = localizator.getText('toolpanel.end.none');
					item.@name = labelText;
				}
				else if(item.@id == "messageIntermediate") {
					labelText = localizator.getText('toolpanel.intermediate.message');
					item.@name = labelText;
				}
			}
			
			this.basicXMLList = tools.descendants("basic");
			this.startEventXMLList = tools.descendants("start");
			this.endEventXMLList = tools.descendants("end");
			this.intermediateEventXMLList = tools.descendants("intermediate");
			
		}
		
		private function initBasicBox() :void{
			basicBox = new VBox();
			basicBox.label = localizator.getText('toolpanel.basic');
			basicBox.percentHeight = 100;
			basicBox.percentWidth = 100;
			basicToolTile.setXMLData(basicXMLList);
			basicBox.addChild(basicToolTile);
			basicToolTile.initArray();
			
			basicToolTile.addEventListener(ListEvent.ITEM_CLICK, itemClickHandle);
			basicBox.addEventListener(MouseEvent.CLICK, basicboxClickHandle);
		}
		
		/**
		 * Init Start Event Box.
		 */
		private function initStartBox() :void{
			
			startBox = new VBox();
			startBox.label = localizator.getText('toolpanel.start');
			startBox.percentHeight = 100;
			startBox.percentWidth = 100;
			startToolTile.setXMLData(startEventXMLList);
			startBox.addChild(startToolTile);
			startToolTile.initArray();
			
			startToolTile.addEventListener(ListEvent.ITEM_CLICK, itemClickHandle);
			startBox.addEventListener(MouseEvent.CLICK, businessboxClickHandle);
		}
		
		/**
		 * Init End Event Box.
		 */
		private function initEndBox() :void{
			
			endBox = new VBox();
			endBox.label = localizator.getText('toolpanel.end');
			endBox.percentHeight = 100;
			endBox.percentWidth = 100;
			endToolTile.setXMLData(endEventXMLList);
			endBox.addChild(endToolTile);
			endToolTile.initArray();
			
			endToolTile.addEventListener(ListEvent.ITEM_CLICK, itemClickHandle);
			endBox.addEventListener(MouseEvent.CLICK, businessboxClickHandle);
		}
		
		/**
		 * Init Intermediate Event Box.
		 */
		private function initIntermediateBox() :void{
			
			intermediateBox = new VBox();
			intermediateBox.label = localizator.getText('toolpanel.intermediate');
			intermediateBox.percentHeight = 100;
			intermediateBox.percentWidth = 100;
			intermediateToolTile.setXMLData(intermediateEventXMLList);
			intermediateBox.addChild(intermediateToolTile);
			intermediateToolTile.initArray();
			
			intermediateToolTile.addEventListener(ListEvent.ITEM_CLICK, itemClickHandle);
			intermediateBox.addEventListener(MouseEvent.CLICK, businessboxClickHandle);
		}
		
		private function setHeader() :void{
			
			var idx :uint;
			var len :uint = accordion.numChildren;
			var btn:Button;
			for (idx=0; idx<len; idx++) {
				btn = accordion.getHeaderAt(idx);
				btn.useHandCursor = true;
				btn.buttonMode = true;
				btn.setStyle("icon", folder);
			}
			
		}
		
		private function itemClickHandle(event : ListEvent) :void {
			isSelected=true;
			
			var selectedFigureName : String = (event.itemRenderer.data as LinkButton).id;
			var selectedFigId : int = BpmnFigureFactory.name2id(selectedFigureName);
			
			new ToolPanelAppEvent( ToolPanelAppEvent.SELECT_FIGURE,
				{selectedFigureId :selectedFigId} ).dispatch();
			
		}
		
		private function basicboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		
		
		private function businessboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
			
		}
		/**
		 * Reset the toolPanel.
		 */
		public function resetAllSelections():void{
			this.basicToolTile.selectedIndex=-1;
			this.startToolTile.selectedIndex=-1;
			this.endToolTile.selectedIndex=-1;
			this.intermediateToolTile.selectedIndex=-1;
			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
		}
		
		
		
	}
}