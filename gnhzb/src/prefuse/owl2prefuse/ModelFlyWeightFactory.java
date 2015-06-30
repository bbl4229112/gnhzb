package prefuse.owl2prefuse;

import java.io.InputStream;
import java.util.HashMap;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import edu.zju.cims201.GOF.util.Constants;

public class ModelFlyWeightFactory {
	
	private static ModelFlyWeightFactory factory=null;
	
	private ModelFlyWeightFactory(){
		
	}
	
	synchronized public static ModelFlyWeightFactory getInstance() {
		if(factory==null)
			factory=new ModelFlyWeightFactory();
		return factory;
	}
	
	private HashMap<String, OntModel> models=new HashMap<String, OntModel> ();
	
	
	public OntModel getModel(String owlPath){
		if(models.containsKey(owlPath)){
			OntModel m_model =models.get(owlPath);
			return models.get(owlPath);
		}else{
			OntModel m_model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

			InputStream in = FileManager.get().open(owlPath);
			if (in == null) {
				throw new IllegalArgumentException("File: " + owlPath	+ " not found");
			}

			m_model.read(in, "");
			OWLNormalize(m_model);
			models.put(owlPath, m_model);
			return m_model;
			
		}
	}
	public void removeMode(String owlfile)
	{
		if(models!=null){

			if(models.containsKey(owlfile)){
				models.get(owlfile).getDocumentManager().clearCache();
				models.remove(owlfile);
				models.keySet().remove(owlfile);
			}
		}
	
		
	}
	
	/**
	 * Get all the classes inthe ontology which do not have a superclass and
	 * link them to owl:Thing.
	 */
	// owl文件标准化
	protected void OWLNormalize(OntModel m_model) {
		// The SPARQL query.
		String strQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				 +"PREFIX base:<http://www.owl-ontologies.com/unnamed.owl#> "
				+ "SELECT ?x  "
				+ "{"
				+ "?x rdf:type owl:Class ."
				+ "OPTIONAL { ?x rdfs:subClassOf?y }"
				+ "FILTER (!bound(?y))"
				+ "FILTER (!isBlank(?x))"

				+ "}";
	
		Query query = QueryFactory.create(strQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, m_model);
		ResultSet rs = qexec.execSelect();
		// ResultSetFormatter.out(rs,query);

		// List list= ResultSetFormatter.toList(rs);
		// System.out.println(">>>>>>>>>>>="+list.get(0).toString());
		// OntClass rootClass =
		// m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		// Property subClassProp =
		// m_model.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
		OntClass rootClass = m_model
				.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		Property subClassProp = m_model
				.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");


		while (rs.hasNext()) {

			// System.out.println(rs.next().getClass());
			// Individual foundIndividual = (Individual)rs.next();
			// System.out.println("名称："+foundIndividual.getLocalName());

	Resource resource = rs.nextSolution().getResource("x");

     resource.addProperty(subClassProp, rootClass);
			


		}
	}
	
	

}
