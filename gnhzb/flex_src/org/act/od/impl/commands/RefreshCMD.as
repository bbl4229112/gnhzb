package org.act.od.impl.commands
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.commands.SequenceCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * Read the new XML and refresh the figureEditorCanvas 
	 * 
	 * @author Mengsong
	 * 
	 */	
	
	public class RefreshCMD extends SequenceCommand
	{
		public function RefreshCMD()
		{
			super();
		}
		
		/**
		 * 
		 * @param event { FigureEditorModel, XString,YString,WidthString,HeightString,FigureID}
		 * 
		 */
		//		add for solute the problem created by asynchronous mechanism
		private var k:int = new int(0);
		private var j:int = new int(0);
		private var FileID:String = new String();
		private var XString:String = new String();
		private var YString:String = new String();
		private var WidthString:String = new String();
		private var HeightString:String= new String();
		private var FigureID:Array = new Array();
		private var DrawConnectionOnlyState:Boolean = new Boolean(false);
		
		override public function execute(event :OrDesignerEvent) :void{
			
			var rootFig :ProcessFigure = ProcessFigure(event.data.figureEditorModel.rootFigure);
			
			//to draw the figures
			var figArray :Array = new Array();
			rootFig.ifiguretoarray(figArray);
			
			var fig:IFigure;
			
			var connection :Array = new Array();
			
			var FEV:FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(event.data.fileID)) as FigureEditorVH;
			
			FEV.figureEditor.figureCanvas.updateFigure();
			event.data.figureEditorModel.resetSelectedFigure();			
			FEV.figureEditor.figureCanvas.removeAllChildren();
			
			rootFig.readInformationToFigure(event.data.figureEditorModel.canvasXML,event.data.figureEditorModel.rootModel,event.data.figureEditorModel.fatherModel);
			
			//		add for solute the problem created by asynchronous mechanism
			j = figArray.length;
			FileID = event.data.fileID;
			XString = event.data.XString;
			YString = event.data.YString;
			WidthString = event.data.WidthString;
			HeightString = event.data.HeightString;
			FigureID = event.data.FigureID;
			DrawConnectionOnlyState = event.data.DrawConnectionOnlyState;
			
			
			for(var i:int=0; i<figArray.length; i++){
				
				fig = figArray[i] as IFigure;
				
				if(fig is ConnectionFigure){
					connection.push(fig);
				}
				AbstractFigure(fig).addEventListener(FlexEvent.CREATION_COMPLETE,CreationComplete);
				FEV.figureEditor.figureCanvas.addChild(AbstractFigure(fig));
				
				
			}
			
			//				event.data.figureEditorModel._showingMultiple=1;
			
			////				maybe can be omit too
			for(i=0; i<connection.length; i++){
				ConnectionFigure(connection[i]).autoSetAnchor();
			}
		}
		
		private function CreationComplete(event:FlexEvent):void{
			k++;
			AbstractFigure(event.currentTarget).updateDraw();
			AbstractFigure(event.currentTarget).removeEventListener(FlexEvent.CREATION_COMPLETE,CreationComplete);
			//		add for solute the problem created by asynchronous mechanism
			if(k==j){
				this.nextEvent = new FigureCanvasAppEvent(FigureCanvasAppEvent.DRAW_SIGN_ON_FIGURES,
					{FileID:FileID,XString:XString,YString:YString,
						WidthString:WidthString,HeightString:HeightString,
						FigureID:FigureID,DrawConnectionOnlyState:DrawConnectionOnlyState});
				this.executeNextCommand();
			}
		}
	}
}