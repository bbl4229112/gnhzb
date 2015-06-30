//qu second
package org.act.od.impl.figure
{
	public class RingNetworkow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function RingNetworkow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=140;
			this.setpicture(FigureFactory.ringNetwork);
		}
		
	}
}


