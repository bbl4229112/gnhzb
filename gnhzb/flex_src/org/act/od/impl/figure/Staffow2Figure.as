//qu second
package org.act.od.impl.figure
{
	public class Staffow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Staffow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=137;
			this.setpicture(FigureFactory.staff);
		}
		
	}
}


