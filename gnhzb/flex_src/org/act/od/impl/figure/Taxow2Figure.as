//qu second
package org.act.od.impl.figure
{
	public class Taxow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Taxow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=143;
			this.setpicture(FigureFactory.tax);
		}
		
	}
}


