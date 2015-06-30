package org.act.od.impl.figure
{
	public class MicroImageNodeArrowFigure extends ConnectionFigure
	{
		public function MicroImageNodeArrowFigure(processType:String=null)
		{
			super(processType);
		}
		
		override public function drawpicture():void
		{
			sprt.graphics.lineStyle(this.lineThickness,0xB6AFA9,1);
		}
	}
}