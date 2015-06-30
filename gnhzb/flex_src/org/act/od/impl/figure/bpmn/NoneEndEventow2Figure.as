package org.act.od.impl.figure.bpmn
{
	import mx.controls.Alert;
	
	import org.act.od.impl.model.FigureEditorModel;
	
	
	public class NoneEndEventow2Figure extends EndEventow2Figure
	{
		
		public function NoneEndEventow2Figure(processType:String=null)
		{
			super(processType);
			
			
			this.drawid=117;
			
			standardwidth=25*1.2*1.2;
			standardheight=25*1.2*1.2;
			r=standardwidth/2;
			width=standardwidth;
			height=standardheight;
			
			if(processType != null)
			{
				if(processType == FigureEditorModel.BPMN_PROCESS_TYPE)
					this.setpicture(BpmnFigureFactory.noneEnd);
				else
					Alert.show("Problem : processType != FigureEditorModel.BPMN_PROCESS_TYPE");
			}
			else
				Alert.show("Problem : processType == null ");
			
			
		}
		
	}
}