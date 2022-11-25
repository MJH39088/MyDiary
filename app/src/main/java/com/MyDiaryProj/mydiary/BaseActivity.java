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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFontSp();
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
                mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/EF_Diary.ttf");
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
