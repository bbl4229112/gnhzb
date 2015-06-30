package prefuse.owl2prefuse.graph;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

import prefuse.owl2prefuse.tree.NodeColorAction;

import prefuse.owl2prefuse.Constants;
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

import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;

import prefuse.action.layout.graph.NodeLinkTreeLayout;

import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Schema;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.tuple.TupleSet;

import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;

import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.util.force.ForceSimulator;

import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;

/**
 * This class creates a display for a graph.
 * <p/>
 * Project OWL2Prefuse <br/>
 * SimpleGraphjava created 3 januari 2007, 11:17
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 * 
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.2 $$, $$Date: 2009/10/09 02:54:08 $$
 */
public class GraphDisplay2 extends Display {
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
	public GraphDisplay2(Graph p_graph, boolean p_distancefilter, int i, int t,
			String direction) {
		// Create a new Display with an empty visualization.

		super(new Visualization());
		if (direction.equals("backward")) {
			directions = "backward";
			m_orientation = prefuse.Constants.ORIENT_RIGHT_LEFT;

		}

		linetype = t;
		initVisualization(p_graph, p_distancefilter, i);
		initDisplay();

		// Create the search panel.
		createSearchPanel();

		// Create the title label.
		createTitleLabel();

		m_vis.run("draw");
	}

	/**
	 * Add the graph to the visualization as the data group "graph" nodes and
	 * edges are accessible as "graph.nodes" and "graph.edges". A renderer is
	 * created to render the edges and the nodes in the graph.
	 * 
	 * @param p_graph
	 *            The Prefuse Graph to be displayed.
	 * @param p_distancefilter
	 *            A boolean, indicating whether or not a GraphDistance filter
	 *            should be used with this display.
	 */
	private void initVisualization(Graph p_graph, boolean p_distancefilter,
			int i) {
		// Add the graph to the visualization.
		VisualGraph vg = m_vis.addGraph(Constants.GRAPH, p_graph);

		// Set up a label renderer for the labels on the nodes and add it to the
		// visualization.

		m_nodeRenderer = new LabelRenderer("name", "image");
		m_nodeRenderer.setRenderType(ShapeRenderer.RENDER_TYPE_FILL);
		m_nodeRenderer.setHorizontalAlignment(prefuse.Constants.LEFT);
		m_nodeRenderer.setVerticalImageAlignment(prefuse.Constants.TOP);
		m_nodeRenderer.setMaxImageDimensions(60, 20);
		m_nodeRenderer.setHorizontalPadding(8);
		m_nodeRenderer.setVerticalPadding(8);
		
		m_nodeRenderer.setImagePosition(0);
		 m_nodeRenderer.setRoundedCorner(8,8);
		// 渲染工厂
		DefaultRendererFactory drf = new DefaultRendererFactory();
		// 添加边渲染
		drf.add(new InGroupPredicate(Constants.GRAPH_NODES), m_nodeRenderer);
		// /添加边的laber渲染
		m_edgeRenderer = new EdgeRenderer(linetype,
				prefuse.Constants.EDGE_ARROW_FORWARD);
		drf.add(new InGroupPredicate(Constants.GRAPH_EDGES), m_edgeRenderer);
		m_vis.setRendererFactory(drf);

		// Add the decorator for the labels.
		m_vis.addDecorators(Constants.EDGE_DECORATORS, Constants.GRAPH_EDGES,
				DECORATOR_SCHEMA);

		// Set the interactive value of the edges to false.
		m_vis.setValue(Constants.GRAPH_EDGES, null, VisualItem.INTERACTIVE,
				Boolean.FALSE);
		// 获得第一个结点，并将其聚焦，由此出发关联距离过滤
		// Get the first node and give it focus, this triggers the distance
		// filter
		// 至少展现从根节点到结束的4级的结点
		// to at least show all nodes with a maximum of 4 hops away from this
		// one.
		if (p_distancefilter) {
			VisualItem f = (VisualItem) vg.getNode(i);
			m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);

			// The position of the first node is not fixed.
			f.setFixed(true);
		}
		ItemAction nodeColor = new NodeColorAction(Constants.GRAPH_NODES, m_vis);
		// ItemAction nodeShape = new NodeShapeAction(Constants.GRAPH_NODES,
		// m_vis);
		ItemAction textColor = new ColorAction(Constants.GRAPH_NODES,
				VisualItem.TEXTCOLOR, ColorLib.rgb(0, 0, 0));
		// 线条填充颜色（箭头的颜色添加进来了）
		ItemAction arowColor = new ColorAction(Constants.GRAPH_EDGES,
				VisualItem.FILLCOLOR, ColorLib.rgb(0, 255, 0));
		// Set the color for the edges.
		ItemAction edgeColor = new ColorAction(Constants.GRAPH_EDGES,
				VisualItem.STROKECOLOR, ColorLib.rgb(200, 200, 200));

		// Quick repaint
		ActionList repaint = new ActionList();
		repaint.add(nodeColor);
		// repaint.add(nodeShape);
		repaint.add(arowColor);
		repaint.add(new RepaintAction());
		m_vis.putAction("repaint", repaint);

		// Full paint
		ActionList fullPaint = new ActionList();
		fullPaint.add(nodeColor);
		// fullPaint.add(nodeShape);
		fullPaint.add(arowColor);
		m_vis.putAction("fullPaint", fullPaint);

		// Animate paint change
		ActionList animatePaint = new ActionList(1000);
		animatePaint.add(new ColorAnimator(Constants.GRAPH_EDGES));
		animatePaint.add(arowColor);
		animatePaint.add(new RepaintAction());
		m_vis.putAction("animatePaint", animatePaint);
		// 最终 将所有的事件列表注册到可视化图形上
		// Finally, we register our ActionList with the Visualization.
		// 同时可以通过下面我们定义的一些名称在可是化图像上执行一些action事件
		// We can later execute our Actions by invoking a method on our
		// Visualization,

		// using the name we have chosen below.
		m_vis.putAction("draw", getDrawActions(p_distancefilter));
		// m_vis.putAction("draw", getDrawActions(p_distancefilter));
		m_vis.putAction("layout", getLayoutActions());
		// m_vis.putAction("layout2", getLayoutActions2());
		// 何必添加为使展示类似于tree
		// Create the filtering and layout
		NodeLinkTreeLayout treeLayout = new NodeLinkTreeLayout(Constants.GRAPH,
				m_orientation, 50, 2, 8);
		if (directions.equals("backward")) {
			treeLayout.setLayoutAnchor(new Point2D.Double(800, 300));
		} else
			treeLayout.setLayoutAnchor(new Point2D.Double(100, 300));
		m_vis.putAction("treeLayout", treeLayout);

		// Create the layout for the collapsed subtree.
		CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(
				Constants.GRAPH, m_orientation);
		m_vis.putAction("subLayout", subLayout);

		ActionList filter = new ActionList();
		m_filter2 = new FisheyeTreeFilter(Constants.GRAPH, 2);
		filter.add(m_filter2);
		filter.add(treeLayout);
		filter.add(subLayout);
		filter.add(new FontAction(Constants.GRAPH_NODES, FontLib.getFont("GBK",
				12)));
		filter.add(textColor);
		// filter.add(nodeShape);
		filter.add(nodeColor);
		filter.add(edgeColor);

		m_vis.putAction("filter", filter);

		// Animated transition.
		ActionList animate = new ActionList(1000);
		animate.setPacingFunction(new SlowInSlowOutPacer());
		animate.add(new QualityControlAnimator());
		animate.add(new VisibilityAnimator(Constants.GRAPH));
		animate.add(new LocationAnimator(Constants.GRAPH_NODES));
		animate.add(new ColorAnimator(Constants.GRAPH_NODES));
		animate.add(new RepaintAction());

		m_vis.putAction("animate", animate);
		m_vis.alwaysRunAfter("filter", "animate");
		// 结束
		// m_vis.putAction("layout", getAnimateActions());
		m_vis.alwaysRunAfter("draw", "layout");
		// m_vis.alwaysRunAfter("draw", "layout2");
		// Create a tupleset for the keyword search resultset.
		// 添加搜索结果的节点的颜色变化事件
		TupleSet search = new PrefixSearchTupleSet();
		m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, search);
		search.addTupleSetListener(new TupleSetListener() {
			public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
				m_vis.cancel("animatePaint");
				m_vis.run("fullPaint");
				m_vis.run("animatePaint");
			}
		});
		;
		// 创建一个tupleset 用户点击时触发事件
		// Create a focus listener which fixex the position of the selected
		// nodes.
		// fix selected focus nodes
		TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS);
		focusGroup.addTupleSetListener(new TupleSetListener() {
			public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem) {
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
		// 创建一个tupleset 用户点击时触发事件结束
		// m_nodeRenderer.getImageFactory().preloadImages(m_vis.items(),"image");
	}

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
		setForeground(Constants.FOREGROUND);
		// 设置背景色
		// Set the background color.
		setBackground(Constants.BACKGROUND);
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
		draw.add(new FontAction(Constants.GRAPH_NODES, FontLib.getFont("GBK",
				15)));
		// Use black for the text on the nodes.对节点的文字颜色进行控制 设置成黑色
		draw.add(new ColorAction(Constants.GRAPH_NODES, VisualItem.TEXTCOLOR,
				ColorLib.rgb(0, 0, 0)));
		draw.add(new ColorAction(Constants.GRAPH_NODES, VisualItem.STROKECOLOR,
				ColorLib.gray(0)));

		// Use light grey for edges.对图形边的颜色进行控制初始化 设置成灰色
		draw.add(new ColorAction(Constants.GRAPH_EDGES, VisualItem.STROKECOLOR,
				ColorLib.gray(200)));
		draw.add(new ColorAction(Constants.GRAPH_EDGES, VisualItem.FILLCOLOR,
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
		NodeLinkTreeLayout treeLayout = new NodeLinkTreeLayout(Constants.GRAPH,
				m_orientation, 50, 2, 8);
		// NodeLinkTreeLayout fdl = new NodeLinkTreeLayout(Constants.GRAPH);
		m_fdl = new ForceDirectedLayout(Constants.GRAPH);
		m_fsim = m_fdl.getForceSimulator();
		m_fsim.getForces()[0].setParameter(0, -1000f);

		if (directions.equals("backward")) {
			treeLayout.setLayoutAnchor(new Point2D.Double(800, 300));
		}
		m_vis.putAction("treeLayout", treeLayout);
		// layout.add(m_fdl);
		layout.add(treeLayout);// ,m_orientation,50, 30, 20
		CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(
				Constants.GRAPH, m_orientation);
		m_vis.putAction("subLayout", subLayout);
		layout.add(subLayout);
		// layout.add(new FisheyeTreeFilter(Constants.GRAPH, 2));
		// Add the repaint action.
		layout.add(new RepaintAction());
		layout.add(new TreeRootAction(Constants.GRAPH));
		m_filter2 = new FisheyeTreeFilter(Constants.GRAPH, 4);
		layout.add(m_filter2);
		layout.add(new QualityControlAnimator());
		layout.add(new VisibilityAnimator(Constants.GRAPH));
		layout.add(new PolarLocationAnimator(Constants.GRAPH_NODES, "linear"));
		layout.add(new ColorAnimator(Constants.GRAPH_NODES));
		// 对节点颜色进行控制的部分代码 针对节点的不同类型进行相应的控制
		// Create the action which takes care of the coloring of the nodes which
		// are under the mouse.
		layout.add(new NodeColorAction(Constants.GRAPH_NODES, m_vis));
		// layout.add(new NodeShapeAction(Constants.GRAPH_NODES, m_vis));
		// 添加边的关系
		// Add the LabelLayout for the labels of the edges.
		layout.add(new LabelLayout(Constants.EDGE_DECORATORS, m_vis));

		return layout;
	}

	/**
	 * This method creates the keyword searchpanel.
	 */
	// 添加查询板块
	private void createSearchPanel() {
		// Create a search panel for the tree.
		m_search = new JSearchPanel(m_vis, Constants.GRAPH_NODES,
				Visualization.SEARCH_ITEMS, Constants.TREE_NODE_LABEL, true,
				true);
		m_search.setShowResultCount(true);
	
		m_search.setBorder(BorderFactory.createEmptyBorder(5, 5, 4, 0));
		m_search.setFont(FontLib.getFont("GBK", Font.PLAIN, 11));
		m_search.setBackground(Constants.BACKGROUND);
		m_search.setForeground(Constants.FOREGROUND);
		
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
								.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
						+ ">提示：</font><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
		// m_URILabel = new JFastLabel(" ");
		// JEditorPane legend = new JEditorPane("text/html","");
		m_URILabel.setPreferredSize(new Dimension(130,200));
		
		// m_URILabel.setVerticalAlignment(SwingConstants.BOTTOM);
		m_URILabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// m_URILabel.set
		//m_URILabel.setFont(FontLib.getFont("GBK", Font.PLAIN, 12));
		m_URILabel.setFont(new Font("宋体", Font.PLAIN, 5));
		m_URILabel.setBackground(Constants.BACKGROUND);
		m_URILabel.setForeground(Constants.FOREGROUND);
		
		// The control listener for changing the title of a node at a mouseover
		// event.
		this.addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
				if (item.canGetString("label"))
					m_URILabel.setText(item.getString("label"));
					m_URILabel.setSize(150, 150);
			}
		
			public void itemExited(VisualItem item, MouseEvent e) {
				m_URILabel
						.setText("<font color="
								+ Integer
										.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
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

			Graph g = (Graph) m_vis.getGroup(m_group);
			Node f = null;
			while (!g.containsTuple(f = (Node) focus.tuples().next())) {
				f = null;
			}
			if (f == null)
				return;
			// System.out.println("根节点name="+f.getString("name"));
			g.getSpanningTree(f);
		}
	}
}