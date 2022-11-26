package com.MyDiaryProj.mydiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FontSelect extends BaseActivity{

    ImageView iv_selectfontback;
    TextView tv_examplefont20, tv_examplefont26;
    RadioButton rb_humanbumsuk, rb_nanumsquare, rb_efdiary, rb_maple;
    protected Typeface mTypeface;
    public static Context mContext;
    public static int a;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfont);
        mContext = this;

        iv_selectfontback = (ImageView) findViewById(R.id.iv_selectfontback);
        tv_examplefont20 = (TextView) findViewById(R.id.tv_examplefont20);
        tv_examplefont26 = (TextView) findViewById(R.id.tv_examplefont26);
        rb_humanbumsuk = (RadioButton) findViewById(R.id.rb_humanbumsuk);
        rb_nanumsquare = (RadioButton) findViewById(R.id.rb_nanumsquare);
        rb_efdiary = (RadioButton) findViewById(R.id.rb_efdiary);
        rb_maple = (RadioButton) findViewById(R.id.rb_maple);

        iv_selectfontback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_humanbumsuk.isChecked()) {
                    Toast.makeText(FontSelect.this, "휴먼범석체로 설정되었어요.", Toast.LENGTH_SHORT).show();
                } else if (rb_nanumsquare.isChecked()) {
                    Toast.makeText(FontSelect.this, "나눔스퀘어체로 설정되었어요.", Toast.LENGTH_SHORT).show();
                } else if (rb_efdiary.isChecked()) {
                    Toast.makeText(FontSelect.this, "다이어리체로 설정되었어요.", Toast.LENGTH_SHORT).show();
                } else if (rb_maple.isChecked()) {
                    Toast.makeText(FontSelect.this, "메이플스토리체로 설정되었어요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FontSelect.this, "폰트 설정을 하지 않고 나왔어요.", Toast.LENGTH_SHORT).show();
                }
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
                a = 1;

                if (rb_humanbumsuk.isEnabled()) {
                    rb_efdiary.setChecked(false);
                    rb_nanumsquare.setChecked(false);
                    rb_maple.setChecked(false);
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
                a = 2;

                if (rb_nanumsquare.isEnabled()) {
                    rb_humanbumsuk.setChecked(false);
                    rb_efdiary.setChecked(false);
                    rb_maple.setChecked(false);
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
                a = 3;

                if (rb_efdiary.isEnabled()) {
                    rb_humanbumsuk.setChecked(false);
                    rb_nanumsquare.setChecked(false);
                    rb_maple.setChecked(false);
                }
            }
        });

        rb_maple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeface = Typeface.createFromAsset(getAssets(), "fonts/maplestory_light.ttf");
                tv_examplefont20.setTypeface(mTypeface);
                tv_examplefont26.setTypeface(mTypeface);
                setSpInt(3);
                a = 4;

                if (rb_efdiary.isEnabled()) {
                    rb_humanbumsuk.setChecked(false);
                    rb_nanumsquare.setChecked(false);
                    rb_efdiary.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (rb_humanbumsuk.isChecked()) {
            Toast.makeText(FontSelect.this, "휴먼범석체로 설정되었어요.", Toast.LENGTH_SHORT).show();
        } else if (rb_nanumsquare.isChecked()) {
            Toast.makeText(FontSelect.this, "나눔스퀘어체로 설정되었어요.", Toast.LENGTH_SHORT).show();
        } else if (rb_efdiary.isChecked()) {
            Toast.makeText(FontSelect.this, "다이어리체로 설정되었어요.", Toast.LENGTH_SHORT).show();
        } else if (rb_maple.isChecked()) {
            Toast.makeText(FontSelect.this, "메이플스토리체로 설정되었어요.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(FontSelect.this, "폰트 설정을 하지 않고 나왔어요.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
