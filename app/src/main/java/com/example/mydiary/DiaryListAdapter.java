package com.example.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Alt + Enter 사용하면 좋음
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {
    ArrayList<DiaryModel> mLst;
    Context mContext;

    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰가 최초로 생성이 될 때 호출되는 곳 = 생성주기 시작이 되는 곳

        mContext = parent.getContext();
        View holder = LayoutInflater.from(mContext).inflate(R.layout.)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        // 생성된 아이템 뷰가 실제 연동이 되어지는 곳

    }

    @Override
    public int getItemCount() {
        // 아이템 뷰의 총 갯수를 관리하는 곳
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 하나의 아이템 뷰
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
        }
    }
}
