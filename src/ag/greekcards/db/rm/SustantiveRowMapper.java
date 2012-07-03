package ag.greekcards.db.rm;

import ag.greekcards.model.Sustantive;
import ag.greekcards.utils.db.RowMapper;
import android.database.Cursor;

public class SustantiveRowMapper implements RowMapper<Sustantive> {
	@Override
	public Sustantive mapRow(Cursor cursor) {
		final Sustantive sustantive = new Sustantive();
		sustantive.setId(cursor.getInt(0));
		sustantive.setGreekText(cursor.getString(1));
		sustantive.setSpanishText(cursor.getString(2));
		
		return sustantive;
	}
}
