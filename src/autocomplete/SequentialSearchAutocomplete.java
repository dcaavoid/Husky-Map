package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
        /*for(CharSequence term : terms){
            this.terms.add(term);
        }*/
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();

        /*if(prefix == null || prefix.length() == 0){
            return matches;
        }*/

        for(CharSequence term : this.terms){
            if(Autocomplete.isPrefixOf(prefix, term)){
                matches.add(term);
            }
        }
        return matches;
    }
}
