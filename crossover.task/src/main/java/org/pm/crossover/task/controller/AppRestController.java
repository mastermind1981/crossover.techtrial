package org.pm.crossover.task.controller;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.pm.crossover.task.config.ApplicationConfiguration.JCServiceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pm.ws.CompileResponse;
import com.pm.ws.Upload;
import com.pm.ws.UploadResponse;

/**
 * The main REST API for application
 *
 */
@RestController
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AppRestController {

	final static Logger logger = Logger.getLogger(AppRestController.class);

	@Autowired
	JCServiceAccessor jcClient;

	@RequestMapping(value = "/compile", method = RequestMethod.GET)
	public @ResponseBody CompileResponse compileCode(@RequestParam String fileId) {
		return jcClient.getService().compile(fileId);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResponse upload(
			MultipartHttpServletRequest request) {

		UploadResponse resp;
		try {
			Iterator<String> itr = request.getFileNames();

			if (itr == null || !itr.hasNext()) {
				throw new Exception("Please select ZIP archive");
			}
			MultipartFile mpf = request.getFile(itr.next());
			logger.info(mpf.getOriginalFilename() + " uploaded!");

			Upload upl = new Upload();
			upl.setFile(mpf.getBytes());
			resp = jcClient.getService().upload(upl);
		} catch (Exception e) {
			logger.error("Error on upload request proecssing ", e);
			resp = new UploadResponse();
			resp.setFileId(null);
			resp.setMessage(e.getLocalizedMessage());
			resp.setSuccess(false);
		}

		return resp;

	}
}
