package com.pm.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.pm.ws.model.CompileResponse;
import com.pm.ws.model.Upload;
import com.pm.ws.model.UploadResponse;

@WebService(name = "JCServiceImpl", targetNamespace = "http://ws.pm.com/")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface JCServer {
	@WebMethod(action = "http://ws.pm.com/JCServiceImpl/compile", operationName = "compile")
	CompileResponse compileFile(
			@WebParam(name = "fileId", targetNamespace = "http://ws.pm.com/") String fileId);

	@WebMethod(action = "http://ws.pm.com/JCServiceImpl/upload", operationName = "upload")
	UploadResponse uploadFile(
			@WebParam(name = "file", targetNamespace = "http://ws.pm.com/") Upload file);
}
