package com.example.first.thegamedb;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/20/2017.
 */

public class GetLogoUtil {


    static public class GamesPullParser {

        static ArrayList<Games> parseGames(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");

            Games games = null;
            ArrayList<Games> arrayList = new ArrayList<>();

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("Game")) {
                            games = new Games();
                        } else if(parser.getName().equals("id")) {


                            games.setId(parser.nextText());
                        } else if(parser.getName().equals("GameTitle")) {
                            games.setTitle(parser.nextText());
                        } else if(parser.getName().equals("ReleaseDate")) {
                            games.setReleaseDate(parser.nextText());
                        } else if (parser.getName().equals("Platform")) {
                            games.setPlatform(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("Game")) {
                            arrayList.add(games);
                        }
                }
                event = parser.next();
            }
            return arrayList;
        }
    }
}
