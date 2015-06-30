package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about assign figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class AssignAttribute extends BPELAttribute
	{
		public var From : String;
		
		public var To : String;
		
		public function AssignAttribute(){
			super();
		}
		
		override public function getAttributeArray():ArrayCollection {
			var atts:ArrayCollection = super.getAttributeArray();
			
			atts.addItem({name: "From: ", value: From, flag: "true"});
			atts.addItem({name: "To: ", value: To, flag: "true"});
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void {
			super.update(atts);
			
			for each(var att:Object in atts) {
				
				if(att.name == "From: ") {
					this.From = att.value;
				}
				else if(att.name == "To: ") {
					this.To = att.value;
				}
			}
		}
		
		override public function getAttributeXML():Array {
			var xmlArray:Array = super.getAttributeXML();
			
			var fromNode:XML = new XML("<Attribute></Attribute>");
			fromNode.@name = "From";
			fromNode.appendChild(this.From);
			
			var toNode:XML = new XML("<Attribute></Attribute>");
			toNode.@name = "To";
			toNode.appendChild(this.To);
			
			xmlArray.push(fromNode,toNode);
			
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void {
			var fromNode:XMLList = atts.Attribute.(@name == "From");
			var toNode:XMLList = atts.Attribute.(@name == "To");
			
			this.From = (fromNode.children()!=null)?fromNode.children().toString():"";
			this.To = (toNode.children()!=null)?toNode.children().toString():"";
		}
	}
}