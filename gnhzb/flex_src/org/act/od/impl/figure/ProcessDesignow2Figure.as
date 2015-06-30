//qu second
package org.act.od.impl.figure
{
	public class ProcessDesignow2Figure extends ProgramStructureFigure
	{
		import org.act.od.impl.vo.*;
		
		public function ProcessDesignow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new ProgramStructureAttribute();
			
			this.drawid=153;
			this.setpicture(FigureFactory.processDesign);
		}
		
	}
}


