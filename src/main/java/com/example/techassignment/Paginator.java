package com.example.techassignment;

import org.json.JSONArray;

public class Paginator {
    private int offset;
    private int limit;
    private int number_of_pages;
    private JSONArray correctJukes;


    public Paginator(int offset, int limit, int number_of_pages, JSONArray correctJukes) {
        this.offset = offset;
        this.limit = limit;
        this.number_of_pages = number_of_pages;
        this.correctJukes = correctJukes;
    }

    public String paginate() {
        String resultString = "";
        resultString = resultString + " <tr><th>Id</th><th>Model</th><th>Components</th></tr>";
        int end_jukebox = offset * limit;
        int start_jukebox = end_jukebox - limit;
        if (end_jukebox > correctJukes.length())
            end_jukebox = correctJukes.length();

        for (int i = start_jukebox; i < end_jukebox; i++) {
            resultString = resultString + "<tr>\n";
            resultString = resultString + "<td>" + correctJukes.getJSONObject(i).getString("id") + "</td>\n";
            resultString = resultString + "<td>" + correctJukes.getJSONObject(i).getString("model") + "</td>\n<td><ul>";
            for (int j = 0; j < correctJukes.getJSONObject(i).getJSONArray("components").length(); j++) {
                resultString = resultString + "<li>" + correctJukes.getJSONObject(i).getJSONArray("components").getJSONObject(j).get("name") + "</li>";
            }
            resultString = resultString + "</ul>\n</td>\n</tr>\n";
        }
        return "<html><header><title>Assignment</title></header><body><table style=\"width:30%\">" + resultString + "</table></body><hr><footer><p>Page " + offset + " out of " + number_of_pages + "</p></p><a href=\"http://localhost:8080\">Go Back</a></footer></html>";
    }
}
