package br.com.associados.utils;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.Objects;

public final class FormatadorUtil {

    public static final String MASCARA_CPF = "AAA.AAA.AAA-AA";
    public static final String MASCARA_CNPJ = "AA.AAA.AAA/AAAA-AA";

    private FormatadorUtil(){

    }

    public static String formatarDocumento(String documento) {
        if (Objects.isNull(documento)) {
            return "";
        } else {
            return documento.length() == 11 ? formatar(documento, "AAA.AAA.AAA-AA") : formatar(documento, "AA.AAA.AAA/AAAA-AA");
        }
    }

    private static String formatar(String valor, String mask) {
        try {
            if (Objects.nonNull(valor) && valor != "") {
                MaskFormatter mf = new MaskFormatter(mask);
                mf.setValueContainsLiteralCharacters(false);
                return mf.valueToString(valor);
            } else {
                return "";
            }
        } catch (ParseException var4) {
            return valor;
        }
    }

}