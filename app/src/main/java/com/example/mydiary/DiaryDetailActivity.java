package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/** 상세보기 화면 or 작성하기 화면이다. **/
public class DiaryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvDate;               // 일시 설정 텍스트
    private EditText mEtTitle, mEtContent;  // 일기 제목, 일기 내용란
    private RadioGroup mRgWeather;          // 날씨
    private int mSelectedWeatherType = -1;  // 선택된 날씨 값 (1~6)
    private String mSelectedUserDate = "";  // 사용자가 실제 다이어리에 표시한 일자
    private String mBeforeDate = "";        // intent로 받아낸 게시글 기존 작성 일자
    private String mDetailMode = "";        // intent로 받아낸 게시글 모드

    private DatabaseHelper mDatabaseHelper; // database 유틸 객체


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        // Database 객체 생성
        mDatabaseHelper = new DatabaseHelper(this);

        mTvDate = findViewById(R.id.tv_date);                   // 일시 설정 텍스트
        mEtTitle = findViewById(R.id.et_title);                 // 제목 입력 필드
        mEtContent = findViewById(R.id.et_content);             // 내용 입력 필드
        mRgWeather = findViewById(R.id.rg_weather);             // 날씨 선택 라디오 그룹

        ImageView iv_back = findViewById(R.id.iv_back);         // 뒤로가기 버튼
        ImageView iv_check = findViewById(R.id.iv_check);       // 작성완료 버튼

        iv_back.setOnClickListener(this);                       // 클릭 기능 부여
        iv_check.setOnClickListener(this);                      // 클릭 기능 부여 밑의 onClick의 view 인자로 전달됨
        mTvDate.setOnClickListener(this);                       // 클릭 기능 부여

        // 기본으로 설정될 날짜의 값을 지정 (디바이스 현재 시간 기준)
        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(new Date());
        // String 값인 mSelectedUserDate(현재 날짜)를 mTvDate에 넣는다.
        mTvDate.setText(mSelectedUserDate);
    }

    @Override
    public void onClick(View view) {
        // setOnClickListener가 붙어있는 뷰들은 클릭이 발생하면 모두 이곳을 수행하게 된다.
        switch (view.getId()) {
            case R.id.iv_back:
                // 뒤로가기 버튼 | 생명 주기가 파괴되면 자동으로 전의 액티비티로 감.
                finish();
                break;
            case R.id.iv_check:
                // 작성 완료 버튼 | indexOfChild는 라디오 그룹의 라디오 버튼을 찾기 위함
                // mRgWeather.getCheckedRadioButtonId()은 현재 체크된 Id값을 체크해서 mSelectedWeatherType으로 넘겨줌
                mSelectedWeatherType = mRgWeather.indexOfChild(findViewById(mRgWeather.getCheckedRadioButtonId()));

                // 입력필드 작성란이 비어있는지 체크
                if (mEtTitle.getText().length() == 0 || mEtContent.getText().length() == 0) {
                    // error | Toast Tab을 누르면 자동으로 완성됨
                    Toast.makeText(this, "입력되지 않은 필드가 존재합니다.", Toast.LENGTH_SHORT).show();
                    return; // 밑의 로직을 태우지 않고 되돌려보냄..!
                }

                // 날씨 선택이 되어있는지 체크
                if (mSelectedWeatherType == -1) {
                    //error
                    Toast.makeText(this, "날씨를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 이곳까지 도착했다면 에러상황이 없으므로 데이터 저장

                String title = mEtTitle.getText().toString();       // 제목 입력 값 toString으로 String 값으로 변환
                String content = mEtContent.getText().toString();   // 내용 입력 값 toString으로 String 값으로 변환
                String userDate = mSelectedUserDate;                // 사용자가 선택한 일시

                String writeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN).format(new Date()); // 작성 완료 누른 시점의 일시

                // 데이터베이스에 저장
                mDatabaseHelper.setInsertDiaryList(title, content, mSelectedWeatherType, userDate, writeDate);
                Toast.makeText(this, "다이어리 동륵이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                finish(); // 현재 액티비티 종료
                break;
            case R.id.tv_date:
                // 일시 설정 텍스트
                // 달력을 띄워서 사용자에게 일시를 입력 받는다.
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // 달력에 선택 된 (년, 월, 일)을 가지고와서 다시 캘린더 함수에 넣어줘서 사용자가 선택한 요일을 알아낸다.
                        Calendar innerCal = Calendar.getInstance();
                        innerCal.set(Calendar.YEAR, year);
                        innerCal.set(Calendar.MONTH, month);
                        innerCal.set(Calendar.DATE, day);

                        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(innerCal.getTime());
                        mTvDate.setText(mSelectedUserDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show();  // 다이얼로그 (팝업) 활성화!
                break;
        }
    }
}