package org.act.od.impl.commands
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.core.UIComponent;
	import mx.graphics.ImageSnapshot;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.view.LevelModel;
	import org.act.od.impl.view.Microimage;
	import org.act.od.impl.view.OrchestraDesigner;
	
	/**
	 * @ author Zhaoxq
	 */ 
	public class CanvasChildrenChangedCMD extends AODCommand
	{
		
		public var num:int = 0;
		public function CanvasChildrenChangedCMD(){
			super();
		}
		/**
		 * @param event{canvas}
		 */ 
		override public function execute(event:OrDesignerEvent):void{
			
			var canvas :FigureCanvas = event.data.canvas as FigureCanvas;
			if(canvas != null){
				var figureEditorModel:FigureEditorModel = canvas._editorModel;
				levelRefresh(figureEditorModel);
			}
		}
		public function levelRefresh(figureeditormodel:FigureEditorModel):void{
			var arrcoll:ArrayCollection = new ArrayCollection();
			var can:FigureCanvas = figureeditormodel.figureCanvas;
			var arrcoll:ArrayCollection = figureeditormodel.getAllUpModelFromSelf(arrcoll);
			clearLevelCanvas();
			var isLast:Boolean = false;
			for(var a:int = 0;a<arrcoll.length;a++){
				if(a == arrcoll.length - 1)
					isLast = true;
				var figurecanvas:FigureCanvas = arrcoll[a] as FigureCanvas;
				figurecanvas.createOneLevel(a,isLast);
			}
		}
		public function clearLevelCanvas():void{
			var orchestraDesigner:OrchestraDesigner = OrDesignerModelLocator.getInstance().getOrchestraDesigner();
			var level:LevelModel = orchestraDesigner.levelModel;
			var canvas:Canvas = level.canvas;
			if(canvas != null){
				canvas.graphics.clear();
				canvas.removeAllChildren();			
			}
		}
		
	}
}