package org.act.od.impl.figure.custom
{
	import flash.events.ContextMenuEvent;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.controls.Alert;
	import mx.controls.Image;
	import mx.core.ContextualClassFactory;
	
	import org.act.od.impl.figure.NoncontainerFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.OrchestraDesigner;
	
	public class CaltksAbstractFigure extends NoncontainerFigure
	{
		public var picture:Image;
		
		public function CaltksAbstractFigure(processType:String=null)
		{
			super(processType);
			picture=new Image();
			this.addChild(picture);
		}
		public function setpicture(image:Object):void{
			
			this.picture.source=image;
		}
		public function setImageSizeAndPosition():void{
			picture.width=this.width;
			picture.height=this.height;
			picture.x=0;
			picture.y=0;
		}
		/**
		 *通过左上角和右下角两点设定组件大小
		 *
		 */	
		override public function setposition(x:Number,y:Number):void{
			super.setposition(x,y);
			this.x=x;
			this.y=y;
			this.rx=x+this.width/2;
			this.ry=y+this.height/2;
		}
		
		override public function setsize(width:Number,height:Number):void{
			this.width=width;
			this.height=this.width;
			this.rx=x+this.width/2;
			this.ry=y+this.height/2;
		}
		
		
		/**
		 *调整组件的大小 
		 * @param arrowX 为点的x坐标
		 * @param arrowY 为点的y坐标
		 * @param mode 1——8分别为矩形上的八个调整点编号，顺时针方向
		 */	
		override public function autosetsize(arrowX:Number,arrowY:Number,mode:Number):void{
			var tempW:Number;
			var tempH:Number;
			switch(mode)
			{
				case 2:
					tempH=this.y+this.height-arrowY;
					if(tempH<this.standardheight){
						tempH=this.standardheight;
					}
					this.y=this.y+this.height-tempH;
					this.height=tempH;
					this.ry=this.y+this.height/2;
					break;
				case 4:
					tempW=arrowX-this.x;
					if(tempW<this.standardwidth){
						tempW=this.standardwidth;
					}
					this.width=tempW;
					this.rx=this.x+this.width/2;
					break;
				case 6:
					tempH=arrowY-this.y;
					if(tempH<this.standardheight){
						tempH=this.standardheight;
					}
					this.height=tempH;
					this.ry=this.y+this.height/2;
					break;
				case 8:
					tempW=this.x+this.width-arrowX;
					if(tempW<this.standardwidth){
						tempW=this.standardwidth;
					}
					this.x=this.x+this.width-tempW;
					this.width=tempW;
					this.rx=this.x+this.width/2;
					break;
				case 1:
					autosetsize(arrowX,arrowY,2);
					autosetsize(arrowX,arrowY,8);
					break;
				case 3:
					autosetsize(arrowX,arrowY,2);
					autosetsize(arrowX,arrowY,4);
					break;
				case 5:
					autosetsize(arrowX,arrowY,4);
					autosetsize(arrowX,arrowY,6);
					break;
				case 7:
					autosetsize(arrowX,arrowY,6);
					autosetsize(arrowX,arrowY,8);
					break;
				case 0:
					autosetsize(arrowX,arrowY,5);
					break;
			}
			super.autosetsize(arrowX,arrowY,mode);
		}
		
		/**
		 *判断某个点是否在组件内
		 *
		 */	
		override public function isin(currentX:Number,currentY:Number):int{
			var ret:int=super.isin(currentX,currentY);
			if(ret>0){
				return ret;
			}
			if(currentX>this.x&&currentX<this.x+this.width&&currentY>this.y&&currentY<this.y+this.height){
				return 1;
			}
			return 0;
		}
		
		/**
		 *继承自GraphicalFigure，决定连接线在该组件边上的位置
		 * end为于该组件连接的另一组件中点，首先更加end位置判断交点在矩形上哪一边，而后再计算于改变交点，本类还是计算矩形的交点
		 * @param end : Destination point
		 * @return selected point in this  object (this compenent)
		 *
		 */
		
		override public function getEdgePoint(end:Point, startOperation:Boolean=true):Point{
			var zsToyx:Boolean;
			var ysTozx:Boolean;
			var point:Point;
			if(pointLine(this.x,this.y,this.rx,this.ry,end.x,end.y)){
				zsToyx=true;
			}
			else{
				zsToyx=false;
			}
			if(pointLine(this.x+width,this.y,this.rx,this.ry,end.x,end.y)){
				ysTozx=true;
			}
			else{
				ysTozx=false;
			}
			point=new Point();
			if(zsToyx&&ysTozx){
				point.y=this.y;
				if(end.y==this.ry){
					point.x=this.rx;
				}
				else{
					point.x=this.height/2/(this.ry-end.y)*(end.x-this.rx)+this.rx;
				}
			}
			else{
				if(zsToyx&&(!ysTozx)){
					point.x=this.x+this.width;
					if(end.x==this.rx){
						point.y=this.ry;
					}
					else{
						point.y=this.width/2/(end.x-this.rx)*(end.y-this.ry)+this.ry;
					}
				}
				else{
					if((!zsToyx)&&ysTozx){
						point.x=this.x;
						if(end.x==this.rx){
							point.y=this.ry;
						}
						else{
							point.y=this.width/2/(this.rx-end.x)*(end.y-this.ry)+this.ry;
						}
					}
					else{
						point.y=this.y+this.height;
						if(end.y==this.ry){
							point.x=this.rx;
						}
						else{
							point.x=this.height/2/(end.y-this.ry)*(end.x-this.rx)+this.rx;
						}
					}
				}
			}
			var rate:Number;
			if(point.x<this.x&&point.y<this.y){
				point=this.circlepoint(this.x,this.y,point.x,point.y,0);
			}
			else{
				if(point.x>this.x+this.width&&point.y<this.y){
					point=this.circlepoint(this.x+this.width,this.y,point.x,point.y,0);
				}
				else{
					if(point.x>this.x+this.width&&point.y>this.y+this.height){
						point=this.circlepoint(this.x+this.width,this.y+this.height,point.x,point.y,0);
					}
					else{
						if(point.x<this.x&&point.y>this.y+this.height){
							point=this.circlepoint(this.x,this.y+this.height,point.x,point.y,0);
						}
					}
				}
			}
			return point;
		}
		
		/**
		 *输出该组件的xml
		 * 
		 */	
		override public function outputAllInformation():XML{
			var info:XML=super.outputAllInformation();
			//info.@radius=this.radius;
			return info;
		}
		
		/**
		 *读入该组件的xml
		 * 
		 */	
		override public function readInformationToFigure(info:XML,rootFigureEditorModel:FigureEditorModel,fatherFigureEditorModel:FigureEditorModel):void{
			super.readInformationToFigure(info,rootFigureEditorModel,fatherFigureEditorModel);
			//this.radius=Number(info.@radius);
		}
		/**
		 *画该图形，分别从改对象中读入长、宽、组件坐标点画菱形
		 *
		 */	
		/**
		 *设置该组件中的文本开始位置
		 *
		 */		
		override protected function setlblNodeNamePosition():void{
			this.lblNodeName.y=(this.height-this.lblNodeName.textHeight)/2-1;
			this.lblNodeName.x=(this.width-this.lblNodeName.textWidth)/2-1;
			
		}
		
		/**
		 *根据文本里文字多少调整组件大小
		 *
		 */	
		override public function setFigureSizeAccordingTolblNodeName():void{
			
			this.width=(this.lblNodeName.textWidth)+35*this.multiple;
			
			this.standardwidth=(this.lblNodeName.textWidth)+35*this.multiple;
			
			if(this.lblNodeName.textHeight){
				//				modified by mengsong 2010-7-5 16:26:18
				//				cause drag up error 
				//				this.height=this.lblNodeName.textHeight*2.7;
				this.standardheight=this.lblNodeName.textHeight*2.7;
			}
			else{
				this.width=this.width-18+30-this.multiple*35;
				this.standardwidth=this.standardwidth-18+30-this.multiple*35;
			}
			
			this.rx=this.x+this.width/2;
			this.ry=this.y+this.height/2;
			
		}
		/**
		 *判断是否需要调整组件大小
		 * 如果返回ture，执行setFigureSizeAccordingTolblNodeName等函数
		 *
		 */	
		override protected function doChangeSize():Boolean{
			if(this.lblNodeName.textHeight&&this.lblNodeName.textWidth){
				if(this.height<this.lblNodeName.textHeight*1.5){
					return true;
				}
				if(this.width<(this.lblNodeName.textWidth)+35*this.multiple){
					return true;
				}
			}
			return false;
		}
		
		/**
		 *调整默认的组件宽度
		 *
		 */	
		override protected function autoSetStandardWidth():void{
			this.standardwidth=(this.lblNodeName.textWidth)+35*this.multiple;
		}
		
		/**
		 *调整默认的组件宽度
		 *
		 */	
		override protected function autoSetStandardHeight():void{
			this.standardheight=this.lblNodeName.textHeight*2.7;
		}
	}
}