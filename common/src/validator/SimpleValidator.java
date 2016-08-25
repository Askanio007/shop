package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import models.Password;

@Component("simplePassValid")
public class SimpleValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Password.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "valid.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confPassword", "valid.passwordConf");
	}

}
