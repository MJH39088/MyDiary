package com.MyDiaryProj.mydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    RecyclerView mRvDiary;              // 리사이클러 뷰 (리스트 뷰)
    DiaryListAdapter mAdapter;          // 리사이클러 뷰와 연동할 어댑터
    ArrayList<DiaryModel> mLstDiary;    // 리스트에 표현할 다이어리 데이터들 (배열)
    DatabaseHelper mDatabaseHelper;     // 데이터베이스 헬퍼 클래스 유틸 객체
    ImageView iv_question;
    ImageView iv_settings;
    DrawerLayout drawerLayout;
    ImageView iv_menu;
    TextView tvHome;
    TextView tv_Change;
    TextView tv_help;
    TextView tv_developer;
    TextView tvFontChange2;
    TextView tvFontChange1;
    TextView tvFontChange3;
    private AlertDialog.Builder builder;

    // var는 전역변수 사용 가능 val은 한 곳에서만 사용 가능 (메소드 안에서만)
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티가 시작할 때 최초 1회만 호출
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 객체 초기화
        mDatabaseHelper = new DatabaseHelper(this);
        mLstDiary = new ArrayList<>();
        mAdapter = new DiaryListAdapter(); // 리사이클러 뷰 어댑터 인스턴스 생성
        builder = new AlertDialog.Builder(this);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        iv_question = findViewById(R.id.iv_question);
        iv_settings = findViewById(R.id.iv_settings);
        mRvDiary = findViewById(R.id.rv_diary);
        drawerLayout = findViewById(R.id.drawerLayout);
        iv_menu = findViewById(R.id.iv_menu);
        tv_Change = (TextView) findViewById(R.id.tvChange);
        tvHome = findViewById(R.id.tvHome);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_developer = (TextView) findViewById(R.id.tv_developer);
        tvFontChange1 = (TextView) findViewById(R.id.tvFontChange1);
        tvFontChange2 = (TextView) findViewById(R.id.tvFontChange2);
        tvFontChange3 = (TextView) findViewById(R.id.tvFontChange3);

        mRvDiary.setAdapter(mAdapter);

        iv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModDialog.class);
                startActivity(intent);
            }
        });

        iv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("작성한 일기를 길게 누르면 수정과 삭제가 가능해요.");

                builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

                builder.setTitle("도움말");
                builder.show();

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 작성하기 버튼을 누를 때 호출되는 곳
                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                startActivity(intent);
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END, true);
            }
        });

        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                startActivity(intent);
            }
        });

        tv_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ModDialog.class);
                startActivity(intent);
            }
        });

        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("작성한 일기를 길게 누르면 수정과 삭제가 가능해요.");

                builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

                builder.setTitle("도움말");
                builder.show();
            }
        });

        tv_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("email : gjalswo3908@gmail.com");

                builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { return; }
                });

                builder.setTitle("피드백을 주시면 힘이 나요.");
                builder.show();
            }
        });

//        API 26이상
//        Typeface typeface1 = getResources().getFont(R.font.nanumsquareroundr);
//        tv_title.setTypeface(typeface1);




//        themeColor = ThemeUtil.modLoad(getApplicationContext());
//        ThemeUtil.applyTheme(themeColor);

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

    }
    /**
     * Activity LifeCycle
     * 날짜 갱신
     */
    // Ctrl + o
    @Override
    protected void onResume() {
        super.onResume();
        tvFontChange2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 나늠스퀘어라운드체
                setFont(1);
                setSpInt(1);
            }
        });
        tvFontChange1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 휴먼범석체
                setFont(0);
                setSpInt(0);
            }
        });
        tvFontChange3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFont(2);
                setSpInt(2);
            }
        });
        // 액티비티가 재시작이 될 때 실행, onCreate와도 같이 실행
        // get load list
        SharedPreferences fontsp = getSharedPreferences("fontmode", Activity.MODE_PRIVATE);
        int fontint = fontsp.getInt("FM", 1);
        switch (fontint) {
            case 0:
                // 휴먼범석체
                setFont(0);
                break;
            case 1:
                // 나눔스퀘어체
                setFont(1);
                break;
            case 2:
                // 다이어리체
                setFont(2);
            default:
                Log.i("폰트 익셉션태그", "폰트 익셉션내용");
        }
        setLoadRecentList();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        setFont(font);
        setLoadRecentList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setLoadRecentList();
    }

    @Override
    public void onBackPressed() {

        /** DrawerRecyclerView 열려있으면 닫고 뒤로가기 한번 더 누를 시 화면 종료 */
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerLayout.closeDrawer(GravityCompat.END);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        } else {
            this.builder.setMessage("앱을 종료하시겠어요?");

            this.builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();
                }
            });

            this.builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });

            this.builder.setTitle("확인");
            this.builder.show();
        }
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