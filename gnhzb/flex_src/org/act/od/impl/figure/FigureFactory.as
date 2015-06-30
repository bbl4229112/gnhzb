package org.act.od.impl.figure
{
	import flash.utils.Dictionary;
	
	import org.act.od.impl.figure.custom.DataFigure;
	import org.act.od.impl.figure.custom.DiamondFigure;
	import org.act.od.impl.figure.custom.DocumentFigure;
	import org.act.od.impl.figure.custom.EndFigure;
	import org.act.od.impl.figure.custom.ReadyFigure;
	import org.act.od.impl.figure.custom.StartFigure;
	import org.act.od.impl.model.FigureEditorModel;
	
	public class FigureFactory
	{
		[Bindable]
		[Embed(source="/../assets/figures/mystart.gif")]
		public static var start:Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/myjudge.gif")]
		public static var judge:Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/mydocument.gif")]
		public static var document:Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/mydata.gif")]
		public static var data:Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/myready.gif")]
		public static var ready:Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/myend.gif")]
		public static var end :Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/havelevel.gif")]
		public static var havelevel :Class;         //增加有下层模型的业务活动节点的图标。20120925
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/switch.gif")]
		public static var Switch :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/link.gif")]
		public static var link :Class;
		
		[Bindable]
		[Embed(source="/../assets/figures/mybusiness.gif")]
		public static var activity :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/subprocess.gif")]
		public static var subProcess :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/email.gif")]
		public static var email :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/fax.gif")]
		public static var fax :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/pager.gif")]
		public static var pager :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/sms.gif")]
		public static var sms :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/voice.gif")]
		public static var voice :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/invoke.gif")]
		public static var invoke :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/receive.gif")]
		public static var receive :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/reply.gif")]
		public static var reply :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/wait.gif")]
		public static var wait :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/assign.gif")]
		public static var assign :Class;
		
		//qu
		[Bindable]
		[Embed(source="/../assets/icon/tool/Data Model/circuit.gif")]
		public static var circuit :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/Data Model/interface.png")]
		public static var inter_face :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/Data Model/datastore.png")]
		public static var datastore :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/Data Model/dataprocess.png")]
		public static var dataprocess :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/UML/package.gif")]
		public static var pack_age :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/UML/class.png")]
		public static var cl_ass :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/UML/object.gif")]
		public static var ob_ject :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/UML/port.gif")]
		public static var port :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/UML/subsystem.gif")]
		public static var subsystem :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/UML/dataclass.gif")]
		public static var dataclass :Class;
		
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/bosses.png")]
		public static var bosses :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/custom.png")]
		public static var custom :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/procurement.png")]
		public static var procurement :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/sales.png")]
		public static var sales :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/production.png")]
		public static var production :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/workers.png")]
		public static var workers :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/work/finance.png")]
		public static var finance :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/boss.png")]
		public static var boss :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/manager.png")]
		public static var manager :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/consultant.png")]
		public static var consultant :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/vacancy.png")]
		public static var vacancy :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/assistant.png")]
		public static var assistant :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/staff.png")]
		public static var staff :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Office Orgnization/position.png")]
		public static var position :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/servers.png")]
		public static var servers :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/Ringnetwork.png")]
		public static var ringNetwork :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/superComputer.png")]
		public static var superComputer :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/printer.png")]
		public static var printer :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/tax.png")]
		public static var tax :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/projector.png")]
		public static var projector :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/screen.png")]
		public static var screen :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/router.png")]
		public static var router :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/modem.png")]
		public static var modem :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/phone.png")]
		public static var phone :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/wirelessnetwork.png")]
		public static var wirelessNetwork :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/firewall.png")]
		public static var firewall :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/network/users.png")]
		public static var users :Class;
		
		[Bindable]
		[Embed(source="/../assets/icon/tool/Program Structure/function.png")]
		public static var func_tion :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Program Structure/processDesign.png")]
		public static var processDesign :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Program Structure/functionCall.png")]
		public static var functionCall :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Program Structure/Switch.png")]
		public static var button :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Program Structure/Cloud.png")]
		public static var cloud :Class;
		[Bindable]
		[Embed(source="/../assets/icon/tool/Program Structure/Vocabulary.png")]
		public static var vocabulary :Class;
		
		
		
		private static var dic :Dictionary = new Dictionary();
		
		private static function initDic() :void {
			var id :int;
			var name :String;
			
			id = 100;
			name = "Start";
			dic[id] = name;
			dic[name] = id;
			
			id = 101;
			name = "End";
			dic[id] = name;
			dic[name] = id;
			
			id = 103;
			name = "Activity";
			dic[id] = name;
			dic[name] = id;
			
			id = 114;
			name = "SubProcess";
			dic[id] = name;
			dic[name] = id;
			
			id = 102;
			name = "Switch";
			dic[id] = name;
			dic[name] = id;
			
			id = 105;
			name = "Fax";
			dic[id] = name;
			dic[name] = id;
			
			id = 104;
			name = "Pager";
			dic[id] = name;
			dic[name] = id;
			
			id = 107;
			name = "SMS";
			dic[id] = name;
			dic[name] = id;
			
			id = 108;
			name = "Voice";
			dic[id] = name;
			dic[name] = id;
			
			id = 106;
			name = "Email";
			dic[id] = name;
			dic[name] = id;
			
			id = 109;
			name = "Invoke";
			dic[id] = name;
			dic[name] = id;
			
			id = 113;
			name = "Receive";
			dic[id] = name;
			dic[name] = id;
			
			id = 110;
			name = "Reply";
			dic[id] = name;
			dic[name] = id;
			
			id = 111;
			name = "Wait";
			dic[id] = name;
			dic[name] = id;
			
			id = 112;
			name = "Assign";
			dic[id] = name;
			dic[name] = id;
			
			id = 3;
			name = "Link";
			dic[id] = name;
			dic[name] = id;
			
			//qu
			id = 115;
			name = "业务";
			dic[id] = name;
			dic[name] = id;
			
			id = 116;
			name = "Interface";
			dic[id] = name;
			dic[name] = id;
			
			id = 117;
			name = "Datastore";
			dic[id] = name;
			dic[name] = id;
			
			id = 118;
			name = "Dataprocess";
			dic[id] = name;
			dic[name]= id;
			
			id = 119;
			name = "Package";
			dic[id] = name;
			dic[name]= id;
			
			id = 120;
			name = "Class";
			dic[id] = name;
			dic[name]= id;
			
			id = 121;
			name = "Object";
			dic[id] = name;
			dic[name]= id;
			
			id = 122;
			name = "Port";
			dic[id] = name;
			dic[name]= id;
			
			id = 123;
			name = "Subsystem";
			dic[id] = name;
			dic[name]= id;
			
			id = 124;
			name = "判断";
			dic[id] = name;
			dic[name]= id;
			
			//			id = 124;
			//			name = "Dataclass";
			//			dic[id] = name;
			//			dic[name]= id;
			
			id = 125;
			name = "Bosses";
			dic[id] = name;
			dic[name]= id;
			id = 126;
			name = "Custom";
			dic[id] = name;
			dic[name]= id;
			id = 127;
			name = "Procurement";
			dic[id] = name;
			dic[name]= id;
			id = 128;
			name = "Sales";
			dic[id] = name;
			dic[name]= id;
			id = 129;
			name = "Production";
			dic[id] = name;
			dic[name]= id;
			id = 130;
			name = "Workers";
			dic[id] = name;
			dic[name]= id;
			id = 131;
			name = "Finance";
			dic[id] = name;
			dic[name]= id;
			
			id = 132;
			name = "Boss";
			dic[id] = name;
			dic[name]= id;
			id = 133;
			name = "Manager";
			dic[id] = name;
			dic[name]= id;
			id = 134;
			name = "Consultant";
			dic[id] = name;
			dic[name]= id;
			id = 135;
			name = "Vacancy";
			dic[id] = name;
			dic[name]= id;
			id = 136;
			name = "Assistant";
			dic[id] = name;
			dic[name]= id;
			id = 137;
			name = "Staff";
			dic[id] = name;
			dic[name]= id;
			id = 138;
			name = "Position";
			dic[id] = name;
			dic[name]= id;
			
			id = 139;
			name = "Servers";
			dic[id] = name;
			dic[name]= id;
			id = 140;
			name = "RingNetwork";
			dic[id] = name;
			dic[name]= id;
			id = 141;
			name = "SuperComputer";
			dic[id] = name;
			dic[name]= id; 
			id = 142;
			name = "Printer";
			dic[id] = name;
			dic[name]= id;
			id = 143;
			name = "Tax";
			dic[id] = name;
			dic[name]= id;
			id = 144;
			name = "Projector";
			dic[id] = name;
			dic[name]= id;
			id = 145;
			name = "Screen";
			dic[id] = name;
			dic[name]= id;
			id = 146;
			name = "Router";
			dic[id] = name;
			dic[name]= id;
			id = 147;
			name = "Modem";
			dic[id] = name;
			dic[name]= id;
			id = 148;
			name = "Phone";
			dic[id] = name;
			dic[name]= id;
			id = 149;
			name = "WirelessNetwork";
			dic[id] = name;
			dic[name]= id;
			id = 150;
			name = "Firewall";
			dic[id] = name;
			dic[name]= id;
			
			id = 151;
			name = "Users";
			dic[id] = name;
			dic[name]= id;
			id = 152;
			name = "Function";
			dic[id] = name;
			dic[name]= id;
			id = 153;
			name = "ProcessDesign";
			dic[id] = name;
			dic[name]= id;
			id = 154;
			name = "FunctionCall";
			dic[id] = name;
			dic[name]= id;
			id = 155;
			name = "Button";
			dic[id] = name;
			dic[name]= id;
			id = 156;
			name = "Cloud";
			dic[id] = name;
			dic[name]= id;
			id = 157;
			name = "Vocabulary";
			dic[id] = name;
			dic[name]= id;
			
			id = 158;
			name = "开始";
			dic[id] = name;
			dic[name]= id;
			
			id = 159;
			name = "结束";
			dic[id] = name;
			dic[name]= id;
			
			id = 160;
			name = "文档";
			dic[id] = name;
			dic[name]= id;
			
			id = 161;
			name = "数据";
			dic[id] = name;
			dic[name]= id;
			
			id = 162;
			name = "准备";
			dic[id] = name;
			dic[name]= id;
		}
		
		public static function createFigure(figureId:int):IFigure{
			var ifi:IFigure;
			switch(figureId)
			{
				case -1:
					ifi=null;
					break;
				
				case 3:
					ifi=new LinkFigure(FigureEditorModel.BPEL_PROCESS_TYPE);
					break;
				
				case 100:
					ifi=new Startow2Figure(FigureEditorModel.BPEL_PROCESS_TYPE);
					break;
				
				case 101:
					ifi=new Endow2Figure(FigureEditorModel.BPEL_PROCESS_TYPE);
					break;
				
				case 102:
					ifi=new Switchow2Figure();
					break;
				
				case 103:
					ifi=new Activityow2Figure(FigureEditorModel.BPEL_PROCESS_TYPE);
					break;
				
				case 104:
					ifi=new Pagerow2Figure();
					break;
				
				case 105:
					ifi=new Faxow2Figure();
					break;
				
				case 106:
					ifi=new Emailow2Figure();
					break;
				
				case 107:
					ifi=new SMSow2Figure();
					break;
				
				case 108:
					ifi=new Voiceow2Figure();
					break;
				
				case 109:
					ifi=new Invokeow2Figure();
					break;
				
				case 110:
					ifi=new Replyow2Figure();
					break;
				
				case 111:
					ifi=new Waitow2Figure();
					break;
				
				case 112:
					ifi=new Assignow2Figure();
					break;
				
				case 113:
					ifi=new Receiveow2Figure();
					break;
				
				case 114:
					ifi=new SubProcessow2Figure();
					break;
				
				//qu
				case 115:
					ifi=new Circuitow2Figure();
					break;
				
				case 116:
					ifi=new Interfaceow2Figure();
					break;
				
				case 117:
					ifi=new Datastoreow2Figure();
					break;
				
				case 118:
					ifi=new Dataprocessow2Figure();
					break;
				case 119:
					ifi=new Packageow2Figure();
					break;
				
				case 120:
					ifi=new Classow2Figure();
					break;
				
				case 121:
					ifi=new Objectow2Figure();
					break;
				
				case 122:
					ifi=new Portow2Figure();
					break;
				
				case 123:
					ifi=new Subsystemow2Figure();
					break;
				
				case 124:
					ifi=new DiamondFigure();
					break;
				
				case 125:
					ifi=new Bossesow2Figure();
					break;
				
				
				case 126:
					ifi=new Customow2Figure();
					break;
				
				case 127:
					ifi=new Procurementow2Figure();
					break;
				
				case 128:
					ifi=new Salesow2Figure();
					break;
				
				case 129:
					ifi=new Productionow2Figure();
					break;
				
				case 130:
					ifi=new Workersow2Figure();
					break;
				
				case 131:
					ifi=new Financeow2Figure();
					break;
				
				case 132:
					ifi=new Bossow2Figure();
					break;
				
				case 133:
					ifi=new Managerow2Figure();
					break;
				
				case 134:
					ifi=new Consultantow2Figure();
					break;
				
				case 135:
					ifi=new Vacancyow2Figure();
					break;
				
				case 136:
					ifi=new Assistantow2Figure();
					break;
				
				case 137:
					ifi=new Staffow2Figure();
					break;			
				
				case 138:
					ifi=new Positionow2Figure();
					break;
				
				case 139:
					ifi=new Serversow2Figure();
					break;
				
				case 140:
					ifi=new RingNetworkow2Figure();
					break;
				
				case 141:
					ifi=new SuperComputerow2Figure();
					break;
				
				case 142:
					ifi=new Printerow2Figure();
					break;
				
				case 143:
					ifi=new Taxow2Figure();
					break;
				
				case 144:
					ifi=new Projectorow2Figure();
					break;
				
				case 145:
					ifi=new Screenow2Figure();
					break;
				
				case 146:
					ifi=new Routerow2Figure();
					break;
				
				case 147:
					ifi=new Modemow2Figure();
					break;
				
				case 148:
					ifi=new Phoneow2Figure();
					break;
				
				case 149:
					ifi=new WirelessNetworkow2Figure();
					break;
				
				case 150:
					ifi=new Firewallow2Figure();
					break;
				
				case 151:
					ifi=new Usersow2Figure();
					break;
				
				case 152:
					ifi=new Functionow2Figure();
					break;
				
				case 153:
					ifi=new ProcessDesignow2Figure();
					break;
				
				case 154:
					ifi=new FunctionCallow2Figure();
					break;
				
				case 155:
					ifi=new Buttonow2Figure();
					break;
				
				case 156:
					ifi=new Cloudow2Figure();
					break;
				
				case 157:
					ifi=new Vacancyow2Figure();
					break;
				case 158:
					ifi=new StartFigure();
					break;
				case 159:
					ifi=new EndFigure();
					break;
				case 160:
					ifi=new DocumentFigure();
					break;
				case 161:
					ifi=new DataFigure();
					break;
				case 162:
					ifi=new ReadyFigure();
					break;
				default:
					ifi=null;
					break;
			}
			if(ifi){
				if(ifi is GraphicalFigure){
					GraphicalFigure(ifi).figureName = id2name(ifi.getdrawid());
				}
			}
			return ifi;
		}
		
		
		public static function nametoid(classname:String):int{
			FigureFactory.initDic();
			var id :int = FigureFactory.dic[classname];
			return id;
		}
		
		public static function id2name(id :int) :String {
			FigureFactory.initDic();
			var name :String = FigureFactory.dic[id];
			return name;
		}
		
	}
}