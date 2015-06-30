package org.act.od.impl.figure
{
	import org.act.od.impl.vo.EmailAttribute;
	
	public class Emailow2Figure extends BusinessFigure
	{
		public function Emailow2Figure()
		{
			super();
			this.attribute = new EmailAttribute();
			
			this.drawid=106;
			this.setpicture(FigureFactory.email);
		}
		
	}
}