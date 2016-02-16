package ua.nure.lisyak.SummaryTask4.util.file;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.exception.FileProcessingException;

import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ParseException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Provides realization of {@link FileService} interface.
 */
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
    private final String folderPath;

    /**
     * @param folderPath root folder for looking files up
     */
    public FileServiceImpl(String folderPath) {
        this.folderPath = folderPath + File.separator;
        File fileSaveDir = new File(folderPath);
        if (!fileSaveDir.exists() && !fileSaveDir.mkdirs()) {
            LOGGER.warn("Folder wasn't create");
            throw new FileProcessingException("Unexpected result when creating folder");
        }
    }

    @Override
    public String saveFile(Integer name, String subDirectory, String file) {
        return saveFile("" + name, subDirectory, file);
    }

    @Override
    public String saveFile(String name, String subDirectory, String file) {
        String extension = getExtension(file);
        String imageToSave = file.split(",")[1];
        String fileName = generateFileName(name, extension);

        byte[] imageByteArray = decodeImage(imageToSave);

        String path = folderPath + subDirectory + fileName;
        writeFileOnDisk(imageByteArray, path);
        LOGGER.debug("File saved to {} ", path);
        return fileName;
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    @Override
    public File getFile(String path) {
        return new File(folderPath + path);
    }

    @Override
    public void removeFile(String name, String subDirectory) {
        if (name == null || name.isEmpty()) {
            return;
        }
        String path = folderPath + subDirectory + name;
        removeFile(path);
    }

    @Override
    public void removeFiles(List<String> names, String subDirectory) {
        for (String name : names) {
            removeFile(name, subDirectory);
        }
    }

    @Override
    public String getExtension(String file) {
        try {
            return file.split("/|;")[1];
        } catch (IndexOutOfBoundsException e) {
            throw new FileProcessingException("Cannot get file extension", e);
        }
    }

    private String getFileName(Part filePart) {
        String header = filePart.getHeader("content-disposition");
        if (header == null) {
            return null;
        }
        try {
            ContentDisposition cd = new ContentDisposition(header);
            String f = cd.getParameter("filename");
            return f == null ? null : f;
        } catch (ParseException e) {
            LOGGER.debug("unable to parse cd");
        }

        return null;
    }

    private void writeFileOnDisk(byte[] file, String filePath) {
        try {
            File f = new File(filePath);
            if(f.exists()){
                f.delete();
            }
                if(f.createNewFile()){
                    FileOutputStream imageOutFile = new FileOutputStream(f);
                    imageOutFile.write(file);
                }
        } catch (IOException e) {
            LOGGER.error("File cannot be written on disk", e);
            throw new FileProcessingException("File cannot be save", e);
        }
    }

    private String generateFileName(String name, String extension) {
        return name + "." + extension;
    }

    private void removeFile(String path) {
        File file = new File(path);
        if (file.delete()) {
            LOGGER.debug("File deleted from {} ", path);
        } else {
            LOGGER.error("File deletion failed. Path: {}", path);
        }
    }

}
