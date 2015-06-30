package org.act.od.impl.figure
{
	public class Waitow2Figure extends BPELFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Waitow2Figure()
		{
			super();
			this.attribute = new WaitAttribute();
			
			this.drawid=111;
			this.setpicture(FigureFactory.wait);
		}
		
	}
}