package prefuse.owl2prefuse.tree;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import prefuse.owl2prefuse.*;
import prefuse.data.Edge;
import prefuse.data.Node;
import prefuse.data.Tree;

/**
 * This class converts the given OWL Model to a Prefuse tree datastructure. <p/>
 * Project OWL2Prefuse <br/> OWLTreeConverter.java created 2 januari 2007, 11:43
 * <p/> Copyright &copy 2006 Jethro Borsje
 * 
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.2 $$, $$Date: 2009/09/03 08:15:35 $$
 */
public class OWLTreeConverter extends Converter {
	/**
	 * The Prefuse tree.
	 */
	private Tree m_tree;
	private int i = 0;
	private int t = 0;

	/**
	 * Creates a new instance of OWLTreeConverter.
	 * 
	 * @param p_OWLFile
	 *            The path to the OWL file that needs to be converted.
	 */
	public OWLTreeConverter(String p_OWLFile, String searchname) {
		super(p_OWLFile);
		createTree(searchname);
	}

	/**
	 * Creates a new instance of OWLTreeConverter.
	 * 
	 * @param p_model
	 *            The Jena model that needs to be converted.
	 */
	public OWLTreeConverter(OntModel p_model, String searchname) {
		super(p_model);
		createTree(searchname);
	}

	/**
	 * Return the created Prefuse tree.
	 * 
	 * @return The created Prefuse tree.
	 */
	public Tree getTree() {
		//Object[] resulte = new Object[2];
		//resulte[0] = m_tree;
		//resulte[1] = t;
		return m_tree;
	}

	/**
	 * Create the Prefuse tree. This method creates an empty tree and adds the
	 * appropriate columns to it. After that it gets the root class (owl:Thing)
	 * of the OWL graph and recursively starts building the tree from there.
	 * This method is automatically called from the constructors of this
	 * converter.
	 */
	private void createTree(String searchname) {
		// Create a new empty tree.
		m_tree = new Tree(true);

		// Add the appropriate columns.
		m_tree.addColumn("URI", String.class);
		m_tree.addColumn("name", String.class);
		m_tree.addColumn("type", String.class);
		m_tree.addColumn("source", String.class);
		m_tree.addColumn("target", String.class);
		m_tree.addColumn("label", String.class);
		m_tree.addColumn("image", String.class);

		// m_tree.getEdgeTable().addColumn("label", String.class);

		// Get the root node.
		OntClass rootClass = m_model
				.getOntClass("http://www.w3.org/2002/07/owl#Thing");

		// Build the entire tree.
		buildTree(null, rootClass, searchname);
		System.out.println("t===================="+t);
		m_tree.setFocusindex(t);
	}

	/**
	 * Build the Prefuse tree, this method is called recursively.
	 * 
	 * @param p_parent
	 *            The parent node of the class that is being added to the graph.
	 * @param p_currentClass
	 *            The class which is being added to the graph.
	 */
	private String oldURI = "";

	private void buildTree(Node p_parent, OntClass p_currentClass,
			String searchname) {
		// If there is no root node yet, one is created.
		Node currNode = null;

		if (p_parent == null) {
			currNode = m_tree.addRoot();

		} else {
			currNode = m_tree.addChild(p_parent);
			Edge edge = m_tree.getEdge(p_parent, currNode);

			// m_tree.addChildEdge(p_parent, currNode);
			// m_tree.addEdge(p_parent, currNode);
		}
		if (p_currentClass.getLocalName().equals(searchname))
			t = i;
	     	i++;
		// System.out.println("URI=" + p_currentClass.getURI());

		currNode.setString("URI", p_currentClass.getURI());
		// System.out.println("name=" + p_currentClass.getLocalName());
		if (p_currentClass.getLocalName().equals("Thing"))
			currNode.setString("name", "知识本体");
		else
		currNode.setString("name", p_currentClass.getLocalName());
		currNode.setString("target", p_currentClass.getURI());
		currNode.setString("source", "http://www.w3.org/2002/07/owl#Thing");
		currNode.setString("type", "class");
		oldURI = p_currentClass.getLocalName();
		// Walk trough the subclasses of the current class.
		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {
			// System.out.println("itClasses");
			// Recurse trough the subclasses of the current node.
			buildTree(currNode, (OntClass) itClasses.next(), searchname);
		}

		// Walk trough the instances of the current class.
		ExtendedIterator itIndividuals = p_currentClass.listInstances();
		while (itIndividuals.hasNext()) {
			// System.out.println("individuals");
			Individual foundIndividual = (Individual) itIndividuals.next();

			// Create the node for this instance.
			Node node = m_tree.addChild(currNode);
			if (foundIndividual.getLocalName().equals(searchname)) {
				System.out.println(searchname);
				t = i;
			}
			i++;
			// 获得实例的各个属性
			String indpropertys = "";
			StmtIterator itProperties = foundIndividual.listProperties();
			while (itProperties.hasNext()) {
				Statement property = itProperties.nextStatement();
				if (!property.getObject().isResource()) {
					indpropertys += "<Strong><font color="
							+ Integer
									.toHexString(Constants.NODE_COLOR_INDIVIDUAL & 0x00ffffff)
							+ ">" + property.getPredicate().getLocalName()
							+ "：</font></Strong>";
					indpropertys += ((LiteralImpl) property.getObject())
							.getValue()
							+ "   <br> <br>         ";
				}
			}
			// / System.out.println("indpropertys="+indpropertys);

			node.setString("label", indpropertys);
			node.setString("URI", foundIndividual.getURI());
			// System.out.println("URI=" + foundIndividual.getURI());
			node.setString("name", foundIndividual.getLocalName());
			// System.out.println("name=" + foundIndividual.getLocalName());
			node.setString("type", "individual");
			node.setString("source", oldURI);
			node.setString("target", foundIndividual.getLocalName());

		}
	}


}