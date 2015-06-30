package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FileIDManager;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	
	public class UploadFileCMD extends AODCommand
	{
		public function UploadFileCMD()
		{
			super();
		}
		
		override public function execute(event:OrDesignerEvent):void {
			var fileNavigatorViewVH :FileNavigatorViewVH =
				ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY) as FileNavigatorViewVH;
			if(fileNavigatorViewVH.getSelectedItem() != null
				&& fileNavigatorViewVH.getSelectedItem().@type != EditorNavigatorChild.FIGURE_EDITOR_TYPE
				&& fileNavigatorViewVH.getSelectedItem().@type != EditorNavigatorChild.BPEL_EDITOR_TYPE){
				
				var exist:Boolean = false;
				var xmlList :XMLList = XMLList(fileNavigatorViewVH.getSelectedItem()).elements();
				for(var i :int = 0; i < xmlList.length(); i++){
					if(event.data.fileName ==xmlList[i].@name)
						exist = true;
				}
				if(exist)
					Alert.show("This file name is exist.");
				else{
					var newFileNode:XML=<file/>;
					newFileNode.@ID = FileIDManager.getAvailabelFileId();
					newFileNode.@name=event.data.fileName;
					newFileNode.@type=EditorNavigatorChild.FIGURE_EDITOR_TYPE;
					
					fileNavigatorViewVH.addAndExpandItem(fileNavigatorViewVH.getSelectedItem(), newFileNode, 0, true);
					
					var remote :RemoteObject = new RemoteObject();
					remote.destination = "navigator";
					//remote.createNewFile(event.data.fileName, fileNavigatorViewVH.getSelectedItemPath());
					
					//save file
					var path : String = fileNavigatorViewVH.getSelectedItemPath()+"\\"+event.data.fileName;
					
					var xml :XML = new XML("<Process></Process>");
					xml.@processType = event.data.fileType;
					xml.@maxId = 1;
					
					//qu
					xml.@category=event.data.fileCategory;
					
//					var xml2 : XML = xml;
					var xml2:XML = new XML(event.data.fileContent);
					remote.saveFile(path, xml2.toXMLString());
					//remote.addEventListener(ResultEvent.RESULT,getXmlResult0);
					remote.addEventListener(FaultEvent.FAULT,fault);
					
				}
				
			}else{
				Alert.show("Please select a folder first!");
			}
		}
				
		private function fault(event :FaultEvent):void {
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}