//qu second
package org.act.od.impl.figure
{
	public class OfficeOrgnizationFigure extends Activityow2Figure
	{
		import org.act.od.impl.vo.*;
		
		public function OfficeOrgnizationFigure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
		}
	}
}