package com.MyDiaryProj.mydiary;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

public class ModDialog extends Activity {

    Button close_btn;
    RadioButton r_btn_ligth, r_btn_dark;
    String themeColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_moddialog);

        r_btn_dark = findViewById(R.id.r_btn_dark);
        r_btn_ligth = findViewById(R.id.r_btn_ligth);

        r_btn_ligth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeColor = ThemeUtil.LIGHT_MODE;
                ThemeUtil.applyTheme(themeColor);
                ThemeUtil.modSave(getApplicationContext(), themeColor);
            }
        });

        r_btn_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeColor = ThemeUtil.DARK_MODE;
                ThemeUtil.applyTheme(themeColor);
                ThemeUtil.modSave(getApplicationContext(), themeColor);
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
}
