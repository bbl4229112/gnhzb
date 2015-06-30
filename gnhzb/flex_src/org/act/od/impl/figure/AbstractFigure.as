package org.act.od.impl.figure
{
	
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	
	import mx.binding.utils.BindingUtils;
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.core.UIComponent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.utils.ObjectProxy;
	import mx.utils.UIDUtil;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.bpmn.BpmnFigureFactory;
	import org.act.od.impl.figure.bpmn.Poolow2Figure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.KnowledgeViewModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.PositionFileSearch;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.vo.BasicAttribute;
	import org.act.od.impl.vo.IAttribute;
	import mx.controls.Alert;
	public class AbstractFigure extends UIComponent implements IFigure
	{  
		//change the type of the variable from protected to public 
		public var sprt:Sprite;
		
		protected var rx:Number; //right x coordinate
		protected var ry:Number; //right y coordinate
		protected var standardwidth:Number; //default width
		protected var standardheight:Number; //default height
		
		[Bindable]
		public var drawid:Number;
		protected var parentNode:IFigure;
		protected var selectedState:Boolean; //whether selected flag
				
		[Bindable]
		public var multiple:Number = 1;
		protected var premultiple:Number = 1;		
		
		[Bindable]
		public var ID:int;
		[Bindable]
		public var figureName:String;
		
		[Bindable]
		public var input:String;
		[Bindable]
		public var output:String;
		[Bindable]
		public var control:String;
		[Bindable]
		public var mechanism:String;
		
		public var attribute:IAttribute; //temporarily public
		
		protected var defaultLineThickness:Number = 2;
		protected var defaultSelectedLineThickness:Number = 6;
		protected var defaultSelectedCircleRadius:Number = 3;
		protected var selectedCircleRadius:Number = defaultSelectedCircleRadius;
		protected var lineThickness:Number = defaultLineThickness;
		protected var selectedLineThickness:Number = defaultSelectedLineThickness;
		
		protected var defaultFontSize:Number = 10;
		protected var fontsize:Number = defaultFontSize;
		
		//process type : bpmn or bpel
		protected var processType:String;
		
		[Bindable]
		public var sonXML:XML;
		public var sonFigureEditorModel:FigureEditorModel;
		public var fileId:String;
		
		
		private var orDesModelLoc:OrDesignerModelLocator;
		private var knowledgeRelated:ArrayCollection;
		public var figureId:String = "";
				
		public function AbstractFigure(processType:String=null)
		{
			this.processType = processType;
			
			super();
			sprt = new Sprite();
			selectedState = false;
			this.addChild( sprt ); 
			
			BindingUtils.bindSetter( this.OutputScale, this, "multiple" );
			knowledgeRelated = OrDesignerModelLocator.getInstance().getKnowledgeViewModel().knowledgeRelated;			
			
			attribute = new BasicAttribute();
//			knowledgeRelated = new ArrayCollection();    //在这里要把关联的知识填充到knowledgeRelated。
			
			if(this.figureId == "") {
				this.figureId = UIDUtil.createUID();
			}

				
		}
		
		public function getKnowledgeRelated():ArrayCollection {
			var remote:RemoteObject = new RemoteObject();
			remote.destination = "knowledgeSource";
			orDesModelLoc = OrDesignerModelLocator.getInstance();
			var activeModel:FigureEditorModel = orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel;  //获得当前xml文件的id，20120908。
			var xmlId:Number = Number(activeModel.rootModel.fileID);
			var figureId:String = this.figureId;
			remote.KnowledgeRelatedList(xmlId, figureId, "related");
			remote.addEventListener(ResultEvent.RESULT, krListResultHandler);
			return this.knowledgeRelated;
		}
		
		public function krListResultHandler(event:ResultEvent):void {
			this.knowledgeRelated = event.result as ArrayCollection;
		}
		
		public function setKnowledgeRelated(kr:ArrayCollection):void {
			this.knowledgeRelated = kr;
		}
		
//		protected function updateAttributeInput(name:String):void
//		{
//			var atts:ArrayCollection = new ArrayCollection();
//			atts.addItem({name:"输入信息",value:input});
//			this.attribute.update(atts);
//		}
//		protected function updateAttributeOutput(name:String):void
//		{
//			var atts:ArrayCollection = new ArrayCollection();
//			atts.addItem({name:"输出信息",value:output});
//			this.attribute.update(atts);
//		}
//		protected function updateAttributeControl(name:String):void
//		{
//			var atts:ArrayCollection = new ArrayCollection();
//			atts.addItem({name:"控制",value:control});
//			this.attribute.update(atts);
//		}
//		protected function updateAttributeMechanism(name:String):void
//		{
//			var atts:ArrayCollection = new ArrayCollection();
//			atts.addItem({name:"机制",value:mechanism});
//			this.attribute.update(atts);
//		}
						
//		protected function setAttributeType( drawid:Number ):void
//		{
//			var type:String;
//			
//			if(this.processType != null)
//			{
//				if(this.processType == FigureEditorModel.BPEL_PROCESS_TYPE)
//					type = FigureFactory.id2name( drawid );
//				else
//					if(this.processType == FigureEditorModel.BPMN_PROCESS_TYPE)
//						type = BpmnFigureFactory.id2name( drawid );
//			}
//			else
//				type = FigureFactory.id2name( drawid );
//			
//			BasicAttribute( attribute ).type = type;
//		}
		
		protected function updateAttributeName( name:String ):void  //没用到的方法，20120809
		{
			//update name attribute
			var atts:ArrayCollection = new ArrayCollection();
			if(name == "input"){
				atts.addItem({ name: "输入信息", value: input });				
			} else if(name == "output"){
				atts.addItem({ name: "输出信息 ", value : output });
			} else if(name == "control"){
				atts.addItem({ name: "控制 ", value : control });				
			} else if(name == "mechanism"){
				atts.addItem({ name: "机制", value : mechanism });				
			}
			this.attribute.update( atts );
		}
		
		public function getIsShowContextPanel():Boolean
		{
			return false;
		}
		
		public function setIsShowContextPanel( isShowContextPanel:Boolean ):void
		{
			
		}
		
		public function setID( ID:int ):void
		{
			this.ID = ID;
//			BasicAttribute( attribute ).id = this.ID;
		}
		
		public function getID():int
		{
			return this.ID;
		}
		
		public function setxy( x:Number, y:Number ):void
		{
			this.setposition( x, y );
		}
		
		public function getx():Number
		{
			return x;
		}
		
		public function gety():Number
		{
			return y;
		}
		
		public function getstandardwidth():Number
		{
			return standardwidth;
		}
		
		public function getstandardheight():Number
		{
			return standardheight;
		}
		
		public function drawclear():void
		{
			this.graphics.clear();
			sprt.graphics.clear();
		}
		
		public function getdrawid():Number
		{
			return drawid;
		}
		
		public function getrx():Number
		{
			return rx;
		}
		
		public function getry():Number
		{
			return ry;
		}
		
		public function getchildren():Array
		{
			return null;
		}
		
		public function addchild( child:IFigure ):void
		{
			
		}
		
		public function addchildWithConnection( child:IFigure ):void
		{
			
		}
		
		public function removechildWithoutConnection( child:IFigure ):void
		{
			
		}
		
		public function removechildWithConnection( child:IFigure, headarr:Array, tailarr:Array ):void
		{
			
		}
		
		public function haschild( child:IFigure ):Boolean
		{
			return false;
		}
		
		public function searchchildWithId( Id:String ):IFigure
		{
			var ifi:IFigure = null;
			
			return ifi;
		}
		
		public function getparent():IFigure
		{
			return parentNode;
		}
		
		public function getrootfigure():IFigure
		{
			var ifi:IFigure = this;
			while ( ifi.getparent())
			{
				ifi = ifi.getparent();
			}
			return ifi;
		}
		
		public function setparent( parent:IFigure ):void
		{
			this.parentNode = parent;
		}
		
		public function getupperfigure( x:Number, y:Number, flag:int = -1 ):IFigure
		{
			
			var temp:IFigure = null;
			var ret:IFigure = null;
			var i:int;
			if ( this.getchildren())
			{
				for ( i = 0; i < this.getchildren().length; i++ )
				{
					temp = IFigure( this.getchildren()[ i ]).getupperfigure( x, y, flag );
					if ( temp != null )
					{
						if ( flag == -1 )
						{
							if ( ret )
							{
								if ( ret.isin( x, y ) < temp.isin( x, y ))
								{
									ret = temp;
								}
							}
							else
							{
								ret = temp;
							}
						}
						else
						{
							ret = temp;
							break;
						}
					}
				}
				
				
			}
			if ( flag == -1 )
			{
				if(this is Poolow2Figure)
				{
					var portTypeId : int = isin(x, y);
					switch ( portTypeId )
					{
						
						case 1: //message flow connector link
							ret = this;
							break;
						
						case 10:
							ret = Poolow2Figure(this).getPortTypeWithNumber(1);
							break;
						
						case 11: //message intermediate Event
							ret = Poolow2Figure(this).getPortTypeWithNumber(2);
							break;
						
						case 12: //message flow connector link
							ret = Poolow2Figure(this).getPortTypeWithNumber(3);
							break;
						
						case 13: //message flow connector link
							ret = Poolow2Figure(this).getPortTypeWithNumber(4);
							break;
						default :
							if(portTypeId >= 10 && portTypeId < (Poolow2Figure(this).PortTypes.length+10) )
								ret = Poolow2Figure(this).getPortTypeWithNumber(portTypeId-10+1);
							break;
					}
				}
				else
					if ( isin( x, y ))
					{
						if ( ret )
						{
							if ( ret.isin( x, y ) < isin( x, y ))
							{
								ret = temp;
							}
							
						}
						else
						{
							ret = this;
						}
					}
			}
			else
			{
				if ( !ret )
				{
					if ( isin( x, y ) == flag )
					{
						ret = this;
					}
				}
			}
			return ret;
		}
		
		public function ifiguretoarray( ar:Array ):void
		{
			var i:int;
			var ifi:IFigure;
			
			if ( ar.indexOf( this ) == -1 )
			{
				//				this.setSelected(true);
				ar.unshift( this );
			}
		}
		
		public function getdrawx():Number
		{
			return getx();
		}
		
		public function getdrawy():Number
		{
			return gety();
		}
		
		public function isSelected():Boolean
		{
			return selectedState;
		}
		
		public function setSelected( isslct:Boolean ):void
		{
			selectedState = isslct;
		}
		
		public function drawSelectedStyle():void
		{
			sprt.graphics.lineStyle( this.defaultSelectedLineThickness * this.multiple, 0x2244ff, 0.4 );
			
			sprt.graphics.drawRoundRect( 0, 0, this.width, this.height, 3, 3 );
			//			sprt.graphics.drawRoundRect( 0, 0, this.width+30, this.height+30, 0, 0 );
			
			sprt.graphics.lineStyle( 2, 0x000000, 1 );
			sprt.graphics.drawCircle( 0, 0, this.selectedCircleRadius );
			sprt.graphics.drawCircle( 0, this.height / 2, this.selectedCircleRadius );
			sprt.graphics.drawCircle( 0, this.height, this.selectedCircleRadius );
			sprt.graphics.drawCircle( this.width / 2, 0, this.selectedCircleRadius );
			sprt.graphics.drawCircle( this.width / 2, this.height, this.selectedCircleRadius );
			sprt.graphics.drawCircle( this.width, 0, this.selectedCircleRadius );
			sprt.graphics.drawCircle( this.width, this.height / 2, this.selectedCircleRadius );
			sprt.graphics.drawCircle( this.width, this.height, this.selectedCircleRadius );
		}
		
		public function updateDraw():void
		{
			this.drawclear();
			var doc:DisplayObjectContainer = this.parent;
			if ( doc )
			{
				doc.removeChild( this );
				doc.addChild( this );
			}
			drawpicture();
			if ( this.selectedState )
			{
				drawSelectedStyle();
			}
			if ( this.parentNode )
			{
				this.parentNode.getchildren().splice( this.parentNode.getchildren().indexOf( this ), 1 );
				this.parentNode.getchildren().unshift( this );
			}
		}
		
		//		test for  compare
		public function compare():void
		{
			this.drawclear();
			var doc:DisplayObjectContainer = this.parent;
			drawpicture();
			if ( this.selectedState )
			{
				sprt.graphics.lineStyle( this.defaultSelectedLineThickness * this.multiple, 0xff0000, 0.6 );
				sprt.graphics.drawRoundRect( -15, -15, this.width+30, this.height+30, 3, 3 );
			}
		}
		
		
		public function getuic():UIComponent
		{
			return this;
		}
		
		public function changedirection( currentX:Number, currentY:Number ):int
		{
			if ( !this.selectedState )
			{
				return 0;
			}
			if ( getDistance( this.x, this.y, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 1;
			}
			if ( getDistance( this.x + this.width / 2, this.y, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 2;
			}
			if ( getDistance( this.x + this.width, this.y, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 3;
			}
			if ( getDistance( this.x + this.width, this.y + this.height / 2, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 4;
			}
			if ( getDistance( this.x + this.width, this.y + this.height, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 5;
			}
			if ( getDistance( this.x + this.width / 2, this.y + this.height, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 6;
			}
			if ( getDistance( this.x, this.y + this.height, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 7;
			}
			if ( getDistance( this.x, this.y + this.height / 2, currentX, currentY ) <= this.selectedCircleRadius )
			{
				return 8;
			}
			return 0;
		}
		
		
		public function drawpicture():void
		{
			
		}
		
		
		public function setposition( x:Number, y:Number ):void
		{
			this.x = x;
			this.y = y;
			this.rx = this.x + this.width / 2;
			this.ry = this.y + this.height / 2;
		}
		
		public function getwidth():Number
		{
			return this.width;
		}
		
		public function getheight():Number
		{
			return this.height;
		}
		
		public function isin( currentX:Number, currentY:Number ):int
		{
			return 0;
		}
		
		
		public function setsize( width:Number, height:Number ):void
		{
			this.width = width;
			this.height = height;
		}
		
		public function autosetsize( arrowX:Number, arrowY:Number, mode:Number ):void
		{
		}
		
		protected function getDistance( x1:Number, y1:Number, x2:Number, y2:Number ):Number
		{
			return Math.sqrt(( x2 - x1 ) * ( x2 - x1 ) + ( y2 - y1 ) * ( y2 - y1 ));
		}
		
		public function getContextPanel():Canvas
		{
			return null;
		}
		
		public function showContextPanel():void
		{
			
		}
		
		public function hideContextPanel():void
		{
			
		}
		
		protected function GetDistancePointToLine( pointX:Number, pointY:Number, lineHeadX:Number, lineHeadY:Number, lineTailX:Number, lineTailY:Number ):Number
		{
			var ret:Number;
			ret = Math.abs((( lineTailY - lineHeadY ) * pointX + ( lineHeadX - lineTailX ) * pointY + lineTailY * ( lineTailX - lineHeadX ) + lineTailX * ( lineHeadY - lineTailY )) / Math.sqrt( Math.pow(( lineTailY - lineHeadY ), 2 ) + Math.pow( lineHeadX - lineTailX, 2 )));
			return ret;
		}
		
		
		public function getAttributeArray():ArrayCollection
		{
			
			return this.attribute.getAttributeArray();
		}
		
		public function setAttribute( attributes:ArrayCollection ):void
		{
			this.attribute.update( attributes );
			
			
		}
		
		public function outputAllInformation():XML
		{			
			var orDesModelLoc:OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			var activeFEModel:FigureEditorModel = orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel;
			var fileID:String;
			var figureEditorVH:FigureEditorVH;
			if ( activeFEModel )
				fileID = activeFEModel.fileID;
			if ( fileID )
				if ( FigureEditorVH.getViewHelperKey( fileID ))
					if ( ViewLocator.getInstance().registrationExistsFor( FigureEditorVH.getViewHelperKey( fileID )))
						figureEditorVH = ViewLocator.getInstance().getViewHelper( FigureEditorVH.getViewHelperKey( fileID )) as FigureEditorVH;
			
			//index figure type
			var figureType:String;
			if ( figureEditorVH )
			{
				if ( figureEditorVH.figureEditor )
				{
					if(activeFEModel.processType == FigureEditorModel.BPEL_PROCESS_TYPE)
					{
						figureType = FigureFactory.id2name( this.drawid );
					}
					else
						if(activeFEModel.processType == FigureEditorModel.BPMN_PROCESS_TYPE)
						{
							figureType = BpmnFigureFactory.id2name( this.drawid );
						}
				}
				else
					figureType = FigureFactory.id2name( this.drawid );
			}
			else
				figureType = FigureFactory.id2name( this.drawid );
			
			
			var info:XML = new XML( "<Figure></Figure>" );
			info.@type = figureType;
			info.@x = this.x;
			info.@y = this.y;
			info.@rx = this.rx;
			info.@ry = this.ry;
			info.@standardwidth = this.standardwidth;
			info.@standardheight = this.standardheight;
			info.@drawid = this.drawid;
			info.@width = this.width;
			info.@height = this.height;
			info.@multiple = this.multiple;
			//			info.@premultiple=this.premultiple;
			info.@ID = this.ID;
			info.@figureName = this.figureName; 
			
			info.@figureId = this.figureId;
			
			var attsXml:XML = new XML( "<Attributes></Attributes>" );
			var attArray:Array = this.attribute.getAttributeXML();
			for each ( var arr:XML in attArray )
			{
				attsXml.appendChild( arr );
			}
			
			info.appendChild( attsXml );
			
			if(this is Poolow2Figure)
			{
				var portTypesXml:XML = new XML( "<PortTypes></PortTypes>" );
				/*var portTypesArray:Array = Poolow2Figure(this).PortTypes;
				var i:int;
				for(i=0;i<portTypesArray.length;i++)
				{
				portTypesXml.appendChild(PortTypeFigure(portTypesArray[i]).outputAllInformation());
				}*/
				portTypesXml.appendChild(Poolow2Figure(this).getPortTypeWithNumber(1).outputAllInformation());
				portTypesXml.appendChild(Poolow2Figure(this).getPortTypeWithNumber(2).outputAllInformation());
				portTypesXml.appendChild(Poolow2Figure(this).getPortTypeWithNumber(3).outputAllInformation());
				portTypesXml.appendChild(Poolow2Figure(this).getPortTypeWithNumber(4).outputAllInformation());
				info.appendChild( portTypesXml );
			}
			return info;
		}
		
		public function readInformationToFigure( info:XML,rootFigureEditorModel:FigureEditorModel,fatherFigureEditorModel:FigureEditorModel):void
		{
			
			this.x = Number( info.@x );
			this.y = Number( info.@y );
			this.rx = Number( info.@rx );
			this.ry = Number( info.@ry );
			this.standardwidth = Number( info.@standardwidth );
			this.standardheight = Number( info.@standardheight );
			this.premultiple = Number( info.@multiple );
			this.multiple = Number( info.@multiple );
			
			
			
			//			this.symbolwidth=Number(info.@symbolwidth);
			//			this.symbolheight=Number(info.@symbolheight);
			
			this.drawid = Number( info.@drawid );
			
			this.setID( Number( info.@ID )); 
			var default_figureName:String = info.@figureName;
			if ( default_figureName != "" )
				this.figureName = info.@figureName;
			else
				info.@figureName = this.figureName; 
			
			if(info.@figureId == ""){
				this.figureId = UIDUtil.createUID();
			}else{
				this.figureId = info.@figureId;
			}
			
			this.width = Number( info.@width );
			this.height = Number( info.@height );
			
			var atts:XMLList = info.children();
			this.attribute.setAttribute( atts );
			
			var submodellist:XMLList = info.elements("subModel");
			var submodel:XML = XML(submodellist[0]);
			var submodelcont:XML = submodel.elements("Process")[0];
			if(submodelcont != null){
				var x:XML = submodelcont;
				var fileidnum:int = OrDesignerModelLocator.getInstance().fileIdNum;
				var _figureEditorModel:FigureEditorModel = new FigureEditorModel(fileidnum+"");
				var feNavModel :FigureEditorNavigatorModel =
					OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				_figureEditorModel = feNavModel.addFigureEditorModel(fileidnum+"","b"+fileidnum);
				trace(_figureEditorModel.fileID+"---------------abstractfigure");
				OrDesignerModelLocator.getInstance().fileIdNum = fileidnum+1; 
				_figureEditorModel.setRootModel(rootFigureEditorModel);
				this.setSonFigureEditorModel(_figureEditorModel);
				_figureEditorModel.setFatherModel(fatherFigureEditorModel);
				//					var xx:XMLList = XML(x).elements();
				//					var xxx:XML = XML(xx[0]);
				//					var ifu:IFigure = _figureEditorModel.rootFigure;
				//					this.readInformationToFigure(xxx);
				_figureEditorModel.rootFigure.readInformationToFigure(x,_figureEditorModel.rootModel,_figureEditorModel);
				
				_figureEditorModel.idManager.setAvailabelId(int(submodellist.@maxId));
				//					
				_figureEditorModel.updateCanvasXML();
				
				this.setSonFigureEditorModel(_figureEditorModel);
				this.setSonXML(_figureEditorModel._canvasXML);
				
			}
		}
		
		public function getMultiple():Number
		{
			return this.multiple;
		}
		
		public function setMultiple( mtp:Number ):void
		{
			this.premultiple = this.multiple;
			this.multiple = mtp;
		}
		
		protected function OutputScale( mtp:Number ):void
		{
			this.x = this.x / this.premultiple * this.multiple;
			this.y = this.y / this.premultiple * this.multiple;
			this.width = this.width / this.premultiple * this.multiple;
			this.height = this.height / this.premultiple * this.multiple;
			this.rx = this.rx / this.premultiple * this.multiple;
			this.ry = this.ry / this.premultiple * this.multiple;
			this.standardheight = this.standardheight / this.premultiple * this.multiple;
			this.standardwidth = this.standardwidth / this.premultiple * this.multiple;
			
			
			this.selectedCircleRadius = this.defaultSelectedCircleRadius * this.multiple;
			this.selectedLineThickness = this.defaultSelectedCircleRadius * this.multiple;
			this.lineThickness = this.defaultLineThickness * this.multiple;
			
			this.fontsize = defaultFontSize * this.multiple;
			
		}
		/**
		 * set the variable  sprt
		 * @param sprt
		 * @return 
		 * 
		 */		
		public function set Sprt(sprt:Sprite):void
		{
			this.sprt = sprt;
		}
		/**
		 * get the variable sprt
		 * @return sprt
		 * 
		 */						
		public function get Sprt():Sprite
		{
			return this.sprt ;
		}
		
		public function setSonXML(value:XML):void{
			this.sonXML = value;
		}
		
		public function setSonFigureEditorModel(value:FigureEditorModel):void{			
			this.sonFigureEditorModel = value;
			if(value != null){
				value.setFatherFigure(this);
			}			
		}
		
		public function setFileId(value:String):void{
			this.fileId = value;
		}
	}
}