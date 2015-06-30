package org.act.od.impl.commands
{
	
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * Handle the process of register 
	 * 
	 * @author Mengsong
	 * 
	 */	
	public class RegisterCMD extends AODCommand
	{
		private var userListLoader:URLLoader;
		private var username:String;
		private var password:String;
		
		public function RegisterCMD()
		{
			super();
		}
		/**
		 * 
		 * @param event { username, password}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			username = event.data.username;
			password = event.data.password;
			if(event.data.username != "" &&event.data.password != ""){
				if(event.data.password==event.data.password2){
					addInformationToXML();
					PopUpManager.removePopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner().titlewindow_reg);
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().currentState = "Login_state";
				}
				else 
					Alert.show("Please insert the same password");
			}
			else
				Alert.show("Please insert the information");
		}
		private function addInformationToXML():void{
			
			var userListRequest:URLRequest = new URLRequest("../database/userdata/userinfor.xml");
			userListLoader = new URLLoader(userListRequest);
			userListLoader.addEventListener("complete",confirmHandler);
		}
		private function confirmHandler(event:Event):void{
			var userListXml:XML = XML(userListLoader.data);
			var newUserNode:XML = <user />;
			var newPassword:XML = <password />;
			newUserNode.@name = username;
			newPassword.@value = password;
			newUserNode.appendChild(newPassword);
			userListXml.appendChild(newUserNode);
			
			var remote :RemoteObject = new RemoteObject();
			remote.destination = "navigator";
			remote.saveFile("../database/userdata/userinfor.xml", userListXml.toString());
			remote.addEventListener(ResultEvent.RESULT, saveFileResult);
			remote.addEventListener(FaultEvent.FAULT, fault);
		}
		
		private function saveFileResult(event :ResultEvent):void{
			Alert.show("Success");
		}
		private function fault(event :FaultEvent):void{
			Alert.show("Remote invoke error: "+event.message);
		}
	}
}