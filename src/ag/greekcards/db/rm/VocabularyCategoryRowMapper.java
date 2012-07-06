package ag.greekcards.db.rm;

import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.utils.db.RowMapper;
import android.database.Cursor;

public class VocabularyCategoryRowMapper implements RowMapper<VocabularyCategory> {
	@Override
	public VocabularyCategory mapRow(Cursor cursor) {
		final VocabularyCategory sustantiveCategory = new VocabularyCategory();
		sustantiveCategory.setId(cursor.getInt(0));
		sustantiveCategory.setDescription(cursor.getString(1));
		
		return sustantiveCategory;
	}
}
