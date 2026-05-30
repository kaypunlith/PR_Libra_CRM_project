package com.ut.nlSystemAPi.helper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.vfs.provider.url.UrlFileName;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class GenerateQRCode
{

    private static final String ROOT;
    private static final String PROJECT_NAME;
    private static final String FOLDER_UPLOAD;

    static {
        ROOT = System.getProperty("catalina.base");
        PROJECT_NAME = "logs/update-share/nlHrAppAPi";
        FOLDER_UPLOAD = "upload";
    }

    public static String generateQRCode(String data) throws WriterException, IOException
    {
        File path = new File(ROOT + File.separator + PROJECT_NAME + File.separator + FOLDER_UPLOAD);
        if (!path.exists()) path.mkdirs();

        String filename = "";
        String ext = filename.substring(0);
        String aliasFilename = UUID.randomUUID().toString();
        String filePath = path + File.separator + aliasFilename + FilenameUtils.EXTENSION_SEPARATOR + ext + "png";

        String charset = "UTF-8";

        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, 200, 200);

        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));

        return UrlFileName.SEPARATOR_CHAR + FOLDER_UPLOAD+ UrlFileName.SEPARATOR_CHAR + aliasFilename + FilenameUtils.EXTENSION_SEPARATOR + ext + "png";

    }

}