package prefuse.owl2prefuse.graph;

import java.util.HashMap;

import com.hp.hpl.jena.ontology.OntModel;

import prefuse.data.Graph;
import prefuse.owl2prefuse.Converter;
import prefuse.owl2prefuse.ModelFlyWeightFactory;




public class ConverterFactory {

	
	private static ConverterFactory factory=null;
	private ConverterFactory(){
		
		}
	synchronized public static ConverterFactory getInstance() {
		if(factory==null)
			factory=new ConverterFactory();
		return factory;
	}
	
	private HashMap<String, Converter> converters=new HashMap<String, Converter> ();
	
	
	public Converter getConverter(String key){
		if(converters.containsKey(key)){
			return converters.get(key);
		}else{
			OntModel ontModel = ModelFlyWeightFactory.getInstance().getModel(key);
			OWLGraphtreeConverter converter=new OWLGraphtreeConverter(ontModel,true);	
			converters.put(key, converter);
			return converter;
		}
	}
}
