package ag.greekcards.db;

import java.util.List;

import ag.greekcards.db.GreekCardsSQL.VocabularyCategories;
import ag.greekcards.db.GreekCardsSQL.VocabularyEntries;
import ag.greekcards.db.rm.VerbMapper;
import ag.greekcards.db.rm.VocabularyCategoryRowMapper;
import ag.greekcards.db.rm.VocabularyEntryRowMapper;
import ag.greekcards.model.Verb;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.db.DbUtils;
import ag.greekcards.utils.db.RowMapper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GreekCardsDbHelper extends SQLiteOpenHelper {
	private static final class RowMappers {
		public static final RowMapper<VocabularyEntry> VOCABULARY_ENTRY = new VocabularyEntryRowMapper();
		public static final RowMapper<VocabularyCategory> VOCABULARY_CATEGORY = new VocabularyCategoryRowMapper();
		public static final RowMapper<Verb> VERBS = new VerbMapper();
		
		private RowMappers() {}
	}
	
	public GreekCardsDbHelper(Context context) {
		super(context, GreekCardsSQL.DATABASE_NAME, null, GreekCardsSQL.DATABASE_VERSION);
		
		DbUtils.initializeDataBase(this, context);
	}
	
	public List<VocabularyEntry> findVocabularyEntries(VocabularyCategory categoryFilter) {
		final String selection;
		final String[] selectionArgs;
		
		if (categoryFilter != null && !categoryFilter.isCategoryAll()) {
			selection = VocabularyEntries.SELECTION_BY_CATEGORY_ID;
			selectionArgs = new String[] {categoryFilter.getId().toString()};
		} else {
			selection = null;
			selectionArgs = null;
		}
		
		final Cursor cursor = getReadableDatabase().query(VocabularyEntries.TABLE_NAME, VocabularyEntries.QUERY_COLS, selection, selectionArgs, null, null, null);
		return DbUtils.extract(cursor, RowMappers.VOCABULARY_ENTRY);
	}
	
	public List<VocabularyCategory> findVocabularyCategories() {
		final Cursor cursor = getReadableDatabase().query(VocabularyCategories.TABLE_NAME, VocabularyCategories.QUERY_COLS, null, null, null, null, VocabularyCategories._DESCRIPTION + " ASC");
		return DbUtils.extract(cursor, RowMappers.VOCABULARY_CATEGORY);
	}

	public void updateVocabularyEntry(VocabularyEntry ve) {
		getWritableDatabase().update(VocabularyEntries.TABLE_NAME, VocabularyEntries.getUpdateContentValues(ve), GreekCardsSQL.FILTER_BY_ID, new String[] {ve.getId().toString()});
	}
	
	public VocabularyEntry newVocabularyEntry(VocabularyEntry ve) {
		final int id = (int)createVocabularyEntry(ve, getWritableDatabase());
		ve.setId(id);
		return ve;
	}

	private static int createVocabularyEntry(VocabularyEntry ve, SQLiteDatabase db) {
		return (int)db.insert(VocabularyEntries.TABLE_NAME, null, VocabularyEntries.getInsertContentValues(ve));
	}

	/*
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			Log.d("DB", "borrando base de datos...");
			context.deleteDatabase(GreekCardsSQL.DATABASE_NAME);
		}
	}
	*/

	public void delete(VocabularyEntry vocabularyEntry) {
		getWritableDatabase().delete(VocabularyEntries.TABLE_NAME, VocabularyEntries.SELECTION_BY_ID, new String[] {vocabularyEntry.getId().toString()});
	}

	public List<Verb> findAllVerbs() {
		final Cursor cursor = getReadableDatabase().query(VocabularyCategories.TABLE_NAME, VocabularyCategories.QUERY_COLS, null, null, null, null, VocabularyCategories._DESCRIPTION + " ASC");
		return DbUtils.extract(cursor, RowMappers.VERBS);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
		// TODO Auto-generated method stub
		
	}
}
