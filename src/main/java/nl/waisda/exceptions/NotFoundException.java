package nl.waisda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 8642646634336884056L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotFoundException(String arg0) {
		super(arg0);
	}

	public NotFoundException(Throwable arg0) {
		super(arg0);
	}

}
