package org.act.od.impl.figure.custom
{
	import flash.events.ContextMenuEvent;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.controls.Alert;
	import mx.core.ContextualClassFactory;
	
	import org.act.od.impl.figure.NoncontainerFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.OrchestraDesigner;

	public class DiamondFigure extends CaltksAbstractFigure
	{
		
		/**
		 *初始化基类和本类各属性
		 *
		 */	
		public function DiamondFigure(processType:String=null)
		{
			super(processType);
			//drawid写入xml文件中，用以标志组件类型，在组建工厂中根据其生产对应对象
			drawid=124;
			standardwidth=110;
			standardheight=38;
			width=standardwidth;
			height=standardheight;
			this.lblNodeName.width=110-8;
			this.lblNodeName.height=17;
			this.lblNodeName.x=8;
			this.lblNodeName.y=11;
			this.addChild(this.lblNodeName);
			
		}
		
		override public function drawpicture():void{
			super.drawpicture();
			sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x000000,1);
			sprt.graphics.beginFill(0xffffff,1);
			draw(0,0,this.width,this.height);
			sprt.graphics.endFill();
		}
		
		private function draw(x:Number,y:Number, width:Number, height:Number):void{
			var centreX:Number=x+width/2;
			var centreY:Number=y+height/2;
			sprt.graphics.moveTo(centreX,centreY-height/2);
			sprt.graphics.lineTo(centreX+width/2,centreY);
			sprt.graphics.lineTo(centreX,centreY+height/2);
			sprt.graphics.lineTo(centreX-width/2,centreY);
			sprt.graphics.lineTo(centreX,centreY-height/2);
			
		}
	}
}