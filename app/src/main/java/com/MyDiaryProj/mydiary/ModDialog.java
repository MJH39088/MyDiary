package com.MyDiaryProj.mydiary;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ModDialog extends Activity {

    Button close_btn;
    RadioButton r_btn_light, r_btn_dark;
    String themeColor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_moddialog);

        r_btn_dark = findViewById(R.id.r_btn_dark);
        r_btn_light = findViewById(R.id.r_btn_light);

        r_btn_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeColor = ThemeUtil.LIGHT_MODE;

                ThemeUtil.applyTheme(themeColor);
                ThemeUtil.modSave(getApplicationContext(), themeColor);

                if (r_btn_light.isEnabled()) {
                    r_btn_dark.setChecked(false);
                }
                if (themeColor == ThemeUtil.LIGHT_MODE) {
                    Toast.makeText(view.getContext(), "라이트 모드로 전환됐어요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        r_btn_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeColor = ThemeUtil.DARK_MODE;
                ThemeUtil.applyTheme(themeColor);
                ThemeUtil.modSave(getApplicationContext(), themeColor);
                if(r_btn_dark.isEnabled()) {
                    r_btn_light.setChecked(false);
                }
                if (themeColor == ThemeUtil.DARK_MODE) {
                    Toast.makeText(view.getContext(), "다크 모드로 전환됐어요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    public void fff() {
//        SharedPreferences fontsp = getSharedPreferences("fontmode", Activity.MODE_PRIVATE);
//        int fontint = fontsp.getInt("FM", 1);
//        switch (fontint) {
//            case 0:
//                // 휴먼범석체
//                setFont(0);
//                break;
//            case 1:
//                // 나눔스퀘어체
//                setFont(1);
//                break;
//            case 2:
//                // 다이어리체
//                setFont(2);
//            default:
//                Log.i("폰트 익셉션태그", "폰트 익셉션내용");
//    }
//
//}
}
