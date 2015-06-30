package org.act.od.impl.commands
{
	
	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;
	/**
	 * control the token while in the process of cooperative modelling
	 * 
	 * @author Mengsong
	 * 
	 */
	
	public class TokenControlCMD extends AODCommand
	{
		private var username:String;
		private var tokennum:Number;
		
		public function TokenControlCMD()
		{
			super();
			
		}
		
		/**
		 * only if the user enter the process of cooperation,can the TokenControlCMD be activated 
		 * only if the user have the token,can he control the process of the whole cooperative modelling
		 * 
		 * @param 
		 * 
		 */
		
		override public function execute(event :OrDesignerEvent) :void{
			username = OrDesignerModelLocator.getInstance().getOrchestraDesigner().userName;
			tokennum = 0;
			
			this.countToken();									
		}
		
		//		count the number of the token now exists
		private function countToken():void{
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileForCountToken);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		private function openFileForCountToken(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var projectUserListXml:XML=XML(str);
			
			for(var i:int = 0; projectUserListXml.user[i]!=null;i++){
				if(String(projectUserListXml.user[i].token)==
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().tokenIcon)
					tokennum++;
			}
			this.isGetTokenEnable();
		}
		private function isGetTokenEnable():void{
			
			var DTVH:DesignerToolBarVH = ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
				as DesignerToolBarVH
			
			if((OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState == false)&&
				(DTVH.designerToolBar.tokencontrolButton.selected == true))
			{
				if(tokennum < OrDesignerModelLocator.getInstance().getOrchestraDesigner().TOKENNUM)
				{
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState = true;
					this.setTokenToUser();
//					for ensure the producer role
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().isProducer = true;
				}	
				else 
					Alert.show("The key has not been released");
			}
			else if((OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState == true)&&
				(DTVH.designerToolBar.tokencontrolButton.selected == false))
			{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState = false;
				this.DissetTokenToUser();
//				for enunsure the producer role
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().isProducer = false;
			}
		}
		
		
		private function setTokenToUser():void{
			
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileForSetToken);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
		}
		
		private function openFileForSetToken(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var projectUserListXml:XML=XML(str);
			projectUserListXml.user.(name == username).token 
				=  OrDesignerModelLocator.getInstance().getOrchestraDesigner().tokenIcon;
			
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
		
		private function DissetTokenToUser():void{
			//			read the  userinfor.xml
			//			test
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
		
		
		
		private function saveFileResult(event :ResultEvent):void{
			//			Alert.show("Update userDatagrid success!!");
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}
