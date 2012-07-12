package ag.greekcards.db.rm;

import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.db.RowMapper;
import android.database.Cursor;

public class VocabularyEntryRowMapper implements RowMapper<VocabularyEntry> {
	@Override
	public VocabularyEntry mapRow(Cursor cursor) {
		final VocabularyEntry ve = new VocabularyEntry();
		ve.setId(cursor.getInt(0));
		ve.setGreekText(cursor.getString(1));
		ve.setSpanishText(cursor.getString(2));
		ve.setCategoryId(cursor.getInt(3));
		
		return ve;
	}
}
