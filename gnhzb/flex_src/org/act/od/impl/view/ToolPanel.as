package org.act.od.impl.view{
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.containers.Accordion;
	import mx.containers.Box;
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.containers.Panel;
	import mx.containers.VBox;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.LinkButton;
	import mx.core.ClassFactory;
	import mx.events.ListEvent;
	import mx.managers.CursorManager;
	
	import org.act.od.impl.events.ToolPanelAppEvent;
	import org.act.od.impl.figure.custom.StartFigure;
	import org.act.od.impl.figure.FigureFactory;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.other.Localizator;
	import org.act.od.impl.state.SelectionState;
	
	
	/**
	 * Tool Panel
	 * 
	 * @author Quyue
	 * 
	 */
	public class ToolPanel extends Box{
		
		
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
		
		/**
		 * The source for "forbidden" image data binding.
		 */		
		[Bindable]
		[Embed(source="/../assets/icon/canvas/forbidden.png")]
		private var forbiddenicon:Class;
		private var forbiddeniconid:int=new int(0);
		
		private var accordion :Accordion = new Accordion();
		
		
		//qu
		private var dataModelBox :Canvas;
		
		private var dataModelToolTile :ToolTile = new ToolTile();
		
		private var UMLBox :VBox;
		
		private var UMLToolTile :ToolTile = new ToolTile();
		
		private var workBox :VBox;
		
		private var workToolTile :ToolTile = new ToolTile();
		
		private var officeOrgnizationBox :VBox;
		
		private var officeOrgnizationToolTile :ToolTile = new ToolTile();
		
		private var networkBox :VBox;
		
		private var networkToolTile :ToolTile = new ToolTile();
		
		private var programStructureBox :VBox;
		
		private var programStructureToolTile :ToolTile = new ToolTile();
		
		private var basicBox :VBox;
		
		private var basicToolTile :ToolTile = new ToolTile();
		
		private var businessBox :VBox;
		
		private var businessToolTile :ToolTile = new ToolTile();
		
		private var bpelBox :VBox;
		
		private var bpelToolTile :ToolTile = new ToolTile();
		//qu
		
		private var dataModelXMLList :XMLList = new XMLList();
		
		private var UMLXMLList :XMLList = new XMLList();
		
		private var workXMLList :XMLList = new XMLList();
		
		private var officeOrgnizationXMLList :XMLList = new XMLList();
		
		private var networkXMLList :XMLList = new XMLList();
		
		private var programStructureXMLList :XMLList = new XMLList();
		
		private var basicXMLList :XMLList = new XMLList();
		
		private var businessXMLList :XMLList = new XMLList();
		
		private var bpelXMLList :XMLList = new XMLList();
		
		private var localizator :Localizator;
		
		private static var tools :XML =
			<tools>
				<basic name="" id="0"/>
				<basic name="" id="1"/>
				<basic name="" id="2"/>
				<basic name="" id="3"/>
				<basic name="" id="4"/>
				<basic name="" id="5"/>
				<business name="" id="6"/>
				<business name="" id="7"/>
				<business name="" id="8"/>
				<business name="" id="9"/>
				<business name="" id="10"/>
				<bpel name="" id="11"/>
				<bpel name="" id="12"/>
				<bpel name="" id="13"/>
				<bpel name="" id="14"/>
				<bpel name="" id="15"/>
					
				<dataModel name="" id="59"/>
              	<dataModel name="" id="16"/>
				<dataModel name="" id="20"/>
				<dataModel name="" id="61"/>
				<dataModel name="" id="62"/>
				<dataModel name="" id="63"/>
				<dataModel name="" id="60"/>
//              	<UML name="" id="20"/>
				<UML name="" id="21"/>
				<UML name="" id="22"/>
				<UML name="" id="23"/>
				<UML name="" id="24"/>
				<UML name="" id="25"/>
			
				<work name="" id="26"/>
				<work name="" id="27"/>
				<work name="" id="28"/>
				<work name="" id="29"/>
				<work name="" id="30"/>
				<work name="" id="31"/>
				<work name="" id="32"/>
				<officeOrgnization name="" id="33"/>
				<officeOrgnization name="" id="34"/>
				<officeOrgnization name="" id="35"/>
				<officeOrgnization name="" id="36"/>
				<officeOrgnization name="" id="37"/>
				<officeOrgnization name="" id="38"/>
				<officeOrgnization name="" id="39"/>
				<network name="" id="40"/>
				<network name="" id="41"/>
				<network name="" id="42"/>
				<network name="" id="43"/>
				<network name="" id="44"/>
				<network name="" id="45"/>
				<network name="" id="46"/>
				<network name="" id="47"/>
				<network name="" id="48"/>
				<network name="" id="49"/>
				<network name="" id="50"/>
				<network name="" id="51"/>
				<network name="" id="52"/>
				<programStructure name="" id="53"/>
				<programStructure name="" id="54"/>
				<programStructure name="" id="55"/>
				<programStructure name="" id="56"/>
				<programStructure name="" id="57"/>
				<programStructure name="" id="58"/>


			</tools>;	  
		
		private var isSelected:Boolean=false;
		
		public function ToolPanel(fileCategory:String){
			super();
			this.percentHeight = 100;
			this.percentWidth = 100;
			this.localizator = Localizator.getInstance();
//			if(fileCategory=="BasicData")
//			{
				this.label = localizator.getText('toolpanel.basicdata');
//			}
//			else if(fileCategory=="Business")
//			{
//				this.label = localizator.getText('toolpanel.business');
//			}
//			else if(fileCategory=="Computer")
//			{
//				this.label = localizator.getText('toolpanel.computer');
//			}
//			else if(fileCategory=="BPEL")
//			{
//				this.label = localizator.getText('toolpanel.label');
//			}
			this.icon = palette;
			initXMLList();
			this.initAccordion(fileCategory);
			
			this.addChild(accordion);
		}
		
		private function initAccordion(fileCategory:String) :void{
			
			accordion.percentHeight = 100;
			accordion.percentWidth = 100;
			accordion.buttonMode = true;
			//qu
//			if(fileCategory=="BasicData")
//			{
				initDataModelBox();
//				initUMLBox();
				accordion.addChild(dataModelBox);
//				accordion.addChild(UMLBox);
//			}
//			else if(fileCategory=="Business")
//			{
//				initWorkBox();
//				initOfficeOrgnizationBox();
//				accordion.addChild(workBox);
//				accordion.addChild(officeOrgnizationBox);
//			}
//			else if(fileCategory=="Computer")
//			{
//				initNetworkBox();
//				initProgramStructureBox();
//				accordion.addChild(networkBox);
//				accordion.addChild(programStructureBox);
//			}
//			else if(fileCategory=="BPEL")
//			{
//				initBasicBox();
//				initBusinessBox();
//				initBpelBox();
//				accordion.addChild(basicBox);
//				accordion.addChild(businessBox);
//				accordion.addChild(bpelBox);
//			}		
			this.setHeader();

		}
		private function initXMLList() :void{
			
			for each(var item : XML in tools.descendants()) {
				var labelText : String = null;
				if(item.@id == "0") {
					labelText = localizator.getText('toolpanel.basic.start');
					item.@name = labelText;
				}
				else if(item.@id == "1") {
					labelText = localizator.getText('toolpanel.basic.end');
					item.@name = labelText;
				}
				else if(item.@id == "2") {
					labelText = localizator.getText('toolpanel.basic.activity');
					item.@name = labelText;
				}
				else if(item.@id == "3") {
					labelText = localizator.getText('toolpanel.basic.switch');
					item.@name = labelText;
				}
				else if(item.@id == "4") {
					labelText = localizator.getText('toolpanel.basic.link');
					item.@name = labelText;
				}
				else if(item.@id == "5") {
					labelText = localizator.getText('toolpanel.basic.subprocess');
					item.@name = labelText;
				}
				else if(item.@id == "6") {
					labelText = localizator.getText('toolpanel.business.email');
					item.@name = labelText;
				}
				else if(item.@id == "7") {
					labelText = localizator.getText('toolpanel.business.fax');
					item.@name = labelText;
				}
				else if(item.@id == "8") {
					labelText = localizator.getText('toolpanel.business.pager');
					item.@name = labelText;
				}
				else if(item.@id == "9") {
					labelText = localizator.getText('toolpanel.business.sms');
					item.@name = labelText;
				}
				else if(item.@id == "10") {
					labelText = localizator.getText('toolpanel.business.voice');
					item.@name = labelText;
				}
				else if(item.@id == "11") {
					labelText = localizator.getText('toolpanel.bpel.invoke');
					item.@name = labelText;
				}
				else if(item.@id == "12") {
					labelText = localizator.getText('toolpanel.bpel.receive');
					item.@name = labelText;
				}
				else if(item.@id == "13") {
					labelText = localizator.getText('toolpanel.bpel.reply');
					item.@name = labelText;
				}
				else if(item.@id == "14") {
					labelText = localizator.getText('toolpanel.bpel.wait');
					item.@name = labelText;
				}
				else if(item.@id == "15") {
					labelText = localizator.getText('toolpanel.bpel.assign');
					item.@name = labelText;
				}
				//qu
				else if(item.@id == "16") {    
					labelText = localizator.getText('toolpanel.datamodel.businessnode');
					item.@name = labelText;
				}
				else if(item.@id == "17") {    
					labelText = localizator.getText('toolpanel.datamodel.interface');
					item.@name = labelText;
				}
				else if(item.@id == "18") {    
					labelText = localizator.getText('toolpanel.datamodel.datastore');
					item.@name = labelText;
				}
				else if(item.@id == "19") {    
					labelText = localizator.getText('toolpanel.datamodel.dataprocess');
					item.@name = labelText;
				}
				else if(item.@id == "20") {    
					labelText = localizator.getText('toolpanel.datamodel.newdata');
					item.@name = labelText;
				}
//				else if(item.@id == "20") {    
//					labelText = localizator.getText('toolpanel.UML.package');
//					item.@name = labelText;
//				}
				else if(item.@id == "21") {    
					labelText = localizator.getText('toolpanel.UML.class');
					item.@name = labelText;
				}
				else if(item.@id == "22") {    
					labelText = localizator.getText('toolpanel.UML.object');
					item.@name = labelText;
				}
				else if(item.@id == "23") {    
					labelText = localizator.getText('toolpanel.UML.port');
					item.@name = labelText;
				}
				else if(item.@id == "24") {    
					labelText = localizator.getText('toolpanel.UML.subsystem');
					item.@name = labelText;
				}
				else if(item.@id == "25") {    
					labelText = localizator.getText('toolpanel.UML.dataclass');
					item.@name = labelText;
				}
				else if(item.@id == "26") {    
					labelText = localizator.getText('toolpanel.work.bosses');
					item.@name = labelText;
				}
				else if(item.@id == "27") {    
					labelText = localizator.getText('toolpanel.work.custom');
					item.@name = labelText;
				}
				else if(item.@id == "28") {    
					labelText = localizator.getText('toolpanel.work.procurement');
					item.@name = labelText;
				}
				else if(item.@id == "29") {    
					labelText = localizator.getText('toolpanel.work.sales');
					item.@name = labelText;
				}
				else if(item.@id == "30") {    
					labelText = localizator.getText('toolpanel.work.production');
					item.@name = labelText;
				}
				else if(item.@id == "31") {    
					labelText = localizator.getText('toolpanel.work.workers');
					item.@name = labelText;
				}
				else if(item.@id == "32") {    
					labelText = localizator.getText('toolpanel.work.finance');
					item.@name = labelText;
				}
				else if(item.@id == "33") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.boss');
					item.@name = labelText;
				}
				else if(item.@id == "34") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.manager');
					item.@name = labelText;
				}
				else if(item.@id == "35") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.consultant');
					item.@name = labelText;
				}
				else if(item.@id == "36") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.vacancy');
					item.@name = labelText;
				}
				else if(item.@id == "37") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.assistant');
					item.@name = labelText;
				}
				else if(item.@id == "38") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.staff');
					item.@name = labelText;
				}
				else if(item.@id == "39") {    
					labelText = localizator.getText('toolpanel.officeOrgnization.position');
					item.@name = labelText;
				}
				else if(item.@id == "40") {    
					labelText = localizator.getText('toolpanel.network.servers');
					item.@name = labelText;
				}
				else if(item.@id == "41") {    
					labelText = localizator.getText('toolpanel.network.ringNetwork');
					item.@name = labelText;
				}
				else if(item.@id == "42") {    
					labelText = localizator.getText('toolpanel.network.superComputer');
					item.@name = labelText;
				}
				else if(item.@id == "43") {    
					labelText = localizator.getText('toolpanel.network.printer');
					item.@name = labelText;
				}
				else if(item.@id == "44") {    
					labelText = localizator.getText('toolpanel.network.tax');
					item.@name = labelText;
				}
				else if(item.@id == "45") {    
					labelText = localizator.getText('toolpanel.network.projector');
					item.@name = labelText;
				}
				else if(item.@id == "46") {    
					labelText = localizator.getText('toolpanel.network.screen');
					item.@name = labelText;
				}
				else if(item.@id == "47") {    
					labelText = localizator.getText('toolpanel.network.router');
					item.@name = labelText;
				}
				else if(item.@id == "48") {    
					labelText = localizator.getText('toolpanel.network.modem');
					item.@name = labelText;
				}
				else if(item.@id == "49") {    
					labelText = localizator.getText('toolpanel.network.phone');
					item.@name = labelText;
				}
				else if(item.@id == "50") {    
					labelText = localizator.getText('toolpanel.network.wirelessNetwork');
					item.@name = labelText;
				}
				else if(item.@id == "51") {    
					labelText = localizator.getText('toolpanel.network.firewall');
					item.@name = labelText;
				}
				else if(item.@id == "52") {    
					labelText = localizator.getText('toolpanel.network.users');
					item.@name = labelText;
				}
				else if(item.@id == "53") {    
					labelText = localizator.getText('toolpanel.programStructure.function');
					item.@name = labelText;
				}
				else if(item.@id == "54") {    
					labelText = localizator.getText('toolpanel.programStructure.processDesign');
					item.@name = labelText;
				}
				else if(item.@id == "55") {    
					labelText = localizator.getText('toolpanel.programStructure.functionCall');
					item.@name = labelText;
				}
				else if(item.@id == "56") {    
					labelText = localizator.getText('toolpanel.programStructure.button');
					item.@name = labelText;
				}
				else if(item.@id == "57") {    
					labelText = localizator.getText('toolpanel.programStructure.cloud');
					item.@name = labelText;
				}
				else if(item.@id == "58") {    
					labelText = localizator.getText('toolpanel.programStructure.vocabulary');
					item.@name = labelText;
				}else if(item.@id == "59") {    
					labelText = localizator.getText('toolpanel.datamodel.start');
					item.@name = labelText;
				}else if(item.@id == "60") {    
					labelText = localizator.getText('toolpanel.datamodel.end');
					item.@name = labelText;
				}else if(item.@id == "61") {    
					labelText = localizator.getText('toolpanel.datamodel.document');
					item.@name = labelText;
				}else if(item.@id == "62") {    
					labelText = localizator.getText('toolpanel.datamodel.data');
					item.@name = labelText;
				}else if(item.@id == "63") {    
					labelText = localizator.getText('toolpanel.datamodel.ready');
					item.@name = labelText;
				}

			}
			
			this.basicXMLList = tools.descendants("basic");
			this.businessXMLList = tools.descendants("business");
			this.bpelXMLList = tools.descendants("bpel");
			//qu
			this.dataModelXMLList = tools.descendants("dataModel");
			this.UMLXMLList = tools.descendants("UML");
			this.workXMLList = tools.descendants("work");
			this.officeOrgnizationXMLList = tools.descendants("officeOrgnization");
			this.networkXMLList = tools.descendants("network");
			this.programStructureXMLList = tools.descendants("programStructure");

		}
		private function initDataModelBox() :void{
			dataModelBox = new Canvas();
			dataModelBox.label = localizator.getText('toolpanel.datamodel');
			dataModelBox.percentHeight = 100;
			dataModelBox.percentWidth = 100;
			dataModelToolTile.setXMLData(dataModelXMLList);
//			dataModelBox.addChild(dataModelToolTile);
//			addFigure(dataModelBox);
			dataModelToolTile.initArray();
//			var can:Canvas = new ComponentsCanvas();
//			dataModelBox.addChild(can);
			var cc:ComponentsCanvas = new ComponentsCanvas(this);
			dataModelBox.addChild(cc);
			
			dataModelToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
//			cc.addEventListener(MouseEvent.CLICK,componentsCanvas_click);
			dataModelBox.addEventListener(MouseEvent.CLICK,dataModelboxClickHandle);
		}
//		public function addFigure(dataModelBox:VBox):void{
//			var label:Label = new Label();
//			var image:Image = new Image();
//			image.source = FigureFactory.circuit;
//			label.addChild(image);
//			dataModelBox.addChild(label);
//			image.source = FigureFactory.start;
//			dataModelBox.addChild(image);
//			
//		}
		private function initUMLBox() :void{
			UMLBox = new VBox();
			UMLBox.label = localizator.getText('toolpanel.UML');
			UMLBox.percentHeight = 100;
			UMLBox.percentWidth = 100;
			UMLToolTile.setXMLData(UMLXMLList);
			UMLBox.addChild(UMLToolTile);
			UMLToolTile.initArray();
			UMLToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			UMLBox.addEventListener(MouseEvent.CLICK,UMLboxClickHandle);
		}
		private function initWorkBox() :void{
			workBox = new VBox();
			workBox.label = localizator.getText('toolpanel.work');
			workBox.percentHeight = 100;
			workBox.percentWidth = 100;
			workToolTile.setXMLData(workXMLList);
			workBox.addChild(workToolTile);
			workToolTile.initArray();
			workToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			workBox.addEventListener(MouseEvent.CLICK,workboxClickHandle);
		}
		private function initOfficeOrgnizationBox() :void{
			officeOrgnizationBox = new VBox();
			officeOrgnizationBox.label = localizator.getText('toolpanel.officeOrgnization');
			officeOrgnizationBox.percentHeight = 100;
			officeOrgnizationBox.percentWidth = 100;
			officeOrgnizationToolTile.setXMLData(officeOrgnizationXMLList);
			officeOrgnizationBox.addChild(officeOrgnizationToolTile);
			officeOrgnizationToolTile.initArray();
			officeOrgnizationToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			officeOrgnizationBox.addEventListener(MouseEvent.CLICK,officeOrgnizationboxClickHandle);
		}
		private function initNetworkBox() :void{
			networkBox = new VBox();
			networkBox.label = localizator.getText('toolpanel.network');
			networkBox.percentHeight = 100;
			networkBox.percentWidth = 100;
			networkToolTile.setXMLData(networkXMLList);
			networkBox.addChild(networkToolTile);
			networkToolTile.initArray();
			networkToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			networkBox.addEventListener(MouseEvent.CLICK,networkboxClickHandle);
		}
		private function initProgramStructureBox() :void{
			programStructureBox = new VBox();
			programStructureBox.label = localizator.getText('toolpanel.programStructure');
			programStructureBox.percentHeight = 100;
			programStructureBox.percentWidth = 100;
			programStructureToolTile.setXMLData(programStructureXMLList);
			programStructureBox.addChild(programStructureToolTile);
			programStructureToolTile.initArray();
			programStructureToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			programStructureBox.addEventListener(MouseEvent.CLICK,programStructureboxClickHandle);
		}

		
		private function initBasicBox() :void{
			basicBox = new VBox();
			basicBox.label = localizator.getText('toolpanel.basic');
			basicBox.percentHeight = 100;
			basicBox.percentWidth = 100;
			basicToolTile.setXMLData(basicXMLList);
			basicBox.addChild(basicToolTile);
			basicToolTile.initArray();
			basicToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			basicToolTile.addEventListener(ListEvent.ITEM_ROLL_OUT,this.releaseTheCursor);
			basicBox.addEventListener(MouseEvent.CLICK,basicboxClickHandle);
		}
		
		private function initBpelBox() :void{
			bpelBox = new VBox();
			bpelBox.label = localizator.getText('toolpanel.bpel');
			bpelBox.percentHeight = 100;
			bpelBox.percentWidth = 100;
			bpelToolTile.setXMLData(bpelXMLList);
			bpelBox.addChild(bpelToolTile);
			bpelToolTile.initArray();
			bpelToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			bpelBox.addEventListener(MouseEvent.CLICK,bpelboxClickHandle);
		}
		
		private function initBusinessBox() :void{
			
			businessBox = new VBox();
			businessBox.label = localizator.getText('toolpanel.business');
			businessBox.percentHeight = 100;
			businessBox.percentWidth = 100;
			businessToolTile.setXMLData(businessXMLList);
			businessBox.addChild(businessToolTile);
			businessToolTile.initArray();
			businessToolTile.addEventListener(ListEvent.ITEM_CLICK, this.itemClickHandle);
			businessBox.addEventListener(MouseEvent.CLICK,businessboxClickHandle);
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
		
		private function releaseTheCursor(event:ListEvent):void{
			CursorManager.removeCursor(forbiddeniconid);
		}
		
		private function itemClickHandle(event : ListEvent) :void {
			
			isSelected=true;
			
			var selectedFigureName : String = (event.itemRenderer.data as LinkButton).label;
			var selectedFigId : int = FigureFactory.nametoid(selectedFigureName);
			if( (OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==false)||
				(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)&&
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState == true)			
				new ToolPanelAppEvent( ToolPanelAppEvent.SELECT_FIGURE,
					{selectedFigureId :selectedFigId} ).dispatch();
			else 
			{
				forbiddeniconid = CursorManager.setCursor(forbiddenicon);
				//				Alert.show("Sorry,Please get the TOKEN");
			}
			
		}
		public function componentsCanvas_click(figureName:String){
				isSelected=true;
				var selectedFigureName:String = figureName;
				var selectedFigId : int = FigureFactory.nametoid(selectedFigureName);
				if( (OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==false)||
					(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)&&
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState == true)			
					new ToolPanelAppEvent( ToolPanelAppEvent.SELECT_FIGURE,
						{selectedFigureId :selectedFigId} ).dispatch();
				else 
				{
					forbiddeniconid = CursorManager.setCursor(forbiddenicon);
				}
			
		}
		private function basicboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		
		private function bpelboxClickHandle(event:MouseEvent):void{
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
		
		//qu
		private function dataModelboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		private function UMLboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		private function workboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		private function officeOrgnizationboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		private function networkboxClickHandle(event:MouseEvent):void{
			if(!isSelected){
				this.resetAllSelections();
			}
			else{
				isSelected=false;
			}
		}
		private function programStructureboxClickHandle(event:MouseEvent):void{
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
			this.bpelToolTile.selectedIndex=-1;
			this.businessToolTile.selectedIndex=-1;
			//qu
			this.dataModelToolTile.selectedIndex=-1;
			this.UMLToolTile.selectedIndex=-1;
			this.workToolTile.selectedIndex=-1;
			this.officeOrgnizationToolTile.selectedIndex=-1;
			this.networkToolTile.selectedIndex=-1;
			this.programStructureToolTile.selectedIndex=-1;

			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
		}
		
		
		
	}
}