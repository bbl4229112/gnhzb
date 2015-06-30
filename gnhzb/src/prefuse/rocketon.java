package prefuse;

import java.applet.Applet;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.SwingUtilities;

import prefuse.controls.ControlAdapter;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.owl2prefuse.graph.GraphDisplay;
import prefuse.owl2prefuse.graph.GraphDisplay2;
import prefuse.owl2prefuse.graph.GraphPanel;
import prefuse.owl2prefuse.graph.GraphPanel2;
import prefuse.owl2prefuse.graph.RadialGraphPanel;
import prefuse.owl2prefuse.graph.RadialGraphView;
import prefuse.owl2prefuse.graph.relationGraphDisplay;
import prefuse.owl2prefuse.graph.relationGraphPanel;

import prefuse.util.ui.JPrefuseApplet;
import prefuse.visual.VisualItem;

public class rocketon extends JPrefuseApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7511555698380320987L;
	/**
	 * The tree panel.
	 *
	 */
	private GraphPanel m_graphPanel;
	private GraphPanel2 m_gtreePanel;
	private RadialGraphPanel radialPanel;
	private Boolean flag = true;
	private String searchname = "";
	private String viewtype = "";
	private String direction = "";
	/**
	 * Indicates if the graph distance filter should be used.
	 */
	private final static boolean GRAPH_DISTANCE_FILTER = true;
	/**
	 * Indicates if a legend should be displayed.
	 */
	private final static boolean LEGEND = true;

	/**
	 * Indicates if the hops control widget for the graph distance filter should
	 * be displayed.
	 */
	private final static boolean HOPS_CONTROL_WIDGET = true;
	// JSObject win;
	Applet app;


	public void init() {

		// win = JSObject.getWindow(this);

//		System.out.println("ceshi");
//		String psearchname="火箭发动机";
//		String pviewtype="tree";
//		String pdirection="forward";
		
		String psearchname = this.getParameter("searchname");
		String pviewtype = this.getParameter("viewtype");
		String pdirection = this.getParameter("direction");
		
//		 pviewtype = "tree";
//		 pdirection = "forward";
//		 psearchname = "运载火箭";

		app = this;
		if (flag) {
			searchname = psearchname;
			viewtype = pviewtype;
			direction = pdirection;
		}
		try {		
			Graph graph = getGraph(searchname, viewtype, direction);
			if (viewtype.equals("tree")) {
				renderTree(graph);//	renderTree(graph);
			} else if (viewtype.equals("graph")) {
				renderNet(graph);
			} 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void renderTree(Graph graph) {

		final GraphDisplay2 gtreeDisp = new GraphDisplay2(graph,
				GRAPH_DISTANCE_FILTER, graph.getFocusindex(), 1, direction);
		gtreeDisp.addControlListener(createPopMenu(gtreeDisp));
		m_gtreePanel = new GraphPanel2(gtreeDisp, LEGEND, HOPS_CONTROL_WIDGET);

		this.setContentPane(m_gtreePanel);
	}

	private ControlAdapter createPopMenu(final Display gDisp)
	{
		return (new ControlAdapter() {
			public void itemClicked(VisualItem item, MouseEvent e) {
				if (e.getButton() == e.BUTTON3) {
					final String nodename = item.getString("name");
					final String type = item.getString("type");
					if (null != type) {
						//System.out.println(nodename);
						//System.out.println(type);
						PopupMenu popupMenu1 = new PopupMenu();
						MenuItem menuItem1 = new MenuItem();
						MenuItem menuItem2 = new MenuItem();
						MenuItem menuItem3 = new MenuItem();
						MenuItem menuItem4 = new MenuItem();
						MenuItem menuItem5 = new MenuItem();

						try {
							String n = new String("展开相关结点");
							menuItem1.setLabel(new String(n
									.getBytes("gbk")));
						} catch (UnsupportedEncodingException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						menuItem2.setLabel("查看网络图谱");
						if (direction.equals("forward"))
							menuItem3.setLabel("反向展开图谱");
						else
							menuItem3.setLabel("正向展开图谱");
						menuItem4.setLabel("重新载入图谱");
						menuItem5.setLabel("查看关联知识");

						menuItem1
								.addActionListener(new java.awt.event.ActionListener() { // 菜单1的事件监听
									public void actionPerformed(
											ActionEvent e) {
										searchname = nodename;
										if (type.equals("class")) {
											searchname = nodename
													.substring(
															0,
															nodename
																	.length() - 1);
										}
										// flag = false;
										// init();
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showwindow(\""
																			+ viewtype
																			+ "\",\""
																			+ searchname
																			+ "\",\""
																			+ direction
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}
									}
								});
						menuItem2
								.addActionListener(new java.awt.event.ActionListener() { // 菜单2的事件监听
									public void actionPerformed(
											ActionEvent e) {
										searchname = nodename;
										if (type.equals("class")) {
											searchname = nodename
													.substring(
															0,
															nodename
																	.length() - 1);
										}
										viewtype = "graph";
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showwindow(\""
																			+ viewtype
																			+ "\",\""
																			+ searchname
																			+ "\",\""
																			+ direction
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}

									}
								});
						menuItem3
								.addActionListener(new java.awt.event.ActionListener() { // 菜单3的事件监听
									public void actionPerformed(
											ActionEvent e) {
										searchname = nodename;
										if (type.equals("class")) {
											searchname = nodename
													.substring(
															0,
															nodename
																	.length() - 1);
										}
										if (direction.equals("forward"))
											direction = "backward";
										else
											direction = "forward";
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showwindow(\""
																			+ viewtype
																			+ "\",\""
																			+ searchname
																			+ "\",\""
																			+ direction
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}

									}
								});
						menuItem4
								.addActionListener(new java.awt.event.ActionListener() { // 菜单4的事件监听
									public void actionPerformed(
											ActionEvent e) {

										flag = true;
										init();

									}
								});
						menuItem5
								.addActionListener(new java.awt.event.ActionListener() { // 菜单5的事件监听
									public void actionPerformed(
											ActionEvent e) {
										searchname = nodename;
										if (type.equals("class")) {
											searchname = nodename
													.substring(
															0,
															nodename
																	.length() - 1);
										}
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showrelate(\""
																			+ searchname
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}

									}
								});

						popupMenu1.add(item.getString("name"));
						popupMenu1.add(menuItem1);
						popupMenu1.add(menuItem2);
						popupMenu1.add(menuItem3);
						popupMenu1.add(menuItem4);
						popupMenu1.add(menuItem5);

						//	
						gDisp.add(popupMenu1);
						popupMenu1.show(gDisp, e.getX(), e.getY());

					}
				}
				if (!SwingUtilities.isLeftMouseButton(e))
					return;
				if (SwingUtilities.isLeftMouseButton(e)
						&& e.getClickCount() == 2) {
					String nodename = item.getString("name");
					String type = item.getString("type");
					if (null != type) {
						if (type.equals("class")) {
							nodename = nodename.substring(0, nodename
									.length() - 1);
						}
						searchname = nodename;
					
						m_gtreePanel.repaint();
						flag = false;
						init();
					}
				}
			}}
		 );
	
		
	}

	private void renderNet(Graph graph) {
		final GraphDisplay graphDisp = new GraphDisplay(graph,
				GRAPH_DISTANCE_FILTER, graph.getFocusindex(), 0, direction);
		graphDisp.addControlListener(new ControlAdapter() {
			public void itemClicked(VisualItem item, MouseEvent e) {
				if (e.getButton() == e.BUTTON3) {
					final String nodename = item.getString("name");
					final String type = item.getString("type");
					// System.out.println(nodename);
					if (null != type) {
						PopupMenu popupMenu1 = new PopupMenu();
						MenuItem menuItem1 = new MenuItem();
						MenuItem menuItem2 = new MenuItem();
						MenuItem menuItem3 = new MenuItem();
						MenuItem menuItem4 = new MenuItem();
						// Font font = new Font("宋体", 14, 1);
						// menuItem1.setFont(font);
						// menuItem2.setFont(font);
						// menuItem3.setFont(font);
						// menuItem4.setFont(font);

						String str = "查看知识树图";
						menuItem1.setLabel(str);

						if (direction.equals("forward"))
							menuItem2.setLabel("反向展开图谱");
						else
							menuItem2.setLabel("正向展开图谱");
						menuItem3.setLabel("重新载入图谱");
						menuItem4.setLabel("查看关联知识");
						menuItem1
								.addActionListener(new java.awt.event.ActionListener() { // 菜单1的事件监听
									public void actionPerformed(ActionEvent e) {
										searchname = nodename;
										viewtype = "tree";
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showwindow(\""
																			+ viewtype
																			+ "\",\""
																			+ searchname
																			+ "\",\""
																			+ direction
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}

									}
								});
						menuItem2
								.addActionListener(new java.awt.event.ActionListener() { // 菜单2的事件监听
									public void actionPerformed(ActionEvent e) {
										searchname = nodename;
										if (direction.equals("forward"))
											direction = "backward";
										else
											direction = "forward";
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showwindow(\""
																			+ viewtype
																			+ "\",\""
																			+ searchname
																			+ "\",\""
																			+ direction
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}

									}
								});
						menuItem3
								.addActionListener(new java.awt.event.ActionListener() { // 菜单2的事件监听
									public void actionPerformed(ActionEvent e) {

										flag = true;
										init();

									}
								});
						menuItem4
								.addActionListener(new java.awt.event.ActionListener() { // 菜单2的事件监听
									public void actionPerformed(ActionEvent e) {

										searchname = nodename;
										if (type.equals("class")) {
											searchname = nodename.substring(0,
													nodename.length() - 1);
										}
										try {
											app
													.getAppletContext()
													.showDocument(
															new URL(
																	"javascript:showrelate(\""
																			+ searchname
																			+ "\")"));
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										}

									}
								});
						popupMenu1.add(item.getString("name"));
						popupMenu1.add(menuItem1);
						popupMenu1.add(menuItem2);
						popupMenu1.add(menuItem3);
						popupMenu1.add(menuItem4);
						//	
						graphDisp.add(popupMenu1);
						popupMenu1.show(graphDisp, e.getX(), e.getY());

					}
				}
				// if (!SwingUtilities.isLeftMouseButton(e)) return;
				// if (SwingUtilities.isLeftMouseButton(e)
				// && e.getClickCount() == 2) {
				// // String nodeid = item.getString("id");
				// String url =
				// "http://icme.zju.edu.cn/caltksp/kshow.do";
				//
				// BrowserLauncher.showDocument(url);
				// }
			}
		});
		m_graphPanel= new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
		this.setContentPane(m_graphPanel);
	}

	private Graph getGraph(String searchname, String viewtype, String direction)
			throws MalformedURLException, IOException, ClassNotFoundException {
		// URL url = new URL(getCodeBase(), getCodeBase()
		// + "servlet/DisplayGraph?searchname=" + searchname
		// + "&viewtype=" + viewtype + "&direction=" + direction);

		String location = this.getCodeBase().toString();

//        String  location="http://localhost:8080/kms/";
//		searchname="火箭发动机";
//		viewtype="tree";
//		direction="forward";
		
		URL url = new URL(location + "kmap/onto!loadGraph.action?tagname=" + URLEncoder.encode(searchname, "UTF-8")
				+ "&viewtype=" + viewtype + "&direction=" + direction);
		URLConnection con = url.openConnection();
		con.setUseCaches(false);
		InputStream in = con.getInputStream();
		ObjectInputStream objStream;
		objStream = new ObjectInputStream(in);
		Graph graph = (Graph) objStream.readObject();
		return graph;
	}

}
