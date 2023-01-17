package dev.project.recommendation.common.handlebars;

import org.springframework.stereotype.Component;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@Component
@HandlebarsHelper
public class HandlebarsStringHelper {

    public boolean eqStr(String str1, String str2) {
        return str1.equals(str2);
    }
}
