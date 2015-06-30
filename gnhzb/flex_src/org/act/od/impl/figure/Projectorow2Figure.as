//qu second
package org.act.od.impl.figure
{
	public class Projectorow2Figure extends NetworkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Projectorow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
			
			this.drawid=144;
			this.setpicture(FigureFactory.projector);
		}
		
	}
}


