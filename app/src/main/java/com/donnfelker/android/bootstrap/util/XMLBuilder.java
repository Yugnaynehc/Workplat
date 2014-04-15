package com.donnfelker.android.bootstrap.util;

import android.util.Xml;

import com.donnfelker.android.bootstrap.core.inspect.object.Device;
import com.donnfelker.android.bootstrap.core.inspect.object.subdevice.SubDevice;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feather on 14-4-10.
 */
public class XMLBuilder {

    public static void buildXML(String inspectType, Device device, OutputStream out) throws IOException{
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(out, "utf-8");
        serializer.startDocument("utf-8", true);
        serializer.startTag(null, inspectType);
        serializer.startTag(null, "device");
        serializer.attribute(null, "name", device.getName());
        for (SubDevice sub : device.getSubDevice()) {
            // build SubDevice part
            List<String> content = sub.getInspectContent();
            //List<String> result = sub.getInspectResult();

            serializer.startTag(null, "sub-device");
            serializer.attribute(null, "name", sub.getName());

            for (int i=0; i < content.size(); ++i) {
                serializer.startTag(null, "content");

                serializer.startTag(null, "ins-content");
                serializer.text(content.get(i));
                serializer.endTag(null, "ins-content");

                serializer.startTag(null, "result");
                serializer.text("暂无结果");
                serializer.endTag(null, "result");

                serializer.endTag(null, "content");
            }

            serializer.endTag(null, "sub-device");
        }
        serializer.endTag(null, "device");
        serializer.endTag(null, inspectType);
        serializer.endDocument();
        out.close();
    }
}
