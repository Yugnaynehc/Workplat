package com.donnfelker.android.bootstrap.util;

import android.util.Xml;

import com.donnfelker.android.bootstrap.core.inspect.result.InspectTool;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by feather on 14-4-13.
 */
public class ResultXmlBuilder {

    public static void ResultXmlBuilder(Result result, OutputStream out) throws IOException {

        // use variable cache?

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

        // start printing device inspected information
        /*
        for (Device device : result.getDevices())
        {
            serializer.startTag(null, "device");
            serializer.attribute(null, "name", device.getName());
            for (SubDevice sub : device.getSubDevice()) {
                // build SubDevice part
                List<String> content = sub.getInspectContent();
                List<String> inspectResult = sub.getInspectResult();

                serializer.startTag(null, "sub-device");
                serializer.attribute(null, "name", sub.getName());

                for (int i=0; i < content.size(); ++i) {
                    serializer.startTag(null, "content");

                    serializer.startTag(null, "ins-content");
                    serializer.text(content.get(i));
                    serializer.endTag(null, "ins-content");

                    serializer.startTag(null, "result");
                    serializer.text(inspectResult.get(i));
                    serializer.endTag(null, "result");

                    serializer.endTag(null, "content");
                }

                serializer.endTag(null, "sub-device");
            }
            serializer.endTag(null, "device");
        }
        */

        serializer.endTag(null, result.getType());
        serializer.endDocument();
        out.close();
    }
}
