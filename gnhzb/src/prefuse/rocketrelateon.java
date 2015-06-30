package prefuse;

import java.applet.Applet;
import java.awt.Container;
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

import prefuse.controls.ControlAdapter;
import prefuse.data.Graph;

import prefuse.owl2prefuse.graph.relationGraphDisplay;
import prefuse.owl2prefuse.graph.relationGraphPanel;
import prefuse.util.ui.BrowserLauncher;
import prefuse.util.ui.JPrefuseApplet;
import prefuse.visual.VisualItem;

public class rocketrelateon extends JPrefuseApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7511555698380320987L;
	/**
	 * The tree panel.
	 */
	private relationGraphPanel m_graphPanel;

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

		String psearchname = this.getParameter("searchname");
//		psearchname = "液体发动机";
		String pviewtype = "graph";
		String pdirection = "forward";
		app = this;
		if (flag) {
			searchname = psearchname;
			viewtype = pviewtype;
			direction = pdirection;
		}
		try {
			final Graph graph = getGraph(searchname);

			if (viewtype.equals("graph")) {
				final relationGraphDisplay graphDisp = new relationGraphDisplay(
						graph, GRAPH_DISTANCE_FILTER, graph.getFocusindex(), 0,
						direction, searchname);
				graphDisp.addControlListener(new ControlAdapter() {
					public void itemClicked(VisualItem item, MouseEvent e) {
						final String nodename = item.getString("name");
						final String type = item.getString("type");
						if (e.getButton() == e.BUTTON3) {

							// System.out.println(nodename);
							if (null != type) {
								PopupMenu popupMenu1 = new PopupMenu();

								MenuItem menuItem4 = new MenuItem();

								menuItem4.setLabel("查看关联知识");

								menuItem4
										.addActionListener(new java.awt.event.ActionListener() { // 菜单2的事件监听
											public void actionPerformed(
													ActionEvent e) {

												searchname = nodename
														.split("-")[0];
												if (!type.equals("class")) {
													searchname = nodename
															.substring(
																	0,
																	searchname
																			.lastIndexOf("-"));
												}
												// String
												// url=getCodeBase()+"welcome.do";
												try {
													// url =
													// getCodeBase()+"http://localhost/caltksporcl/knowledge.do?page=1&query=true&flag=kword&alluktag="+
													// URLEncoder.encode(searchname,
													// "utf-8")+"&minkstatus=2";
													// System.out.println(url);
													// url =
													// getCodeBase()+"knowledge.do?page=1&query=true&flag=kword&alluktag="+
													// URLEncoder.encode(searchname,
													// "utf-8")+"&minkstatus=2";

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

												// BrowserLauncher.showDocument(url);

											}
										});
								popupMenu1.add(item.getString("name"));

								popupMenu1.add(menuItem4);
								//	
								graphDisp.add(popupMenu1);
								popupMenu1.show(graphDisp, e.getX(), e.getY());

							}
						} else if (e.getButton() == e.BUTTON1) {
							// graphDisp.removeAll();//= new
							// relationGraphDisplay(graph,GRAPH_DISTANCE_FILTER,
							// graph.getFocusindex(), 0,direction,searchname);
							if (searchname.equals( nodename)==false) {
								Graph p_graph = null;
								try {
									p_graph = getGraph(nodename);
								} catch (MalformedURLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								graphDisp.setVisualization(new Visualization());
								graphDisp.initVisualization(p_graph, true, 0);
								graphDisp.initDisplay();

								// Create the search panel.
								graphDisp.createSearchPanel();

								// Create the title label.
								graphDisp.createTitleLabel();
								graphDisp.getVisualization().addDisplay(
										graphDisp);
								graphDisp.getVisualization().run("draw");
								graphDisp.getVisualization().addDisplay(
										graphDisp);

								JPrefuseApplet c = (JPrefuseApplet) graphDisp
										.getParent().getParent().getParent()
										.getParent();

								// c.remove(graphDisp.getParent());
								m_graphPanel = new relationGraphPanel(
										graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
								c.setContentPane(m_graphPanel);
							}
						}

					}
				});
				m_graphPanel = new relationGraphPanel(graphDisp, LEGEND,
						HOPS_CONTROL_WIDGET);
				this.setContentPane(m_graphPanel);
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

	private Graph getGraph(String searchname) throws MalformedURLException,
			IOException, ClassNotFoundException {

		 String location = this.getCodeBase().toString();
		// String location="http://localhost/caltks/";
		
		URL url = new URL(location + "/kmap/onto!loadRidaGraph.action?tagname=" +  URLEncoder.encode(searchname, "UTF-8"));
		URLConnection con = url.openConnection();
		con.setUseCaches(false);
		InputStream in = con.getInputStream();
		ObjectInputStream objStream;
		objStream = new ObjectInputStream(in);
		Graph graph = (Graph) objStream.readObject();
		return graph;
	}

}
