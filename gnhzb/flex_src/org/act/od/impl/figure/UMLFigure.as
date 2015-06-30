//qu second
package org.act.od.impl.figure
{
	public class UMLFigure extends Activityow2Figure
	{
		import org.act.od.impl.vo.*;
		
		public function UMLFigure()
		{
			super();
			
			//added by zjn
			this.attribute = new UMLAttribute();
		}
	}
}