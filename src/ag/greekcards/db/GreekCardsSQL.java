package ag.greekcards.db;

import ag.greekcards.model.Sustantive;
import ag.greekcards.model.SustantiveCategory;
import android.content.ContentValues;
import android.provider.BaseColumns;

public final class GreekCardsSQL {
	public static final String DATABASE_NAME = "greekcards";
	public static final int DATABASE_VERSION = 1;
	
	public static final class Sustantives implements BaseColumns {
		public static final String _GREEK_TEXT = "txt_gr";
		public static final String _SPANISH_TEXT = "txt_es";
		public static final String TABLE_NAME = "sustantives";
		public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
				_ID + " integer primary key autoincrement, " +
				_GREEK_TEXT	+ " text not null, " +
				_SPANISH_TEXT + " text not null);";
		public static final String[] QUERY_COLS = new String[] {_ID, _GREEK_TEXT, _SPANISH_TEXT};
		public static final String FILE = "sustantives.txt";
		
		private Sustantives() {}
		
		public static ContentValues getInsertContentValues(Sustantive s) {
			final ContentValues values = new ContentValues();
			values.put(_GREEK_TEXT, s.getGreekText());
			values.put(_SPANISH_TEXT, s.getSpanishText());
			return values;
		}
	}
	
	public static final class SustantiveCategories implements BaseColumns {
		public static final String _DESCRIPTION = "desc";
		public static final String TABLE_NAME = "sustantive_categories";
		public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
				_ID + " integer primary key autoincrement, " +
				_DESCRIPTION + " text not null);";
		public static final String[] QUERY_COLS = new String[]{_ID, _DESCRIPTION};
		public static final String FILE = "sustantive_categories.txt";
		
		private SustantiveCategories() {}

		public static ContentValues getInsertContentValues(SustantiveCategory sc) {
			final ContentValues values = new ContentValues();
			values.put(_DESCRIPTION, sc.getDescription());
			return values;
		}
	}
	
	public static final class SustantiveCategoryRelations implements BaseColumns {
		public static final String _ID_SUSTANTIVE = "id_sust";
		public static final String _ID_CATEGORY = "id_cat";
		public static final String TABLE_NAME = "sustantive_category_rels";
		
		private SustantiveCategoryRelations() {}
	}
	
	private GreekCardsSQL() {}
}
