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

import com.pm.ws.model.JCException;

public class StorageUtil {

	private static final String TEMP_FOLDER = System
			.getProperty("java.io.tmpdir");
	private static final String PREFIX = "jcUpload";
	private static final String SUFFIX = ".zip";

	private static final Map<String, File> storageMap = new HashMap<String, File>();

	public static String getFileId(File file) {
		return FilenameUtils.removeExtension(file.getName());
	}

	public static File getFileStorage() {
		File storage = new File(TEMP_FOLDER, "jcwsstorage");
		if (!storage.exists()) {
			storage.mkdirs();
		}
		return storage;
	}

	public static File saveFile(byte[] bytes) throws IOException {
		File f = File.createTempFile(PREFIX, SUFFIX, getFileStorage());
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bytes);
		fos.close();
		storageMap.put(getFileId(f), f);
		return f;
	}

	public static File getFile(String fileId) {
		return storageMap.get(fileId);
	}

	public static void deleteFile(File file) {
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

	public static File unzip(File zipFile) throws Exception {
		System.out.println("Extracting " + zipFile.getName());
		if (!isZipFile(zipFile)) {
			throw new JCException(
					"The file with this id is not a ZIP archive: "
							+ getFileId(zipFile));
		}
		byte[] buffer = new byte[1024];
		File outFolder = new File(zipFile.getParentFile(), getFileId(zipFile));
		if (!outFolder.exists()) {
			outFolder.mkdir();
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry ze = zis.getNextEntry();
		while (ze != null) {

			String fileName = ze.getName();
			File newFile = new File(outFolder, fileName);

			System.out.println("file unzip : " + newFile.getAbsoluteFile());

			new File(newFile.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		System.out.println("Done");
		return outFolder;
	}

	public static boolean clearStorage() {
		return getFileStorage().delete();
	}
}
