package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about email figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class EmailAttribute extends BusinessAttribute
	{
		
		public var FromAccount:String;
		
		public var To:String;
		
		public var Co:String;
		
		public var Bcc:String;
		
		public var ReplyTo:String;
		
		public var Subject:String;
		
		public var Body:String;
		
		public function EmailAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			atts.addItem({name: "From Account: ", value: FromAccount, flag: "true"});
			atts.addItem({name: "To: ", value: To, flag: "true"});
			atts.addItem({name: "Co: ", value: Co, flag: "true"});
			atts.addItem({name: "Bcc: ", value: Bcc, flag: "true"});
			atts.addItem({name: "Reply To: ", value: ReplyTo, flag: "true"});
			atts.addItem({name: "Subject: ", value: Subject, flag: "true"});
			atts.addItem({name: "Body: ", value: Body, flag: "true"});
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
			for each (var att:Object in atts)
			{
				
				if (att.name == "From Account: ")
				{
					this.FromAccount=att.value;
				}
				else if (att.name == "To: ")
				{
					this.To=att.value;
				}
				else if (att.name == "Co: ")
				{
					this.Co=att.value;
				}
				else if (att.name == "Bcc: ")
				{
					this.Bcc=att.value;
				}
				else if (att.name == "Reply To: ")
				{
					this.ReplyTo=att.value;
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
			
			var fromAccountNode:XML=new XML("<Attribute></Attribute>");
			fromAccountNode.@name="FromAccount";
			fromAccountNode.appendChild(this.FromAccount);
			
			var toNode:XML=new XML("<Attribute></Attribute>");
			toNode.@name="To";
			toNode.appendChild(this.To);
			
			var coNode:XML=new XML("<Attribute></Attribute>");
			coNode.@name="Co";
			coNode.appendChild(this.Co);
			
			var bccNode:XML=new XML("<Attribute></Attribute>");
			bccNode.@name="Bcc";
			bccNode.appendChild(this.Bcc);
			
			var replyToNode:XML=new XML("<Attribute></Attribute>");
			replyToNode.@name="ReplyTo";
			replyToNode.appendChild(this.ReplyTo);
			
			var subjectNode:XML=new XML("<Attribute></Attribute>");
			subjectNode.@name="Subject";
			subjectNode.appendChild(this.Subject);
			
			var bodyNode:XML=new XML("<Attribute></Attribute>");
			bodyNode.@name="Body";
			bodyNode.appendChild(this.Body);
			
			xmlArray.push(fromAccountNode, toNode, coNode, bccNode, replyToNode, subjectNode, bodyNode);
			
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			var fromAccountNode:XMLList=atts.Attribute.(@name == "FromAccount");
			var toNode:XMLList=atts.Attribute.(@name == "To");
			var coNode:XMLList=atts.Attribute.(@name == "Co");
			var bccNode:XMLList=atts.Attribute.(@name == "Bcc");
			var replyToNode:XMLList=atts.Attribute.(@name == "ReplyTo");
			var subjectNode:XMLList=atts.Attribute.(@name == "Subject");
			var bodyNode:XMLList=atts.Attribute.(@name == "Body");
			
			this.FromAccount=(fromAccountNode.children() != null) ? fromAccountNode.children().toString() : "";
			this.To=(toNode.children() != null) ? toNode.children().toString() : "";
			this.Co=(coNode.children() != null) ? coNode.children().toString() : "";
			this.Bcc=(bccNode.children() != null) ? bccNode.children().toString() : "";
			this.ReplyTo=(replyToNode.children() != null) ? replyToNode.children().toString() : "";
			this.Subject=(subjectNode.children() != null) ? subjectNode.children().toString() : "";
			this.Body=(bodyNode.children() != null) ? bodyNode.children().toString() : "";
		}
		
	}
}