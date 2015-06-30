package org.act.od.impl.commands
{
	import mx.binding.utils.BindingUtils;
	import mx.collections.XMLListCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.FileIDManager;
	import org.act.od.impl.model.FileNavigatorViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.view.LevelModel;
	import org.act.od.impl.view.OrchestraDesigner;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	
	/**
	 * Create a new xmlFile in fileNavigator.
	 * 
	 *  @author Quyue
	 * 
	 */
	public class NewFileCMD extends AODCommand
	{
		[Bindable]
		public var newfilejudge:Boolean = false;
		public function NewFileCMD(){
			super();
		}
		/**
		 * @param event {fileName}
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			var curChild :EditorNavigatorChild = feNavVH.figureEditorNavigator.selectedChild as EditorNavigatorChild;
			if(event.data.fathefigureeditormodel == null){
				var fileNavigatorViewModel :FileNavigatorViewModel =
					OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
				
				var xml2 : XML;
				var xml: XML;
				if(event.data.sourceXML != null){
					xml = event.data.sourceXML;
				}else{
					xml = new XML("<Process></Process>");
					xml.@processType = event.data.fileType;
					xml.@maxId = 1;
					xml.@category=event.data.fileCategory;
				}
				xml2 = xml;
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = xml2;	
				var fileidnum:String = event.data.fileId;
				var _figureEditorModel:FigureEditorModel = new FigureEditorModel(fileidnum);
				trace(_figureEditorModel.fileID+"---------------newfilecmd1");
				var feNavModel :FigureEditorNavigatorModel =
					OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				_figureEditorModel = feNavModel.addFigureEditorModel(fileidnum,"b"+fileidnum);
				_figureEditorModel.setRootModel(_figureEditorModel);
				_figureEditorModel.setFatherModel(null);
				var proF:ProcessFigure = _figureEditorModel.rootFigure as ProcessFigure;
				proF.readInformationToFigure(xml,_figureEditorModel,_figureEditorModel);
				
				//add by lu 2009-09-24
				_figureEditorModel.idManager.setAvailabelId(int(xml.@maxId));
				
				_figureEditorModel.updateCanvasXML();				
				
				//2. active the figureEditorpage
				new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
					{ fileID:fileidnum, filePath:null,
						fileName:event.data.fileName, fileCategory:null,  figureEditorModel:_figureEditorModel }
				).dispatch();
				
			}else{
				var xml2 : XML;
				var fileNavigatorViewModel :FileNavigatorViewModel =
					OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
				
				var xml :XML = new XML("<Process></Process>");
				xml.@processType = event.data.fileType;
				xml.@maxId = 1;
				
				xml.@category=event.data.fileCategory;
				
				xml2 = xml;
				
				//  下面不知道有什么用
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = xml2;		
				var fileidnum:String = event.data.fileId;
				var _figureEditorModel:FigureEditorModel = new FigureEditorModel(fileidnum);
				trace(_figureEditorModel.fileID+"---------------newfilecmd2");
				var feNavModel :FigureEditorNavigatorModel =
					OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				_figureEditorModel = feNavModel.addFigureEditorModel(fileidnum,"b"+fileidnum);
//				OrDesignerModelLocator.getInstance().fileIdNum = fileidnum+1; 
				var fatherFigureEditorModel:FigureEditorModel = event.data.fathefigureeditormodel;
				//				var fatherfigure:AbstractFigure = event.data.fatherFigure;
				_figureEditorModel.setFatherModel(fatherFigureEditorModel);
				_figureEditorModel.setRootModel(fatherFigureEditorModel.rootModel);
				if(_figureEditorModel.canvasXML == null){
					
					_figureEditorModel.rootFigure.readInformationToFigure(xml,_figureEditorModel.rootModel,_figureEditorModel);
					
					_figureEditorModel.idManager.setAvailabelId(int(xml.@maxId));
					
					_figureEditorModel.updateCanvasXML();
				}
				//				fatherfigure.setSonFigureEditorModel(_figureEditorModel);
				
				var fatherFigure:AbstractFigure = event.data.fatherFigure as AbstractFigure;
				if(fatherFigure.sonFigureEditorModel != null){
					var sonfem:FigureEditorModel = fatherFigure.sonFigureEditorModel;
					sonfem.setRootModel(fatherFigure.sonFigureEditorModel.rootModel);
					fatherFigure.setSonFigureEditorModel(sonfem);
					fatherFigure.setSonXML(fatherFigure.sonFigureEditorModel._canvasXML);
					fatherFigure.setFileId(fatherFigure.fileId);
				}else{
					fatherFigure.setSonFigureEditorModel(_figureEditorModel);
					fatherFigure.setSonXML(_figureEditorModel._canvasXML);
					fatherFigure.setFileId(event.data.num+"");
				}
				_figureEditorModel.setFatherFigure(fatherFigure);
				//2. active the figureEditorpage
				new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
					{ fileID:fileidnum, filePath:null,
						fileName:event.data.fileName, fileCategory:null,  figureEditorModel:_figureEditorModel }
				).dispatch();
								
			}
		}
		

		
		private function getXmlResult0(event :ResultEvent):void
		{
			var remote2 :RemoteObject = new RemoteObject();
			remote2.destination = "navigator";
			remote2.initFile();
			remote2.addEventListener(ResultEvent.RESULT,getXmlResult1);
			remote2.addEventListener(FaultEvent.FAULT,fault2);
			
		}
		
		private function getXmlResult1(event :ResultEvent):void
		{
			var remote3 :RemoteObject = new RemoteObject();
			remote3.destination = "navigator";
			remote3.getXml();
			remote3.addEventListener(ResultEvent.RESULT,getXmlResult2);
			remote3.addEventListener(FaultEvent.FAULT,fault3);
			
		}
		
		private function getXmlResult2(event :ResultEvent):void{
			var fileNavigatorViewModel :FileNavigatorViewModel =
				OrDesignerModelLocator.getInstance().getFileNavigatorViewModel();
			var str:String=event.result.valueOf();
			var xml:XML=new XML(str);
			var xmlList :XMLList = xml.elements();
			
			fileNavigatorViewModel.xmlList = xmlList.copy();
			fileNavigatorViewModel.xmlListCollection = new XMLListCollection(fileNavigatorViewModel.xmlList);
			
		}
		private function fault3(event :FaultEvent):void{
			Alert.show("Remote 3 invoke error : "+event.message);
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		private function fault2(event :FaultEvent):void{
			Alert.show("Remote 2 invoke error: "+event.message);
		}
	}
}