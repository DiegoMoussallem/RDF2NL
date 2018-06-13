package org.aksw.rdf2es;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aksw.rdf2es.triple2nl.DocumentGeneratorSpanish;
import org.aksw.rdf2es.triple2nl.TripleConverterSpanish;
import org.aksw.rdf2es.triple2nl.util.Sparql;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.dllearner.kb.sparql.SparqlEndpoint;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.*;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.spanish.XMLLexicon;
import simplenlg.phrasespec.*;
import simplenlg.realiser.spanish.*;



public class RDF2ES {
	

	public static String triple(String subject, String predicate, String object) throws IOException{
		// create the triple we want to convert by using JENA API
		Triple t = Triple.create(
					 NodeFactory.createURI(subject),
					 NodeFactory.createURI(predicate),
					 NodeFactory.createURI(object));

		// Optionally, we can declare a knowledge base that contains the triple.
		// This can be useful during the verbalization process, e.g. the KB could contain labels for entities.
		// Here, we use the DBpedia SPARQL endpoint.
		SparqlEndpoint endpoint = SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://pt.dbpedia.org");

		// create the triple converter
		TripleConverterSpanish converter = new TripleConverterSpanish(endpoint);

		// convert the triple into natural language
		return converter.convert(t);
	}
	
	public static String triples(List<Triple> triples) throws IOException{
		// create the triple we want to convert by using JENA API


		// Optionally, we can declare a knowledge base that contains the triple.
		// This can be useful during the verbalization process, e.g. the KB could contain labels for entities.
		// Here, we use the DBpedia SPARQL endpoint.
		SparqlEndpoint endpoint = SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org");

		// create the triple converter
		TripleConverterSpanish converter = new TripleConverterSpanish(endpoint);

		// convert the triple into natural language
		return converter.convert(triples);
	}
	
	public static String resumo(String uri) throws IOException{
		
		Sparql sparql = new Sparql();
		Set<Triple> list = new HashSet<>();
		
		list = sparql.getTriples(uri);

		//list.addAll(sparql.getTriples("http://pt.dbpedia.org/resource/Estados_Unidos"));
		Iterator<Triple> iterator = list.iterator();
		
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		//DocumentGeneratorPortuguese gen = new DocumentGeneratorPortuguese(SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
		DocumentGeneratorSpanish gen = new DocumentGeneratorSpanish(SparqlEndpoint.create("http://es.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
		
		return gen.generateDocument(list);

	}

    public static void main(String[] args) {

	
	Lexicon spanishLexicon = new XMLLexicon("src/main/resources/default-spanish-lexicon.xml");
	NLGFactory spanishFactory = new NLGFactory(spanishLexicon);
	Realiser realiser = new Realiser(spanishLexicon);
	
	//realiser.setDebugMode(true);
	//String output = null;
//	NLGElement s1 = spanishFactory.createSentence("mi perro es feliz");
//
//    String output = realiser.realiseSentence(s1);
    //System.out.println(output);
    
	SPhraseSpec clauseEs = spanishFactory.createClause("Pierre", "querer", "Sophie");
	clauseEs.setFeature(Feature.TENSE, Tense.PAST);
	
	NLGFactory nlgFactory = new NLGFactory(spanishLexicon);
    SPhraseSpec p = nlgFactory.createClause();
    p.setSubject("María");
    p.setVerb("perseguir");
    p.setObject("un mono");
    
    String output2 = realiser.realiseSentence(p);
    System.out.println(output2);
    
	DocumentElement paragraph = spanishFactory.createParagraph(clauseEs);

	//DocumentElement document = spanishFactory.createDocument(paragraph);
			//.createDocument("Spanish love\n");
	//document.addComponent(paragraph);

	String outString = realiser.realise(paragraph).getRealisation();
    System.out.print(outString);
    }
}