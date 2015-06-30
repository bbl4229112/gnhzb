package org.act.od.impl.figure.router
{
	public class RouterFactory
	{
		public function RouterFactory()
		{
		}
		
		public function CreateRouter(routerId:int):IRouter{
			var ir:IRouter;
			switch(routerId)
			{
				case 1:
					ir=new DefaultRouter();
					break;
				default:
					ir=null;
					break;
			}
			return ir;
		}
		
	}
}