package repository.architecture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A custom annotation for class depending Strategies
 *
 * @author stibe
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringDriven
@Component
@Scope(value = "prototype")
public @interface Strategy {

    StrategyType type();

    Class<?> entityClass();

    boolean isDefaultStrategy() default false;

}
