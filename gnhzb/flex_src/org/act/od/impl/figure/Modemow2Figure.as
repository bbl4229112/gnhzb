//qu second
package org.act.od.impl.figure
{
	public class Modemow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Modemow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=147;
			this.setpicture(FigureFactory.modem);
		}
		
	}
}


