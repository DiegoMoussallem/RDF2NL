package org.aksw.rdf2de;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//import org.aksw.rdf2de.triple2nl.DocumentGeneratorGerman;
//import org.aksw.rdf2de.triple2nl.TripleConverterGerman;
import org.aksw.rdf2de.triple2nl.util.Sparql;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.dllearner.kb.sparql.SparqlEndpoint;

import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.DBLexicon;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.Realiser;
import simplenlg.realiser.SPhraseSpec;

public class RDF2DE {

//	public static String triple(String subject, String predicate, String object) throws IOException{
//		// create the triple we want to convert by using JENA API
//		Triple t = Triple.create(
//					 NodeFactory.createURI(subject),
//					 NodeFactory.createURI(predicate),
//					 NodeFactory.createURI(object));
//
//		// Optionally, we can declare a knowledge base that contains the triple.
//		// This can be useful during the verbalization process, e.g. the KB could contain labels for entities.
//		// Here, we use the DBpedia SPARQL endpoint.
//		SparqlEndpoint endpoint = SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://pt.dbpedia.org");
//
//		// create the triple converter
//		TripleConverterGerman converter = new TripleConverterGerman(endpoint);
//
//		// convert the triple into natural language
//		return converter.convert(t);
//	}
//	
//	public static String triples(List<Triple> triples) throws IOException{
//		// create the triple we want to convert by using JENA API
//
//
//		// Optionally, we can declare a knowledge base that contains the triple.
//		// This can be useful during the verbalization process, e.g. the KB could contain labels for entities.
//		// Here, we use the DBpedia SPARQL endpoint.
//		SparqlEndpoint endpoint = SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org");
//
//		// create the triple converter
//		TripleConverterGerman converter = new TripleConverterGerman(endpoint);
//
//		// convert the triple into natural language
//		return converter.convert(triples);
//	}
//	
//	public static String resumo(String uri) throws IOException{
//		
//		Sparql sparql = new Sparql();
//		Set<Triple> list = new HashSet<>();
//		
//		list = sparql.getTriples(uri);
//
//		//list.addAll(sparql.getTriples("http://pt.dbpedia.org/resource/Estados_Unidos"));
//		Iterator<Triple> iterator = list.iterator();
//		
//		while(iterator.hasNext()){
//			System.out.println(iterator.next());
//		}
//		
//		//DocumentGeneratorGerman gen = new DocumentGeneratorGerman(SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
//		DocumentGeneratorGerman gen = new DocumentGeneratorGerman(SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
//		
//		return gen.generateDocument(list);
//
//	}
	
	public static void main(String[] args) {

		Lexicon germanLexicon = new DBLexicon(
				new simplenlg.lexicon.db.XMLAccessor("src/main/resources/german-lexicon.xml"));
		NLGFactory germanFactory = new simplenlg.framework.NLGFactory(germanLexicon);

		Realiser realiser = new Realiser();
		// realiser.setDebugMode(true);
		// String output = null;

		SPhraseSpec clauseDe = germanFactory.createSentence("Pierre", "lieben", "Sophie");
		clauseDe.setTense(Tense.PAST);

		// DocumentElement paragraph = germanFactory.createParagraph();

		// DocumentElement document = germanFactory.createNounPhrase("German
		// love\n");
		// document.addComponent(paragraph);

		String outString = realiser.realise(clauseDe);
		System.out.print(outString);
	}
}