package org.aksw.rdf2de;

import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.DBLexicon;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.Realiser;
import simplenlg.realiser.SPhraseSpec;

public class RDF2DE {

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