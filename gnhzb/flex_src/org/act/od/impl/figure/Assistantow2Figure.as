//qu
package org.act.od.impl.figure
{
	public class Assistantow2Figure extends OfficeOrgnizationFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Assistantow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new OfficeOrgnizationAttribute();
			
			this.drawid=136;
			this.setpicture(FigureFactory.assistant);
		}
		
	}
}
