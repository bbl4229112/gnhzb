package prefuse.owl2prefuse.graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.owl2prefuse.Constants;

/**
 * This class converts the given OWL Model to a Prefuse graph datastructure.
 * <p/>
 * Project OWL2Prefuse <br/>
 * OWLGraphConverter.java created 3 januari 2007, 9:58
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 * 
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.3 $$, $$Date: 2009/09/22 12:42:27 $$
 */

public class OWLrelateGraphConverter {

	Map<String,String> m_edges;
	/**
	 * The created Prefuse graph.
	 */
	private Graph m_graph;

	/**
	 * An ArrayList containing all the edges that have to be added to the
	 * Prefuse graph.
	 */

	//private String appUrl;

	/**
	 * An Hashtable containing all the nodes in Prefuse graph.
	 */
	private Hashtable<String, Node> m_nodes;

	// private Hashtable<String, Individual> m_OntResource;
	/**
	 * An ArrayList containing the URI's of OWL classes that should not be
	 * converted into the Prefuse graph.
	 */
	private ArrayList<String> m_uselessType;

	/**
	 * Creates a new instance of OWLGraphConverter
	 * 
	 * @param p_OWLFile
	 *            The path to the OWL file that needs to be converted.
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	// public OWLrelateGraphConverter(List relatelist) {
	//
	// init( relatelist);
	// }

	/**
	 * Creates a new instance of OWLGraphConverter
	 * 
	 * @param p_model
	 *            The Jena model that needs to be converted.
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */

	/**
	 * Initialize the graph converter.
	 * 
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	public OWLrelateGraphConverter(String searchtag, List relatelist,Map<String,String> edges) {
		m_edges = edges;
		//this.appUrl = appUrl;
		m_nodes = new Hashtable<String, Node>();

		// m_OntResource=new Hashtable<String, Individual>() ;

		// Create an ArrayList which contains URI's of nodes we do not want to
		// visualize, because they are too general.
		m_uselessType = new ArrayList<String>();
		m_uselessType.add("http://www.w3.org/2002/07/owl#Class");
		// m_uselessType.add("http://www.w3.org/2000/01/rdf-schema#Class");

		// Create the graph.
		createGraph(searchtag, relatelist);
	}

	/**
	 * Return the created Prefuse graph.
	 * 
	 * @return The created Prefuse graph.
	 */
	public Graph getGraph() {

		return m_graph;
	}

	/**
	 * Create the Prefuse graph. This method creates an empty graph and adds the
	 * appropriate columns to the node- and edgestable. After that it gets the
	 * root class (owl:Thing) of the OWL graph and recursively starts building
	 * the graph from there. This method is automatically called from the
	 * constructors of this converter.
	 * 
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	private void createGraph(String searchtag, List relatelist) {
		// Create a new empty graph.
		m_graph = new Graph(true);

		// Add the appropriate columns.

		m_graph.getNodeTable().addColumn("name", String.class);
		m_graph.getNodeTable().addColumn("label", String.class);
		m_graph.getNodeTable().addColumn("image", String.class);
		m_graph.getNodeTable().addColumn("type", String.class);
		m_graph.getEdgeTable().addColumn("label", String.class);

		// Get the root node.
		// OntClass rootClass =
		// m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");

		// Build the entire tree.
		// firstsearchname = searchname;
		buildGraph(searchtag, relatelist);
		// buildGraph1(rootClass, searchname);
		// m_nodesadded.put(firstsearchURI, firstsearchname);
		// listsub();
		// System.out.println("t=" + t);
		m_graph.setFocusindex(0);
		// All the edges are stored in an ArrayList, because they can only be
		// added
		// if all the appropriate nodes exist. At this point this is the case,
		// so
		// all the nodes are created.
		// createEdges();
	}

	/**
	 * Build the Prefuse graph, this method is called recursively.
	 * 
	 * @param p_currentClass
	 *            The class which is being added to the graph.
	 */
	//
	private void buildGraph(String searchtag, List relatelist) {

		Node currNode = m_graph.addNode();

		currNode.setString("name", searchtag);
		currNode.setString("label", "查询术语:"+searchtag);
		currNode.setString("image",edu.zju.cims201.GOF.util.Constants.ONTO_IMG_FILE_PATH+"/root.gif");
		currNode.setString("type", "class");
		//
		// Add this node to the nodes ArrayList.
		m_nodes.put(searchtag, currNode);

		// Add the edges, connected to this node, to the edges ArrayList.
		// storeEdges(p_currentClass, searchname);
		// }
		// Walk trough the subclasses of the current class.

		// 遍历类别实例
		String name;
		String edgeName="";
		try {
			for (int i = 0; i < relatelist.size(); i++) {
				if (relatelist.get(i).toString().indexOf("-") != -1
						&& relatelist.get(i).toString().lastIndexOf("-") > 1) {

					 System.out.println("相关度"+relatelist.get(i).toString().substring(relatelist.get(i).toString().indexOf("-")));
					if (!relatelist.get(i).toString().substring(
							relatelist.get(i).toString().indexOf("-")).equals(
							"-0.0")) {
						Node node = m_graph.addNode();
						
						// System.out.println("relatelist.get(i)here="+relatelist.get(i));
                         String relationscore="";
						if (relatelist.get(i).toString().length() > relatelist
								.get(i).toString().indexOf("-") + 6) {
							node.setString("name", relatelist.get(i).toString().substring(0,
											relatelist.get(i).toString()
													.indexOf("-") + 6).split("-")[0]);
							relationscore=relatelist.get(i).toString().substring(0,
									relatelist.get(i).toString()
									.indexOf("-") + 6).split("-")[1];
						} else {
							node
									.setString("name", relatelist.get(i)
											.toString().split("-")[0]);
							relationscore=relatelist.get(i)
							.toString().split("-")[1];
						}
						node.setString("type", "individual");
						node.setString("image",edu.zju.cims201.GOF.util.Constants.ONTO_IMG_FILE_PATH+"/2.gif");
						node.setString("label","与根节点相关度:"+relationscore);
						Edge edge = m_graph.addEdge(currNode, node);
						name = node.getString("name");
						if(m_edges.containsKey(name))
						edgeName=m_edges.get(name).split("-")[0];
						if(edgeName.equals("")==false)
						node.setString("label", "<font color="+ Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+">"+searchtag+"</font>的"+edgeName+":"
								+ name);
						if(edgeName.equals("")==false)	edge.setString("label", edgeName);	
						edgeName="";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}