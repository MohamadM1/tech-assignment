package com.example.techassignment;

import org.json.JSONArray;
import org.json.JSONObject;

public class searchController {

    private final JSONArray jukesJSONArray;

    public searchController(JSONArray jukesJSONArray) {
        this.jukesJSONArray = jukesJSONArray;
    }

    //Gets a JSONObject when given key and the identifying value
    public JSONObject findObjectinArray(String key, String id, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString(key).equals(id))
                return jsonArray.getJSONObject(i);
        }
        return null;
    }


    public JSONArray getJukes(JSONArray requirements, String model) {

        JSONArray resultArray = new JSONArray();
        JSONObject jukeboxJSONObject = new JSONObject();
        JSONArray componentsJSONArray = new JSONArray();
        String component;
        String requirement;
        boolean componentFound;
        int requirementTotal = requirements.length();
        int requirementCount = 0;

        //No requirements
        if (requirementTotal == 0) {
            for (int i = 0; i < jukesJSONArray.length(); i++) {
                if (model.equals("") == false && model.equals(jukesJSONArray.getJSONObject(i).getString("model")) == false) {
                    continue;
                }
                resultArray.put(jukesJSONArray.getJSONObject(i));
            }
        }

        //Going through jukes; 'i' is the index of a jukebox
        for (int i = 0; i < jukesJSONArray.length(); i++) {
            componentFound = true;
            requirementCount = 0;
            jukeboxJSONObject = jukesJSONArray.getJSONObject(i);
            componentsJSONArray = jukeboxJSONObject.getJSONArray("components");
            if (model.equals("") == false && model.equals(jukesJSONArray.getJSONObject(i).getString("model")) == false) {
                continue;
            }
            //Going through requirements
            for (int j = 0; j < requirements.length(); j++) {
                requirement = requirements.getString(j);
                if (componentFound == false)
                    break;
                componentFound = true;
                //Checking if requirement 'j' is present in the components (k) jukebox 'i'
                for (int k = 0; k < componentsJSONArray.length(); k++) {
                    component = componentsJSONArray.getJSONObject(k).getString("name");
                    componentFound = false;
                    if (component.equals(requirement)) {
                        requirementCount++;
                        //adding Jukebox i to the array
                        if (requirementCount == requirementTotal) {
                            resultArray.put(jukesJSONArray.getJSONObject(i));
                        }
                        componentFound = true;
                        break;
                    }
                }
            }
        }
        return resultArray;
    }

}
