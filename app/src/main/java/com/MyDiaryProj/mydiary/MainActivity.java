package com.MyDiaryProj.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRvDiary;              // 리사이클러 뷰 (리스트 뷰)
    DiaryListAdapter mAdapter;          // 리사이클러 뷰와 연동할 어댑터
    ArrayList<DiaryModel> mLstDiary;    // 리스트에 표현할 다이어리 데이터들 (배열)
    DatabaseHelper mDatabaseHelper;     // 데이터베이스 헬퍼 클래스 유틸 객체

    // var는 전역변수 사용 가능 val은 한 곳에서만 사용 가능 (메소드 안에서만)
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티가 시작할 때 최초 1회만 호출
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 객체 초기화
        mDatabaseHelper = new DatabaseHelper(this);

        mLstDiary = new ArrayList<>();

        //fb 입력하면 findViewByID가 바로 나온다
        mRvDiary = findViewById(R.id.rv_diary);

        mAdapter = new DiaryListAdapter(); // 리사이클러 뷰 어댑터 인스턴스 생성

        // 다이어리 샘플 아이템 1개 생성
//        DiaryModel item = new DiaryModel();
//        item.setId(0);
//        item.setTitle("나는 오늘도 피곤하다");
//        item.setContent("허민재 내용입니다.");
//        item.setUserDate("2022/05/19 수");
//        item.setWriteDate("2022/05/19 수");
//        item.setWeatherType(0);
//        mLstDiary.add(item);
//
//        DiaryModel item2 = new DiaryModel();
//        item2.setId(0);
//        item2.setTitle("나는 오늘도 피곤하다");
//        item2.setContent("허민재 내용입니다.");
//        item2.setUserDate("2022/05/20 목");
//        item2.setWriteDate("2022/05/20 목");
//        item2.setWeatherType(3);
//        mLstDiary.add(item2);

        mRvDiary.setAdapter(mAdapter);

        // 액티비티 (화면)이 실행될 때 가장 먼저 호출되는 곳
        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 작성하기 버튼을 누를 때 호출되는 곳
                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                startActivity(intent);
            }
        });

    }
    /**
     * Activity LifeCycle
     * 날짜 갱신
     */
    // Ctrl + o
    @Override
    protected void onResume() {
        super.onResume();
        // 액티비티가 재시작이 될 때 실행, onCreate와도 같이 실행
        // get load list
        setLoadRecentList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setLoadRecentList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setLoadRecentList();
    }

    private void setLoadRecentList() {
        // 최근 데이터베이스 정보를 가지고 와서 리사이클러뷰에 갱신해준다.

        // 이전에 배열 리스트에 저장된 데이터가 있으면 비워버린다. 즉 초기화
        if (!mLstDiary.isEmpty()) {
            mLstDiary.clear();
        }

        mLstDiary = mDatabaseHelper.getDiaryListFromDB(); // 데이터베이스로부터 저장되어 있는 DB를 확인하여 가지고 옴.
        mAdapter.setListInit(mLstDiary);
    }
}