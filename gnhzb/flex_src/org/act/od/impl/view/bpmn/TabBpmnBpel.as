package org.act.od.impl.view.bpmn
{
	import flash.events.Event;
	
	import mx.containers.Panel;
	import mx.containers.TabNavigator;
	import mx.events.IndexChangedEvent;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	
	/**
	 * create two tabs one for BPMN and the other for BPEL
	 * 
	 * @author Quyue
	 *
	 */
	public class TabBpmnBpel extends TabNavigator
	{
		//key for Bpel active tab
		public static const BPEL_TAB:String="BPEL_ACTIVE_TAB";
		//key for Bpmn active tab
		public static const BPMN_TAB:String="BPMN_ACTIVE_TAB";
		// Active tab (bpel tab or bpmn tab)
		private var _activeTabe:String=BPEL_TAB;
		
		/**
		 * Constructor
		 *
		 */
		public function TabBpmnBpel()
		{
			super();
			this.setStyle("borderStyle", "solid");
			
			//this.percentHeight = 100;
			this.percentWidth=100;
			
			//create BPEL TAB and BPMN TAB
			//this.createTab( ob );
			
			//create an listen to the event
			this.initEventListener();
			
		}
		private var _myTab:TabNavigator;
		
		/**
		 * Create Tab
		 *
		 * @param tabNavigator tab label
		 *
		 * @return Panel tab created
		 */
		public function createTab(label:String):Panel
		{
			
			var panelTab:Panel=new Panel();
			panelTab.percentHeight=100;
			panelTab.percentWidth=100;
			panelTab.label=label;
			
			// add canvas tab
			this.addChild(panelTab);
			
			//return canvasBPMN;
			return panelTab;
			
		}
		
		/**
		 * Change tab, and active selected tab
		 *
		 * @param evt
		 *
		 */
		private function changed(evt:IndexChangedEvent):void
		{
			var fileID:String;
			var figureEditorVH:FigureEditorVH;
			
			fileID=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel.fileID;
			figureEditorVH=ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(fileID)) as FigureEditorVH;
			// BPEL TAB
			if (evt.oldIndex == 0)
			{
				this._activeTabe=TabBpmnBpel.BPMN_TAB;
				figureEditorVH.figureEditor.loadGraphFile(figureEditorVH.figureEditor.bpmnFigureCanvas, figureEditorVH.figureEditor.bpmnFigureEditorModel);
			}
			else
			{ //BPMN TAB
				if (evt.oldIndex == 1)
				{
					this._activeTabe=TabBpmnBpel.BPEL_TAB;
					figureEditorVH.figureEditor.loadGraphFile(figureEditorVH.figureEditor.figureCanvas, figureEditorVH.figureEditor.figureEditorModel);
					
				}
				
			}
			
		}
		
		/**
		 * initialise the event
		 *
		 */
		private function initEventListener():void
		{
			this.addEventListener(Event.CHANGE, changed);
			
		}
		
		/**
		 * Get
		 *
		 * @return _activeTabe attribute
		 *
		 */
		public function get activeTab_():String
		{
			return this._activeTabe;
		}
	}
}

