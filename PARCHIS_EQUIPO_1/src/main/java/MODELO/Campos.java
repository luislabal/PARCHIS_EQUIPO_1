package MODELO;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class Campos {

    private Campos() { }

    public static String empaquetar(String... campos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            if (i > 0) sb.append(';');
            sb.append(URLEncoder.encode(campos[i] == null ? "" : campos[i], StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    
}
