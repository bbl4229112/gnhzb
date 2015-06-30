package org.act.od.impl.view{
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.external.ExternalInterface;
	import flash.net.SharedObject;
	import flash.net.URLLoader;
	import flash.system.System;
	
	import mx.binding.utils.BindingUtils;
	import mx.containers.Canvas;
	import mx.containers.HDividedBox;
	import mx.containers.Panel;
	import mx.containers.TitleWindow;
	import mx.containers.VDividedBox;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.effects.WipeRight;
	import mx.events.CloseEvent;
	import mx.events.FlexEvent;
	import mx.events.ResizeEvent;
	import mx.managers.PopUpManager;
	import mx.managers.ToolTipManager;
	import mx.messaging.Consumer;
	import mx.messaging.Producer;
	import mx.messaging.events.MessageAckEvent;
	import mx.messaging.events.MessageEvent;
	import mx.messaging.events.MessageFaultEvent;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.states.*;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.commands.RefreshCMD;
	import org.act.od.impl.control.OrDesignerController;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.DesignerToolBarAppEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.events.PositionKnowledgeAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.state.*;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.viewhelper.FileNavigatorVH;
	import org.act.od.impl.viewhelper.UserNavigatorViewVH;
	
	/**
	 * Initialize all views and OrDesignerController.
	 * 
	 */
	public class OrchestraDesigner extends Canvas{
		
		/** controller，must init here  */
		private var odController :OrDesignerController = new OrDesignerController();
		
		/** Menu Bar */
		private var designerMenuBar :DesignerMenuBar;
		
		/** Tool Bar*/
		private var designerToolBar :DesignerToolBar;
		
		/** Attribute Navigator*/
		private var figureAttributeNav :FigureAttributeNavigator;
		
		/** Figure Editor Navigator*/
		private var figureEditorNavigator:EditorNavigator;
		
		/** File Navigator */
		private var fileNavigator : FileNavigator;
		
		private var fileNavigatorView :FileNavigatorView;
		
		/**	Microimage */
		private var microimage:Microimage;
		
		/** MicroImageNode */
		private var microImageNode:MicroImageNode;
		
		public var leftBmPanel :Panel = new Panel();
		public var bottomMdBox :HDividedBox = new HDividedBox();
		public var rightVDBox :VDividedBox = new VDividedBox();
		public var midHDPanel :Canvas = new Canvas();
		public var bottomHDBox :HDividedBox = new HDividedBox();
		
		private var usertxt_log:TextInput = new TextInput();
		private var pswtxt_log:TextInput = new TextInput();
		private var usertxt_reg:TextInput = new TextInput();
		private var pswtxt_reg:TextInput = new TextInput();
		private var pswtxt_reg2:TextInput = new TextInput();		
		public var titlewindow_log:TitleWindow= new TitleWindow();
		public var titlewindow_reg:TitleWindow= new TitleWindow();
		
		//		added by mengsong 2010-7-5 21:35:19
		/**	Save the history of the rollback operations */
		private var _RollBack_arraytemp:Array = new Array();
		
		/**	Save the history of the redo operations */
		private var _ReDo_arraytemp:Array = new Array();
		
		/**	Save the total history of the rollback operations */
		private var _RollBack_TotalArray:Array = new Array();
		
		/**	Save the total history of the rollback operations */
		private var _ReDo_TotalArray:Array = new Array();
		
		/**	Save the number of the tags */
		private var _IDnum:Number = new Number(-1);
		/**	The state of the cooperation */				
		public var _CooperateState:Boolean = new Boolean(false);		
		/**	for the recovery */			
		private var lso:SharedObject = SharedObject.getLocal("recovery_state");		
		/**	for the cooperation */	
		public var producer:Producer;
		public var consumer:Consumer;		
		private var _isProducer:Boolean = new Boolean(false);
		
		private var _cooperatexml:XML;
		
		//		for update userlist
		public var producer_login:Producer;
		public var consumer_login:Consumer;
		private var userDataGridLoader:URLLoader;
		private var userDataGridXml:XML;
		public var userName:String;
		
		//		for update projectuserlist
		public var producer_projectuserlist:Producer;
		public var consumer_projectuserlist:Consumer;	
		
		//		for token icon
		public var tokenIcon:String;
		//		set the number of the token
		public var TOKENNUM:Number = new Number(1);
		
		private var _moveOperationArray:Array = new Array();
		private var _deleteOperationArray:Array = new Array();
		private var _addOperationArray:Array = new Array();
		private var _cooperationMessage:AsyncMessage = new AsyncMessage();
		//		for sign mode
		private var _SignModeArray:Array = new Array();
		private var _SignModeState:Boolean = new Boolean(false);
		private var _DrawConnectionOnlyState:Boolean = new Boolean(false);
		
		//		for  compare mode
		private var _CompareModeState:Boolean = new Boolean(false);
		public var figureEditorModel:FigureEditorModel;
		
		public var _TokenState:Boolean = new Boolean(false);	
		
		
		private var _FileID:String = new String();
		private var _Name:String = new String();
		private var _Path:String = new String();
		private var _BpelID :String = new String();
		private var _OriginalXML:XML = new XML;	
		
		public var levelModel:LevelModel;
		public var level;

		/**
		 * Initialize OrchestraDesigner.
		 */
		public function OrchestraDesigner(){
			super();
			System.useCodePage = true;
			this.percentWidth=100;
			this.percentHeight=100;					
			this.init();
			
			this.init4MoveOperationArray();
			this.init4DeleteOperationArray()
			this.init4AddOperationArray();
			this.Cooperate();
//			this.login();
			//			this.Recovery();			
			//			to be completed...
			//			this.UpdateUserGridList();
			
			this.UpdateProjectUserList();
			//			initalize the tokenstate
			this.TokenState = false;
//			//			for all user list login and out
//			ExternalInterface.addCallback("logout_js",logout);
		//logout in javascript
			ExternalInterface.addCallback("Logout_js",Logout);			
//			test
			this.setStyle("verticalGap","0");
			
		}
		/**
		 * process of logout
		 */		
		public function Logout():String{
			return userName;
		}
		/**
		 * init all operations for Move in cooperating
		 */		
		private function init4MoveOperationArray():void
		{
			this.moveOperationArray.push("move");
			this.moveOperationArray.push("moveleft");
			this.moveOperationArray.push("moveright");
			this.moveOperationArray.push("moveup");
			this.moveOperationArray.push("movedown");
			this.moveOperationArray.push("changesize");
			this.moveOperationArray.push("graphicifigure");
			this.moveOperationArray.push("changelinklabel");
		}
		
		/**
		 * init all operations for Deleting in cooperating
		 */		
		private function init4DeleteOperationArray():void
		{
			this.deleteOperationArray.push("figuredelete");
			this.deleteOperationArray.push("linkdelete");
		}
		
		
		/**
		 * init all operations for Deleting in cooperating
		 */		
		private function init4AddOperationArray():void
		{
			this.addOperationArray.push("create");
			this.addOperationArray.push("paste");
		}

//		public function logout():void{
//			new DesignerToolBarAppEvent(DesignerToolBarAppEvent.LOGOUT,
//				{ username:userName }).dispatch();
//		}
		/**
		 * Initialize all views.
		 */
		private function init():void{
						
			//initilize main menu
//			designerMenuBar = new DesignerMenuBar();
//			this.addChild(designerMenuBar);
//			designerToolBar = new DesignerToolBar();
//			this.addChild(designerToolBar);
			
			//			intilize the tokenicon
			tokenIcon = new String("../assets/icon/toolbar/token.gif");
		
			//initilize figure editor area
			figureEditorNavigator = new EditorNavigator();		
			figureEditorNavigator.percentWidth = 100;
			midHDPanel.name = "midHDPanel";
			midHDPanel.addChild(figureEditorNavigator);
			midHDPanel.setStyle("borderStyle","solid");
			midHDPanel.setStyle("headerHeight","0");
			midHDPanel.setStyle("borderThicknessTop","1");
			midHDPanel.setStyle("borderThicknessLeft","1");
			midHDPanel.setStyle("borderThicknessRight","1");
			midHDPanel.setStyle("backgroundAlpha","0.5");
			
			midHDPanel.percentHeight=75;
			midHDPanel.percentWidth=100;
			midHDPanel.horizontalScrollPolicy = "off";
			midHDPanel.verticalScrollPolicy = "off";
						
			figureAttributeNav = new FigureAttributeNavigator();		
			figureAttributeNav.percentWidth = 70;
			bottomMdBox.name = "bottomMdBox";
			bottomMdBox.percentWidth = 100;
			bottomMdBox.height = 1;
			bottomMdBox.addChild(figureAttributeNav);
			bottomMdBox.maxHeight = 400;
			bottomMdBox.minHeight = 215;

			var midVDBox :VDividedBox = new VDividedBox();
			//layout arrangement
			midVDBox.name = "midVDBox";
			midVDBox.percentHeight=100;
			midVDBox.percentWidth=100;
			midVDBox.setStyle("fontSize", 12);	
			midVDBox.addChild(midHDPanel);
			midVDBox.addChild(bottomMdBox);
			
			//layout arrangement
			bottomHDBox.name = "bottomHDBox";
			
			leftBmPanel.name = "leftBmPanel";
			leftBmPanel.percentHeight = 100;
			leftBmPanel.percentWidth = 40;
			leftBmPanel.setStyle("borderStyle","solid");
			leftBmPanel.setStyle("headerHeight","0");
			leftBmPanel.setStyle("borderThicknessTop","2");
			leftBmPanel.setStyle("borderThicknessLeft","2");
			leftBmPanel.setStyle("borderThicknessRight","2");
			leftBmPanel.setStyle("backgroundAlpha","0.5");
			
			
			fileNavigatorView = new FileNavigatorView();  
			//直接放一个FileNavigatorView（Tree类型）可乎？
			//而不是放一个Box，这样就不会有那个头部了。20120825
			//这两个对象的分工不同，要区分开来。（20120905）
			fileNavigator = new FileNavigator();
			leftBmPanel.addChild(fileNavigator);
			leftBmPanel.setStyle("fontSize", 12);
			leftBmPanel.minWidth = 210;
			leftBmPanel.maxWidth = 600;

			bottomHDBox.resizeToContent = true;
			
			bottomHDBox.addChild(leftBmPanel);
			bottomHDBox.addChild(midVDBox);
			//bottomHDBox.addChild(rightVDBox);
			bottomHDBox.percentHeight=100;
			bottomHDBox.percentWidth=100;
			
			this.checkShowState();
			this.addChild(bottomHDBox);
			this.setStyle("headerHeight","25");
			this.setStyle("fontSize", 14);
//			this.title = "岗位知识体系管理（普通用户查看、使用岗位知识，知识管理专员进行岗位知识体系的管理）";
			
			
			this.setStyle("borderThicknessTop","1");
			this.setStyle("borderThicknessLeft","1");
			this.setStyle("borderThicknessRight","1");
			this.setStyle("backgroundAlpha","0.7");
			
			this.initToolTip();
			
			this.addEventListener(FlexEvent.CREATION_COMPLETE, editorCreationComplete);
		}
		
		private function initToolTip() :void{
			var wiperight:WipeRight=new WipeRight();
			wiperight.duration=600;
			wiperight.repeatCount=1;
			ToolTipManager.showEffect=wiperight;
		}
		
		private function editorCreationComplete(event :FlexEvent):void{
			//To be considered
			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
			OrDesignerModelLocator.getInstance().setOrchestraDesigner(this);
			level = new LevelModel();			
			midHDPanel.addChild(level);
			this.setLevelModel(level);
			level.addEventListener(FlexEvent.CREATION_COMPLETE, levelCreationComplete);
		}
		
		private function levelCreationComplete(event:FlexEvent):void {
			level.x = midHDPanel.width - level.width * 0.1;
			level.y = midHDPanel.height * 0.1;
			level.addEventListener(MouseEvent.MOUSE_OVER, levelMouseOverHandler);
			level.addEventListener(MouseEvent.MOUSE_OUT, levelMouseOutHandler);
			midHDPanel.addEventListener(ResizeEvent.RESIZE, midHDPanelResizeHandler);
		}
		
		private function levelMouseOverHandler(event:MouseEvent):void {
			level.x = midHDPanel.width - level.width;
		}
		
		private function levelMouseOutHandler(event:MouseEvent):void {
			level.x = midHDPanel.width - level.width * 0.1;
		}
		
		private function midHDPanelResizeHandler(event:ResizeEvent):void {
			level.x = midHDPanel.width - level.width * 0.1;
			level.y = midHDPanel.height * 0.1;
		}
		
		private function setLevelModel(value:LevelModel):void{
			this.levelModel = value;
		}
//		for new cooperate
		/**
		 * get the Attribute of Link Figures from the given CanvasXML
		 * and given MovedFiguresIDArray
		 * @author Mengsong
		 * */
		public function getLinkAttribute(moveFigureCanvasXML:XML, moveFiguresidArray:Array):AsyncMessage
		{
			var linkIDArray:Array=new Array();
			var linkAttributeMessage:AsyncMessage=new AsyncMessage();
			
			var xLinkArray_Move:Array=new Array();
			var yLinkArray_Move:Array=new Array();
			var rxLinkArray_Move:Array=new Array();
			var ryLinkArray_Move:Array=new Array();
			var widthLinkArray_Move:Array=new Array();
			var heightLinkArray_Move:Array=new Array();
			var tailxLinkArray_Move:Array=new Array();
			var tailyLinkArray_Move:Array=new Array();
			var headxLinkArray_Move:Array=new Array();
			var headyLinkArray_Move:Array=new Array();
			var idLinkArray_Move:Array=new Array();
			var lineStartPointX_Move:Array=new Array();
			var lineStartPointY_Move:Array=new Array();
			var lineEndPointX_Move:Array=new Array();
			var lineEndPointY_Move:Array=new Array();
			var headPointX_Move:Array=new Array();
			var headPointY_Move:Array=new Array();
			var leftPointX_Move:Array=new Array();
			var leftPointY_Move:Array=new Array();
			var rightPointX_Move:Array=new Array();
			var rightPointY_Move:Array=new Array();
			var sourcePointX_Move:Array=new Array();
			var sourcePointY_Move:Array=new Array();
			var targetPointX_Move:Array=new Array();
			var targetPointY_Move:Array=new Array();
			var sequence0X_Move:Array=new Array();
			var sequence0Y_Move:Array=new Array();
			var sequence1X_Move:Array=new Array();
			var sequence1Y_Move:Array=new Array();
			var linkName_Move:Array = new Array();
			
			for (var i:int=0, k:int=0; i < moveFigureCanvasXML.child("Figure").length(); i++)
			{
				if (moveFigureCanvasXML.Figure[i].@type == "Link")
					for (var j:int=0; j < moveFiguresidArray.length; j++)
						if (moveFigureCanvasXML.Figure[i].@startFigureId == moveFiguresidArray[j] || moveFigureCanvasXML.Figure[i].@endFigureId == moveFiguresidArray[j])
							if ((linkIDArray.length == 0) || (linkIDArray.length > 0 && (linkIDArray.indexOf(moveFigureCanvasXML.Figure[i].@ID) == -1)))
							{
								linkIDArray.push(moveFigureCanvasXML.Figure[i].@ID);
								
								xLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@x);
								yLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@y);
								rxLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@rx);
								ryLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@ry);
								widthLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@width);
								heightLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@height);
								tailxLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@tailX);
								tailyLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@tailY);
								headxLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@headX);
								headyLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@headY);
								linkName_Move.push(moveFigureCanvasXML.Figure[i].@figureName);
								
								idLinkArray_Move.push(moveFigureCanvasXML.Figure[i].@ID);
								lineStartPointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@lineStartPointX);
								lineStartPointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@lineStartPointY);
								lineEndPointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@lineEndPointX);
								lineEndPointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@lineEndPointY);
								headPointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@headPointX);
								headPointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@headPointY);
								leftPointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@leftPointX);
								leftPointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@leftPointY);
								rightPointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@rightPointX);
								rightPointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@rightPointY);
								sourcePointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@sourcePointX);
								sourcePointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@sourcePointY);
								targetPointX_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@targetPointX);
								targetPointY_Move.push(moveFigureCanvasXML.Figure[i].arrow[0].@targetPointY);
								sequence0X_Move.push(moveFigureCanvasXML.Figure[i].router[0].pathpoints[0].point[0].@x);
								sequence0Y_Move.push(moveFigureCanvasXML.Figure[i].router[0].pathpoints[0].point[0].@y);
								sequence1X_Move.push(moveFigureCanvasXML.Figure[i].router[0].pathpoints[0].point[1].@x);
								sequence1Y_Move.push(moveFigureCanvasXML.Figure[i].router[0].pathpoints[0].point[1].@y);
								k++;
							}
			}
			linkAttributeMessage.headers.modify_xLink=xLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_yLink=yLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_rxLink=rxLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_ryLink=ryLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_widthLink=widthLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_heigthLink=heightLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_tailxLink=tailxLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_tailyLink=tailyLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_headxLink=headxLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_headyLink=headyLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_nameLink=linkName_Move.join(",");
			
			linkAttributeMessage.headers.modify_idLink=idLinkArray_Move.join(",");
			linkAttributeMessage.headers.modify_lineStartPointX=lineStartPointX_Move.join(",");
			linkAttributeMessage.headers.modify_lineStartPointY=lineStartPointY_Move.join(",");
			linkAttributeMessage.headers.modify_lineEndPointX=lineEndPointX_Move.join(",");
			linkAttributeMessage.headers.modify_lineEndPointY=lineEndPointY_Move.join(",");
			linkAttributeMessage.headers.modify_headPointX=headPointX_Move.join(",");
			linkAttributeMessage.headers.modify_headPointY=headPointY_Move.join(",");
			linkAttributeMessage.headers.modify_leftPointX=leftPointX_Move.join(",");
			linkAttributeMessage.headers.modify_leftPointY=leftPointY_Move.join(",");
			linkAttributeMessage.headers.modify_rightPointX=rightPointX_Move.join(",");
			linkAttributeMessage.headers.modify_rightPointY=rightPointY_Move.join(",");
			linkAttributeMessage.headers.modify_sourcePointX=sourcePointX_Move.join(",");
			linkAttributeMessage.headers.modify_sourcePointY=sourcePointY_Move.join(",");
			linkAttributeMessage.headers.modify_targetPointX=targetPointX_Move.join(",");
			linkAttributeMessage.headers.modify_targetPointY=targetPointY_Move.join(",");
			linkAttributeMessage.headers.modify_sequence0X=sequence0X_Move.join(",");
			linkAttributeMessage.headers.modify_sequence0Y=sequence0Y_Move.join(",");
			linkAttributeMessage.headers.modify_sequence1X=sequence1X_Move.join(",");
			linkAttributeMessage.headers.modify_sequence1Y=sequence1Y_Move.join(",");
			
			return linkAttributeMessage;
		}
		/**
		 * Send the message for coopeartion
		 * @author Mengsong
		 * */	
		public function sendMessage():void
		{
			producer.send(this.cooperationMessage);
		}
		
		
		/**
		 * add for control the hide and show of the navigators
		 * @author Mengsong
		 * */
		private function checkShowState():void{
			
			//			if((DesignerMenuBar.lso.data.selected==false)||(DesignerMenuBar.lso.data.selected==null)){
			//				bottomMdBox.width = 0;
			//				bottomMdBox.height = 0;	
			//			}
			
			if(DesignerMenuBar.lso.data.selected_file_navigator == false &&
				DesignerMenuBar.lso.data.selected_projectuserlist == false&&
				//				DesignerMenuBar.lso.data.selected_user == false&&
				DesignerMenuBar.lso.data.selected_uddi_navigator==false&&
				DesignerMenuBar.lso.data.selected_micro==false){
				leftBmPanel.width=0;
			}
			else
				leftBmPanel.width=210;
			
			//			if((DesignerMenuBar.lso.data.selected_micro==false)||(DesignerMenuBar.lso.data.selected_micro==null)){
			//				leftBmPanel.width=0;
			//			}
			//			else
			//				leftBmPanel.width=210;
			
			if(DesignerMenuBar.lso.data.selected_file_navigator==false){
				leftBmPanel.width=0;
			}
			if(DesignerMenuBar.lso.data.selected_file_navigator==null){
				DesignerMenuBar.lso.data.selected_file_navigator=true;
			}
			
			//			init the state  of Filenavigator
			if(DesignerMenuBar.lso.data.selected_file_navigator == true)
				fileNavigator.showFileNavigator();
			else 
				fileNavigator.hideFileNavigator();
			
			//			init the state  of ProjectUserlist
			if(DesignerMenuBar.lso.data.selected_projectuserlist == true)
				fileNavigator.showProjectUserList();
			else 
				fileNavigator.hideProjectUserList();
			
			//			init the state  of UDDI
			if(DesignerMenuBar.lso.data.selected_uddi_navigator == true)
				fileNavigator.showUDDINavigator();
			else 
				fileNavigator.hideUDDINavigator();
			
			//			init the state of FigureAttribute
			if(DesignerMenuBar.lso.data.selected_attribute==false&&
				//				DesignerMenuBar.lso.data.selected_bpmndata==false&&
				DesignerMenuBar.lso.data.selected_chatroom==false)
				bottomMdBox.height=1;
			
			//			init the state of FigureAttribute
//			if(DesignerMenuBar.lso.data.selected_attribute==true)
//				figureAttributeNav.showFigureAttribute();
//			else
//				figureAttributeNav.hideFigureAttribute();
			
			//			init the state of ChatRoom
//			if(DesignerMenuBar.lso.data.selected_chatroom==true)
//				figureAttributeNav.showChatRoom();
//			else
//				figureAttributeNav.hideChatRoom();
			
//			figureAttributeNav.showKnowledgeRelated();
			
		}
		
		//		for recovery
		private function Recovery():void{
			
			if(lso.data.recovery_state == null)
			{
				lso.data.recovery_state = 0;
			}
			else if(lso.data.recovery_state == 0)
			{		
				var a:Alert = Alert.show("Abnormal Exit\nNeed to Recovery?"
					,"Confirmation"
					, Alert.YES|Alert.NO,null, confirmHandler);
				
				// modify the style 
				a.setStyle("backgroundColor", 0xffffff);
				a.setStyle("backgroundAlpha", 0.50);
				a.setStyle("borderColor", 0xffffff);
				a.setStyle("borderAlpha", 0.75);
				a.setStyle("color", 0x000000); 
				lso.data.recovery_state = 1; 
			}
			else
			{ 
				lso.data.recovery_state = 0;
			}
		}
		//		for recovery
		private function confirmHandler(event:CloseEvent):void {
			
			if (event.detail == Alert.YES) 
			{
				var lso_recovery:SharedObject = SharedObject.getLocal("nowState");
				var lso_recovery_array:Array = new Array;
				
				var path:String = new String();
				var name:String = new String();
				var fileID:String = new String();
				
				//			bpelID is unsorted
				var BpelID :String = new String("temp");
				var xml:XML = new XML();
				var feNavModel :FigureEditorNavigatorModel;
				var feModel_one :FigureEditorModel;
				
				
				
				lso_recovery_array = lso_recovery.data.nowState;
				path = lso_recovery_array.pop();
				name = lso_recovery_array.pop();
				fileID = lso_recovery_array.pop();
				xml = lso_recovery_array.pop();
				feNavModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				feModel_one = feNavModel.addFigureEditorModel(fileID,BpelID);	
				feModel_one._canvasXML = xml;
				feModel_one.rootFigure.readInformationToFigure(xml,feModel_one.rootModel,feModel_one);	
				feModel_one.updateCanvasXML();
				
				new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
					{ fileID:fileID, filePath:path, 
						fileName:name, figureEditorModel:feModel_one }).dispatch();	
			}
		}
		
		//		for cooperate
		private function Cooperate():void{
			//init for cooperate
			this.cooperateState = false;
			producer = new Producer();		
			producer.destination = "chatRoom";
			producer.addEventListener(MessageAckEvent.ACKNOWLEDGE,ackMessageHandler);
			producer.addEventListener(MessageFaultEvent.FAULT,faultMessageHandler);
			
			consumer = new Consumer();
			consumer.destination = "chatRoom";
			consumer.subscribe();
			consumer.addEventListener(MessageEvent.MESSAGE,messageHandler);
		}
		
		private function messageHandler(event:MessageEvent):void {
			var BpelID:String = new String("temp");	
			var feModelCooperate :FigureEditorModel;
			var feNavModelCooperate :FigureEditorNavigatorModel;
			var feNavModel :FigureEditorNavigatorModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var figureEditorVH:FigureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(event.message.headers.fileID)) as FigureEditorVH;		
			
			feNavModelCooperate = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();		
			feModelCooperate = feNavModel.addFigureEditorModel(event.message.headers.fileID,BpelID);	
//			cooperatexml now is original
			this.cooperatexml = feModelCooperate._canvasXML;
			
//			for resending the message when message is imperfect in translating
			if(this.isProducer==true)
				if(event.message.headers.errorCode!=null)
					if(event.message.headers.errorCode=="1")
						this.sendMessage();
			
			
			if(this.isProducer==false)
			{
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEADMMANAGE,
																{
																	figureXML:this.cooperatexml,CooperationMessage:event.message as AsyncMessage
																}).dispatch();
			}
//				feModelCooperate.rootFigure.readInformationToFigure(this.cooperatexml );//方法已被改，20120906
				feModelCooperate.updateCanvasXML();
			
				figureEditorModel = feModelCooperate;
			
			if(cooperateState == true)
			{
				new FigureCanvasAppEvent(FigureCanvasAppEvent.RE_FRESH,
					{ figureEditorModel:feModelCooperate, fileID:event.message.headers.fileID ,XString:event.message.headers.x,YString:event.message.headers.y,
						WidthString:event.message.headers.width,HeightString:event.message.headers.height,FigureID:event.message.body.figureID,
						DrawConnectionOnlyState:event.message.headers.DrawConnectionOnlyState}).dispatch();
			}
			
			if(CompareModeState==true)
			{
				//				test
				//				Alert.show("Canvas width:"+figureEditorVH.figureEditor._figureCanvas.width+"comparelayer:"+figureEditorVH.figureEditor._figureCanvas.comparelayer.width);
				figureEditorVH.figureEditor._figureCanvas.addCompareLayer();
				figureEditorVH.figureEditor._figureCanvas.comparelayer.horizontalScrollPolicy = "no";
				figureEditorVH.figureEditor._figureCanvas.comparelayer.verticalScrollPolicy = "no";
				
				this.loadGraphFile_CompareMode(figureEditorVH.figureEditor._figureCanvas.comparelayer,
					feModelCooperate,event.message.headers.comparexml);
				//				BindingUtils.bindProperty(figureEditorVH.figureEditor._figureCanvas.comparelayer,"horizontalScrollPosition",figureEditorVH.figureEditor._figureCanvas,"horizontalScrollPosition");
			}
		}
//		private function cooperateXmlOperation(figureXml:XML,message:AsyncMessage):XML
//		{
//			for(var i:int = 0;i<figureXml.child("Figure").length();i++)
//			{
//				if(figureXml.Figure[i].@ID==message.headers.testid)
//				{
//					figureXml.Figure[i].@x = message.headers.testx;
//					figureXml.Figure[i].@y = message.headers.testy;
//					figureXml.Figure[i].@rx = message.headers.testrx;
//					figureXml.Figure[i].@ry = message.headers.testry;
//				}
//			}
//			return figureXml;
//		}
		
		
		
		/**
		 * Initialize the canvas by a graphFile.
		 * @param figureCanvas
		 * @param figureEditorModel
		 *
		 */
		public function loadGraphFile_CompareMode(figureCanvas : FigureCanvas, figureEditorModel : FigureEditorModel,xml:XML) :void
		{
			//			figureCanvas.updateFigure();
			
			if( xml != null ){
				
				//				figureEditorModel.resetSelectedFigure();
				
				var rootFig :ProcessFigure = ProcessFigure(figureEditorModel.rootFigure);
				
//				rootFig.readInformationToFigure(xml);  //不需要了，20120906
				
				//to draw the figures
				var figArray :Array = new Array();
				rootFig.ifiguretoarray(figArray);
				
				var fig:IFigure;
				
				var connection :Array = new Array();
				
				for(var i:int=0; i<figArray.length; i++){
					
					fig = figArray[i] as IFigure;
					
					if(fig is ConnectionFigure){
						connection.push(fig);
					}
					//					maybe can be omit
					AbstractFigure(fig).updateDraw();
					//					AbstractFigure(fig).addEventListener(FlexEvent.CREATION_COMPLETE,CreationComplete);
					figureCanvas.addChild(AbstractFigure(fig));
				}
				figureEditorModel._showingMultiple=1;
				
				//				maybe can be omit too
				for(i=0; i<connection.length; i++){
					ConnectionFigure(connection[i]).autoSetAnchor();
				}
			}
			
		}
		private function CreationComplete(event:FlexEvent):void{
			AbstractFigure(event.currentTarget).updateDraw();
			AbstractFigure(event.currentTarget).removeEventListener(FlexEvent.CREATION_COMPLETE,CreationComplete);
		}
		
		public function login():void{
			//			push all states
			states.push(logState());
			states.push(regState());
			currentState = "Login_state";
			
		}
		public function logState():State
		{
			var newState:State = new State();
			
			//			remove the states
			newState.name = "Login_state";
			var rmc_HD:RemoveChild = new RemoveChild();
			rmc_HD.target = bottomHDBox;
			newState.overrides[0] = rmc_HD;
			
			var rmc_MB:RemoveChild = new RemoveChild();
			rmc_MB.target =  designerMenuBar;
			newState.overrides[1] = rmc_MB;
			
			var rmc_DT:RemoveChild = new RemoveChild();
			rmc_DT.target =  designerToolBar;
			newState.overrides[2] = rmc_DT;
			
			var rmc_titilewindow_reg:RemoveChild = new RemoveChild();
			rmc_titilewindow_reg.target = titlewindow_log;
			newState.overrides[3] = rmc_titilewindow_reg;			
			
			//			add the login state
			var add_Panle:AddChild = new AddChild();
			var userline:HDividedBox = new HDividedBox();
			var pswline:HDividedBox = new HDividedBox();
			var btnline:HDividedBox = new HDividedBox();
			var blanklabel:Label = new Label();
			blanklabel.width = 70;
			userline.setStyle("horizontalGap",5);
			pswline.setStyle("horizontalGap",5);
			btnline.setStyle("horizontalGap",5);
			
			var username:Label = new Label();
			username.text = "User Name:";
			var password:Label = new Label();
			password.text = " PassWord:";				
			
			
			var btnConfirm:Button = new Button();
			btnConfirm.label = "Confirm";
			var btnRegister:Button = new Button();
			btnRegister.label = "Register";
			
			pswtxt_log.displayAsPassword = true;
			
			//				btnConfirm.addEventListener(KeyboardEvent.KEY_DOWN,loginConfirmHandler_keyboard);
			usertxt_log.addEventListener(FlexEvent.ENTER,loginConfirmHandler);
			pswtxt_log.addEventListener(FlexEvent.ENTER,loginConfirmHandler);
			btnConfirm.addEventListener(MouseEvent.CLICK,loginConfirmHandler);
			btnRegister.addEventListener(MouseEvent.CLICK,regeditConfirmHandler);
			
			userline.addChild(username);
			userline.addChild(usertxt_log);
			
			pswline.addChild(password);
			pswline.addChild(pswtxt_log);
			
			btnline.addChild(blanklabel);
			btnline.addChild(btnConfirm);
			btnline.addChild(btnRegister);
			
			titlewindow_log.addChild(userline);
			titlewindow_log.addChild(pswline);
			titlewindow_log.addChild(btnline);
			titlewindow_log.setStyle("horizontalCenter","true");
			
			newState.addEventListener(FlexEvent.ENTER_STATE,openCenter_log);
			newState.overrides[4] = add_Panle;
			
			titlewindow_log.title = "Login";
			
			//				test
			//				titlewindow_log.focusEnabled = true;
			
			return newState;
		}
		
		//		update usergridlist for login
		private function UpdateUserGridList():void{
			producer_login = new Producer();		
			producer_login.destination = "chatRoom2";
			producer_login.addEventListener(MessageAckEvent.ACKNOWLEDGE,ackMessageHandler);
			producer_login.addEventListener(MessageFaultEvent.FAULT,faultMessageHandler);
			
			consumer_login = new Consumer();
			consumer_login.destination = "chatRoom2";
			consumer_login.subscribe();
			consumer_login.addEventListener(MessageEvent.MESSAGE,messageHandler_login);
		}
		
		private function messageHandler_login(event:MessageEvent):void {
			
			//				this.userDataGridXml = event.message.body.content as XML;
			this.dataGridBinding();
		}	
		
		private function dataGridBinding():void{
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/userlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult_dataGrid);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		
		private function openFileResult_dataGrid(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userListXml:XML=XML(str);
			var UNVH:UserNavigatorViewVH = ViewLocator.getInstance().getViewHelper(UserNavigatorViewVH.VH_KEY) as UserNavigatorViewVH
			
			UNVH.userNavigatorView.dataProvider =userListXml.children();
		}
		
		//		!!!!!!!
		//		update projectuserlist for login
		private function UpdateProjectUserList():void{
			producer_projectuserlist = new Producer();		
			producer_projectuserlist.destination = "chatRoom3";
			producer_projectuserlist.addEventListener(MessageAckEvent.ACKNOWLEDGE,ackMessageHandler);
			producer_projectuserlist.addEventListener(MessageFaultEvent.FAULT,faultMessageHandler);
			
			consumer_projectuserlist = new Consumer();
			consumer_projectuserlist.destination = "chatRoom3";
			consumer_projectuserlist.subscribe();
			consumer_projectuserlist.addEventListener(MessageEvent.MESSAGE,messageHandler_projectuserlist);
		}
		
		private function messageHandler_projectuserlist(event:MessageEvent):void {
			
			//				this.userDataGridXml = event.message.body.content as XML;
			this.dataGridBinding_projectuserlist();
		}	
		
		private function dataGridBinding_projectuserlist():void{
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult_projectuserlist);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		
		private function openFileResult_projectuserlist(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userListXml:XML=XML(str);
			var FNVH:FileNavigatorVH = ViewLocator.getInstance().getViewHelper(FileNavigatorVH.VH_KEY) as FileNavigatorVH
			
			FNVH.fileNavigator.projectuserNavigatorView.dataProvider = userListXml.children();
		}
		
		private function openCenter_log(event:FlexEvent):void{
			PopUpManager.addPopUp(titlewindow_log,this,false);
			PopUpManager.centerPopUp(titlewindow_log);
		}
		
		//		check the process of  login (mouse)
		private function loginConfirmHandler(event:Event):void{
			new DesignerToolBarAppEvent(DesignerToolBarAppEvent.LOGIN_CHECK,
				{ username:usertxt_log.text, password:pswtxt_log.text }).dispatch();
		}
		
		//		for reg
		private function regeditConfirmHandler(event:MouseEvent):void{
			currentState = "Regedit_state";
			PopUpManager.removePopUp(titlewindow_log);
		}
		
		public function regState():State
		{
			var newState:State = new State();
			
			//			remove the states
			newState.name = "Regedit_state";
			var rmc_titilewindow_log:RemoveChild = new RemoveChild();
			rmc_titilewindow_log.target = titlewindow_log;
			newState.overrides[0] = rmc_titilewindow_log;
			
			var rmc_HD:RemoveChild = new RemoveChild();
			rmc_HD.target = bottomHDBox;
			newState.overrides[1] = rmc_HD;
			
			var rmc_MB:RemoveChild = new RemoveChild();
			rmc_MB.target =  designerMenuBar;
			newState.overrides[2] = rmc_MB;
			
			var rmc_DT:RemoveChild = new RemoveChild();
			rmc_DT.target =  designerToolBar;
			newState.overrides[3] = rmc_DT;			
			
			//			add the reg state
			var add_Panle:AddChild = new AddChild();
			var userline:HDividedBox = new HDividedBox();
			var pswline:HDividedBox = new HDividedBox();
			var psw2line:HDividedBox = new HDividedBox();
			var btnline:HDividedBox = new HDividedBox();
			var blanklabel:Label = new Label();
			blanklabel.width = 70;
			userline.setStyle("horizontalGap",5);
			pswline.setStyle("horizontalGap",5);
			psw2line.setStyle("horizontalGap",5);
			btnline.setStyle("horizontalGap",5);
			
			var username:Label = new Label();
			username.text = "User Name:";
			var password:Label = new Label();
			password.text = " PassWord:";				
			var password2:Label = new Label();
			password2.text = " Conform:  ";							
			
			var btnRegister:Button = new Button();
			btnRegister.label = "Register";
			
			pswtxt_reg.displayAsPassword = true;
			pswtxt_reg2.displayAsPassword = true;
			
			btnRegister.addEventListener(MouseEvent.CLICK,addInforHandler);
			
			userline.addChild(username);
			userline.addChild(usertxt_reg);
			
			pswline.addChild(password);
			pswline.addChild(pswtxt_reg);
			
			psw2line.addChild(password2);
			psw2line.addChild(pswtxt_reg2);			
			
			btnline.addChild(blanklabel);
			btnline.addChild(btnRegister);
			
			titlewindow_reg.addChild(userline);
			titlewindow_reg.addChild(pswline);
			titlewindow_reg.addChild(psw2line);
			titlewindow_reg.addChild(btnline);
			titlewindow_reg.setStyle("horizontalCenter","true");
			
			newState.addEventListener(FlexEvent.ENTER_STATE,openCenter_reg);
			newState.overrides[4] = add_Panle;
			titlewindow_reg.title = "Regist";
			
			return newState;
		}		
		
		private function openCenter_reg(event:FlexEvent):void{
			PopUpManager.addPopUp(titlewindow_reg,this,false);
			PopUpManager.centerPopUp(titlewindow_reg);
		}	
		//		register
		private function addInforHandler(event:MouseEvent):void{
			new DesignerToolBarAppEvent(DesignerToolBarAppEvent.REGISTER,
				{ username:usertxt_reg.text, password:pswtxt_reg.text ,password2:pswtxt_reg2.text}).dispatch();
		}
		
		
		//		for cooperate
		private function ackMessageHandler(event:MessageAckEvent):void{
			
		}
		private function faultMessageHandler(event:MessageFaultEvent):void{
			this.sendMessage();
		}
		
		//		get&&set
		public function get RollBack_arraytemp():Array{
			return this._RollBack_arraytemp;
		}
//		public function set RollBack_arraytemp(RollBack_arraytemp:Array):void{
//			this._RollBack_arraytemp = RollBack_arraytemp;
//		}
		
		public function get ReDo_arraytemp():Array{
			return this._ReDo_arraytemp;
		}
		public function set ReDo_arraytemp(ReDo_arraytemp:Array):void{
			this._ReDo_arraytemp = ReDo_arraytemp;
		}
		
		public function get RollBack_TotalArray():Array{
			return this._RollBack_TotalArray;
		}
		public function set RollBack_TotalArray(RollBack_TotalArray:Array):void{
			this._RollBack_TotalArray = RollBack_TotalArray;
		}
		
		public function get ReDo_TotalArray():Array{
			return this._ReDo_TotalArray;
		}
		public function set ReDo_TotalArray(ReDo_TotalArray:Array):void{
			this._ReDo_TotalArray = ReDo_TotalArray;
		}		
		
		public function get IDnum():Number{
			return this._IDnum;
		}
		public function set IDnum(IDnum:Number):void{
			this._IDnum = IDnum;
		}
		
		public function get FileID():String{
			return this._FileID;
		}
		public function set FileID(FileID:String):void{
			this._FileID = FileID;
		}
		
		public function get Name():String{
			return this._Name;
		}
		public function set Name(Name:String):void{
			this._Name = Name;
		}
		
		public function get Path():String{
			return this._Path;
		}
		public function set Path(Path:String):void{
			this._Path = Path;
		}
		
		public function get BpelID():String{
			return this._BpelID;
		}
		public function set BpelID(BpelID:String):void{
			this._BpelID = BpelID;
		}
		
		public function get OriginalXML():XML{
			return this._OriginalXML;
		}
		public function set OriginalXML(OriginalXML:XML):void{
			this._OriginalXML = OriginalXML;
		}
		public function get cooperateState():Boolean{
			return this._CooperateState;
		}
		public function set cooperateState(CooperateState:Boolean):void{
			this._CooperateState = CooperateState;
		}				
		public function get TokenState():Boolean{
			return _TokenState;
		}
		public function set TokenState(value:Boolean):void	{
			_TokenState = value;
		}
		public function get SignModeArray():Array
		{
			return _SignModeArray;
		}
		public function set SignModeArray(value:Array):void
		{
			_SignModeArray = value;
		}
		public function get SignModeState():Boolean
		{
			return _SignModeState;
		}
		public function set SignModeState(value:Boolean):void
		{
			_SignModeState = value;
		}
		public function getfileNavigator():FileNavigator{
			return this.fileNavigator;
		}
		public function getfigureAttributeNavigator():FigureAttributeNavigator{
			return this.figureAttributeNav;
		}
		public function showMic():void{
			leftBmPanel.addChild(microimage);
			this.isHide();
		}
		public function hidMic():void{
			leftBmPanel.removeChild(microimage);
			this.isHide();
		}
		private function isHide():void{
			if(DesignerMenuBar.lso.data.selected_file_navigator == false &&
				DesignerMenuBar.lso.data.selected_projectuserlist == false&&
				//				DesignerMenuBar.lso.data.selected_user == false&&
				DesignerMenuBar.lso.data.selected_uddi_navigator==false&&
				DesignerMenuBar.lso.data.selected_micro==false){
				leftBmPanel.width=0;
			}
			else
				leftBmPanel.width=210;
		}
		
		public function get CompareModeState():Boolean
		{
			return _CompareModeState;
		}
		
		public function set CompareModeState(value:Boolean):void
		{
			_CompareModeState = value;
		}
		
		public function get DrawConnectionOnlyState():Boolean
		{
			return _DrawConnectionOnlyState;
		}
		
		public function set DrawConnectionOnlyState(value:Boolean):void
		{
			_DrawConnectionOnlyState = value;
		}

		public function get cooperatexml():XML
		{
			return _cooperatexml;
		}

		public function set cooperatexml(value:XML):void
		{
			_cooperatexml = value;
		}

		public function get isProducer():Boolean
		{
			return _isProducer;
		}

		public function set isProducer(value:Boolean):void
		{
			_isProducer = value;
		}

		public function get moveOperationArray():Array
		{
			return _moveOperationArray;
		}

		public function set moveOperationArray(value:Array):void
		{
			_moveOperationArray = value;
		}

		public function get deleteOperationArray():Array
		{
			return _deleteOperationArray;
		}

		public function set deleteOperationArray(value:Array):void
		{
			_deleteOperationArray = value;
		}

		public function get addOperationArray():Array
		{
			return _addOperationArray;
		}

		public function set addOperationArray(value:Array):void
		{
			_addOperationArray = value;
		}

		public function get cooperationMessage():AsyncMessage
		{
			return _cooperationMessage;
		}

		public function set cooperationMessage(value:AsyncMessage):void
		{
			_cooperationMessage = value;
		}
		
		
	}
}