//qu second
package org.act.od.impl.figure
{
	public class Cloudow2Figure extends ProgramStructureFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Cloudow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new ProgramStructureAttribute();
			
			this.drawid=156;
			this.setpicture(FigureFactory.cloud);
		}
		
	}
}


