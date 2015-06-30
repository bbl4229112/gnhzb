package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.messaging.events.MessageEvent;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.states.*;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.state.*;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;
	/**
	 * @author mengsong
	 */	
	
	public class CooperateCMD extends AODCommand
	{
		private var str :String = new String();
		private var username:String;
		public function CooperateCMD()
		{
			super();
		}
		override public function execute(event :OrDesignerEvent) :void
		{
			username = OrDesignerModelLocator.getInstance().getOrchestraDesigner().userName;
			
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState == true)
			{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState = false;
				this.setTokenControlDisabled();
				var DTVH:DesignerToolBarVH = ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
					as DesignerToolBarVH
				DTVH.designerToolBar.tokencontrolButton.selected = false;
				//				Alert.show("Cooperate Close");
			}
			else
			{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState = true;
				this.setTokenControlEnabled();
				//				Alert.show("Cooperate Open");
			}
			
		}
		private function setTokenControlEnabled():void{
			var DTVH:DesignerToolBarVH = ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
				as DesignerToolBarVH
			
			DTVH.designerToolBar.tokencontrolButton.enabled = true;
			//			set for icon changing right
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState = false
		}
		private function setTokenControlDisabled():void{
			var DTVH:DesignerToolBarVH = ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
				as DesignerToolBarVH
			
			DTVH.designerToolBar.tokencontrolButton.enabled = false;
			
			this.DissetTokenToUser();
		}
		
		private function DissetTokenToUser():void{
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileForDisSetToken);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		
		private function openFileForDisSetToken(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var projectUserListXml:XML=XML(str);
			projectUserListXml.user.(name == username).token 
				=  "";
			
			//			save the userdata to the userlist by remoting
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.saveFile("../database/userlist/projectuserlist.xml", projectUserListXml.toString());
			remote.addEventListener(ResultEvent.RESULT, saveFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
			this.SendMessage();
			
			////			for update userListDataGrid
			//			var message:AsyncMessage = new AsyncMessage();   
			//			message.body.content = "";
			//			OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer_projectuserlist.send(message);
		}
		private function SendMessage():void{
			//			for update userListDataGrid
			var message:AsyncMessage = new AsyncMessage();   
			message.body.content = "";
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer_projectuserlist.send(message);
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		
		private function saveFileResult(event :ResultEvent):void{
			//			Alert.show("Update userDatagrid success!!");
		}
	}
}