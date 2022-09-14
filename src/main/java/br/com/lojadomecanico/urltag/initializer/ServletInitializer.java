package br.com.lojadomecanico.urltag.initializer;

import br.com.lojadomecanico.urltag.UrlTagApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UrlTagApplication.class);
    }
}
