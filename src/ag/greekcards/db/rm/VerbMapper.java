package ag.greekcards.db.rm;

import ag.greekcards.model.Verb;
import ag.greekcards.utils.db.RowMapper;
import android.database.Cursor;

public class VerbMapper implements RowMapper<Verb> {
	@Override
	public Verb mapRow(Cursor cursor) {
		final Verb v = new Verb();
		v.setId(cursor.getInt(0));
		v.setSpanishText(cursor.getString(1));
		v.setGreekText(cursor.getString(2));
		
		return v;
	}
}
