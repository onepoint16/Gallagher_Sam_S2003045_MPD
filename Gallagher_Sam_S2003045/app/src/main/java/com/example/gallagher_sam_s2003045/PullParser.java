package com.example.gallagher_sam_s2003045;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class PullParser {
    static final String KEY_MAIN = "channel";
    static final String KEY_SITE = "item";
    static final String KEY_NAME = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_COORDINATES = "point";
    static final String KEY_DATE_PUBLISHED = "pubDate";

    public static ArrayList<TrafficScotlandInfo> parseData(String stringToParse) {

        // List of StackSites that we will return
        ArrayList<TrafficScotlandInfo> tsiList = null;

        Boolean itemFound = false;

        TrafficScotlandInfo item = null;


        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // point the parser to our file.
            xpp.setInput( new StringReader( stringToParse ) );

            // get initial eventType
            int eventType = xpp.getEventType();


            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase(KEY_MAIN))
                    {
                        tsiList = new ArrayList<>();
                    }
                    else if (xpp.getName().equalsIgnoreCase(KEY_SITE))
                    {
                        itemFound = true;
                        item = new TrafficScotlandInfo();
                    }

                    if (itemFound == true)
                    {
                        if (xpp.getName().equalsIgnoreCase(KEY_NAME))
                        {
                            String title = xpp.nextText();
                            item.setRoad(title);
                        }
                        else if (xpp.getName().equalsIgnoreCase(KEY_DESCRIPTION))
                        {
                            String description = xpp.nextText();
                            item.setDescription(description);
                        }
                        else if (xpp.getName().equalsIgnoreCase(KEY_COORDINATES))
                        {
                            String coordinates = xpp.nextText();
                            item.setCoordinates(coordinates);
                        }
                        else if (xpp.getName().equalsIgnoreCase(KEY_DATE_PUBLISHED))
                        {
                            String dateString = xpp.nextText();
                            item.setPublished(dateString);

                        }
                    }

                }
                else if (eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase((KEY_SITE)))
                    {
                        tsiList.add(item);
                    }
                    else if (xpp.getName().equalsIgnoreCase(KEY_MAIN))
                    {
                        int size = tsiList.size();

                        Log.e("string", "Array size " + size);
                    }
                }

                //move on to next iteration
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return tsiList;
    }
}
