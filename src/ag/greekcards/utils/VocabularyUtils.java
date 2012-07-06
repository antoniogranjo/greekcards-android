package ag.greekcards.utils;

import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.TranslationMode;

public final class VocabularyUtils {
	private VocabularyUtils() {}
	
	public static String getQuestionText(VocabularyEntry ve, TranslationMode translationMode) {
		boolean greekToSpanish = TranslationMode.GREEK_TO_SPANISH.equals(translationMode);
		return greekToSpanish ? ve.getGreekText() : ve.getSpanishText();
	}
	
	public static String getAnswerText(VocabularyEntry ve, TranslationMode translationMode) {
		boolean greekToSpanish = TranslationMode.GREEK_TO_SPANISH.equals(translationMode);
		return greekToSpanish ? ve.getSpanishText() : ve.getGreekText();
	}
}
