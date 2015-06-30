package com.leixiao
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	
	import mx.containers.TitleWindow;
	import mx.controls.Button;
	import mx.controls.TextArea;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.utils.ObjectUtil;
		
	public class _MyClassDesign
	{

		public function Save(e:Event):void{
			var s:Array=_this.MainPanel.getChildren();
			var str:String="";
			for(var i:int=0;i<s.length;i++){
				if(s[i] instanceof ObjectHandles){
					var CurSaveOh:ObjectHandles=s[i] as ObjectHandles;
					var CurMy:MyCanvas=CurSaveOh.getChildAt(0) as MyCanvas;
					var text:String=CurMy.NodeTitle.text;
					var x:String=CurSaveOh.x.toString();
					var y:String=CurSaveOh.y.toString();
					var w:String=CurSaveOh.width.toString();
					var h:String=CurSaveOh.height.toString();
					var outline:Array= CurMy.getOutput();			
					var NodeStr:String="<node label='"+text+"' x='"+x+"' y='"+y+"' width='"+w+"' height='"+h+"' type='"+ObjectUtil.getClassInfo(CurMy).name+"' >\n";
					for(var j:int=0;j<outline.length;j++){
							var ohline:Line=outline[j] as Line;
							var Nm:MyCanvas=ohline.EndNode.getChildAt(0) as MyCanvas
							NodeStr+="<line id='"+ohline.LineId+"' for='"+Nm.NodeTitle.text+"'></line>\n";
					}
					NodeStr+="</node>\n";
					str+=NodeStr;
				}
			}
			var red:TitleWindow = new TitleWindow();
			red.width=500;
			red.height=350;
			red.showCloseButton=true;
			var textaren:TextArea = new TextArea();
			red.addEventListener(CloseEvent.CLOSE,function(e:Event):void{
				red.parent.removeChild(red);
			});
			red.addEventListener(MouseEvent.MOUSE_DOWN,function(e:MouseEvent):void{
				red.startDrag(false);
			});
			red.addEventListener(MouseEvent.MOUSE_UP,function(e:MouseEvent):void{
				red.stopDrag();
			});
			textaren.text="<LxWorkFlow>\n"+str+"</LxWorkFlow>";
			textaren.percentHeight=100;
			textaren.percentWidth=100;
			red.addChild(textaren);
			_this.addChild(red);
			//Alert.show(str);
		}
		

	}
}