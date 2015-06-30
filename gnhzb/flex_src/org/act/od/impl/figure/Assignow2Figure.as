package org.act.od.impl.figure
{
	import org.act.od.impl.vo.AssignAttribute;
	
	public class Assignow2Figure extends BPELFigure
	{
		public function Assignow2Figure()
		{
			super();
			
			this.attribute = new AssignAttribute;
			
			this.drawid=112;
			this.setpicture(FigureFactory.assign);
		}
		
	}
}