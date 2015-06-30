package prefuse.owl2prefuse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import prefuse.owl2prefuse.graph.GraphDisplay;
import prefuse.owl2prefuse.graph.GraphDisplay2;
import prefuse.owl2prefuse.graph.GraphPanel;
import prefuse.owl2prefuse.graph.GraphPanel2;


import prefuse.data.Graph;


/**
 * This class loads a GraphML or a TreeML file and returns a graph or a tree
 * respectively.
 * <p/>
 * Project OWL2Prefuse <br/>
 * Demo.java created 2 januari 2007, 10:40
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 *
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.4 $$, $$Date: 2009/10/08 16:43:11 $$
 */
public class Demo implements ActionListener
{
    /**
     * The path to the OWL file.
     */

    /**
     * The path to the GraphML file.
     */
//    private static final String GRAPHML_FILE = "prefuse/owl2prefuse/graphml-sample.xml";
    
    /**
     * The path to the TreeML file.
     */
  //  private static final String TREEML_FILE = "prefuse/owl2prefuse/treeml-sample.xml";
    
    /**
     * The JFrame of the demo application.
     */
  //  private JFrame m_frame;
    
    /**
     * The tabbed pane of the demo application.
     */
 //   private JTabbedPane  m_tabbedPane;
    
    /**
     * The file chooser, used by this demo.
     */
 //   private JFileChooser m_fc;
    
    /**
     * The Prefuse graph.
     */
    private Graph m_graph;
  //  public static int i=0;
    /**
     * The graph panel.
     */
    GraphPanel m_graphPanel;
    
    /**
     * The Prefuse tree.
     */
    private Graph m_gtree;
    
    /**
     * The tree panel.
     */
    GraphPanel2 m_gtreePanel2;
  
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
    
    /**
     * Indicates if the orientation control widget for the tree should be displayed.
     */

    /**
     * Creates a new instance of the Demo class.
     */
    public Demo(String searchname,Graph graph,String viewtype)
    {
        // Create the tree from an OWL file.subClassOf
    	//读取owl文件
        //创建树的展示
    	//将owl文件转换成树的形式
    //   int t=0;

     //   m_tabbedPane = new JTabbedPane();
    	if(viewtype.equals("tree")){
    	 	System.out.println("tree...................");
    	 	m_gtree=graph;
        // Create a tree display.
        GraphDisplay2 gtreeDisp = new GraphDisplay2(m_gtree, GRAPH_DISTANCE_FILTER,m_gtree.getFocusindex(),1,"");

        
        
        //创建树的展示的panel
        // Create a panel for the tree display.
        m_gtreePanel2 = new GraphPanel2(gtreeDisp, LEGEND, HOPS_CONTROL_WIDGET);

    	}
        System.out.println("++++++++++++++++++Treedisplay结束  Graphdisplay开始+++++++++++++++++++++++++++=");
    if(viewtype.equals("graph")){
    	System.out.println("graph...................");
        // Create a graph.
        //将wol文件转换成网络图的形式

        m_graph=graph;
 
  //     int i=Integer.parseInt(result[1].toString());

//        m_graph = Loader.loadGraphML(GRAPHML_FILE);
        //创建网络图的展示
        // Create a graph display.
       GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER,m_graph.getFocusindex(),0,"");
        
        // Create a panel for the graph display, which includes a widget for
        // controlling the number of hops in the graph.
        //创建网络图的展示panel
      m_graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);

    }  

    }
    
    /**
     * This methods starts the demo.
     * @param args the command line arguments
     */

    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
   }
    
  

	public GraphPanel getM_graphPanel() {
		return m_graphPanel;
	}

	public void setM_graphPanel(GraphPanel panel) {
		m_graphPanel = panel;
	}

	public GraphPanel2 getM_gtreePanel() {
		return m_gtreePanel2;
	}

	public void setM_gtreePanel(GraphPanel2 panel) {
		m_gtreePanel2 = panel;
	}

}