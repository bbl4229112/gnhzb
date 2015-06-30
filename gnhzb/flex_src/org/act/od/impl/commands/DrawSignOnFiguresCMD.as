package org.act.od.impl.commands
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.messaging.Consumer;
	import mx.messaging.events.MessageEvent;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.commands.SequenceCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.figure.RedLinkFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * Sign the circle on the related figures
	 * 
	 * @author Mengsong
	 * 
	 */	
	
	public class DrawSignOnFiguresCMD extends SequenceCommand
	{
		public function DrawSignOnFiguresCMD()
		{
			super();
		}
		
		/**
		 * 
		 * @param event { XString,YString,WidthString,HeightString, FileID}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeState==true)
			{
				var tempArray:Array = new Array();
				var i:int;
				var tempXArray:Array = new Array();
				var tempYArray:Array = new Array();
				var tempWidthArray:Array = new Array();
				var tempHeightArray:Array = new Array();
				var figureID:Array ;
				var figureEditorVH:FigureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(
					event.data.FileID)) as FigureEditorVH;
				
				tempXArray = event.data.XString.split(",");
				tempYArray = event.data.YString.split(",");
				tempWidthArray = event.data.WidthString.split(",");
				tempHeightArray =event.data.HeightString.split(",");
				figureID = event.data.FigureID;
				
				var figure:AbstractFigure;
				figureEditorVH.figureEditor._figureCanvas.signlayer.removeAllChildren();
				//					for red LinkFigure
				if(figureID != null)
					for(var j:int = 0; j < figureID.length; j++){
						var connectionFigure:RedLinkFigure = new RedLinkFigure();
						connectionFigure.readInformationToFigure(figureID[j] as XML,null,null);
						figureEditorVH.figureEditor._figureCanvas.signlayer.addChild(connectionFigure);
						connectionFigure.updateDraw();
					}
				
				figureEditorVH.figureEditor._figureCanvas.signlayer.graphics.clear();
				figureEditorVH.figureEditor._figureCanvas.signlayer.graphics.lineStyle(2, 0xff0000, 1);
				//				set the focus
				if(figureEditorVH.figureEditor._figureCanvas.horizontalScrollBar){
					figureEditorVH.figureEditor._figureCanvas.horizontalScrollPosition=
						int(tempXArray[0] - figureEditorVH.figureEditor._figureCanvas.width/2);
				}
				if(figureEditorVH.figureEditor._figureCanvas.verticalScrollBar){
					figureEditorVH.figureEditor._figureCanvas.verticalScrollPosition=
						int(tempYArray[0] - figureEditorVH.figureEditor._figureCanvas.height/2);
				}
				
				
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().DrawConnectionOnlyState = event.data.DrawConnectionOnlyState;
				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().DrawConnectionOnlyState == false)
					for(i=0;i<tempXArray.length;i++){
						if(int(tempWidthArray[i])!=0)
							figureEditorVH.figureEditor._figureCanvas.signlayer.graphics
								.drawRect(int(tempXArray[i])-14,int(tempYArray[i])-14,
									int(tempWidthArray[i])+28,int(tempHeightArray[i])+28);
					}
				figureEditorVH.figureEditor._figureCanvas.addSignLayer();
			}
		}
	}
}