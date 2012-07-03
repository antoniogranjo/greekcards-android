package ag.greekcards.db.rm;

import ag.greekcards.model.SustantiveCategory;
import ag.greekcards.utils.db.RowMapper;
import android.database.Cursor;

public class SustantiveCategoryRowMapper implements RowMapper<SustantiveCategory> {
	@Override
	public SustantiveCategory mapRow(Cursor cursor) {
		final SustantiveCategory sustantiveCategory = new SustantiveCategory();
		sustantiveCategory.setId(cursor.getInt(0));
		sustantiveCategory.setDescription(cursor.getString(1));
		
		return sustantiveCategory;
	}
}
