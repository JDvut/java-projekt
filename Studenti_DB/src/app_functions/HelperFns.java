package app_functions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

public class HelperFns extends MorseAC {
	public static int readUserInputInt(Scanner sc) {
		int num = 0;
		
		try {
			num = sc.nextInt();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return num;
	}
	
    public static String groupMorse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String morseCode = "";
        String lowerCaseText = text.toLowerCase();

        for (int i = 0; i < lowerCaseText.length(); i++) {
            String letter = String.valueOf(lowerCaseText.charAt(i));
            
            String morseEquivalent = morse.getOrDefault(letter, "");
            morseCode += morseEquivalent;
        }

        return morseCode;
    }
    
    public static String groupHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            
            return Base64.getEncoder().encodeToString(hashBytes); 
        } catch (Exception e) {
            System.err.println("an error has occured while hashing: " + e);
            
            return null;
        }
    }
    
    public static void sortBySecondName() {
    	
    }
}
