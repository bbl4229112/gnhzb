package prefuse.owl2prefuse.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JTextField;

import prefuse.owl2prefuse.Constants;
import prefuse.owl2prefuse.graph.readial.HideDecoratorAction;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.Display.InputEventCapturer;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.ColorAnimator;

import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.animate.VisibilityAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.FontAction;

import prefuse.action.filter.GraphDistanceFilter;

import prefuse.action.layout.AxisLayout;
import prefuse.action.layout.graph.BalloonTreeLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.action.layout.graph.FruchtermanReingoldLayout;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.action.layout.graph.SquarifiedTreeMapLayout;
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

import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.util.UpdateListener;
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
 * @version $$Revision: 1.4 $$, $$Date: 2009/10/09 02:54:08 $$
 */
public class relationGraphDisplay extends Display {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create data description of labels, setting colors, and fonts ahead of
	 * time
	 */
	private static final Schema DECORATOR_SCHEMA = PrefuseLib
			.getVisualItemSchema();
	static {
		DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false);
		DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(0));
		DECORATOR_SCHEMA.setDefault(VisualItem.FONT, FontLib.getFont("UTF-8",
				10));
	}

	private int n_orientation = prefuse.Constants.LEFT;
	// private String directions="forward";
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
	private int linetype;
	/**
	 * The focus control of the graph.
	 */
	private FocusControl m_focusControl;

	/**
	 * The force directed layout.
	 */
	// private ForceDirectedLayout m_fdl;

	/**
	 * The force simulator of the force directed layout.
	 */
	private ForceSimulator m_fsim;
	private String searchname;

	/**
	 * Creates a new instance of GraphDisplay
	 * 
	 * @param p_graph
	 *            The Prefuse Graph to be displayed.
	 * @param p_distancefilter
	 *            A boolean, indicating whether or not a GraphDistance filter
	 *            should be used with this display.
	 */
	public relationGraphDisplay(Graph p_graph, boolean p_distancefilter, int i,
			int t, String direction, String searchname) {
		// Create a new Display with an empty visualization.

		super(new Visualization());
		if (direction.equals("backward")) { // directions="backward";
			// m_orientation = prefuse.Constants.ORIENT_RIGHT_LEFT;
			n_orientation = prefuse.Constants.RIGHT;
		}
		this.searchname = searchname;
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
	public void initVisualization(Graph p_graph, boolean p_distancefilter, int i) {
		// Add the graph to the visualization.
		VisualGraph vg = m_vis.addGraph(Constants.GRAPH, p_graph);

		// Set up a label renderer for the labels on the nodes and add it to the
		// visualization.
		LabelRenderer nodeRenderer = new LabelRenderer("name", "image");
		// nodeRenderer.setHorizontalAlignment(prefuse.Constants.RIGHT);

		nodeRenderer.setHorizontalAlignment(n_orientation);
		nodeRenderer.setHorizontalAlignment(prefuse.Constants.LEFT);
		nodeRenderer.setVerticalImageAlignment(prefuse.Constants.TOP);
		nodeRenderer.setMaxImageDimensions(60, 20);
		nodeRenderer.setHorizontalPadding(8);
		nodeRenderer.setVerticalPadding(8);

		nodeRenderer.setImagePosition(0);
		nodeRenderer.setRoundedCorner(8, 8);
		// 渲染工厂
		DefaultRendererFactory drf = new DefaultRendererFactory();
		// 添加边渲染
		drf.add(new InGroupPredicate(Constants.GRAPH_NODES), nodeRenderer);
		// /添加边的laber渲染
		// drf.add(new InGroupPredicate(Constants.EDGE_DECORATORS),new
		// LabelRenderer("label"));
		// 添加边类型渲染
		EdgeRenderer m_edgeRenderer = new EdgeRenderer(linetype,
				prefuse.Constants.EDGE_ARROW_FORWARD);

		// m_edgeRenderer.setHorizontalAlignment1(prefuse.Constants.LEFT);
		// m_edgeRenderer.setHorizontalAlignment2(prefuse.Constants.RIGHT);
		// m_edgeRenderer.setVerticalAlignment1(prefuse.Constants.CENTER);
		// m_edgeRenderer.setVerticalAlignment2(prefuse.Constants.CENTER);

		drf.add(new InGroupPredicate(Constants.EDGE_DECORATORS),
				new LabelRenderer("label"));

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
		// ItemAction textColor = new ColorAction(Constants.GRAPH_NODES,
		// VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0));
		// 线条填充颜色（箭头的颜色添加进来了）
		ItemAction arowColor = new ColorAction(Constants.GRAPH_EDGES,
				VisualItem.FILLCOLOR, ColorLib.rgb(0, 255, 0));
		// Set the color for the edges.
		// ItemAction edgeColor = new ColorAction(Constants.GRAPH_EDGES,
		// VisualItem.STROKECOLOR, ColorLib.rgb(200,200,200));

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
		m_vis.putAction("layout", getLayoutActions());
		// m_vis.putAction("layout", getAnimateActions());
		m_vis.alwaysRunAfter("draw", "layout");
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
//				Graph g = null;
//				try {
//					g = getGraph(searchname);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				// m_vis.removeGroup(Constants.GRAPH);
				// setDoubleBuffered(false);
				// setBackground(Color.WHITE);
				//			        
				// // initialize text editor
				//			      
				//			        
				// // register input event capturer
				// InputEventCapturer iec = new InputEventCapturer();
				// addMouseListener(iec);
				// addMouseMotionListener(iec);
				// addMouseWheelListener(iec);
				// addKeyListener(iec);
				//			        
				// registerDefaultCommands();
				//			        
				// // invalidate the display when the filter changes
				// m_predicate.addExpressionListener(new UpdateListener() {
				// public void update(Object src) { damageReport(); }
				// });
				//			        
				// setVisualization(new Visualization());
				//			      
				// setSize(400,400); // set a default size
				//		
				// initVisualization(g, true, 1);
				//				
				// initDisplay();
				//
				// // Create the search panel.
				// createSearchPanel();
				//
				// // Create the title label.
				// createTitleLabel();

				 m_vis.run("draw");
			}
		});
		// 创建一个tupleset 用户点击时触发事件结束
	}



	/**
	 * Initialize this display. This method adds several control listeners, sets
	 * the size and sets the foreground and background colors.
	 */

	// 图像初始化展示
	public void initDisplay() {
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

		// Highlight the neighbours.
		// 高亮临界点事件监听
		addControlListener(new NeighborHighlightControl());
		// 焦点结点控制事件监听
		// Conrol which nodes are in focus.
		m_focusControl = new FocusControl(1, "draw");
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
		// 设置展示到焦点的距离 默认是3
		if (p_distancefilter) {
			m_filter = new GraphDistanceFilter(Constants.GRAPH, 16);
			draw.add(m_filter);

		}
		draw.add(new FontAction(Constants.GRAPH_NODES, FontLib.getFont("UTF-8",
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

		// Make sure the nodes to not get to close together.
//		 NodeLinkTreeLayout fdl = new NodeLinkTreeLayout(Constants.GRAPH);
//		 m_fdl = new ForceDirectedLayout(Constants.GRAPH);
//		 m_fsim = m_fdl.getForceSimulator();
//		 m_fsim.getForces()[0].setParameter(0, -1000f);
//
//		// Create an action list with an animated layout, the INFINITY parameter
//		// tells the action list to run indefinitely.
//		 ActionList layout = new ActionList(Activity.INFINITY);
//
//		// Add the force directed layout.
//		 layout.add(new CollapsedSubtreeLayout(Constants.GRAPH));
//
//		 layout.add(m_fdl);
		// frocedirect展示
		
		ActionList layout = new ActionList(1500);
		 //layout.add(new ForceDirectedLayout(Constants.GRAPH,true,true));
			 //保龄球展示
			 //layout.add(new BalloonTreeLayout(Constants.GRAPH));
			 //不知道怎么翻译的展示
			 //layout.add(new FruchtermanReingoldLayout(Constants.GRAPH));
			// 正方形展示
			 //layout.add(new SquarifiedTreeMapLayout(Constants.GRAPH));
			 //隐藏节点间关系的action 使节点间关系只有在鼠标悬停的时候才显示出来
			 //layout.add(new AxisLayout(Constants.GRAPH,"label"));
		layout.setPacingFunction(new SlowInSlowOutPacer());
		RadialTreeLayout treeLayout = new RadialTreeLayout(Constants.GRAPH, 350);

		m_vis.putAction("treeLayout", treeLayout);
		layout.add(treeLayout);// ,m_orientation,50, 30, 20
		// CollapsedSubtreeLayout subLayout = new
		// CollapsedSubtreeLayout(Constants.GRAPH, m_orientation);
		// m_vis.putAction("subLayout", subLayout);
		// layout.add(subLayout);
		// layout.add(new FisheyeTreeFilter(Constants.GRAPH, 5));
		// Add the repaint action.
		layout.add(new RepaintAction());
//		layout.add(new HideDecoratorAction(m_vis));
		// layout.add(new TreeRootAction(Constants.GRAPH));
		layout.add(new QualityControlAnimator());
		layout.add(new VisibilityAnimator(Constants.GRAPH));
		// layout.add(new PolarLocationAnimator(Constants.GRAPH_NODES,
		// "linear"));
		// layout.add(new ColorAnimator(Constants.GRAPH_NODES));
		// 对节点颜色进行控制的部分代码 针对节点的不同类型进行相应的控制
		// Create the action which takes care of the coloring of the nodes which
		// are under the mouse.
		layout.add(new NodeColorAction(Constants.GRAPH_NODES, m_vis));
		// 添加边的关系
		// Add the LabelLayout for the labels of the edges.
		layout.add(new LabelLayout(Constants.EDGE_DECORATORS, m_vis));

		return layout;
	}

	/**
	 * This method creates the keyword searchpanel.
	 */
	// 添加查询板块
	public void createSearchPanel() {
		// Create a search panel for the tree.
		m_search = new JSearchPanel(m_vis, Constants.GRAPH_NODES,
				Visualization.SEARCH_ITEMS, Constants.TREE_NODE_LABEL, true,
				true);
		m_search.setShowResultCount(true);
		m_search.setBorder(BorderFactory.createEmptyBorder(5, 5, 4, 0));

		m_search.setFont(FontLib.getFont("UTF-8", Font.PLAIN, 11));
		m_search.setBackground(Constants.BACKGROUND);
		m_search.setForeground(Constants.FOREGROUND);
	}

	/**
	 * This method creates the title label.
	 */
	// 添加标题板块
	public void createTitleLabel() {
		// Create a label for the title of the nodes.
		m_URILabel = new JEditorPane(
				"text/html",
				"<Strong><font color="
						+ Integer
								.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
						+ ">提示：</font></Strong><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
		// m_URILabel = new JFastLabel(" ");
		// JEditorPane legend = new JEditorPane("text/html","");
		m_URILabel.setPreferredSize(new Dimension(120, 220));
		// m_URILabel.setVerticalAlignment(SwingConstants.BOTTOM);
		m_URILabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		// m_URILabel.set
		m_URILabel.setFont(FontLib.getFont("UTF-8", Font.PLAIN, 15));
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
				m_URILabel
						.setText("<Strong><font color="
								+ Integer
										.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
								+ ">提示：</font></Strong><br>  &nbsp;&nbsp;&nbsp;&nbsp;请将鼠标悬停在知识节点上,鼠标左键可以查看关联知识");
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