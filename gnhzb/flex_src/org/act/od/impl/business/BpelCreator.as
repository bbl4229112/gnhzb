package org.act.od.impl.business
{
	import mx.managers.PopUpManager;
	
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.BPELWrongWindow;
	
	
	/**
	 * Bpel creator, help to creat bpel file from figure Ggraphics
	 *
	 * @author Zhaoxq
	 *
	 */
	public class BpelCreator{
		
		private var diagram :XML = new XML;
		private var imformation :XML = new XML;
		private var structure :XML = new XML;
		
		/**
		 * Constructor,
		 * <p> NONE </p>
		 */
		public function BpelCreator(){
			
		}
		/**
		 * creat bpelString from <code> processFigure </code> parameter.
		 * @param processFigure process of figures
		 * @return bpelString  bpel string
		 *
		 */
		public function outBpelStirng( processFigure :ProcessFigure ) :String{
			
			diagram = processFigure.outputAllInformation();
			
			this.outputImformation();
			
			var restr :String;
			
			if(this.errorhandle()=="losefigure"||"loselink"){
				restr = "The process must started with a 'start' figure, and fineshed with a 'end' figure!" + "\n" + "All the figure must have a link! If there are some figures you do not need, please delete them.";
			}
			
			if(this.errorhandle()=="true"){
				this.outputStructure();
				restr = colorbpel(this.outbpel());
				//				restr = this.outbpel();
			}
			if(restr == "The process must started with a 'start' figure, and fineshed with a 'end' figure!" + "\n" + "All the figure must have a link! If there are some figures you do not need, please delete them."){
				
				var bpelWrongWindow :BPELWrongWindow = BPELWrongWindow(PopUpManager.createPopUp(
					OrDesignerModelLocator.getInstance().getOrchestraDesigner(), BPELWrongWindow, true));
				bpelWrongWindow.setmessage(restr);
				PopUpManager.centerPopUp(bpelWrongWindow);
				return null;
				
			}
			
			return restr;
		}
		
		/**
		 * judge wether the structure can be trans into bpel
		 * <p> CHECK if the structure begining with Start figure and finishing with End figure.</p>
		 * @return tag error description of the process figurs structure.
		 *
		 */
		private function errorhandle():String{
			var start :String;
			var end :String;
			var info :XML = this.imformation;
			var figurelist :XMLList = info.descendants("Figure");
			var linklist :XMLList = info.descendants("Link");
			var i :int;
			var k :int;
			var j :int = linklist.length();
			var tag :String;
			var linktag :Object = new Object;
			var figures : int = 0;
			
			for(i=0;i<figurelist.length();i++){
				if(XML(figurelist[i]).@type == "Start"){
					start = "Start";
				}
			}
			
			for(i=0;i<figurelist.length();i++){
				if(XML(figurelist[i]).@type == "End"){
					end = "End";
				}
			}
			
			if(start=="Start"&&end=="End"){
				tag = "true";
			}
			else{
				tag = "losefigure";
			}
			
			for(i=0;i<linklist.length();i++){
				linktag[i] = XML(linklist[i]).@sourceID;
			}
			
			for(i=0;i<linklist.length();i++){
				linktag[j+i] = XML(linklist[i]).@targetID;
			}
			
			for(i=0;i<figurelist.length();i++){
				var id :String = XML(figurelist[i]).@ID;
				for(k=0;k<2*j+1;k++){
					if(id==linktag[k]){
						figures = figures+1;
						break;
					}
				}
			}
			
			if(figures<figurelist.length()){
				tag = "loselink";
			}
			
			return tag;
		}
		
		/**
		 * output the bpel root, includes partnerlinks, variables and sequence
		 * <p>show the <code>bpelWrongWindow</code> if existing bug</p>
		 * @return string <code>process</code>
		 *
		 */
		public function outbpel():String{
			var process:XML = <process/>;
			
			process.appendChild(partnerLinks());
			process.appendChild(variables());
			process.appendChild(sequence());
			
			if(sequence()==<bug/>){
				var bug:String = "Sorry! The process can't be transed to BPEL.";
				
				var bpelWrongWindow :BPELWrongWindow = BPELWrongWindow(PopUpManager.createPopUp(
					OrDesignerModelLocator.getInstance().getOrchestraDesigner(), BPELWrongWindow, true));
				bpelWrongWindow.setmessage(bug);
				PopUpManager.centerPopUp(bpelWrongWindow);
				return "";
			}
			
			process.@name = "Process";
			process.@targetNamespace = "";
			process.@xmlns = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";
			//process.@queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116";
			//process.@expressionLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116";
			
			return process.toXMLString();
		}
		
		/**
		 * output the 'partnerLinks' child in the process bpel root.
		 * @return
		 *
		 */
		private function partnerLinks():XML{
			var partnerLinks:XML = <partnerLinks/>;
			partnerLinks.appendChild(<partnerLink/>);
			partnerLinks.partnerLink.@name = "";
			return partnerLinks;
			/**
			 * TODO //
			 */
		}
		
		/**
		 * output the 'variables' child in the process bpel root.
		 * @return
		 *
		 */
		private function variables():XML{
			var variables:XML= <variables/>;
			variables.appendChild(<variable/>);
			variables.variable.@name = "";
			return variables;
			/**
			 * TODO //
			 */
		}
		
		/**
		 * output the 'sequence' child in the process bpel root.
		 * @return
		 *
		 */
		
		private function sequence():XML{
			var sequence:XML = this.outputStructure();
			
			return sequence;
		}
		
		/**
		 * deal with the XML has got, delete some attributes do not need, get imformation XML
		 *
		 */
		
		private function outputImformation() :void{
			var imformation :XML = new XML;
			var figurelist :XMLList = new XMLList;
			
			
			var i :int;
			
			imformation = this.diagram;
			
			delete imformation.Figure.arrow;
			delete imformation.Figure.router;
			delete imformation.Figure.@x;
			delete imformation.Figure.@y;
			delete imformation.Figure.@rx;
			delete imformation.Figure.@ry;
			delete imformation.Figure.@standardwidth;
			delete imformation.Figure.@standardheight;
			delete imformation.Figure.@drawid;
			delete imformation.Figure.@width;
			delete imformation.Figure.@height;
			delete imformation.Figure.@r;
			delete imformation.Figure.@tailX;
			delete imformation.Figure.@tailY;
			delete imformation.Figure.@radius;
			//delete imformation.Figure.@startFigureId;
			//delete imformation.Figure.@endFigureId;
			delete imformation.Figure.@arrowid;
			delete imformation.Figure.@routerid;
			
			figurelist = imformation.descendants("Figure");
			
			
			for(i=0;i<figurelist.length();i++)
			{
				if(XML(figurelist[i]).attribute("type").toString()=="Link"){
					var link :XML = <Link/>;
					link.@sourceID = XML(figurelist[i]).attribute("startFigureId");
					link.@targetID = XML(figurelist[i]).attribute("endFigureId");
					link.@ID = XML(figurelist[i]).attribute("ID");
					var attributeslist :XMLList = XML(figurelist[i]).descendants("Attributes");
					if(attributeslist.child(2).toString()==""){
						link.@transitionCondition = "yes";
					}
					else{
						link.@transitionCondition = attributeslist.child(2).toString();
					}
					imformation.appendChild(link);
					
				}
			}
			
			//trace(imformation);
			
			for(i=0;i<figurelist.length();i++)
			{
				if(XML(figurelist[i]).attribute("type").toString()=="Link"){
					delete figurelist[i];
				}
			}
			
			delete imformation.Figure;
			
			
			for(i=0;i<figurelist.length();i++)
			{
				if(XML(figurelist[i]).attribute("type").toString()!="Link"){
					imformation.appendChild(XML(figurelist[i]));
				}
			}
			
			//			trace("imformation is:"+"\n"+imformation);
			this.imformation = imformation;
		}
		
		/**
		 * trans the imformation XML to sequence, get structure XML
		 * @return
		 *
		 */
		
		private function outputStructure() :XML{
			var structure :XML =<sequence/>;
			var startid :String;
			var endid:String;
			var figurelist :XMLList = new XMLList;
			var linklist :XMLList = new XMLList;
			var i :int;
			var j :int;
			var k :int;
			var l :int;
			var m :int;
			var n :int;
			var p :int;
			var q :int;
			var t :int;
			var r :int;
			//var flow :XML = <flow/>;
			var midsource :String;
			var midtarget :String;
			var objects :Object = new Object;
			var objectt :Object = new Object;
			var object :Object = new Object;
			var bug :XML = <bug/>;
			
			var sourcetype :String;
			
			
			figurelist = imformation.descendants("Figure");
			linklist = imformation.descendants("Link");
			
			//trace(figurelist.length().toString());
			//trace(linklist.length().toString());
			
			
			
			for(i=0;i<figurelist.length();i++)
			{
				if(XML(figurelist[i]).attribute("type").toString()=="Start"){
					startid=XML(figurelist[i]).@ID;
					//					trace("startid is:"+startid);
				}
			}
			
			//midsource = startid;
			
			
			for(i=0;i<figurelist.length();i++)
			{
				if(XML(figurelist[i]).attribute("type").toString()=="End"){
					endid=XML(figurelist[i]).@ID;
					//					trace("endid is:"+endid);
				}
			}
			
			
			
			for(i=0;i<figurelist.length();i++)//;
			{
				if(XML(figurelist[i]).attribute("ID").toString()!=null){
					var id :int = int(XML(figurelist[i]).attribute("ID").toString());
					//trace(id);
					var s :int = 0;
					t = 0;
					for(j=0;j<linklist.length();j++)
					{
						if(XML(linklist[j]).attribute("sourceID").toString()== id.toString()){
							s=s+1;
						}
					}
					for(k=0;k<linklist.length();k++)
					{
						if(XML(linklist[k]).attribute("targetID").toString()== id.toString()){
							t=t+1;
						}
					}
					objects[id] = s;
					objectt[id] = t;
				}
			}
			
			
			midsource = startid;
			
			do{
				
				for(i=0;i<linklist.length();i++)
				{
					
					if(XML(linklist[i]).attribute("sourceID").toString()==midsource && objects[midsource]==1){
						//					object[p] = midsource;
						//					p = p+1;
						for(j=0;j<figurelist.length();j++)
						{
							if(XML(figurelist[j]).attribute("ID").toString()==midsource){
								structure.appendChild(outputBpelFigure(XML(figurelist[j])));//kkk
							}
						}
						midtarget = XML(linklist[i]).attribute("targetID").toString();
						midsource = midtarget;
						
						for(q=0;q<figurelist.length();q++)
						{
							if(XML(figurelist[q]).attribute("ID").toString()==midsource){
								sourcetype = XML(figurelist[q]).@type;
							}
						}
						
					}
					
					
					if(XML(linklist[i]).attribute("sourceID").toString()==midsource && objects[midsource]>1 &&sourcetype!="Switch"){
						//var objectflow :Object = new Object;
						var sourcenumber :int;
						var targetnumber :int;
						var flow :XML = <flow/>
						var Links :XML = <links/>
						
						
						var midflowtarget :String;
						var midflowsource :String;
						var midflowsequencesource :String;
						var midflowsequencetarget :String;
						
						
						
						for(n=0;n<figurelist.length();n++)
						{
							if(XML(figurelist[n]).attribute("ID").toString()==midsource){
								structure.appendChild(outputBpelFigure(XML(figurelist[n])));//kkk
							}
						}
						
						midflowsource = midsource;
						sourcenumber = objects[midsource];
						
						
						for(k=0;k<linklist.length();k++)
						{
							if(XML(linklist[k]).attribute("sourceID").toString()==midflowsource){
								midflowsequencesource=XML(linklist[k]).attribute("targetID").toString();
								var bugt: int = 0;
								do{//si
									bugt = bugt+1;
									for(j=0;j<linklist.length();j++)
									{
										
										if(XML(linklist[j]).attribute("sourceID").toString()==midflowsequencesource){
											midflowsequencetarget=XML(linklist[j]).attribute("targetID").toString();
											for(l=0;l<figurelist.length();l++)//
											{
												if(XML(figurelist[l]).attribute("ID").toString()==midflowsequencesource){
													flow.appendChild(XML(figurelist[l]));
													
													
													for(m=0;m<linklist.length();m++)
													{
														if(XML(linklist[m]).attribute("sourceID").toString()==midflowsequencesource){
															Links.appendChild(outputBpelLink(XML(linklist[m]),midsource));//kkk
														}
													}
												}
											}
										}
										//										if(j==linklist.length()-1&&XML(linklist[j]).attribute("sourceID").toString()!=midflowsequencesource&&objectt[midflowsequencetarget]!=sourcenumber){
										//
										//											return(bug);
										//										}
									}
									
									midflowsequencesource = midflowsequencetarget;
									
									if(bugt==100){
										return(bug);
									}
									
								}while(objectt[midflowsequencetarget]!=sourcenumber)
								
							}
						}
						
						midsource = midflowsequencetarget;
						
						
						var flowlinklist :XMLList = Links.descendants("link");
						
						delete Links.link;
						
						for(p=0;p<flowlinklist.length();p++)
						{
							if(XML(flowlinklist[p]).attribute("targetID").toString()!=midsource){
								Links.appendChild(flowlinklist[p]);
							}
						}
						
						flow.appendChild(Links);
						
						//
						var flowfigurelist :XMLList = flow.descendants("Figure");
						var idstr :String;
						var flowmidlinklist :XMLList = Links.descendants("link");
						for(t=0;t<flowfigurelist.length();t++)
						{
							var flowmidfigure :XML = outputBpelFigure(XML(flowfigurelist[t]));
							
							
							idstr = XML(flowfigurelist[t]).attribute("ID");
							for(r=0;r<flowmidlinklist.length();r++)
							{
								var flowsource :XML = <source/>;
								var flowtarget :XML = <target/>;
								
								if(XML(flowmidlinklist[r]).attribute("sourceID").toString()==idstr){
									flowsource.@linkName = XML(flowmidlinklist[r]).@name;
									flowmidfigure.appendChild(flowsource);
								}
								if(XML(flowmidlinklist[r]).attribute("targetID").toString()==idstr){
									flowtarget.@linkName = XML(flowmidlinklist[r]).@name;
									flowmidfigure.appendChild(flowtarget);
								}
							}
							flow.appendChild(flowmidfigure);
						}
						
						delete flow.Figure;
						
						//
						
						
						structure.appendChild(flow);
						
					}
					if(XML(linklist[i]).attribute("sourceID").toString()==midsource && objects[midsource]>1 &&sourcetype=="Switch"){
						//var objectflow :Object = new Object;
						var switchsourcenumber :int;
						var switchtargetnumber :int;
						var switchflow :XML = <flow/>
						var switchlinks :XML = <links/>
						
						
						var switchmidflowtarget :String;
						var switchmidflowsource :String;
						var switchmidflowsequencesource :String;
						var switchmidflowsequencetarget :String;
						
						
						
						for(n=0;n<figurelist.length();n++)
						{
							if(XML(figurelist[n]).attribute("ID").toString()==midsource){
								switchflow.appendChild(XML(figurelist[n]));
							}
						}
						
						for(i=0;i<linklist.length();i++)
						{
							if(XML(linklist[i]).attribute("sourceID").toString()==midsource){
								switchlinks.appendChild(outputBpelLink(XML(linklist[i]),midsource));//kkk
							}
						}
						
						
						switchmidflowsource = midsource;
						switchsourcenumber = objects[midsource];
						
						
						for(k=0;k<linklist.length();k++)
						{
							if(XML(linklist[k]).attribute("sourceID").toString()==switchmidflowsource){
								switchmidflowsequencesource=XML(linklist[k]).attribute("targetID").toString();
								var bugts: int = 0;
								do{//si
									bugts = bugts+1;
									for(j=0;j<linklist.length();j++)
									{
										if(XML(linklist[j]).attribute("sourceID").toString()==switchmidflowsequencesource){
											switchmidflowsequencetarget=XML(linklist[j]).attribute("targetID").toString();
											for(l=0;l<figurelist.length();l++)//
											{
												if(XML(figurelist[l]).attribute("ID").toString()==switchmidflowsequencesource){
													switchflow.appendChild(XML(figurelist[l]));
													
													
													for(m=0;m<linklist.length();m++)
													{
														if(XML(linklist[m]).attribute("sourceID").toString()==switchmidflowsequencesource){
															switchlinks.appendChild(outputBpelLink(XML(linklist[m]),midsource));//kkk
														}
													}
												}
											}
										}
										
										//										if(j==linklist.length()-1&&XML(linklist[j]).attribute("sourceID").toString()!=switchmidflowsequencesource&&objectt[switchmidflowsequencetarget]!=switchsourcenumber){
										//											return bug;
										//										}
										
									}
									
									
									switchmidflowsequencesource = switchmidflowsequencetarget;
									
									if(bugts==100){
										return(bug);
									}
									
								}while(objectt[switchmidflowsequencetarget]!=switchsourcenumber)
								
							}
						}
						
						midsource = switchmidflowsequencetarget;
						
						
						var switchflowlinklist :XMLList = switchlinks.descendants("Link");
						
						delete switchlinks.Link;
						
						for(p=0;p<switchflowlinklist.length();p++)
						{
							if(XML(switchflowlinklist[p]).attribute("targetID").toString()!=midsource){
								switchlinks.appendChild(switchflowlinklist[p]);
							}
						}
						
						switchflow.appendChild(switchlinks);
						
						//
						var switchflowfigurelist :XMLList = switchflow.descendants("Figure");
						switchflowlinklist = switchlinks.descendants("link");
						for(t=0;t<switchflowfigurelist.length();t++)
						{
							var switchmidfigure :XML = outputBpelFigure(XML(switchflowfigurelist[t]));//kkk
							
							
							idstr = XML(switchflowfigurelist[t]).attribute("ID");
							for(r=0;r<switchflowlinklist.length();r++)
							{
								var switchsource :XML = <source/>;
								var switchtarget :XML = <target/>;
								
								if(XML(switchflowlinklist[r]).attribute("sourceID").toString()==idstr){
									switchsource.@linkName = XML(switchflowlinklist[r]).@name;
									if(XML(switchflowlinklist[r]).@transitionCondition!=null&&idstr==XML(switchflowfigurelist[0]).attribute("ID")){
										switchsource.@transitionCondition = XML(switchflowlinklist[r]).@transitionCondition;
									}
									
									switchmidfigure.appendChild(switchsource);
								}
								if(XML(switchflowlinklist[r]).attribute("targetID").toString()==idstr){
									switchtarget.@linkName = XML(switchflowlinklist[r]).@name;
									switchmidfigure.appendChild(switchtarget);
								}
							}
							switchflow.appendChild(switchmidfigure);
						}
						
						delete switchflow.Figure;
						
						//
						
						structure.appendChild(switchflow);
						
					}
				}
				
			}while(midsource!=endid)
			
			delete structure.flow.links.link.@sourceID;
			delete structure.flow.links.link.@targetID;
			delete structure.flow.links.link.@transitionCondition;//transitionCondition show in link or empty
			delete structure.Start;
			
			//			trace("structure is:"+"\n"+structure);
			
			return structure;
		}
		
		/**
		 * trans the figure to BPEL element
		 * @param figurexml
		 * @return
		 *
		 */
		
		private function outputBpelFigure(figurexml:XML) :XML{
			var bpelfigure :XML = new XML;
			var attributelist :XMLList = new XMLList;
			
			attributelist = figurexml.descendants("Attributes").descendants("Attribute");
			
			if(figurexml.@type=="Invoke"){
				bpelfigure = <invoke/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				//bpelfigure.@serviceName = XML(attributelist[3]).child(0).toString();
				//bpelfigure.@serviceLoc = XML(attributelist[4]).child(0).toString();
				bpelfigure.@operation = XML(attributelist[5]).child(0).toString();
				bpelfigure.@partnerLink = XML(attributelist[6]).child(0).toString();
				
				if(XML(attributelist[3]).child(0).toString()!=""){
					bpelfigure.@inputVariable = XML(attributelist[3]).child(0).toString();
				}
				if(XML(attributelist[4]).child(0).toString()!=""){
					bpelfigure.@outputVariable = XML(attributelist[4]).child(0).toString();
				}
				
				
				if(XML(attributelist[7]).child(0).toString()!=""){
					bpelfigure.@portType = XML(attributelist[7]).child(0).toString();
				}
				if(XML(attributelist[8]).child(0).toString()!=""){
					bpelfigure.@suppressJoinFailure = XML(attributelist[8]).child(0).toString();
				}
				
			}
			
			if(figurexml.@type=="Receive"){
				bpelfigure = <receive/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				bpelfigure.@partnerLink = XML(attributelist[6]).child(0).toString();
				bpelfigure.@operation = XML(attributelist[5]).child(0).toString();
				
				if(XML(attributelist[7]).child(0).toString()!=""){
					bpelfigure.@portType = XML(attributelist[7]).child(0).toString();
				}
				
				if(XML(attributelist[3]).child(0).toString()!=""){
					bpelfigure.@variable = XML(attributelist[3]).child(0).toString();
				}
				if(XML(attributelist[4]).child(0).toString()!=""){
					bpelfigure.@createInstance = XML(attributelist[4]).child(0).toString();
				}
				if(XML(attributelist[8]).child(0).toString()!=""){
					bpelfigure.@suppressJoinFailure = XML(attributelist[8]).child(0).toString();
				}
				
			}
			
			if(figurexml.@type=="Reply"){
				bpelfigure = <reply/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				bpelfigure.@partnerLink = XML(attributelist[6]).child(0).toString();
				bpelfigure.@operation = XML(attributelist[5]).child(0).toString();
				
				if(XML(attributelist[7]).child(0).toString()!=""){
					bpelfigure.@portType = XML(attributelist[7]).child(0).toString();
				}
				
				if(XML(attributelist[3]).child(0).toString()!=""){
					bpelfigure.@variable = XML(attributelist[3]).child(0).toString();
				}
				if(XML(attributelist[4]).child(0).toString()!=""){
					bpelfigure.@faultName = XML(attributelist[4]).child(0).toString();
				}
				if(XML(attributelist[8]).child(0).toString()!=""){
					bpelfigure.@suppressJoinFailure = XML(attributelist[8]).child(0).toString();
				}
				
			}
			
			if(figurexml.@type=="Wait"){
				bpelfigure = <wait/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				//				if(XML(attributelist[3]).child(0).toString()!=""){
				//					bpelfigure.@for = XML(attributelist[3]).child(0).toString();
				//				}
				if(XML(attributelist[4]).child(0).toString()!=""){
					bpelfigure.@until = XML(attributelist[4]).child(0).toString();
				}
				if(XML(attributelist[5]).child(0).toString()!=""){
					bpelfigure.@suppressJoinFailure = XML(attributelist[5]).child(0).toString();
				}
				
			}
			
			if(figurexml.@type=="Assign"){
				bpelfigure = <assign/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				var copyelement :XML = <copy/>;
				
				copyelement.@Trom = XML(attributelist[3]).child(0).toString();
				copyelement.@To = XML(attributelist[4]).child(0).toString();
				bpelfigure.appendChild(copyelement);
				bpelfigure.@name = figurexml.@ID;
			}
			
			if(figurexml.@type=="SubProcess"){
				bpelfigure = <invoke/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
			}
			
			if(figurexml.@type=="Email"){
				bpelfigure = <scope/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				var vrs :XML = <variables/>;
				var vr :XML = <variable/>;
				vr.@name = "varNotificationReq";
				vrs.appendChild(vr);
				
				var sq :XML = <sequence/>;
				sq.@name = "sequence_"+figurexml.@ID;
				
				var assign :XML = <assign/>;
				
				var copy1 :XML = <copy/>;
				var from1 :XML = <from/>;
				var to1 :XML = <to/>;
				from1.@expression = "string('"+XML(attributelist[3]).child(0).toString()+"')";
				to1.@variable = "varNotificationReq";
				to1.@part = "email";
				to1.@query = "from account";
				
				copy1.appendChild(from1);
				copy1.appendChild(to1);
				assign.appendChild(copy1);
				
				var copy2 :XML = <copy/>;
				var from2 :XML = <from/>;
				var to2 :XML = <to/>;
				from2.@expression = "string('"+XML(attributelist[4]).child(0).toString()+"')";
				to2.@variable = "varNotificationReq";
				to2.@part = "email";
				to2.@query = "to";
				
				copy2.appendChild(from2);
				copy2.appendChild(to2);
				assign.appendChild(copy2);
				
				var copy3 :XML = <copy/>;
				var from3 :XML = <from/>;
				var to3 :XML = <to/>;
				from3.@expression = "string('"+XML(attributelist[5]).child(0).toString()+"')";
				to3.@variable = "varNotificationReq";
				to3.@part = "email";
				to3.@query = "co";
				
				copy3.appendChild(from3);
				copy3.appendChild(to3);
				assign.appendChild(copy3);
				
				var copy4 :XML = <copy/>;
				var from4 :XML = <from/>;
				var to4 :XML = <to/>;
				from4.@expression = "string('"+XML(attributelist[6]).child(0).toString()+"')";
				to4.@variable = "varNotificationReq";
				to4.@part = "email";
				to4.@query = "bcc";
				
				copy4.appendChild(from4);
				copy4.appendChild(to4);
				assign.appendChild(copy4);
				
				var copy5 :XML = <copy/>;
				var from5 :XML = <from/>;
				var to5 :XML = <to/>;
				from5.@expression = "string('"+XML(attributelist[7]).child(0).toString()+"')";
				to5.@variable = "varNotificationReq";
				to5.@part = "email";
				to5.@query = "reply to";
				
				copy5.appendChild(from5);
				copy5.appendChild(to5);
				assign.appendChild(copy5);
				
				var copy6 :XML = <copy/>;
				var from6 :XML = <from/>;
				var to6 :XML = <to/>;
				from6.@expression = "string('"+XML(attributelist[8]).child(0).toString()+"')";
				to6.@variable = "varNotificationReq";
				to6.@part = "email";
				to6.@query = "subject";
				
				copy6.appendChild(from6);
				copy6.appendChild(to6);
				assign.appendChild(copy6);
				
				var copy7 :XML = <copy/>;
				var from7 :XML = <from/>;
				var to7 :XML = <to/>;
				from7.@expression = "string('"+XML(attributelist[8]).child(0).toString()+"')";
				to7.@variable = "varNotificationReq";
				to7.@part = "email";
				to7.@query = "body";
				
				copy7.appendChild(from7);
				copy7.appendChild(to7);
				assign.appendChild(copy7);
				
				var invoke :XML = <invoke/>
				
				invoke.@name = "InvokeNotificationService";
				invoke.@partnerLink = "";
				invoke.@operation = "";
				invoke.@portType = "";
				invoke.@inputVariable = "varNotificationReq";
				invoke.@outputVariable = "";
				
				sq.appendChild(assign);
				sq.appendChild(invoke);
				
				bpelfigure.appendChild(vrs);
				bpelfigure.appendChild(sq);
			}
			
			if(figurexml.@type=="Fax"){
				bpelfigure = <scope/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				vrs = <variables/>;
				vr = <variable/>;
				vr.@name = "varNotificationReq";
				vrs.appendChild(vr);
				
				sq = <sequence/>;
				sq.@name = "sequence_"+figurexml.@ID;
				
				assign = <assign/>;
				
				copy1 = <copy/>;
				from1 = <from/>;
				to1 = <to/>;
				from1.@expression = "string('"+XML(attributelist[3]).child(0).toString()+"')";
				to1.@variable = "varNotificationReq";
				to1.@part = "fax";
				to1.@query = "fax number";
				
				copy1.appendChild(from1);
				copy1.appendChild(to1);
				assign.appendChild(copy1);
				
				copy2 = <copy/>;
				from2 = <from/>;
				to2 = <to/>;
				from2.@expression = "string('"+XML(attributelist[4]).child(0).toString()+"')";
				to2.@variable = "varNotificationReq";
				to2.@part = "fax";
				to2.@query = "cover page";
				
				copy2.appendChild(from2);
				copy2.appendChild(to2);
				assign.appendChild(copy2);
				
				copy3 = <copy/>;
				from3 = <from/>;
				to3 = <to/>;
				from3.@expression = "string('"+XML(attributelist[5]).child(0).toString()+"')";
				to3.@variable = "varNotificationReq";
				to3.@part = "fax";
				to3.@query = "body";
				
				invoke = <invoke/>
				
				invoke.@name = "InvokeNotificationService";
				invoke.@partnerLink = "";
				invoke.@operation = "";
				invoke.@portType = "";
				invoke.@inputVariable = "varNotificationReq";
				invoke.@outputVariable = "";
				
				sq.appendChild(assign);
				sq.appendChild(invoke);
				
				bpelfigure.appendChild(vrs);
				bpelfigure.appendChild(sq);
			}
			
			if(figurexml.@type=="Pager"){
				bpelfigure = <scope/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				vrs = <variables/>;
				vr = <variable/>;
				vr.@name = "varNotificationReq";
				vrs.appendChild(vr);
				
				sq = <sequence/>;
				sq.@name = "sequence_"+figurexml.@ID;
				
				assign = <assign/>;
				
				copy1  = <copy/>;
				from1  = <from/>;
				to1 = <to/>;
				from1.@expression = "string('"+XML(attributelist[3]).child(0).toString()+"')";
				to1.@variable = "varNotificationReq";
				to1.@part = "pager";
				to1.@query = "from number";
				
				copy1.appendChild(from1);
				copy1.appendChild(to1);
				assign.appendChild(copy1);
				
				copy2  = <copy/>;
				from2 = <from/>;
				to2  = <to/>;
				from2.@expression = "string('"+XML(attributelist[4]).child(0).toString()+"')";
				to2.@variable = "varNotificationReq";
				to2.@part = "pager";
				to2.@query = "pager number";
				
				copy2.appendChild(from2);
				copy2.appendChild(to2);
				assign.appendChild(copy2);
				
				copy3  = <copy/>;
				from3  = <from/>;
				to3 = <to/>;
				from3.@expression = "string('"+XML(attributelist[5]).child(0).toString()+"')";
				to3.@variable = "varNotificationReq";
				to3.@part = "pager";
				to3.@query = "body";
				
				copy3.appendChild(from3);
				copy3.appendChild(to3);
				assign.appendChild(copy3);
				
				invoke  = <invoke/>
				
				invoke.@name = "InvokeNotificationService";
				invoke.@partnerLink = "";
				invoke.@operation = "";
				invoke.@portType = "";
				invoke.@inputVariable = "varNotificationReq";
				invoke.@outputVariable = "";
				
				sq.appendChild(assign);
				sq.appendChild(invoke);
				
				bpelfigure.appendChild(vrs);
				bpelfigure.appendChild(sq);
			}
			
			if(figurexml.@type=="SMS"){
				bpelfigure = <scope/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				vrs  = <variables/>;
				vr = <variable/>;
				vr.@name = "varNotificationReq";
				vrs.appendChild(vr);
				
				sq  = <sequence/>;
				sq.@name = "sequence_"+figurexml.@ID;
				
				assign = <assign/>;
				
				copy1  = <copy/>;
				from1  = <from/>;
				to1  = <to/>;
				from1.@expression = "string('"+XML(attributelist[3]).child(0).toString()+"')";
				to1.@variable = "varNotificationReq";
				to1.@part = "sms";
				to1.@query = "from number";
				
				copy1.appendChild(from1);
				copy1.appendChild(to1);
				assign.appendChild(copy1);
				
				copy2  = <copy/>;
				from2 = <from/>;
				to2 = <to/>;
				from2.@expression = "string('"+XML(attributelist[4]).child(0).toString()+"')";
				to2.@variable = "varNotificationReq";
				to2.@part = "sms";
				to2.@query = "telephone number";
				
				copy2.appendChild(from2);
				copy2.appendChild(to2);
				assign.appendChild(copy2);
				
				copy3  = <copy/>;
				from3  = <from/>;
				to3 = <to/>;
				from3.@expression = "string('"+XML(attributelist[5]).child(0).toString()+"')";
				to3.@variable = "varNotificationReq";
				to3.@part = "sms";
				to3.@query = "subject";
				
				copy3.appendChild(from3);
				copy3.appendChild(to3);
				assign.appendChild(copy3);
				
				copy4  = <copy/>;
				from4  = <from/>;
				to4  = <to/>;
				from4.@expression = "string('"+XML(attributelist[6]).child(0).toString()+"')";
				to4.@variable = "varNotificationReq";
				to4.@part = "sms";
				to4.@query = "body";
				
				copy4.appendChild(from4);
				copy4.appendChild(to4);
				assign.appendChild(copy4);
				
				
				invoke = <invoke/>
				
				invoke.@name = "InvokeNotificationService";
				invoke.@partnerLink = "";
				invoke.@operation = "";
				invoke.@portType = "";
				invoke.@inputVariable = "varNotificationReq";
				invoke.@outputVariable = "";
				
				sq.appendChild(assign);
				sq.appendChild(invoke);
				
				bpelfigure.appendChild(vrs);
				bpelfigure.appendChild(sq);
			}
			
			if(figurexml.@type=="Voice"){
				bpelfigure = <scope/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				
				vrs  = <variables/>;
				vr  = <variable/>;
				vr.@name = "varNotificationReq";
				vrs.appendChild(vr);
				
				sq = <sequence/>;
				sq.@name = "sequence_"+figurexml.@ID;
				
				assign = <assign/>;
				
				copy1 = <copy/>;
				from1 = <from/>;
				to1 = <to/>;
				from1.@expression = "string('"+XML(attributelist[3]).child(0).toString()+"')";
				to1.@variable = "varNotificationReq";
				to1.@part = "voice";
				to1.@query = "telephone number";
				
				copy1.appendChild(from1);
				copy1.appendChild(to1);
				assign.appendChild(copy1);
				
				copy2  = <copy/>;
				from2  = <from/>;
				to2  = <to/>;
				from2.@expression = "string('"+XML(attributelist[4]).child(0).toString()+"')";
				to2.@variable = "varNotificationReq";
				to2.@part = "voice";
				to2.@query = "body";
				
				copy2.appendChild(from2);
				copy2.appendChild(to2);
				assign.appendChild(copy2);
				
				invoke = <invoke/>
				
				invoke.@name = "InvokeNotificationService";
				invoke.@partnerLink = "";
				invoke.@operation = "";
				invoke.@portType = "";
				invoke.@inputVariable = "varNotificationReq";
				invoke.@outputVariable = "";
				
				sq.appendChild(assign);
				sq.appendChild(invoke);
				
				bpelfigure.appendChild(vrs);
				bpelfigure.appendChild(sq);
			}
			
			if(figurexml.@type=="Start"){
				bpelfigure = <Start/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
			}
			
			if(figurexml.@type=="End"){
				bpelfigure = <End/>;
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
			}
			
			if(figurexml.@type=="Activity"){
				bpelfigure = <invoke/>;
				
				bpelfigure.@name = XML(attributelist[2]).child(0).toString()+"_"+figurexml.@ID;
				//bpelfigure.@serviceName = XML(attributelist[3]).child(0).toString();
				//bpelfigure.@serviceLoc = XML(attributelist[4]).child(0).toString();
				//bpelfigure.@inputVariable = XML(attributelist[5]).child(0).toString();
				//bpelfigure.@outputVariable = XML(attributelist[6]).child(0).toString();
				bpelfigure.@operation = XML(attributelist[7]).child(0).toString();
				bpelfigure.@partnerLink = XML(attributelist[8]).child(0).toString();
				//bpelfigure.@portType = XML(attributelist[9]).child(0).toString();
				//bpelfigure.@suppressJoinFailure = XML(attributelist[10]).child(0).toString();
			}
			
			if(figurexml.@type=="Switch"){
				bpelfigure = <empty/>;
			}
			
			
			return bpelfigure;
		}
		
		
		/**
		 * trans link to BPEL link
		 * @param linkxml
		 * @param switchid
		 * @return
		 *
		 */
		private function outputBpelLink(linkxml:XML,switchid:String) :XML{
			var linkfigure :XML = <link/>
			var name :String;
			var figurelist :XMLList = new XMLList;
			var sourcename :String;
			var targetname :String;
			var i :int;
			
			figurelist = this.imformation.descendants("Figure");
			
			for(i=0;i<figurelist.length();i++){
				if(figurelist[i].@ID==linkxml.@sourceID){
					var attributeslist :XMLList = XML(figurelist[i]).descendants("Attributes")
					sourcename = attributeslist.child(2).toString()+"_"+figurelist[i].@ID
				}
			}
			
			for(i=0;i<figurelist.length();i++){
				if(figurelist[i].@ID==linkxml.@targetID){
					attributeslist = XML(figurelist[i]).descendants("Attributes")
					targetname = attributeslist.child(2).toString()+"_"+figurelist[i].@ID
				}
			}
			
			for(i=0;i<figurelist.length();i++){
				if(figurelist[i].@ID==linkxml.@sourceID&&figurelist[i].@ID==switchid){
					//var attributeslist :XMLList = linkxml.descendants("Attributes")
					if(linkxml.@transitionCondition=="Link"){
						linkfigure.@transitionCondition = "true";
					}
					else{
						linkfigure.@transitionCondition = linkxml.@transitionCondition;
					}
				}
			}
			
			name = sourcename+"to"+targetname;
			
			linkfigure.@name = name;
			linkfigure.@sourceID = linkxml.@sourceID;
			linkfigure.@targetID = linkxml.@targetID;
			
			
			
			return linkfigure;
			
			
		}
		
		/**
		 * show the BPEL elements and attributes with color
		 * @param bpel original BPEL elements and attributes, before coloring
		 * @return <code>colorbpel</code> bpel elements and attributes colored
		 *
		 */
		
		public function colorbpel(bpel :String):String{
			
			var colorbpel :String;
			var htmlbpel :String;
			
			var mypattern1 :RegExp = /</g;
			var mypattern2 :RegExp = />/g;
			var mypattern3 :RegExp = /&/g;
			
			//			bpel = outbpel();
			
			bpel = bpel.replace(mypattern3,"&amp;");//MUST BE DONE FIRST, OR CAN NOT SHOW "<"
			bpel = bpel.replace(mypattern1,"&lt;");
			bpel = bpel.replace(mypattern2,"&gt;");
			
			//repalce the attributes
			
			var targetNamespace :RegExp = /targetNamespace=/g;
			var targetNamespace1 :String = attributeFont("targetNamespace");
			bpel = bpel.replace(targetNamespace,targetNamespace1);
			
			//			var queryLanguage :RegExp = / queryLanguage/g;
			//			var queryLanguage1 :String = attributeFont(" queryLanguage");
			//			bpel = bpel.replace(queryLanguage,queryLanguage1);
			//
			//			var expressionLanguage :RegExp = / expressionLanguage/g;
			//			var expressionLanguage1 :String = attributeFont(" expressionLanguage");
			//			bpel = bpel.replace(expressionLanguage,expressionLanguage1);
			
			//			var serviceName :RegExp = / serviceName/g;
			//			var serviceName1 :String = attributeFont(" serviceName");
			//			bpel = bpel.replace(serviceName,serviceName1);
			//
			//			var serviceLoc :RegExp = / serviceLoc/g;
			//			var serviceLoc1 :String = attributeFont(" serviceLoc");
			//			bpel = bpel.replace(serviceLoc,serviceLoc1);
			
			var inputVariable :RegExp = /inputVariable=/g;
			var inputVariable1 :String = attributeFont("inputVariable");
			bpel = bpel.replace(inputVariable,inputVariable1);
			
			var outputVariable :RegExp = /outputVariable=/g;
			var outputVariable1 :String = attributeFont("outputVariable");
			bpel = bpel.replace(outputVariable,outputVariable1);
			
			var operation :RegExp = /operation=/g;
			var operation1 :String = attributeFont("operation");
			bpel = bpel.replace(operation,operation1);
			
			var partnerLink :RegExp = /partnerLink=/g;
			var partnerLink1 :String = attributeFont("partnerLink");
			bpel = bpel.replace(partnerLink,partnerLink1);
			
			var portType :RegExp = /portType=/g;
			var portType1 :String = attributeFont("portType");
			bpel = bpel.replace(portType,portType1);
			
			var name :RegExp = /name=/g;
			var name1 :String = attributeFont("name");
			bpel = bpel.replace(name,name1);
			
			var faultName :RegExp = /faultName=/g;
			var faultName1 :String = attributeFont("faultName");
			bpel = bpel.replace(faultName,faultName1);
			
			var createInstance :RegExp = /createInstance=/g;
			var createInstance1 :String = attributeFont("createInstance");
			bpel = bpel.replace(createInstance,createInstance1);
			
			var suppressJoinFailure :RegExp = /suppressJoinFailure=/g;
			var suppressJoinFailure1 :String = attributeFont("suppressJoinFailure");
			bpel = bpel.replace(suppressJoinFailure,suppressJoinFailure1);
			
			//			var for :RegExp = / for=/g;
			//			var for1 :String = attributeFont(" for");
			//			bpel = bpel.replace(for,for1);
			
			var until :RegExp = /until=/g;
			var until1 :String = attributeFont("until");
			bpel = bpel.replace(until,until1);
			
			var linkName :RegExp = /linkName=/g;
			var linkName1 :String = attributeFont("linkName");
			bpel = bpel.replace(linkName,linkName1);
			
			var transitionCondition :RegExp = /transitionCondition=/g;
			var transitionCondition1 :String = attributeFont("transitionCondition");
			bpel = bpel.replace(transitionCondition,transitionCondition1);
			
			var variable :RegExp = /variable=/g;
			var variable1 :String = attributeFont("variable");
			bpel = bpel.replace(variable,variable1);
			
			var expression :RegExp = /expression=/g;
			var expression1 :String = attributeFont("expression");
			bpel = bpel.replace(expression,expression1);
			
			var part :RegExp = /part=/g;
			var part1 :String = attributeFont("part");
			bpel = bpel.replace(part,part1);
			
			var query :RegExp = /query=/g;
			var query1 :String = attributeFont("query");
			bpel = bpel.replace(query,query1);
			
			var xmlns :RegExp = /xmlns=/g;
			var xmlns1 :String = attributeFont("xmlns");
			bpel = bpel.replace(xmlns,xmlns1);
			
			//replace the element_start
			
			var wait :RegExp = /&lt;wait /g;
			var wait1 :String = elementFont("wait");
			bpel = bpel.replace(wait,wait1);
			
			var reply :RegExp = /&lt;reply /g;
			var reply1 :String = elementFont("reply");
			bpel = bpel.replace(reply,reply1);
			
			var receive :RegExp = /&lt;receive /g;
			var receive1 :String = elementFont("receive");
			bpel = bpel.replace(receive,receive1);
			
			var links :RegExp = /&lt;links /g;
			var links1 :String = elementFont("links");
			bpel = bpel.replace(links,links1);
			
			var link :RegExp = /&lt;link /g;
			var link1 :String = elementFont("link");
			bpel = bpel.replace(link,link1);
			
			var process :RegExp = /&lt;process /g;
			var process1 :String = elementFont("process");
			bpel = bpel.replace(process,process1);
			
			var partnerLinks :RegExp = /&lt;partnerLinks /g;
			var partnerLinks1 :String = elementFont("partnerLinks");
			bpel = bpel.replace(partnerLinks,partnerLinks1);
			
			partnerLink  = /&lt;partnerLink /g;
			partnerLink1 = elementFont("partnerLink");
			bpel = bpel.replace(partnerLink,partnerLink1);
			
			var variables :RegExp = /&lt;variables /g;
			var variables1 :String = elementFont("variables");
			bpel = bpel.replace(variables,variables1);
			
			var sequence :RegExp = /&lt;sequence /g;
			var sequence1 :String = elementFont("sequence");
			bpel = bpel.replace(sequence,sequence1);
			
			var flow :RegExp = /&lt;flow /g;
			var flow1 :String = elementFont("flow");
			bpel = bpel.replace(flow,flow1);
			
			var empty :RegExp = /&lt;empty /g;
			var empty1 :String = elementFont("empty");
			bpel = bpel.replace(empty,empty1);
			
			var source :RegExp = /&lt;source /g;
			var source1 :String = elementFont("source");
			bpel = bpel.replace(source,source1);
			
			var target :RegExp = /&lt;target /g;
			var target1 :String = elementFont("target");
			bpel = bpel.replace(target,target1);
			
			var invoke :RegExp = /&lt;invoke /g;
			var invoke1 :String = elementFont("invoke");
			bpel = bpel.replace(invoke,invoke1);
			
			var assign :RegExp = /&lt;assign /g;
			var assign1 :String = elementFont("assign");
			bpel = bpel.replace(assign,assign1);
			
			var scope :RegExp = /&lt;scope /g;
			var scope1 :String = elementFont("scope");
			bpel = bpel.replace(scope,scope1);
			
			variable = /&lt;variable /g;
			variable1 = elementFont("variable");
			bpel = bpel.replace(variable,variable1);
			
			var copy :RegExp = /&lt;copy /g;
			var copy1 :String = elementFont("copy");
			bpel = bpel.replace(copy,copy1);
			
			var from :RegExp = /&lt;from /g;
			var from1 :String = elementFont("from");
			bpel = bpel.replace(from,from1);
			
			var to :RegExp = /&lt;to /g;
			var to1 :String = elementFont("to");
			bpel = bpel.replace(to,to1);
			
			//replace the element_end
			
			var waite :RegExp = /wait&gt;/g;
			var waite1 :String = elementeFont("wait");
			bpel = bpel.replace(waite,waite1);
			
			var replye :RegExp = /reply&gt;/g;
			var replye1 :String = elementeFont("reply");
			bpel = bpel.replace(replye,replye1);
			
			var receivee :RegExp = /receive&gt;/g;
			var receivee1 :String = elementeFont("receive");
			bpel = bpel.replace(receivee,receivee1);
			
			var linkse :RegExp = /links&gt;/g;
			var linkse1 :String = elementeFont("links");
			bpel = bpel.replace(linkse,linkse1);
			
			var linke :RegExp = /link&gt;/g;
			var linke1 :String = elementeFont("link");
			bpel = bpel.replace(linke,linke1);
			
			var processe :RegExp = /process&gt;/g;
			var processe1 :String = elementeFont("process");
			bpel = bpel.replace(processe,processe1);
			
			var partnerLinkse :RegExp = /partnerLinks&gt;/g;
			var partnerLinkse1 :String = elementeFont("partnerLinks");
			bpel = bpel.replace(partnerLinkse,partnerLinkse1);
			
			var variablese :RegExp = /variables&gt;/g;
			var variablese1 :String = elementeFont("variables");
			bpel = bpel.replace(variablese,variablese1);
			
			var sequencee :RegExp = /sequence&gt;/g;
			var sequencee1 :String = elementeFont("sequence");
			bpel = bpel.replace(sequencee,sequencee1);
			
			var flowe :RegExp = /flow&gt;/g;
			var flowe1 :String = elementeFont("flow");
			bpel = bpel.replace(flowe,flowe1);
			
			var emptye :RegExp = /empty&gt;/g;
			var emptye1 :String = elementeFont("empty");
			bpel = bpel.replace(emptye,emptye1);
			
			var sourcee :RegExp = /source&gt;/g;
			var sourcee1 :String = elementeFont("source");
			bpel = bpel.replace(sourcee,sourcee1);
			
			var targete :RegExp = /target&gt;/g;
			var targete1 :String = elementeFont("target");
			bpel = bpel.replace(targete,targete1);
			
			var invokee :RegExp = /invoke&gt;/g;
			var invokee1 :String = elementeFont("invoke");
			bpel = bpel.replace(invokee,invokee1);
			
			var assigne :RegExp = /assign&gt;/g;
			var assigne1 :String = elementeFont("assign");
			bpel = bpel.replace(assigne,assigne1);
			
			var scopee :RegExp = /scope&gt;/g;
			var scopee1 :String = elementeFont("scope");
			bpel = bpel.replace(scopee,scopee1);
			
			var copye :RegExp = /copy&gt;/g;
			var copye1 :String = elementeFont("copy");
			bpel = bpel.replace(copye,copye1);
			
			
			colorbpel = "<FONT SIZE='12'>"+bpel+"</FONT>";
			return colorbpel;
		}
		
		/**
		 * set the  attribute color and size
		 * @param attribute
		 * @return
		 *
		 */
		
		private function attributeFont(attribute :String) :String{
			var str :String = "</FONT>"+"<FONT SIZE='12' COLOR='#ff0000'>"+attribute+"</FONT>"+"<FONT SIZE='12'>"+"=";
			return str;
		}
		
		/**
		 * set the  element color and size
		 * @param element
		 * @return
		 *
		 */
		
		private function elementFont(element :String) :String{
			var str :String = "&lt;"+"</FONT>"+"<FONT SIZE='12' COLOR='#b56d0f'>"+element+"</FONT>"+"<FONT SIZE='12'>"+" ";
			return str;
		}
		
		/**
		 * set the  element color and size
		 * @param element
		 * @return
		 *
		 */
		
		private function elementeFont(element :String) :String{
			var str :String = "</FONT>"+"<FONT SIZE='12' COLOR='#b56d0f'>"+element+"</FONT>"+"<FONT SIZE='12'>"+"&gt;";
			return str;
		}
		
	}
}