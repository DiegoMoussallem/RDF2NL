package org.aksw.rdf2es;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.*;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.spanish.XMLLexicon;
import simplenlg.phrasespec.*;
import simplenlg.realiser.spanish.*;



public class RDF2ES {
	


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