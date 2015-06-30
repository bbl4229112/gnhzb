package org.act.od.impl.commands
{
	import mx.containers.Canvas;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.view.MicroImageNode;
	
	/**
	 * @Author LiTao
	 * @Date 20120809
	 */
	public class CanvasNodeSelectedCMD extends AODCommand
	{
		public function CanvasNodeSelectedCMD()
		{
			super();
		}
		/**
		 * @param event{canvas}
		 */
		override public function execute(event:OrDesignerEvent):void {
			var selectedFigure:AbstractFigure = event.data.selectedFigure as AbstractFigure;
			MicroImageNode.getInstance().updateShow(selectedFigure);
		}
	}
}