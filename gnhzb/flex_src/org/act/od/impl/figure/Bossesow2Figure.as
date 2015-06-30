//qu second
package org.act.od.impl.figure
{
	public class Bossesow2Figure extends WorkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Bossesow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new WorkAttribute();
			
			this.drawid=125;
			this.setpicture(FigureFactory.bosses);
		}
		
	}
}


