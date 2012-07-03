package ag.greekcards.utils.io.linemappers;

import ag.greekcards.model.Sustantive;
import ag.greekcards.utils.io.LineMapper;

public class SustantiveLineMapper implements LineMapper<Sustantive> {
	private static final String LANGUAGE_SEPARATOR = "[\t]";
	private static final int GREEK_TEXT_INDEX = 0;
	private static final int SPANISH_TEXT_INDEX = 1;
	
	@Override
	public Sustantive map(String line) {
		final String[] words = line.trim().split(LANGUAGE_SEPARATOR);
		final Sustantive sustantive = new Sustantive();
		sustantive.setGreekText(words[GREEK_TEXT_INDEX]);
		sustantive.setSpanishText(words[SPANISH_TEXT_INDEX]);
		
		return sustantive;
	}

}
