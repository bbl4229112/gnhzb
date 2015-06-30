package org.act.od.impl.figure
{
	import org.act.od.impl.vo.PagerAttribute;
	
	public class Pagerow2Figure extends BusinessFigure
	{
		public function Pagerow2Figure()
		{
			super();
			this.attribute = new PagerAttribute;
			
			this.drawid=104;
			this.setpicture(FigureFactory.pager);
		}
		
	}
}