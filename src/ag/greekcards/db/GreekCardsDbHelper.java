package ag.greekcards.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ag.greekcards.db.GreekCardsSQL.SustantiveCategories;
import ag.greekcards.db.GreekCardsSQL.Sustantives;
import ag.greekcards.db.rm.SustantiveCategoryRowMapper;
import ag.greekcards.db.rm.SustantiveRowMapper;
import ag.greekcards.model.Sustantive;
import ag.greekcards.model.SustantiveCategory;
import ag.greekcards.utils.db.DbUtils;
import ag.greekcards.utils.db.RowMapper;
import ag.greekcards.utils.io.IoUtils;
import ag.greekcards.utils.io.LineMapper;
import ag.greekcards.utils.io.linemappers.SustantiveCategoryLineMapper;
import ag.greekcards.utils.io.linemappers.SustantiveLineMapper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.AndroidRuntimeException;

public class GreekCardsDbHelper extends SQLiteOpenHelper {
	private Context context;
	
	private static final class RowMappers {
		public static final RowMapper<Sustantive> SUSTANTIVE = new SustantiveRowMapper();
		public static final RowMapper<SustantiveCategory> SUSTANTIVE_CATEGORY = new SustantiveCategoryRowMapper();
		
		private RowMappers() {}
	}
	
	private static final class LineMappers {
		public static final LineMapper<Sustantive> SUSTANTIVE = new SustantiveLineMapper();
		public static final LineMapper<SustantiveCategory> SUSTANTIVE_CATEGORY = new SustantiveCategoryLineMapper();
		
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
	
	public List<Sustantive> findSustantives(SustantiveCategory categoryFilter) {
		String selectFrom = null;
		String where = null;
		String[] whereArgs = null;
		
		if (categoryFilter != null && !categoryFilter.isCategoryAll()) {
			// TODO filtro por categoria
		}
		
		final Cursor cursor = getReadableDatabase().query(Sustantives.TABLE_NAME, Sustantives.QUERY_COLS, null, null, null, null, null);
		return DbUtils.extract(cursor, RowMappers.SUSTANTIVE);
	}
	
	public List<SustantiveCategory> findSustantiveCategories() {
		final Cursor cursor = getReadableDatabase().query(SustantiveCategories.TABLE_NAME, SustantiveCategories.QUERY_COLS, null, null, null, null, SustantiveCategories._DESCRIPTION + " ASC");
		return DbUtils.extract(cursor, RowMappers.SUSTANTIVE_CATEGORY);
	}

	private void createGreekCardsDatabase(SQLiteDatabase db) {
		createSustantivesTable(db);
		createSustantiveCategoriesTable(db);
		loadSustantivesFromFile(context, db);
		loadSustantiveCategoriesFromFile(context, db);
	}

	private static void loadSustantivesFromFile(Context context, SQLiteDatabase db) {
		InputStream is;
		try {
			is = context.getAssets().open(Sustantives.FILE);
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		final List<Sustantive> sustantives = IoUtils.map(is, LineMappers.SUSTANTIVE);
		for (Sustantive s : sustantives) {
			createSustantive(s, db);
		}
	}
	
	private static void loadSustantiveCategoriesFromFile(Context context, SQLiteDatabase db) {
		InputStream is;
		try {
			is = context.getAssets().open(SustantiveCategories.FILE);
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		final List<SustantiveCategory> sustantiveCategories = IoUtils.map(is, LineMappers.SUSTANTIVE_CATEGORY);
		for (SustantiveCategory sc : sustantiveCategories) {
			createSustantiveCategory(sc, db);
		}
	}
	
	private static void createSustantiveCategory(SustantiveCategory sc, SQLiteDatabase db) {
		db.insert(SustantiveCategories.TABLE_NAME, null, SustantiveCategories.getInsertContentValues(sc));
	}

	private static void createSustantive(Sustantive sustantive, SQLiteDatabase db) {
		db.insert(Sustantives.TABLE_NAME, null, Sustantives.getInsertContentValues(sustantive));
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
