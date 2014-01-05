package repository.architecture;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * ApplicationContextAware, enabled to fetch beans
 *
 * @author stibe
 * @since 1.0.0
 */
public class DefaultContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	DefaultContextProvider.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) DefaultContextProvider.applicationContext.getBean(beanName);
    }

    public static Map<String, Object> getAnnotatedBean(Class<? extends Annotation> annotationClass) {
        return getApplicationContext().getBeansWithAnnotation(annotationClass);
    }

}
