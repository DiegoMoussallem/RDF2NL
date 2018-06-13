package org.aksw.rdf2fr;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aksw.rdf2fr.triple2nl.DocumentGeneratorFrench;
import org.aksw.rdf2fr.triple2nl.TripleConverterFrench;
import org.aksw.rdf2fr.triple2nl.util.Sparql;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.dllearner.kb.sparql.SparqlEndpoint;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.XMLLexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

public class RDF2FR {
	
	public static String triple(String subject, String predicate, String object) throws IOException{
		// create the triple we want to convert by using JENA API
		Triple t = Triple.create(
					 NodeFactory.createURI(subject),
					 NodeFactory.createURI(predicate),
					 NodeFactory.createURI(object));

		// Optionally, we can declare a knowledge base that contains the triple.
		// This can be useful during the verbalization process, e.g. the KB could contain labels for entities.
		// Here, we use the DBpedia SPARQL endpoint.
		SparqlEndpoint endpoint = SparqlEndpoint.create("http://fr.dbpedia.org/sparql", "http://fr.dbpedia.org");

		// create the triple converter
		TripleConverterFrench converter = new TripleConverterFrench(endpoint);

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
		TripleConverterFrench converter = new TripleConverterFrench(endpoint);

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
		
		//DocumentGeneratorFrench gen = new DocumentGeneratorFrench(SparqlEndpoint.create("http://pt.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
		DocumentGeneratorFrench gen = new DocumentGeneratorFrench(SparqlEndpoint.create("http://fr.dbpedia.org/sparql", "http://dbpedia.org"), "cache");
		
		return gen.generateDocument(list);

	}

	public static void main(String[] args) throws IOException {
		
		List<Triple> list = new ArrayList<>();
		list.add(Triple.create(
				 NodeFactory.createURI("http://fr.dbpedia.org/resource/Albert_Einstein"),
				 NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
				 NodeFactory.createURI("http://dbpedia.org/ontology/Scientist")));
		
		list.add(Triple.create(
				 NodeFactory.createURI("http://fr.dbpedia.org/resource/Albert_Einstein"),
				 NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
				 NodeFactory.createURI("http://dbpedia.org/ontology/Philosopher")));
		
		System.out.println(triples(list));
		
		//System.out.println(resumo("http://pt.dbpedia.org/resource/Os_Lusíadas"));
		//System.out.println(resumo("http://pt.dbpedia.org/resource/Marcos_Pontes"));
		//System.out.println(resumo("http://pt.dbpedia.org/resource/Albert_Einstein"));
		
		System.out.println(triple("http://fr.dbpedia.org/resource/Albert_Einstein", "http://dbpedia.org/ontology/birthPlace", "http://fr.dbpedia.org/resource/Ulm"));
		
	
		Lexicon englishLexicon = new XMLLexicon();
		NLGFactory englishFactory = new NLGFactory(englishLexicon);
	    Lexicon frenchLexicon = new simplenlg.lexicon.french.XMLLexicon();
		NLGFactory frenchFactory = new NLGFactory(frenchLexicon);
		



		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		//String output = null;
			

		SPhraseSpec clauseEn = englishFactory.createClause("John", "love", "Mary");
		clauseEn.setFeature(Feature.TENSE, Tense.PAST);
		
	
		SPhraseSpec clauseFr = frenchFactory.createClause("Pierre", "aimer", "Sophie");
		clauseFr.setFeature(Feature.TENSE, Tense.PAST);

		
		DocumentElement paragraph = englishFactory.createParagraph();
		paragraph.addComponent(clauseEn);
		paragraph.addComponent(clauseFr);

		DocumentElement document = englishFactory.createDocument("Trilingual love\n");
		document.addComponent(paragraph);

		String outString = realiser.realise(document).getRealisation();
	        System.out.print(outString);
	}
}

