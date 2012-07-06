package ag.greekcards.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ag.greekcards.db.GreekCardsSQL.SustantiveCategories;
import ag.greekcards.db.GreekCardsSQL.Sustantives;
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
		String selectFrom = null;
		String where = null;
		String[] whereArgs = null;
		
		if (categoryFilter != null && !categoryFilter.isCategoryAll()) {
			// TODO filtro por categoria
		}
		
		final Cursor cursor = getReadableDatabase().query(Sustantives.TABLE_NAME, Sustantives.QUERY_COLS, null, null, null, null, null);
		return DbUtils.extract(cursor, RowMappers.VOCABULARY_ENTRY);
	}
	
	public List<VocabularyCategory> findSustantiveCategories() {
		final Cursor cursor = getReadableDatabase().query(SustantiveCategories.TABLE_NAME, SustantiveCategories.QUERY_COLS, null, null, null, null, SustantiveCategories._DESCRIPTION + " ASC");
		return DbUtils.extract(cursor, RowMappers.VOCABULARY_CATEGORY);
	}

	private void createGreekCardsDatabase(SQLiteDatabase db) {
		createSustantivesTable(db);
		createSustantiveCategoriesTable(db);
		loadSustantivesFromFile(context, db);
		loadVocabularyCategoriesFromFile(context, db);
	}

	private static void loadSustantivesFromFile(Context context, SQLiteDatabase db) {
		InputStream is;
		try {
			is = context.getAssets().open(Sustantives.FILE);
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
			is = context.getAssets().open(SustantiveCategories.FILE);
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		final List<VocabularyCategory> vocabularyCategories = IoUtils.map(is, LineMappers.VOCABULARY_CATEGORY);
		for (VocabularyCategory sc : vocabularyCategories) {
			createVocabularyCategory(sc, db);
		}
	}
	
	private static int createVocabularyCategory(VocabularyCategory sc, SQLiteDatabase db) {
		return (int)db.insert(SustantiveCategories.TABLE_NAME, null, SustantiveCategories.getInsertContentValues(sc));
	}
	
	public void updateVocabularyEntry(VocabularyEntry sustantive) {
		getWritableDatabase().update(Sustantives.TABLE_NAME, Sustantives.getUpdateContentValues(sustantive), GreekCardsSQL.FILTER_BY_ID, new String[] {sustantive.getId().toString()});
	}
	
	public VocabularyEntry newVocabularyEntry(VocabularyEntry ve) {
		final int id = (int)createVocabularyEntry(ve, getWritableDatabase());
		ve.setId(id);
		return ve;
	}

	private static int createVocabularyEntry(VocabularyEntry ve, SQLiteDatabase db) {
		return (int)db.insert(Sustantives.TABLE_NAME, null, Sustantives.getInsertContentValues(ve));
	}

	private static void createSustantivesTable(SQLiteDatabase db) {
		db.execSQL(Sustantives.CREATE_TABLE);
	}

	private static void createSustantiveCategoriesTable(SQLiteDatabase db) {
		db.execSQL(SustantiveCategories.CREATE_TABLE);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
