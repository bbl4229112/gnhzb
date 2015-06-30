package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.Startow2Figure;
	/**
	 * 
	 * @author Mengsong
	 * 
	 */
	public class poolClicCMD extends AODCommand
	{
		
		
		public function poolClicCMD()
		{
			super();
		}
		
		/**
		 * @param event {subProcessFigure}
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var start : Startow2Figure = event.data.point as Startow2Figure;
			start.showContextPanel();
			
		}
		
		
	}
}