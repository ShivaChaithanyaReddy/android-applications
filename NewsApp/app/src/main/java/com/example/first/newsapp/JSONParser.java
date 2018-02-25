package com.example.first.newsapp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/6/2017.
 */

public class JSONParser {

    static public class parser {
        static ArrayList<Articles> articlesParser(String in) throws JSONException {

            ArrayList<Articles> list = new ArrayList<Articles>();

            JSONObject root =  new JSONObject(in);
            JSONArray ArticlesArray = root.getJSONArray("articles");

            for(int i=0;i<ArticlesArray.length();i++) {
                JSONObject articlesObject = ArticlesArray.getJSONObject(i);
                Articles articles = new Articles();

                articles.setAuthor(articlesObject.getString("author"));
                articles.setTitle(articlesObject.getString("title"));
                articles.setDescription(articlesObject.getString("description"));
                articles.setUrlToImage(articlesObject.getString("urlToImage"));
                articles.setPublishedAt(articlesObject.getString("publishedAt"));
                list.add(articles);
            }

            return list;
        }
    }

}