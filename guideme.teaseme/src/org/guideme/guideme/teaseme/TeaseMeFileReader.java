package org.guideme.guideme.teaseme;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.guideme.guideme.model.*;
import org.openide.util.Exceptions;

public class TeaseMeFileReader {

    public Guide readGuide(InputStream inputStream) throws IOException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(TmGuideAdapter.class);
            TmGuideAdapter guideAdapter = (TmGuideAdapter) ctx.createUnmarshaller().unmarshal(inputStream);
            return guideAdapter.toGuide();
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
            throw new IOException(ex);
        }
    }
}
