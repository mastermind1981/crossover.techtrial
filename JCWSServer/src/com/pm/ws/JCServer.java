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

/**
 * Interface for the web service server
 *
 */
@WebService(name = "JCServiceImpl", targetNamespace = "http://ws.pm.com/")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface JCServer {
	/**
	 * The action method that does compilation of the given project
	 * @param fileId id of the file previously uploaded
	 * @return the instance of {@link CompileResponse} containing exit code and compilation output
	 */
	@WebMethod(action = "http://ws.pm.com/JCServiceImpl/compile", operationName = "compile")
	CompileResponse compileFile(
			@WebParam(name = "fileId", targetNamespace = "http://ws.pm.com/") String fileId);

	/**
	 * 
	 * @param file {@link Upload} instance containing the bytearray of the file to upload
	 * @return the instance of {@link UploadResponse} containing the upload result, uploaded file id and the message
	 */
	@WebMethod(action = "http://ws.pm.com/JCServiceImpl/upload", operationName = "upload")
	UploadResponse uploadFile(
			@WebParam(name = "file", targetNamespace = "http://ws.pm.com/") Upload file);
}
