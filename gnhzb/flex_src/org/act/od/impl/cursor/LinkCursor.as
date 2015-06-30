package org.act.od.impl.cursor
{
	import flash.display.Sprite;
	import mx.controls.Image;
	
	public class LinkCursor extends Sprite
	{
		protected var image:Image=new Image();
		public function LinkCursor()
		{
			super();
			this.graphics.lineStyle(2,0x000000,1);
			this.graphics.moveTo(6,-6);
			this.graphics.lineTo(-6,6);
			this.graphics.lineTo(-6,2);
			this.graphics.moveTo(-6,6);
			this.graphics.lineTo(-2,6);
		}
		
	}
}