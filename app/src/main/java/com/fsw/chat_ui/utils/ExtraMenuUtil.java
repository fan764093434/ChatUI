package com.fsw.chat_ui.utils;

import android.util.Xml;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.ExtraMenu;
import com.fsw.chat_ui.interfaces.ChatExtendMenuItemClickListener;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ExtraMenuUtil {

    public static List<ExtraMenu> getExtraMenu(InputStream inputStream, ChatExtendMenuItemClickListener listener) {
        XmlPullParser parser = Xml.newPullParser();
        int eventType = 0;
        List<ExtraMenu> extraMenus = null;
        ExtraMenu extraMenu = null;
        try {
            parser.setInput(inputStream, "UTF-8");
            eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        extraMenus = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("ExtraMenu".equals(parser.getName())) {
                            extraMenu = new ExtraMenu();
                        } else if ("id".equals(parser.getName())) {
                            extraMenu.setId(Integer.parseInt(parser.nextText()));
                        } else if ("name".equals(parser.getName())) {
                            extraMenu.setName(parser.nextText());
                        } else if ("background".equals(parser.getName())) {
                            Field backgroundField = R.drawable.class.getDeclaredField(parser.nextText());
                            extraMenu.setBackground(backgroundField.getInt(R.drawable.class));
                        } else if ("icon".equals(parser.getName())) {
                            Field iconField = R.drawable.class.getDeclaredField(parser.nextText());
                            extraMenu.setIcon(iconField.getInt(R.drawable.class));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("ExtraMenu".equals(parser.getName())) {
                            extraMenu.setClickListener(listener);
                            extraMenus.add(extraMenu);
                            extraMenu = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extraMenus;
    }

}
