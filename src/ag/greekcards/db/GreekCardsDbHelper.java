package ag.greekcards.db;

import java.util.Collection;
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

public class GreekCardsDbHelper extends SQLiteOpenHelper {
	
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
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createGreekCardsDatabase(db);
	}
	
	public List<Sustantive> findSustantives(Collection<SustantiveCategory> categoryFilter) {
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
		loadSustantivesFromFile(db);
		loadSustantiveCategoriesFromFile(db);
	}

	private static void loadSustantivesFromFile(SQLiteDatabase db) {
		final List<Sustantive> sustantives = IoUtils.map(Sustantives.FILE, LineMappers.SUSTANTIVE);
		for (Sustantive s : sustantives) {
			createSustantive(s, db);
		}
	}
	
	private void loadSustantiveCategoriesFromFile(SQLiteDatabase db) {
		final List<SustantiveCategory> sustantiveCategories = IoUtils.map(SustantiveCategories.FILE, LineMappers.SUSTANTIVE_CATEGORY);
		for (SustantiveCategory sc : sustantiveCategories) {
			createSustantiveCategory(sc, db);
		}
	}
	
	private void createSustantiveCategory(SustantiveCategory sc, SQLiteDatabase db) {
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
