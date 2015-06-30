package org.act.od.impl.viewhelper
{
	import mx.events.FlexEvent;
	
	import org.act.od.framework.view.ViewHelper;
	import org.act.od.impl.events.UDDIRefViewAppEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.UDDIReferenceModel;
	import org.act.od.impl.view.UDDIReferenceView;
	/**
	 * The ViewHelper of UDDIReference.
	 * Used to isolate command classes from the implementation details of a view.
	 * 
	 * @author Likexin
	 * 
	 */
	public class UDDIReferenceVH extends ViewHelper{
		
		/**
		 * The key of UDDIReferenceVH
		 */
		public static const VH_KEY:String = "UDDIReferenceViewH";		
		
		/**
		 * Initialize UDDIReference with UDDIReferenceVH
		 */
		public function UDDIReferenceVH(document : Object, id : String){
			super();
			initialized(document, id);
		}
		/**
		 * Return UDDIReferenceView
		 */
		private function get uddiReferenceView() :UDDIReferenceView{
			return this.view as UDDIReferenceView;
		}
		
		/**
		 * Initialize the content 
		 * @param event
		 * 
		 */
		public function initContent(event : FlexEvent) : void {
			new UDDIRefViewAppEvent(UDDIRefViewAppEvent.GET_REF_FROM_SERVER).dispatch();
		}
	}
}