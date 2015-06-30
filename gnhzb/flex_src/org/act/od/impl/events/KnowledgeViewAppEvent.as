package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	
	public class KnowledgeViewAppEvent extends OrDesignerEvent
	{
		public static const KNOWLEDGEVIEW_UPDATE:String  = "knowledgeView_update";
		
		public static const KNOWLEDGEINPUT_UPDATE:String = "knowledgeInput_update";
		
		public static const KNOWLEDGEOUTPUT_UPDATE:String = "knowledgeOutput_update";
		
		public static const BESTPRACTICE_UPDATE:String = "bestPractice_update";
		
		public static const KNOWLEDGERELATED_POPUP:String = "knowledgeRelated_popup";
		
		public function KnowledgeViewAppEvent(type:String, dataParamter:Object=null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, dataParamter, bubbles, cancelable);
		}
	}
}