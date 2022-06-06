package com.example.mydiary;

/* 데이터 베이스 관리 유틸 클래스 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    // crud create, read, update, delete
    // 다이어리 작성 데이터를 DB 에 저장한다. ( INSERT ) - create
    public void setInsertDiaryList(String _title, String _content, int _weatherType, String _userDate, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DiaryInfo (title, content, weatherType, userDate, writeDate) VALUES ('"  + _title + "', '" + _content + "', '" + _weatherType + "', '" + _userDate + "', '" + _writeDate + "')");
    }

    // 다이어리 작성 데이터를 조회하여 가지고 온다. ( SELECT ) - read
    public ArrayList<DiaryModel> getDiaryListFromDB() {
        ArrayList<DiaryModel> lstDiary = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase(); // read하는 과정이기 때문에 getRead
        // 특정 테이블 위치를 가르치기 위해 Cursor 사용, rawQuery는 sql문을 한 번 더 사용하겠다는 의미, ORDER BY는 userdate를 DESC, 내림차순으로 최신 데이터를 정렬
        Cursor cursor = db.rawQuery("SELECT * FROM DiaryInfo ORDER BY userdate DESC", null);
        if (cursor.getCount() != 0) { // 커서로 가져온 데이터가 0이 아니라면
            while (cursor.moveToNext()) { // 커서의 데이터 중에서 다음 데이터가 없을 때까지 반복
                // 커서에서 테이블 데이터를 모두 끌고와서 내림차순으로 정렬한 다음에 데이터들을 커서로 하나하나 가르쳐서 diaryModel 인스턴스를 만든다음 lstDiary 배열에다가 넣어준 것
                // getDiaryListFromDB가 호출되면 모든 테이블 데이터들을 가지고 와서 ArrayList 형태로 담아주는 것.
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                int weatherType = cursor.getInt(cursor.getColumnIndexOrThrow("weatherType"));
                String userDate = cursor.getString(cursor.getColumnIndexOrThrow("userDate"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                // create data class
                DiaryModel diaryModel = new DiaryModel();
                diaryModel.setId(id);
                diaryModel.setTitle(title);
                diaryModel.setContent(content);
                diaryModel.setWeatherType(weatherType);
                diaryModel.setUserDate(userDate);
                diaryModel.setWriteDate(writeDate);

                lstDiary.add(diaryModel);
            }
        }
        cursor.close();
        return lstDiary;
    }

    // 기존 작성 데이터를 수정한다. ( UPDATE ) - update
    public void setUpdateDiaryList(String _title, String _content, int _weatherType, String _userDate, String _writeDate, String _beforeDate) {
        // _beforeDate는 기존에 작성했던 날짜를 키값으로 사용함으로서 실제 마지막으로 작성했던 데이터가 어떤 것인지 구분하고 접근하기 위해 사용
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DiaryInfo Set title = '" + _title + "', content = '" + _content + "', weatherType = '" + _weatherType + "', userDate = '" + _userDate + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "'");
    }

    // 기존 작성 데이터를 삭제한다. ( DELETE ) - delete
    public void setDeleteDiaryList(String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DiaryInfo WHERE writeDate = '" + _writeDate + "'");
    }
}
