package org.act.od.impl.figure.bpmn
{
	import flash.geom.Point;
	
	import org.act.od.impl.model.FigureEditorModel;
	
	
	public class MessageIntermediateEventow2Figure extends IntermediateEventow2Figure
	{
		
		public function MessageIntermediateEventow2Figure(processType:String=null)
		{
			super(processType);
			this.drawid=118;
			standardwidth=25*1.2*1.2;
			standardheight=25*1.2*1.2;
			r=standardwidth/2;
			width=standardwidth;
			height=standardheight;
			
			
			if(processType != null)
			{
				if(processType == FigureEditorModel.BPEL_PROCESS_TYPE)
					;//this.setpicture(FigureFactory.end);
				else
					if(processType == FigureEditorModel.BPMN_PROCESS_TYPE)
						this.setpicture(BpmnFigureFactory.messageIntermediate);
			}
			
		}
		
		override public function setposition(x:Number,y:Number):void{
			super.setposition(x,y);
			this.rx=x;
			this.ry=y;
			this.x=rx-r;
			this.y=ry-r;
		}
		
		override public function setsize(width:Number,height:Number):void{
			r=width/this.width*r;
			this.width=width;
			this.height=this.width;
			this.x=rx-r;
			this.y=ry-r;
		}
		
		override public function autosetsize(arrowX:Number,arrowY:Number,mode:Number):void{
			var tempN:Number;
			tempN=Math.sqrt((arrowX-this.rx)*(arrowX-this.rx)+(arrowY-this.ry)*(arrowY-this.ry));
			if(tempN<this.standardwidth/2){
				this.width=this.standardwidth;
				this.height=this.standardwidth;
				this.r=this.standardwidth/2;
				this.x=rx-r;
				this.y=ry-r;
			}
			else{
				this.r=tempN;
				this.x=rx-r;
				this.y=ry-r;
				this.width=2*r;
				this.height=2*r;
			}
			super.autosetsize(arrowX,arrowY,mode);
		}
		
		override public function isin(currentX:Number,currentY:Number):int{
			var ret:int=super.isin(currentX,currentY);
			if(ret>0){
				return ret;
			}
			if(Math.pow(currentX-rx,2)+Math.pow(currentY-ry,2)<=r*r){
				return 1;
			}
			else{
				if(this.selectedState){
					if(currentX>this.x&&currentX<this.x+this.width&&currentY>this.y&&currentY<this.y+this.height){
						return 1;
					}
				}
			}
			return 0;
		}
		
		override public function drawSelectedStyle():void{
			sprt.graphics.lineStyle(this.defaultSelectedLineThickness*this.multiple,0x2244ff,0.4);
			sprt.graphics.drawRoundRect(0,0,this.width,this.height,3,3);
			
			sprt.graphics.lineStyle(2,0x000000,1);
			sprt.graphics.drawCircle(0,this.height/2,this.selectedCircleRadius);
			sprt.graphics.drawCircle(this.width/2,0,this.selectedCircleRadius);
			sprt.graphics.drawCircle(this.width/2,this.height,this.selectedCircleRadius);
			sprt.graphics.drawCircle(this.width,this.height/2,this.selectedCircleRadius);
		}
		
		override public function changedirection(currentX:Number,currentY:Number):int{
			if(!this.selectedState){
				return 0;
			}
			if(getDistance(this.x+this.width/2,this.y,currentX,currentY)<=this.selectedCircleRadius){
				return 2;
			}
			if(getDistance(this.x+this.width,this.y+this.height/2,currentX,currentY)<=this.selectedCircleRadius){
				return 4;
			}
			if(getDistance(this.x+this.width/2,this.y+this.height,currentX,currentY)<=this.selectedCircleRadius){
				return 6;
			}
			if(getDistance(this.x,this.y+this.height/2,currentX,currentY)<=this.selectedCircleRadius){
				return 8;
			}
			return 0;
		}
		
		override public function getdrawx():Number{
			return getrx();
		}
		
		override public function getdrawy():Number{
			return getry();
		}
		
		override public function getEdgePoint(end:Point, startOperation:Boolean=true):Point{
			return this.circlepoint(this.rx,this.ry,end.x,end.y,this.r);
		}
		
		override public function setxy(x:Number,y:Number):void{
			this.setposition(x+this.r,y+this.r);
		}
		
		override public function outputAllInformation():XML{
			var info:XML=super.outputAllInformation();
			info.@r=this.r;
			return info;
		}
		
		override public function readInformationToFigure(info:XML,rootFigureEditorModel:FigureEditorModel,fatherFigureEditorModel:FigureEditorModel):void{
			super.readInformationToFigure(info,rootFigureEditorModel,fatherFigureEditorModel);
			this.r=Number(info.@r);
		}
		override protected function OutputScale(mtp:Number):void{
			super.OutputScale(mtp);
			this.r=this.r/this.premultiple*this.multiple;
		}
	}
}