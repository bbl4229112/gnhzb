//qu second
package org.act.od.impl.figure
{
	public class Printerow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Printerow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=142;
			this.setpicture(FigureFactory.printer);
		}
		
	}
}


