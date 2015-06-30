package org.act.od.impl.commands
{
	import mx.collections.ArrayCollection;
	import mx.controls.Label;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.KnowledgeRelatedView;
	
	public class KnowledgeViewUpdateCMD extends AODCommand
	{
		
		private var orDesModelLoc:OrDesignerModelLocator;
		
		public function KnowledgeViewUpdateCMD()
		{
			super();
		}
		private var selectFig:AbstractFigure;
		override public function execute(event:OrDesignerEvent):void
		{
			orDesModelLoc = OrDesignerModelLocator.getInstance();	
			selectFig = event.data.selectedFigure as AbstractFigure;
			
			if(selectFig != null) {
				orDesModelLoc.getKnowledgeViewModel().editedFigure = selectFig;				
				var remote:RemoteObject = new RemoteObject();
				remote.destination = "knowledgeSource";
				var activeModel:FigureEditorModel = orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel;  //获得当前xml文件的id，20120908。
				var xmlId:Number = Number(activeModel.rootModel.fileID);
				var figureId:String = selectFig.figureId;
				remote.KnowledgeRelatedList(xmlId, figureId, "related");
				remote.addEventListener(ResultEvent.RESULT, krListResultHandler);
				
//				orDesModelLoc.getOrchestraDesigner().getfigureAttributeNavigator().knowledgeRelatedBox
//					.label = "“" + selectFig.figureName + "”的知识资源";
			} else {
				//OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().hideKnowledgeRelated();
				orDesModelLoc.getKnowledgeViewModel().editedFigure = null;
				orDesModelLoc.getKnowledgeViewModel().updateKnowledgeRelated();
			}
		}
		
		public function krListResultHandler(event:ResultEvent):void {
			var list:ArrayCollection = event.result as ArrayCollection;
			var knowledgeRelatedView:KnowledgeRelatedView = OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().
				knowledgeRelatedBox.getChildAt(0) as KnowledgeRelatedView;
			if(list.length == 0) {
				//应该让空的datagrid消失不见。提示“该节点当前没有关联知识。”			
				knowledgeRelatedView.getChildAt(0).visible = false;
				knowledgeRelatedView.getChildAt(1).visible = false;
				knowledgeRelatedView.getChildAt(2).visible = false;
				knowledgeRelatedView.getChildAt(3).visible = true;
			} else {
				knowledgeRelatedView.getChildAt(0).visible = true;
				knowledgeRelatedView.getChildAt(1).visible = true;
				knowledgeRelatedView.getChildAt(2).visible = true;
				knowledgeRelatedView.getChildAt(3).visible = false;
				orDesModelLoc.getKnowledgeViewModel().knowledgeRelated = list;
				orDesModelLoc.getKnowledgeViewModel().updateKnowledgeRelated();
			}
		}
	}
}