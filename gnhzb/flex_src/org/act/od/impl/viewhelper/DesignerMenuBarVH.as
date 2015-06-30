package org.act.od.impl.viewhelper
{
	import flash.events.*;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	
	import mx.containers.HDividedBox;
	import mx.containers.Panel;
	import mx.containers.VDividedBox;
	import mx.events.CloseEvent;
	import mx.events.MenuEvent;
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.view.ViewHelper;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.DesignerMenuBarAppEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorAppEvent;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.FileNavigatorViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.BPELFileOverWriteWarning;
	import org.act.od.impl.view.DesignerMenuBar;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.view.FigureAttributeNavigator;
	import org.act.od.impl.view.FileNavigator;
	import org.act.od.impl.view.Microimage;
	import org.act.od.impl.view.SaveFileWindow;
	import org.act.od.impl.view.SaveFileWindownew;
	import org.act.od.impl.view.UDDIConfigurationWindow;
	
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	
	/**
	 * The ViewHelper of DesignerMenuBar.
	 * Used to isolate command classes from the implementation details of a view.
	 * 
	 * @author Likexin
	 * 
	 */
	public class DesignerMenuBarVH extends ViewHelper
	{
		/**
		 * The key of DesignerMenuBarVH
		 */
		public static const VH_KEY :String = "DesignerMenuBarVH";
		private var fileNavigatorModel:FileNavigatorViewModel;
		private var figureEditorNavigatorModel:FigureEditorNavigatorModel;
		private var fileNavigator :FileNavigator;
		private var figureAttributeNavigator:FigureAttributeNavigator;
		
		private var fileReference:FileReference;
		private var _fileID:String;
		private var _filePath:String;
		private var _fileName:String;
		private var _fileCategory :String;
		private var _figureEditorModel :FigureEditorModel;
		
		/**
		 * Initialize DesignerMenuBar with DesignerMenuBarVH
		 */
		public function DesignerMenuBarVH(document : Object, id : String)
		{
			super();
			initialized(document, id);
			this.fileNavigatorModel=OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
			
			fileReference = new FileReference;
			fileReference.addEventListener(Event.SELECT, fileReference_select);  
			fileReference.addEventListener(Event.COMPLETE, fileReference_complete);
		}
		/**
		 * Return DesignerMenuBar.
		 */
		private function get designerMenuBar() :DesignerMenuBar{
			return this.view as DesignerMenuBar;
		}
		
		/**
		 * Deal with the MenuClick event.
		 */
		public function menuClick(event:MenuEvent) :void{
			var midVDBox1 : HDividedBox;
			var leftBmBox : Panel;
			fileNavigator = OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfileNavigator();
			figureAttributeNavigator = OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator();
			switch(int(event.item.@id))
			{
				case 101:
					var fileSelectWindow:FileFilter = new FileFilter("模型文件(*.xml)", "*.xml");
					fileReference.browse([fileSelectWindow]);
					break;
				case 1: //new project
					var newProject:SaveFileWindow = SaveFileWindow(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), SaveFileWindow,true));
					newProject.setTitle("Project");
					PopUpManager.centerPopUp(newProject);
					newProject.addEventListener(CloseEvent.CLOSE, newProjectResult);
					break;
				
				case 2://open/new folder
					var newFolder:SaveFileWindow = SaveFileWindow(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), SaveFileWindow,true));
					newFolder.setTitle("Folder");
					PopUpManager.centerPopUp(newFolder);
					newFolder.addEventListener(CloseEvent.CLOSE, newFolderResult);
					break;
				case 3://quit or new file
					var newFile:SaveFileWindownew = SaveFileWindownew(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), SaveFileWindownew,true));
					newFile.setTitle("File");
					PopUpManager.centerPopUp(newFile);
					newFile.addEventListener(CloseEvent.CLOSE,newFileResult);
					break;
				
				case 4:// Close
//										this.figureEditorNavigatorModel.closeFigureEditor();
					break;
				
				case 5://Close All
//										this.figureEditorNavigatorModel.closeAllFigureEditor();
					break;
				
				case 6://save
					this.onSaveHandle();
					break;
				
				case 7://save all
					this.onSaveAllHandle();
					break;
				
				case 8://Cut
					this.onCutHandle();
					break;
				
				case 9://Copy
					this.onCopyHandle();
					break;
				
				case 10://Paste
					this.onPasteHandle();
					break;
				
				case 11://Select All
					this.onSelectAll();
					break;
				
				case 12://Create BPEL
					this.onBpelHandel();
					break;
				
				case 13://UDDI Configuration
					this.onDUUIConfigurationHandle();
					break;
				
				case 14://About
					this.onAboutHandle();
					break;	
				
				case 20://Option Display File Navigator
					if(event.item.@toggled ==true){
						fileNavigator.showFileNavigator();
						DesignerMenuBar.lso.data.selected_file_navigator =true;
						DesignerMenuBar.lso.flush();
					}else{
						fileNavigator.hideFileNavigator();
						DesignerMenuBar.lso.data.selected_file_navigator =false;
						DesignerMenuBar.lso.flush();
					}
					break;
				case 21://Option Display Project User List
					if(event.item.@toggled ==true){
						fileNavigator.showProjectUserList();
						DesignerMenuBar.lso.data.selected_projectuserlist =true;
						DesignerMenuBar.lso.flush();
					}else{
						fileNavigator.hideProjectUserList();
						DesignerMenuBar.lso.data.selected_projectuserlist =false;
						DesignerMenuBar.lso.flush();
					}
					break;
				
				//				Add for total userlist
				case 22://Option Display Total User List
					//					if(event.item.@toggled ==true){
					//						fileNavigator.showUserList();
					//						DesignerMenuBar.lso.data.selected_user_list =true;
					//						DesignerMenuBar.lso.flush();
					//					}else{
					//						fileNavigator.hideUserList();
					//						DesignerMenuBar.lso.data.selected_user_list =false;
					//						DesignerMenuBar.lso.flush();
					//					}
					break;
				case 23://Option Display UDDI Navigator
					if(event.item.@toggled ==true){
						fileNavigator.showUDDINavigator();
						DesignerMenuBar.lso.data.selected_uddi_navigator =true;
						DesignerMenuBar.lso.flush();
					}else{
						fileNavigator.hideUDDINavigator();
						DesignerMenuBar.lso.data.selected_uddi_navigator =false;
						DesignerMenuBar.lso.flush();
					}
					break;
				case 24://Option Display Proprities
					if(event.item.@toggled ==true){
						figureAttributeNavigator.showFigureAttribute();
						DesignerMenuBar.lso.data.selected_attribute =true;
						DesignerMenuBar.lso.flush();
					}else{
						figureAttributeNavigator.hideFigureAttribute();
						DesignerMenuBar.lso.data.selected_attribute =false;
						DesignerMenuBar.lso.flush();
					}
					break;	
				
//				case 25://Option Display ChatRoom
//					if(event.item.@toggled ==true){
//						figureAttributeNavigator.showChatRoom();
//						DesignerMenuBar.lso.data.selected_chatroom =true;
//						DesignerMenuBar.lso.flush();
//					}else{
//						figureAttributeNavigator.hideChatRoom();
//						DesignerMenuBar.lso.data.selected_chatroom =false;
//						DesignerMenuBar.lso.flush();
//					}
//					break;	
				
				case 26 :// Option display microimage 
					if(event.item.@toggled ==true){
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().showMic();
						DesignerMenuBar.lso.data.selected_micro =true;
						DesignerMenuBar.lso.flush();
					}else{
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().hidMic();
						DesignerMenuBar.lso.data.selected_micro =false;
						DesignerMenuBar.lso.flush();
					}
					break;	
				
				default:
					break;
			}
		}
		
		private function fileReference_select(event:Event):void {
			fileReference.load();
		}
		private function fileReference_complete(event:Event):void {
			var fileString:String = fileReference.data.toString();
			trace(fileString);
			var xml:XML = new XML(fileString);
			//qu
			this._fileCategory=xml.@category;
			this._fileID = "File-998";
			this._fileName = "BPEL.xml";
			this._filePath = "Example\\BPEL.xml";
			
			var feNavModel :FigureEditorNavigatorModel =
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			this._figureEditorModel = feNavModel.addFigureEditorModel(this._fileID, this._fileID);
//			this._figureEditorModel = feNavModel.getFigureEditorModel(this._fileID);
//			this._figureEditorModel = feNavModel.activeFigureEditorModel;
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = xml;		
			
			if(this._figureEditorModel.canvasXML == null){
//				this._figureEditorModel.rootFigure.readInformationToFigure(xml);   //不需要了，20120906
				
				//add by lu 2009-09-24
				this._figureEditorModel.idManager.setAvailabelId(int(xml.@maxId));
				
				this._figureEditorModel.updateCanvasXML();
			}
			
			//2. active the figureEditorpage
//			this.nextEvent = 
			new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
				{ fileID:this._fileID, filePath:this._filePath,
					fileName:this._fileName, fileCategory:this._fileCategory, figureEditorModel:this._figureEditorModel }
			);
			
//			this.executeNextCommand();
		}
		private function newProjectResult(event:CloseEvent):void{
			var projectName:String=SaveFileWindow(event.currentTarget).getText();
			if(projectName != "")
				new DesignerMenuBarAppEvent(DesignerMenuBarAppEvent.NEW_PROJECT,
					{projectName:projectName}).dispatch();
		}
		private function newFolderResult(event:CloseEvent):void{
			var folderName:String=SaveFileWindow(event.currentTarget).getText();
			if(folderName != "")
				new DesignerMenuBarAppEvent(DesignerMenuBarAppEvent.NEW_FOLDER,
					{folderName:folderName}).dispatch();
		}
		private function newFileResult(event:CloseEvent):void{
//			var fileName:String=SaveFileWindownew(event.currentTarget).getText();
//			
//			var fileType:String = SaveFileWindownew(event.currentTarget).getSelectedFileType();
//			//qu
//			var fileCategory:String =SaveFileWindownew(event.currentTarget).getSelectedFileCategory();
//			
//			if(fileName != "")
//				fileName = fileName + ".xml";
//			new DesignerMenuBarAppEvent(DesignerMenuBarAppEvent.NEW_FILE,
//				{fileName:fileName , fileType:fileType ,fileCategory:fileCategory}).dispatch();
		}
		
		private function onSaveHandle():void {
			new DesignerMenuBarAppEvent(DesignerMenuBarAppEvent.FILE_SAVE,
				{}).dispatch();
		}
		private function onSaveAllHandle():void{
			new DesignerMenuBarAppEvent(DesignerMenuBarAppEvent.FILE_SAVEALL,
				{}).dispatch();
		}
		private function onCutHandle():void{
			var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if(activeFEModel != null){
				
				new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURES_COPY,
					{fileID :activeFEModel.fileID} ).dispatch();
				new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURE_DELETE_FROM_CANVAS,
					{fileID :activeFEModel.fileID} ).dispatch();
			}
		}
		
		private function onCopyHandle():void{
			var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if(activeFEModel != null){
				
				new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURES_COPY,
					{fileID :activeFEModel.fileID} ).dispatch();
			}
		}
		
		private function onPasteHandle():void{
			var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if(activeFEModel != null){
				
				new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURES_PASTE,
					{fileID :activeFEModel.fileID} ).dispatch();
			}
		}
		private function onSelectAll():void{
			
			var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if(activeFEModel != null){
				
				new FigureCanvasAppEvent(FigureCanvasAppEvent.Select_All,
					{fileID :activeFEModel.fileID} ).dispatch();
			}
		}
		private function onBpelHandel():void{
			var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if(activeFEModel != null && activeFEModel.relativeBpelID != null){
				var figureEditorVH :FigureEditorVH =
					ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(activeFEModel.fileID)) as FigureEditorVH;
				
				var isOverWrite :BPELFileOverWriteWarning = BPELFileOverWriteWarning(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), BPELFileOverWriteWarning,true));
				var filePath :String = figureEditorVH.filePath;
				var figureFileName :String = filePath.substring(filePath.lastIndexOf("\\", filePath.length) + 1, filePath.length);
				var bpelFileName :String = figureFileName.substring(0, figureFileName.length - 4) + ".bpel";
				isOverWrite.setText(bpelFileName);
				PopUpManager.centerPopUp(isOverWrite);
				isOverWrite.addEventListener(CloseEvent.CLOSE, onBpelHandelResult);
				
			}else if(activeFEModel != null){
				this.onBpelHandelResult(new CloseEvent(CloseEvent.CLOSE));
			}
		}
		private function onBpelHandelResult(event :CloseEvent) :void{
			var figureEditorNavigatorVH :EditorNavigatorVH = ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			if(figureEditorNavigatorVH.getCurrentChildType() == null)
				return;
			if(figureEditorNavigatorVH.getCurrentChildType() == EditorNavigatorChild.FIGURE_EDITOR_TYPE){
				var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
				var figureEditorVH :FigureEditorVH =
					ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(activeFEModel.fileID)) as FigureEditorVH;
				new FigureEditorAppEvent(FigureEditorAppEvent.BPEL_CREAT,
					{figureFilePath :figureEditorVH.filePath }).dispatch();
			}
		}
		private function onDUUIConfigurationHandle():void{
			var configuration :UDDIConfigurationWindow = UDDIConfigurationWindow(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(),UDDIConfigurationWindow,true));
			PopUpManager.centerPopUp(configuration);
			configuration.addEventListener(CloseEvent.CLOSE, configurationResult);
		}
		private function configurationResult(event :CloseEvent):void{
			var _address :String = UDDIConfigurationWindow(event.currentTarget).getAddress();
			var _name :String = UDDIConfigurationWindow(event.currentTarget).getName();
			new DesignerMenuBarAppEvent(DesignerMenuBarAppEvent.UDDI_CONFIGURATION,
				{address : _address, name : _name} ).dispatch();
		}
		
		private function onAboutHandle():void{
			
			new FigureCanvasAppEvent(FigureCanvasAppEvent.About_Info).dispatch();
		}
	}
	
}