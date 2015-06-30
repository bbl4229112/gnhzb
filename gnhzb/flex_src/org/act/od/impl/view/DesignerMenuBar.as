package org.act.od.impl.view{
	
	import flash.net.SharedObject;
	
	import mx.collections.XMLListCollection;
	import mx.controls.MenuBar;
	import mx.events.MenuEvent;
	
	import org.act.od.impl.other.Localizator;
	import org.act.od.impl.viewhelper.DesignerMenuBarVH;
	
	/**
	 * Menu Bar for OrDesigner.
	 * 
	 * @author Quyue
	 * 
	 */
	public class DesignerMenuBar extends MenuBar{
		
		private var designerMenuBarVH :DesignerMenuBarVH;
		
		/**
		 * Share Object to store information
		 */
		public static const lso :SharedObject = SharedObject.getLocal("Proprities");
		
		
		/**
		 * Initialize DesignerMenuBar.
		 */
		public function DesignerMenuBar(){
			//view component
			super();
			
			this.labelField = "@label";
			this.dataProvider = new XMLListCollection(menubarXML);
			this.percentWidth = 100;
			designerMenuBarVH = new DesignerMenuBarVH(this, DesignerMenuBarVH.VH_KEY);
			this.initEventListener();
			
		}
		private function initEventListener():void{
			
			var localizator : Localizator = Localizator.getInstance();
			for each(var item : XML in menubarXML){
				var labelText : String = null;
				if(item.@id == "0") {
					labelText = localizator.getText('menubar.file');
					item.@label = labelText;
				}
				else if(item.@id == "1") {
					labelText = localizator.getText('menubar.file.project');
					item.@label = labelText;
				}
				else if(item.@id == "2") {
					labelText = localizator.getText('menubar.file.folder');
					item.@label = labelText;
				}
				else if(item.@id == "3") {
					labelText = localizator.getText('menubar.file.file');
					item.@label = labelText;
				}
				else if(item.@id == "4") {
					labelText = localizator.getText('menubar.file.close');
					item.@label = labelText;
				}
				else if(item.@id == "5") {
					labelText = localizator.getText('menubar.file.closeall');
					item.@label = labelText;
				}
				else if(item.@id == "6") {
					labelText = localizator.getText('menubar.file.save');
					item.@label = labelText;
				}
				else if(item.@id == "7") {
					labelText = localizator.getText('menubar.file.saveall');
					item.@label = labelText;
				}
				else if(item.@id == "8") {
					labelText = localizator.getText('menubar.edit.cut');
					item.@label = labelText;
				}
				else if(item.@id == "9") {
					labelText = localizator.getText('menubar.edit.copy');
					item.@label = labelText;
				}
				else if(item.@id == "10") {
					labelText = localizator.getText('menubar.edit.paste');
					item.@label = labelText;
				}
				else if(item.@id == "11") {
					labelText = localizator.getText('menubar.edit.selectall');
					item.@label = labelText;
				}
				else if(item.@id == "12") {
					labelText = localizator.getText('menubar.bpel.createbpel');
					item.@label = labelText;
				}
				else if(item.@id == "13") {
					labelText = localizator.getText('menubar.help.about');
					item.@label = labelText;
				}
				else if(item.@id == "14") {
					labelText = localizator.getText('menubar.file.new');
					item.@label = labelText;
				}
				else if(item.@id == "15") {
					labelText = localizator.getText('menubar.edit');
					item.@label = labelText;
				}
				else if(item.@id == "16") {
					labelText = localizator.getText('menubar.bpel');
					item.@label = labelText;
				}
				else if(item.@id == "17") {
					labelText = localizator.getText('menubar.help');
					item.@label = labelText;
				}
			}
			this.addEventListener(MenuEvent.ITEM_CLICK, designerMenuBarVH.menuClick);
		}
		//data
		private var menubarXML :XMLList = 
			<>
				<menuitem label="文件" icon="newproject">
					<menuitem label="新建" icon="newproject">
						<menuitem label="项目" icon="{newproject}" id="1"/>
						<menuitem label="文件夹" icon="" id="2"/>
						<menuitem label="文件" icon="" id="3"/>
					</menuitem>
					<menuitem label="打开…" icon="" id="101"/>
					<menuitem type="separator" icon=""/>
					<menuitem label="关闭" icon="" id="4"/>
					<menuitem label="关闭其它项" icon="" />
					<menuitem label="关闭所有" icon="" id="5"/>
					<menuitem type="separator" icon="" />
					<menuitem label="保存" icon="" id="6"/>
					<menuitem label="保存所有" icon="" id="7"/>
				</menuitem>
				
				<menuitem label="编辑" icon="{newproject}">
					<menuitem label="剪切" icon="" id="8"/>
					<menuitem label="复制" icon="" id="9"/>
					<menuitem label="粘贴" icon="" id="10"/>
					<menuitem type="separator" icon="" />
					<menuitem label="选择所有" icon="" id="11"/>
				</menuitem>

				<!--<menuitem label="BPEL" icon="{newproject}">
					<menuitem label="Create BPEL"  icon="" id="12"/>
				</menuitem>
				
				<menuitem label="UDDI" icon="{newproject}">
					<menuitem label="Configuration"  icon="" id="13"/>
				</menuitem>
				-->
				<menuitem label="选项" icon="{newproject}">
					<menuitem label="视图" icon="{newproject}" >
						<menuitem label="文件导航器" icon="" id="20" type="check" toggled={lso.data.selected_file_navigator}/>
						<menuitem label="项目用户列表" icon="" id="21" type="check" toggled={lso.data.selected_projectuserlist}/>
						<!--<menuitem label="User List" icon="" id="22" type="check" toggled={lso.data.selected_user}/>-->
						<menuitem label="UDDI" icon="" id="23" type="check" toggled={lso.data.selected_uddi_navigator}/>
						
						<menuitem label="属性" icon="" id="24" type="check" toggled={lso.data.selected_attribute}/>
						<!--<menuitem label="聊天室" icon="" id="25" type="check" toggled={lso.data.selected_chatroom}/>-->
						<!--<menuitem label="BPMN Data" icon="" id="27" type="check" toggled={lso.data.selected_bpmndata}/>-->
						
						<menuitem label="缩略图" icon="" id="26" type="check" toggled={lso.data.selected_micro}/>

					</menuitem>
				</menuitem>
				
				<menuitem label="帮助" icon="{newproject}">
					<menuitem label="关于平台"  icon="" id="14"/>
				</menuitem>
			</>;
	}
}