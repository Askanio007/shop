package utils;

public class PaginationFilter {

	private final int limit;

	private final int offset;

	public PaginationFilter(int currentPage, int limit) {
		if (currentPage == 0)
			currentPage++;
		this.offset = ((currentPage - 1) * limit);
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

}
