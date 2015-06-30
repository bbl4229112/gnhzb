//qu second
package org.act.od.impl.figure
{
	public class Consultantow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Consultantow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=134;
			this.setpicture(FigureFactory.consultant);
		}
		
	}
}


