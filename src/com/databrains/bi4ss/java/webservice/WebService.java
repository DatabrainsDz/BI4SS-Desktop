package com.databrains.bi4ss.java.webservice;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.models.Asociation;
import com.databrains.bi4ss.java.models.YearData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WebService {
    // IP of server
    private static final String HOST = "192.168.137.215";
    private static final String HOST2 = "172.26.0.39:5000";

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

        for (String key : byCityJSONObject.keySet()) {
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

        for (String key : byNatJSONObject.keySet()) {
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

    public static YearData getLevelDataByAdmis(int year, String level) {
        String url = "AdmittedAdjourned/" + year + "?current_year=" + level.charAt(level.length() - 1) +
                "&level=" + level.substring(0, level.length() - 1);
        JSONObject rootObject = getJSONFromServer(url);
        if (rootObject == null) {
            return null;
        }
        JSONObject jsonObject = rootObject.getJSONObject("data");

        YearData yearData = new YearData();

        /* Start Get (parse) data of City (admitted/adjourned) */

        JSONObject byCityJSONObject = jsonObject.getJSONObject("byCity");

        List<AdmisInfo> admisByCities = new LinkedList<>();

        for (String key : byCityJSONObject.keySet()) {
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

        for (String key : byNatJSONObject.keySet()) {
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

    public static YearData getLevelDataBySubjects(int year, String level, char semesterFlag) {
        char semester, semester2;
        if (semesterFlag == '?') {
            semester2 = '2';
            semester = '1';
        } else {
            semester = semesterFlag;
            semester2 = '0';
        }
        String url = "subjects/" + year + "?current_year=" + level.charAt(level.length() - 1) +
                "&level=" + level.substring(0, level.length() - 1) + "&semester=" + semester + "&semester2=" + semester2;
        JSONObject rootObject = getJSONFromServer(url);
        if (rootObject == null) {
            System.out.println("json null");
            return null;
        }
        JSONObject jsonObject = rootObject.getJSONObject("data");

        YearData yearData = new YearData();

        /* Start Get (parse) data of City (admitted/adjourned) */

        JSONObject byCityJSONObject = jsonObject.getJSONObject("byCity");

        List<AdmisInfo> admisByCities = new LinkedList<>();

        for (String key : byCityJSONObject.keySet()) {
            JSONObject jsonObj = byCityJSONObject.getJSONObject(key);
            admisByCities.add(new AdmisInfo(getShortCuteName(key),
                    Integer.parseInt(jsonObj.getString("Tiaret")),
                    Integer.parseInt(jsonObj.getString("Tissemsilt"))
            ));
        }
        yearData.setAdmisCity(admisByCities);

        /* End Get (parse) data of City (admitted/adjourned) */

        /* Start Get (parse) data of Nationality (admitted/adjourned) */

        JSONObject byNatJSONObject = jsonObject.getJSONObject("byNationality");

        List<AdmisInfo> admisByNationality = new LinkedList<>();

        for (String key : byNatJSONObject.keySet()) {
            JSONObject jsonObj = byNatJSONObject.getJSONObject(key);
            int strg = 0;
            if (jsonObj.keySet().contains("entrangère")) strg = Integer.parseInt(jsonObj.getString("entrangère"));
            admisByNationality.add(new AdmisInfo(getShortCuteName(key),
                    Integer.parseInt(jsonObj.getString("algérienne")),
                    strg)
            );
        }
        yearData.setAdmisNationality(admisByNationality);

        /* End Get (parse) data of Nationality (admitted/adjourned) */

        /* Start Get (parse) data of Gender (admitted/adjourned) */

        JSONObject byGenderJSONObject = jsonObject.getJSONObject("byGender");
        List<AdmisInfo> admisByGender = new LinkedList<>();
        for (String key : byGenderJSONObject.keySet()) {
            JSONObject jsonObj = byGenderJSONObject.getJSONObject(key);
            admisByGender.add(new AdmisInfo(getShortCuteName(key),
                    Integer.parseInt(jsonObj.getString("M")),
                    Integer.parseInt(jsonObj.getString("F"))
            ));
        }
        yearData.setAdmisGender(admisByGender);

        /* End Get (parse) data of Gender (admitted/adjourned) */

        return yearData;
    }

    public static List<String> getSubjects(int year, String level, int currentYear) {
        String url = "subjects/info?current_year=" + currentYear + "&level=" + level + "&scholar_year=" + year;
        JSONObject jsonObject = getJSONFromServer(url);
        if (jsonObject == null) {
            return null;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        List<String> subjects = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            subjects.add(jsonArray.getString(i));
        }

        return subjects;
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

    public static boolean getPredictionProfil(char algo, char gender, char nationality, String city, double bac, int age) {
        String url = "http://" + HOST2 + "/profile/" + algo + "/" + gender + "/" + nationality + "/" + city + "/" + bac + "/" + age;
        JSONObject rootObject = null;
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get(url)
                    .asJson();

            rootObject = new JSONObject(jsonResponse.getBody().toString());
        } catch (UnirestException ue) {
            ue.printStackTrace();
        }

        if (rootObject == null) {
            return false;
        }

        return (rootObject.getInt("prediction") == 1);
    }

    public static ArrayList<Asociation> getAsociations(String level, char semester) {
        ArrayList<Asociation> asociation = new ArrayList<>();
        String url = "subjects/association?current_year=" + level.charAt(level.length() - 1) +
                "&level=" + level.substring(0, level.length() - 1) + "&semester=" + semester;
        JSONObject rootObject = getJSONFromServer(url);
        if (rootObject == null) {
            return null;
        }
        JSONArray jsonArray = rootObject.getJSONArray("data");
        String ass;
        for (int j = 0; j < jsonArray.length(); j++) {
            ass = "";
            String key = jsonArray.getJSONObject(j).getString("subject");
            JSONArray modules = jsonArray.getJSONObject(j).getJSONArray("relatedTo");
            for (int i = 0; i < modules.length(); i++) {
                ass += ", " + modules.get(i);
            }
            asociation.add(new Asociation(key, ass));


        }

        return asociation;
    }

    private static String getShortCuteName(String name) {
        String[] splitName = name.split(" ");
        String resultName = splitName[0] + " ";
        for (int i = 1; i < splitName.length; i++) {
            resultName += String.valueOf(splitName[i].charAt(0)).toUpperCase();
        }
        return resultName;
    }

    public static void main(String[] args) {
        ArrayList<Asociation> asociations = getAsociations("MIAS1", '1');
        for (Asociation asociation : asociations) {
            System.out.println(asociation.getModule());
        }
    }
}