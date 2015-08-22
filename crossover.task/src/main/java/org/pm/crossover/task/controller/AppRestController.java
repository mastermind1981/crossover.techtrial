package org.pm.crossover.task.controller;

import java.io.IOException;
import java.util.Iterator;

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
import com.pm.ws.JCServiceImpl;
import com.pm.ws.Upload;
import com.pm.ws.UploadResponse;

/**
 * The main REST API for exam application
 *
 */
@RestController
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AppRestController {

	@Autowired
	JCServiceImpl jcClient;

	@RequestMapping(value = "/compile", method = RequestMethod.GET)
	public @ResponseBody CompileResponse compileCode(@RequestParam String fileId) {
		return jcClient.compile(fileId);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResponse upload(
			MultipartHttpServletRequest request) {

		UploadResponse resp;
		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");

		try {
			Upload upl = new Upload();
			upl.setFile(mpf.getBytes());
			resp = jcClient.upload(upl);
		} catch (IOException e) {
			resp = new UploadResponse();
			resp.setFileId(null);
			resp.setMessage(e.getLocalizedMessage());
			resp.setSuccess(false);
			e.printStackTrace();
		}

		return resp;

	}
}
