package autocomplete.cities;

import autocomplete.Autocomplete;
import autocomplete.TreeSetAutocomplete;

public class TreeSetAutocompleteTests extends AutocompleteTests {
    public Autocomplete createAutocomplete() { return new TreeSetAutocomplete(); }
}
