//qu second
package org.act.od.impl.figure
{
	public class Vacancyow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Vacancyow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=135;
			this.setpicture(FigureFactory.vacancy);
		}
		
	}
}


