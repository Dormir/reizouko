package jp.dormir.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "shokuhin.db";
	public static final int DB_VERSION = 1; // version for migration

	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create table
		db.execSQL("CREATE TABLE shokuhin_data(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, class VARCHAR, DATE DATE DATE CHECK( DATE like '__-__'))");
		// 初期レコード
		// db.execSQL("INSERT INTO shokuhin_data(name, class, DATE) VALUES('aaa', '冷蔵', 11-11)");
		// db.execSQL("INSERT INTO shokuhin_data(name, class, DATE) VALUES('bbb', '冷�?, 01-01)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// drop old table
		db.execSQL("DROP TABLE IF EXISTS shokuhin_data");
		// onCreate
		onCreate(db);
	}
}
