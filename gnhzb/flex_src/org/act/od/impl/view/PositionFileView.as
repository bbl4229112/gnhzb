package org.act.od.impl.view
{
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuBuiltInItems;
	import flash.ui.ContextMenuItem;
	
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	import mx.controls.Tree;
	import mx.controls.listClasses.IListItemRenderer;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.impl.events.DesignerToolBarAppEvent;
	
	public class PositionFileView extends Tree
	{
		private var xmlFileId:String;
		private var xmlFileName:String;
		
		[Bindable]
		[Embed(source="/../assets/icon/accordion/folder.gif")]
		private static var folderIcon:Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/container/xml.jpg")]
		public var xmlFile :Class;
		
		private var openXMLItem :ContextMenuItem = new ContextMenuItem("打开");
		
		public function PositionFileView()
		{
			super();
			var remote:RemoteObject = new RemoteObject();
			remote.destination = "positionFile";
			remote.getTreeRoots();
			remote.addEventListener(ResultEvent.RESULT, listTreeRoots);
			
			this.doubleClickEnabled = true;
			this.iconFunction = iconFun;
//			this.showDataTips = true;
			this.dataTipFunction = tipFun;
			
//			this.itemRenderer = itemRen;
			
			var contextMenu: ContextMenu = new ContextMenu();			
			contextMenu.hideBuiltInItems();			
			contextMenu.customItems.push(openXMLItem);
//			this.contextMenu = contextMenu;
			
			this.initEventListener();
			
		}
		
		private function listTreeRoots(event:ResultEvent):void {
			var xml:XML = new XML(event.result.toString());
			var xmlList:XMLList = xml.elements();
			this.dataProvider = xmlList;
			this.labelField = "@foddersortName";
		}	
		
		private function initEventListener():void{
			this.addEventListener(MouseEvent.DOUBLE_CLICK, tree_itemDoubleClick);
		}
		
		private function tree_itemDoubleClick(event:MouseEvent):void {
			var node:XML = this.selectedItem as XML;
			var isOpen:Boolean = this.isItemOpen(node);
			if(node != null) {
				var name:String = node.@foddersortName;
				if(name.substring(name.lastIndexOf("."),name.length) == ".xml") {
					this.fileOpen();
				} else {
					this.expandItem(node, !isOpen);					
				}
			}
		}
		
		private function fileOpen():void {
			xmlFileId = this.selectedItem.@foddersortId;
			xmlFileName = this.selectedItem.@foddersortName;
			var remote:RemoteObject = new RemoteObject();
			remote.destination = "knowledgeSource";
			remote.getFileByPath(Number(this.xmlFileId));
			remote.addEventListener(ResultEvent.RESULT, fileOpenResultHandler);
		}
		
		private function fileOpenResultHandler(event:ResultEvent):void {			
			var str:String=event.result.toString();
			var xml:XML=XML(str);
			new DesignerToolBarAppEvent(DesignerToolBarAppEvent.NEW_FILE,
				{fileName:this.xmlFileName, fileType:null ,fileCategory:null,sourceXML:xml,fileId:this.xmlFileId}).dispatch();
		}
		
		private function iconFun(item:Object):Class{
			var node :XML = item as XML;
			var name:String = node.@foddersortName;
			if(name.substring(name.lastIndexOf("."),name.length) == ".xml") {
				return xmlFile;
			} else {
				return folderIcon;
			}
		}
		
		private function tipFun(item:Object):String {
			return item.@foddersortName;
		}
		
		private function itemRen(item:Object):Class{
			
			var node :XML = item as XML;
			var name:String = node.@foddersortName;
			if(name.substring(name.lastIndexOf("."),name.length) == ".xml") {
				return xmlFile;
			} else {
				return folderIcon;
			}
		}
	}
}