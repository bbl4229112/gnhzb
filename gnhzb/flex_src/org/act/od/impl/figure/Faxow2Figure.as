package org.act.od.impl.figure
{
	import org.act.od.impl.vo.FaxAttribute;
	
	public class Faxow2Figure extends BusinessFigure
	{
		public function Faxow2Figure()
		{
			super();
			this.attribute = new FaxAttribute;
			
			this.drawid=105;
			this.setpicture(FigureFactory.fax);
		}
		
	}
}