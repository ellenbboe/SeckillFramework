package sf.validator;

import com.alibaba.druid.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    @Override
    public void initialize(IsMobile constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if(StringUtils.isEmpty(value))
            {
                return false;
            }
            return Pattern.matches(REGEX_MOBILE,value);
        } catch (Exception e) {
            return false;
        }
    }
}
