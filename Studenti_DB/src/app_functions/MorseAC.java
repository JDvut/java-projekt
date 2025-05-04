package app_functions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class MorseAC {
	protected static final Map<String, String> morse;

    static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("a", ".-");
        tempMap.put("á", ".--.-");
        tempMap.put("b", "-...");
        tempMap.put("c", "-.-.");
        tempMap.put("č", "-.-..");
        tempMap.put("d", "-..");
        tempMap.put("ď", "-..-.");
        tempMap.put("e", ".");
        tempMap.put("ě", "..-..");
        tempMap.put("é", "..-..");
        tempMap.put("f", "..-.");
        tempMap.put("g", "--.");
        tempMap.put("h", "....");
        tempMap.put("i", "..");
        tempMap.put("í", "..-..");
        tempMap.put("j", ".---");
        tempMap.put("k", "-.-");
        tempMap.put("l", ".-..");
        tempMap.put("m", "--");
        tempMap.put("n", "-.");
        tempMap.put("ň", "--.--");
        tempMap.put("o", "---");
        tempMap.put("ó", "---.");
        tempMap.put("p", ".--.");
        tempMap.put("q", "--.-");
        tempMap.put("r", ".-.");
        tempMap.put("ř", ".-..-");
        tempMap.put("s", "...");
        tempMap.put("š", "---...");
        tempMap.put("t", "-");
        tempMap.put("ť", "--..-");
        tempMap.put("u", "..-");
        tempMap.put("ú", "..--");
        tempMap.put("ů", "..--");
        tempMap.put("v", "...-");
        tempMap.put("w", ".--");
        tempMap.put("x", "-..-");
        tempMap.put("y", "-.--");
        tempMap.put("ý", "-.--");
        tempMap.put("z", "--..");
        tempMap.put("ž", "--..-.");

        morse = Collections.unmodifiableMap(tempMap);
    }
}