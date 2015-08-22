package com.pm.ws;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import com.pm.ws.model.CompileResponse;
import com.pm.ws.model.Upload;
import com.pm.ws.model.UploadResponse;

@MTOM
@WebService(endpointInterface = "com.pm.ws.JCServer")
public class JCServerImpl implements JCServer {

	@Override
	public CompileResponse compileFile(String fileId) {
		CompileResponse result = new CompileResponse();
		if ("1".equals(fileId)) {
			result.setExitCode(0);
			result.setOutput("All right");
		} else {
			result.setExitCode(1);
			result.setOutput("Wrong file Id");
		}
		System.out.println("JCServerImpl.compileFile()");
		return result;
	}

	@Override
	public UploadResponse uploadFile(Upload file) {
		UploadResponse result = new UploadResponse();
		result.setSuccess(true);
		result.setFileId("1");
		result.setMessage("Hello");
		System.out.println("JCServerImpl.uploadFile()");
		return result;
	}

}
