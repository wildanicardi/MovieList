package com.example.submissionmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ListTvShow implements Parcelable {
    private  int id;
    private String title;
    private String overview;
    private String pic;

    public ListTvShow(JSONObject jsonObject) {
        try {
            int id_tvshow = jsonObject.getInt("id");
            String poster = jsonObject.getString("poster_path");
            String title = jsonObject.getString("name");
            String overview = jsonObject.getString("overview");

            this.id = id_tvshow;
            this.pic = poster;
            this.title = title;
            this.overview = overview;

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public ListTvShow() {

    }

    public ListTvShow(int id, String title, String description, String pic) {
        this.id = id;
        this.title = title;
        this.overview = description;
        this.pic = pic;
    }

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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.pic);
    }

    protected ListTvShow(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.pic = in.readString();
    }

    public static final Creator<ListTvShow> CREATOR = new Creator<ListTvShow>() {
        @Override
        public ListTvShow createFromParcel(Parcel source) {
            return new ListTvShow(source);
        }

        @Override
        public ListTvShow[] newArray(int size) {
            return new ListTvShow[size];
        }
    };
}
