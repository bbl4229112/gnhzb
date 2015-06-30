//qu second
package org.act.od.impl.figure
{
	public class Buttonow2Figure extends ProgramStructureFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Buttonow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new ProgramStructureAttribute();
			
			this.drawid=155;
			this.setpicture(FigureFactory.button);
		}
		
	}
}


