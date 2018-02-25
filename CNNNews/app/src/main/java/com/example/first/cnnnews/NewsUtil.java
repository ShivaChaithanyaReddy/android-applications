package com.example.first.cnnnews;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chaithanya on 2/13/2017.
 */

public class NewsUtil {
    static public class NewsUtilParser {
        static ArrayList<News> parseNews(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");

            //object foer news class
            News news = null;
            ArrayList<News> newsList = new ArrayList<>();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("item")) {
                                news = new News();
                        }

                        if(news != null) {
                            if(parser.getName().equals("title")) {
                               news.setTitle(parser.nextText());
                            }else if(parser.getName().equals("description")) {
                                news.setDesciption(parser.nextText());
                            }else if(parser.getName().equals("pubDate")) {

                                String datestr = parser.nextText();
                                SimpleDateFormat format1 = new SimpleDateFormat("EE MMM dd hh:mm:ss z yyyy");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(format1.parse(datestr));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                news.setPubDate(format.format(c.getTime()));
                            }else if(parser.getName().equals("media:content")) {
                                if(Integer.parseInt(parser.getAttributeValue(null,"height" )) == 300 &&   Integer.parseInt(parser.getAttributeValue(null,"width" )) == 300 ) {
                                    news.setImage(parser.getAttributeValue(null,"url"));
                                }
                            }


                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")) {
                            newsList.add(news);
                        }
                }
                event = parser.next();
            }
        return newsList;
        }
    }
}
