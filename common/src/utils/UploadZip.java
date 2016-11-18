package utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.web.multipart.MultipartFile;

import entity.PictureProduct;

public class UploadZip {

	// TODO: Kirill серьезно, шеф, завязывай
	@SuppressWarnings("static-access")
	public static List<PictureProduct> getPicFromArchive(List<PictureProduct> newListPic, String dir, MultipartFile file)
	{
		byte[] buffer = new byte[1024];
		int len;
		try {
			byte[] bytes = file.getBytes();
			ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bytes));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				File f = new File(dir, ze.getName());
				FileOutputStream fos = new FileOutputStream(f);
				while ((len = zis.read(buffer)) > 0)
					fos.write(buffer, 0, len);
				fos.close();
				newListPic.add(new PictureProduct(dir+f.separator+ze.getName()));
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newListPic;
	}
}
