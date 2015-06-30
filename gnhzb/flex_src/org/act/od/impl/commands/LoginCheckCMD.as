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
	 * Check the Process of login 
	 * 
	 * @author Mengsong
	 * 
	 */	
	
	public class LoginCheckCMD extends AODCommand
	{
		private var userListLoader:URLLoader;
		private var userDataGridLoader:URLLoader;
		private var username:String;
		private var password:String;
		private var newUser:XML;
		private var loginEnable:Boolean;
		
		public function LoginCheckCMD()
		{
			super();
		}
		/**
		 * Read the registed userinformation first,then check the userlist 
		 * if approved,enter the system
		 * 
		 * @param event { username, password}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			username = event.data.username;
			password = event.data.password;
			
			loginEnable = true;
			this.checkLogin();
			
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userdata/userinfor.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
		}
		private function checkLogin():void{
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/projectuserlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult_checkEnable);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		
		private function openFileResult_checkEnable(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userListXml:XML=XML(str);
			
			for(var i:int = 0; userListXml.user[i]!=null;i++){
				if(String(userListXml.user[i].name)==String(username))
					loginEnable = false;
			}
		}		
		
		private function openFileResult(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userListXml:XML=XML(str);
			if((userListXml.user.(@name==username).password.@value==password)
				&&loginEnable == true)
			{
				//				this.dataGridBinding();
				//			trans the username to Orchestra.userName
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().userName=username;
				
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().currentState = "";	
				PopUpManager.removePopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner().titlewindow_log);	
			}
			else
				Alert.show("User is not exist or Password is worng or the person have already login","Error");					
		}
		
		/**
		 *  Read the userdatagrid first,then add the add the new user to the userdatagrid,
		 *  at the end, put the xmllist to the provider and updata the userdatagrid 
		 */
		private function dataGridBinding():void{
			
			newUser = new XML(<user />);
			newUser.name =username ;
			newUser.token = " ";
			
			//			read the  userinfor.xml
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.openFile("../database/userlist/userlist.xml");
			remote.addEventListener(ResultEvent.RESULT, openFileResult_dataGrid);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
		}
		
		private function openFileResult_dataGrid(event :ResultEvent):void{
			var str:String=event.result.valueOf();
			var userDataGridXml:XML=XML(str);
			
			var UNVH:UserNavigatorViewVH = ViewLocator.getInstance().getViewHelper(UserNavigatorViewVH.VH_KEY) as UserNavigatorViewVH
			
			userDataGridXml.appendChild(newUser);
			
			//			save the userdata to the userlist by remoting
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.saveFile("../database/userlist/userlist.xml", userDataGridXml.toString());
			remote.addEventListener(ResultEvent.RESULT, saveFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
			
			
			UNVH.userNavigatorView.dataProvider =userDataGridXml.children();
			
			//			for update userListDataGrid
			var message:AsyncMessage = new AsyncMessage();   
			message.body.content = userDataGridXml;
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer_login.send(message);
		}		
		
		private function saveFileResult(event :ResultEvent):void{
			//			Alert.show("Update userDatagrid success!!");
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
		
	}
}