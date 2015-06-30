package org.act.od.impl.view
{
	import flash.events.Event;
	
	import mx.binding.utils.BindingUtils;
	import mx.controls.TextArea;
	
	import org.act.od.impl.model.*;
	import org.act.od.impl.viewhelper.BpelEditorVH;
	
	/**
	 * The editor for BPEL file.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class BpelEditor extends EditorNavigatorChild{
		
		private var _textArea :TextArea = new TextArea;
		
		private var _bpelEditorModel :BpelEditorModel = null;
		
		private var bpelEditorVH :BpelEditorVH;
		
		/**
		 *Constructor, 
		 * Initialize the BPELEditor.
		 *  
		 * @param filePath file path.
		 * @param fileName file name.
		 * @param beModel bpel editor model.
		 * 
		 */		
		public function BpelEditor(filePath :String, fileName :String, beModel :BpelEditorModel){
			
			super(beModel.fileID, filePath);
			
			this.percentHeight = 100;
			this.setStyle("headerHeight","5");
			this.percentWidth = 100;
			_textArea.percentHeight=100;
			_textArea.percentWidth=100;
			_textArea.addEventListener(Event.CHANGE,changeHandler);
			this.addChild(_textArea);
			
			this.label = fileName;
			this.type = EditorNavigatorChild.BPEL_EDITOR_TYPE;
			
			//model
			this._bpelEditorModel = beModel;
			
			//view helper
			this.bpelEditorVH = new BpelEditorVH(this, BpelEditorVH.getViewHelperKey(beModel.fileID));
			
			BindingUtils.bindProperty(_textArea, "htmlText" ,this._bpelEditorModel, "bpelText");
			//			BindingUtils.bindProperty(textArea, "text" ,this._bpelEditorModel, "bpelContent");
		}
		/**
		 * Getter, 
		 * <p> Return BPELEditorModel.</p>
		 */
		public function get bpelEditorModel():BpelEditorModel {
			return this._bpelEditorModel;
		}
		
		/**
		 * Return the content of the textArea.
		 */
		public function getBpelContent() :String{
			return this._textArea.text;
		}
		private function changeHandler(event :Event):void{
			this._bpelEditorModel.bpelIsChange = true;
		}
	}
}