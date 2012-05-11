package nl.waisda.forms;

/** Fills a form with values from a domain object. */
public interface FillForm<T> {

	public void fillFrom(T t);

}
