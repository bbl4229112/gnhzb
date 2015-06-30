package org.act.od.impl.figure
{
	public class Receiveow2Figure extends BPELFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Receiveow2Figure()
		{
			super();
			this.attribute = new ReceiveAttribute();
			
			this.drawid=113;
			this.setpicture(FigureFactory.receive);
		}
		
	}
}