//qu second
package org.act.od.impl.figure
{
	public class WirelessNetworkow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function WirelessNetworkow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=149;
			this.setpicture(FigureFactory.wirelessNetwork);
		}
		
	}
}


