package ag.greekcards.db.rm;

import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.db.RowMapper;
import android.database.Cursor;

public class VocabularyEntryRowMapper implements RowMapper<VocabularyEntry> {
	@Override
	public VocabularyEntry mapRow(Cursor cursor) {
		final VocabularyEntry sustantive = new VocabularyEntry();
		sustantive.setId(cursor.getInt(0));
		sustantive.setGreekText(cursor.getString(1));
		sustantive.setSpanishText(cursor.getString(2));
		sustantive.setCategoryId(cursor.getInt(3));
		
		return sustantive;
	}
}
