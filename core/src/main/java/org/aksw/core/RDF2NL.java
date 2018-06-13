package org.aksw.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aksw.rdf2pt.*;
import org.apache.jena.graph.Triple;

public class RDF2NL {

	
	public static void main(String[] args) throws IOException {

//		List<Triple> list = new ArrayList<>();
//		list.add(Triple.create(NodeFactory.createURI("http://pt.dbpedia.org/resource/Albert_Einstein"),
//				NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
//				NodeFactory.createURI("http://dbpedia.org/ontology/Scientist")));
//
//		list.add(Triple.create(NodeFactory.createURI("http://pt.dbpedia.org/resource/Albert_Einstein"),
//				NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
//				NodeFactory.createURI("http://dbpedia.org/ontology/Philosopher")));

//		System.out.println(triples(list));

		// System.out.println(resumo("http://pt.dbpedia.org/resource/Os_Lusíadas"));
		// System.out.println(resumo("http://pt.dbpedia.org/resource/Marcos_Pontes"));
		// System.out.println(resumo("http://pt.dbpedia.org/resource/Albert_Einstein"));

		
		System.out.println(RDF2PT.triple("http://pt.dbpedia.org/resource/Albert_Einstein",
				"http://dbpedia.org/ontology/birthPlace", "http://pt.dbpedia.org/resource/Ulm"));

	}
}
