package org.act.od.impl.viewhelper
{
	import org.act.od.framework.view.ViewHelper;
	import org.act.od.impl.view.FileNavigator;
	/**
	 * 
	 * @author Likexin
	 * 
	 */
	public class FileNavigatorVH extends ViewHelper
	{
		public static const VH_KEY :String = "FileNavigatorVH";
		public function FileNavigatorVH(document : Object, id : String)
		{
			super();
			initialized(document, id);			
		}
		/**
		 * Return FileNavigatorView
		 */
		public function get fileNavigator():FileNavigator{
			return this.view as FileNavigator;
		}
	}
}
