//qu second
package org.act.od.impl.figure
{
	public class Salesow2Figure extends WorkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Salesow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new WorkAttribute();
			
			this.drawid=128;
			this.setpicture(FigureFactory.sales);
		}
		
	}
}


