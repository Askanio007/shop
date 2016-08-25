package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import models.Password;

public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Password.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "valid.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confPassword", "valid.password");
		Password pw = (Password) target;
		String newPs = pw.getNewPassword();
		if (!newPs.matches("^((?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).*){6,12}$"))	
			errors.rejectValue("newPassword", "valid.passwordNotEnough");
		if (!pw.getNewPassword().equals(pw.getConfPassword())) {
			errors.rejectValue("newPassword", "valid.passwordConfDiff");
		}
	}
}
