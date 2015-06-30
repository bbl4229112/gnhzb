package org.act.od.impl.events
{
	/**
	 * This class define the events related to cooperation
	 * 
	 * @ author Mengsong
	 * 
	 */
	import org.act.od.framework.control.OrDesignerEvent;
	
	public class CooperateOperationEvent extends OrDesignerEvent
	{
		/**
		 *	to decide the add,delete,or modify command for original xml
		 */
		public static const CORECEIVEMESSAGEADMMANAGE :String = "co_receive_message_adm_manage_cooperateoperation";
		/**
		 *	add,delete,or modify the original xml
		 */
		public static const COSENDMESSAGECOLLECTION :String = "co_send_message_collection_cooperateoperation";
			
		/**
		 *	modify the original xml
		 */
		public static const CORECEIVEMESSAGEMODIFY :String = "co_receive_message_modify_cooperateoperation";
		
		/**
		 *	modify the original xml for moveFigure Operation
		 */
		public static const CORECEIVEMESSAGEMOVEFIGURE :String = "co_receive_message_movefigure_cooperateoperation";
				
		/**
		 *	modify the original xml for MoveLeft Operation
		 */
		public static const CORECEIVEMESSAGEMOVELEFT :String = "co_receive_message_moveleft_cooperateoperation";
		/**
		 *	modify the original xml for MoveRight Operation
		 */
		public static const CORECEIVEMESSAGEMOVERIGHT :String = "co_receive_message_moveright_cooperateoperation";
		/**
		 *	modify the original xml for MoveUp Operation
		 */
		public static const CORECEIVEMESSAGEMOVEUP :String = "co_receive_message_moveup_cooperateoperation";
		/**
		 *	modify the original xml for MoveDown Operation
		 */
		public static const CORECEIVEMESSAGEMOVEDOWN :String = "co_receive_message_movedown_cooperateoperation";
		/**
		 *	modify the original xml for ChangeSize Operation
		 */
		public static const CORECEIVEMESSAGECHANGESIZE :String = "co_receive_message_changesize_cooperateoperation";
		
		/**
		 *	modify the original xml for GraphicIFigure Operation
		 */
		public static const CORECEIVEMESSAGEGRAPHICIFIGURE :String = "co_receive_message_graphicifigure_cooperateoperation";
		
		/**
		 *	modify the original xml for ChangeLinkLabel Operation
		 */
		public static const CORECEIVEMESSAGECHANGELINKLABEL :String = "co_receive_message_changelinklabel_cooperateoperation";
		/**
		 *	Delete the original xml for FigureDelete Operation
		 */
		public static const CORECEIVEMESSAGEFIGUREDELETE :String = "co_receive_message_figuredelete_cooperateoperation";
		/**
		 *	Delete the original xml for Delete Operation
		 */
		public static const CORECEIVEMESSAGEDELETE :String = "co_receive_message_delete_cooperateoperation";
		/**
		 *	Delete the original xml Link for Delete Operation
		 */
		public static const CORECEIVEMESSAGELINKDELETE :String = "co_receive_message_linkdelete_cooperateoperation";
		/**
		 *	Add the original xml Link for Creating Operation
		 */
		public static const CORECEIVEMESSAGEADD :String = "co_receive_message_add_cooperateoperation";
		/**
		 *	Add the original xml Link for Creating State
		 */
		public static const CORECEIVEMESSAGECREATE :String = "co_receive_message_create_cooperateoperation";
		/**
		 *	Add the original xml Link for pasting
		 */
		public static const CORECEIVEMESSAGEPASTE :String = "co_receive_message_paste_cooperateoperation";
		
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function CooperateOperationEvent(type : String, dataParameter:Object = null,
												bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(type, dataParameter, bubbles, cancelable);
		}
	}
}