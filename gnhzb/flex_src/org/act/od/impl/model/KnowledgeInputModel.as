package org.act.od.impl.model
{
	import mx.collections.ArrayCollection;	
	import org.act.od.impl.figure.AbstractFigure;
	
	public class KnowledgeInputModel
	{		
		[Bindable]
		public var knowledgeInput:ArrayCollection;
		/**
		 * The selected figure.
		 */
		public var editedFigure :AbstractFigure;
		
		public function KnowledgeInputModel()
		{

		}
	}
}