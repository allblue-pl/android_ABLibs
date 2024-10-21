package pl.allblue.ablibs.strings;

import java.util.EnumSet;
import java.util.regex.Pattern;

public class ABStrings {

    static final public int REGEX_TYPES_DIGITS  = 0b001;
    static final public int REGEX_TYPES_LETTERS = 0b010;
    static final public int REGEX_TYPES_SPECIAL = 0b100;

    static final public int LANGS_PL = 0b1;

    static public String escapeRegExpChars(String s) {
        return Pattern.compile("[{}()\\[\\].+*?^$\\\\|]").matcher(s)
                .replaceAll("\\$0");
    }

    static public String getCharsRegexp(EnumSet<RegExTypes> regExpTypes,
            String extra, EnumSet<Langs> langs) {
        String chars = "";

        if (regExpTypes.contains(RegExTypes.DIGITS))
            chars += "0-9";
        if (regExpTypes.contains(RegExTypes.LETTERS))
            chars += "a-zA-Z" + ABStrings.getLangSpecialCharacters(langs);
        if (regExpTypes.contains(RegExTypes.SPECIAL)) {
            chars += " `!@#%&_=/<>:;',\"" +
                    "\\\\" + "\\^" + "\\$" + "\\." + "\\[" + "\\]" + "\\|" +
                    "\\(" + "\\)" + "\\?" + "\\*" + "\\+" + "\\{" + "\\}" +
                    "\\-";
        }

        return chars + ABStrings.escapeRegExpChars(extra);
    }

    static public String getCharsRegexp(EnumSet<RegExTypes> regExpTypes,
            String extra) {
        return ABStrings.getCharsRegexp(regExpTypes, extra, EnumSet.allOf(
                    Langs.class));
    }

    static public String getCharsRegexp_Basic(String extra) {
        return ABStrings.getCharsRegexp(EnumSet.allOf(RegExTypes.class), extra);
    }

    static public String getCharsRegexp_Basic() {
        return ABStrings.getCharsRegexp_Basic("");
    }


    static private String getLangSpecialCharacters(EnumSet<Langs> langs) {
        String chars = "";
        if (langs.contains(Langs.PL))
            chars += "ąćęłńóśźż" + "ĄĆĘŁŃÓŚŹŻ";

        return chars;
    }


    public enum Langs {
        PL,
    }

    public enum RegExTypes {
        DIGITS,
        LETTERS,
        SPECIAL,
    }


}
