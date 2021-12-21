package br.com.william.anotations;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.List;

@Documented
@Constraint( validatedBy = { UniqueValueValidator.class } )
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

    String message() default "Existing field!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName();
    Class<?> domainClass();
}

@ApplicationScoped
class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object>{
    private String domainAttribute;
    private  Class<?> klass;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue value) {
        this.domainAttribute = value.fieldName();
        this.klass = value.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query =
                entityManager.createQuery("select 1 from " +
                        klass.getName() + " where " +  domainAttribute + " =:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        return list.isEmpty();
    }
}
