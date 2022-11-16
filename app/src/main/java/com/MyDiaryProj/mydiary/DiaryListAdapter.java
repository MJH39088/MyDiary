package com.MyDiaryProj.mydiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Alt + Enter 사용하면 좋음
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {
    ArrayList<DiaryModel> mLstDiary;        // 다이어리 데이터들을 들고 있는 자료형 배열
    Context mContext;
    DatabaseHelper mDatabaseHelper;         // 데이터베이스 헬퍼 클래스 유틸 객체

    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰가 최초로 생성이 될 때 호출되는 곳 = 생성주기 시작이 되는 곳

        mContext = parent.getContext();
        mDatabaseHelper = new DatabaseHelper(mContext); // mContext 데이터를 DatabaseHelper에 보내준다
        View holder = LayoutInflater.from(mContext).inflate(R.layout.list_item_diary, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        // 생성된 아이템 뷰가 실제 연동이 되어지는 곳

        // 날씨의 경우의 수 작성
        int weatherType = mLstDiary.get(position).getWeatherType();
        switch (weatherType) {
            case 0:
                // 맑음
                holder.iv_weather.setImageResource(R.drawable.img_sun);
                break;
            case 1:
                // 흐림뒤갬
                holder.iv_weather.setImageResource(R.drawable.img_cloudy);
                break;
            case 2:
                // 흐림
                holder.iv_weather.setImageResource(R.drawable.img_cloud);
                break;
            case 3:
                // 매우흐림
                holder.iv_weather.setImageResource(R.drawable.img_bad_cloud);
                break;
            case 4:
                // 비
                holder.iv_weather.setImageResource(R.drawable.img_rainy);
                break;
            case 5:
                // 눈
                holder.iv_weather.setImageResource(R.drawable.img_snowy);
                break;
        }
        // 날씨 제목, 일시
        String title = mLstDiary.get(position).getTitle();
        String userDate = mLstDiary.get(position).getUserDate();

        holder.tv_title.setText(title);
        holder.tv_user_date.setText(userDate);
    }

    @Override
    public int getItemCount() {
        // 아이템 뷰의 총 갯수를 관리하는 곳
        return mLstDiary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_weather;
        TextView tv_title, tv_user_date;

        // 하나의 아이템 뷰
        public ViewHolder(@NonNull View itemView) {
            super(itemView); // itemView가 해당하는 영역은 list_item_diary의 리니어 레이아웃의 영역
            iv_weather = itemView.findViewById(R.id.iv_weather);        // 날씨 이미지
            tv_title = itemView.findViewById(R.id.tv_title);            // 다이어리 제목
            tv_user_date = itemView.findViewById(R.id.tv_user_date);    // 사용자 지정 날짜

            // 일반 클릭 (상세 보기)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 현재 클릭이 된 위치 (배열 개념이어서 첫 시작이 0부터 세는 기준)
                    int currentPositon = getAdapterPosition();

                    // 현재 클릭 된 리스트 아이템 정보를 가지는 변수
                    DiaryModel diaryModel = mLstDiary.get(currentPositon);

                    // 화면 이동 및 다이어리 데이터 다음 액티비티로 전달
                    Intent diaryDetailIntent = new Intent(mContext, DiaryDetailActivity.class);
                    diaryDetailIntent.putExtra("diaryModel", diaryModel);       // 다이어리 데이터 넘기기
                    diaryDetailIntent.putExtra("mode", "detail");         // 상세보기 모드로 설정
                    mContext.startActivity(diaryDetailIntent);
                }
            });

            // 선택지 옵션 팝업 (수정, 삭제) 꾹 누르면 작동
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // 현재 클릭이 된 위치 (배열 개념이어서 첫 시작이 0부터 세는 기준)
                    int currentPositon = getAdapterPosition();

                    // 현재 클릭 된 리스트 아이템 정보를 가지는 변수
                    DiaryModel diaryModel = mLstDiary.get(currentPositon);

                    // 버튼 선택지 배열
                    String[] strChoiceArray = {"수정 하기", "삭제 하기"};

                    // 팝업 화면 표시
                    new AlertDialog.Builder(mContext)
                            .setTitle("원하시는 동작을 선택하세요")
                            .setItems(strChoiceArray, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position) {
                                    if (position == 0) {
                                        // 수정 하기 버튼 눌렀을 때..
                                        // 화면 이동 및 다이어리 데이터 다음 액티비티로 전달
                                        Intent diaryDetailIntent = new Intent(mContext, DiaryDetailActivity.class);
                                        diaryDetailIntent.putExtra("diaryModel", diaryModel);       // 다이어리 데이터 넘기기
                                        diaryDetailIntent.putExtra("mode", "modify");         // 수정하기 모드로 설정
                                        mContext.startActivity(diaryDetailIntent);
                                    } else {
                                        // 삭제 하기 버튼 눌렀을 때.
                                        // delete database data
                                        mDatabaseHelper.setDeleteDiaryList(diaryModel.getWriteDate());

                                        //delete ui
                                        mLstDiary.remove(currentPositon);       //배열에서 제거함 (데이터)
                                        //notify는 새로고침 즉 제거하면서 UI를 고친다는 의미 배열도 지워야 되고 UI도 지워야 됨
                                        notifyItemRemoved(currentPositon);      //리스트 뷰에서 제거함
                                        Toast.makeText(view.getContext(), "선택한 일기를 삭제했어요.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();

                    return false;
                }
            });
        }
    }

    public void setListInit(ArrayList<DiaryModel> lstDiary) {
        // 데이터 리스트 update
        mLstDiary = lstDiary;
        notifyDataSetChanged(); // 리스트 뷰 새로고침
    }
}
