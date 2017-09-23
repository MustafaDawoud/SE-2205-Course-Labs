package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import java.util.SortedSet;

/**
 * Implement a Trie via linking Nodes.
 */
public class LinkedTrie implements Trie {

    public LinkedTrie() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean put(String word) {
        return false;
    }

    @Override
    public int putAll(SortedSet<? extends String> words) {
        return 0;
    }

    @Override
    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean contains(@Nonnull String word) throws IllegalArgumentException {
        return false;
    }
}
