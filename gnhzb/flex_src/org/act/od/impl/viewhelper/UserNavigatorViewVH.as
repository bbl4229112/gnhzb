package org.act.od.impl.viewhelper
{
	import org.act.od.framework.view.ViewHelper;
	import org.act.od.impl.view.UserNavigatorView;
	/**
	 * 
	 * @author Likexin
	 * 
	 */
	public class UserNavigatorViewVH extends ViewHelper
	{
		public static const VH_KEY :String = "UserNavigatorViewVH";
		public function UserNavigatorViewVH(document : Object, id : String)
		{
			super();
			initialized(document, id);
		}
		/**
		 * Return FileNavigatorView
		 */
		public function get userNavigatorView() :UserNavigatorView{
			return this.view as UserNavigatorView;
		}
	}
}