package org.act.od.impl.figure
{
	import flash.geom.Point;
	
	
	public class Switchow2Figure extends ow2Figure
	{
		protected var radius:Number;
		public function Switchow2Figure()
		{
			super();
			drawid=102;
			standardwidth=110;
			standardheight=38;
			radius=8;
			width=standardwidth;
			height=standardheight;
			this.lblNodeName.width=110-8;
			this.lblNodeName.height=17;
			this.lblNodeName.x=8;
			this.lblNodeName.y=11;
			this.addChild(this.lblNodeName);
			this.setpicture(FigureFactory.Switch);
		}
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
			if(point.x<this.x+this.radius&&point.y<this.y+this.radius){
				point=this.circlepoint(this.x+this.radius,this.y+this.radius,point.x,point.y,this.radius);
			}
			else{
				if(point.x>this.x+this.width-this.radius&&point.y<this.y+this.radius){
					point=this.circlepoint(this.x+this.width-this.radius,this.y+this.radius,point.x,point.y,this.radius);
				}
				else{
					if(point.x>this.x+this.width-this.radius&&point.y>this.y+this.height-this.radius){
						point=this.circlepoint(this.x+this.width-this.radius,this.y+this.height-this.radius,point.x,point.y,this.radius);
					}
					else{
						if(point.x<this.x+this.radius&&point.y>this.y+this.height-this.radius){
							point=this.circlepoint(this.x+this.radius,this.y+this.height-this.radius,point.x,point.y,this.radius);
						}
					}
				}
			}
			return point;
		}
		override public function drawpicture():void{
			super.drawpicture();
			sprt.graphics.beginFill(0xffffff,1);
			sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x000000,1);
			sprt.graphics.drawRoundRect(0,0,this.width,this.height,2*radius,2*radius);
			sprt.graphics.endFill();
		}
		override protected function setImageSizeAndPosition():void{
			var short:Number=this.width>this.height?this.height:this.width;
			picture.width=short*0.42;
			picture.height=short*0.42;
			picture.x=short*0.29;
			picture.y=short*0.29;
		}
		override protected function setlblNodeNamePosition():void{
			this.lblNodeName.y=(this.height-this.lblNodeName.textHeight)/2-1;
			this.setImageSizeAndPosition();
			
			
			//			this.lblNodeName.x=picture.x+picture.width+0.1*this.width;
			
			this.lblNodeName.x=picture.x+picture.width+8;
			
		}
		override public function setFigureSizeAccordingTolblNodeName():void{
			//			var updatedrawstate:Boolean=false;
			//			if(this.height<this.lblNodeName.textHeight*1.5){
			//				this.height=this.lblNodeName.textHeight*1.5;
			//				updatedrawstate=true;
			//			}
			////			if(this.width<(picture.x+picture.width+this.lblNodeName.textWidth)*1.5){
			////				this.width=(picture.x+picture.width+this.lblNodeName.textWidth)*1.5;
			////				updatedrawstate=true;
			////			}
			//
			//			if(this.width<(picture.x+picture.width+this.lblNodeName.textWidth)+30){
			//				this.width=(picture.x+picture.width+this.lblNodeName.textWidth)+30;
			//				updatedrawstate=true;
			//			}
			
			this.width=(picture.x+picture.width+this.lblNodeName.textWidth)+30;
			
			
			
			
			
			//			this.standardwidth=(picture.x+picture.width+this.lblNodeName.textWidth)*1.5;
			
			this.standardwidth=(picture.x+picture.width+this.lblNodeName.textWidth)+30;
			
			if(this.lblNodeName.textHeight){
				//				modified by mengsong 2010-7-5 16:30:04
				//				cause drag up error 				
				//				this.height=this.lblNodeName.textHeight*2.7;
				this.standardheight=this.lblNodeName.textHeight*2.7;
			}
			else{
				this.width-=18;
				this.standardwidth-=18;
			}
			
			this.rx=this.x+this.width/2;
			this.ry=this.y+this.height/2;
			//			this.updateDraw();
		}
		override protected function doChangeSize():Boolean{
			if(this.lblNodeName.textHeight&&this.lblNodeName.textWidth){
				if(this.height<this.lblNodeName.textHeight*1.5){
					return true;
				}
				if(this.width<(picture.x+picture.width+this.lblNodeName.textWidth)+30){
					return true;
				}
			}
			return false;
		}
		override protected function autoSetStandardWidth():void{
			this.standardwidth=(picture.x+picture.width+this.lblNodeName.textWidth)+30;
		}
		override protected function autoSetStandardHeight():void{
			this.standardheight=this.lblNodeName.textHeight*2.7;
		}
		
	}
}