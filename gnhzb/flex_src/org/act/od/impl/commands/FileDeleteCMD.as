package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	/**
	 * Delete the selected file.
	 * 
	 * @author Quyue
	 * 
	 */ 
	public class FileDeleteCMD extends AODCommand{
		
		public function FileDeleteCMD(){
			super();
		}
		/**
		 * 
		 * @param event {fileID, type}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var fileNavigatorViewVH :FileNavigatorViewVH = 
				ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY) as FileNavigatorViewVH;
			
			var figureEditorNavigatorVH :EditorNavigatorVH =
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			
			var theParentItem :Object = fileNavigatorViewVH.getParentItem(fileNavigatorViewVH.getSelectedItem());
			var xmlList :XMLList = XMLList(theParentItem).elements();
			for(var i :int = 0; i < xmlList.length(); i++){
				if(xmlList[i].@ID == event.data.fileID)
					break;
			}
			var path :String = fileNavigatorViewVH.getSelectedItemPath();
			
			fileNavigatorViewVH.getDataDescriptor().removeChildAt(theParentItem, fileNavigatorViewVH.getSelectedItem(), i);
			figureEditorNavigatorVH.CloseTabelByGivenFileID(event.data.fileID);
			
			var figureEditorNavigatorModel :FigureEditorNavigatorModel = OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
			
			if(event.data.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE)
				figureEditorNavigatorModel.deleteFigureEditorModel(event.data.fileID);
			else if(event.data.type == EditorNavigatorChild.BPEL_EDITOR_TYPE)
				figureEditorNavigatorModel.deleteBpelEditorModel(event.data.fileID);
			
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.deleteFile(path);
			remote.addEventListener(FaultEvent.FAULT,fault);
		}
		
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}