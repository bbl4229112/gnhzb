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
	
	public class FillTheWindowsCMD extends AODCommand
	{
		public function FillTheWindowsCMD()
		{
			super();
		}
		
		public function maximum():State
		{
			var newState:State = new State();
			
			newState.name = "the_maximun_state";
			var rmc_l:RemoveChild = new RemoveChild();
			rmc_l.target = OrDesignerModelLocator.getInstance().getOrchestraDesigner().leftBmPanel;
			newState.overrides[0] = rmc_l;
			
			var rmc_r:RemoveChild = new RemoveChild();
			rmc_r.target =  OrDesignerModelLocator.getInstance().getOrchestraDesigner().rightVDBox;
			newState.overrides[1] = rmc_r;
			
			var rmc_b:RemoveChild = new RemoveChild();
			rmc_b.target =  OrDesignerModelLocator.getInstance().getOrchestraDesigner().bottomMdBox;
			newState.overrides[2] = rmc_b;
			
			return newState;
		}
		override public function execute(event :OrDesignerEvent) :void
		{
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().states.push(maximum());
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().currentState = "the_maximun_state";
		}
		
		
	}
}