package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about SMS figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class SMSAttribute extends BusinessAttribute
	{
		public var FromNumber:String;
		
		public var TelephoneNumber:String;
		
		public var Subject:String;
		
		public var Body:String;
		
		public function SMSAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			atts.addItem({name: "From Number: ", value: FromNumber, flag: "true"});
			atts.addItem({name: "Telephone Number: ", value: TelephoneNumber, flag: "true"});
			atts.addItem({name: "Subject: ", value: Subject, flag: "true"});
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
				else if (att.name == "Telephone Number: ")
				{
					this.TelephoneNumber=att.value;
				}
				else if (att.name == "Subject: ")
				{
					this.Subject=att.value;
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
			
			var tnNode:XML=new XML("<Attribute></Attribute>");
			tnNode.@name="TelephoneNumber";
			tnNode.appendChild(this.TelephoneNumber);
			
			var subjectNode:XML=new XML("<Attribute></Attribute>");
			subjectNode.@name="Subject";
			subjectNode.appendChild(this.Subject);
			
			var bodyNode:XML=new XML("<Attribute></Attribute>");
			bodyNode.@name="Body";
			bodyNode.appendChild(this.Body);
			
			xmlArray.push(fnNode, tnNode, subjectNode, bodyNode);
			
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			var fnNode:XMLList=atts.Attribute.(@name == "FromNumber");
			var tnNode:XMLList=atts.Attribute.(@name == "TelephoneNumber");
			var subjectNode:XMLList=atts.Attribute.(@name == "Subject");
			var bodyNode:XMLList=atts.Attribute.(@name == "Body");
			
			this.FromNumber=(fnNode.children() != null) ? fnNode.children().toString() : "";
			this.TelephoneNumber=(tnNode.children() != null) ? tnNode.children().toString() : "";
			this.Subject=(subjectNode.children() != null) ? subjectNode.children().toString() : "";
			this.Body=(bodyNode.children() != null) ? bodyNode.children().toString() : "";
		}
		
	}
}