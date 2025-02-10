package top.gaogle.framework.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 动态选择提示消息
 *
 * @author goge
 * @since 1.0.0
 */
@Configuration
public class I18nMessageConfig {
    public static ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver() {
            @Override
            @NonNull
            public Locale resolveLocale(@NonNull HttpServletRequest request) {
                String languageHeader = request.getHeader("Accept-Language");
                if (languageHeader == null || languageHeader.isEmpty()) {
                    return Locale.getDefault();
                }
                String[] languageHeaderParts = languageHeader.split(",");
                return Locale.forLanguageTag(languageHeaderParts[0]);
            }
        };
    }

    public static String getMessage(String code, Object... args) {
        return getMessage(messageSource(), code, args);
    }

    public static String getMessage(MessageSource messageSource, String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
