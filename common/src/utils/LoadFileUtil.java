package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import entity.PictureProduct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class LoadFileUtil {

	public enum FileType {
		IMAGE, ARCHIVE
	}

	private static List<String> acceptedImageExts;
	private static List<String> acceptedArchiveExts;
	private static List<String> acceptedExts;

	static {
		// TODO: Kirill вот тебе еще варианты конструкций
		/*List<String> imageExts = new ArrayList<>();
		imageExts.add("jpg");
		imageExts.add("png");
		acceptedImageExts = Collections.unmodifiableList(imageExts);*/
		acceptedImageExts = Collections.unmodifiableList(Arrays.asList("jpg", "png"));

		List<String> archiveExts = new ArrayList<>();
		archiveExts.add("zip");
		acceptedArchiveExts = Collections.unmodifiableList(archiveExts);

		List<String> exts = new ArrayList<>();
		exts.addAll(acceptedImageExts);
		exts.addAll(acceptedArchiveExts);
		acceptedExts = Collections.unmodifiableList(exts);

		// TODO: Kirill а вообще погляди как используются эти списки, е для этих цедей есть более подходящая конструкция
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

	private static boolean checkExtension(String fileName, List<String> extsList) {
		return extsList.contains(fileName);
	}

	public static void addPictureInList(MultipartFile file, List<PictureProduct> list, String dir)  throws IOException {
		list.add(new PictureProduct(LoadFileUtil.storeToFileWithOriginalName(file, dir)));
	}

	public static boolean isCorrectFormat(MultipartFile file) {
		return checkExtension(FilenameUtils.getExtension(file.getOriginalFilename()),acceptedExts);
	}
}