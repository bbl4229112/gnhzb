package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import mx.rpc.remoting.RemoteObject;
	import org.act.od.impl.model.FigureEditorModel;
	import mx.rpc.events.ResultEvent;
	import mx.collections.ArrayCollection;
	import org.act.od.impl.view.KnowledgeOutputView;
	import mx.controls.Label;
	
	public class KnowledgeOutputUpdateCMD extends AODCommand
	{
		private var orDesModelLoc:OrDesignerModelLocator;
		
		public function KnowledgeOutputUpdateCMD()
		{
			super();
		}
		
		override public function execute(event:OrDesignerEvent):void
		{
			orDesModelLoc = OrDesignerModelLocator.getInstance();	
			var selectFig:AbstractFigure = event.data.selectedFigure as AbstractFigure;
			
			if(selectFig != null) {
				orDesModelLoc.getKnowledgeOutputModel().editedFigure = selectFig;
				var remote:RemoteObject = new RemoteObject();
				remote.destination = "knowledgeSource";
				var activeModel:FigureEditorModel = orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel;  //获得当前xml文件的id，20120908。
				var xmlId:Number = Number(activeModel.rootModel.fileID);
				var figureId:String = selectFig.figureId;
				remote.KnowledgeRelatedList(xmlId, figureId, "output");
				remote.addEventListener(ResultEvent.RESULT, koListResultHandler);
			} else {
				//OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().hideKnowledgeOutput()
			}
		}
		
		public function koListResultHandler(event:ResultEvent):void {
			var list:ArrayCollection = event.result as ArrayCollection;
			var knowledgeOutputView:KnowledgeOutputView = OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().
				knowledgeOutputBox.getChildAt(0) as KnowledgeOutputView;
			if(list.length == 0) {
				//应该让空的datagrid消失不见。提示“该节点当前没有关联知识。”			
				knowledgeOutputView.getChildAt(0).visible = false;
				knowledgeOutputView.getChildAt(1).visible = false;
				knowledgeOutputView.getChildAt(2).visible = false;
				knowledgeOutputView.getChildAt(3).visible = true;
			} else {
				knowledgeOutputView.getChildAt(0).visible = true;
				knowledgeOutputView.getChildAt(1).visible = true;
				knowledgeOutputView.getChildAt(2).visible = true;
				knowledgeOutputView.getChildAt(3).visible = false;
				orDesModelLoc.getKnowledgeOutputModel().knowledgeOutput = list;
				orDesModelLoc.getKnowledgeOutputModel().knowledgeOutput.refresh();				
			}
		}
	}
}