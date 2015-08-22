package org.pm.crossover.task.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pm.ws.JCServerImplService;
import com.pm.ws.JCServiceImpl;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
@EnableAutoConfiguration
@ComponentScan({ "org.pm.crossover.task.*" })
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	@Bean
	public JCServiceImpl jcClient() {
		URL url = null;
		WebServiceException e = null;
		try {
			url = new URL(serviceHost() + "techtrialServer/jc?wsdl");
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		}
		JCServerImplService.setWSDLLocAndException(url, e);
		JCServerImplService jcServer = new JCServerImplService();
		return jcServer.getJCServerImplPort();
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setPathMatcher(new CaseInsensitivePathMatcher());
	}

	/**
	 * 
	 * Support case insensitive url matching
	 */
	public static class CaseInsensitivePathMatcher extends AntPathMatcher {

		@Override
		protected boolean doMatch(String pattern, String path,
				boolean fullMatch, Map<String, String> uriTemplateVariables) {
			return super.doMatch(pattern.toLowerCase(), path.toLowerCase(),
					fullMatch, uriTemplateVariables);
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/home.html");
	}

	public String serviceHost() {
		String host = env.getProperty("jcservice.host");
		if (!host.endsWith("/")) {
			host += "/";
		}
		return host;
	}

}