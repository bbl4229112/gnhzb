package org.act.od.impl.commands
{
	
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.binding.utils.*;
	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FileNavigatorView;
	import org.act.od.impl.viewhelper.UserNavigatorViewVH;
	/**
	 * execute the Process of logout 
	 * 
	 * @author Mengsong
	 * 
	 */	
	public class LogoutCMD extends AODCommand
	{
		private var userListLoader:URLLoader;
		private var userDataGridLoader:URLLoader;
		private var username:String;
		private var password:String;
		private var newUser:XML;
		
		public function LogoutCMD()
		{
			super();
		}
		/**
		 * Read the registed userinformation first,then logout the user from the userlist 
		 * update the userlist
		 * 
		 * @param event { username }
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			username = event.data.username;
			
			//			do sth. with the xml file
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.logOut("../database/userlist/projectuserlist.xml",username);
			remote.addEventListener(ResultEvent.RESULT, openFileResult_modify);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
		}
		private function openFileResult_modify(event :ResultEvent):void{
			//			for update userListDataGrid
			var message:AsyncMessage = new AsyncMessage();   
			message.body.content = "";
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer_login.send(message);
		}	
		
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}