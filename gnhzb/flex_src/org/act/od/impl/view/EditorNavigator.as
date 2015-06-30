package org.act.od.impl.view
{
	import org.act.od.framework.view.SuperTabNavigator;
	import org.act.od.framework.view.SuperTabEvent;
	import mx.events.IndexChangedEvent;
	import org.act.od.framework.view.SuperTabBar;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	
	/**
	 * The parent panel of FigureEditor and BPELEditor.
	 * 
	 * @author Quyue
	 * 
	 */
	public class EditorNavigator extends SuperTabNavigator {
		
		//viewHelper
		private var figureEditorNavigatorVH :EditorNavigatorVH;
		
		/**
		 * Initialize FigureEditorNavigator
		 */
		public function EditorNavigator(){
			super();
			
			//view init
			this.setStyle("borderStyle","solid");
			this.dragEnabled = true;//disable drag function
			
			this.percentHeight=100;
			this.percentWidth=100;
			
			//viewHelper init
			figureEditorNavigatorVH = new EditorNavigatorVH(this, EditorNavigatorVH.VH_KEY);
			
			this.initEventListener();
		}
		
		
		private function initEventListener() :void{
			
			this.addEventListener(SuperTabEvent.TAB_CLOSE, figureEditorNavigatorVH.onTabCloseHandle);//20120907
			
			this.addEventListener(IndexChangedEvent.CHANGE, figureEditorNavigatorVH.onTabIndexChangeHandle);

		}
		
	}
}