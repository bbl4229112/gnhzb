package org.act.od.impl.control{
	
	import org.act.od.framework.control.FrontController;
	import org.act.od.impl.commands.AboutInfoCMD;
	import org.act.od.impl.commands.ActiveBpelEditorPageCMD;
	import org.act.od.impl.commands.ActiveFigureEditorPageCMD;
	import org.act.od.impl.commands.AttributeChangeCMD;
	import org.act.od.impl.commands.AttributeViewUpdateCMD;
	import org.act.od.impl.commands.BPELCreatCMD;
	import org.act.od.impl.commands.BPELEditorCloseCMD;
	import org.act.od.impl.commands.BPELFileOpenCMD;
	import org.act.od.impl.commands.BestPracticeUpdateCMD;
	import org.act.od.impl.commands.CancleCloseBPELEditorCMD;
	import org.act.od.impl.commands.CancleCloseFigureEditorCMD;
	import org.act.od.impl.commands.CanvasChildrenChangedCMD;
	import org.act.od.impl.commands.CanvasNodeSelectedCMD;
	import org.act.od.impl.commands.ChangeFigureSizeCMD;
	import org.act.od.impl.commands.ChangeToBPELViewCMD;
	import org.act.od.impl.commands.CoReceiveMessageADMManageCMD;
	import org.act.od.impl.commands.CoReceiveMessageAddCMD;
	import org.act.od.impl.commands.CoReceiveMessageChangeLinkLabelCMD;
	import org.act.od.impl.commands.CoReceiveMessageChangeSizeCMD;
	import org.act.od.impl.commands.CoReceiveMessageCreateCMD;
	import org.act.od.impl.commands.CoReceiveMessageDeleteCMD;
	import org.act.od.impl.commands.CoReceiveMessageFigureDeleteCMD;
	import org.act.od.impl.commands.CoReceiveMessageGraphicIFigureCMD;
	import org.act.od.impl.commands.CoReceiveMessageLinkDeleteCMD;
	import org.act.od.impl.commands.CoReceiveMessageModifyCMD;
	import org.act.od.impl.commands.CoReceiveMessageMoveDownCMD;
	import org.act.od.impl.commands.CoReceiveMessageMoveFigureCMD;
	import org.act.od.impl.commands.CoReceiveMessageMoveLeftCMD;
	import org.act.od.impl.commands.CoReceiveMessageMoveRightCMD;
	import org.act.od.impl.commands.CoReceiveMessageMoveUpCMD;
	import org.act.od.impl.commands.CoReceiveMessagePasteCMD;
	import org.act.od.impl.commands.CoSendMessageCollectionCMD;
	import org.act.od.impl.commands.CompareModeCMD;
	import org.act.od.impl.commands.CooperateCMD;
	import org.act.od.impl.commands.CreateSubProcessCMD;
	import org.act.od.impl.commands.DataAttributeChangeCMD;
	import org.act.od.impl.commands.DefaultWindowsCMD;
	import org.act.od.impl.commands.DrawSignOnFiguresCMD;
	import org.act.od.impl.commands.FigureCopyFromCanvasCMD;
	import org.act.od.impl.commands.FigureDeleteConfirmPopCMD;
	import org.act.od.impl.commands.FigureDeleteFromCanvasCMD;
	import org.act.od.impl.commands.FigureEditorCloseCMD;
	import org.act.od.impl.commands.FigureFileOpenCMD;
	import org.act.od.impl.commands.FigurePasteToCanvasCMD;
	import org.act.od.impl.commands.FileDeleteCMD;
	import org.act.od.impl.commands.FileRenameCMD;
	import org.act.od.impl.commands.FileSaveAllCMD;
	import org.act.od.impl.commands.FileSaveCMD;
	import org.act.od.impl.commands.FillTheWindowsCMD;
	import org.act.od.impl.commands.FolderDeleteCMD;
	import org.act.od.impl.commands.KnowledgeInputUpdateCMD;
	import org.act.od.impl.commands.KnowledgeOutputUpdateCMD;
	import org.act.od.impl.commands.KnowledgeRelatedPopupCMD;
	import org.act.od.impl.commands.KnowledgeViewUpdateCMD;
	import org.act.od.impl.commands.LoginCheckCMD;
	import org.act.od.impl.commands.LogoutCMD;
	import org.act.od.impl.commands.MoveDownCMD;
	import org.act.od.impl.commands.MoveFigureCMD;
	import org.act.od.impl.commands.MoveLeftCMD;
	import org.act.od.impl.commands.MoveRightCMD;
	import org.act.od.impl.commands.MoveUpCMD;
	import org.act.od.impl.commands.NewBPELFileCMD;
	import org.act.od.impl.commands.NewFileCMD;
	import org.act.od.impl.commands.NewFolderCMD;
	import org.act.od.impl.commands.NewProjectCMD;
	import org.act.od.impl.commands.NodeCreateLinkCMD;
	import org.act.od.impl.commands.OpenSubProcessCMD;
	import org.act.od.impl.commands.PositionKnowledgeViewCMD;
	import org.act.od.impl.commands.ReDoCMD;
	import org.act.od.impl.commands.RefreshCMD;
	import org.act.od.impl.commands.RegisterCMD;
	import org.act.od.impl.commands.RollBackCMD;
	import org.act.od.impl.commands.SelectAllCMD;
	import org.act.od.impl.commands.SelectFigureOfToolPanelCMD;
	import org.act.od.impl.commands.SetConnectionEndPointCMD;
	import org.act.od.impl.commands.SetConnectionStartPointCMD;
	import org.act.od.impl.commands.SignModeCMD;
	import org.act.od.impl.commands.TokenControlCMD;
	import org.act.od.impl.commands.UDDIConfigurationCMD;
	import org.act.od.impl.commands.UDDIRefFromServerCMD;
	import org.act.od.impl.commands.UploadFileCMD;
	import org.act.od.impl.commands.ZoomInCMD;
	import org.act.od.impl.commands.ZoomOutCMD;
	import org.act.od.impl.commands.poolClicCMD;
	import org.act.od.impl.events.AttributeViewAppEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.DesignerMenuBarAppEvent;
	import org.act.od.impl.events.DesignerToolBarAppEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.events.FileNavigatorViewAppEvent;
	import org.act.od.impl.events.KnowledgeViewAppEvent;
	import org.act.od.impl.events.PositionKnowledgeAppEvent;
	import org.act.od.impl.events.ToolPanelAppEvent;
	import org.act.od.impl.events.UDDIRefViewAppEvent;
	
	
	/**
	 * A base class for an application specific front controller,
	 * that is able to dispatch control following particular user gestures to appropriate
	 * command classes.
	 * 
	 * @ author Mengsong
	 * 
	 */
	public class OrDesignerController extends FrontController{
		
		
		/**
		 * Registers all ICommand classes with the Front Controller, against an event name
		 * and listens for events with that name.
		 */
		
		public function OrDesignerController(){
			
			//FigureEditorNavigatorAppEvent
			addCommand(FigureEditorNavigatorAppEvent.FIGURE_EDITOR_CLOSE, FigureEditorCloseCMD);
			
			addCommand(FigureEditorNavigatorAppEvent.BPEL_EDITOR_CLOSE, BPELEditorCloseCMD);
			
			addCommand(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE, ActiveFigureEditorPageCMD);
			
			addCommand(FigureEditorNavigatorAppEvent.ACTIVE_BPELEDITOR_PAGE, ActiveBpelEditorPageCMD);
			
			addCommand(FigureEditorNavigatorAppEvent.CANCLE_CLOSE_FIGURE_EDITOR, CancleCloseFigureEditorCMD);
			
			addCommand(FigureEditorNavigatorAppEvent.CANCLE_CLOSE_BPEL_EDITOR, CancleCloseBPELEditorCMD);
			
			//FigureEditorAppEvent
			//			addCommand(FigureEditorAppEvent.CHANGE_To_XML_VIEW, ChangeToXMLViewCMD);
			
			//			added by mengsong 2010-5-17 12:52:24
			//			for DoubleClick the tab of the navigator to fill the windows
			addCommand(FigureEditorNavigatorAppEvent.FILL_THE_WINDOWS,FillTheWindowsCMD);
			//			for DoubleClick again the tab of the navigator to return the default windows
			addCommand(FigureEditorNavigatorAppEvent.DEFAULT_WINDOWS,DefaultWindowsCMD);
			
			addCommand(FigureEditorAppEvent.CHANGE_To_XML_VIEW, ChangeToBPELViewCMD);
			
			addCommand(FigureEditorAppEvent.BPEL_CREAT, BPELCreatCMD);
			
			
			//AttributeView
			addCommand(AttributeViewAppEvent.ATTRIBUTEVIEW_UPDATE, AttributeViewUpdateCMD);
			
			addCommand(AttributeViewAppEvent.CHANGE_ATTRIBUTE, AttributeChangeCMD);
			
			//Data Attribute
//			addCommand(AttributeViewAppEvent.CHANGE_DATA_ATTRIBUTE, DataAttributeChangeCMD);
			
			addCommand(KnowledgeViewAppEvent.KNOWLEDGEINPUT_UPDATE, KnowledgeInputUpdateCMD);
			addCommand(KnowledgeViewAppEvent.KNOWLEDGEVIEW_UPDATE, KnowledgeViewUpdateCMD);
			addCommand(KnowledgeViewAppEvent.KNOWLEDGEOUTPUT_UPDATE, KnowledgeOutputUpdateCMD);
			addCommand(KnowledgeViewAppEvent.BESTPRACTICE_UPDATE, BestPracticeUpdateCMD); 
			addCommand(KnowledgeViewAppEvent.KNOWLEDGERELATED_POPUP, KnowledgeRelatedPopupCMD);
			
			addCommand(PositionKnowledgeAppEvent.POSITIONKNOWLEDGEVIEW, PositionKnowledgeViewCMD);
			
			//uddi
			addCommand(UDDIRefViewAppEvent.GET_REF_FROM_SERVER, UDDIRefFromServerCMD);
			
			//tool panel
			addCommand(ToolPanelAppEvent.SELECT_FIGURE, SelectFigureOfToolPanelCMD);
			
			//			rollback
			addCommand(DesignerToolBarAppEvent.ROLL_BACK,RollBackCMD);
			
			//			redo
			addCommand(DesignerToolBarAppEvent.RE_DO,ReDoCMD);	
			//			Cooperate
			addCommand(DesignerToolBarAppEvent.Cooperate,CooperateCMD);	
			//			TokenControl
			addCommand(DesignerToolBarAppEvent.TokenControl,TokenControlCMD);				
			
			//			SignMode
			addCommand(DesignerToolBarAppEvent.SIGNMODE,SignModeCMD);			
			
			//			SignMode
			addCommand(DesignerToolBarAppEvent.COMPAREMODE,CompareModeCMD);	
			
			//figureCanvas
			addCommand(FigureCanvasAppEvent.POP_FIGURE_DELETE_CONFIRM, FigureDeleteConfirmPopCMD);
			
			addCommand(FigureCanvasAppEvent.FIGURE_DELETE_FROM_CANVAS, FigureDeleteFromCanvasCMD);
			
			addCommand(FigureCanvasAppEvent.FIGURES_COPY, FigureCopyFromCanvasCMD);
			
			addCommand(FigureCanvasAppEvent.FIGURES_PASTE, FigurePasteToCanvasCMD);
			
			addCommand(FigureCanvasAppEvent.CHANGE_FIGURE_SIZE_IN_CANVAS, ChangeFigureSizeCMD);
			
			addCommand(FigureCanvasAppEvent.CREATE_CONNECTION_START, SetConnectionStartPointCMD);
			
			addCommand(FigureCanvasAppEvent.CREATE_CONNECTION_END, SetConnectionEndPointCMD);
			
			addCommand(FigureCanvasAppEvent.Zoom_In, ZoomInCMD);
			
			addCommand(FigureCanvasAppEvent.Zoom_Out, ZoomOutCMD);
			
			addCommand(FigureCanvasAppEvent.About_Info, AboutInfoCMD);
			
			addCommand(FigureCanvasAppEvent.Select_All,SelectAllCMD);
			
			//Simple Link (_____>)
			addCommand(FigureCanvasAppEvent.Node_Create_Link,NodeCreateLinkCMD);
			
			//Message Flow Connector Link  ( ----> )
			addCommand(FigureCanvasAppEvent.Node_Create_MessageFlow_Link,NodeCreateLinkCMD);
			
			addCommand(FigureCanvasAppEvent.OPEN_SUBPROCESS,OpenSubProcessCMD);
			
			addCommand(FigureCanvasAppEvent.CREATE_NEW_SUBPROCESS,CreateSubProcessCMD);
			
			addCommand(FigureCanvasAppEvent.MOVE_LEFT,MoveLeftCMD);
			
			addCommand(FigureCanvasAppEvent.MOVE_UP,MoveUpCMD);
			
			addCommand(FigureCanvasAppEvent.MOVE_RIGHT,MoveRightCMD);
			
			addCommand(FigureCanvasAppEvent.MOVE_DOWN,MoveDownCMD);
			
			addCommand(FigureCanvasAppEvent.Canvas_Children_Changed,CanvasChildrenChangedCMD);
			addCommand(FigureCanvasAppEvent.Canvas_Node_Selected, CanvasNodeSelectedCMD);
			
			
			addCommand(FigureCanvasAppEvent.POOL_CLIC,poolClicCMD);
			
			addCommand(FigureCanvasAppEvent.FIGURE_MOVE_IN_CANVAS ,MoveFigureCMD);
			//			Refresh
			addCommand(FigureCanvasAppEvent.RE_FRESH,RefreshCMD);
			//			draw sign on figures
			addCommand(FigureCanvasAppEvent.DRAW_SIGN_ON_FIGURES,DrawSignOnFiguresCMD);
//			//			Cooperate Operation
//			addCommand(FigureCanvasAppEvent.CooperateOperation,CoSendMessageCollectionCMD);
			//DesignerMenuBarAppEvent			
			addCommand(DesignerMenuBarAppEvent.NEW_PROJECT, NewProjectCMD);
			
			addCommand(DesignerMenuBarAppEvent.NEW_FOLDER, NewFolderCMD);
			
			addCommand(DesignerMenuBarAppEvent.NEW_FILE, NewFileCMD);
			
			addCommand(DesignerMenuBarAppEvent.FILE_SAVE, FileSaveCMD);
			
			addCommand(DesignerMenuBarAppEvent.FILE_SAVEALL, FileSaveAllCMD);
			
			addCommand(DesignerMenuBarAppEvent.UDDI_CONFIGURATION, UDDIConfigurationCMD);
			
			//DesignerToolBarAppEvent
			addCommand(DesignerToolBarAppEvent.UPLOAD_FILE, UploadFileCMD);
			
			addCommand(DesignerToolBarAppEvent.NEW_PROJECT, NewProjectCMD);
			
			addCommand(DesignerToolBarAppEvent.NEW_FOLDER, NewFolderCMD);
			
			addCommand(DesignerToolBarAppEvent.NEW_FILE, NewFileCMD);
			
			addCommand(DesignerToolBarAppEvent.FILE_SAVE, FileSaveCMD);
			
			addCommand(DesignerToolBarAppEvent.FILE_SAVEALL, FileSaveAllCMD);
			
			addCommand(DesignerToolBarAppEvent.UDDI_CONFIGURATION, UDDIConfigurationCMD);
			
			//			Login check
			addCommand(DesignerToolBarAppEvent.LOGIN_CHECK,LoginCheckCMD);
			
			//			Logout
			addCommand(DesignerToolBarAppEvent.LOGOUT,LogoutCMD);
			
			//			Register
			addCommand(DesignerToolBarAppEvent.REGISTER,RegisterCMD);
			
			//FileNavigatorViewAppEvent
			addCommand(FileNavigatorViewAppEvent.FIGUREFILE_OPEN, FigureFileOpenCMD);
			
			addCommand(FileNavigatorViewAppEvent.BPELFILE_OPEN, BPELFileOpenCMD);
			
			addCommand(FileNavigatorViewAppEvent.NEW_BPEL_FILE, NewBPELFileCMD);
			
			addCommand(FileNavigatorViewAppEvent.FILE_DELETE, FileDeleteCMD);
			
			addCommand(FileNavigatorViewAppEvent.FOLDER_DELETE, FolderDeleteCMD);
			
			addCommand(FileNavigatorViewAppEvent.FILE_RENAME, FileRenameCMD);
	
			
//			for manage the receivie message to dispacth for add,delete or modify
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEADMMANAGE,CoReceiveMessageADMManageCMD);
//			for collecting the message for sending
			addCommand(CooperateOperationEvent.COSENDMESSAGECOLLECTION,CoSendMessageCollectionCMD);
//			for dispatch the detail command for modifying the xml
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEMODIFY,CoReceiveMessageModifyCMD);
//			for dispatch the command for moving figures
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEMOVEFIGURE,CoReceiveMessageMoveFigureCMD);
			//			for dispatch the command for moving left
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEMOVELEFT,CoReceiveMessageMoveLeftCMD);
			//			for dispatch the command for moving right
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEMOVERIGHT,CoReceiveMessageMoveRightCMD);
			//			for dispatch the command for moving up
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEMOVEUP,CoReceiveMessageMoveUpCMD);
			//			for dispatch the command for moving down
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEMOVEDOWN,CoReceiveMessageMoveDownCMD);
			//			for dispatch the command for changing size
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGECHANGESIZE,CoReceiveMessageChangeSizeCMD);
			//			for dispatch the command for graphicIfgure
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEGRAPHICIFIGURE,CoReceiveMessageGraphicIFigureCMD);
			//			for dispatch the command for changing linklabel
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGECHANGELINKLABEL,CoReceiveMessageChangeLinkLabelCMD);
			//			for dispatch the command for deleting figures
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEFIGUREDELETE,CoReceiveMessageFigureDeleteCMD);
			//			for dispatch the command for deleting
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEDELETE,CoReceiveMessageDeleteCMD);
			//			for dispatch the command for deleting links
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGELINKDELETE,CoReceiveMessageLinkDeleteCMD);
			//			for dispatch the command for Adding
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEADD,CoReceiveMessageAddCMD);
			
			//			for dispatch the command for Creating
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGECREATE,CoReceiveMessageCreateCMD);
			
			//			for dispatch the command for Pasting
			addCommand(CooperateOperationEvent.CORECEIVEMESSAGEPASTE,CoReceiveMessagePasteCMD);
			
			
			//WSDL file Uploader Event
			//addCommand( AbstrunctPoolAppEvent)
		}
		
	}
	
}






