package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.SubProcessow2Figure;
	import org.act.od.impl.model.FileIDManager;
	import org.act.od.impl.model.FileNavigatorViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.view.SaveFileWindow;
	import org.act.od.impl.view.SaveFileWindownew;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	
	/**
	 * Create a sub process.
	 * 
	 * @author Mengsong
	 * 
	 */ 
	public class CreateSubProcessCMD extends AODCommand
	{
		private var subProcessFigure : SubProcessow2Figure;
		
		public function CreateSubProcessCMD(){
			super();
		}
		/**
		 * 
		 * @param event {subProcessFigure}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			subProcessFigure = event.data.subProcessFigure as SubProcessow2Figure;
			var fileName : String = event.data.fileName;
			
			var newFile : SaveFileWindownew = SaveFileWindownew(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), SaveFileWindownew,true));
			newFile.setTitle("File");
			PopUpManager.centerPopUp(newFile);
			newFile.addEventListener(CloseEvent.CLOSE,newFileResult);
		}
		
		private function newFileResult(event : CloseEvent):void{
			var fileName : String = SaveFileWindownew(event.currentTarget).getText();
			if(fileName != "") {
				fileName = fileName + ".xml";
				subProcessFigure.setSubProcessFileName(fileName);
				createNewProcessFile(subProcessFigure);
			}
		}
		
		private function createNewProcessFile(subProcessFigure : SubProcessow2Figure) : void {
			var subProcessFileName : String = subProcessFigure.getSubProcessFileName();
			var filePath : String = subProcessFigure.filePath;
			var fileNavigatorViewVH :FileNavigatorViewVH = 
				ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY) as FileNavigatorViewVH;
			var fileNavigatorViewModel :FileNavigatorViewModel =
				OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
			
			var theParentItem:Object;
			theParentItem=fileNavigatorViewModel.xmlList.children();
			var startIndex:int=0;
			var endIndex:int=0;
			var path:Array=new Array;
			while(filePath.indexOf("\\",startIndex)>0){
				endIndex=filePath.indexOf("\\",startIndex);
				path.push(filePath.substring(startIndex,endIndex));
				startIndex = endIndex+1;
			}
			var i:int,j:int;
			var thisXmlList:XMLList = fileNavigatorViewModel.xmlList;
			for(i = 0; i < path.length-1; i++)
				for(j = 0; j<thisXmlList.length(); j++)
				{
					if(XML(thisXmlList[j]).@name == path[i]){
						thisXmlList = XML(thisXmlList[j]).elements();
						break;	
					}
				}
			for(j = 0; j < thisXmlList.length(); j++)
				if(XML(thisXmlList[j]).@name == path[i])
					break;
			var temp:XMLList=XML(thisXmlList[j]).elements();
			var exist:Boolean = false;
			for(i = 0; i < temp.length(); i++)
				if( XML(temp[i]).@name == subProcessFileName){
					exist = true;
					break;
				}
			if(exist)
				Alert.show("This file name is exist.");
			else{
				var newFileNode:XML=<file/>;
				newFileNode.@ID = FileIDManager.getAvailabelFileId();
				newFileNode.@name=subProcessFileName;
				newFileNode.@type=EditorNavigatorChild.FIGURE_EDITOR_TYPE;
				fileNavigatorViewVH.addAndExpandItem(thisXmlList[j], newFileNode, i + 1, true);
				subProcessFigure.setSubProcessFileID(newFileNode.@ID);
			}
		}
	}
}