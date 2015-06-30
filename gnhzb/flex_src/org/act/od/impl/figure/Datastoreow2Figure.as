//qu 2011/3/5 2011/2/20
package org.act.od.impl.figure
{
	public class Datastoreow2Figure extends DataModelFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Datastoreow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new DataModelAttribute();
			
			this.drawid=117;
			this.setpicture(FigureFactory.datastore);
		}
		
	}
}
