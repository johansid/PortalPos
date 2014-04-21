package cn.burgeon.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public Context context;

	public DbHelper(Context context) {
		super(context, DbConstant.DB_NAME, null, DbConstant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DbHelper", "====oncreate=====");
		db.execSQL("CREATE TABLE IF NOT EXISTS c_vip" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, cardno VARCHAR,"+
				"name VARCHAR, sex VARCHAR,idno VARCHAR,mobile VARCHAR,birthday VARCHAR"+
                "employee VARCHAR,email VARCHAR,createTime VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
