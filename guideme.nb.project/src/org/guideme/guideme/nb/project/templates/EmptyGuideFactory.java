package org.guideme.guideme.nb.project.templates;

import org.guideme.guideme.model.Guide;

public class EmptyGuideFactory {

    public static Guide create(String title) {
        Guide guide = new Guide(title);
        guide.addPage("start").setText("Welcome");
        return guide;
    }
}
