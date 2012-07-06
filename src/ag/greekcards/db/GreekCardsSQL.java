package ag.greekcards.db;

import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import android.content.ContentValues;
import android.provider.BaseColumns;

public final class GreekCardsSQL {
	public static final String DATABASE_NAME = "greekcards";
	public static final int DATABASE_VERSION = 2;
	public static final String FILTER_BY_ID = BaseColumns._ID + " = ?";
	
	public static final class VocabularyEntries implements BaseColumns {
		public static final String _GREEK_TEXT = "txt_gr";
		public static final String _SPANISH_TEXT = "txt_es";
		public static final String _CATEGORY_ID = "category_id";
		public static final String TABLE_NAME = "vocabulary_entries";
		public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
				_ID + " integer primary key autoincrement, " +
				_GREEK_TEXT	+ " text not null, " +
				_SPANISH_TEXT + " text not null, " +
				_CATEGORY_ID + " integer not null default " + VocabularyCategory.NO_CATEGORY_ID + ");";
		public static final String[] QUERY_COLS = new String[] {_ID, _GREEK_TEXT, _SPANISH_TEXT};
		public static final String FILE = "vocabulary.txt";
		
		private VocabularyEntries() {}
		
		public static ContentValues getInsertContentValues(VocabularyEntry s) {
			final ContentValues values = new ContentValues();
			values.put(_GREEK_TEXT, s.getGreekText());
			values.put(_SPANISH_TEXT, s.getSpanishText());
			return values;
		}

		public static ContentValues getUpdateContentValues(VocabularyEntry s) {
			final ContentValues values = new ContentValues();
			values.put(_GREEK_TEXT, s.getGreekText());
			values.put(_SPANISH_TEXT, s.getSpanishText());
			values.put(_CATEGORY_ID, s.getCategoryId());
			return values;
		}
	}
	
	public static final class VocabularyCategories implements BaseColumns {
		public static final String _DESCRIPTION = "desc";
		public static final String TABLE_NAME = "vocabulary_categories";
		public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
				_ID + " integer primary key, " +
				_DESCRIPTION + " text not null);";
		public static final String[] QUERY_COLS = new String[]{_ID, _DESCRIPTION};
		public static final String FILE = "vocabulary_categories.txt";
		
		private VocabularyCategories() {}

		public static ContentValues getInsertContentValues(VocabularyCategory sc) {
			final ContentValues values = new ContentValues();
			values.put(_ID, sc.getId());
			values.put(_DESCRIPTION, sc.getDescription());
			return values;
		}
	}
	
	private GreekCardsSQL() {}
}
