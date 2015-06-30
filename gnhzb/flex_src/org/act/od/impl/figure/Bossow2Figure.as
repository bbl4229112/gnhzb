//qu second
package org.act.od.impl.figure
{
	public class Bossow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Bossow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=132;
			this.setpicture(FigureFactory.boss);
		}
		
	}
}


