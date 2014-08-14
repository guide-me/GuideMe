package org.guideme.guideme.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.guideme.guideme.model.Guide;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;

public class XmlFileGuideSerializer extends GuideSerializer {

    @Override
    public Guide ReadGuide(InputStream inputStream) throws IOException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(XmlGuideAdapter.class);
            XmlGuideAdapter guideAdapter = (XmlGuideAdapter) ctx.createUnmarshaller().unmarshal(inputStream);
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

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            Document document = docBuilderFactory.newDocumentBuilder().newDocument();

            marshaller.marshal(guideAdapter, document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer nullTransformer = transformerFactory.newTransformer();
            nullTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            nullTransformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            nullTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            nullTransformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "Text Script");
            nullTransformer.transform(new DOMSource(document), new StreamResult(outputStream));

        } catch (ParserConfigurationException | JAXBException | TransformerException ex) {
            Exceptions.printStackTrace(ex);
            throw new IOException(ex);
        }
    }
}
