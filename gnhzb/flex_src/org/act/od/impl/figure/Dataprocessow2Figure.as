//qu 2011/3/5 2011/2/20
package org.act.od.impl.figure
{
	public class Dataprocessow2Figure extends DataModelFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Dataprocessow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new DataModelAttribute();
			
			this.drawid=118;
			this.setpicture(FigureFactory.dataprocess);
		}
		
	}
}
