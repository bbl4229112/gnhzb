//qu second
package org.act.od.impl.figure
{
	public class Subsystemow2Figure extends UMLFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Subsystemow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new UMLAttribute();
			
			this.drawid=123;
			this.setpicture(FigureFactory.subsystem);
		}
		
	}
}


