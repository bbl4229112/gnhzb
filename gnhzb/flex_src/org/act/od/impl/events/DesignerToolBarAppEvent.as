package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	/**
	 * This class define the events raised by the DesignerToolBar.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class DesignerToolBarAppEvent extends OrDesignerEvent
	{
		/**
		 * 
		 */
		public static const UPLOAD_FILE :String = "upload_file_DesignerToolBar";
		
		/**
		 * Create a new project.
		 */
		public static const NEW_PROJECT :String = "new_project_DesignerToolBar";
		/**
		 * Create a new folder.
		 */
		public static const NEW_FOLDER :String = "new_folder_DesignerToolBar";
		/**
		 * Create a new file.
		 */
		public static const NEW_FILE :String = "new_file_DesignerToolBar";
		/**
		 * Save the selected file.
		 */ 
		public static const FILE_SAVE :String = "file_save_DesignerToolBar";
		/**
		 * Save all opening files.
		 */ 
		public static const FILE_SAVEALL :String = "file_saveall_DesignerToolBar";
		
		public static const UDDI_CONFIGURATION :String = "uddi_configuration_DesignerToolBar";
		/**
		 * Create a new BPEL file.
		 */
		public static const CREATE_BPEL :String = "create_bpel_DesignerToolBar";
		
		/**
		 * rollback the process
		 */ 
		public static const ROLL_BACK :String = "roll_back_DesignerToolBar";
		/**
		 * redo the process
		 */ 
		public static const RE_DO :String = "re_do_DesignerToolBar";	
		/**
		 * collaboration
		 */ 
		public static const Cooperate :String = "cooperate_DesignerToolBar";		
		/**
		 * tokencontrol
		 */ 
		public static const TokenControl :String = "tokencontrol_DesignerToolBar";				
		
		/**
		 * Check the login.
		 */
		public static const LOGIN_CHECK :String = "login_check_DesignerToolBar";
		
		/**
		 * logout.
		 */
		public static const LOGOUT :String = "logout_DesignerToolBar";
		
		/**
		 * Register.
		 */
		public static const REGISTER :String = "register_DesignerToolBar";	
		
		/**
		 * Sign Model.
		 */
		public static const SIGNMODE :String = "sign_mode_DesignerToolBar";		
		
		/**
		 * Compare Model.
		 */
		public static const COMPAREMODE :String = "compare_mode_DesignerToolBar";	
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function DesignerToolBarAppEvent(type : String, dataParameter:Object = null,
												bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(type, dataParameter, bubbles, cancelable);
		}
		
	}
}