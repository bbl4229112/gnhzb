package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	
	public class PositionKnowledgeAppEvent extends OrDesignerEvent
	{
		public static const POSITIONKNOWLEDGEVIEW:String = "position_knowledge_view";
		
		public function PositionKnowledgeAppEvent(type:String, dataParamter:Object=null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, dataParamter, bubbles, cancelable);
		}
	}
}