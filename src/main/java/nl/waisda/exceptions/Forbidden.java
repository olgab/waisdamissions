package nl.waisda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class Forbidden extends RuntimeException {

	private static final long serialVersionUID = -2800632345767986626L;

	public Forbidden() {
		super();
	}

	public Forbidden(String msg, Throwable t) {
		super(msg, t);
	}

	public Forbidden(String msg) {
		super(msg);
	}

	public Forbidden(Throwable t) {
		super(t);
	}

}
