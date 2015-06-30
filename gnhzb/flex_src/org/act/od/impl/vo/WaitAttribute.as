package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about wait figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class WaitAttribute extends BPELAttribute
	{
		public var isFor:String;
		
		public var until:String;
		
		public var suppressJoinFailure:String;
		
		public function WaitAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			atts.addItem({name: "For: ", value: isFor, flag: "true"});
			atts.addItem({name: "Until: ", value: until, flag: "true"});
			atts.addItem({name: "Suppress Join Failure: ", value: suppressJoinFailure, flag: "true"});
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
			for each (var att:Object in atts)
			{
				
				if (att.name == "For: ")
				{
					this.isFor=att.value;
				}
				else if (att.name == "Until: ")
				{
					this.until=att.value;
				}
				else if (att.name == "Suppress Join Failure: ")
				{
					this.suppressJoinFailure=att.value;
				}
			}
		}
		
		override public function getAttributeXML():Array
		{
			var xmlArray:Array=super.getAttributeXML();
			
			var forNode:XML=new XML("<Attribute></Attribute>");
			forNode.@name="isFor";
			forNode.appendChild(this.isFor);
			
			var utilNode:XML=new XML("<Attribute></Attribute>");
			utilNode.@name="until";
			utilNode.appendChild(this.until);
			
			var sjfNode:XML=new XML("<Attribute></Attribute>");
			sjfNode.@name="suppressJoinFailure";
			sjfNode.appendChild(this.suppressJoinFailure);
			
			xmlArray.push(sjfNode, utilNode, sjfNode);
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			var forNode:XMLList=atts.Attribute.(@name == "isFor");
			var utilNode:XMLList=atts.Attribute.(@name == "until");
			var sjfNode:XMLList=atts.Attribute.(@name == "suppressJoinFailure");
			
			this.isFor=(forNode.children() != null) ? forNode.children().toString() : "";
			this.until=(utilNode.children() != null) ? utilNode.children().toString() : "";
			this.suppressJoinFailure=(sjfNode.children() != null) ? sjfNode.children().toString() : "";
		}
	}
}