package servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import entity.PictureProduct;
import service.BuyerService;
import service.ProductService;
import service.SettingsService;

@SuppressWarnings("serial")
@WebServlet("/img")
public class ImageServlet extends HttpServlet {

	@Autowired
	private ProductService serviceProduct;

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private SettingsService setting;

	private WebApplicationContext springContext;

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		if (request.getParameter("pic") != null) {
			String pathPic = serviceProduct.getPathPicture(getParam(request, "pic"));
			write(pathPic, response);
			return;
		}

		if (request.getParameter("tempPic") != null) {
			// TODO: Kirill если не избежать, то надо делать это на минимальном отрезке ::: прошёлся по коду, исправил их как смог
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) request.getSession().getAttribute("pics");
			String path = list.get(Integer.parseInt(request.getParameter("tempPic")));

			/* еще вариант, но это хуже гораздо, хоть и работает без супресов
			List list = (List) request.getSession().getAttribute("pics");
			String path = ((PictureProduct)list.get(Integer.parseInt(request.getParameter("tempPic")))).getPath();
			*/

			write(path, response);
			return;
		}

		if (request.getParameter("avaPic") != null) {
			String path = serviceBuyer.getPathAva(getParam(request, "avaPic"));
			write(path, response);
			return;
		}

		if (request.getParameter("tempAvaPic") != null) {
			String path = request.getParameter("tempAvaPic");
			write(setting.getPathUploadAva() + "\\" + path, response);
			return;
		}

	}

	public void write(String path, HttpServletResponse response) throws IOException {
		byte[] buffer = new byte[1024];
		int ch;
		// TODO: Kirill exception и стрим не закроется. ::: добавил блок finally
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(path));
		BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
		try {
			while ((ch = bin.read(buffer)) > 0)
				bout.write(buffer, 0, ch);
		} finally {
			bin.close();
			bout.close();
		}
	}

	public Long getParam(HttpServletRequest request, String nameParam) {
		return Long.parseLong(request.getParameter(nameParam));
	}
}
