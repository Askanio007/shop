package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import entity.PictureProduct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class LoadFileUtil {

	public enum FileType {
		IMAGE, ARCHIVE
	}

	private static Set<String> acceptedImageExts;
	private static Set<String> acceptedArchiveExts;
	private static Set<String> acceptedExts;

	static {
		// TODO: Kirill вот тебе еще варианты конструкций

		acceptedImageExts = new HashSet<>(Arrays.asList("png", "jpg"));
		acceptedArchiveExts = new HashSet<>(Arrays.asList("zip"));
		acceptedExts = new HashSet<>();
		acceptedExts.addAll(acceptedImageExts);
		acceptedExts.addAll(acceptedArchiveExts);

		// TODO: Kirill а вообще погляди как используются эти списки, е для этих цедей есть более подходящая конструкция ::: исправил на сеты
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

	public static String storeToFileWithOriginalName(MultipartFile file, String directoryToSave) throws IllegalStateException, IOException {
		Path path = Paths.get(directoryToSave, file.getOriginalFilename());
		File f = path.toFile();
		file.transferTo(f);
		// TODO: Kirill может вместо того чтобы суспендить ворнинг, лучше его исправить?  ::: Исправляю везде по коду
		return directoryToSave + "\\" + file.getOriginalFilename();
	}

	private static boolean checkExtension(String fileName, Set<String> extsList) {
		return extsList.contains(fileName);
	}

	public static void addPictureInList(MultipartFile file, List<String> list, String dir)  throws IOException {
		list.add(storeToFileWithOriginalName(file, dir));
	}

	public static boolean isCorrectFormat(MultipartFile file) {
		return checkExtension(FilenameUtils.getExtension(file.getOriginalFilename()),acceptedExts);
	}
}