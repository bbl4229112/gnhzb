package prefuse;

import java.applet.Applet;
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
import prefuse.owl2prefuse.graph.readial.GraphDisplay;
import prefuse.owl2prefuse.graph.readial.GraphPanel;
import prefuse.owl2prefuse.graph.readial.OWLGraphConverter;
import prefuse.util.ui.BrowserLauncher;
import prefuse.util.ui.JPrefuseApplet;
import prefuse.visual.VisualItem;
public class rocketradial extends JPrefuseApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7511555698380320987L;
    /**
	 * The tree panel.
	 */

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
	  private Graph m_graph;
	    
	    /**
	     * The graph panel.
	     */
	    private GraphPanel m_graphPanel;
	/**
	 * Indicates if the hops control widget for the graph distance filter should
	 * be displayed.
	 */
	private final static boolean HOPS_CONTROL_WIDGET = true;
//	  JSObject   win;   
	Applet app;
	public void init() {
	//	 win   =   JSObject.getWindow(this);     

		 OWLGraphConverter graphConverter = new OWLGraphConverter("http://zzh:8080/MapleTr/KnowledgeMap/Resource/test.owl", true);
	        m_graph = graphConverter.getGraph();
//	        m_graph = Loader.loadGraphML(GRAPHML_FILE);
	        
	        // Create a graph display.
	        GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER);
	        
	        // Create a panel for the graph display, which includes a widget for
	        // controlling the number of hops in the graph.
	        m_graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
	this.setContentPane(m_graphPanel);       
	}
	private Graph getGraph(String searchname)
			throws MalformedURLException, IOException, ClassNotFoundException {
//		System.out.println("tag====="+searchname);
		 String location="http://zzh:8080/MapleTr/";
		//String location = this.getCodeBase().toString();
			// if(location.eq)
			URL url = new URL(location +"/map/displayrelate?tag="+searchname  );
		URLConnection con = url.openConnection();
		con.setUseCaches(false);
		InputStream in = con.getInputStream();
		ObjectInputStream objStream;
		objStream = new ObjectInputStream(in);
		Graph graph = (Graph) objStream.readObject();
		return graph;
	}

}
