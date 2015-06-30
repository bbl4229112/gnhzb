package org.act.od.impl.view
{
	
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.containers.*;
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.core.UIComponent;
	import mx.graphics.ImageSnapshot;
	
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.custom.StartFigure;
	import org.act.od.impl.figure.FigureFactory;
	
	public class ComponentsCanvas extends Canvas
	{
		public var toolPanel:ToolPanel;
		public function ComponentsCanvas(toolPanelValue:ToolPanel)
		{
			super();
			this.toolPanel = toolPanelValue;
			this.percentWidth = 100;
			this.percentHeight = 100;
			this.initComponents();
			
		}
		public function mouseClickHandle(e:MouseEvent):void{
			var can:Canvas = e.currentTarget as Canvas;
			var figureName:String = can.id;
			toolPanel.componentsCanvas_click(figureName);
		}
		public function rollOverHandle(e:MouseEvent):void{
			var can:Canvas = e.currentTarget as Canvas;
			var label:Label = can.getChildAt(1) as Label;
			can.setStyle("backgroundColor","0x83c8f4");
			can.setStyle("backgroundAlpha",0.5);
		}
		public function rollOutHandle(e:MouseEvent):void{
			var can:Canvas = e.currentTarget as Canvas;
			can.setStyle("backgroundColor","0xffffff");
		}
		public function initComponents():void{
			var arr:Array = new Array(158,115,124,160,161,162,159);
			var arr2:ArrayCollection = new ArrayCollection();
			arr2.addItem(FigureFactory.start);//开始
			arr2.addItem(FigureFactory.activity);//业务
			arr2.addItem(FigureFactory.judge);//判断
			arr2.addItem(FigureFactory.document);//文档
			arr2.addItem(FigureFactory.data);//数据
			arr2.addItem(FigureFactory.ready);//准备
			arr2.addItem(FigureFactory.end);//结束
			var arr3:Array = new Array("开始","业务","判断","文档","数据","准备","结束");
			var hbox:Canvas;
			var image:Image;
			var label:Label;
			for(var i:int = 0;i<arr.length;i++){
				hbox = new Canvas();
				hbox.height = 36;
				hbox.id = arr3[i] as String;
				image = new Image();
				label = new Label();
				image.source = arr2.getItemAt(i);
				label.text = arr3[i] as String;
				hbox.addChild(image);
				hbox.addChild(label);
				hbox.setChildIndex(label,1);
				label.id = "label";
				label.x = 100;
				label.y = 10;
				label.setStyle("fontSize",12);
				image.x = 0;
				this.addChild(hbox);
				hbox.percentWidth = 100;
				hbox.x = 0;
				hbox.y = 10+50*i;
				hbox.addEventListener(MouseEvent.CLICK, mouseClickHandle);
				hbox.addEventListener(MouseEvent.ROLL_OVER, rollOverHandle);
				hbox.addEventListener(MouseEvent.ROLL_OUT, rollOutHandle);
			}
		}
		
	}
}