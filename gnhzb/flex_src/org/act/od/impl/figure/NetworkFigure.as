//qu second
package org.act.od.impl.figure
{
	public class NetworkFigure extends Activityow2Figure
	{
		import org.act.od.impl.vo.*;
		
		public function NetworkFigure()
		{
			super();
			
			//added by zjn
			this.attribute = new NetworkAttribute();
		}
	}
}