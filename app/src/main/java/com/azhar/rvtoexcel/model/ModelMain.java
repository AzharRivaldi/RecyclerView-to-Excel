package com.azhar.rvtoexcel.model;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Azhar Rivaldi on 07-09-2022
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class ModelMain {

    int strId;
    String strType;
    String strName;
    String strRelease;
    String strDesc;
    String strPhoto;
    String strPopularity;
    String strVote;
    String strCover;

    public int getStrId() {
        return strId;
    }

    public void setStrId(int strId) {
        this.strId = strId;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrRelease() {
        return strRelease;
    }

    public void setStrRelease(String strRelease) {
        this.strRelease = strRelease;
    }

    public String getStrDesc() {
        return strDesc;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public String getStrPhoto() {
        return strPhoto;
    }

    public void setStrPhoto(String strPhoto) {
        this.strPhoto = strPhoto;
    }

    public String getStrPopularity() {
        return strPopularity;
    }

    public void setStrPopularity(String strPopularity) {
        this.strPopularity = strPopularity;
    }

    public String getStrVote() {
        return strVote;
    }

    public void setStrVote(String strVote) {
        this.strVote = strVote;
    }

    public String getStrCover() {
        return strCover;
    }

    public void setStrCover(String strCover) {
        this.strCover = strCover;
    }

    public ModelMain(JSONObject object) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int id = object.getInt("id");
            String description = object.getString("overview");
            String popularity = object.getString("popularity");
            String release = object.getString("first_air_date");
            String title = object.getString("name");
            String url_image = object.getString("poster_path");
            String vote = object.getString("vote_average");
            String url_cover = object.getString("backdrop_path");

            this.strId = id;
            this.strDesc = description;
            this.strPopularity = popularity;
            this.strRelease = formatter.format(dateFormat.parse(release));
            this.strName = title;
            this.strPhoto = url_image;
            this.strVote = vote;
            this.strCover = url_cover;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
