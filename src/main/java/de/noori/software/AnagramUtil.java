package de.noori.software;

import java.io.*;
import java.util.*;

import static java.lang.String.format;

public final class AnagramUtil {

    public AnagramUtil() {
        loadAnagrams();
    }

    public static final String ANAGRAMZ_STORE = ".anagramz.store";
    private static final Map<String, Set<String>> word2anagram = new HashMap<String, Set<String>>();


    /**
     * Puts the word and its anagram into existing map
     * @param word1
     * @param anagram
     */
    public void rememberAnagram(String word1, String anagram) {
        final Set<String> existingAnagrams = word2anagram.get(word1);
        final Set<String> anagrams = (existingAnagrams == null || existingAnagrams.isEmpty())
                ? new HashSet<>() : existingAnagrams;
        anagrams.add(anagram);
        word2anagram.put(word1, anagrams);
    }

    /**
     *
     * @param word
     * @return
     */
    public Set<String> getAnagrams(String word) {
        Set<String> existing = new HashSet<>(10);
        if (word2anagram.get(word) != null) {
            existing.addAll(word2anagram.get(word));
        }
        return existing;
    }

    /**
     * Ref. https://stackoverflow.com/questions/13692221/anagram-algorithm-in-java
     *
     * @param w1 Base word
     * @param w2 anagram to be checked
     * @return true if w2 is anagram
     */
    public boolean isAnagram(String w1, String w2) {

        // Early termination check, if strings are of unequal lengths,
        // then they cannot be anagrams
        if (w1.length() != w2.length()) {
            return false;
        }
        w1 = w1.toLowerCase();
        w2 = w2.toLowerCase();
        char[] c1 = w1.toCharArray();
        char[] c2 = w2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        String sc1 = new String(c1);
        String sc2 = new String(c2);
        return sc1.equals(sc2);
    }


    /**
     * Storing anagrams to a file @see AnagramUtil.ANAGRAMZ_STORE
     */
    public void writeAnagramsToStore() {
        if (word2anagram.isEmpty()) {
            return;
        }
        try {
            final File fileOne = new File(ANAGRAMZ_STORE);
            final FileOutputStream fos = new FileOutputStream(fileOne);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(word2anagram);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ignored) {
        }

    }

    /**
     * Loading of existing anagrams from the storage
     */
    private void loadAnagrams() {
        word2anagram.clear();
        word2anagram.putAll(loadAnagramsFromStore());
    }

    private Map<String, Set<String>> loadAnagramsFromStore() {
        Map<String, Set<String>> anagrams = new HashMap<>(10);
        try {
            final File toRead = new File(ANAGRAMZ_STORE);
            final FileInputStream fis = new FileInputStream(toRead);
            final ObjectInputStream ois = new ObjectInputStream(fis);

            anagrams = (HashMap<String, Set<String>>) ois.readObject();

            ois.close();
            fis.close();
            //print All data in MAP
            for (Map.Entry<String, Set<String>> m : anagrams.entrySet()) {
                System.out.println(m.getKey() + " : ANAGRAM(s) " + m.getValue());
            }
        } catch (Exception ignore) {
        }
        return anagrams;
    }
}

class BiGram {
    public String getWord() {
        return word;
    }

    public String getAnagram() {
        return anagram;
    }

    public BiGram(String word, String anagram) {
        this.word = word;
        this.anagram = anagram;
    }
    private final String word;
    private final String anagram;

    @Override
    public String toString() {
        return format("[word: %s, anagram: %s]", word, anagram);
    }
}
