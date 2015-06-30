package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import mx.states.*;
	import org.act.od.impl.state.*;
	/**
	 * 
	 * @author Quyue
	 * 
	 */	
	
	public class DefaultWindowsCMD extends AODCommand
	{
		public function DefaultWindowsCMD()
		{
			super();
		}
		override public function execute(event :OrDesignerEvent) :void
		{
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().currentState = "";
		}
		
	}
}