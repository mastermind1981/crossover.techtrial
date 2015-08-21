package org.pm.crossover.task.controller;

import java.io.IOException;
import java.util.Iterator;

import org.pm.crossover.task.model.CompilationResult;
import org.pm.crossover.task.model.UploadResponse;
import org.pm.crossover.task.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * The main REST API for exam application
 *
 */
@RestController
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AppRestController {

	@RequestMapping(value = "/compile", method = RequestMethod.GET)
	public @ResponseBody CompilationResult compileCode(@RequestParam String fileId) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new CompilationResult(0, "OK oK ok Ok " + fileId);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResponse upload(MultipartHttpServletRequest request) {

		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");

		try {
			// just temporary save file info into ufile
			UploadedFile ufile;
			ufile = new UploadedFile(mpf.getBytes().length, mpf.getBytes(),
					mpf.getOriginalFilename(), mpf.getContentType());
			return new UploadResponse(true, "Successfully uploaded", "123");
		} catch (IOException e) {

			e.printStackTrace();
		}

		return new UploadResponse(false, "Unable to upload file", null);

	}
}
