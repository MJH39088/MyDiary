package com.MyDiaryProj.mydiary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ModDialog extends Activity {

    Button close_btn;
    RadioButton r_btn_light, r_btn_dark;
    TextView tv_Modtitle;
    String themeColor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_moddialog);

        tv_Modtitle = (TextView) findViewById(R.id.tv_Modtitle);
        close_btn = findViewById(R.id.close_btn);
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

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // FontSelect의 설정된 폰트로 설정
        setDialogFont();
    }

    public void setDialogFont() {
        int a1 = ((FontSelect)FontSelect.mContext).a;
        int b1 = ((BaseActivity)BaseActivity.mContext).b;

        // 처음 들어왔을 때 Dialog 내의 폰트 재설정
        switch (b1) {
            case 1:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                break;
            case 2:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                break;
            case 3:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                break;
            case 4:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                break;
            case 5:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                break;
            default:
                // 앱 처음 설치 시 넘어온 변수값이 없기 때문에 익셉션 설정
                Log.i("어댑터 폰트 태그", "현재 저장된 폰트가 없어요.");
                break;
        }

        // 폰트 모드에서 폰트 선택시 Dialog 내의 폰트 재설정
        switch (a1) {
            case 1:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf"));
                break;
            case 2:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"));
                break;
            case 3:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pretendardlight.ttf"));
                break;
            case 4:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf"));
                break;
            case 5:
                r_btn_light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                r_btn_dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                close_btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                tv_Modtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/diary.ttf"));
                break;
            default:
                // 앱 처음 설치 시 넘어온 변수값이 없기 때문에 익셉션 설정
                Log.i("어댑터 폰트 태그", "현재 저장된 폰트가 없어요.");
                break;
        }
    }

}
