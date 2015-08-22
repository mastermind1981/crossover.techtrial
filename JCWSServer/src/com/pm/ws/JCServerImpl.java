package com.pm.ws;

import java.io.File;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import com.pm.ws.model.CompileResponse;
import com.pm.ws.model.JCException;
import com.pm.ws.model.Upload;
import com.pm.ws.model.UploadResponse;
import com.pm.ws.storage.StorageUtil;

@MTOM
@WebService(endpointInterface = "com.pm.ws.JCServer")
public class JCServerImpl implements JCServer {

	@Override
	public CompileResponse compileFile(String fileId) {
		System.out.println("JCServerImpl.compileFile()");
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
			
			result.setExitCode(0);
			result.setOutput(outFolder.getAbsolutePath());
			
		} catch (Exception e) {
			result.setExitCode(1);
			result.setOutput(e.getMessage());
		}
		finally {
			StorageUtil.deleteFile(file);
		}
		return result;
	}

	@Override
	public UploadResponse uploadFile(Upload upload) {
		System.out.println("JCServerImpl.uploadFile()");
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
			result.setMessage(e.getLocalizedMessage());
			result.setSuccess(false);
			result.setFileId(null);
			if (file != null && file.exists()) {
				StorageUtil.deleteFile(file);
			}
		}
		return result;
	}

}
