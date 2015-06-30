//qu second
package org.act.od.impl.figure
{
	public class Procurementow2Figure extends WorkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Procurementow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new WorkAttribute();
			
			this.drawid=127;
			this.setpicture(FigureFactory.procurement);
		}
		
	}
}


