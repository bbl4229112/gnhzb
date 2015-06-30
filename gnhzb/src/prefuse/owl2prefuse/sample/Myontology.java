package prefuse.owl2prefuse.sample;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.reasoner.*;
public class Myontology{
  public static void main(String[] args) {
    OntModel text_ontmodel = ModelFactory.createOntologyModel();
       OntDocumentManager dm = text_ontmodel.getDocumentManager();
       dm.addAltEntry("http://www.owl-ontologies.com/Ontology1241779535.owl#","file:" + "Family.owl");
       text_ontmodel.read("file:C:/onto/Family.owl");
       String k=" base:Julia ";
       String prefix = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                       "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                       "PREFIX  base:<http://www.owl-ontologies.com/Ontology1241779535.owl#> ";
       String slect =  "SELECT ?son ?daughter ?father ?mother ?sister ?brother ?marry_with ";
       String where =  "WHERE { "+
                         "OPTIONAL { "+ k + 
                                  " base:son ?son ."+
                         " }"+
                         "OPTIONAL { "+ k +
                                  " base:daughter ?daughter ."+
                         //for <daughter xml:lang="en">Lily</daughter> in <married rdf:ID="Julia">
                         " }"+
                         "OPTIONAL { "+ k +
                                  " base:father ?father ."+
                         " }"+
                         "OPTIONAL { "+ k +
                                  " base:mother ?mother ."+
                         " }"+     
                         "OPTIONAL { "+ k +        
                                  " base:sister ?sister ."+
                         " }"+ 
                         "OPTIONAL { "+ k +
                                  " base:brother ?brother ."+
                         " }"+ 
                         "OPTIONAL { "+ k +
                                  " base:marry_with ?marry_with ."+
                         " }"+
                               //can test "?x rdfs:subClassOf ?y ." +
                               //and "?y base:son \"Jack\"@en ."+
                        " }";
       Query query = QueryFactory.create(prefix + slect + where);
       Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
       InfModel inf = ModelFactory.createInfModel(reasoner, text_ontmodel);
       QueryExecution qe = QueryExecutionFactory.create(query,inf);
       ResultSet results = qe.execSelect();
       ResultSetFormatter.out(System.out,results,query);
       qe.close();
       
       
       
       
       OntModel text_ontmodel2 = ModelFactory.createOntologyModel();
       OntDocumentManager dm2 = text_ontmodel.getDocumentManager();
       dm2.addAltEntry("http://www.owl-ontologies.com/Ontology1241779535.owl#","file:" + "Family2.owl");
       text_ontmodel2.read("file:C:/onto/Family2.owl");
       String prefix2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                     "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX  base:<http://www.owl-ontologies.com/Ontology1241779535.owl#> ";
       String slect2 =  "SELECT ?relationship ?with ";
       String where2 =  "WHERE { "+
                                "?x owl:onProperty ?relationship ." +
                                //here for <owl:ObjectProperty rdf:ID="daughter_is"/> in <owl:onProperty> in <owl:Restriction> in <owl:Class rdf:ID="Julia">
                                "?x owl:allValuesFrom ?with ."+ k +
                                " rdfs:subClassOf ?x ."+
                                
                        " }";
       Query query2 = QueryFactory.create(prefix2 + slect2 + where2);
       Reasoner reasoner2 = ReasonerRegistry.getOWLReasoner();
       InfModel inf2 = ModelFactory.createInfModel(reasoner2, text_ontmodel2);
       QueryExecution qe2 = QueryExecutionFactory.create(query2,inf2);
       ResultSet results2 = qe2.execSelect();
       ResultSetFormatter.out(System.out,results2,query2);
       qe2.close();
       
       
       
       

       OntModel text_ontmodel3 = ModelFactory.createOntologyModel();
       OntDocumentManager dm3 = text_ontmodel3.getDocumentManager();
       dm3.addAltEntry("http://www.owl-ontologies.com/Ontology1241772983.owl#","file:" + "human_being.owl");
       text_ontmodel3.read("file:C:/onto/human_being.owl");
       String prefix3 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                     "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX  base:<http://www.owl-ontologies.com/Ontology1241772983.owl#> ";
       String slect3 = "SELECT ?birthday ?firstname ?familyname ?nationality ";
       String where3 = "WHERE { "+
                         "OPTIONAL { "+ k + 
                                  " base:birthday ?birthday ."+
                         " }"+
                         "OPTIONAL { "+ k +
                                  " base:firstname ?firstname ."+
                         " }"+
                         "OPTIONAL { "+ k +
                                  " base:familyname ?familyname ."+
                         " }"+
                         "OPTIONAL { "+ k +
                                  " base:nationality ?nationality ."+
                         " }"+     
                       " }";
       Query query3 = QueryFactory.create(prefix3 + slect3 + where3);
       Reasoner reasoner3 = ReasonerRegistry.getOWLReasoner();
       InfModel inf3 = ModelFactory.createInfModel(reasoner3, text_ontmodel3);
       QueryExecution qe3 = QueryExecutionFactory.create(query3,inf3);
       ResultSet results3 = qe3.execSelect();
       ResultSetFormatter.out(System.out,results3,query3);
       qe3.close();       


  }
}

