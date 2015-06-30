package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FileNavigatorViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	/**
	 * Create a new folder in fileNavigator.
	 * 
	 *  @author Mengsong
	 * 
	 */ 
	public class NewFolderCMD extends AODCommand
	{
		public function NewFolderCMD(){
			super();
		}
		/**
		 * @param event {folderName}
		 */ 
		override public function execute(event :OrDesignerEvent) :void{
			var fileNavigatorViewVH :FileNavigatorViewVH = 
				ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY) as FileNavigatorViewVH;
			var fileNavigatorViewModel :FileNavigatorViewModel =
				OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
			
			if(fileNavigatorViewVH.getSelectedItem() != null 
				&& fileNavigatorViewVH.getSelectedItem().@type != EditorNavigatorChild.FIGURE_EDITOR_TYPE
				&& fileNavigatorViewVH.getSelectedItem().@type != EditorNavigatorChild.BPEL_EDITOR_TYPE){
				
				var exist:Boolean = false;
				var xmlList :XMLList = XMLList(fileNavigatorViewVH.getSelectedItem()).elements();
				for(var i :int = 0; i < xmlList.length(); i++){
					if(event.data.folderName == xmlList[i].@name)
						exist = true;
				}
				if(exist)
					Alert.show("This folder name is exist.");
				else{
					var remote :RemoteObject = new RemoteObject();
					remote.destination = "navigator";
					remote.createNewFolder(event.data.folderName, fileNavigatorViewVH.getSelectedItemPath());
//					trace(fileNavigatorViewVH.getSelectedItem());
//					trace(fileNavigatorViewVH.getSelectedItemPath());
					remote.addEventListener(FaultEvent.FAULT,fault);
					
					var newFolderNode:XML = <folder/>;
					newFolderNode.@ID= "0";
					newFolderNode.@name=event.data.folderName;
					newFolderNode.@type="folder";
//					fileNavigatorViewModel.xmlListCollection.addItem(newFolderNode);
//					trace(fileNavigatorViewModel.xmlListCollection + "qqqqqqqqqqqqqqqqqqqq");
//					trace(fileNavigatorViewModel.xmlList);
//					fileNavigatorViewVH.getDataDescriptor().addChildAt(
					fileNavigatorViewVH.addAndExpandItem(fileNavigatorViewVH.getSelectedItem(), newFolderNode, 0, true);
				}
			}
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}