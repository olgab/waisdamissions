package nl.waisda.forms;

/** A form that knows how to apply its fields to some (domain) object. */
public interface ApplyForm<T> {

	/** Applies this form to the given object. Assumes it has been validated. */
	public void applyTo(T t);

}
