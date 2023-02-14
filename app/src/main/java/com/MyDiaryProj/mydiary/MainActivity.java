package com.MyDiaryProj.mydiary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.RuntimeExecutionException;
import com.google.android.play.core.tasks.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    RecyclerView mRvDiary;              // 리사이클러 뷰 (리스트 뷰)
    DiaryListAdapter mAdapter;          // 리사이클러 뷰와 연동할 어댑터
    ArrayList<DiaryModel> mLstDiary;    // 리스트에 표현할 다이어리 데이터들 (배열)
    DatabaseHelper mDatabaseHelper;     // 데이터베이스 헬퍼 클래스 유틸 객체
    ImageView iv_question, iv_settings, iv_menu, img_nodiary;
    DrawerLayout drawerLayout;
    TextView tvHome, tv_Change, tv_help,  tvFontChangemode, tv_email, tv_reveiw, tv_item_empty_text;
//    tv_developer,


    // var는 전역변수 사용 가능 val은 한 곳에서만 사용 가능 (메소드 안에서만)
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티가 시작할 때 최초 1회만 호출
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        // 데이터베이스 객체 초기화
        mDatabaseHelper = new DatabaseHelper(this);
        mLstDiary = new ArrayList<>();
        mAdapter = new DiaryListAdapter(); // 리사이클러 뷰 어댑터 인스턴스 생성

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        iv_question = findViewById(R.id.iv_question);
        iv_settings = findViewById(R.id.iv_settings);
        mRvDiary = findViewById(R.id.rv_diary);
        drawerLayout = findViewById(R.id.drawerLayout);
        iv_menu = findViewById(R.id.iv_menu);
        tv_Change = (TextView) findViewById(R.id.tvChange);
        tvHome = findViewById(R.id.tvHome);
        tv_help = (TextView) findViewById(R.id.tv_help);
//        tv_developer = (TextView) findViewById(R.id.tv_developer);
        tvFontChangemode = (TextView) findViewById(R.id.tvFontChangemode);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_reveiw = (TextView) findViewById(R.id.tv_review);
        tv_item_empty_text = (TextView) findViewById(R.id.tv_item_empty_text);
        img_nodiary = (ImageView) findViewById(R.id.img_nodiary);

        setAdapterObserver();

        mRvDiary.setAdapter(mAdapter);

        mRvDiary.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(mRvDiary.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisible >= totalItemCount - 1) {
                    setFontSp();
                }
                setFontSp();
            }
        });

        mRvDiary.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = mRvDiary.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    setFontSp();
                }
                int position = mRvDiary.getChildAdapterPosition(child);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        iv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentActivity(getApplicationContext(), ModDialog.class);
                mAdapter.notifyDataSetChanged();
            }
        });

        iv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogNagativeMessage("타이틀 화면에서 작성한 일기를 길게 누르면 수정과 삭제가 가능해요.", "확인", "도움말");
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 작성하기 버튼을 누를 때 호출되는 곳
                IntentActivity(MainActivity.this, DiaryDetailActivity.class);
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
                // Drawer의 일기 작성하기
                IntentActivity(MainActivity.this, DiaryDetailActivity.class);
            }
        });

        tv_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 라이트모드, 다크모드 변경 ModDialog 호출
                IntentActivity(MainActivity.this, ModDialog.class);
            }
        });

        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 메인액티비티 로고 옆 도움말
                setDialogNagativeMessage("작성한 일기를 길게 누르면 수정과 삭제가 가능해요.", "확인", "도움말");
            }
        });

//        tv_developer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setDialogNagativeMessage("email : gjalswo3908@gmail.com", "확인", "피드백을 주시면 힘이 나요.");
//            }
//        });

        tvFontChangemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentActivity(MainActivity.this, FontSelect.class);
            }
        });

        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"hcr71@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[간단 일기장] 의견 보내기");
                email.putExtra(Intent.EXTRA_TEXT, "안녕하세요!\n소중한 의견을 주셔서 감사합니다!\n고객님이 주신 소중한 의견\n신중하게 검토 후 답변드리겠습니다:)" +
                        "\n-------------------------------------\n앱 버전 : "+ BuildConfig.VERSION_NAME +"\n기기명 : "+ Build.MODEL +
                        "\n안드로이드 OS : "+ Build.VERSION.RELEASE +"\n-------------------------------------");
                startActivity(email);
            }
        });

        tv_reveiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                startActivity(intent);
//                인앱리뷰
//                showInAppReviewPopup();
            }
        });

        // 다이어리 샘플 아이템 생성
//        DiaryModel item = new DiaryModel();
//        item.setId(0);
//        item.setTitle("나는 오늘도 피곤하다");
//        item.setContent("허민재 내용입니다.");
//        item.setUserDate("2022/05/19 수");
//        item.setWriteDate("2022/05/19 수");
//        item.setWeatherType(0);
//        mLstDiary.add(item);


    }

    /**
     * Activity LifeCycle
     * 날짜 갱신
     */
    // Ctrl + o
    // 액티비티가 재시작이 될 때 실행, onCreate와도 같이 실행
    @Override
    protected void onResume() {
        super.onResume();
        this.setFontSp();
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

    @Override
    public void onBackPressed() {
        /** DrawerRecyclerView 열려있으면 닫고 뒤로가기 한번 더 누를 시 화면 종료 */
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerLayout.closeDrawer(GravityCompat.END);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("앱을 종료하시겠어요?");
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                    finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                    android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
                }
            });
            builder.setTitle("나가기");
            builder.show();
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

//    인앱리뷰
//    private void showInAppReviewPopup() {
//        ReviewManager manager = ReviewManagerFactory.create(getApplicationContext());
//        Task<ReviewInfo> request = manager.requestReviewFlow();
//        request.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                ReviewInfo reviewInfo = task.getResult();
//                manager.launchReviewFlow(this, reviewInfo).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                    }
//                });
//            } else {
//                @ReviewErrorCode int reviewErrorCode = ((RuntimeExecutionException) task.getException()).getErrorCode();
//            }
//        });
//    }

    private void setAdapterObserver() {
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                checkEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                checkEmpty();
            }
        });
    }

    private void checkEmpty() {
        if (mAdapter.getItemCount() == 0) {
            tv_item_empty_text.setText("현재 저장된 일기가 없어요.");
            img_nodiary.setVisibility(View.VISIBLE);
        }
    }
}