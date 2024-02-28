package de.noori.software;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class AnagramApp {

    private static boolean addedNewAnagrams = false;

    public static void main(String[] args) {
        final AnagramUtil anagramUtil = new AnagramUtil();

        int userChoice;
        boolean exitFlag = true;

        while (exitFlag) {
            userChoice = menu();
            switch (userChoice) {
                case 1:
                    // Feature1: Perform "compare words for anagram" case.
                    handleFeature1CheckAnagram(anagramUtil);
                    break;
                case 2:
                    // Perform "find an anagram" case.
                    handleFeature2SearchOfExistingAnagrams(anagramUtil);
                    break;
                case 3:
                default:
                    // The invalid choice.
                    exitFlag = false;
                    defaultExitCase(anagramUtil);

                    break;
            }
        }
    }

    private static void defaultExitCase(AnagramUtil anagramUtil) {
        if (addedNewAnagrams) {
            anagramUtil.writeAnagramsToStore();
            System.out.println("Known anagrams saved");
        }
        System.out.println("Exit: Thank you for anagramming! Bye");
    }

    private static void handleFeature2SearchOfExistingAnagrams(AnagramUtil anagramUtil) {
        System.out.println("Find anagram(s) of word");
        final String word = getWordInput();
        final Set<String> anagrams = anagramUtil.getAnagrams(word);
        if (!anagrams.isEmpty()) { // Found
            System.out.printf("\033[32m Yes, word '%s' has existing anagram(s) '%s'.%n", word, anagrams);
            resetTextColor();
        } else {
            System.out.printf("\033[31m NO, anagram found for word '%s'%n", word);
            resetTextColor();
        }
    }

    private static void handleFeature1CheckAnagram(AnagramUtil anagramUtil) {
        System.out.println("Compare words for anagram");
        System.out.println("Enter a word1 and word2, each followed by ENTER key");
        final BiGram biGram = getInputBigram();
        if (anagramUtil.isAnagram(biGram.getWord(), biGram.getAnagram())) {
            System.out.printf("\033[32m Yes, word '%s' is anagram of '%s'.%n", biGram.getAnagram(), biGram.getWord());
            anagramUtil.rememberAnagram(biGram.getWord(), biGram.getAnagram());
            addedNewAnagrams = true;
            resetTextColor();
        } else {
            System.out.printf("\033[31m NO, word '%s' is anagram of '%s'%n", biGram.getAnagram(), biGram.getWord());
            resetTextColor();
        }

    }


    private static void resetTextColor() {
        System.out.println("\033[0m");
    }


    /**
     * Reading two words from the user input
     */
    private static BiGram getInputBigram() {
        String word = getWordInput();
        String anagram = getWordInput();
        return new BiGram(word, anagram);
    }

    private static String getWordInput() {
        String word = "";
        Scanner inputScan = new Scanner(System.in);

        try {
            word = inputScan.next();
        } catch (InputMismatchException ignored) {

        }
        return word;
    }

    private static int menu() {

        int choice = 0;
        Scanner inputScan = new Scanner(System.in);

        System.out.println("Choose from these choices [1, 2, 3]");
        System.out.println("-------------------------\n");
        System.out.println("1 - Compare for Anagram / Vergleiche zwei Worten");
        System.out.println("2 - Search for Anagram / Suche Anagram");
        System.out.println("3 - Exit / Beenden");
        try {
            choice = inputScan.nextInt();
        } catch (InputMismatchException ignored) {

        }
        return choice;
    }

}
