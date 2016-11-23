package view;

import javax.servlet.http.HttpServletRequest;

import utils.PaginationFilter;

public class ViewPagination {

	private static final int DEFAULT_PAGE_SIZE = 5;
	public static final String NAME_PAGE_PARAM = "page";
	private int currentPage;
	private int countPage;
	private final PaginationFilter dbFilter;

	public ViewPagination(String page, int countAllRecords, int countRecordOnPage) {
		this.currentPage = 1;
		if (page != null)
			this.currentPage = Integer.parseInt(page);
		double finalCountRecord = countAllRecords / (countRecordOnPage * 1.0);
		this.countPage = (int) Math.ceil(finalCountRecord);
		dbFilter = new PaginationFilter(this.currentPage, countRecordOnPage);
	}

	public ViewPagination(String page, int countAllRecords) {
		this(page, countAllRecords, DEFAULT_PAGE_SIZE);
	}

	// TODO: Kirill на мой взгляд странно передавать сюда запрос. Чтобы подчеркнуть постоянство этого параметра, ::: Так и сделал
	// что тебе он нужен, лучше сделать тут строковую константу с названием параметра который тебе нужен.

	public PaginationFilter getDBPagination() {
		return dbFilter;
	}

	public int getCountPage() {
		return countPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

}
