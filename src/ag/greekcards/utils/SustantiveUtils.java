package ag.greekcards.utils;

import ag.greekcards.model.Sustantive;
import ag.greekcards.model.enums.SustantiveTranslationMode;

public final class SustantiveUtils {
	private SustantiveUtils() {}
	
	public static String getQuestionText(Sustantive sustantive, SustantiveTranslationMode translationMode) {
		boolean greekToSpanish = SustantiveTranslationMode.GREEK_TO_SPANISH.equals(translationMode);
		return greekToSpanish ? sustantive.getGreekText() : sustantive.getSpanishText();
	}
	
	public static String getAnswerText(Sustantive sustantive, SustantiveTranslationMode translationMode) {
		boolean greekToSpanish = SustantiveTranslationMode.GREEK_TO_SPANISH.equals(translationMode);
		return greekToSpanish ? sustantive.getSpanishText() : sustantive.getGreekText();
	}
}
