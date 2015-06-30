//qu second
package org.act.od.impl.figure
{
	public class Screenow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Screenow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=145;
			this.setpicture(FigureFactory.screen);
		}
		
	}
}


