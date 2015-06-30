//qu 2011/3/5 2011/2/20
package org.act.od.impl.figure
{
	public class DataModelFigure extends Activityow2Figure
	{
		import org.act.od.impl.vo.*;
		
		public function DataModelFigure()
		{
			super();
			
			//added by zjn
			this.attribute = new DataModelAttribute();
		}
	}
}