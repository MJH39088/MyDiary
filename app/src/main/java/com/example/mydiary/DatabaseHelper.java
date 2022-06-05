package com.example.mydiary;

/* 데이터 베이스 관리 유틸 클래스 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {  // SQLite = 안드로이드에서 지원하는 앱 내부 데이터베이스 시스템
    private static final String DB_NAME = "MyDiary.db";
    private static final int DB_VERSION = 1;
    // 생성자 (constructor)

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Database Create
        // SQL 이란 Database에 접근하기 위해서 명령을 내리는 쿼리문
        // 기존에 테이블이 존재하지 않을 때에만 만드는 명령문 테이블 생성! (최초 1회만 생성)
        db.execSQL("CREATE TABLE IF NOT EXISTS DiaryInfo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL, " +
                "content TEXT NOT NULL, " +
                "weatherType INTEGER NOT NULL, " +
                "userDate TEXT NOT NULL, " +
                "writeDate TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  onCreate(db); }

    // 다이어리 작성 데이터를 DB 에 저장한다. ( INSERT ) = create
    public void setInsertDiaryList(String _title, String _content, int _weatherType, String _userDate, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DiaryInfo (title, content, weatherType, userDate, writeDate) VALUES ('"  + _title + "', '" + _content + "', '" + _weatherType + "', '" + _userDate + "', '" + _writeDate + "')");
    }

    // 기존 작성 데이터를 수정한다. ( UPDATE ) - update
    public void setUpdateDiaryList(String _title, String _content, int _weatherType, String _userDate, String _writeDate, String _beforeDate) {
        // _beforeDate는 기존에 작성했던 날짜를 키값으로 사용함으로서 실제 마지막으로 작성했던 데이터가 어떤 것인지 구분하고 접근하기 위해 사용
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DiaryInfo Set title = '" + _title + "', content = '" + _content + "', weatherType = '" + _weatherType + "', userDate = '" + _userDate + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "'");
    }

    // 기존 작성 데이터를 샂게한다. ( DELETE ) - delete
    public void setDeleteDiaryList(String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DiaryInfo WHERE writeDate = '" + _writeDate + "'");
    }
}
