package org.act.od.impl.view
{
	import mx.containers.Box;
	import mx.containers.TabNavigator;
	import mx.controls.Alert;
	
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.other.Localizator;
	import org.act.od.impl.viewhelper.FileNavigatorVH;
	
	/**
	 * The parent panel of fileNavigatorView.
	 * 
	 * @author Quyue
	 * 
	 */
	public class FileNavigator extends TabNavigator
	{
		/**
		 * The source for "navigator" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/navigator.gif")]
		public var navigator :Class;
		public var projectuserNavigatorView:UserNavigatorView;
		
		/**
		 * The source for "uddi" image data binding.
		 */
		[Bindable]
		[Embed(source="/../assets/icon/container/uddi.gif")]
		public var uddi :Class;
		
		private var localizator :Localizator;
		private var fileBox:Box = new Box();
		private var userBox : Box = new Box();
		private var uddiBox:Box = new Box();		
		
		
		private var fileNavigatorView :FileNavigatorView;
		private var fileNavigatorVH :FileNavigatorVH = new FileNavigatorVH( this, FileNavigatorVH.VH_KEY );
		
		public function FileNavigator()
		{
			super();
			localizator = Localizator.getInstance();
			this.percentHeight=100;
			this.percentWidth=100;
			
			projectuserNavigatorView = new UserNavigatorView();
			userBox.addChild(projectuserNavigatorView);
			projectuserNavigatorView.percentHeight = 100;
			projectuserNavigatorView.percentWidth = 100;
			userBox.label = "ProjectUser";
			
//			var positionFileSearch:PositionFileSearch = new PositionFileSearch();
			var positionFileView:PositionFileView = new PositionFileView();
			
			positionFileView.percentWidth = 100;
			positionFileView.percentHeight = 100;
			fileBox.addChild(positionFileView);
			fileBox.visible = false;
			
			var fileNavigatorView:FileNavigatorView = new FileNavigatorView();
//			fileBox.addChild(fileNavigatorView);
			fileNavigatorView.percentHeight = 100;
			fileNavigatorView.percentWidth = 100;
			fileBox.setStyle("borderStyle","solid");
			fileBox.icon = navigator;
			fileBox.label = localizator.getText('filenavigator.label');
			
			
			var uddiView :UDDIReferenceView = new UDDIReferenceView();
			uddiBox.addChild(uddiView);
			uddiView.percentHeight = 100;
			uddiView.percentWidth = 100;
			uddiBox.setStyle("borderStyle","solid");
			uddiBox.icon = uddi;
			uddiBox.label = localizator.getText('uddinavigator.label');
			
			this.addChild(fileBox);
			this.addChild(userBox);
			this.addChild(uddiBox);
		}
		
		
		public function showFileNavigator():void{
			//			this.addChildAt(fileBox,0);
			this.addChild(fileBox);
			this.isHide();
		}
		public function showProjectUserList():void{
			//			this.addChildAt(userBox,1);
			this.addChild(userBox);
			this.isHide();
		}
		public function showUDDINavigator():void{
			//			this.addChildAt(uddiBox,2);
			this.addChild(uddiBox);
			this.isHide();
		}
		public function hideFileNavigator():void{
			this.removeChild(fileBox);
			this.isHide();
		}
		public function hideProjectUserList():void{
			this.removeChild(userBox);
			this.isHide();
		}
		public function hideUDDINavigator():void{
			this.removeChild(uddiBox);
			this.isHide();
		}
		public function getChildNumber():int{
			return this.numChildren;
		}
		private function isHide():void{
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner()!=null)
			{
				if(this.getChildNumber()==0&&
					DesignerMenuBar.lso.data.selected_micro==false)
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().leftBmPanel.width=0;
				else
					OrDesignerModelLocator.getInstance().getOrchestraDesigner().leftBmPanel.width = 210;
			}
		}
	}
}