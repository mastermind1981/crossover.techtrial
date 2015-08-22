package com.pm.ws.storage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.pm.ws.model.JCException;


/**
 * Util class containing storage related methods. 
 */
public class StorageUtil {
	final static Logger logger = Logger.getLogger(StorageUtil.class);
	private static final String TEMP_FOLDER = System
			.getProperty("java.io.tmpdir");
	private static final String PREFIX = "jcUpload";
	private static final String SUFFIX = ".zip";

	private static final Map<String, File> storageMap = new HashMap<String, File>();

	/**
	 * 
	 * @param file file 
	 * @return file id from the storage map (the name without extension)
	 */
	public static String getFileId(File file) {
		return FilenameUtils.removeExtension(file.getName());
	}

	/**
	 * 
	 * @return storage folder (%TEMP%\jcwsstorage)
	 */
	public static File getFileStorage() {
		File storage = new File(TEMP_FOLDER, "jcwsstorage");
		if (!storage.exists()) {
			storage.mkdirs();
		}
		return storage;
	}

	/**
	 * Saves file in the storage and returns the {@link File} instance of it;
	 * @param bytes byte array, the content of the file;
	 * @return the file that was saved {@link File} 
	 * @throws IOException in case of any error occurred during writing data to the file
	 */
	public static File saveFile(byte[] bytes) throws IOException {
		File f = File.createTempFile(PREFIX, SUFFIX, getFileStorage());
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bytes);
		fos.close();
		storageMap.put(getFileId(f), f);
		logger.info("Saved " + f.getAbsolutePath());
		return f;
	}

	/**
	 * 
	 * @param fileId file id 
	 * @return {@link File} by file id value 
	 */
	public static File getFile(String fileId) {
		return storageMap.get(fileId);
	}

	/**
	 * 
	 * @param file file to delete
	 */
	public static void deleteFile(File file) {
		logger.info("Deleting " + file.getAbsolutePath());
		if (file != null) {
			File outFolder = new File(file.getParentFile(), getFileId(file));
			if (outFolder != null && outFolder.exists()) {
				outFolder.delete();
			}
			storageMap.remove(getFileId(file));
			if (file.exists()) {
				file.delete();
			}

		}
	}

	/**
	 * Checks if the file is a ZIP archive
	 * @param file
	 * @return <code>true</code> if the file is a <i>ZIP</i> archive, <code>false</code> otherwise
	 * @throws IOException in case of any error occurred during writing data to the file
	 */
	public static boolean isZipFile(File file) throws IOException {
		if (file.isDirectory()) {
			return false;
		}
		if (!file.canRead()) {
			throw new IOException("Cannot read file " + file.getAbsolutePath());
		}
		if (file.length() < 4) {
			return false;
		}
		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream(file)));
		int test = in.readInt();
		in.close();
		return test == 0x504b0304;
	}

	/**
	 * Extracts files from te ZIP archive the the folder with the same name (without extension)
	 * @param zipFile the file to unzip
	 * @return the {@link File} representation of the target folder with extracted data 
	 * @throws Exception in case of any error occurred 
	 */
	public static File unzip(File zipFile) throws Exception {
		logger.info("Extracting " + zipFile.getName());
		if (!isZipFile(zipFile)) {
			throw new JCException(
					"The file with this id is not a ZIP archive: "
							+ getFileId(zipFile));
		}
		byte[] buffer = new byte[1024];
		File outFolder = new File(zipFile.getParentFile(), getFileId(zipFile));
		if (!outFolder.exists()) {
			outFolder.mkdirs();
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry ze = zis.getNextEntry();
		while (ze != null) {

			String fileName = ze.getName();
			File newFile = new File(outFolder, fileName);

			logger.info("File unzip : " + newFile.getAbsoluteFile());

			if (ze.isDirectory()) {
				newFile.mkdirs();
			} else {
				new File(newFile.getParent()).mkdirs();
				newFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
			}
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		logger.info("Done");
		return outFolder;
	}

	/**
	 * removes storage folder from the file system
	 * @return <code>true</code> if the storage was successfully removed, <code>false</code> otherwise
	 */
	public static boolean clearStorage() {
		return getFileStorage().delete();
	}
}
