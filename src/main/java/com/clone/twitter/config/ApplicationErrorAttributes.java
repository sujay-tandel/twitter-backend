package com.clone.twitter.config;

import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.web.context.request.WebRequest;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;

@Component
public class ApplicationErrorAttributes extends DefaultErrorAttributes {
	
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

	    errorAttributes.put("locale", webRequest.getLocale().toString());
	    errorAttributes.remove("error");
	    errorAttributes.put("cause", errorAttributes.get("message"));
	    errorAttributes.remove("message");
	    errorAttributes.put("status", String.valueOf(errorAttributes.get("status")));

	    return errorAttributes;
	  }
}
