package com.example.techassignment;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class assignmentController {
    dataRetriever dataRetriever = new dataRetriever();
    JSONObject jsonObject = new JSONObject();

    String jukesResponseString;
    String jukesJSONString;
    JSONArray jukesJSONArray = new JSONArray();

    String settingsResponseString;
    JSONArray settingsJSONArray = new JSONArray();

    JSONArray requirements;


    @GetMapping("/assignment")
    @ResponseBody
    public String assignment(@RequestParam(value = "settingId") String settingId, @RequestParam(value = "model", required = false, defaultValue = "") String model, @RequestParam(value = "offset", required = false, defaultValue = "1") int offset, @RequestParam(value = "limit", required = false, defaultValue = "1") int limit) {

        if (limit <= 0 || offset <= 0)
            return "<html><head><title>Error</title></head><body><h1>Error "+ HttpStatus.BAD_REQUEST +"</h1><hr><p>Both offset and limit are greater than, or equal to 1.</p><a href=\"http://localhost:8080\">Go Back</a></body></html>";
        try {
            try {
                jukesResponseString = dataRetriever.get("jukes");
            } catch (Exception e) {
                return "<html><head><title>Error</title></head><body><h1>Error "+HttpStatus.BAD_GATEWAY+"</h1><hr><p>Please check your internet connection.</p><a href=\"http://localhost:8080\">Go Back</a></body></html>";
            }
            jukesJSONString = "{\"jukeboxes\":" + jukesResponseString + "}";
            jsonObject = new JSONObject(jukesJSONString);
            jukesJSONArray = jsonObject.getJSONArray("jukeboxes");

            settingsResponseString = dataRetriever.get("settings");
            jsonObject = new JSONObject(settingsResponseString);
            settingsJSONArray = jsonObject.getJSONArray("settings");

            searchController searchController = new searchController(jukesJSONArray);
            jsonObject = (searchController.findObjectinArray("id", settingId, settingsJSONArray));
            try {
                requirements = jsonObject.getJSONArray("requires");
            } catch (Exception e) {
                return "<html><head><title>Error</title></head><body><h1>Error "+HttpStatus.BAD_REQUEST+"</h1><hr><p>Enter a valid Setting Id.</p><a href=\"http://localhost:8080\">Go Back</a></body></html>";
            }

            JSONArray correctJukes = searchController.getJukes(requirements, model);
            if (correctJukes.length() == 0)
                return "<html><header><title>No jukeboxes found</title><header><body><p>Unfortunately there are no jukeboxes that meet those requirements.</p><hr><a href=\"http://localhost:8080\">Go Back</a></body></html>";
            if (limit > correctJukes.length())
                limit = correctJukes.length();

            int number_of_pages;
            if (correctJukes.length() % limit == 0) {
                number_of_pages = correctJukes.length() / limit;
            } else number_of_pages = (correctJukes.length() / limit) + 1;
            if (offset > number_of_pages) {
                return "<html><head><title>Error</title></head><body><h1>Error "+HttpStatus.BAD_REQUEST+"</h1><hr><p>Offset is too large. Maximum offset is " + number_of_pages + ".</p><a href=\"http://localhost:8080\">Go Back</a></body></html>";
            }

            Paginator paginator = new Paginator(offset, limit, number_of_pages, correctJukes);
            return paginator.paginate();

        } catch (Exception e) {
            return "<html><header><title>Error</title><header><body><h1>Error "+HttpStatus.INTERNAL_SERVER_ERROR+"</h1><hr><p>" + e.getCause().toString() + "</p><hr><a href=\"http://localhost:8080\">Go Back</a></body></html>";
        }
    }
}