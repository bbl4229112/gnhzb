package org.act.od.impl.figure
{
	import org.act.od.impl.vo.VoiceAttribute;
	
	public class Voiceow2Figure extends BusinessFigure
	{
		public function Voiceow2Figure()
		{
			super();
			this.attribute = new VoiceAttribute;
			
			this.drawid=108;
			this.setpicture(FigureFactory.voice);
		}
		
	}
}