//qu second
package org.act.od.impl.figure
{
	public class Customow2Figure extends WorkFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Customow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new WorkAttribute();
			
			this.drawid=126;
			this.setpicture(FigureFactory.custom);
		}
		
	}
}


