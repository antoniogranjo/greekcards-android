package ag.greekcards.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public final class DbUtils {
	private DbUtils() {}
	
	public static <T> List<T> extract(Cursor cursor, RowMapper<T> rowMapper) {
		final List<T> resultList = new ArrayList<T>(cursor.getCount());
		
		cursor.moveToFirst();
		T element = null;
		while (!cursor.isAfterLast()) {
			element = rowMapper.mapRow(cursor);
			resultList.add(element);
			cursor.moveToNext();
		}
		cursor.close();
		
		return resultList;
	}
}
