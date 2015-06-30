//qu second
package org.act.od.impl.figure
{
	public class Portow2Figure extends UMLFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Portow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new UMLAttribute();
			
			this.drawid=122;
			this.setpicture(FigureFactory.port);
		}
		
	}
}


