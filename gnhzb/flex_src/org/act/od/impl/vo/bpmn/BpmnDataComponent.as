package org.act.od.impl.vo.bpmn
{
	/**
	 * 
	 * @author Quyue
	 * 
	 */	
	public class BpmnDataComponent
	{
		private  var dataName : String;
		private  var type : String ;
		private  var value: String ;
		
		
		public function BpmnDataComponent( type:String, value:String)
		{
			this.type = type;
			this.value = value;
		}
		/**
		 *
		 * @return
		 *
		 */
		public function get Value():String
		{
			return this.value;
		}
		/**
		 *
		 * @param value
		 *
		 */
		public function set 	Value(value : String): void
		{
			this.value = value;
		}
		
		
		/**
		 *
		 * @return
		 *
		 */
		public function get Type():String
		{
			return this.type;
		}
		/**
		 *
		 * @param value
		 *
		 */
		public function set Type(type : String): void
		{
			this.type = type;
		}
		
		/**
		 *
		 * @return
		 *
		 */
		public function get DataName():String
		{
			return this.dataName;
		}
		
		/**
		 *
		 * @param value
		 *
		 */
		public function set DataName(data : String): void
		{
			this.dataName = data;
		}
		
	}
	
}

