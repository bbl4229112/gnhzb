package org.act.od.impl.vo
{
	import mx.collections.ArrayCollection;
	import mx.utils.ObjectProxy;
	
	import org.act.od.impl.other.StringConvertor;

	/**
	 * The attribute about basic figures.
	 * @author Quyue-->LiTao
	 * @date 20120806
	 */
	public class BasicAttribute implements IAttribute
	{
		public var input:String;
		public var output:String;
		public var control:String;
		public var mechanism:String;
		
		public function BasicAttribute():void {
			this.input = "";
			this.output = "";
			this.control = "";
			this.mechanism = "";
		}
		
		public function getAttributeArray():ArrayCollection {
			
			var atts:ArrayCollection = new ArrayCollection();
			atts.addItem(new ObjectProxy({name: "输入信息", value: input, flag: "true"}));
			atts.addItem(new ObjectProxy({name: "输出信息", value: output, flag: "true"}));
			atts.addItem(new ObjectProxy({name: "控制", value: control, flag: "true"}));
			atts.addItem(new ObjectProxy({name: "机制", value: mechanism, flag: "true"}));
			return atts;
		}
		
		public function update(atts:ArrayCollection):void {
			for each(var att:Object in atts) {
				if(att.name == "输入信息") {
					this.input = att.value;
				} else if(att.name == "输出信息") {
					this.output = att.value;
				} else if(att.name == "控制") {
					this.control = att.value;
				} else if(att.name == "机制") {
					this.mechanism = att.value;
				}							
			}
		}
		
		public function getAttributeXML():Array {
			var xmlArray:Array = new Array();
			
			var inputNode:XML = new XML("<Attribute></Attribute>");
			inputNode.@name = "input";
			inputNode.appendChild(this.input);
			
			var outputNode:XML = new XML("<Attribute></Attribute>");
			outputNode.@name = "output";
			outputNode.appendChild(this.output);
			
			var controlNode:XML = new XML("<Attribute></Attribute>");
			controlNode.@name = "control";
			controlNode.appendChild(this.control);
			
			var mechanismNode:XML = new XML("<Attribute></Attribute>");
			mechanismNode.@name = "mechanism";
			mechanismNode.appendChild(this.mechanism);
			
			xmlArray.push(inputNode,outputNode,controlNode,mechanismNode);
			return xmlArray;
		}
		
		public function setAttribute(atts:XMLList):void {
			var inputNode:XMLList = atts.Attribute.(@name == "input");
			var outputNode:XMLList = atts.Attribute.(@name == "output");
			var controlNode:XMLList = atts.Attribute.(@name == "control");
			var mechanismNode:XMLList = atts.Attribute.(@name == "mechanism");
			
			this.input = (inputNode.children()!=null)?inputNode.children().toString():"";
			this.output = (outputNode.children()!=null)?outputNode.children().toString():"";
			this.control = (controlNode.children()!=null)?controlNode.children().toString():"";
			this.mechanism = (mechanismNode.children()!=null)?mechanismNode.children().toString():"";
			
		}		
	}
}