
package com.example.studentregister.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    public static final String CHARSET = "UTF-8";

    private final RequestLoggingInterceptor requestLoggingInterceptor;

    public MvcConfiguration(
            RequestLoggingInterceptor requestLoggingInterceptor) {
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("fa"));
        return resolver;
    }

    @Bean
    @Primary
    public MessageSource messageSource() {
        return createMessageSource(
                "classpath:/messages/exception/BusinessExceptionMessages",
                "classpath:/messages/validation/ValidationMessages",
                "classpath:/messages/general/GeneralMessages",
                "classpath:/messages/label/LabelMessages"
        );
    }


    @Bean("businessExceptionMessageSource")
    public MessageSource businessExceptionMessageSource() {
        return createMessageSource(
                "classpath:/messages/exception/BusinessExceptionMessages"
        );
    }

    @Bean("labelMessageSource")
    public MessageSource labelMessageSource() {
        return createMessageSource(
                "classpath:/messages/label/LabelMessages"
        );
    }

    @Bean("generalMessageSource")
    public MessageSource generalMessageSource() {
        return createMessageSource(
                "classpath:/messages/general/GeneralMessages"
        );
    }

    private ReloadableResourceBundleMessageSource createMessageSource(
            String... basenames) {

        ReloadableResourceBundleMessageSource source =
                new ReloadableResourceBundleMessageSource();

        source.setBasenames(basenames);
        source.setDefaultEncoding(CHARSET);
        source.setFallbackToSystemLocale(false);

        return source;
    }

    @Bean
    public ObjectMapper customObjectMapper(
            MessageSource messageSource) {

        CustomObjectMapper mapper =
                new CustomObjectMapper(messageSource);

        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
        );

        mapper.configure(
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
                false
        );

        return mapper;
    }

    // تنظیمات CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    // ثبت Interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor);
    }

    // تنظیم اعتبارسنجی
    @Bean
    public LocalValidatorFactoryBean getValidator(
            MessageSource messageSource) {

        LocalValidatorFactoryBean validator =
                new LocalValidatorFactoryBean();

        validator.setValidationMessageSource(messageSource);

        return validator;
    }
}
