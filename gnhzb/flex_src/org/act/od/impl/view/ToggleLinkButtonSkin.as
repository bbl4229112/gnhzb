package org.act.od.impl.view
{
	import mx.skins.halo.LinkButtonSkin;
	/**
	 * edit link skin.
	 * 
	 * @author Quyue
	 * 
	 */	
	public class ToggleLinkButtonSkin extends LinkButtonSkin
	{
		public function ToggleLinkButtonSkin() {
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w, h);
			
			var cornerRadius:Number = getStyle("cornerRadius");
			var rollOverColor:uint = getStyle("rollOverColor");
			var selectionColor:uint = getStyle("selectionColor");
			
			graphics.clear();
			
			switch (name) {
				case "upSkin":
					//              	Draw an invisible rectangle, 
					//						when the user clicks on the region
					drawRoundRect(
						0, 0, w, h, cornerRadius,
						0, 0);
					break;
				//				add two states: selectedUpSkin and selectedOverSkin
				case "selectedUpSkin":
				case "selectedOverSkin":
				case "overSkin":
					drawRoundRect(
						0, 0, w, h, cornerRadius,
						rollOverColor, 1);
					break;
				//             add the new state:selectedDownSkin
				case "selectedDownSkin":
				case "downSkin":
					drawRoundRect(
						0, 0, w, h, cornerRadius,
						selectionColor, 1);
					break;
				//				add the new state:selectedDisabledSkin
				case "selectedDisabledSkin":
				case "disabledSkin":
					//				Draw invisible shape so we have a hit area.
					drawRoundRect(
						0, 0, w, h, cornerRadius,
						0, 0);
					break;
			}
		}
	}
}