package org.act.od.impl.view
{
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.ui.Keyboard;
	import flash.utils.ByteArray;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.containers.Form;
	import mx.containers.FormItem;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.events.FlexEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.impl.events.DesignerToolBarAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;

	public class PositionFileSearch extends Canvas
	{
		[Bindable]
		[Embed(source="/../assets/icon/container/search.gif")]
		public var searchIcon :Class;		
		[Bindable]
		[Embed(source="/../assets/icon/container/xml.jpg")]
		public var xmlFile :Class;
		
		public static var searchKey:TextInput;		
		public var xmlFileId:String;
		public var xmlFilePath:String;
		public var xmlFileName:String;
		public var xmlFileCategory:String;
		public var _figureEditorModel:FigureEditorModel;
		public var _fileCategory:String;
		
		public function PositionFileSearch()
		{
			searchKey = new TextInput();
			searchKey.x = 0;
			searchKey.y = 0;
			searchKey.width = 136;
			searchKey.height = 24;
			var searchButton:Button = new Button();
			searchButton.x = 137;
			searchButton.y = 0;
			searchButton.width = 64;
			searchButton.height = 24;
			searchButton.label = "Go";
			searchButton.setStyle("icon", searchIcon);
			searchButton.addEventListener(MouseEvent.CLICK, SearchKnowledge);
			
			this.addChild(searchKey);
			searchKey.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDownHandler);
			searchKey.text = "查找岗位知识……";
			searchKey.addEventListener(MouseEvent.CLICK, searchKeyClick_Handler);
			this.addChild(searchButton);			
		}
		
		protected function onKeyDownHandler(event:KeyboardEvent):void {
			if(event.keyCode == Keyboard.ENTER) {
				this.SearchKnowledge(event);
			}
		}
		
		protected function searchKeyClick_Handler(event:MouseEvent):void {
			searchKey.text = "";
		}
		
		protected function SearchKnowledge(event:Event):void {
			if(searchKey.text == "") {
				Alert.show("请输入搜索词！", "提示");
			} else {
				var remote:RemoteObject = new RemoteObject();
				remote.destination = "knowledgeSource";
//				remote.getKnowledgeByTitle(searchKey.text);
				remote.getKnowledgeByTitle("唯一");				
				remote.addEventListener(ResultEvent.RESULT, resultHandler);
			}
		}
		
		protected function resultHandler(event:ResultEvent):void {
			var resultCanvas:Canvas = new Canvas();
			resultCanvas.x = 0;
			resultCanvas.y = 24;
			this.addChild(resultCanvas);
			var label:Label = new Label();
			var result:ArrayCollection = event.result as ArrayCollection;
			if(result.length == 0) {
				label.text = "     没有找到记录，请重新检索。"
				resultCanvas.addChild(label);
			} else {
				resultCanvas.removeAllChildren();  //这句没起作用？20120911.
				label.text = "“" + searchKey.text + "”的搜索结果：";
//				resultCanvas.addChild(label);
				for(var i=0;i<result.length;i++) {
					var titleName:String = result.getItemAt(i).titleName;
					var titleButton:Button = new Button();
					titleButton.label = titleName;
					titleButton.setStyle("icon", xmlFile);
					resultCanvas.addChild(titleButton);
					
					var titleLabel:Label = new Label();
					titleLabel.text = titleName;
//					resultCanvas.addChild(titleLabel);										
					
					var feNavModel :FigureEditorNavigatorModel =
						OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();					
					this.xmlFileId = result.getItemAt(i).id + "";
					this.xmlFileName = result.getItemAt(i).titleName;
					this.xmlFilePath = result.getItemAt(i).knowledgeSourceFilePath;
					this._figureEditorModel = feNavModel.addFigureEditorModel(this.xmlFileId, this.xmlFileId);
					var remote:RemoteObject = new RemoteObject();
					remote.destination = "knowledgeSource";
//					remote.getFileByPath(this.xmlFilePath);
					remote.getFileByPath(result.getItemAt(i).id);
					remote.addEventListener(ResultEvent.RESULT, fileOpenResultHandler);					
				}
			}
		}
		
		private function fileOpenResultHandler(event:ResultEvent):void {
			
			var str:String=event.result.toString();
//			var bytes:ByteArray = ByteArray(event.result);
//			var xmlStr:String = bytes.readMultiByte(bytes.length, "utf-8");
//			trace(xmlStr);
			var xml:XML=XML(str);
			new DesignerToolBarAppEvent(DesignerToolBarAppEvent.NEW_FILE,
				{fileName:this.xmlFileName, fileType:null ,fileCategory:null,sourceXML:xml,fileId:this.xmlFileId}).dispatch();
		}
		
		public function getXMLFileId():String {
			return this.xmlFileId;
		}
	}
}