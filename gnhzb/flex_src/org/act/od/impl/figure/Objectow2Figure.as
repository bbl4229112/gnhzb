//qu second
package org.act.od.impl.figure
{
	public class Objectow2Figure extends UMLFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Objectow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new UMLAttribute();
			
			this.drawid=121;
			this.setpicture(FigureFactory.ob_ject);
		}
		
	}
}


