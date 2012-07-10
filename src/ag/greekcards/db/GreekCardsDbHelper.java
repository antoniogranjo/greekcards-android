package ag.greekcards.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ag.greekcards.db.GreekCardsSQL.VocabularyCategories;
import ag.greekcards.db.GreekCardsSQL.VocabularyEntries;
import ag.greekcards.db.rm.VocabularyCategoryRowMapper;
import ag.greekcards.db.rm.VocabularyEntryRowMapper;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.db.DbUtils;
import ag.greekcards.utils.db.RowMapper;
import ag.greekcards.utils.io.IoUtils;
import ag.greekcards.utils.io.LineMapper;
import ag.greekcards.utils.io.linemappers.VocabularyCategoryLineMapper;
import ag.greekcards.utils.io.linemappers.VocabularyEntryLineMapper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.AndroidRuntimeException;
import android.util.Log;

public class GreekCardsDbHelper extends SQLiteOpenHelper {
	private Context context;
	
	private static final class RowMappers {
		public static final RowMapper<VocabularyEntry> VOCABULARY_ENTRY = new VocabularyEntryRowMapper();
		public static final RowMapper<VocabularyCategory> VOCABULARY_CATEGORY = new VocabularyCategoryRowMapper();
		
		private RowMappers() {}
	}
	
	private static final class LineMappers {
		public static final LineMapper<VocabularyEntry> VOCABULARY_ENTRY = new VocabularyEntryLineMapper();
		public static final LineMapper<VocabularyCategory> VOCABULARY_CATEGORY = new VocabularyCategoryLineMapper();
		
		private LineMappers() {}
	}
	
	public GreekCardsDbHelper(Context context) {
		super(context, GreekCardsSQL.DATABASE_NAME, null, GreekCardsSQL.DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createGreekCardsDatabase(db);
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

	private void createGreekCardsDatabase(SQLiteDatabase db) {
		createVocabularyEntriesTable(db);
		createVocabularyEntriesTableIndex(db);
		createVocabularyCategoriesTable(db);
		loadVocabularyEntriesFromFile(context, db);
		loadVocabularyCategoriesFromFile(context, db);
	}

	private void createVocabularyEntriesTableIndex(SQLiteDatabase db) {
		db.execSQL(VocabularyEntries.CREATE_INDEX);
	}

	private static void loadVocabularyEntriesFromFile(Context context, SQLiteDatabase db) {
		InputStream is;
		try {
			is = context.getAssets().open(VocabularyEntries.FILE);
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		final List<VocabularyEntry> sustantives = IoUtils.map(is, LineMappers.VOCABULARY_ENTRY);
		for (VocabularyEntry s : sustantives) {
			createVocabularyEntry(s, db);
		}
	}
	
	private static void loadVocabularyCategoriesFromFile(Context context, SQLiteDatabase db) {
		InputStream is;
		try {
			is = context.getAssets().open(VocabularyCategories.FILE);
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		final List<VocabularyCategory> vocabularyCategories = IoUtils.map(is, LineMappers.VOCABULARY_CATEGORY);
		for (VocabularyCategory sc : vocabularyCategories) {
			createVocabularyCategory(sc, db);
		}
	}
	
	private static int createVocabularyCategory(VocabularyCategory sc, SQLiteDatabase db) {
		return (int)db.insert(VocabularyCategories.TABLE_NAME, null, VocabularyCategories.getInsertContentValues(sc));
	}
	
	public void updateVocabularyEntry(VocabularyEntry sustantive) {
		getWritableDatabase().update(VocabularyEntries.TABLE_NAME, VocabularyEntries.getUpdateContentValues(sustantive), GreekCardsSQL.FILTER_BY_ID, new String[] {sustantive.getId().toString()});
	}
	
	public VocabularyEntry newVocabularyEntry(VocabularyEntry ve) {
		final int id = (int)createVocabularyEntry(ve, getWritableDatabase());
		ve.setId(id);
		return ve;
	}

	private static int createVocabularyEntry(VocabularyEntry ve, SQLiteDatabase db) {
		return (int)db.insert(VocabularyEntries.TABLE_NAME, null, VocabularyEntries.getInsertContentValues(ve));
	}

	private static void createVocabularyEntriesTable(SQLiteDatabase db) {
		db.execSQL(VocabularyEntries.CREATE_TABLE);
	}

	private static void createVocabularyCategoriesTable(SQLiteDatabase db) {
		db.execSQL(VocabularyCategories.CREATE_TABLE);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			Log.d("DB", "borrando base de datos...");
			context.deleteDatabase(GreekCardsSQL.DATABASE_NAME);
		}
	}

	public void delete(VocabularyEntry vocabularyEntry) {
		getWritableDatabase().delete(VocabularyEntries.TABLE_NAME, VocabularyEntries.SELECTION_BY_ID, new String[] {vocabularyEntry.getId().toString()});
	}
}
