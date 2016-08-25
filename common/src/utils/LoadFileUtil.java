package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class LoadFileUtil {

	public enum FileType {
		IMAGE, ARCHIVE
	}

	private static List<String> acceptedImageExts;
	private static List<String> acceptedArchiveExts;

	static {
		List<String> imageExts = new ArrayList<>();
		imageExts.add("jpg");
		imageExts.add("png");
		acceptedImageExts = Collections.unmodifiableList(imageExts);
		List<String> archiveExts = new ArrayList<>();
		archiveExts.add("zip");
		acceptedArchiveExts = Collections.unmodifiableList(archiveExts);
	}

	public static boolean checkExtension(String fileName, FileType fileType) {
		switch (fileType) {
		case ARCHIVE:
			return checkExtension(fileName, acceptedArchiveExts);
		case IMAGE:
			return checkExtension(fileName, acceptedImageExts);
		default:
			return false;
		}
	}

	public static boolean checkExtension(MultipartFile file, FileType fileType) {
		return checkExtension(FilenameUtils.getExtension(file.getOriginalFilename()), fileType);
	}

	@SuppressWarnings("static-access")
	public static String storeToFileWithOriginalName(MultipartFile file, String directoryToSave) throws IllegalStateException, IOException {
		Path path = Paths.get(directoryToSave, file.getOriginalFilename());
		File f = path.toFile();
		file.transferTo(f);
		return directoryToSave +f.separator+ file.getOriginalFilename();
	}

	private static boolean checkExtension(String fileName, List<String> extsList) {
		return extsList.contains(fileName);
	}
}