package org.act.od.impl.commands
{
	import mx.collections.ICollectionView;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FileNavigatorViewAppEvent;
	import org.act.od.impl.model.FileNavigatorViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	/**
	 * Delete the selected folder.
	 * 
	 * @author Quyue
	 * 
	 */ 
	public class FolderDeleteCMD extends AODCommand
	{
		private var fileNavigatorViewVH :FileNavigatorViewVH = 
			ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY) as FileNavigatorViewVH;
		public function FolderDeleteCMD(){
			super();
		}
		/**
		 * 
		 * @param event 
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			
			var figureEditorNavigatorVH :EditorNavigatorVH =
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			
			var theParentItem :Object = theParentItem=fileNavigatorViewVH.getParentItem(fileNavigatorViewVH.getSelectedItem());
			var path :String = fileNavigatorViewVH.getSelectedItemPath();
			
			this.Traversing(fileNavigatorViewVH.getSelectedItem());
			
			if(theParentItem != null){
				fileNavigatorViewVH.getDataDescriptor().removeChildAt(theParentItem, fileNavigatorViewVH.getSelectedItem(), 0);
			}
			else{  //说明要删除的是一个project。20120826
				var fileNavigatorViewModel :FileNavigatorViewModel = OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
				fileNavigatorViewModel.xmlListCollection.removeItemAt(fileNavigatorViewModel.xmlListCollection.getItemIndex(fileNavigatorViewVH.getSelectedItem()));	
			}
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.deleteFile(path);
			remote.addEventListener(FaultEvent.FAULT,fault);
			
		}
		private function Traversing(theParentItem:Object):void{
			if(fileNavigatorViewVH.getDataDescriptor().isBranch(theParentItem)){
				var children :ICollectionView = fileNavigatorViewVH.getDataDescriptor().getChildren(theParentItem);
				var i:int;
				for(i = 0; i < children.length; i++){
					Traversing(children[i]);
				}
			}
			else if(theParentItem.@type == EditorNavigatorChild.FIGURE_EDITOR_TYPE || theParentItem.@type == EditorNavigatorChild.BPEL_EDITOR_TYPE){
				
				new FileNavigatorViewAppEvent(FileNavigatorViewAppEvent.FILE_DELETE,
					{fileID :theParentItem.@ID}).dispatch();
			}
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}