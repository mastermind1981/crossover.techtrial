package org.pm.crossover.task.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		FilterRegistration.Dynamic filter = servletContext.addFilter(
				"openSessionInViewFilter", OpenSessionInViewFilter.class);
		filter.setInitParameter("singleSession", "true");
		filter.addMappingForServletNames(null, true, "dispatcher");
	}

}
