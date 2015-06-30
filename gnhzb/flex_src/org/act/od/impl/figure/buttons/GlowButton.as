package org.act.od.impl.figure.buttons{
	import flash.events.MouseEvent;
	import flash.filters.GlowFilter;
	
	import mx.controls.Image;
	
	[Style(name="glowColor", type="uint", format="Color")]
	
	public class GlowButton extends Image {
		
		private var glowFilter:GlowFilter;
		
		public function GlowButton() {
			super();
			this.glowFilter = new GlowFilter;
			this.glowFilter.blurX = 12; glowFilter.blurY = 9;
			this.glowFilter.alpha = 1;
			this.glowFilter.quality = 2;
			this.glowFilter.knockout = false;
			this.glowFilter.strength = 1;
			this.addEventListener(MouseEvent.MOUSE_OVER, handleMouseOver);
			this.addEventListener(MouseEvent.MOUSE_OUT, handleMouseOut);
			this.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown);
			this.addEventListener(MouseEvent.MOUSE_UP, handleMouseUp);
			
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			this.glowFilter.color = this.getStyle("glowColor");
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		private function handleMouseOver(event:MouseEvent):void {
			this.filters = [this.glowFilter];
		}
		
		private function handleMouseOut(event:MouseEvent):void {
			this.filters = null;
		}
		
		private function handleMouseDown(event:MouseEvent):void {
			this.x += 1;
			this.y += 1;
		}
		
		private function handleMouseUp(event:MouseEvent):void {
			this.x -= 1;
			this.y -= 1;
		}
		
	}
}