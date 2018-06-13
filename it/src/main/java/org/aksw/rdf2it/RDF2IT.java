package org.aksw.rdf2it;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.lexicon.italian.ITXMLLexicon;
import simplenlg.realiser.*;
import simplenlg.phrasespec.SPhraseSpec;

import java.io.File;
import java.net.URLClassLoader;

import simplenlg.features.*;


public class RDF2IT {
	


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