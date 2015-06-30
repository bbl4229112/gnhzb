//qu second
package org.act.od.impl.figure
{
	public class Dataclassow2Figure extends UMLFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Dataclassow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new UMLAttribute();
			
			this.drawid=124;
			this.setpicture(FigureFactory.dataclass);
		}
		
	}
}


