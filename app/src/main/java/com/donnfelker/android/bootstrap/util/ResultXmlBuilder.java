package com.donnfelker.android.bootstrap.util;

import android.app.Activity;
import android.content.Context;
import android.util.Xml;

import com.donnfelker.android.bootstrap.core.inspect.Weather;
import com.donnfelker.android.bootstrap.core.inspect.object.Device;
import com.donnfelker.android.bootstrap.core.inspect.result.DeviceResult;
import com.donnfelker.android.bootstrap.core.inspect.result.InspectEnvironment;
import com.donnfelker.android.bootstrap.core.inspect.result.InspectTool;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by feather on 14-4-13.
 */
public class ResultXmlBuilder {

    public static void Build(Result result, OutputStream out) throws IOException {

        XmlSerializer serializer = Xml.newSerializer();
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        serializer.setOutput(out, "GB2312");
        serializer.startDocument("GB2312", true);

        serializer.startTag(null, "Total");

        // print weather information
        serializer.startTag(null, "weather");
        serializer.attribute(null, "date", result.getEnv().getDate());

        serializer.startTag(null, "temperature");
        serializer.text(result.getEnv().getTemperature());
        serializer.endTag(null, "temperature");

        serializer.startTag(null, "humidity");
        serializer.text(result.getEnv().getHumidity());
        serializer.endTag(null, "humidity");

        serializer.startTag(null, "person");
        serializer.text(result.getEnv().getPersonName());
        serializer.endTag(null, "person");

        serializer.endTag(null, "weather");

        // print tools information
        for (InspectTool tool : result.getTools()) {
            serializer.startTag(null, "tool");
            serializer.attribute(null, "name", tool.getName());

            serializer.startTag(null, "type");
            serializer.text(tool.getType());
            serializer.endTag(null, "type");

            serializer.startTag(null, "num");
            serializer.text(tool.getNum());
            serializer.endTag(null, "num");

            serializer.endTag(null, "tool");
        }

        // print device inspect result
        constructResult(result, serializer);

        serializer.endTag(null, "Total");

        serializer.endDocument();
    }


    private static void constructResult(Result result, XmlSerializer serializer) throws IOException {

        for (DeviceResult deviceResult : result.getDeviceResults()) {

            ArrayList<String> inspectContent = deviceResult.getInspectContent();
            ArrayList<String> inspectStandard = deviceResult.getInspectStandard();
            ArrayList<String> inspectResult = deviceResult.getInspectResult();

            serializer.startTag(null, "device");
            serializer.attribute(null, "name", deviceResult.getDeviceName());
            for (int i=0; i<inspectContent.size(); ++i) {
                serializer.startTag(null, "item");
                serializer.attribute(null, "name",inspectContent.get(i));
                serializer.startTag(null, "standard");
                serializer.text(inspectStandard.get(i));
                serializer.endTag(null, "standard");
                if(inspectResult.get(i).equals("正常")) {
                    serializer.startTag(null, "result");
                    serializer.text(inspectResult.get(i));
                    serializer.endTag(null, "result");
                } else {
                    serializer.startTag(null, "result");
                    serializer.text("异常");
                    serializer.endTag(null, "result");
                    serializer.startTag(null, "exceptions");
                    serializer.text(inspectResult.get(i));
                    serializer.endTag(null, "exceptions");
                }
                serializer.endTag(null, "item");
            }
            serializer.endTag(null, "device");
        }
    }

    public static Result GET(Activity activity, String resultId) throws Exception {

        Result result = new Result();
        DeviceResult device;
        InspectTool tool;
        int toolNum;
        int deviceNum;
        XmlPullParser parser = Xml.newPullParser();
        File xmlResult = new File(activity.getFilesDir(), "result_" + resultId + ".xml");
        InputStream inStream = new FileInputStream(xmlResult);
        int eventType;

        parser.setInput(inStream, "GB2312");
        eventType = parser.getEventType();

        deviceNum = 0;
        toolNum = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    if (name.equalsIgnoreCase("weather")) {
                        InspectEnvironment env = new InspectEnvironment();
                        env.setDate(parser.getAttributeValue(0));
                        Ln.d("get result %s", env.getDate());
                        result.setEnv(env);
                    } else if (name.equalsIgnoreCase("temperature")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            result.getEnv().setTemperature(parser.getText());
                            Ln.d("get result %s", result.getEnv().getTemperature());
                        }
                    } else if (name.equalsIgnoreCase("humidity")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            result.getEnv().setHumidity(parser.getText());
                            Ln.d("get result %s", result.getEnv().getHumidity());
                        }
                    } else if (name.equalsIgnoreCase("person")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            result.getEnv().setPersonName(parser.getText());
                            Ln.d("get result %s", result.getEnv().getPersonName());
                        }
                    } else if (name.equalsIgnoreCase("tool")) {
                        tool = new InspectTool();
                        tool.setName(parser.getAttributeValue(0));
                        Ln.d("get result %s", tool.getName());
                        result.addInspectTools(tool);
                        toolNum++;
                    } else if (name.equalsIgnoreCase("type")) {
                        tool = result.getTools().get(toolNum - 1);
                        if (parser.next() == XmlPullParser.TEXT) {
                            tool.setType(parser.getText());
                            Ln.d("get result %s", tool.getType());
                        }
                    } else if (name.equalsIgnoreCase("num")) {
                        tool = result.getTools().get(toolNum - 1);
                        if (parser.next() == XmlPullParser.TEXT) {
                            tool.setNum(parser.getText());
                            Ln.d("get result %s", tool.getName());
                        }
                    } else if (name.equalsIgnoreCase("device")) {
                        device = new DeviceResult();
                        device.setDeviceName(parser.getAttributeValue(0));
                        result.addDeviceResult(device);
                        deviceNum++;
                        Ln.d("get result %s", device.getDeviceName());
                    } else if (name.equalsIgnoreCase("item")) {
                        device = result.getDeviceResults().get(deviceNum - 1);
                        device.addInspectContent(parser.getAttributeValue(0));
                        Ln.d("get result %s", parser.getAttributeValue(0));
                    } else if (name.equalsIgnoreCase("standard")) {
                        device = result.getDeviceResults().get(deviceNum - 1);
                        if (parser.next() == XmlPullParser.TEXT) {
                            device.addInspectStandard(parser.getText());
                            Ln.d("get result %s", parser.getText());
                        }
                    } else if (name.equalsIgnoreCase("result")) {
                        device = result.getDeviceResults().get(deviceNum - 1);
                        if (parser.next() == XmlPullParser.TEXT) {
                            if (parser.getText().equals("正常")) {
                                device.addInspectResult(parser.getText());
                                Ln.d("get result %s", parser.getText());
                            }
                        }
                    } else if (name.equalsIgnoreCase("exceptions")) {
                        device = result.getDeviceResults().get(deviceNum - 1);
                        if (parser.next() == XmlPullParser.TEXT) {
                            device.addInspectResult(parser.getText());
                            Ln.d("get result %s", parser.getText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }

        return result;
    }
}
