package com.databrains.bi4ss.java.webservice;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class WebService {
    // IP of server
    private static final String HOST = "65b83e0d.ngrok.io";

    public static List<AdmisInfo> getPastYearsData() {
        String url = "years/all";
        JSONObject jsonObject = getJSONFromServer(url);
        if(jsonObject == null) {
            return null;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        List<AdmisInfo> generalYearAdmisList = new LinkedList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            AdmisInfo yearAdmis = new AdmisInfo();
            yearAdmis.setName(jsonObj.getString("scholar_year"));
            yearAdmis.setAdmis(Integer.parseInt(jsonObj.getString("admitted")));
            yearAdmis.setAjourne(Integer.parseInt(jsonObj.getString("adjourned")));
            generalYearAdmisList.add(yearAdmis);

        }

        return generalYearAdmisList;
    }

    public static Object getYearData(int year) {
        return null;
    }

    public static Object getLevelDataByAdmis(int year, String level) {
        return null;
    }

    public static Object getLevelDataBySubjects(int year, String level) {
        return null;
    }

    private static JSONObject getJSONFromServer(String url) {
        JSONObject jsonObject = null;
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get("http://" + HOST + "/databrains/" + url)
                    .asJson();

            jsonObject = new JSONObject(jsonResponse.getBody().toString());
        } catch (UnirestException ue) {
            ue.printStackTrace();
        }
        return jsonObject;
    }

}