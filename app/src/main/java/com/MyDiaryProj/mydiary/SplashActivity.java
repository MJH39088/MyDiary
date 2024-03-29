package com.MyDiaryProj.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends BaseActivity {

    String themeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // R은 res파일. 즉 xml과 연동하는 코드
        // onCreate() : Activity (화면) 이 시작될 때 가장 먼저 실행되는 곳 ! [안드로이드 액티비티 생명주기]
        setFontSp();
        themeColor = ThemeUtil.modLoad(getApplicationContext());
        ThemeUtil.applyTheme(themeColor);

        // 딜레이를 발생시켜서 (1~2초 가량) 그 이후에 MainActivity 로 이동 해야 함.
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 메인 액티비티로 이동 하는 구간
                IntentActivity(SplashActivity.this, MainActivity.class); // 현재클래스.this > 다음클래스.class 로 이동하는 클래스 인스턴스 생성, 변수에 저장된 클래스로 이동
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                finish(); // 생명 주기 파괴
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
            }
        }, 1500); // 딜레이 해주고 싶은 초를 적어야 함. (1000 밀리초 = 1초)

    }
}