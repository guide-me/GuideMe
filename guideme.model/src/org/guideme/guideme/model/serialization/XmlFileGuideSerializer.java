package org.guideme.guideme.model.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.guideme.guideme.model.Guide;
import org.openide.util.Exceptions;

public class XmlFileGuideSerializer extends GuideSerializer {

    @Override
    public Guide ReadGuide(InputStream inputStream) throws IOException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(XmlGuideAdapter.class);
            XmlGuideAdapter guideAdapter = (XmlGuideAdapter)ctx.createUnmarshaller().unmarshal(inputStream);
            return guideAdapter.toGuide();
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
            throw new IOException(ex);
        }
    }
    
    @Override
    public void WriteGuide(Guide guide, OutputStream outputStream) throws IOException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(XmlGuideAdapter.class);
            XmlGuideAdapter guideAdapter = new XmlGuideAdapter(guide);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(guideAdapter, outputStream);
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
            throw new IOException(ex);
        }
    }
}
