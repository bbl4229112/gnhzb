package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about voice figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class VoiceAttribute extends BusinessAttribute
	{
		public var TelephoneNumber:String;
		
		public var Body:String;
		
		public function VoiceAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			atts.addItem({name: "Telephone Number: ", value: TelephoneNumber, flag: "true"});
			atts.addItem({name: "Body: ", value: Body, flag: "true"});
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
			for each (var att:Object in atts)
			{
				
				if (att.name == "Telephone Number: ")
				{
					this.TelephoneNumber=att.value;
				}
				else if (att.name == "Body: ")
				{
					this.Body=att.value;
				}
			}
		}
		
		override public function getAttributeXML():Array
		{
			var xmlArray:Array=super.getAttributeXML();
			
			var tnNode:XML=new XML("<Attribute></Attribute>");
			tnNode.@name="TelephoneNumber";
			tnNode.appendChild(this.TelephoneNumber);
			
			var bodyNode:XML=new XML("<Attribute></Attribute>");
			bodyNode.@name="Body";
			bodyNode.appendChild(this.Body);
			
			xmlArray.push(tnNode, bodyNode);
			
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			var tnNode:XMLList=atts.Attribute.(@name == "TelephoneNumber");
			var bodyNode:XMLList=atts.Attribute.(@name == "Body");
			
			this.TelephoneNumber=(tnNode.children() != null) ? tnNode.children().toString() : "";
			this.Body=(bodyNode.children() != null) ? bodyNode.children().toString() : "";
		}
	}
}