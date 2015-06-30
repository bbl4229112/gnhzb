package org.act.od.impl.figure
{
	public class Invokeow2Figure extends BPELFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Invokeow2Figure()
		{
			super();
			this.attribute = new InvokeAttribute();
			
			this.drawid=109;
			this.setpicture(FigureFactory.invoke);
		}
		
	}
}