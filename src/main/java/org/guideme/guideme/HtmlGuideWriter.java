package org.guideme.guideme;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class HtmlGuideWriter {

	public String Write(Guide guide) {
		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.init();
		VelocityContext context = new VelocityContext();
		
		context.put("guide", guide);
		
		StringWriter writer = new StringWriter();
		Velocity.mergeTemplate("guide-html5.vm", "UTF-8", context, writer);
		
		return writer.toString();
	}
}
