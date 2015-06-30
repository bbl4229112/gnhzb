//qu 2011/3/5 2011/2/20
package org.act.od.impl.figure
{
	public class Circuitow2Figure extends DataModelFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Circuitow2Figure()
		{
			super();
			
			//added by zjn
			this.attribute = new DataModelAttribute();
			
			this.drawid=115;
			this.setpicture(null);  //初始不设置业务活动节点的图标，留待以后判断有下层模型时再修改图标显示。20120925
		}
		
	}
}


