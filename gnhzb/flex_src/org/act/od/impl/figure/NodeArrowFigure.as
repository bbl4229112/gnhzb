package org.act.od.impl.figure
{
	import mx.core.UIComponent;
	
	public class NodeArrowFigure extends UIComponent
	{		
		public function NodeArrowFigure(direction:String)
		{
			if(direction == "input" || direction == "output"){
				this.graphics.clear();   
				this.graphics.lineStyle(3,0xFF0000,1);   
				this.graphics.moveTo(5,5);   
				this.graphics.lineTo(55,5);
				
				this.graphics.beginFill(0xFF0000,1);   				
				this.graphics.lineStyle(1,0xFF0000,1);   				
				this.graphics.moveTo(55,5);   
				this.graphics.lineTo(48,8);   								
				this.graphics.lineTo(48,2);   
				this.graphics.lineTo(55,5);   				
				this.graphics.endFill();   			  
			} else if(direction == "control"){
				this.graphics.clear();
				this.graphics.lineStyle(3,0xFF0000,1);
				this.graphics.moveTo(5,5);
				this.graphics.lineTo(5,55);
				
				this.graphics.beginFill(0xFF0000,1);
				this.graphics.lineStyle(1,0xFF0000,1);
				this.graphics.moveTo(5,55);
				this.graphics.lineTo(2,48);
				this.graphics.lineTo(8,48);
				this.graphics.lineTo(5,55);
				this.graphics.endFill();
			} else if(direction == "mechanism"){
				this.graphics.clear();
				this.graphics.lineStyle(3,0xFF0000,1);
				this.graphics.moveTo(5,55);
				this.graphics.lineTo(5,5);
				
				this.graphics.beginFill(0xFF0000,1);
				this.graphics.lineStyle(1,0xFF0000,1);
				this.graphics.moveTo(5,5);
				this.graphics.lineTo(2,12);
				this.graphics.lineTo(8,12);
				this.graphics.lineTo(5,5);
				this.graphics.endFill();
			}
		}
	}
}