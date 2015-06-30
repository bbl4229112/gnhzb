package org.act.od.impl.figure
{
	import mx.controls.Button;
	import mx.controls.Image;
	
	
	public class ow2Figure extends NoncontainerFigure
	{
		protected var picture:Image;
		
		
		
		public function ow2Figure(processType:String=null)
		{
			super(processType);
			picture=new Image();
			this.addChild(picture);
			
		}
		
		public function setpicture(image:Object):void{
			this.picture.source=image;
			
		}
		
		override public function drawpicture():void{
			super.drawpicture();
			setImageSizeAndPosition();
			picture.maintainAspectRatio=false;
			this.addChild(picture);
			
			
		}
		protected function setImageSizeAndPosition():void{
			picture.width=this.width;
			picture.height=this.height;
			picture.x=0;
			picture.y=0;
			
			
			
		}
		
		override public function drawclear():void{
			super.drawclear();
			if(this.contains(this.picture)){
				this.removeChild(picture);
			}
		}
		
	}
}