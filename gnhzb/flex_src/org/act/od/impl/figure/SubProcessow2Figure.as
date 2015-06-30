package org.act.od.impl.figure
{
	import org.act.od.impl.figure.bpmn.Poolow2Figure;
	import org.act.od.impl.model.FigureEditorModel; //readInformationToFigure()方法修改了，20120906
	
	public class SubProcessow2Figure extends Activityow2Figure
	{
		protected var subProcessFileName : String = null;
		
		protected var subProcessFileID : String = null;
		
		protected var subProcessFilePath : String = null;
		
		protected var _filePath :String = null;
		
		public function SubProcessow2Figure(processType:String = null, figureType:String= null)
		{
			super(processType,figureType);
			
			drawid=114;
			this.setpicture(FigureFactory.subProcess);
		}
		
		public function setSubProcessFileName(subProcessFileName : String) : void {
			this.subProcessFileName = subProcessFileName;
		}
		
		public function getSubProcessFileName() : String {
			return this.subProcessFileName;
		}
		
		public function setSubProcessFileID(subProcessFileID : String) : void {
			this.subProcessFileID = subProcessFileID;
		}
		
		public function getSubProcessFileID() : String {
			return this.subProcessFileID;
		}
		
		public function setSubProcessFilePath(subProcessFilePath : String) : void {
			this.subProcessFilePath = subProcessFilePath;
		}
		
		public function getSubProcessFilePath() : String {
			return this.subProcessFilePath;
		}
		
		public function set filePath(filePath :String):void{
			this._filePath = filePath;
		}
		public function get filePath():String{
			return this._filePath;
		}
		override public function outputAllInformation():XML{
			var info:XML=super.outputAllInformation();
			info.@subProcessFileName=this.subProcessFileName;
			info.@subProcessFileID=this.subProcessFileID;
			info.@subProcessFilePath=this.subProcessFilePath;
			return info;
		}
		
		
		override public function readInformationToFigure(info:XML,rootFigureEditorModel:FigureEditorModel,fatherFigureEditorModel:FigureEditorModel):void{
			super.readInformationToFigure(info,rootFigureEditorModel,fatherFigureEditorModel);
			this.subProcessFileName = info.@subProcessFileName;
			this.subProcessFileID = info.@subProcessFileID;
			this.subProcessFilePath = info.@subProcessFilePath;
		}
	}
}