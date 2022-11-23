package com.MyDiaryProj.mydiary;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FontSelect extends BaseActivity{

    ImageView iv_selectfontback;
    TextView tv_examplefont20, tv_examplefont26;
    RadioButton rb_humanbumsuk, rb_nanumsquare, rb_efdiary;
    protected Typeface mTypeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfont);

        iv_selectfontback = (ImageView) findViewById(R.id.iv_selectfontback);
        tv_examplefont20 = (TextView) findViewById(R.id.tv_examplefont20);
        tv_examplefont26 = (TextView) findViewById(R.id.tv_examplefont26);
        rb_humanbumsuk = (RadioButton) findViewById(R.id.rb_humanbumsuk);
        rb_nanumsquare = (RadioButton) findViewById(R.id.rb_nanumsquare);
        rb_efdiary = (RadioButton) findViewById(R.id.rb_efdiary);

        iv_selectfontback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rb_humanbumsuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeface = Typeface.createFromAsset(getAssets(), "fonts/humanbumsuk.ttf");
                tv_examplefont20.setTypeface(mTypeface);
                tv_examplefont26.setTypeface(mTypeface);
                setSpInt(0);

                if (rb_humanbumsuk.isEnabled()) {
                    rb_efdiary.setChecked(false);
                    rb_nanumsquare.setChecked(false);
                }
            }
        });

        rb_nanumsquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeface = Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf");
                tv_examplefont20.setTypeface(mTypeface);
                tv_examplefont26.setTypeface(mTypeface);
                setSpInt(1);

                if (rb_nanumsquare.isEnabled()) {
                    rb_humanbumsuk.setChecked(false);
                    rb_efdiary.setChecked(false);
                }
            }
        });

        rb_efdiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeface = Typeface.createFromAsset(getAssets(), "fonts/EF_Diary.ttf");
                tv_examplefont20.setTypeface(mTypeface);
                tv_examplefont26.setTypeface(mTypeface);
                setSpInt(2);

                if (rb_efdiary.isEnabled()) {
                    rb_humanbumsuk.setChecked(false);
                    rb_nanumsquare.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
