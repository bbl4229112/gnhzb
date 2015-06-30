//qu second
package org.act.od.impl.figure
{
	public class Positionow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Positionow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=138;
			this.setpicture(FigureFactory.position);
		}
		
	}
}


