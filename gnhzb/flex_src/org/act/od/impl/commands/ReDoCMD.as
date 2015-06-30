package org.act.od.impl.commands
{
	import flash.net.SharedObject;
	
	import mx.controls.Alert;
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
	import org.act.od.impl.viewhelper.FileNavigatorViewVH;
	import org.act.od.impl.events.CooperateOperationEvent;
	
	/**
	 * Redo the process in 20 steps（can edit the steps）
	 *  
	 * @author mengsong
	 * 
	 */		
	
	public class ReDoCMD extends AODCommand
	{	
		
		public function ReDoCMD()
		{	
			super();
		}
		
		override public function execute(event :OrDesignerEvent) :void
		{
			
			var FNV:FileNavigatorViewVH;
			var ENVH:EditorNavigatorVH;
			var BpelID :String;
			
			//			nowArrayID is the ID of Total Array
			var nowArrayID :Number = new Number();
			
			var figureEditorNavigatorModel :FigureEditorNavigatorModel;
			var xml :XML = new XML;
			var feNavModel :FigureEditorNavigatorModel;
			var feModel_one :FigureEditorModel;
			
			var feModel_multi :FigureEditorModel;
			var FileID:String = new String();	
			var Name:String = new String();		
			var Path:String = new String();
			//			Add 4 crash recovery
			var lso:SharedObject;			
			var lso_array:Array = new Array;			
			//			for change the icon
			var DTVH:DesignerToolBarVH;
			
			DTVH=ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
				as DesignerToolBarVH;
			
			FNV = ViewLocator.getInstance().getViewHelper(FileNavigatorViewVH.VH_KEY)
				as FileNavigatorViewVH
			
			ENVH = ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY)
				as EditorNavigatorVH	
			
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
			
			
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp.length!=0)
			{
				var tempXML:XML = new XML(OrDesignerModelLocator.getInstance().getOrchestraDesigner()
					.ReDo_arraytemp.pop());
				
				//				add for icon change
				DTVH.designerToolBar.rollbackButton.enabled = true;
				DTVH.designerToolBar.rollbackButton.setStyle("icon",DTVH.designerToolBar.rollback);
				//jinyong
				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp.length==1)
				{
					DTVH.designerToolBar.redoButton.enabled = false;
					DTVH.designerToolBar.redoButton.setStyle("icon",DTVH.designerToolBar.newredo);
				}
				
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
					.push(tempXML);
				
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_TotalArray[nowArrayID]
					=OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp;
				
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_TotalArray[nowArrayID]
					=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
				
				if(tempXML.children() == xml.children())
				{
					var array:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner()
						.ReDo_arraytemp;
					var aaa:XML = new XML(array.pop());
					tempXML = aaa;
					
					//					4 RollBack
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
						.push(tempXML);
					
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_TotalArray[nowArrayID]
						=OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp;
					
					OrDesignerModelLocator.getInstance().getOrchestraDesigner()
						.RollBack_TotalArray[nowArrayID]
						=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
					
				}
				
				//				Added for crash recovery
				lso = SharedObject.getLocal("nowState");
				
				lso_array.push(xml);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path);
				
				lso.data.nowState = lso_array;				
				
				//			multi tabs
				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_TotalArray.length==1)
				{
					feModel_multi._canvasXML = tempXML;
					feModel_multi.rootFigure.readInformationToFigure(tempXML,feModel_multi.rootModel,feModel_multi);	//20120906修改
					
					if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp.length == 0)
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = tempXML;
					
					feModel_multi.updateCanvasXML();
					
					//			For cooperate
					var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
					if(activeFEModel != null){
//						new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION).dispatch();
					}
					else				
						new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
							{ fileID:FileID, filePath:Path, 
								fileName:Name, figureEditorModel:feModel_multi }).dispatch();
				}
					//			sigle tabs
				else
				{
					feModel_one._canvasXML = tempXML;
					feModel_one.rootFigure.readInformationToFigure(tempXML,feModel_one.rootModel,feModel_one);	//20120906修改
					
					if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().ReDo_arraytemp.length == 0)
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML = tempXML;
					
					feModel_one.updateCanvasXML();
					new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
						{ fileID:ENVH.FileID, filePath:ENVH.Path, 
							fileName:ENVH.Name, figureEditorModel:feModel_one }).dispatch();	
				}			  
				new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
					{canvas :null}).dispatch();
			}
			else 
			{
				DTVH.designerToolBar.redoButton.enabled = false;
				DTVH.designerToolBar.redoButton.setStyle("icon",DTVH.designerToolBar.newredo);
			}						
		}
	}
	
}