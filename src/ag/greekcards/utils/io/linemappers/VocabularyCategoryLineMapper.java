package ag.greekcards.utils.io.linemappers;

import org.apache.commons.lang.StringUtils;

import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.utils.io.LineMapper;
import android.util.Log;

public class VocabularyCategoryLineMapper implements LineMapper<VocabularyCategory> {
	private static final String SEPARATOR = "[|]";
	private static final int ID_INDEX = 0;
	private static final int DESC_INDEX = 1;
	
	@Override
	public VocabularyCategory map(String line) {
		Log.d("CARGA_CATEGORIAS", "[" + line + "]");
		final String[] words = line.trim().split(SEPARATOR); 
		final VocabularyCategory sustantiveCategory = new VocabularyCategory();
		sustantiveCategory.setId(Integer.parseInt(words[ID_INDEX]));
		sustantiveCategory.setDescription(StringUtils.capitalize(StringUtils.lowerCase(words[DESC_INDEX])));
		
		return sustantiveCategory;
	}
}
