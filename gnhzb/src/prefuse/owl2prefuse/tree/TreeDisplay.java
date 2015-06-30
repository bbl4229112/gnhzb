package prefuse.owl2prefuse.tree;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;

import prefuse.owl2prefuse.Constants;
import prefuse.owl2prefuse.graph.HideDecoratorAction;
import prefuse.owl2prefuse.graph.NodeColorAction;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.ColorAnimator;
import prefuse.action.animate.LocationAnimator;
import prefuse.action.animate.PolarLocationAnimator;
import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.animate.VisibilityAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.filter.FisheyeTreeFilter;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.filter.VisibilityFilter;
import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.ControlAdapter;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Schema;
import prefuse.data.Tree;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.tuple.TupleSet;

import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
import prefuse.visual.VisualTupleSet;
import prefuse.visual.expression.InGroupPredicate;
import prefuse.visual.expression.VisiblePredicate;
import prefuse.visual.sort.TreeDepthItemSorter;

/**
 * This class creates a display for a tree.
 * <p/>
 * Project OWL2Prefuse <br/>
 * TreeDisplay.java created 2 januari 2007, 13:44
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 * 
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.2 $$, $$Date: 2009/09/03 08:15:35 $$
 */
public class TreeDisplay extends Display
{
	private static final Schema DECORATOR_SCHEMA = PrefuseLib
	.getVisualItemSchema();
static {
DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false);
DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(0));
DECORATOR_SCHEMA.setDefault(VisualItem.FONT, FontLib.getFont("GBK", 10));
}
    /**
     * The label renderer for nodes. This renderer shows the nodes with a lable 
     * on them.
     */
    private LabelRenderer m_nodeRenderer;
    
    /**
     * The renderer for the edges.
     */
    private EdgeRenderer m_edgeRenderer;
	private GraphDistanceFilter m_filter;
    /**
     * The orientation of the tree, this is left-to-right by default.
     */
    private int m_orientation = prefuse.Constants.ORIENT_LEFT_RIGHT;
    
    /**
     * The searchpanel, used for the keyword search in the tree.
     */
    private JSearchPanel m_search;
    
    /**
     * The label which displays the URI of the node under the mouse.
     */
    private JEditorPane m_URILabel;
    
    /**
     * The focus control of the tree.
     */
    private FocusControl m_focusControl;
    
    /**
     * Creates a new instance of TreeDisplay.
     * 
     * @param p_tree The Prefuse tree that has to be displayed.
     */
    public TreeDisplay(Tree p_tree,int t)
    {
        // Create a new Display with an empty visualization.
        super(new Visualization());
        
        // Initialize the visualization.
        initVisualization(p_tree,t);
        
        // Initialize this display.
        initDisplay();
        
        // Create the search panel.
        createSearchPanel();
        
        // Create the title label.
        createTitleLabel();
        
        // Filter graph and perform layout.
 //       setOrientation(m_orientation);
        m_vis.run("filter");
    }
    
    /**
     * Add the tree to the visualization as the data group "tree"
     * nodes and edges are accessible as "tree.nodes" and "tree.edges". A
     * renderer is created to render the edges and the nodes in the tree.
     * @param p_tree The Prefuse Tree to be displayed.
     */
    public void initVisualization(Tree p_tree,int t)
    {
        // Add the tree to the visualization.
    	VisualGraph vg =m_vis.addGraph(Constants.TREE, p_tree);
        m_vis.setInteractive(Constants.TREE_NODES, null, true);
     //   p_tree.get
        
        // Create a renderer for the nodes which draws the labels on them.
        //创建结点的渲染
        m_nodeRenderer = new LabelRenderer(Constants.TREE_NODE_LABEL);
        m_nodeRenderer.setRenderType(ShapeRenderer.RENDER_TYPE_FILL);
        m_nodeRenderer.setHorizontalAlignment(prefuse.Constants.LEFT);
        m_nodeRenderer.setRoundedCorner(8,8);
        
        // Create a renderer for the edges.
        //创建边的渲染
        m_edgeRenderer = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_CURVE);
   
        m_edgeRenderer.setArrowType(prefuse.Constants.EDGE_ARROW_FORWARD);
        // Create a render factory with the two previously created renderers.
        //对之前创造的两个渲染类创建渲染工厂
        DefaultRendererFactory rf = new DefaultRendererFactory(m_nodeRenderer);
        rf.add(new InGroupPredicate(Constants.TREE_EDGES), m_edgeRenderer);
        m_vis.addDecorators(Constants.EDGE_DECORATORS, Constants.TREE_EDGES,
				DECORATOR_SCHEMA);
        // Add the render factory to the visualization.
        m_vis.setRendererFactory(rf);
        //针对边界添加箭头
    	m_vis.setValue(Constants.TREE_EDGES, null, VisualItem.INTERACTIVE,
				Boolean.FALSE);
		// 获得第一个结点，并将其聚焦，由此出发关联距离过滤
		// Get the first node and give it focus, this triggers the distance
		// filter
		// 至少展现从根节点到结束的4级的结点
		// to at least show all nodes with a maximum of 4 hops away from this
		// one.
		if (true) {
			VisualItem fs = (VisualItem) vg.getNode(t);
			m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(fs);
			// The position of the first node is not fixed.
			fs.setFixed(true);
		}

        // Set the color for the nodes and the text on the nodes.
  
        ItemAction nodeColor = new NodeColorAction(Constants.TREE_NODES, m_vis);
        ItemAction textColor = new ColorAction(Constants.TREE_NODES, VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0));
        //线条填充颜色（箭头的颜色添加进来了）
        ItemAction arowColor=new ColorAction(Constants.TREE_EDGES, VisualItem.FILLCOLOR, ColorLib.gray(200));
        // Set the color for the edges.
        ItemAction edgeColor = new ColorAction(Constants.TREE_EDGES, VisualItem.STROKECOLOR, ColorLib.rgb(200,200,200));
        
        // Quick repaint
        ActionList repaint = new ActionList();
        repaint.add(nodeColor);
        repaint.add(arowColor);
        repaint.add(new RepaintAction());
        m_vis.putAction("repaint", repaint);
        
        // Full paint
        ActionList fullPaint = new ActionList();
        fullPaint.add(nodeColor);
        fullPaint.add(arowColor);
        m_vis.putAction("fullPaint", fullPaint);
        
        // Animate paint change
        ActionList animatePaint = new ActionList(1000);
        animatePaint.add(new ColorAnimator(Constants.TREE_NODES));
        animatePaint.add(arowColor);
        animatePaint.add(new RepaintAction());
        m_vis.putAction("animatePaint", animatePaint);
        
        // Create the tree layout action
      //代风修改 将treelayout 类型进行更改
      //  RadialTreeLayout treeLayout = new RadialTreeLayout(Constants.TREE);
       NodeLinkTreeLayout treeLayout = new NodeLinkTreeLayout(Constants.TREE, m_orientation, 50, 2, 8);
        treeLayout.setLayoutAnchor(new Point2D.Double(25,300));
        m_vis.putAction("treeLayout", treeLayout);

        // Create the layout for the collapsed subtree.
      CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(Constants.TREE, m_orientation);
        m_vis.putAction("subLayout", subLayout);
   // 	final VisibilityFilter Distance = new VisibilityFilter(Constants.TREE,	VisiblePredicate.TRUE);
        // Create the filtering and layout
        ActionList filter = new ActionList();
  //     filter.add(new FisheyeTreeFilter(Constants.TREE,1));
  
 //        filter.add(new FisheyeTreeFilter(Constants.TREE, 6));
//    	filter.add(Distance);
        filter.add(treeLayout);
        filter.add(subLayout);
        filter.add(new FontAction(Constants.TREE_NODES, FontLib.getFont("GBK", 15)));        
        filter.add(textColor);
        filter.add(nodeColor);
        filter.add(edgeColor);
        filter.add(arowColor);
      
   
        filter.add(new RepaintAction());
        m_vis.putAction("filter", filter);
        
        // Animated transition.
        //变化的动态效果
        //创建action
        ActionList animate = new ActionList(1000);
        //放慢步骤
  
        animate.setPacingFunction(new SlowInSlowOutPacer());
        animate.add(new NodeLinkTreeLayout(Constants.TREE));
        animate.add(new HideDecoratorAction(m_vis));
        animate.add(new RepaintAction());
        animate.add(new TreeRootAction(Constants.TREE));
        //添加控制动画
        animate.add(new QualityControlAnimator());
        //可视动画为真
        animate.add(new VisibilityAnimator(Constants.TREE));
        //位置动画
  //      animate.add(new LocationAnimator(Constants.TREE_NODES));
        animate.add(new PolarLocationAnimator(Constants.TREE_NODES, "linear"));
        //色彩动画
        animate.add(new ColorAnimator(Constants.TREE_NODES));
 
        m_vis.putAction("animate", animate);
        //在filter动画后添加animate动画
      m_vis.alwaysRunAfter("filter", "animate");
        
        // Create animator for orientation changes
        //添加方向变化的动画效果
//        ActionList orient = new ActionList(1000);
//        orient.setPacingFunction(new SlowInSlowOutPacer());
//        orient.add(new QualityControlAnimator());
//        orient.add(new LocationAnimator(Constants.TREE_NODES));
//        orient.add(new RepaintAction());
//        m_vis.putAction("orient", orient);

        // Create a tupleset for the keyword search resultset.
        //添加搜索结果的节点的颜色变化事件
        TupleSet search = new PrefixSearchTupleSet();
        m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, search);
        search.addTupleSetListener(new TupleSetListener()
        {
            public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem)
            {//System.out.println("sdfasdfadsfasdf");
                m_vis.cancel("animatePaint");
                m_vis.run("fullPaint");
                m_vis.run("animatePaint");
            }
        }
        );
        //创建一个tupleset 用户点击时触发事件
     // fix selected focus nodes
		TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS);
		focusGroup.addTupleSetListener(new TupleSetListener() {
			public void tupleSetChanged(TupleSet ts, Tuple[] add,
					Tuple[] rem) {
				for (int i = 0; i < rem.length; ++i)
					((VisualItem) rem[i]).setFixed(false);
				for (int i = 0; i < add.length; ++i) {
					((VisualItem) add[i]).setFixed(false);
					((VisualItem) add[i]).setFixed(true);
				}
				if (ts.getTupleCount() == 0) {
					ts.addTuple(rem[0]);
					((VisualItem) rem[0]).setFixed(false);
				}
				m_vis.run("filter");
			}
		});
        //创建一个tupleset 用户点击时触发事件结束
    }
    
    /**
     * Initialize this display. This method adds several control listeners, sets 
     * the size and sets the foreground and background colors.
     */
    private void initDisplay()
    {
        // Set the display size.
        setSize(500,500);
        
        // Set the foreground color.
        //添加前景色
        setForeground(Constants.FOREGROUND);
        
        // Set the background color.
        //添加背景色
        setBackground(Constants.BACKGROUND);
        
        // Set the iterm so the three depth item sorter.
        
        setItemSorter(new TreeDepthItemSorter());
 
        // Double click for zooming to fit the graph.
        //添加双击变大变小控制
        addControlListener(new ZoomToFitControl());
        
        // Zoom with vertical right-drag.
        //右键  拖拽大小事件
        addControlListener(new ZoomControl());
        
        // Pan with background left-drag.
        addControlListener(new PanControl());
        // Highlight the neighbours.
        //高亮临界点事件监听
        addControlListener(new NeighborHighlightControl());
        
        // Conrol which nodes are in focus.
        //对焦点节点控制时间
        m_focusControl = new FocusControl(1, "filter");
        addControlListener(m_focusControl);
    }
    
    /**
     * Get the focus control of the tree, so it can be adjusted or even removed 
     * to implement a custom made focus control.
     * @return The focus control of the tree.
     */
    public FocusControl getFocusControl()
    {
        return m_focusControl;
    }
    
    /**
     * Set the orientation of the tree, this can be:
     * - left-to-right (prefuse.Constants.ORIENT_LEFT_RIGHT)
     * - right-to-left (prefuse.Constants.ORIENT_RIGHT_LEFT)
     * - top-to-bottom (prefuse.Constants.ORIENT_TOP_BOTTOM)
     * - bottom-to-top (prefuse.Constants.ORIENT_BOTTOM_TOP)
     * @param orientation The orientation of the tree.
     */    
    //设置图像成图方向
//    protected void setOrientation(int orientation)
//    {
//    	//RadialTreeLayout
//    //	RadialTreeLayout rtl = (RadialTreeLayout) m_vis.getAction("treeLayout");
//        NodeLinkTreeLayout rtl = (NodeLinkTreeLayout) m_vis.getAction("treeLayout");
//        CollapsedSubtreeLayout stl = (CollapsedSubtreeLayout) m_vis.getAction("subLayout");
//        switch (orientation)
//        {
//            case prefuse.Constants.ORIENT_LEFT_RIGHT:
//                m_nodeRenderer.setHorizontalAlignment(prefuse.Constants.LEFT);
//                m_edgeRenderer.setHorizontalAlignment1(prefuse.Constants.RIGHT);
//                m_edgeRenderer.setHorizontalAlignment2(prefuse.Constants.LEFT);
//                m_edgeRenderer.setVerticalAlignment1(prefuse.Constants.CENTER);
//                m_edgeRenderer.setVerticalAlignment2(prefuse.Constants.CENTER);
//                break;
//            case prefuse.Constants.ORIENT_RIGHT_LEFT:
//                m_nodeRenderer.setHorizontalAlignment(prefuse.Constants.RIGHT);
//                m_edgeRenderer.setHorizontalAlignment1(prefuse.Constants.LEFT);
//                m_edgeRenderer.setHorizontalAlignment2(prefuse.Constants.RIGHT);
//                m_edgeRenderer.setVerticalAlignment1(prefuse.Constants.CENTER);
//                m_edgeRenderer.setVerticalAlignment2(prefuse.Constants.CENTER);
//                break;
//            case prefuse.Constants.ORIENT_TOP_BOTTOM:
//                m_nodeRenderer.setHorizontalAlignment(prefuse.Constants.CENTER);
//                m_edgeRenderer.setHorizontalAlignment1(prefuse.Constants.CENTER);
//                m_edgeRenderer.setHorizontalAlignment2(prefuse.Constants.CENTER);
//                m_edgeRenderer.setVerticalAlignment1(prefuse.Constants.BOTTOM);
//                m_edgeRenderer.setVerticalAlignment2(prefuse.Constants.TOP);
//                break;
//            case prefuse.Constants.ORIENT_BOTTOM_TOP:
//                m_nodeRenderer.setHorizontalAlignment(prefuse.Constants.CENTER);
//                m_edgeRenderer.setHorizontalAlignment1(prefuse.Constants.CENTER);
//                m_edgeRenderer.setHorizontalAlignment2(prefuse.Constants.CENTER);
//                m_edgeRenderer.setVerticalAlignment1(prefuse.Constants.TOP);
//                m_edgeRenderer.setVerticalAlignment2(prefuse.Constants.BOTTOM);
//                break;
//            default:
//                throw new IllegalArgumentException("Unrecognized orientation value: " + orientation);
//        }
//        m_orientation = orientation;
//        rtl.setOrientation(orientation);
//        stl.setOrientation(orientation);
//    }
    
    /**
     * Get the current orientation of the tree display.
     * @return The Prefuse Constant (int) that represents the current orientation.
     */
    protected int getOrientation()
    {
        return m_orientation;
    }
    
    /**
     * This method creates the keyword searchpanel.
     */
    //创建查询panel
    private void createSearchPanel()
    {
        // Create a search panel for the tree.
        m_search = new JSearchPanel(m_vis, Constants.TREE_NODES, Visualization.SEARCH_ITEMS, Constants.TREE_NODE_LABEL, true, true);
        m_search.setShowResultCount(true);
        m_search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        m_search.setFont(FontLib.getFont("GBK", Font.PLAIN, 11));
        m_search.setBackground(Constants.BACKGROUND);
        m_search.setForeground(Constants.FOREGROUND);
    }
    
    /**
     * This method creates the title label.
     */
	private void createTitleLabel() {
		// Create a label for the title of the nodes.
		m_URILabel = new JEditorPane("text/html","<Strong><font color="+ Integer
						.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
				+">提示：</font></Strong><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
//		m_URILabel = new JFastLabel(" ");
	//	JEditorPane legend = new JEditorPane("text/html","");
		m_URILabel.setPreferredSize(new Dimension(200, 350));
	//	m_URILabel.setVerticalAlignment(SwingConstants.BOTTOM);
		m_URILabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		//m_URILabel.set
		m_URILabel.setFont(FontLib.getFont("GBK", Font.PLAIN, 15));
		m_URILabel.setBackground(Constants.BACKGROUND);
		m_URILabel.setForeground(Constants.FOREGROUND);

		// The control listener for changing the title of a node at a mouseover
		// event.
		this.addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
				if (item.canGetString("label"))
					m_URILabel.setText(item.getString("label"));
			
			}

			public void itemExited(VisualItem item, MouseEvent e) {
				m_URILabel.setText("<Strong><font color="+ Integer
						.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
						+">提示：</font></Strong><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
			}
		});
	}

    /**
     * Returns the keyword searchpanel.
     * @return The keyword searchpanel.
     */
    public JSearchPanel getSearchPanel()
    {
        return m_search;
    }

	/**
	 * Return the GraphDistanceFilter.
	 * 
	 * @return The GraphDistanceFilter.
	 */
	public GraphDistanceFilter getDistanceFilter() {
		return m_filter;
	}
    /**
     * Returns the title label.
     * @return 
     * @return The title label.
     */
    public  JEditorPane getTitleLabel()
    {
        return m_URILabel;
    }
	/**
	 * Switch the root of the tree by requesting a new spanning tree at the
	 * desired root
	 */
	public class TreeRootAction extends GroupAction {
		public TreeRootAction(String graphGroup) {
			super(graphGroup);
		}

		public void run(double frac) {
			TupleSet focus = m_vis.getGroup(Visualization.FOCUS_ITEMS);
			if (focus == null || focus.getTupleCount() == 0)
				return;
         //    System.out.println("m_group="+m_group);
			Graph g = (Graph) m_vis.getGroup(m_group);
			Node f = null;
			while (!g.containsTuple(f = (Node) focus.tuples().next())) {
				f = null;
			}
			if (f == null)
				return;
		//	System.out.println("根节点name="+f.getString("name"));
			g.getSpanningTree(f);

		}
	}

}