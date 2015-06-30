package org.act.od.impl.figure
{
	public class Replyow2Figure extends BPELFigure
	{
		import org.act.od.impl.vo.*;
		
		public function Replyow2Figure()
		{
			super();
			this.attribute = new ReplyAttribute();
			
			this.drawid=110;
			this.setpicture(FigureFactory.reply);
		}
		
	}
}