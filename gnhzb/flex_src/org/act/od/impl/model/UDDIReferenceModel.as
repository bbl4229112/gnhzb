package org.act.od.impl.model
{
	
	import mx.controls.Alert;
	
	/**
	 * Store message of UDDIRefView.
	 * 
	 * @ author Zhaoxq
	 * 
	 */
	public class UDDIReferenceModel
	{
		/**
		 * The xmlList for the dataprovider of UDDIRefView
		 */
		[Bindable]
		public var xmllist:XMLList = new XMLList();
		/**
		 * The xml for the dataprovider of UDDIRefView
		 */
		private var xml : XML = new XML();
		
		/**
		 * The xmlList for the portType of UDDIRefView
		 */
		[Bindable]
		public var xmlPortType:XML = new XML();
		
		public function UDDIReferenceModel(){
			
		}
		
		public function setXMLContent(xml : XML) : void {
			this.xml = xml;
			this.xmllist = xml.elements();
		}
		
		public function appendXMLContent(xml : XML) : void {
			
			this.xml.appendChild(xml);
			this.xmllist = xml.elements();
		}
	}
}