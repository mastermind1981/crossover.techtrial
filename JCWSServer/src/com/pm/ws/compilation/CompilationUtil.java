package com.pm.ws.compilation;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;

import com.pm.ws.model.CompileResponse;
import com.pm.ws.model.JCException;

/**
 * Util class containing methods related to compilation process
 *
 */
public class CompilationUtil {
	final static Logger logger = Logger.getLogger(CompilationUtil.class);

	/**
	 * Runs maven build with <i>test</i> goal and returns build output  
	 * @param projectRootFolder existing {@link File} instance representing the folder with <code>pom.xml</code> in the root 
	 * @param response optional response instance to put the resulting information to
	 * @return {@link CompileResponse} instance containing exit code and build output
	 */
	public static CompileResponse compileProject(File projectRootFolder,
			CompileResponse response) {
		if (response == null) {
			response = new CompileResponse();
		}
		try {
			logger.info("CompilationUtil.compileProject()");
			File pom = new File(projectRootFolder, "pom.xml");
			if (pom == null || !pom.exists()) {
				throw new JCException("There is no pom.xml in the given folder");
			}

			InvocationRequest request = new DefaultInvocationRequest();
			request.setPomFile(pom);
			request.setGoals(Collections.singletonList("test"));
			final StringBuilder outputLog = new StringBuilder();
			Invoker invoker = new DefaultInvoker();
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			Properties properties = new Properties();
			properties.load(classLoader.getResourceAsStream("/mvn.properties"));
			String mvnHome = properties.getProperty("mvn.home");
			invoker.setMavenHome(new File(mvnHome));
			logger.info("Maven Home: " + mvnHome);
			invoker.setOutputHandler(new InvocationOutputHandler() {
				@Override
				public void consumeLine(String line) {
					outputLog.append(line).append("<br/>");
					logger.info(line);
				}
			});
			InvocationResult result = invoker.execute(request);

			response.setExitCode(result.getExitCode());
			if (result.getExitCode() != 0 && outputLog.length() == 0) {
				response.setOutput("Build failed.");
			}
			response.setOutput(outputLog.toString());
		} catch (Exception e) {
			logger.error("Error on compilation state", e);
			response.setExitCode(-1);
			response.setOutput(e.getMessage());
		}

		logger.info("Done CompilationUtil.compileProject()");
		return response;
	}
}
