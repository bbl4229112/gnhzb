package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about paper figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class PagerAttribute extends BusinessAttribute
	{
		public var FromNumber:String;
		
		public var PagerNumber:String;
		
		public var Body:String;
		
		public function PagerAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			atts.addItem({name: "From Number: ", value: FromNumber, flag: "true"});
			atts.addItem({name: "Pager Number: ", value: PagerNumber, flag: "true"});
			atts.addItem({name: "Body: ", value: Body, flag: "true"});
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
			for each (var att:Object in atts)
			{
				
				if (att.name == "From Number: ")
				{
					this.FromNumber=att.value;
				}
				else if (att.name == "Pager Number: ")
				{
					this.PagerNumber=att.value;
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
			fnNode.@name="FromNumber";
			fnNode.appendChild(this.FromNumber);
			
			var pnNode:XML=new XML("<Attribute></Attribute>");
			pnNode.@name="PagerNumber";
			pnNode.appendChild(this.PagerNumber);
			
			var bodyNode:XML=new XML("<Attribute></Attribute>");
			bodyNode.@name="Body";
			bodyNode.appendChild(this.Body);
			
			xmlArray.push(fnNode, pnNode, bodyNode);
			
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			var fnNode:XMLList=atts.Attribute.(@name == "FromNumber");
			var pnNode:XMLList=atts.Attribute.(@name == "PagerNumber");
			var bodyNode:XMLList=atts.Attribute.(@name == "Body");
			
			this.FromNumber=(fnNode.children() != null) ? fnNode.children().toString() : "";
			this.PagerNumber=(pnNode.children() != null) ? pnNode.children().toString() : "";
			this.Body=(bodyNode.children() != null) ? bodyNode.children().toString() : "";
		}
		
	}
}