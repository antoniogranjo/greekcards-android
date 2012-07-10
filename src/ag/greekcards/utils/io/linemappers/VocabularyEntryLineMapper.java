package ag.greekcards.utils.io.linemappers;

import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.io.LineMapper;
import android.util.Log;

public class VocabularyEntryLineMapper implements LineMapper<VocabularyEntry> {
	private static final String LANGUAGE_SEPARATOR = "[|]";
	private static final int GREEK_TEXT_INDEX = 0;
	private static final int SPANISH_TEXT_INDEX = 1;
	private static final int CATEGORY_ID_TEXT_INDEX = 2;
	
	@Override
	public VocabularyEntry map(String line) {
		Log.d("CARGA_SUSTANTIVOS", "[" + line + "]");
		final String[] words = line.trim().split(LANGUAGE_SEPARATOR);
		final VocabularyEntry ve = new VocabularyEntry();
		ve.setGreekText(words[GREEK_TEXT_INDEX]);
		ve.setSpanishText(words[SPANISH_TEXT_INDEX]);
		
		if (words.length > 2) {
			final int categoryId = Integer.valueOf(words[CATEGORY_ID_TEXT_INDEX]);
			ve.setCategoryId(categoryId);
		} else {
			ve.setCategoryId(VocabularyCategory.NO_CATEGORY_ID);
		}
		
		return ve;
	}

}
