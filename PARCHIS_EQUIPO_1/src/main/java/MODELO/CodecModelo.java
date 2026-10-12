package MODELO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CodecModelo {

    private CodecModelo() { }

    public static String codificar(Jugador j) {
        if (j == null) return "";
        return Campos.empaquetar(String.valueOf(j.getId()), j.getNombre(), j.getAvatar(), j.getColor(),
                String.valueOf(j.isEsAnfitrion()), String.valueOf(j.isPreparado()));
    }

    
}
