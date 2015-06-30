package org.act.od.impl.figure.bpmn
{
	import flash.utils.Dictionary;
	
	import org.act.od.impl.figure.*;
	import org.act.od.impl.model.FigureEditorModel;
	
	
	public class BpmnFigureFactory
	{
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/start2.gif" )]
		public static var start:Class;
		
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/event/start/messageStart.gif" )]
		public static var messageStart:Class;
		
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/end.gif" )]
		public static var end:Class;
		
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/event/end/noneEnd.gif" )]
		public static var noneEnd:Class;
		
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/event/intermediate/messageIntermediate.gif" )]
		public static var messageIntermediate:Class;
		
		[Bindable]
		[Embed( source="/../assets/icon/tool/bpmn/link.gif" )]
		public static var link:Class;
		
		[Bindable]
		[Embed( source="/../assets/icon/tool/bpmn/link.gif" )]
		public static var messageFlowLink:Class;
		
		[Bindable]
		[Embed( source="/../assets/icon/tool/bpmn/activity.png" )]
		public static var activity:Class;
		
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/Pool5.GIF" )]
		public static var pool:Class;
		
		[Bindable]
		[Embed( source="/../assets/figures/bpmn/portType.gif" )]
		public static var portType:Class;
		
		
		
		private static var dic:Dictionary = new Dictionary();
		
		private static function initDic():void
		{
			var id:int;
			var name:String;
			
			id = 100;
			name = "Start";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 101;
			name = "End";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 103;
			name = "Activity";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 3;
			name = "Link";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 115;
			name = "Pool";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 116;
			name = "messageStart";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 117;
			name = "noneEnd";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 118;
			name = "messageIntermediate";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 119;
			name = "messageFlowLink";
			dic[ id ] = name;
			dic[ name ] = id;
			
			id = 120;
			name = "PortType";
			dic[ id ] = name;
			dic[ name ] = id;
			
			
		}
		
		public static function createFigure( figureId:int ):IFigure
		{
			var ifi:IFigure;
			switch ( figureId )
			{
				case -1:
					ifi = null;
					break;
				
				case 3:
					ifi = new LinkFigure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 100:
					ifi = new Startow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 101:
					ifi = new Endow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 103:
					ifi = new Activityow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 115: //pool
					ifi = new Poolow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 116: //message Start Event
					ifi = new MessageStartEventow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 117: //none End Event
					ifi = new NoneEndEventow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 118: //message intermediate Event
					ifi = new MessageIntermediateEventow2Figure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 119: //message flow connector link
					ifi = new MessageFlowLinkFigure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				case 120: //message flow connector link
					ifi = new PortTypeFigure(FigureEditorModel.BPMN_PROCESS_TYPE);
					break;
				
				default:
					ifi = null;
					break;
			}
			if ( ifi )
			{
				if ( ifi is GraphicalFigure )
				{
					GraphicalFigure( ifi ).figureName = id2name( ifi.getdrawid());
				}
			}
			return ifi;
		}
		
		
		public static function name2id( classname:String ):int
		{
			BpmnFigureFactory.initDic();
			var id:int = BpmnFigureFactory.dic[ classname ];
			return id;
		}
		
		public static function id2name( id:int ):String
		{
			BpmnFigureFactory.initDic();
			var name:String = BpmnFigureFactory.dic[ id ];
			return name;
		}
		
	}
}