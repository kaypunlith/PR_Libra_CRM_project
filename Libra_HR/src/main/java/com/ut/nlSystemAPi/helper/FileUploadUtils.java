package com.ut.nlSystemAPi.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs.provider.url.UrlFileName;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

  private static final String ROOT;
  private static final String PROJECT_NAME;
  private static final String FOLDER_UPLOAD;

  static {
    ROOT = System.getProperty("catalina.base");
    PROJECT_NAME = "logs/update-share/nlSystemHrAPi";
    FOLDER_UPLOAD = "upload";
  }

  private FileUploadUtils() {

  }

  public static String saveFileUploaded(MultipartFile fileUploaded) {
    try {
      byte[] bytes = IOUtils.toByteArray(fileUploaded.getInputStream());

      File path = new File(ROOT + File.separator + PROJECT_NAME + File.separator + FOLDER_UPLOAD);
      if (!path.exists())
        path.mkdirs();

      String filename = fileUploaded.getOriginalFilename();
      String ext = filename.substring(filename.lastIndexOf(".") + 1);
      String aliasFilename = UUID.randomUUID().toString();
      String filePath = path + File.separator + aliasFilename + FilenameUtils.EXTENSION_SEPARATOR + ext;

      File file = new File(filePath);
      file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(bytes);
      fos.close();

      return makeFileUploadedUrl(aliasFilename + FilenameUtils.EXTENSION_SEPARATOR + ext);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }

  public static String makeFileUploadedUrl(String filename) {
    String filePath = UrlFileName.SEPARATOR_CHAR + FOLDER_UPLOAD + UrlFileName.SEPARATOR_CHAR + filename;
    return filePath;
  }

  public static void deleteFile(String fileName) {
    if (fileName != null && !fileName.isEmpty()) {
      File file = new File(ROOT + File.separator + PROJECT_NAME + File.separator + fileName);
      file.delete();
    }
  }

}