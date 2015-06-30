package org.act.od.impl.model
{
	import mx.collections.ArrayCollection;
	
	import org.act.od.impl.figure.AbstractFigure;

	public class KnowledgeViewModel
	{
		
		[Bindable]
		public var knowledgeRelated:ArrayCollection;
			
		/**
		 * The selected figure.
		 */
		public var editedFigure :AbstractFigure;
		
		public function KnowledgeViewModel():void
		{
			if(editedFigure != null) {
				knowledgeRelated = editedFigure.getKnowledgeRelated();
				
			} else {
				knowledgeRelated = new ArrayCollection();
			}
		}
		
		//下面这个方法好像用处不大，先写到这儿。20120901
		public function updateKnowledgeRelated():void {
			if(editedFigure != null) {
				editedFigure.setKnowledgeRelated(knowledgeRelated);
			} else {
				knowledgeRelated = new ArrayCollection();
			}
		}
		
		
	}
}