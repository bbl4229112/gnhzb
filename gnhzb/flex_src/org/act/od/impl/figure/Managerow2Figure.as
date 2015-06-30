//qu second
package org.act.od.impl.figure
{
	public class Managerow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Managerow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=133;
			this.setpicture(FigureFactory.manager);
		}
		
	}
}


