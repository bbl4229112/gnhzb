package org.act.od.impl.viewhelper
{
	import org.act.od.framework.view.ViewHelper;
	import org.act.od.impl.view.FigureAttributeNavigator;
	import flash.events.Event;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.events.KnowledgeViewAppEvent;
	import org.act.od.impl.events.AttributeViewAppEvent;
	
	/**
	 * @author Likexin
	 */	
	public class FigureAttributeNavigatorVH extends ViewHelper
	{
		public static const VH_KEY :String = "FigureAttributeNavigatorVH";
		public function FigureAttributeNavigatorVH(document : Object, id : String)
		{
			super();
			initialized(document, id);			
		}
		/**
		 * Return FigureAttributeNavigator
		 */
		public function get figureAttributeNavigator():FigureAttributeNavigator{
			return this.view as FigureAttributeNavigator;
		}
		
		public function onTabIndexChangeHandle(event :Event) :void {
			var currentFig :AbstractFigure = OrDesignerModelLocator.getInstance().getAttributeViewModel().editedFigure;
			if(this.figureAttributeNavigator.selectedIndex == 0) {
				new AttributeViewAppEvent(AttributeViewAppEvent.ATTRIBUTEVIEW_UPDATE,
					{selectedFigure:currentFig} ).dispatch();
			} else if(this.figureAttributeNavigator.selectedIndex == 1) {
				new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGEINPUT_UPDATE,
					{selectedFigure:currentFig}).dispatch();
			} else if(this.figureAttributeNavigator.selectedIndex == 2) {
				new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGEVIEW_UPDATE,
					{selectedFigure:currentFig}).dispatch();
			} else if(this.figureAttributeNavigator.selectedIndex == 3) {
				new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGEOUTPUT_UPDATE,
					{selectedFigure:currentFig}).dispatch();
			} else if(this.figureAttributeNavigator.selectedIndex == 4) {
				new KnowledgeViewAppEvent(KnowledgeViewAppEvent.BESTPRACTICE_UPDATE,
					{selectedFigure:currentFig}).dispatch();
			}
		}
	}
}