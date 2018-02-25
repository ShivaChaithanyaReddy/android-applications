package com.example.first.tedpodcast;

import android.util.Log;

import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vandita on 3/9/17.
 */

public class PodcastUtil {
    static public class PodcastPullParser extends DefaultHandler {
        static public ArrayList<Podcast> parsePodcast(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");

            //object foer news class
            Podcast podcast = null;
            ArrayList<Podcast> newsList = new ArrayList<>();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            podcast = new Podcast();
                        }

                        if (podcast != null) {
                            if (parser.getName().equals("title")) {
                                podcast.setTitle(parser.nextText());
                            } else if (parser.getName().equals("enclosure")) {
                                podcast.setLink(parser.getAttributeValue(null, "url"));
                            } else if (parser.getName().equals("description")) {
                                podcast.setDescription(parser.nextText());
                            } else if (parser.getName().equals("itunes:duration")) {
                                podcast.setDuration(parser.nextText());
                            } else if (parser.getName().equals("pubDate")) {

                                String datestr = parser.nextText();


                                java.util.Date date = new Date(datestr);
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                String format = formatter.format(date);
                                Log.d("date us:  ",""+format);

                                podcast.setReleaseDate(datestr.substring(0,16)+"&&"+format);
                                podcast.setCtimedate(datestr);

                            } else if (parser.getName().equals("itunes:image")) {
                                podcast.setImage(parser.getAttributeValue(null, "href"));

                            }


                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            newsList.add(podcast);
                        }
                }
                event = parser.next();
            }
            return newsList;
        }
    }
}
