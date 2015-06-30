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
	 * Create a new BPELfile in fileNavigator.
	 * 
	 *  @author Zhaoxq
	 * 
	 */ 
	public class NewBPELFileCMD extends AODCommand
	{   
		/**
		 *Constructor,
		 * call super class constructor. 
		 * 
		 */	
		public function NewBPELFileCMD(){
			super();
		}
		/**
		 *  Execute Commande to create BPEL file.
		 * 
		 * @param event {fileID, filePath, fileName} : OrDesignerEvent type.
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			var fileNavigatorViewVH :FileNavigatorViewVH = 
				ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY) as FileNavigatorViewVH;
			var fileNavigatorViewModel :FileNavigatorViewModel =
				OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
			
			var bpelFilePath: String = event.data.filePath;
			var bpelFileName: String = event.data.fileName;	
			var figureFileName :String = bpelFileName.substring(0 ,bpelFileName.length - 5) + ".xml";
			
			var newBpelFileNode:XML=<file/>;
			newBpelFileNode.@ID = event.data.fileID;
			newBpelFileNode.@name = bpelFileName;
			newBpelFileNode.@type = EditorNavigatorChild.BPEL_EDITOR_TYPE;
			var theParentItem:Object;
			theParentItem=fileNavigatorViewModel.xmlList.children();
			var startIndex:int=0;
			var endIndex:int=0;
			var path:Array=new Array;
			while(bpelFilePath.indexOf("\\",startIndex)>0){
				endIndex=bpelFilePath.indexOf("\\",startIndex);
				path.push(bpelFilePath.substring(startIndex,endIndex));
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
			for(i = 0; i < temp.length(); i++)
				if( XML(temp[i]).@name == figureFileName)
					break;
			var tag:Boolean = temp.contains(newBpelFileNode);
			if(!tag){
				fileNavigatorViewVH.addAndExpandItem(thisXmlList[j], newBpelFileNode, i + 1, true);
				
				var remote :RemoteObject = new RemoteObject();
				remote.destination = "navigator";
				remote.createNewFile(event.data.fileName, bpelFilePath.substring(0, bpelFilePath.lastIndexOf("\\", bpelFilePath.length)));
				remote.addEventListener(FaultEvent.FAULT,fault);
			}
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}