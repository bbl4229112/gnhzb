package org.act.od.impl.other
{
	import mx.skins.Border;
	/**
	 * Provide properties be used as the source for image data binding.
	 */
	public class BpmnResource
	{
		private static var instance: BpmnResource;
		
		/**
		 * The source for "start" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/event/start/messageStart.gif")]
		public var icon_tool_message_start :Class;
		
		/**
		 * The source for "start" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/event/end/noneEnd.gif")]
		public var icon_tool_none_end :Class;
		
		/**
		 * The source for "start" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/event/intermediate/messageIntermediate.gif")]
		public var icon_tool_message_intermediate :Class;
		
		/**
		 * The source for "start" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/start2.gif")]
		public var icon_tool_start :Class;
		
		/**
		 * The source for "end" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/end3.GIF")]
		public var icon_tool_end :Class;
		
		/**
		 * The source for "pool" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/pool.png")]
		public var icon_tool_pool :Class;
		
		/**
		 * The source for "link" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/link.gif")]
		public var icon_tool_link :Class;
		
		/**
		 * The source for "activity" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/tool/bpmn/activity.png")]
		public var icon_tool_activity :Class;
		
		/**
		 * The source for "Message Flow Link" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/smallicons/bpmn/btn-message_flow-link.png")]
		public var smallicons_btn_messageFlow_link :Class;
		
		
		/**
		 * The Resource constructor should only be created
		 * through the static singleton getInstance() method.  Resource
		 * provide properties be used as the source for image data binding.
		 */
		public function BpmnResource(){
			if(instance!=null)
				throw new Error("Error: Singletons can only be instantiated via getInstance() method!");
			BpmnResource.instance = this;
		}
		
		/**
		 * Singleton access to the Resource is assured through the static getInstance()
		 * method, which is used to retrieve the only ViewLocator instance in a Cairngorm
		 * application.
		 *
		 * <p>Wherever there is a need to retreive the Resource instance, it is achieved
		 * using the following code:</p>
		 *
		 * <pre>
		 * var resource:Resource = Resource.getInstance();
		 * </pre>
		 */
		public static function getInstance():BpmnResource{
			if(instance==null)
				instance = new BpmnResource();
			return instance;
		}
	}
}