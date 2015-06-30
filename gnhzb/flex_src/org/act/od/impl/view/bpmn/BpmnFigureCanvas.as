package org.act.od.impl.view.bpmn
{
	
	
	import mx.containers.Canvas;
	import mx.containers.Panel;
	
	import org.act.od.impl.figure.*;
	import org.act.od.impl.model.*;
	import org.act.od.impl.other.Localizator;
	import org.act.od.impl.view.FigureCanvas;
	
	/**
	 * Canvas edit area
	 * 
	 * @author Quyue
	 *
	 */
	public class BpmnFigureCanvas extends Panel
	{
		
		/**
		 * Store all figure canvas (All Bpmn Pool)
		 */
		private var _figuresCanvas : Array = new Array();
		
		
		/**
		 * Create a new canvas by the figureEditorModel.
		 */
		public function BpmnFigureCanvas(figureEditorModel :FigureEditorModel = null)
		{
			super();
			
			//this.styleName="GraphicalViewerStyle";
			this.percentWidth = 100;
			this.percentHeight = 100;
			
			if(figureEditorModel)
			{
				var figureCanvas : FigureCanvas = new FigureCanvas( figureEditorModel );
				figureCanvas.percentWidth = 100;
				figureCanvas.percentHeight = 40;
				var localizator : Localizator = Localizator.getInstance();
				figureCanvas.label= localizator.getText('figureeditor.graphic');
				this.addChild( figureCanvas );
			}
			
			//this.doubleClickEnabled = true;
			
		}
		
		
		/**
		 * Add Bpmn Pools
		 * @param figureEditorModel
		 * @param nbrPool pools number
		 *
		 */
		public function addPoolFigureCanvas( figureEditorModel :FigureEditorModel, nbrPool :Number): void
		{
			var yy:int = 20;
			for(var i:int =0; i< nbrPool; i++)
			{
				var figureCanvas : FigureCanvas = new FigureCanvas( figureEditorModel );
				figureCanvas.percentWidth = 100;
				figureCanvas.percentHeight = 40;//(100 / nbrPool ) - ((20*(nbrPool-1)) / nbrPool);
				
				var localizator : Localizator = Localizator.getInstance();
				figureCanvas.label= localizator.getText('figureeditor.graphic');
				this.addChild( figureCanvas );
				
				this._figuresCanvas.push(figureCanvas);
				
				//Empty canvas between pools
				if( i < (nbrPool-1) )
				{
					var canvas : Canvas  = new Canvas();
					canvas.percentWidth  = 100;
					canvas.percentHeight = 20;
					this.addChild( canvas );
				}
			}
			
		}
		
		public function get figuresCanvas() : Array
		{
			if(this._figuresCanvas == null)
				this._figuresCanvas = new Array();
			return this._figuresCanvas;
		}
		
	}
}

