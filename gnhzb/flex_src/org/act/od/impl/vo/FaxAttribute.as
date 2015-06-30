package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about fax figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class FaxAttribute extends BusinessAttribute
	{
		public var FaxNumber:String;
		
		public var CoverPage:String;
		
		public var Body:String;
		
		public function FaxAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			atts.addItem({name: "Fax Number: ", value: FaxNumber, flag: "true"});
			atts.addItem({name: "Cover Page: ", value: CoverPage, flag: "true"});
			atts.addItem({name: "Body: ", value: Body, flag: "true"});
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
			for each (var att:Object in atts)
			{
				
				if (att.name == "Fax Number: ")
				{
					this.FaxNumber=att.value;
				}
				else if (att.name == "Cover Page: ")
				{
					this.CoverPage=att.value;
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
			
			var fnNode:XML=new XML("<Attribute></Attribute>");
			fnNode.@name="FaxNumber";
			fnNode.appendChild(this.FaxNumber);
			
			var cpNode:XML=new XML("<Attribute></Attribute>");
			cpNode.@name="CoverPage";
			cpNode.appendChild(this.CoverPage);
			
			var bodyNode:XML=new XML("<Attribute></Attribute>");
			bodyNode.@name="Body";
			bodyNode.appendChild(this.Body);
			
			xmlArray.push(fnNode, cpNode, bodyNode);
			
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			var fnNode:XMLList=atts.Attribute.(@name == "FaxNumber");
			var cpNode:XMLList=atts.Attribute.(@name == "CoverPage");
			var bodyNode:XMLList=atts.Attribute.(@name == "Body");
			
			this.FaxNumber=(fnNode.children() != null) ? fnNode.children().toString() : "";
			this.CoverPage=(cpNode.children() != null) ? cpNode.children().toString() : "";
			this.Body=(bodyNode.children() != null) ? bodyNode.children().toString() : "";
		}
		
	}
}