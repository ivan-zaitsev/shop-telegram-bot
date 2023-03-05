package ua.ivan909020.admin.configs;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import no.api.freemarker.java8.Java8ObjectWrapper;

@Configuration
public class FreemarkerConfig implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FreeMarkerConfigurer) {
            FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) bean;
            Java8ObjectWrapper objectWrapper = new Java8ObjectWrapper(freemarker.template.Configuration.getVersion());
            configurer.getConfiguration().setObjectWrapper(objectWrapper);
        }
        return bean;
    }

}
