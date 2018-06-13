package org.aksw.rdf2it.triple2nl.gender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Lorenz Buehmann
 */
public class GeneralGenderDictionary extends GenderDictionary {

	public static String MALE_GENDER_FILE_LOCATION = "/Users/diegomoussallem/Documents/workspace/RDF2NL/Portuguese/src/main/resources/gender/male.txt";
	public static String FEMALE_GENDER_FILE_LOCATION = "/Users/diegomoussallem/Documents/workspace/RDF2NL/Portuguese/src/main/resources/gender/female.txt";

	public GeneralGenderDictionary() {
		try {
			ClassPathResource maleResource = new ClassPathResource(MALE_GENDER_FILE_LOCATION);
			ClassPathResource femaleResource = new ClassPathResource(FEMALE_GENDER_FILE_LOCATION);

			male = new BufferedReader(new InputStreamReader(
					maleResource.getInputStream(), StandardCharsets.UTF_8))
					.lines().map(name -> name.toLowerCase()).collect(Collectors.toSet());

			female = new BufferedReader(new InputStreamReader(
					femaleResource.getInputStream(), StandardCharsets.UTF_8))
					.lines().map(name -> name.toLowerCase()).collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}


		setCaseSensitive(false);
	}
}
