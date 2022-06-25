package ir.ac.ui.twitter;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringUtils {

    private static ApplicationContext applicationContext;

    public SpringUtils(ApplicationContext context) {
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }
}
