package com.donnfelker.android.bootstrap.util;

import android.content.Context;
import android.util.Xml;

import com.donnfelker.android.bootstrap.core.inspect.result.DeviceResult;
import com.donnfelker.android.bootstrap.core.inspect.result.InspectTool;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by feather on 14-4-13.
 */
public class ResultXmlBuilder {

    public static void Build(Result result, OutputStream out) throws IOException {

        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(out, "utf-8");
        serializer.startDocument("utf-8", true);

        // print inspect type in result xml file
        serializer.startTag(null, result.getType());

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
        serializer.startTag(null, "Total");
        constructResult(result, serializer);
        serializer.endTag(null, "Total");

        serializer.endTag(null, result.getType());

        serializer.endDocument();
        //out.close();
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
}
