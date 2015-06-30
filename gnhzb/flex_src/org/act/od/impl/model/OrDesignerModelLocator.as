package org.act.od.impl.model{
	
	import org.act.od.framework.model.IModelLocator;
	import org.act.od.impl.view.FileNavigator;
	import org.act.od.impl.view.OrchestraDesigner;
	
	/**
	 * OrDesignerModelLocator is the marker interface used by Cairngorm
	 * applications to implement the model in an Model-View-Controller
	 * architecture.
	 * 
	 * @ author Zhaoxq
	 * 
	 */
	public class OrDesignerModelLocator implements IModelLocator{
		
		/**
		 * Singleton Pattern.
		 */
		private static var odModelLocator : OrDesignerModelLocator;
		
		
		/** 
		 * Attribute view Model.
		 */
		[Bindable]
		private var attributeViewModel :AttributeViewModel;
		[Bindable]
		private var knowledgeInputModel :KnowledgeInputModel;
		[Bindable]
		private var knowledgeViewModel :KnowledgeViewModel;
		[Bindable]
		private var knowledgeOutputModel :KnowledgeOutputModel;
		
		/** 
		 * Primitives editor area Moedl.
		 */
		public var figureEditorNavigatorModel :FigureEditorNavigatorModel;
		
		/** 
		 * TooLBox Model.
		 */
		private var toolPanelModel : ToolPanelModel;
		
		/** 
		 * Primitives editor area data Moedl.
		 */
		private var fcStateDomain :FigureCanvasStateDomain;
		
		/** 
		 * File navigator Model.
		 */
		private var fileNavigatorViewModel:FileNavigatorViewModel;
		
		/**
		 * UDDIRefModel reference model.
		 */
		[Bindable]
		private var UDDIRefModel : UDDIReferenceModel;
		
		/**
		 * The instance of OrchestraDesigner.
		 */
		private var orchestraDesigner :OrchestraDesigner;
		
//		private var businessActivities :BusinessActivities;
		
		public var fileIdNum:int = 1;
		
		/**
		 * Singleton access to the OrDesignerModelLocator is assured through the static getInstance()
		 * method, which is used to retrieve the only OrDesignerModelLocator instance in a Cairngorm
		 * application.
		 * 
		 * <p>Wherever there is a need to retreive the Resource instance, it is achieved
		 * using the following code:</p>
		 * 
		 * <pre>
		 * var orDesignerModelLocator:OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		 * </pre>
		 */
		public static function getInstance() : OrDesignerModelLocator{
			if(odModelLocator == null)
				OrDesignerModelLocator.odModelLocator = new OrDesignerModelLocator(new SingleEnforcer());
			return OrDesignerModelLocator.odModelLocator;
		}
		
		/**
		 * Initial all models.
		 */
		public function OrDesignerModelLocator(enforcer:SingleEnforcer):void {
			super();	
			
			this.attributeViewModel = new AttributeViewModel();
			this.knowledgeInputModel = new KnowledgeInputModel();
			this.knowledgeViewModel = new KnowledgeViewModel();
			this.knowledgeOutputModel = new KnowledgeOutputModel();
			this.figureEditorNavigatorModel = new FigureEditorNavigatorModel();
			this.toolPanelModel = new ToolPanelModel();
			this.fcStateDomain = new FigureCanvasStateDomain();
			this.fileNavigatorViewModel  = new FileNavigatorViewModel();
			this.UDDIRefModel = new UDDIReferenceModel();
		}
		
		public function getKnowledgeInputModel():KnowledgeInputModel {
			return this.knowledgeInputModel;
		}
		public function getKnowledgeViewModel():KnowledgeViewModel {
			return this.knowledgeViewModel;
		}
		public function getKnowledgeOutputModel():KnowledgeOutputModel {
			return this.knowledgeOutputModel;
		}
		
		/**
		 * Return the attributeViewModel.
		 */
		public function getAttributeViewModel() :AttributeViewModel{
			return this.attributeViewModel;
		}
		/**
		 * Return the figureEditorNavigatorModel.
		 */
		public function getFigureEditorNavigatorModel() :FigureEditorNavigatorModel{
			return this.figureEditorNavigatorModel;
		}
		/**
		 * Return the toolPanelModel
		 */
		public function getToolPanelModel() : ToolPanelModel{
			return this.toolPanelModel;
		}
		/**
		 * Return the fcStateDomain.
		 */
		public function getFigureCanvasStateDomain() :FigureCanvasStateDomain{
			return this.fcStateDomain;
		}
		/**
		 * Return the fileNavigatorViewModel
		 */
		public function getFileNavigatorViewModel():FileNavigatorViewModel{
			return this.fileNavigatorViewModel;
		}
		/**
		 * Return the orchestraDesigner.
		 */
		public function getOrchestraDesigner() :OrchestraDesigner{
			return this.orchestraDesigner;
		}
		/**
		 * Set the orchestraDesigner.
		 */
		public function setOrchestraDesigner(od :OrchestraDesigner):void{
			this.orchestraDesigner = od;
		}
//		public function getBusinessActivities():BusinessActivities {
//			return this.businessActivities;
//		}
//		public function setBusinessActivities(ba:BusinessActivities):void {
//			this.businessActivities = ba;
//		}
		/**
		 * Return the UDDIRefModel.
		 */
		public function getUDDIReferenceModel() : UDDIReferenceModel {
			return this.UDDIRefModel;
		}
		/**
		 * Return the FileNavigatorModel.
		 */
		public function getfileNavigator() :FileNavigatorViewModel {
			return this.fileNavigatorViewModel;
		}
	}
	
	
}