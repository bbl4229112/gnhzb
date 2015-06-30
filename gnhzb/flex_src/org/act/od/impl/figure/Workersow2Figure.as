//qu second
package org.act.od.impl.figure
{
	public class Workersow2Figure extends WorkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Workersow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new WorkAttribute();
			
			this.drawid=130;
			this.setpicture(FigureFactory.workers);
		}
		
	}
}


