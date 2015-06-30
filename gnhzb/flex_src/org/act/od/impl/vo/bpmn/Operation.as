package org.act.od.impl.vo.bpmn
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	[Bindable]
	[RemoteClass(alias="wsdl.OperationImplm")]
	/**
	 * 
	 * @author Quyue
	 * 
	 */	
	public class Operation implements IExternalizable
	{
		
		private var name : String;
		
		
		public function Operation()
		{
		}
		
		
		public function readExternal(input:IDataInput):void {
			name = input.readObject() as String;
		}
		
		public function writeExternal(output:IDataOutput):void {
			output.writeObject(name);
		}
		
		public function get Name()
		{
			return this.name;
		}
		
		public function set Name(name:String)
		{
			this.name = name;
		}
	}
}