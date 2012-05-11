package nl.waisda.validators;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UberValidator implements Validator, InitializingBean {

    @Autowired
    private List<Validator> validators;

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public void validate(Object target, Errors errors) {
        if (validators != null) {
        	for (Validator validator : validators) {
        		if (validator != this && validator.supports(target.getClass())) {
        			validator.validate(target, errors);
        		}
        	}
        }
    }

	@Override
	public void afterPropertiesSet() throws Exception {
	}
}