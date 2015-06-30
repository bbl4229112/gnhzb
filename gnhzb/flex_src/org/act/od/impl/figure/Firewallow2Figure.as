//qu second
package org.act.od.impl.figure
{
	public class Firewallow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Firewallow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=150;
			this.setpicture(FigureFactory.firewall);
		}
		
	}
}


