package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	
	/**
	 * The attribute about invoke figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class InvokeAttribute extends BPELAttribute
	{
		public var inputVariable:String="";
		
		public var outputVariable:String="";
		
		public var operation:String="";
		
		public var partnerLink:String="";
		
		public var portType:String="";
		
		public var suppressJoinFailure:String="";
		
		public function InvokeAttribute()
		{
			super();
		}
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			atts.addItem({name: "Input Variable: ", value: inputVariable, flag: "true"});
			atts.addItem({name: "Output Variable: ", value: outputVariable, flag: "true"});
			atts.addItem({name: "Operation: ", value: operation, flag: "true"});
			atts.addItem({name: "Partner Link: ", value: partnerLink, flag: "true"});
			atts.addItem({name: "Port Type: ", value: portType, flag: "true"});
			atts.addItem({name: "Suppress Join Failure: ", value: suppressJoinFailure, flag: "true"});
			return atts;
		}
		
		/**
		 * update the invoke attributes by refering to the needing-update attribute object array collection
		 * @param atts
		 *
		 */
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
			for each (var att:Object in atts)
			{
				
				if (att.name == "Input Variable: ")
				{
					this.inputVariable=att.value;
				}
				else if (att.name == "Output Variable: ")
				{
					this.outputVariable=att.value;
				}
				else if (att.name == "Operation: ")
				{
					this.operation=att.value;
				}
				else if (att.name == "Partner Link: ")
				{
					this.partnerLink=att.value;
				}
				else if (att.name == "Port Type: ")
				{
					this.portType=att.value;
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
			
			var ivNode:XML=new XML("<Attribute></Attribute>");
			ivNode.@name="inputVariable";
			//			ivNode.@value = this.inputVariable;
			ivNode.appendChild(this.inputVariable);
			
			var ovNode:XML=new XML("<Attribute></Attribute>");
			ovNode.@name="outputVariable";
			//			ovNode.@value = this.outputVariable;
			ovNode.appendChild(this.outputVariable);
			
			var opNode:XML=new XML("<Attribute></Attribute>");
			opNode.@name="operation";
			//			opNode.@value = this.operation;
			opNode.appendChild(this.operation);
			
			var plNode:XML=new XML("<Attribute></Attribute>");
			plNode.@name="partnerLink";
			//			plNode.@value = this.partnerLink;
			plNode.appendChild(this.partnerLink);
			
			var ptNode:XML=new XML("<Attribute></Attribute>");
			ptNode.@name="portType";
			//			ptNode.@value = this.portType;
			ptNode.appendChild(this.portType);
			
			var sjfNode:XML=new XML("<Attribute></Attribute>");
			sjfNode.@name="suppressJoinFailure";
			//			sjfNode.@value = this.suppressJoinFailure;
			sjfNode.appendChild(this.suppressJoinFailure);
			
			
			xmlArray.push(ivNode, ovNode, opNode, plNode, ptNode, sjfNode);
			return xmlArray;
		}
		
		override public function setAttribute(atts:XMLList):void
		{
			super.setAttribute(atts);
			
			var ivNode:XMLList=atts.Attribute.(@name == "inputVariable");
			var ovNode:XMLList=atts.Attribute.(@name == "outputVariable");
			var opNode:XMLList=atts.Attribute.(@name == "operation");
			var plNode:XMLList=atts.Attribute.(@name == "partnerLink");
			var ptNode:XMLList=atts.Attribute.(@name == "portType");
			var sjfNode:XMLList=atts.Attribute.(@name == "suppressJoinFailure");
			
			this.inputVariable=(ivNode.children() != null) ? ivNode.children().toString() : "";
			this.outputVariable=(ovNode.children() != null) ? ovNode.children().toString() : "";
			this.operation=(opNode.children() != null) ? opNode.children().toString() : "";
			this.partnerLink=(plNode.children() != null) ? plNode.children().toString() : "";
			this.portType=(ptNode.children() != null) ? ptNode.children().toString() : "";
			this.suppressJoinFailure=(sjfNode.children() != null) ? sjfNode.children().toString() : "";
		}
	}
}