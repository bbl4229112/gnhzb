package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FileNavigatorViewAppEvent;
	import org.act.od.impl.figure.SubProcessow2Figure;
	/**
	 * Create a new subProcess file in fileNavigator and a subProcess figure.
	 * 
	 * @author Mengsong
	 * 
	 */ 
	public class OpenSubProcessCMD extends AODCommand
	{
		public function OpenSubProcessCMD(){
			super();
		}
		/**
		 * @param event {subProcessFigure}
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var subProcessFigure : SubProcessow2Figure = event.data.subProcessFigure as SubProcessow2Figure;
			var spFileName : String = subProcessFigure.getSubProcessFileName();
			var tempSpFileName : String = spFileName;
			var spFileID : String = subProcessFigure.getSubProcessFileID();
			var spFilePath : String = subProcessFigure.getSubProcessFilePath();
			if(spFileID != null && spFileName != null) {
				new FileNavigatorViewAppEvent(FileNavigatorViewAppEvent.FIGUREFILE_OPEN,
					{fileID:spFileID,filePath:spFilePath,fileName:tempSpFileName}).dispatch();
			}
			else {
				new FigureCanvasAppEvent(FigureCanvasAppEvent.CREATE_NEW_SUBPROCESS,
					{subProcessFigure:subProcessFigure}).dispatch();
			}
		}
		
	}
}