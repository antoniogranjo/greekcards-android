package ag.greekcards.utils.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import ag.greekcards.db.GreekCardsSQL;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public final class DbUtils {
	private static final String DB_NAME = GreekCardsSQL.DATABASE_NAME;
	private static final String DB_DIR = "/data/data/ag.greekcards/databases/";
	private static final String DB_PATH = DB_DIR + DB_NAME;

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
	
	public static void initializeDataBase(SQLiteOpenHelper dbHelper, Context context) {
	    if (DbUtils.checkDataBase()) {
	        DbUtils.openDataBase();
	    } else {
	        try {
	        	dbHelper.getReadableDatabase();
	            DbUtils.copyDataBase(context);
	            dbHelper.close();
	            DbUtils.openDataBase();
	        } catch (IOException e) {
	            throw new Error("Error copiando BBDD");
	        }
	        
	        Toast.makeText(context, "Se ha inicializado correctamente la BBDD", Toast.LENGTH_LONG).show();
	    }
	}
	
	public static void copyDataBase(Context context) throws IOException{
		final InputStream myInput = context.getAssets().open(DB_NAME);
		FileUtils.copyInputStreamToFile(myInput, new File(DB_PATH));
	}
	
	public static SQLiteDatabase openDataBase() {
	    return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
	}

	public static boolean checkDataBase() {
	    SQLiteDatabase checkDB = null;
	    boolean exist = false;
	    try {
	        checkDB = SQLiteDatabase.openDatabase(DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
	    } catch (SQLiteException e) {
	        Log.v("db log", "No existe la BBDD [" + DB_NAME + "]");
	    }

	    if (checkDB != null) {
	        exist = true;
	        checkDB.close();
	    }
	    
	    return exist;
	}
}
