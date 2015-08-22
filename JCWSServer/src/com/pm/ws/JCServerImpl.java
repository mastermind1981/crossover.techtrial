package com.pm.ws;

import java.io.File;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.log4j.Logger;

import com.pm.ws.compilation.CompilationUtil;
import com.pm.ws.model.CompileResponse;
import com.pm.ws.model.JCException;
import com.pm.ws.model.Upload;
import com.pm.ws.model.UploadResponse;
import com.pm.ws.storage.StorageUtil;

/**
 * Implementation of {@link JCServer} 
 *
 */
@MTOM
@WebService(endpointInterface = "com.pm.ws.JCServer")
public class JCServerImpl implements JCServer {
	final static Logger logger = Logger.getLogger(JCServerImpl.class);

	@Override
	public CompileResponse compileFile(String fileId) {
		logger.info("JCServerImpl.compileFile()");
		CompileResponse result = new CompileResponse();
		File file = StorageUtil.getFile(fileId);
		try {
			if (file == null){
				throw new JCException("Wrong file id: " + fileId);
			}
			if (!file.exists()){
				throw new JCException("File with this id does not exist: " + fileId);
			}
			File outFolder = StorageUtil.unzip(file);
			if (outFolder == null || !outFolder.exists()){
				throw new JCException("Problem after extracting the archive: " + fileId);
			}
			result = CompilationUtil.compileProject(outFolder, result);
		} catch (Exception e) {
			logger.error("Error on compilation state", e);
			result.setExitCode(1);
			result.setOutput(e.getMessage());
		}
		finally {
			StorageUtil.deleteFile(file);
			logger.info("Done JCServerImpl.compileFile()");
		}
		return result;
	}

	@Override
	public UploadResponse uploadFile(Upload upload) {
		logger.info("JCServerImpl.uploadFile()");
		UploadResponse result = new UploadResponse();
		byte[] bytes = upload.getFile();
		File file = null;
		try {
			file = StorageUtil.saveFile(bytes);
			if (!StorageUtil.isZipFile(file)) {
				throw new JCException("The given file is not a ZIP archive");
			}
			result.setFileId(StorageUtil.getFileId(file));
			result.setSuccess(true);
			result.setMessage("File has been successfully uploaded.");
		} catch (Exception e) {
			logger.error("Error on upload state", e);
			result.setMessage(e.getLocalizedMessage());
			result.setSuccess(false);
			result.setFileId(null);
			if (file != null && file.exists()) {
				StorageUtil.deleteFile(file);
			}
		}
		logger.info("Done JCServerImpl.uploadFile()");
		return result;
	}

}
