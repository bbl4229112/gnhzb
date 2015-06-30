package org.act.od.impl.view
{	
	import mx.containers.Box;
	import mx.containers.TabNavigator;
	import mx.events.IndexChangedEvent;
	
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.bpmn.BpmnFigureData;
	import org.act.od.impl.viewhelper.FigureAttributeNavigatorVH;
	
	/**
	 * The view for figure's attribute.
	 * 
	 * @author Quyue
	 * 
	 * modified by Mengsong 2010-8-6 20:50:23
	 */
	public class FigureAttributeNavigator extends TabNavigator
	{
		/**
		 * The source for "properties" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/properties.gif")]
		public var properties :Class;
		/**
		 * The source for "knowledge" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/knowledge.gif")]
		public var knowledge :Class;
		[Bindable]
		[Embed(source="/../assets/icon/container/best.png")]
		public var bestPractice :Class;
		
		private var figureAttributeView :AttributeView;
		private var bpmnFigureData : BpmnFigureData ;
		private var chatRoom:ChatPanel;		
		private var knowledgeInputView:KnowledgeInputView;
		private var knowledgeRelatedView:KnowledgeRelatedView;
		private var knowledgeOutputView:KnowledgeOutputView;
		private var bestPracticeView:BestPracticeView;
		
		public var figureAttributeBox:Box = new Box();
		private var bpmnDataBox:Box = new Box();
		private var chatRoomBox:Box = new Box();
		public var knowledgeInputBox:Box = new Box();
		public var knowledgeRelatedBox:Box = new Box();
		public var knowledgeOutputBox:Box = new Box();
		public var bestPracticeBox:Box = new Box();
		
		private var figureNavigatorVH:FigureAttributeNavigatorVH;
		
		/**
		 * Initialize figure's attribute view.
		 */
		public function FigureAttributeNavigator(){
			super();
			figureAttributeView = new AttributeView();
			figureAttributeView.percentHeight = 100;
			figureAttributeView.percentWidth = 100;
			figureAttributeBox.addChild(figureAttributeView);
			figureAttributeBox.percentHeight = 100;
			figureAttributeBox.percentWidth = 100;
			figureAttributeBox.label = "节点信息";
			figureAttributeBox.icon = properties;
			
			knowledgeInputView = new KnowledgeInputView();
			knowledgeInputView.percentHeight = 100;
			knowledgeInputView.percentWidth = 100;
			knowledgeInputBox.addChild(knowledgeInputView);
			knowledgeInputBox.percentHeight = 100;
			knowledgeInputBox.percentWidth = 100;
			knowledgeInputBox.label = "输入文档";
			knowledgeInputBox.icon = knowledge;			
			
			knowledgeRelatedView = new KnowledgeRelatedView();
			knowledgeRelatedView.percentHeight = 100;
			knowledgeRelatedView.percentWidth = 100;
			knowledgeRelatedBox.addChild(knowledgeRelatedView);
			knowledgeRelatedBox.percentHeight = 100;
			knowledgeRelatedBox.percentWidth = 100;
			knowledgeRelatedBox.label = "支撑知识";
			knowledgeRelatedBox.icon = knowledge;
			
			knowledgeOutputView = new KnowledgeOutputView();
			knowledgeOutputView.percentHeight = 100;
			knowledgeOutputView.percentWidth = 100;
			knowledgeOutputBox.addChild(knowledgeOutputView);
			knowledgeOutputBox.percentHeight = 100;
			knowledgeOutputBox.percentWidth = 100;
			knowledgeOutputBox.label = "输出文档";
			knowledgeOutputBox.icon = knowledge;
			
			bestPracticeView = new BestPracticeView();
			bestPracticeView.percentHeight = 100;
			bestPracticeView.percentWidth = 100;
			bestPracticeBox.addChild(bestPracticeView);
			bestPracticeBox.percentHeight = 100;
			bestPracticeBox.percentWidth = 100;
			bestPracticeBox.label = "最佳实践";
			bestPracticeBox.icon = bestPractice;
								
			this.setStyle("borderStyle","solid");
			this.percentHeight=100;
			this.percentWidth=80;
			
			figureNavigatorVH = new FigureAttributeNavigatorVH(this, FigureAttributeNavigatorVH.VH_KEY);
			this.initEventListener();

		}
		
		public function showFigureAttribute():void{
			this.addChild(figureAttributeBox);
			this.isHide();
		}
		public function hideFigureAttribute():void{
			this.removeChild(figureAttributeBox);
			this.isHide();
		}
		public function showKnowledgeInput():void {
			this.addChild(knowledgeInputBox);
			this.isHide();
		}
		public function hideKnowledgeInput():void {
			this.removeChild(knowledgeInputBox);
			this.isHide();
		}
		public function showKnowledgeRelated():void{
			this.addChild(knowledgeRelatedBox);
			this.isHide();
		}
		public function hideKnowledgeRelated():void {
			this.removeChild(knowledgeRelatedBox);
			this.isHide();
		}
		public function showKnowledgeOutput():void {
			this.addChild(knowledgeOutputBox);
			this.isHide();
		}
		public function hideKnowledgeOutput():void {
			this.removeChild(knowledgeOutputBox);
			this.isHide();
		}
		public function showBestPractice():void {
			this.addChild(bestPracticeBox);
			this.isHide();
		}
		public function hideBestPractice():void {
			this.removeChild(bestPracticeBox);
			this.isHide();
		}
		private function getChildNumber():int{
			return this.numChildren;
		}		
		private function isHide():void{
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner()!=null)
			{
				if(this.getChildNumber()==0)
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().bottomMdBox.height = 1;
				else
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().bottomMdBox.height = 215;
			}
		}
		
		private function initEventListener():void {
			this.addEventListener(IndexChangedEvent.CHANGE, figureNavigatorVH.onTabIndexChangeHandle);
		}
		
	}
}