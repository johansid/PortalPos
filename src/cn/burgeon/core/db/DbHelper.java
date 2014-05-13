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
		db.execSQL("CREATE TABLE IF NOT EXISTS c_vip" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, cardno VARCHAR,"+
				"name VARCHAR, sex VARCHAR,idno VARCHAR,mobile VARCHAR,birthday VARCHAR,"+
                "employee VARCHAR,email VARCHAR,createTime VARCHAR,type VARCHAR)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS c_settle" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, orderno VARCHAR,"+
				"settleTime VARCHAR, type VARCHAR,count VARCHAR,money VARCHAR,"
				+ "orderEmployee VARCHAR,employeeID VARCHAR,status VARCHAR,settleDate VARCHAR,"
				+ "settleMonth VARCHAR,settleUUID VARCHAR)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS c_settle_detail" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, settleUUID VARCHAR,"+
				"price VARCHAR, discount VARCHAR,count VARCHAR,money VARCHAR,settleDate VARCHAR,"
				+ "pdtname VARCHAR,pdtcode VARCHAR,color VARCHAR,size VARCHAR,settleType VARCHAR)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS tc_sku (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "sku varchar, style varchar, clr varchar, sizeid varchar, pname varchar, skuout varchar default null,timestamp varchar default null)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS tc_style (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "style varchar, style_name varchar, attrib1 varchar, attrib2 varchar, attrib3 varchar, "
				+ "attrib4 varchar,attrib5 varchar,attrib6 varchar,attrib7 varchar,attrib8 varchar,"
				+ "attrib9 varchar,attrib10 varchar)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS tc_styleprice (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "style varchar, store varchar, fprice varchar, timestamp varchar)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS tdefclr(clr varchar PRIMARY KEY,clrname varchar, timestamp varchar)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS tdefsize(sizeid varchar PRIMARY KEY,sizename varchar, timestamp varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
