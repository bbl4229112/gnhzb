package org.act.od.impl.model
{
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.business.BpelCreator;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.viewhelper.BpelEditorVH;
	/**
	 * The model of BPELEditor. 
	 *@ author Zhaoxq
	 * 
	 */	
	public class BpelEditorModel{
		
		// modelLocator
		private var orDesModelLoc :OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		
		private var feNavModel :FigureEditorNavigatorModel = 
			OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
		private var bpelEditorVH :BpelEditorVH;		
		
		private var _fileID :String;
		
		private var _relativeFigureFileID :String;
		
		private var _bpelText :String;
		
		private var _bpelContent :String;
		
		private var _bpelIsChange :Boolean = true;
		
		private var bpelCreator :BpelCreator = new BpelCreator();
		
		/**
		 * Constructor, 
		 * <p> Initialize _fileID with fileID and _relativeFigureFileID with relativeFigureFileID </p>.		
		 */ 
		
		public function BpelEditorModel(fileID :String, relativeFigureFileID :String){
			this._fileID = fileID;
			this._relativeFigureFileID = relativeFigureFileID;
			
		}
		/**
		 * Get the file ID property.
		 */	
		public function get fileID():String{
			return this._fileID;
		}
		
		/**
		 * Get the file ID of the relative figure. 
		 */	
		public function get relativeFigureFileID() :String{
			return this._relativeFigureFileID;
		}
		/**
		 * @private 
		 */	
		public function set relativeFigureFileID(relativeFigureFileID :String):void{
			this._relativeFigureFileID = relativeFigureFileID;
		}
		
		/**
		 * Get the content of the BPELText in BPEL Editor.
		 * @return original BPEL text
		 * 
		 */	
		public function getOriginalBPELText() :String{
			var m :String = bpelEditorVH.getBpelContent();
			return m;
		}
		/**
		 * Use the activeFigureEditorModel.rootFigure to init bpel textArea
		 * <p> Update Bpel text by the ActiveFigureEditorModel </p>
		 */
		public function updateBpelTextByActiveFEModel() :void{
			this._bpelText = bpelCreator.outBpelStirng(
				ProcessFigure(orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel.rootFigure) );
		}
		
		/**
		 * Use the BPELText to init bpel textArea.
		 * <p> Initialise _bpelContent property with given parameter.</p>
		 * @param content Content of the bpel
		 * 
		 */
		public function updateBpelContent(content :String) :void{
			this._bpelContent = content;
		}
		/**
		 * Get the current content of BPELEditor's BPELText.
		 * <p> Exception if the BPELEditor with this fileID deosn't exist </p>
		 */
		public function getcurrentbpelText() :String{
			if( ViewLocator.getInstance().registrationExistsFor(BpelEditorVH.getViewHelperKey(this.fileID)))
				bpelEditorVH = ViewLocator.getInstance().getViewHelper(BpelEditorVH.getViewHelperKey(this.fileID)) as BpelEditorVH;
			var currentbpelText : String = bpelEditorVH.getBpelContent();
			return currentbpelText;
		}
		
		/**
		 * Get the _bpelText property. 
		 */	
		public function get bpelText() :String{
			return this._bpelText;
		}
		
		/**
		 * @private
		 */
		public function set bpelText(bpelText :String):void{
			this._bpelText = bpelText;
		}
		
		/**
		 * Get the _bpelIsChange property. 
		 */	
		public function get bpelIsChange():Boolean{
			return this._bpelIsChange;
		}
		
		/**
		 * @private
		 */
		public function set bpelIsChange(change :Boolean):void{
			this._bpelIsChange = change;
		}
		
		/**
		 * Get the _bpelContent property. 
		 */
		public function get bpelContent():String{
			return this._bpelContent;
		}
		
		/**
		 * @private
		 */
		public function set bpelContent(content :String):void{
			this._bpelContent = content;
		}
		
		/**
		 * Color the Bpel text given in the parameter
		 * @param bpel Bpel text
		 * @return Bpel text colored
		 * 
		 */	
		public function colorBpelText(bpel :String) :String{
			return bpelCreator.colorbpel(bpel);
		}
	}
	
}