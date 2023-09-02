package br.com.associados.utils;

public final class RegexUtil {

    public static final String REGEX_DOCUMENTO = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";
    public static final String REGEX_ASSOCIADO_ID = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
    public static final String REGEX_LIMPA_DOCUMENTO = "[^0-9]";

    private RegexUtil(){

    }
}
