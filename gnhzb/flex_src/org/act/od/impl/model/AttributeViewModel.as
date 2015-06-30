package org.act.od.impl.model{
	
	import mx.collections.ArrayCollection;
	
	import org.act.od.impl.figure.*;
	/**
	 * The model for AttributeView
	 * 
	 * @ author Zhaoxq
	 * 
	 */
	public class AttributeViewModel{
		
		/**
		 * The attribute of the selected figure.
		 */
		[Bindable]
		public var attributes :ArrayCollection;
		/**
		 * The selected figure.
		 */
		public var editedFigure :AbstractFigure;
		
		/**
		 * The data attributes of the selected figure.
		 */
		[Bindable]
		public var dataAttributes:ArrayCollection;
		
		
		/**
		 * Initial the selected figure's attribute.
		 */
		public function AttributeViewModel():void {
			if(editedFigure != null) {
				attributes = editedFigure.getAttributeArray();
			}else{
				attributes = new ArrayCollection();
			}
		}
		/**
		 * Updata the selected figure's attribute.
		 */
		public function updateAttribute():void {
			if(editedFigure != null) {
				editedFigure.setAttribute(attributes);
			}else{
				attributes = new ArrayCollection();
			}
		}
		
		/**
		 * Initial the selected figure's data attribute.
		 */
		public function DataAttributeViewModel():void {
			if(editedFigure != null) {
				dataAttributes = GraphicalFigure(editedFigure).getDataAttributes();
			}else{
				dataAttributes = new ArrayCollection();
			}
		}
		
		/**
		 * Updata the selected figure's data attribute.
		 */
		public function updateDataAttribute():void {
			if(editedFigure != null) {
				GraphicalFigure(editedFigure).setDataAttributes(dataAttributes);
			}else{
				dataAttributes = new ArrayCollection();
			}
		}
		
	}
	
}