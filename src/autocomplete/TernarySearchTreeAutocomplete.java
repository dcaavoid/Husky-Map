package autocomplete;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        for(CharSequence term : terms){
            this.overallRoot = put(this.overallRoot, term, 0);
        }
    }

    private Node put(Node root, CharSequence term, int index) {
        char c = term.charAt(index);
        if (root == null) {
            root = new Node(c);
        }

        if      (c < root.data)                root.left = put(root.left,  term, index);
        else if (c > root.data)                root.right = put(root.right, term, index);
        else if (index < term.length() - 1)    root.mid = put(root.mid, term, index+1);
        else                                   root.isTerm = true;
        return root;
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();

        if(prefix == null || prefix.length() == 0) return matches;

        Node temp = get(this.overallRoot, prefix, 0);

        if(temp == null) return matches;
        if(temp.isTerm) matches.add(prefix);

        collect(temp.mid, new StringBuilder(prefix), matches);
        return matches;
    }

    private Node get(Node root, CharSequence prefix, int d) {
        if (root == null) return null;
        if (prefix.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = prefix.charAt(d);
        if      (c < root.data)              return get(root.left,  prefix, d);
        else if (c > root.data)              return get(root.right, prefix, d);
        else if (d < prefix.length() - 1)    return get(root.mid, prefix, d+1);
        else                                 return root;
    }

    private void collect(Node root, StringBuilder prefix, List<CharSequence> matches) {
        if (root == null) return;
        collect(root.left, prefix, matches);
        if(root.isTerm) matches.add(prefix.toString() + root.data);
        collect(root.mid, prefix.append(root.data), matches);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(root.right, prefix, matches);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
