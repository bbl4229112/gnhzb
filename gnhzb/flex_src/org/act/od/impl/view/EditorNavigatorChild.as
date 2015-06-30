package org.act.od.impl.view
{
	import mx.containers.Panel;
	import mx.containers.Canvas;
	
	import org.act.od.impl.model.OrDesignerModelLocator;	
	
	/**
	 * The parent class of FigureEditor and BPELEditor.
	 * It content fileID binding with the file and the file's type.
	 * 
	 * @author Quyue
	 * 
	 */
	public class EditorNavigatorChild extends Canvas{
		
		/**
		 * The flag of FigureEditor.
		 * It used to differentiate FigureEditor and BPELEditor.
		 */
		public static const FIGURE_EDITOR_TYPE :String = "Figure_Editor_Type";
		
		/**
		 * The flag of BPELEditor.
		 * It used to differentiate FigureEditor and BPELEditor.
		 */
		public static const BPEL_EDITOR_TYPE :String = "Bpel_Editor_Type";
		
		private var _type:String;
		
		private var _filePath :String;
		
		private var _fileID :String;
		
		public var _Idnum:Number = new Number();
		
		public var _FileID:String = new String();
		
		public var _Name:String = new String();		
		
		public var _Path:String = new String();
		
		public var _BpleID:String = new String();
		
		public var _OriginalXML:XML = new XML;			
		
		public function EditorNavigatorChild( fileID:String, filePath:String ){
			super();
			this._filePath = filePath;
			this._fileID = fileID;
			
			this._Idnum = OrDesignerModelLocator.getInstance().getOrchestraDesigner().IDnum;
			
			this._FileID = fileID
			
			this._Name = OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name;
			
			this._Path = OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path;	
			
			this._OriginalXML = OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML;			
		}
		
		/**
		 * Return the type message.
		 */
		public function get type():String {
			return this._type;
		}
		/**
		 * Set the type message.
		 */
		public function set type(type:String):void {
			this._type = type;
		}
		/**
		 * return the filePath.
		 */
		public function get filePath():String{
			return this._filePath;
		}
		/**
		 * Set the filePath.
		 */
		public function set filePath( filePath:String):void{
			this._filePath = filePath;
		}
		/**
		 * Return fileID.
		 */
		public function get fileID() :String{
			return this._fileID;
		}		
	}
}