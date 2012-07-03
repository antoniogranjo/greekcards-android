package ag.greekcards.utils.db;

import android.database.Cursor;

public interface RowMapper<T> {
	T mapRow(Cursor cursor); 
}
 