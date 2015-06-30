package org.act.od.impl.model{
	
	
	import org.act.od.impl.figure.*;
	import org.act.od.impl.view.*;
	
	/**
	 * Store message of tool panel
	 * 
	 * @ author Zhaoxq
	 * 
	 */
	public class ToolPanelModel{
		
		/**
		 * the selected ToolEntry delegate a Figure
		 */
		private var _selectedFigure : IFigure = null;
		
		public function get selectedFigure() :IFigure{
			return this._selectedFigure;
		}
		
		public function set selectedFigure(selFigure :IFigure) :void{
			this._selectedFigure = selFigure;
		}
		
	}
}