package org.act.od.impl.figure
{
	import org.act.od.impl.vo.SMSAttribute;
	
	public class SMSow2Figure extends BusinessFigure
	{
		public function SMSow2Figure()
		{
			super();
			this.attribute = new SMSAttribute;
			
			this.drawid=107;
			this.setpicture(FigureFactory.sms);
		}
		
	}
}