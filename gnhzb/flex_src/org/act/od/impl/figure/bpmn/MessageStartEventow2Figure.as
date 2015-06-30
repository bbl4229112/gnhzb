package org.act.od.impl.figure.bpmn
{
	import mx.controls.Alert;
	
	import org.act.od.impl.figure.Startow2Figure;
	import org.act.od.impl.model.FigureEditorModel;
	
	public class MessageStartEventow2Figure extends Startow2Figure
	{
		
		public function MessageStartEventow2Figure(processType:String = FigureEditorModel.BPEL_PROCESS_TYPE)
		{
			super(processType);
			drawid=116;
			standardwidth=25*1.2*1.2;
			standardheight=25*1.2*1.2;
			r=standardwidth/2;
			width=standardwidth;
			height=standardheight;
			
			if(processType==FigureEditorModel.BPMN_PROCESS_TYPE)
				this.setpicture(BpmnFigureFactory.messageStart);
			else
				Alert.show("Problem : processType==FigureEditorModel.BPMN_PROCESS_TYPE ");
		}
		
		
	}
}