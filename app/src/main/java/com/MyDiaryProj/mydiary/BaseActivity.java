package com.MyDiaryProj.mydiary;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected Typeface mTypeface = null;
    public static Context mContext;
    public static int b;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFontSp();
        mContext=this;
        setb();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mTypeface = null;
        super.onDestroy();
    }

    protected void setb() {
        // setb를 onCreate안에 넣어줌으로서 MainActivity 진입 시 b의 값 설정, 앱 설치 시 0이 기본이므로 휴먼범석체가 기본.
        SharedPreferences fontsp = getSharedPreferences("fontmode", Activity.MODE_PRIVATE);
        int fontint = fontsp.getInt("FM", 0);
        switch (fontint) {
            case 0:
                // 휴먼범석체
                b = 1;
                break;
            case 1:
                // 나눔스퀘어체
                b = 2;
                break;
            case 2:
                // 다이어리체
                b = 3;
            case 3:
                // 메이플스토리체
                b = 4;
            default:
                Log.i("b 태그", "b 익셉션");
        }
    }

    protected void setFont(int getFont) {
        switch (getFont) {
            case 0:
                // 휴먼범석체
                mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/humanbumsuk.ttf");
                break;
            case 1:
                // 나눔스퀘어체
                mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/nanumsquareroundr.ttf");
                break;
            case 2:
                // 다이어리체
                mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/EF_Diary.ttf");
            case 3:
                // 메이플스토리체
                mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/maplestory_light.ttf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int vgCnt = vg.getChildCount();
                for (int i = 0; i < vgCnt; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }

    protected void setFontSp() {
        // fontsp의 저장 정보를 가져와서 액티비티에 폰트를 뿌림
        SharedPreferences fontsp = getSharedPreferences("fontmode", Activity.MODE_PRIVATE);
        // 앱 처음 설치시 휴먼범석체로 지정
        int fontint = fontsp.getInt("FM", 0);
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
            case 3:
                // 메이플스토리체
                setFont(3);
            default:
                Log.i("폰트 익셉션태그", "폰트 익셉션");
        }
    }

    protected void setSpInt(int i) {
        SharedPreferences fontsp = getSharedPreferences("fontmode", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = fontsp.edit();
        editor.putInt("FM", i);
        editor.commit();
    }

    protected void setDialogNagativeMessage(String setMessage, String setButtonText, String setTitle) {
        // 확인 버튼만 있는 경우 리턴한다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(setMessage);
        builder.setNegativeButton(setButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.setTitle(setTitle);
        builder.show();
    }

    protected void setDialogPositiveMessage(String setMessage, String setNagativeButtonText, String setPositiveButtonText, String setTitle) {
        // 작업 중인 액티비티에서 나갈 경우를 묻는다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(setMessage);
        builder.setNegativeButton(setNagativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.setPositiveButton(setPositiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setTitle(setTitle);
        builder.show();
    }

    protected void IntentActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
    }
}
