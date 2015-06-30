package org.act.od.impl.vo.bpmn
{
	import mx.collections.ArrayCollection;
	
	import org.act.od.impl.vo.BusinessAttribute;
	
	/**
	 * The attribute about email figure.
	 * 
	 * @author Quyue
	 * 
	 */
	public class PoolAttribute extends BusinessAttribute
	{
		
		public function PoolAttribute()
		{
			super();
		}
		
		
		override public function getAttributeArray():ArrayCollection
		{
			var atts:ArrayCollection=super.getAttributeArray();
			
			return atts;
		}
		
		override public function update(atts:ArrayCollection):void
		{
			super.update(atts);
			
		}
		
		override public function getAttributeXML():Array
		{
			var xmlArray:Array=super.getAttributeXML();
			
			return xmlArray;
		}
		
	}
}