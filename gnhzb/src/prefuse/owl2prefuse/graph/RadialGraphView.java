package prefuse.owl2prefuse.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.ColorAnimator;
import prefuse.action.animate.PolarLocationAnimator;
import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.animate.VisibilityAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.filter.FisheyeTreeFilter;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.HoverActionControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Schema;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.GraphMLReader;
import prefuse.data.query.SearchQueryBinding;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.search.SearchTupleSet;
import prefuse.data.tuple.DefaultTupleSet;
import prefuse.data.tuple.TupleSet;
//import prefuse.demos.RadialGraphView.TextColorAction;
import prefuse.owl2prefuse.graph.GraphDisplay2.TreeRootAction;
import prefuse.owl2prefuse.sample.RadialGraphView.TextColorAction;
import prefuse.owl2prefuse.tree.NodeColorAction;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.util.force.ForceSimulator;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import prefuse.visual.sort.TreeDepthItemSorter;


/**
 * Demonstration of a node-link tree viewer
 *
 * @version 1.0
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class RadialGraphView extends Display {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LabelRenderer m_nodeRenderer;
	private EdgeRenderer m_edgeRenderer;

	/**
	 * Create data description of labels, setting colors, and fonts ahead of
	 * time
	 */
	private static final Schema DECORATOR_SCHEMA = PrefuseLib
			.getVisualItemSchema();
	static {
		DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false);
		DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(0));
		DECORATOR_SCHEMA
				.setDefault(VisualItem.FONT, FontLib.getFont("GBK", 10));
	}
	private int m_orientation = prefuse.Constants.ORIENT_LEFT_RIGHT;

	private String directions = "forward";
	/**
	 * The searchpanel, used for the keyword search in the graph.
	 */
	private JSearchPanel m_search;

	/**
	 * The label which displays the URI of the node under the mouse.
	 */
	// private JFastLabel m_URILabel;
	private JEditorPane m_URILabel;
	/**
	 * The GraphDistanceFilter, which makes sure that only the nodes with a
	 * certain number of hops away from the currently selected node, are
	 * displayed.
	 */
	private GraphDistanceFilter m_filter;
	private FisheyeTreeFilter m_filter2;
	private int linetype;
	/**
	 * The focus control of the graph.
	 */
	private FocusControl m_focusControl;

	/**
	 * The force directed layout.
	 */
	private ForceDirectedLayout m_fdl;

	/**
	 * The force simulator of the force directed layout.
	 */
	private ForceSimulator m_fsim;

	/**
	 * Creates a new instance of GraphDisplay
	 * 
	 * @param p_graph
	 *            The Prefuse Graph to be displayed.
	 * @param p_distancefilter
	 *            A boolean, indicating whether or not a GraphDistance filter
	 *            should be used with this display.
	 */
	public RadialGraphView(Graph p_graph) {
		// Create a new Display with an empty visualization.

		super(new Visualization());
		//if (direction.equals("backward")) {
			directions = "backward";
			m_orientation = prefuse.Constants.ORIENT_RIGHT_LEFT;

		

		linetype = 2;
		initVisualization(p_graph);
		initDisplay();

		// Create the search panel.
		createSearchPanel();

		// Create the title label.
		createTitleLabel();

		m_vis.run("draw");
	}

    private static final String tree = "tree";
    private static final String treeNodes = "tree.nodes";
    private static final String treeEdges = "tree.edges";
    private static final String linear = "linear";
    
    /**
     * Switch the root of the tree by requesting a new spanning tree
     * at the desired root
     */
    public static class TreeRootAction extends GroupAction {
        public TreeRootAction(String graphGroup) {
            super(graphGroup);
        }
        public void run(double frac) {
            TupleSet focus = m_vis.getGroup(Visualization.FOCUS_ITEMS);
            if ( focus==null || focus.getTupleCount() == 0 ) return;
            
            Graph g = (Graph)m_vis.getGroup(m_group);
            Node f = null;
            Iterator tuples = focus.tuples();
            while (tuples.hasNext() && !g.containsTuple(f=(Node)tuples.next()))
            {
                f = null;
            }
            if ( f == null ) return;
            g.getSpanningTree(f);
        }
    }
    
    /**
     * Set node fill colors
     */
    public static class NodeColorAction extends ColorAction {
        public NodeColorAction(String group) {
            super(group, VisualItem.FILLCOLOR, ColorLib.rgba(255,255,255,0));
            add("_hover", ColorLib.gray(220,230));
            add("ingroup('_search1_')", ColorLib.rgb(255,190,190));
            add("ingroup('_focus1_')", ColorLib.rgb(198,229,229));
        }
                
    } // end of inner class NodeColorAction
    
    private String m_label = "label";
    public void initVisualization(Graph g) {
     
//        m_label = label;
        createSearchPanel();

		// Create the title label.
		createTitleLabel();
		 m_label = "tree";

	        // -- set up visualization --
	        m_vis.add(tree, g);
	        m_vis.setInteractive(treeEdges, null, false);
	        
	        // -- set up renderers --
	        m_nodeRenderer = new LabelRenderer(m_label);
	        m_nodeRenderer.setRenderType(AbstractShapeRenderer.RENDER_TYPE_FILL);
	        m_nodeRenderer.setHorizontalAlignment(Constants.CENTER);
	        m_nodeRenderer.setRoundedCorner(8,8);
	        m_edgeRenderer = new EdgeRenderer();
	        
	        DefaultRendererFactory rf = new DefaultRendererFactory(m_nodeRenderer);
	        rf.add(new InGroupPredicate(treeEdges), m_edgeRenderer);
	        m_vis.setRendererFactory(rf);
	               
	        // -- set up processing actions --
	        
	        // colors
	        ItemAction nodeColor = new NodeColorAction(treeNodes);
	        ItemAction textColor = new TextColorAction(treeNodes);
	        m_vis.putAction("textColor", textColor);
	        
	        ItemAction edgeColor = new ColorAction(treeEdges,
	                VisualItem.STROKECOLOR, ColorLib.rgb(200,200,200));
	        
	        FontAction fonts = new FontAction(treeNodes, 
	                FontLib.getFont("宋体", 10));
	        fonts.add("ingroup('_focus_')", FontLib.getFont("宋体", 11));
	        
	        // recolor
	        ActionList recolor = new ActionList();
	        recolor.add(nodeColor);
	        recolor.add(textColor);
	        m_vis.putAction("recolor", recolor);
	        
	        // repaint
	        ActionList repaint = new ActionList();
	        repaint.add(recolor);
	        repaint.add(new RepaintAction());
	        m_vis.putAction("repaint", repaint);
	        
	        // animate paint change
	        ActionList animatePaint = new ActionList(400);
	        animatePaint.add(new ColorAnimator(treeNodes));
	        animatePaint.add(new RepaintAction());
	        m_vis.putAction("animatePaint", animatePaint);
	        
	        // create the tree layout action
	        RadialTreeLayout treeLayout = new RadialTreeLayout(tree);
	        //treeLayout.setAngularBounds(-Math.PI/2, Math.PI);
	        m_vis.putAction("treeLayout", treeLayout);
	        
	        CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(tree);
	        m_vis.putAction("subLayout", subLayout);
	        
	        // create the filtering and layout
	        ActionList filter = new ActionList();
	        filter.add(new TreeRootAction(tree));
	        filter.add(fonts);
	        filter.add(treeLayout);
	        filter.add(subLayout);
	        filter.add(textColor);
	        filter.add(nodeColor);
	        filter.add(edgeColor);
	        m_vis.putAction("filter", filter);
	        
	        // animated transition
	        ActionList animate = new ActionList(1250);
	        animate.setPacingFunction(new SlowInSlowOutPacer());
	        animate.add(new QualityControlAnimator());
	        animate.add(new VisibilityAnimator(tree));
	        animate.add(new PolarLocationAnimator(treeNodes, linear));
	        animate.add(new ColorAnimator(treeNodes));
	        animate.add(new RepaintAction());
	        m_vis.putAction("animate", animate);
	        m_vis.alwaysRunAfter("filter", "animate");
	        
	        // ------------------------------------------------
	        
	        // initialize the display
	        setSize(600,600);
	        setItemSorter(new TreeDepthItemSorter());
	        addControlListener(new DragControl());
	        addControlListener(new ZoomToFitControl());
	        addControlListener(new ZoomControl());
	        addControlListener(new PanControl());
	        addControlListener(new FocusControl(1, "filter"));
	        addControlListener(new HoverActionControl("repaint"));
	        
	        // ------------------------------------------------
	        
	        // filter graph and perform layout
	        m_vis.run("filter");
	        
	        // maintain a set of items that should be interpolated linearly
	        // this isn't absolutely necessary, but makes the animations nicer
	        // the PolarLocationAnimator should read this set and act accordingly
	        m_vis.addFocusGroup(linear, new DefaultTupleSet());
	        m_vis.getGroup(Visualization.FOCUS_ITEMS).addTupleSetListener(
	            new TupleSetListener() {
	                public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
	                    TupleSet linearInterp = m_vis.getGroup(linear);
	                    if ( add.length < 1 ) return; linearInterp.clear();
	                    for ( Node n = (Node)add[0]; n!=null; n=n.getParent() )
	                        linearInterp.addTuple(n);
	                }
	            }
	        );
	        
//	        SearchTupleSet search = new PrefixSearchTupleSet();
//	        m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, search);
//	        search.addTupleSetListener(new TupleSetListener() {
//	            public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
//	                m_vis.cancel("animatePaint");
//	                m_vis.run("recolor");
//	                m_vis.run("animatePaint");
//	            }
//	        });

    }
    
    // ------------------------------------------------------------------------
    
 
    
   
    
	/**
	 * Initialize this display. This method adds several control listeners, sets
	 * the size and sets the foreground and background colors.
	 */

	// 图像初始化展示
	private void initDisplay() {
		// Set the display size.
		// 设置展现板块大小
		setSize(800, 600);

		// 设置前景色
		// Set the foreground color.
		setForeground(prefuse.owl2prefuse.Constants.FOREGROUND);
		// 设置背景色
		// Set the background color.
		setBackground(prefuse.owl2prefuse.Constants.BACKGROUND);
		// 添加拖拽事件监听
		// Drag items around.
		addControlListener(new DragControl());
		// 添加左键拖拽事件监听
		// Pan with background left-drag.
		addControlListener(new PanControl());
		// 添加右键改变图像大小事件监听
		// Zoom with vertical right-drag.
		addControlListener(new ZoomControl());

		// Zoom using the scroll wheel.
		// 添加滚轮改变图像大小事件监听
		addControlListener(new WheelZoomControl());
		// 添加双击将图像适应展示窗口事件监听
		// Double click for zooming to fit the graph.
		addControlListener(new ZoomToFitControl());
		setFont(new Font("宋体", 0, 12));
		// Highlight the neighbours.
		// 高亮临界点事件监听
		addControlListener(new NeighborHighlightControl());
		// 焦点结点控制事件监听
		// Conrol which nodes are in focus.
		m_focusControl = new FocusControl(1, "filter");
		addControlListener(m_focusControl);
	}

	/**
	 * Get the focus control of the graph, so it can be adjusted or even removed
	 * to implement a custom made focus control.
	 * 
	 * @return The focus control of the graph.
	 */
	public FocusControl getFocusControl() {
		return m_focusControl;
	}

	/**
	 * This metodh creates a TupleSetListener which listens for changes in a
	 * tuple set.
	 * 
	 * @return A TupleSetListener.
	 */

	/**
	 * Returns the actionlist for the colors of our graph.
	 * 
	 * @param p_distancefilter
	 *            A boolean, indicating whether or not a GraphDistance filter
	 *            should be used with this display.
	 * @return The actionlist containing the actions for the colors.
	 */

	private ActionList getDrawActions(boolean p_distancefilter) {
		// Create an action list containing all color assignments.
		// ActionList draw = new ActionList();
		ActionList draw = new ActionList();
		// draw.setPacingFunction(new SlowInSlowOutPacer());

		// Create the graph distance filter, if wanted.
		// 设置展示到焦点的距离 默认是10
		if (p_distancefilter) {
			// m_filter = new GraphDistanceFilter(Constants.GRAPH, 10);
			// draw.add(m_filter);

		}
		draw.add(new FontAction(prefuse.owl2prefuse.Constants.GRAPH_NODES, FontLib.getFont("GBK",
				15)));
		// Use black for the text on the nodes.对节点的文字颜色进行控制 设置成黑色
		draw.add(new ColorAction(prefuse.owl2prefuse.Constants.GRAPH_NODES, VisualItem.TEXTCOLOR,
				ColorLib.rgb(0, 0, 0)));
		draw.add(new ColorAction(prefuse.owl2prefuse.Constants.GRAPH_NODES, VisualItem.STROKECOLOR,
				ColorLib.gray(0)));

		// Use light grey for edges.对图形边的颜色进行控制初始化 设置成灰色
		draw.add(new ColorAction(prefuse.owl2prefuse.Constants.GRAPH_EDGES, VisualItem.STROKECOLOR,
				ColorLib.gray(200)));
		draw.add(new ColorAction(prefuse.owl2prefuse.Constants.GRAPH_EDGES, VisualItem.FILLCOLOR,
				ColorLib.gray(200)));
		draw.add(new RepaintAction());
		return draw;
	}

	/**
	 * This method sets the parameter of the force directed layout that
	 * determines the distance between the nodes (e.g. the length of the edges).
	 * 
	 * @param p_value
	 *            The new value of the force directed layout.
	 */
	public void setForceDirectedParameter(float p_value) {
		m_fsim.getForces()[0].setParameter(0, p_value);
	}

	/**
	 * Returns the actionlist for the layout of our graph.
	 * 
	 * @return The actionlist containing the layout of the graph.
	 */

	private ActionList getLayoutActions() {

		ActionList layout = new ActionList(1500);
		layout.setPacingFunction(new SlowInSlowOutPacer());
		NodeLinkTreeLayout treeLayout = new NodeLinkTreeLayout(prefuse.owl2prefuse.Constants.GRAPH,
				m_orientation, 50, 2, 8);
		// NodeLinkTreeLayout fdl = new NodeLinkTreeLayout(Constants.GRAPH);
		m_fdl = new ForceDirectedLayout(prefuse.owl2prefuse.Constants.GRAPH);
		m_fsim = m_fdl.getForceSimulator();
		m_fsim.getForces()[0].setParameter(0, -1000f);

		if (directions.equals("backward")) {
			treeLayout.setLayoutAnchor(new Point2D.Double(800, 300));
		}
		m_vis.putAction("treeLayout", treeLayout);
		// layout.add(m_fdl);
		layout.add(treeLayout);// ,m_orientation,50, 30, 20
		CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(
				prefuse.owl2prefuse.Constants.GRAPH, m_orientation);
		m_vis.putAction("subLayout", subLayout);
		layout.add(subLayout);
		// layout.add(new FisheyeTreeFilter(Constants.GRAPH, 2));
		// Add the repaint action.
		layout.add(new RepaintAction());
		layout.add(new TreeRootAction(prefuse.owl2prefuse.Constants.GRAPH));
		m_filter2 = new FisheyeTreeFilter(prefuse.owl2prefuse.Constants.GRAPH, 4);
		layout.add(m_filter2);
		layout.add(new QualityControlAnimator());
		layout.add(new VisibilityAnimator(prefuse.owl2prefuse.Constants.GRAPH));
		layout.add(new PolarLocationAnimator(prefuse.owl2prefuse.Constants.GRAPH_NODES, "linear"));
		layout.add(new ColorAnimator(prefuse.owl2prefuse.Constants.GRAPH_NODES));
		// 对节点颜色进行控制的部分代码 针对节点的不同类型进行相应的控制
		// Create the action which takes care of the coloring of the nodes which
		// are under the mouse.
		layout.add(new NodeColorAction(prefuse.owl2prefuse.Constants.GRAPH_NODES));
		// layout.add(new NodeShapeAction(prefuse.owl2prefuse.Constants.GRAPH_NODES, m_vis));
		// 添加边的关系
		// Add the LabelLayout for the labels of the edges.
		layout.add(new LabelLayout(prefuse.owl2prefuse.Constants.EDGE_DECORATORS, m_vis));

		return layout;
	}

	/**
	 * This method creates the keyword searchpanel.
	 */
	// 添加查询板块
	private void createSearchPanel() {
		// Create a search panel for the tree.
		m_search = new JSearchPanel(m_vis, prefuse.owl2prefuse.Constants.GRAPH_NODES,
				Visualization.SEARCH_ITEMS, prefuse.owl2prefuse.Constants.TREE_NODE_LABEL, true,
				true);
		m_search.setShowResultCount(true);
	
		m_search.setBorder(BorderFactory.createEmptyBorder(5, 5, 4, 0));
		m_search.setFont(FontLib.getFont("GBK", Font.PLAIN, 11));
		m_search.setBackground(prefuse.owl2prefuse.Constants.BACKGROUND);
		m_search.setForeground(prefuse.owl2prefuse.Constants.FOREGROUND);
		
	}

	/**
	 * This method creates the title label.
	 */
	// 添加标题板块
	private void createTitleLabel() {
		// Create a label for the title of the nodes.
		m_URILabel = new JEditorPane(
				"text/html",
				"<font color="
						+ Integer
								.toHexString(prefuse.owl2prefuse.Constants.NODE_COLOR_SELECTED & 0x00ffffff)
						+ ">提示：</font><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
		// m_URILabel = new JFastLabel(" ");
		// JEditorPane legend = new JEditorPane("text/html","");
		m_URILabel.setPreferredSize(new Dimension(100, 150));
		
		// m_URILabel.setVerticalAlignment(SwingConstants.BOTTOM);
		m_URILabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// m_URILabel.set
		//m_URILabel.setFont(FontLib.getFont("GBK", Font.PLAIN, 12));
		m_URILabel.setFont(new Font("宋体", Font.PLAIN, 5));
		m_URILabel.setBackground(prefuse.owl2prefuse.Constants.BACKGROUND);
		m_URILabel.setForeground(prefuse.owl2prefuse.Constants.FOREGROUND);
		
		// The control listener for changing the title of a node at a mouseover
		// event.
		this.addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
				if (item.canGetString("label"))
					m_URILabel.setText(item.getString("label"));
					m_URILabel.setSize(200, 600);
			}
		
			public void itemExited(VisualItem item, MouseEvent e) {
				m_URILabel
						.setText("<font color="
								+ Integer
										.toHexString(prefuse.owl2prefuse.Constants.NODE_COLOR_SELECTED & 0x00ffffff)
								+ ">提示：</font><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
			}
		});
	}

	/**
	 * Returns the keyword searchpanel.
	 * 
	 * @return The keyword searchpanel.
	 */
	public JSearchPanel getSearchPanel() {
		return m_search;
	}

	/**
	 * Returns the title label.
	 * 
	 * @return The title label.
	 */
	public JEditorPane getTitleLabel() {
		return m_URILabel;
	}

	/**
	 * Return the GraphDistanceFilter.
	 * 
	 * @return The GraphDistanceFilter.
	 */
	public GraphDistanceFilter getDistanceFilter() {
		return m_filter;
	}

	public FisheyeTreeFilter getFisheyeTreeFilter() {
		return m_filter2;
	}
    
    // ------------------------------------------------------------------------
    
  
    public static class TextColorAction extends ColorAction {
        public TextColorAction(String group) {
            super(group, VisualItem.TEXTCOLOR, ColorLib.gray(0));
            add("_hover", ColorLib.rgb(255,0,0));
        }
    } // end of inner class TextColorAction
    
    


} // end of class RadialGraphView
