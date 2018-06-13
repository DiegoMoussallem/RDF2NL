package org.aksw.rdf2it;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.lexicon.italian.ITXMLLexicon;
import simplenlg.realiser.*;
import simplenlg.phrasespec.SPhraseSpec;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aksw.rdf2it.triple2nl.DocumentGenerator;
import org.aksw.rdf2it.triple2nl.TripleConverter;
import org.aksw.rdf2it.triple2nl.util.Sparql;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.dllearner.kb.sparql.SparqlEndpoint;

import simplenlg.features.*;


public class RDF2IT {
	

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
		TripleConverter converter = new TripleConverter(endpoint);

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
		TripleConverter converter = new TripleConverter(endpoint);

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
		
		//DocumentGenerator gen = new DocumentGenerator(SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
		DocumentGenerator gen = new DocumentGenerator(SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
		
		return gen.generateDocument(list);

	}
    public static void main(String[] args) {

    	
    
	Lexicon englishLexicon = new XMLLexicon();
	NLGFactory englishFactory = new NLGFactory(englishLexicon);
    Lexicon frenchLexicon = new XMLLexicon();
	NLGFactory frenchFactory = new NLGFactory(frenchLexicon);


	
	Lexicon italianLexicon = new ITXMLLexicon();
	NLGFactory italianFactory = new NLGFactory(italianLexicon);

	Realiser realiser = new Realiser();
	//realiser.setDebugMode(true);
	//String output = null;
		

	SPhraseSpec clauseEn = englishFactory.createClause("John", "love", "Mary");
	clauseEn.setFeature(Feature.TENSE, Tense.PAST);
	

	SPhraseSpec clauseFr = frenchFactory.createClause("Pierre", "aimer", "Sophie");
	clauseFr.setFeature(Feature.TENSE, Tense.PAST);

	
	SPhraseSpec clauseIt = italianFactory.createClause("Paolo", "amare", "Freancesca");
	clauseIt.setFeature(Feature.TENSE, Tense.PAST);
	
	DocumentElement paragraph = englishFactory.createParagraph();
	paragraph.addComponent(clauseEn);
	paragraph.addComponent(clauseFr);
	paragraph.addComponent(clauseIt);
	DocumentElement document = englishFactory.createDocument("Trilingual love\n");
	document.addComponent(paragraph);

	String outString = realiser.realise(document).getRealisation();
        System.out.print(outString);
    }
}