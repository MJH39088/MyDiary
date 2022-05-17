package com.example.mydiary;

/** 다이어리 리스트 아이템을 구성하는 모델(표본) **/
public class DiaryModel {
    int id;             // 게시물 고유 ID 값
    String title;       // 게시물 제목
    String content;     // 게시물 내용
    int weatherType;    // 날씨 값 (0:맑음 1:흐림뒤갬 2:흐림 3:매우흐림 4:비 5:눈)
    String userDate;    // 사용자가 지정한 날짜(일시)
    String writeDate;   // 게시글 작성한 날짜(일시)

    // getter & setter 게터세터 Alt + Insert
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
