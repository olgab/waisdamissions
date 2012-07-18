package nl.waisda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

	public static final Locale DUTCH_LOCALE = new Locale("NL", "nl");
	public static final Locale ENGLISH_LOCALE = Locale.ENGLISH;

	private Util() {

	}

	public static <T> List<T> mapGet(List<Value<T>> values) {
		List<T> ts = new ArrayList<T>(values.size());
		for (Value<T> v : values) {
			ts.add(v.get());
		}
		return ts;
	}

}
