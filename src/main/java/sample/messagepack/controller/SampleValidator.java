package sample.messagepack.controller;

import sample.messagepack.bean.message.MyMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SampleValidator implements Validator {

	final Logger logger = LoggerFactory.getLogger(SampleValidator.class);

	public boolean supports(Class<?> clazz) {
		if(MyMessage.class.equals(clazz)) {
			return true;
		}else {
			// Though It is not target, execute the controller.
			return false;
		}
	}

	public void validate(Object object, Errors errors) {

	}
	
}