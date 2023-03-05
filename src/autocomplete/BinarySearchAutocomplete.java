package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();

        if(prefix == null || prefix.length() == 0){
            return matches;
        }

        int index = Collections.binarySearch(this.terms, prefix, CharSequence::compare);

        if(index < 0){
            index = -(index + 1);
        }

        while(Autocomplete.isPrefixOf(prefix, this.terms.get(index))){
            matches.add(this.terms.get(index));
            index++;
        }
        return matches;
    }
}
