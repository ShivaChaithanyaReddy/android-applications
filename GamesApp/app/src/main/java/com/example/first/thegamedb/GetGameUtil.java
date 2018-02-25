package com.example.first.thegamedb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/17/2017.
 */

public class GetGameUtil {
    static public class getGameParser {
        static ArrayList Gparser(InputStream in, String selected) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");
Log.d("","In parser");
            ArrayList<GetGame> arrayList = new ArrayList<>();
            ArrayList<SimilarGames> arrayList1 = new ArrayList<>();

            GetGame getGame = null;
            SimilarGames similarGames = null;
            boolean flag = false;
            boolean imageSet = false;

            int event = parser.getEventType();

            while(event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("Data")) {
                            getGame = new GetGame();
                        getGame.setGid(selected);
                        }
                        else if(parser.getName().equals("Game") && flag == false){
                            flag = true;
                        }else if(parser.getName().equals("baseImgUrl")){
                            getGame.setBaseurl(parser.nextText());
                        }else if(parser.getName().equals("GameTitle")) {
                            getGame.setTitle(parser.nextText());
                        }else if(parser.getName().equals("ReleaseDate")) {
                            getGame.setReleaseDate(parser.nextText());
                        }else if(parser.getName().equals("Platform")) {
                            getGame.setPlatform(parser.nextText());
                        }else if(parser.getName().equals("Overview")) {
                            getGame.setOverview(parser.nextText());
                        }else if(parser.getName().equals("clearlogo ")) {
                            getGame.setLogo(getGame.getBaseurl()+parser.nextText());
                        }else if(parser.getName().equals("genre")) {
                            getGame.setGenre(parser.nextText());
                        } else if(parser.getName().equals("Publisher")) {
                            getGame.setPublisher(parser.nextText());
                        }else if(parser.getName().equals("Youtube")) {
                            getGame.setTrailer(parser.nextText());
                        }else if(parser.getName().equals("SimilarCount")){
                int count = Integer.parseInt(parser.nextText());
                            int temp = 0;
                            parser.next();

                            while(temp <count) {
                                if(parser.getName().equals("Game")) {
                                    similarGames = new SimilarGames();
                                }else if(parser.getName().equals("id")) {
                                    similarGames.setId(parser.nextText());
                                }else if(parser.getName().equals("PlatformId")) {
                                    similarGames.setPlatform(parser.nextText());
                                    arrayList1.add(similarGames);
                                    temp++;
                                }
                                parser.next();
                            }
                            getGame.setArrayList(arrayList1);

                        } else if(parser.getName().equals("original") && imageSet == false) {
                                getGame.setImage(getGame.getBaseurl()+parser.nextText());
                                imageSet = true;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("Data")) {
                            arrayList.add(getGame);
                        }
                }
                event = parser.next();
            }
            return  arrayList;
        }
    }
}
