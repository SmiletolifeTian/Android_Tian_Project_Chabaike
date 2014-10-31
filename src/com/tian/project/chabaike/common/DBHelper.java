package com.tian.project.chabaike.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "database.db";
	private static int VERSION = 1;
	private SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		db = getWritableDatabase();
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "DROP TABLE IF EXISTS tb_chabaike";
		db.execSQL(sql);
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("CREATE TABLE tb_chabaike (					      ");
		strBuffer.append("_id                  INTEGER              NOT NULL, ");
		strBuffer.append("detail_id            VARCHAR(10),                   ");
		strBuffer.append("title                VARCHAR(50),                   ");
		strBuffer.append("source               VARCHAR(10),                   ");
		strBuffer.append("description          VARCHAR(100),                  ");
		strBuffer.append("wap_thumb            VARCHAR(100),                  ");
		strBuffer.append("create_time          VARCHAR(15),                   ");
		strBuffer.append("nickname             VARCHAR(15),                   ");
		strBuffer.append("primary key (_id)                                   ");
		strBuffer.append(")                                                   ");

		db.execSQL(strBuffer.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion<newVersion){
			onCreate(db);
		}
	}
	
	public void execSQL(String sql,Object... bindArgs){
		db.execSQL(sql, bindArgs);
	}
	
	public Cursor execQuery(String sql, String... selectionArgs){
		return db.rawQuery(sql, selectionArgs);
	}

}
