package org.act.od.impl.commands
{
	
	import flash.events.Event;
	import flash.net.SharedObject;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	import org.act.od.impl.viewhelper.UserNavigatorViewVH;
	import org.act.od.impl.events.CooperateOperationEvent;
	/**
	 * Rollback the figure in 20 steps（can edit the steps）
	 *  
	 * @author mengsong
	 * 
	 */	
	
	public class RollBackCMD extends AODCommand
	{	
		
		
		public function RollBackCMD()
		{	
			super();
			
		}
		public var feModel_one :FigureEditorModel;
		public var feModel_multi :FigureEditorModel;
		public var FileID:String = new String();
		public var ENVH:EditorNavigatorVH;
		override public function execute(event :OrDesignerEvent) :void
		{
			
			var FNV:FileNavigatorViewVH;
			var ENVH:EditorNavigatorVH;
			var BpelID :String;
			
			var nowArrayID :Number = new Number();
			
			var figureEditorNavigatorModel :FigureEditorNavigatorModel;
			var xml :XML = new XML;
			
			var feNavModel :FigureEditorNavigatorModel;
			//			var feModel_one :FigureEditorModel;
			
			//			var feModel_multi :FigureEditorModel;
			//			var FileID:String = new String();	
			var Name:String = new String();		
			var Path:String = new String();
			//			Add 4 crash recovery
			var lso:SharedObject;					
			var lso_array:Array = new Array;			
			var DTVH:DesignerToolBarVH;
			
			FNV = ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY)
				as FileNavigatorViewVH
			ENVH = ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY)
				as EditorNavigatorVH	
			DTVH=ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
				as DesignerToolBarVH;		
			BpelID = new String(FNV.relativeBpelFileIDT.toString());	
			nowArrayID = ENVH.IDNum;	
			
			FileID = FNV.fileIDT.toString();
			Name = FNV.fileNameT.toString();
			Path = FNV.filePathT.toString();
			
			//			get the runtime xml code
			figureEditorNavigatorModel = OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
			figureEditorNavigatorModel.activeFigureEditorModel.saveCanvasXML();
			xml = ProcessFigure(figureEditorNavigatorModel.activeFigureEditorModel.rootFigure)
				.outputAllInformation();
			
			feNavModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			feModel_one = feNavModel.addFigureEditorModel(ENVH.FileID,BpelID);	
			feModel_multi = feNavModel.addFigureEditorModel(FileID,BpelID);
			
			
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length!=0)
			{
				var tempXML:XML = new XML(OrDesignerModelLocator.getInstance().getOrchestraDesigner()
					.RollBack_arraytemp.pop());
				
				//				for redo
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp.push(tempXML);
				
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_TotalArray[nowArrayID]
					=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
				//				for redo
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_TotalArray[nowArrayID]
					=OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp;
				
				//				add by quyue
				//				for change the icon 
				DTVH.designerToolBar.redoButton.enabled = true;
				DTVH.designerToolBar.redoButton.setStyle("icon",DTVH.designerToolBar.redo);
				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length==1)
				{
					DTVH.designerToolBar.rollbackButton.enabled = false;
					DTVH.designerToolBar.rollbackButton.setStyle("icon",DTVH.designerToolBar.newrollback);
				}
												
				if(tempXML.children() == xml.children())
				{
					var array:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner()
						.RollBack_arraytemp;
					var aaa:XML = new XML(array.pop());
					tempXML = aaa;
					
					//					for redo
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp
						.push(tempXML);
					
					OrDesignerModelLocator.getInstance().getOrchestraDesigner()
						.RollBack_TotalArray[nowArrayID]
						=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
					
					//					for redo
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_TotalArray[nowArrayID]
						=OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp;										
				}
				
				//				Added for crash recovery
				lso = SharedObject.getLocal("nowState");
				
				lso_array.push(xml);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path);
				
				lso.data.nowState = lso_array;	
				
				//				single tabs	
				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_TotalArray.length==1)
				{
					feModel_multi._canvasXML = tempXML;
					//					
					//					feModel_multi.rootFigure.readInformationToFigure(tempXML);	
					//					
					if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
						.length == 0)
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = tempXML;
					//													
					//					feModel_multi.updateCanvasXML();
					
					//			For cooperate
					var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
					if(activeFEModel != null){
//						new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION).dispatch();
					}
					else		
						
						//				test
						//				!!!!!!!!!!!!!!!!!!!
						//						new FigureCanvasAppEvent(FigureCanvasAppEvent.RE_FRESH,
						//							{ figureEditorModel:feModel_multi, fileID:FileID }).dispatch();
						
						new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
							{ fileID:FileID, filePath:Path, 
								fileName:Name, figureEditorModel:feModel_multi }).dispatch();					
				}
					//				multi tabs
				else
				{
					feModel_one._canvasXML = tempXML;
					
					feModel_one.rootFigure.readInformationToFigure(tempXML,feModel_one.rootModel,feModel_one);	
					
					if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
						.length == 0)
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = tempXML;
					
					feModel_one.updateCanvasXML();
//					for cooperate
					var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
					if(activeFEModel != null){
//						new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION).dispatch();
					}
					else					
						new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
							{ fileID:ENVH.FileID, filePath:ENVH.Path, 
								fileName:ENVH.Name, figureEditorModel:feModel_one }).dispatch();	
					//						this.testopen2();
				}			  
				new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
					{canvas :null}).dispatch();
			}
			else
			{
				DTVH.designerToolBar.rollbackButton.enabled = false;
				DTVH.designerToolBar.rollbackButton.setStyle("icon",DTVH.designerToolBar.newrollback);
			}							
		}		
	}	
}