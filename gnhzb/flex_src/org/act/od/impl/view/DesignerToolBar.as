package org.act.od.impl.view
{
	import flash.events.MouseEvent;
	
	import mx.containers.ControlBar;
	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.controls.LinkButton;
	import mx.controls.VRule;
	
	import org.act.od.impl.model.*;
	import org.act.od.impl.other.Localizator;
	import org.act.od.impl.view.ToggleLinkButtonSkin;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;

	/**
	 * Tool Bar for OrDesigner.
	 * 
	 * @author Quyue
	 * 
	 */
	public class DesignerToolBar extends ControlBar
	{
		/**
		 * The source for "uploadmodelxml" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/uploadmodelxml.gif")]
		public var uploadmodelxml :Class;
		/**
		 * The source for "newproject" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/newproject.gif")]
		public var newproject :Class;
		
		/**
		 * The source for "newfolder" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/newfolder.gif")]
		public var newfolder :Class;
		
		/**
		 * The source for "newfile" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/newfile.gif")]
		public var newfile :Class;
		
		/**
		 * The source for "save" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/save.gif")]
		public var save :Class;
		
		/**
		 * The source for "saveall" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/saveall.gif")]
		public var saveall :Class;
		
		/**
		 * The source for "zoomin" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/zoomin.gif")]
		public var zoomin :Class;
		
		/**
		 * The source for "zoomout" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/zoomout.gif")]
		public var zoomout :Class;
		
		/**
		 * The source for "bpel" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/bpel.gif")]
		public var bpel :Class;
		
		/**
		 * The source for "cut" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/cut.gif")]
		public var cut :Class;
		
		/**
		 * The source for "copy" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/copy.gif")]
		public var copy :Class;
		
		/**
		 * The source for "paste" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/paste.gif")]
		public var paste :Class;
		
		
		/**
		 * The source for "configuration" image data binging.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/uddi.gif")]
		public var configuration :Class;
		
		/**
		 * The source for "rollback" image data binging.
		 */
		[Bindable]
		[Embed(source="../assets/icon/toolbar/rollback.gif")]
		public var rollback :Class;
		
		/**
		 * The source for "redo" image data binging.
		 */
		[Bindable]
		[Embed(source="../assets/icon/toolbar/redo.gif")]
		public var redo :Class;		
		
		/**
		 * The change icon of rollback and redo.
		 */
		[bindable]
		[Embed(source="../assets/icon/toolbar/newrollback.gif")]
		public var newrollback :Class;
		
		[bindable]
		[Embed(source="../assets/icon/toolbar/newredo.gif")]
		public var newredo :Class;		
		
		
		/**
		 * The source for "cooperation" image data binging.
		 */
		[Bindable]
		[Embed(source="../assets/icon/toolbar/cooperate.gif")]
		public var cooperate :Class;			
		
		/**
		 * The source for "tokencontrol" image data binging.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/token.gif")]
		public var tokencontrol :Class;		
		
		/**
		 * The source for "signmodel" image data binging.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/signmode.gif")]
		public var signmode :Class;		
		
		/**
		 * The source for "signmodel" image data binging.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/toolbar/compare.gif")]
		public var comparemode :Class;		
		
		
		private var newProjectButton :Button = new Button();
		private var newFolderButton :Button = new Button();
		private var newFileButton :LinkButton = new LinkButton();
		private var saveButton :LinkButton = new LinkButton();
		private var saveAllButton :LinkButton = new LinkButton();
		private var zoomInButton :LinkButton = new LinkButton();
		private var zoomOutButton :LinkButton = new LinkButton();
		private var bpelButton:LinkButton = new LinkButton();
		private var cutButton :LinkButton = new LinkButton();
		private var copyButton :LinkButton = new LinkButton();
		private var pasteButton :LinkButton = new LinkButton();
		private var configurationButton :LinkButton = new LinkButton();
		public var rollbackButton:LinkButton = new LinkButton();
		public var redoButton:LinkButton = new LinkButton();		
		public var tokencontrolButton:LinkButton = new LinkButton();
		private var cooperateButton:LinkButton = new LinkButton();
		private var signmodeButton:LinkButton = new LinkButton();
		private var comparemodeButton:LinkButton = new LinkButton();
		
		private var uploadModelXMLButton:Button = new Button();
		
		
//		test for flexspy
		private var flexSpyButton:LinkButton = new LinkButton();
		
		private var designerToolBarVH:DesignerToolBarVH;
		private var localizator : Localizator;
		
		/**
		 * Initialize DesignerToolBar.
		 */
		public function DesignerToolBar() {
			super();
			this.designerToolBarVH = new DesignerToolBarVH(this, "DesignerToolBarVH");
			this.localizator = Localizator.getInstance();
			this.percentHeight = 2;
			this.percentWidth = 100;
			
//			test for flexspy
//			flexSpyButton.id="btnFlexSpy";
//			flexSpyButton.label="flexSpy";
//			flexSpyButton.percentHeight = 100;
			
			uploadModelXMLButton.label = "添加业务活动模型文件";
			uploadModelXMLButton.setStyle("icon", uploadmodelxml);
			uploadModelXMLButton.toolTip = "选择要上传到该组织节点的业务活动模型文件";
			
			newProjectButton.label = "新建中心/所";
			newProjectButton.setStyle("icon",newproject);
//			newProjectButton.percentHeight = 100;
//			newProjectButton.width = 20;
//			newProjectButton.toolTip = localizator.getText('toolbar.newproject');
			newProjectButton.toolTip = "新建中心/所";
			
			newFolderButton.label = "新建室";
			newFolderButton.setStyle("icon",newfolder);
//			newFolderButton.percentHeight = 100;
//			newFolderButton.width = 20;
//			newFolderButton.toolTip = localizator.getText('toolbar.newfolder');
			newFolderButton.toolTip = "新建室";
			
			newFileButton.setStyle("icon",newfile);
			newFileButton.percentHeight = 100;
			newFileButton.width = 20;
			newFileButton.toolTip = localizator.getText('toolbar.newfile');
			
			saveButton.setStyle("icon",save);
			saveButton.percentHeight = 100;
			saveButton.width = 20;
			saveButton.toolTip = localizator.getText('toolbar.save');
			
			saveAllButton.setStyle("icon",saveall);
			saveAllButton.percentHeight = 100;
			saveAllButton.width = 20;
			saveAllButton.toolTip = localizator.getText('toolbar.saveall');
			
			zoomInButton.setStyle("icon",zoomin);
			zoomInButton.width = 20;
			zoomInButton.percentHeight = 100;
			zoomInButton.toolTip = localizator.getText('toolbar.zoomin');
			
			zoomOutButton.setStyle("icon",zoomout);
			zoomOutButton.width = 20;
			zoomOutButton.percentHeight = 100;
			zoomOutButton.toolTip = localizator.getText('toolbar.zoomout');
			
			bpelButton.setStyle("icon",bpel);
			bpelButton.width = 20;
			bpelButton.percentHeight = 100;
			bpelButton.toolTip = localizator.getText('toolbar.createbpel');
			
			cutButton.setStyle("icon",cut);
			cutButton.width = 20;
			cutButton.percentHeight = 100;
			cutButton.toolTip = localizator.getText('toolbar.cut');
			
			copyButton.setStyle("icon",copy);
			copyButton.width = 20;
			copyButton.percentHeight = 100;
			copyButton.toolTip = localizator.getText('toolbar.copy');
			
			pasteButton.setStyle("icon",paste);
			pasteButton.width = 20;
			pasteButton.percentHeight = 100;
			pasteButton.toolTip = localizator.getText('toolbar.paste');
			
			configurationButton.setStyle("icon",configuration);
			configurationButton.width = 20;
			configurationButton.percentHeight = 100;
			configurationButton.toolTip = localizator.getText('toolbar.configuration');
			
			//			rollback
			rollbackButton.setStyle("icon",rollback);
			rollbackButton.percentHeight = 100;
			rollbackButton.width = 20;
			rollbackButton.toolTip = localizator.getText('toolbar.rollback');
			
			//			redo
			redoButton.setStyle("icon",redo);
			redoButton.percentHeight = 100;
			redoButton.width = 20;
			redoButton.toolTip = localizator.getText('toolbar.redo');
			
			//			cooperate
			cooperateButton.setStyle("icon",cooperate);
			cooperateButton.percentHeight = 100;
			cooperateButton.width = 20;
			cooperateButton.toolTip = localizator.getText('toolbar.cooperate');		
			cooperateButton.toggle = true;
			cooperateButton.addEventListener(MouseEvent.CLICK,clickHandler);
			cooperateButton.setStyle("skin", ToggleLinkButtonSkin);
			
			//			tokencontrol
			tokencontrolButton.setStyle("icon",tokencontrol);
			tokencontrolButton.percentHeight = 100;
			tokencontrolButton.width = 20;
			tokencontrolButton.toolTip = localizator.getText('toolbar.token');		
			tokencontrolButton.toggle = true;
			tokencontrolButton.addEventListener(MouseEvent.CLICK,clickHandler_token);
			tokencontrolButton.setStyle("skin", ToggleLinkButtonSkin);			
			tokencontrolButton.enabled = false;
			
			//			signmode
			signmodeButton.setStyle("icon",signmode);
			signmodeButton.percentHeight = 100;
			signmodeButton.width = 20;
			signmodeButton.toolTip = localizator.getText('toolbar.signmode');		
			signmodeButton.toggle = true;
			signmodeButton.addEventListener(MouseEvent.CLICK,clickHandler_signmode);
			signmodeButton.setStyle("skin", ToggleLinkButtonSkin);
			//			tokencontrolButton.enabled = false;
			
			//			signmode
			comparemodeButton.setStyle("icon",comparemode);
			comparemodeButton.percentHeight = 100;
			comparemodeButton.width = 20;
			comparemodeButton.toolTip = localizator.getText('toolbar.comparemode');		
			comparemodeButton.toggle = true;
			comparemodeButton.addEventListener(MouseEvent.CLICK,clickHandler_comparemode);
			comparemodeButton.setStyle("skin", ToggleLinkButtonSkin);
			//			tokencontrolButton.enabled = false;
			
			//layout
			var fileBox :HBox = new HBox();
			fileBox.percentHeight = 100;
			//			fileBox.width = 120;
			
			fileBox.addChild(newProjectButton);
			fileBox.addChild(newFolderButton);
			fileBox.addChild(uploadModelXMLButton);
//			fileBox.addChild(newFileButton);
//			fileBox.addChild(saveButton);
			
			//			var saveBox :HBox = new HBox();
			//			saveBox.percentHeight = 100;
			//			saveBox.width = 50;
			//			saveBox.addChild(saveButton);
			////			saveBox.addChild(saveAllButton);
			
			var zoomBox :HBox = new HBox();
			zoomBox.percentHeight = 100;
			zoomBox.width = 50;
			zoomBox.addChild(zoomInButton);
			zoomBox.addChild(zoomOutButton);
			
			var RollbackBox :HBox = new HBox();
			RollbackBox.percentHeight = 100;
			RollbackBox.width = 50;
			RollbackBox.addChild(rollbackButton);
			RollbackBox.addChild(redoButton);			
			
			//			cooperate functions
			var CooperationBox :HBox = new HBox();
			CooperationBox.percentHeight = 100;
			//			CooperationBox.width = 120;
			CooperationBox.addChild(cooperateButton);
			CooperationBox.addChild(tokencontrolButton);	
			CooperationBox.addChild(signmodeButton);
			CooperationBox.addChild(comparemodeButton);				
			
			
			
			var editBox :HBox = new HBox();
			editBox.percentHeight = 100;
			editBox.width = 80;
			editBox.addChild(cutButton);
			editBox.addChild(copyButton);
			editBox.addChild(pasteButton);
			
			var vrule1 :VRule = new VRule();
			vrule1.percentHeight = 100;
			
			var vrule2 :VRule = new VRule();
			vrule2.percentHeight = 100;
			
			var vrule3 :VRule = new VRule();
			vrule3.percentHeight = 100;
			
			var vrule4:VRule = new VRule();
			vrule4.percentHeight = 100;
			
			var vrule5:VRule = new VRule();
			vrule5.percentHeight = 100;
			
			this.setStyle("borderStyle", "solid");
			
			this.addChild(fileBox);
//			this.addChild(vrule2);
//			this.addChild(editBox);
			//			rollback&&redo
//			this.addChild(RollbackBox);
//			this.addChild(vrule3);
//			this.addChild(zoomBox);
//			this.addChild(vrule4);
//			this.addChild(CooperationBox);
//			this.addChild(vrule5);
//			this.addChild(bpelButton);
//			this.addChild(vrule1);
//			this.addChild(configurationButton);
			//			test for flexspy
//			this.addChild(flexSpyButton);
			
			this.initEventListener();
		}
		
		//		cooperate
		private function clickHandler(event:MouseEvent):void{
			if(cooperateButton.selected==true)
				cooperateButton.setStyle("","");
			else
				cooperateButton.setStyle("icon",cooperate);
			
		}
		
		//		tokencontrol
		private function clickHandler_token(event:MouseEvent):void{
			if(tokencontrolButton.selected==true)
				tokencontrolButton.setStyle("","");
			else
				tokencontrolButton.setStyle("icon",tokencontrol);
			
		}		
		
		//		signmode
		private function clickHandler_signmode(event:MouseEvent):void{
			if(cooperateButton.selected==true)
				cooperateButton.setStyle("","");
			else
				cooperateButton.setStyle("icon",cooperate);
			
		}
		
		//		comparemode
		private function clickHandler_comparemode(event:MouseEvent):void{
			if(cooperateButton.selected==true)
				cooperateButton.setStyle("","");
			else
				cooperateButton.setStyle("icon",cooperate);
			
		}
		/**
		 * Initialize the listeners of all button.
		 */
		private function initEventListener():void{
			
			this.uploadModelXMLButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.uploadModelXMLHander);
			
			this.newProjectButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.newProjectHandler);
			this.newFolderButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.newFolderHandler);
			this.newFileButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.newFileHandler);
			
			this.saveButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onSaveHandler);
			this.saveAllButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onSaveAllHandler);
			
			this.copyButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onCopyHandler);
			this.pasteButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onPasteHandler);
			this.cutButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onCutHandler);
			
			this.zoomInButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onZoomInHandler);
			this.zoomOutButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onZoomOutHandler);
			
			this.configurationButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onDUUIConfigurationHandle);
			
			this.rollbackButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.RollBackHandler);
			this.redoButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.ReDoHandler);
			this.cooperateButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.CooperateHandler);	
			this.tokencontrolButton.addEventListener(MouseEvent.CLICK,designerToolBarVH.TokenControlHandler);
			this.signmodeButton.addEventListener(MouseEvent.CLICK,designerToolBarVH.SignModeHandler);
			this.comparemodeButton.addEventListener(MouseEvent.CLICK,designerToolBarVH.CompareModeHandler);
			
			this.bpelButton.addEventListener(MouseEvent.CLICK, designerToolBarVH.onBpelHandler);
			
//			test for flexspy
			this.flexSpyButton.addEventListener(MouseEvent.CLICK,designerToolBarVH.flexSpyHandler);
		}
	}
}