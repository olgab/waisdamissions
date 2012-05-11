package nl.waisda.model;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	private static int NUMBER_OF_PAGES = 4;

	private int totalResults;
	private int totalPageCount;
	private int resultsPerPage;
	private int current;

	private int first;
	private int from;
	private int to;
	private int last;

	private int itemFirst;
	private int itemLast;

	public Pagination(int totalResults, int currentPage, int resultsPerPage) {
		this.totalResults = totalResults;
		this.resultsPerPage = resultsPerPage;
		this.current = currentPage;

		calculate();
	}

	private void calculate() {
		first = 1;
		from = Math.max(first, current - NUMBER_OF_PAGES);
		last = (totalResults / resultsPerPage)
				+ ((totalResults % resultsPerPage == 0) ? 0 : 1);
		to = Math.min(last, current + NUMBER_OF_PAGES);

		if (current > last - NUMBER_OF_PAGES)
			from = Math.max(first, last - (2 * NUMBER_OF_PAGES));

		if (current < first + NUMBER_OF_PAGES)
			to = Math.min(last, first + (2 * NUMBER_OF_PAGES));

		itemFirst = current * resultsPerPage - resultsPerPage + 1;
		itemLast = Math.min(itemFirst + resultsPerPage - 1, totalResults);

		totalPageCount = totalResults / resultsPerPage;
		if (totalResults % resultsPerPage > 0) {
			totalPageCount++;
		}
	}

	public List<Integer> getItems() {
		List<Integer> pagination = new ArrayList<Integer>();
		for (int page = from; page <= to; page++)
			pagination.add(page);
		return pagination;
	}
	
	/*
	 * Getters and setters
	 */

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getItemFirst() {
		return itemFirst;
	}

	public void setItemFirst(int itemFirst) {
		this.itemFirst = itemFirst;
	}

	public int getItemLast() {
		return itemLast;
	}

	public void setItemLast(int itemLast) {
		this.itemLast = itemLast;
	}

}