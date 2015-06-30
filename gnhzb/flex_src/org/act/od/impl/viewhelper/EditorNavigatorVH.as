package org.act.od.impl.viewhelper{
	
	
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.view.SuperTabEvent;
	import org.act.od.framework.view.ViewHelper;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.BpelEditorModel;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.BpelEditor;
	import org.act.od.impl.view.EditorNavigator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.view.FigureEditor;
	import org.act.od.impl.view.Microimage;
	
	
	/**
	 * The ViewHelper of FigureEditorNavigator.
	 * Used to isolate command classes from the implementation details of a view.
	 * 
	 * @author Likexin
	 * 
	 */
	public class EditorNavigatorVH extends ViewHelper
	{
		
		/**
		 * The source for "title" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/title.gif")]
		public var title :Class;
		
		/**
		 * The source for "Bpmn title" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/bpmn.gif")]
		public var bpmnTitle :Class;
		
		/**
		 * The source for "bpelTitle" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/accordion/bpel.gif")]
		public var bpelTitle :Class;
		
		/**
		 * The key of FigureEditorNavigatorVH
		 */
		public static const VH_KEY :String = "FigureEditorNavigatorVH";
		
		public var figureEditor :FigureEditor;
		public var FileID:String = new String();
		public var IDNum:Number = new Number();		
		public var Name:String = new String();		
		public var Path:String = new String();	
		public var OriginalXML:String = new XML;	        
		private var username:String;
		
		// model attribute
		private var figureEditorNavigatorModel :FigureEditorNavigatorModel;
		
		/**
		 * Initialize FigureEditorNavigator with FigureEditorNavigatorVH
		 */
		public function EditorNavigatorVH(document :Object, id :String){
			super();
			this.initialized(document, id);
			this.figureEditorNavigatorModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
		}
		
		/**
		 * return FigureEditorNavigator
		 */
		public function get figureEditorNavigator() :EditorNavigator{
			return this.view as EditorNavigator;
		}
		
		/**
		 * Return active editor's type.
		 * This method return "Figure_Editor_Type" or "Bpel_Editor_Type".
		 */
		public function getCurrentChildType() :String{
			var curChild :EditorNavigatorChild = this.figureEditorNavigator.selectedChild as EditorNavigatorChild;
			if(curChild != null)
				return curChild.type;
			else
				return null;
		}
		
		public function cancelClosedFigureEditor(figureEditor : FigureEditor) : void {
			EditorNavigator(this.view).addChild(figureEditor);
			EditorNavigator(this.view).enabled = Boolean (EditorNavigator(this.view).numChildren - 1);
		}
		
		
		public function getSelectedChildOfNavigator() :EditorNavigatorChild{
			return this.figureEditorNavigator.selectedChild as EditorNavigatorChild;
		}
		
		/**
		 * Handle of TabIndexChange event
		 * @param event
		 *
		 */
		public function onTabIndexChangeHandle(event :Event) :void {
			
			var viewLoc :ViewLocator = ViewLocator.getInstance();
			
			var curChild :EditorNavigatorChild = this.figureEditorNavigator.selectedChild as EditorNavigatorChild;
			
			if(curChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE) {
				
				figureEditorNavigatorModel.activeFigureEditorModel = FigureEditor(curChild).figureEditorModel;
				
				IDNum = FigureEditor(curChild)._Idnum;				 
				FileID =FigureEditor(curChild)._FileID;
				Name =  FigureEditor(curChild)._Name;
				Path =  FigureEditor(curChild)._Path;
				OriginalXML = FigureEditor(curChild)._OriginalXML;
				
				if( !viewLoc.registrationExistsFor(FigureEditorVH.getViewHelperKey(curChild.fileID)) ){
					throw new Error("no binding  with existed figure editor model");
				}
				
				//				FigureEditor(curChild).figureCanvas.notifyMicroimageUpdate();
				//				FigureEditor(curChild).figureCanvas.notifyLevelModelUpdate();
				
			}else if(curChild.type == EditorNavigatorChild.BPEL_EDITOR_TYPE){
				
				figureEditorNavigatorModel.activeBpelEditorModel = BpelEditor(curChild).bpelEditorModel;
				Microimage.getInstance().clearShow();
				if( !viewLoc.registrationExistsFor(BpelEditorVH.getViewHelperKey(curChild.fileID)) ){
					throw new Error("no binding  with existed bpel editor model");
				}
			}
			//			if(curChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE)
			////				FigureEditor(curChild).figureCanvas.notifyMicroimageUpdate();
			//				FigureEditor(curChild).figureCanvas.notigyLevelModelUpdate();
			var feNavModel :FigureEditorNavigatorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var fem:FigureEditorModel = feNavModel.activeFigureEditorModel;
			var figureCanvas:FigureCanvas = fem.figureCanvas;
			figureCanvas.notifyLevelModelUpdate();
		}
		
		
		/**
		 * Handle of TabClose Event
		 * @param event
		 *
		 */
		public function onTabCloseHandle(event :SuperTabEvent):void {
			
			var curChild :EditorNavigatorChild =
				this.figureEditorNavigator.getChildAt(event.tabIndex) as EditorNavigatorChild;
//			
//			if(curChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE) {
//				this.projectLogout();
//				var closedFE : FigureEditor = curChild as FigureEditor;
//				var filename:String = closedFE.label;
//				var closedFEModel : FigureEditorModel = closedFE.figureEditorModel;
//				//				closedFEModel.updateCanvasXML();
//				closedFEModel.updateCanvasXML();
//				
//				var father:AbstractFigure = closedFEModel.fatherFigure;
//				if(father == null){
//					//					father.setSonFigureEditorModel(closedFEModel);
//					//					father.setSonXML(closedFEModel._canvasXML);
//					//					closedFEModel.fatherModel.updateCanvasXML();
//					new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.FIGURE_EDITOR_CLOSE,
//						{closedFE:closedFE,filename:filename} ).dispatch();
//					
//				}
//				//				if(closedFEModel.isChanged()) {
//				//				}
//				
//				//				FigureEditor(this.figureEditorNavigator.selectedChild).figureCanvas.notifyMicroimageUpdate();
//				//				FigureEditor(this.figureEditorNavigator.selectedChild).figureCanvas.notifyLevelModelUpdate();
//				
//			}else if(curChild.type == EditorNavigatorChild.BPEL_EDITOR_TYPE){
//				Microimage.getInstance().clearShow();
//				var closedBE :BpelEditor = this.figureEditorNavigator.getChildAt(event.tabIndex) as BpelEditor;
//				if(closedBE.bpelEditorModel.bpelIsChange){
//					new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.BPEL_EDITOR_CLOSE,
//						{closedBE :closedBE} ).dispatch();
//				}
//			}
//			if(curChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE)
//				//				FigureEditor(curChild).figureCanvas.notifyMicroimageUpdate();
//				//				FigureEditor(curChild).figureCanvas.notifyLevelModelUpdate();
//				var num :int = 0;
//			for(var i :int = 0; i < this.figureEditorNavigator.numChildren; i++){
//				if(EditorNavigatorChild (this.figureEditorNavigator.getChildAt(i)).type == EditorNavigatorChild.FIGURE_EDITOR_TYPE)
//					num++;
//			}
//			if(curChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE && num == 1)
//				Microimage.getInstance().clearShow();
			var closedFE : FigureEditor = curChild as FigureEditor;
//			if(closedFE.figureEditorModel.rootModel == null){
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().bottomMdBox.height = 0;				
//			}
			
		}
		/**
		 * Create a new FigureEditor.
		 * @param feModel
		 * @param filePath
		 * @param fileName
		 * @return
		 *
		 */
		public function createNewFigureEditor(feModel :FigureEditorModel, filePath :String, fileName :String,fileCategory :String) :FigureEditor{
			
			var figureEditor :FigureEditor = new FigureEditor(filePath, fileName, feModel, fileCategory);
			
			if(feModel.processType == FigureEditorModel.BPEL_PROCESS_TYPE)
			{
				figureEditor.icon = title;
			}
			else
				if(feModel.processType == FigureEditorModel.BPMN_PROCESS_TYPE)
				{
					figureEditor.icon = bpmnTitle;
				}
			
			this.figureEditorNavigator.addChild(figureEditor);
			
			//			figureEditor._Idnum = OrDesignerModelLocator.getInstance().getOrchestraDesigner().IDnum;
			//			figureEditor._FileID = OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID;
			//			figureEditor._Name = OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name;
			//			figureEditor._Path = OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path;
			var em:FigureEditorModel = figureEditor.figureEditorModel;
			return figureEditor;
		}
		
		/**
		 * Create a new BPELEditor.
		 * @param beModel
		 * @param filePath
		 * @param fileName
		 * @return
		 *
		 */
		public function createNewBpelEditor(beModel:BpelEditorModel, filePath :String, fileName :String):BpelEditor{
			
			var bpelEditor:BpelEditor = new BpelEditor(filePath, fileName, beModel);
			bpelEditor.icon = bpelTitle;
			
			this.figureEditorNavigator.addChild(bpelEditor);
			return bpelEditor;
		}
		
		
		/**
		 * Change the figureEditorNavigator.selectedIndex by the given fileID
		 * if the Tab of the given fileID not exist, then do nothing
		 *
		 * @param feNavChild
		 */
		public function SwithToGivenFileID(fileID :String) :void{
			
			var index :int;
			
			for(index=0; index<this.figureEditorNavigator.numChildren; index++) {
				
				var curChildren :EditorNavigatorChild =
					EditorNavigatorChild(this.figureEditorNavigator.getChildAt(index));
				
				if(curChildren.fileID == fileID){
					this.figureEditorNavigator.selectedIndex = index;
					break;
				}
			}
		}
		/**
		 * Close one Tab in the figureEditorNavigator by the given fileID
		 * if the Tab of the given fielID not exist, then do nothing.
		 *
		 * @param feNavChild
		 */
		public function CloseTabelByGivenFileID(fileID :String):void{
			var curChildren :EditorNavigatorChild;
			for (var index :int = 0; index < this.figureEditorNavigator.numChildren; index++){
				curChildren = EditorNavigatorChild(this.figureEditorNavigator.getChildAt(index));
				
				if(curChildren.fileID == fileID){
					this.figureEditorNavigator.removeChildAt(index);
				}
			}
		}
		/**
		 * Logout from the userlist of the projectuserlist when click the close of the tab
		 * 
		 */
		
		private function projectLogout():void{
			username = OrDesignerModelLocator.getInstance().getOrchestraDesigner().userName;
			//			do sth. with the xml file
			//			var remote :RemoteObject = new RemoteObject();
			//			remote.destination = "navigator";
			//			remote.logOut("../database/userlist/projectuserlist.xml",username);
			//			remote.addEventListener(ResultEvent.RESULT, openFileResult_modify);
			//			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		private function openFileResult_modify(event :ResultEvent):void{
			//			for update userListDataGrid
			var message:AsyncMessage = new AsyncMessage();   
			message.body.content = "";
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer_projectuserlist.send(message);
		}	
		
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		
		public function getNum():Number{
			return this.figureEditorNavigator.numChildren;
		}
		// 拿到当前编辑区的所有子模型
		public function getChildren():Array{
			return this.figureEditorNavigator.getChildren();
		}
		
		
	}
}