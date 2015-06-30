//qu second
package org.act.od.impl.figure
{
	public class Serversow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Serversow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=139;
			this.setpicture(FigureFactory.servers);
		}
		
	}
}


