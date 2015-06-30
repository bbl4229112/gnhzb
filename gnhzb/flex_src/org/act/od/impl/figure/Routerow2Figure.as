//qu second
package org.act.od.impl.figure
{
	public class Routerow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Routerow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=146;
			this.setpicture(FigureFactory.router);
		}
		
	}
}


