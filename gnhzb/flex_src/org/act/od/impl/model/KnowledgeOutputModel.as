package org.act.od.impl.model
{
	import mx.collections.ArrayCollection;	
	import org.act.od.impl.figure.AbstractFigure;
	
	public class KnowledgeOutputModel
	{
		[Bindable]
		public var knowledgeOutput:ArrayCollection;
		/**
		 * The selected figure.
		 */
		public var editedFigure :AbstractFigure;
		
		public function KnowledgeOutputModel()
		{
		}
	}
}