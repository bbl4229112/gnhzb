package org.act.od.impl.vo.bpmn
{
	[Bindable]
	[RemoteClass(alias="javax.xml.namespace.QName")]
	/**
	 * 
	 * @author Quyue
	 * 
	 */	
	public class QName
	{
		/**
		 * <p>Namespace URI of this <code>QName</code>.</p>
		 */
		private var namespaceURI : String;
		
		/**
		 * <p>local part of this <code>QName</code>.</p>
		 */
		private var localPart : String;
		
		/**
		 * <p>prefix of this <code>QName</code>.</p>
		 */
		private var prefix : String;
		
		public function QName()
		{
		}
		
		public function get NamespaceURI():String
		{
			return this.namespaceURI;
		}
		
		public function set NamespaceURI(namespaceURI:String)
		{
			this.namespaceURI = namespaceURI;
		}
		
		public function get LocalPart():String
		{
			return this.localPart;
		}
		
		public function set LocalPart(localPart:String)
		{
			this.localPart = localPart;
		}
		
		public function get Prefix():String
		{
			return this.prefix;
		}
		
		public function set Prefix(localPart:String)
		{
			this.prefix = prefix;
		}
	}
}