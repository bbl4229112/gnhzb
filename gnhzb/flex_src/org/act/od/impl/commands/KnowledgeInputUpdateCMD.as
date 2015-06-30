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
	import org.act.od.impl.view.KnowledgeInputView;
	
	public class KnowledgeInputUpdateCMD extends AODCommand
	{
		
		private var orDesModelLoc:OrDesignerModelLocator;
		
		public function KnowledgeInputUpdateCMD()
		{
			super();
		}
		
		override public function execute(event:OrDesignerEvent):void
		{
			orDesModelLoc = OrDesignerModelLocator.getInstance();	
			var selectFig:AbstractFigure = event.data.selectedFigure as AbstractFigure;
			
			if(selectFig != null) {
				orDesModelLoc.getKnowledgeInputModel().editedFigure = selectFig;
				var remote:RemoteObject = new RemoteObject();
				remote.destination = "knowledgeSource";
				var activeModel:FigureEditorModel = orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel;  //获得当前xml文件的id，20120908。
				var xmlId:Number = Number(activeModel.rootModel.fileID);
				var figureId:String = selectFig.figureId;
				remote.KnowledgeRelatedList(xmlId, figureId, "input");
				remote.addEventListener(ResultEvent.RESULT, kiListResultHandler);
			} else {
				//OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().hideKnowledgeInput();
			}
		}
		
		public function kiListResultHandler(event:ResultEvent):void {
			var list:ArrayCollection = event.result as ArrayCollection;
			var knowledgeInputView:KnowledgeInputView = OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().
				knowledgeInputBox.getChildAt(0) as KnowledgeInputView;
			if(list.length == 0) {
				//应该让空的datagrid消失不见。提示“该节点当前没有关联知识。”			
				knowledgeInputView.getChildAt(0).visible = false;
				knowledgeInputView.getChildAt(1).visible = false;
				knowledgeInputView.getChildAt(2).visible = false;
				knowledgeInputView.getChildAt(3).visible = true;
			} else {
				knowledgeInputView.getChildAt(0).visible = true;
				knowledgeInputView.getChildAt(1).visible = true;
				knowledgeInputView.getChildAt(2).visible = true;
				knowledgeInputView.getChildAt(3).visible = false;
				orDesModelLoc.getKnowledgeInputModel().knowledgeInput = list;
				orDesModelLoc.getKnowledgeInputModel().knowledgeInput.refresh();				
			}
		}
	}
}