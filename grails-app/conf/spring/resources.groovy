// Place your Spring DSL code here
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

beans = {
    localeResolver(SessionLocaleResolver) {
        defaultLocale = new Locale("pt", "BR")
    }
}
