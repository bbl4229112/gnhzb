package org.act.od.impl.figure.bpmn
{
	import flash.geom.Point;
	
	import mx.binding.utils.BindingUtils;
	
	import org.act.od.impl.figure.Activityow2Figure;
	import org.act.od.impl.model.FigureEditorModel;
	
	public class PortTypeFigure extends Activityow2Figure
	{
		protected var r:Number;
		
		//Number of this portType in parent Pool
		protected var num:int;
		
		protected var rxInPool : Number ;
		protected var ryInPool : Number;
		
		public function PortTypeFigure(processType:String = FigureEditorModel.BPEL_PROCESS_TYPE)
		{
			super(processType);
			drawid=120;
			standardwidth=100;
			standardheight=20;
			r=standardwidth/2;
			width=standardwidth;
			height=standardheight;
			
			//if(processType==FigureEditorModel.BPMN_PROCESS_TYPE)
			//	this.setpicture(BpmnFigureFactory.portType);
			
			BindingUtils.bindSetter(updatePositionInPool, this, "x");
			BindingUtils.bindSetter(updatePositionInPool, this, "y");
			
		}
		
		/*override public function setposition(x:Number,y:Number):void{
		super.setposition(x,y);
		this.rx=x;
		this.ry=y;
		this.x=rx-r;
		this.y=ry-r;
		//this.setpositionInPool(this.x,this.y);
		}*/
		
		public function setpositionInPool(x:Number,y:Number):void{
			this.rxInPool=x;
			this.ryInPool=y;
		}
		
		public function updatePositionInPool(num : Number):void
		{
			var parent : Poolow2Figure  = this.parent as Poolow2Figure;
			if(parent)
			{
				this.rxInPool = this.x + 15;
				this.ryInPool = this.y + parent.y + 15;
			}
			else
			{
				this.rxInPool = this.x;
				this.ryInPool = this.y;
			}
			
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
		
		
		/*	override public function drawpicture():void{
		super.drawpicture();
		sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x000000,1);
		sprt.graphics.beginFill(0xe3e8f3,1);
		
		sprt.graphics.drawRoundRect(0,0,this.width,this.height,2,2);
		sprt.graphics.endFill();
		
		
		}*/
		
		/*override public function drawSelectedStyle():void{
		sprt.graphics.lineStyle(this.defaultSelectedLineThickness*this.multiple,0x2244ff,0.4);
		//sprt.graphics.drawCircle(this.width,this.height,this.width/5*2);
		//sprt.graphics.lineStyle(2,0x2244ff,1);
		
		sprt.graphics.beginFill(0xe3e8f3,1);
		sprt.graphics.drawRoundRect(0,0,this.width,this.height,2,2);
		
		
		}*/
		
		/*override public function changedirection(currentX:Number,currentY:Number):int{
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
		}*/
		
		override public function getdrawx():Number{
			return getrx();
		}
		
		override public function getdrawy():Number{
			return getry();
		}
		
		//		override public function getEdgePoint(end:Point):Point{
		//			return this.circlepoint(this.rx,this.ry,end.x,end.y,this.r);
		//		}
		
		public function getEdgePointInPool__(end:Point):Point{
			return this.circlepoint(this.rxInPool,this.ryInPool,end.x,end.y,this.r);
		}
		
		public function getEdgePoint_(end:Point):Point{
			var zsToyx:Boolean;
			var ysTozx:Boolean;
			var point:Point;
			var parent : Poolow2Figure  = this.parent as Poolow2Figure;
			
			if(pointLine(this.x,this.y+parent.y,this.rx,this.ryInPool,end.x,end.y)){
				zsToyx=true;
			}
			else{
				zsToyx=false;
			}
			if(pointLine(this.x+width,this.y+parent.y,this.rx,this.ryInPool,end.x,end.y)){
				ysTozx=true;
			}
			else{
				ysTozx=false;
			}
			point=new Point();
			if(zsToyx&&ysTozx){
				point.y=this.y+parent.y;
				if(end.y==this.ryInPool){
					point.x=this.rx;
				}
				else{
					point.x=this.height/2/(this.ryInPool-end.y)*(end.x-this.rx)+this.rx;
				}
			}
			else{
				if(zsToyx&&(!ysTozx)){
					point.x=this.x+this.width;
					if(end.x==this.rx){
						point.y=this.ryInPool;
					}
					else{
						point.y=this.width/2/(end.x-this.rx)*(end.y-this.ryInPool)+this.ryInPool;
					}
				}
				else{
					if((!zsToyx)&&ysTozx){
						point.x=this.x;
						if(end.x==this.rx){
							point.y=this.ryInPool;
						}
						else{
							point.y=this.width/2/(this.rx-end.x)*(end.y-this.ryInPool)+this.ryInPool;
						}
					}
					else{
						point.y=this.y+parent.y+this.height;
						if(end.y==this.ryInPool){
							point.x=this.rx;
						}
						else{
							point.x=this.height/2/(end.y-this.ryInPool)*(end.x-this.rx)+this.rx;
						}
					}
				}
			}
			var rate:Number;
			if(point.x<this.x+this.radius&&point.y<this.y+parent.y+this.radius){
				point=this.circlepoint(this.x+this.radius,this.y+parent.y+this.radius,point.x,point.y,this.radius);
			}
			else{
				if(point.x>this.x+this.width-this.radius&&point.y<this.y+parent.y+this.radius){
					point=this.circlepoint(this.x+this.width-this.radius,this.y+parent.y+this.radius,point.x,point.y,this.radius);
				}
				else{
					if(point.x>this.x+this.width-this.radius&&point.y>this.y+parent.y+this.height-this.radius){
						point=this.circlepoint(this.x+this.width-this.radius,this.y+parent.y+this.height-this.radius,point.x,point.y,this.radius);
					}
					else{
						if(point.x<this.x+this.radius&&point.y>this.y+parent.y+this.height-this.radius){
							point=this.circlepoint(this.x+this.radius,this.y+parent.y+this.height-this.radius,point.x,point.y,this.radius);
						}
					}
				}
			}
			
			//point.y += parent.y;
			
			return point;
		}
		
		public function getEdgePoint____(end:Point):Point
		{
			var parent : Poolow2Figure  = this.parent as Poolow2Figure;
			//end.y += parent.y;
			
			var op : PortTypeFigure = this;
			op.y += parent.y
			
			var endd : Point = super.getEdgePoint(end);
			
			return endd;
		}
		
		override public function getEdgePoint(end:Point,start:Boolean=true):Point
		{
			var endd : Point = new Point();
			var parent : Poolow2Figure  = this.parent as Poolow2Figure;
			if(this.y+parent.y> end.y)
			{
				if(start==true)
					endd.x = this.x + this.width/3;
				else
					endd.x = this.x + 2*this.width/3;
				endd.y = this.y + parent.y ;
				return endd;
			}
			else
			{
				if(start==true)
					endd.x = this.x + this.width/3;
				else
					endd.x = this.x + 2*this.width/3;
				endd.y = this.y +  parent.y +this.height;
				return endd;
			}
			
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
		
		public function getNum():int
		{
			return this.num;
		}
		
		public function setNum(num : int):void
		{
			this.num = num;
		}
		
		public function getrxInPool():Number
		{
			return this.rxInPool;
		}
		
		public function getryInPool():Number
		{
			return this.ryInPool;
		}
		
		override public function getrx():Number
		{
			return this.rxInPool;
		}
		
		override public function getry():Number
		{
			//Alert.show(" voivoivoivoiv : "+this.ryInPool);
			return this.ryInPool;
		}
	}
}