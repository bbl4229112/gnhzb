package org.act.od.impl.vo.bpmn
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	
	[Bindable]
	[RemoteClass(alias="wsdl.PortTypeImplm")]
	/**
	 * 
	 * @author Quyue
	 * 
	 */	
	public class PortType  implements IExternalizable
	{
		private var namespaceURI : String;
		private var localPart : String;
		private var prefix : String;
		private var operations : String;
		
		//private var operations : ArrayCollection;
		
		public function PortType()
		{
		}
		
		public function readExternal(input:IDataInput):void {
			namespaceURI = input.readObject() as String;
			localPart = input.readObject() as String;
			prefix = input.readObject() as String;
			//operations = input.readObject() as ArrayCollection;
			operations = input.readObject() as String;
		}
		
		public function writeExternal(output:IDataOutput):void {
			output.writeObject(namespaceURI);
			output.writeObject(localPart);
			output.writeObject(prefix);
			output.writeObject(operations);
			//output.writeObject(operation);
		}
		
		
		public function set NamespaceURI(name : String):void
		{
			this.namespaceURI = name;
		}
		
		public function get NamespaceURI():String
		{
			return this.namespaceURI;
		}
		
		public function get LocalPart():String
		{
			return this.localPart;
		}
		
		public function set LocalPart(local:String):void
		{
			this.localPart = local;
		}
		
		public function set Prefix(pref:String):void
		{
			this.prefix = pref;
		}
		
		public function get Prefix():String
		{
			return this.prefix;
		}
		
		public function set Operations(op:String):void
		{
			this.operations = op;
		}
		
		public function get Operations():String
		{
			return this.operations;
		}
		
		public function getArrayOperations():Array
		{
			return this.operations.split(";");
		}
	}
}