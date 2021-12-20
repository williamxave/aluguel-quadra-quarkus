package br.com.william.anootations;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Documented
@Constraint( validatedBy = { PasswordValidator.class } )
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Sua senha deve ter pelo menos 8 caracteres. " +
            " 1 Letra maiúsula, 1 Letra minúsula, númerps e pelo menos 1 caracter especial";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName();
    Class<?> domainClass();
}

@ApplicationScoped
class PasswordValidator implements ConstraintValidator<Password, Object>{
    private String domainAttribute;
    private Class<?> klass;

    @Override
    public void initialize(Password param) {
        this.domainAttribute = param.fieldName();
        this.klass = param.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String regexp =
                "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z";
        CharSequence input = value.toString();
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(input);

        if(matcher.matches()){
            return true;
        }
        return false;
    }
}