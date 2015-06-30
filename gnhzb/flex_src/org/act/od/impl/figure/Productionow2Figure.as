//qu second
package org.act.od.impl.figure
{
	public class Productionow2Figure extends WorkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Productionow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new WorkAttribute();
			
			this.drawid=129;
			this.setpicture(FigureFactory.production);
		}
		
	}
}


