package org.act.od.framework.commands{
	import org.act.od.framework.control.OrDesignerEvent;
	
	
	/**
	 * 
	 * @author lizq
	 * 
	 */
	public class AODCommand implements IODCommand{
		
		public function AODCommand(){
			super();
		}
		
		/**
		 * nead to be overwrite 
		 * @param event
		 * 
		 */
		public function execute(event :OrDesignerEvent) :void{
		}
    
      	public function unExecute() :void{
      	}
      
      
     	/**
     	 * if return TRUE，then the method Unexecute is required 
     	 * 
     	 * @return 
     	 */
     	public function isReversible() :Boolean{
     		return false;
     	}

	}
}