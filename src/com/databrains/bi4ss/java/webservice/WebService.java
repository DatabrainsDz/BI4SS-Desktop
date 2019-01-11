package com.databrains.bi4ss.java.webservice;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.models.YearData;
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
    private static final String HOST = "efb86db8.ngrok.io";

    public static List<AdmisInfo> getPastYearsData() {
        String url = "years/all";
        JSONObject jsonObject = getJSONFromServer(url);
        if (jsonObject == null) {
            return null;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        List<AdmisInfo> generalYearAdmisList = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            AdmisInfo yearAdmis = new AdmisInfo();
            yearAdmis.setName(jsonObj.getString("scholar_year"));
            yearAdmis.setAdmis(Integer.parseInt(jsonObj.getString("admitted")));
            yearAdmis.setAjourne(Integer.parseInt(jsonObj.getString("adjourned")));
            generalYearAdmisList.add(yearAdmis);

        }

        return generalYearAdmisList;
    }

    public static YearData getYearData(int year) {
        String url = "years/" + year;
        JSONObject rootObject = getJSONFromServer(url);
        if (rootObject == null) {
            return null;
        }
        JSONObject jsonObject = rootObject.getJSONObject("data");
        //System.out.println(jsonObject);

        YearData yearData = new YearData();

        /* Start Get (parse) data of City (admitted/adjourned) */

        JSONObject byCityJSONObject = jsonObject.getJSONObject("byCity");

        List<AdmisInfo> admisByCities = new LinkedList<>();

        for(String key : byCityJSONObject.keySet()) {
            JSONObject jsonObj = byCityJSONObject.getJSONObject(key);
            admisByCities.add(new AdmisInfo(key,
                    Integer.parseInt(jsonObj.getString("Admis")),
                    Integer.parseInt(jsonObj.getString("Ajourné"))
            ));
        }
        yearData.setAdmisCity(admisByCities);

        /* End Get (parse) data of City (admitted/adjourned) */

        /* Start Get (parse) data of Nationality (admitted/adjourned) */

        JSONObject byNatJSONObject = jsonObject.getJSONObject("byNationality");

        List<AdmisInfo> admisByNationality = new LinkedList<>();

        for(String key : byNatJSONObject.keySet()) {
            JSONObject jsonObj = byNatJSONObject.getJSONObject(key);
            admisByNationality.add(new AdmisInfo(key,
                    Integer.parseInt(jsonObj.getString("Admis")),
                    Integer.parseInt(jsonObj.getString("Ajourné"))
            ));
        }
        yearData.setAdmisNationality(admisByNationality);

        /* End Get (parse) data of Nationality (admitted/adjourned) */

        /* Start Get (parse) data of Gender (admitted/adjourned) */

        JSONObject byGenderJSONObject = jsonObject.getJSONObject("byGender");

        List<AdmisInfo> admisByGender = new LinkedList<>();

        admisByGender.add(new AdmisInfo("Famale",
                Integer.parseInt(byGenderJSONObject.getJSONObject("F").getString("Admis")),
                Integer.parseInt(byGenderJSONObject.getJSONObject("F").getString("Ajourné"))
        ));
        admisByGender.add(new AdmisInfo("Male",
                Integer.parseInt(byGenderJSONObject.getJSONObject("M").getString("Admis")),
                Integer.parseInt(byGenderJSONObject.getJSONObject("M").getString("Ajourné"))
        ));
        yearData.setAdmisGender(admisByGender);

        /* End Get (parse) data of Gender (admitted/adjourned) */

        return yearData;
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

    public static void main(String[] args) {
        getYearData(2014);
    }
}