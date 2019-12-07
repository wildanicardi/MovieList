package com.example.submissionmovie.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ListMovie implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String pic;

    public ListMovie(int id, String title, String overview, String pic) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.pic = pic;
    }

    public ListMovie(JSONObject jsonObject)
    {
        try {
            int id_tvshow = jsonObject.getInt("id");
            String poster = jsonObject.getString("poster_path");
            String title = jsonObject.getString("title");
            String overview = jsonObject.getString("overview");

            this.id = id_tvshow;
            this.pic = poster;
            this.title = title;
            this.overview = overview;

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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


    public ListMovie() {
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

    protected ListMovie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.pic = in.readString();
    }

    public static final Creator<ListMovie> CREATOR = new Creator<ListMovie>() {
        @Override
        public ListMovie createFromParcel(Parcel source) {
            return new ListMovie(source);
        }

        @Override
        public ListMovie[] newArray(int size) {
            return new ListMovie[size];
        }
    };
}
