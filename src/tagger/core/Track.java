package tagger.core;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Author: Justin Toniazzo
 * Date:   13 December 2013
 */
public class Track {

    private String artist;
    private String title;
    private String length;
    private String number;

    private LinkedList<Grammar> grammars;

    public Track(String s) {
        Scanner scan = new Scanner(s);

        // Skip the first field (indicates time into show).
        scan.next();

        // Get the track number.
        number = scan.next();
        number = number.substring(0, number.length() - 1);

        // Get the artist or performer.
        scan.useDelimiter(" - ");
        artist = scan.next();
        artist = artist.substring(1);

        // Reverse the string so we can scan it backwards.
        // We only need the part of the track after the " - ",
        // (the part that contains the track title and duration).
        s = reverse(s.split(" - ")[1]);

        // Get the track's length.
        scan = new Scanner(s);
        scan.useDelimiter("\\(\\s");
        length = reverse(scan.next().substring(1));

        // Get the track's title.
        s = s.substring(length.length() + 3);
        scan = new Scanner(s);
        title = reverse(scan.nextLine());

        // Get the track's title.
//        scan.useDelimiter(" ");
//        scan.next();
//        scan.useDelimiter("\\s\\(");
//        title = scan.next();
//        title = title.substring(1);
//
//        // Get the length of the track.
//        scan.useDelimiter("\\)");
//        length = scan.next();
//        length = length.substring(2);

        // Close the scanner and format the track according
        // to our style guidelines.
        scan.close();
        format();
    }

    private String reverse(String s) {
        String ret = "";
        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i --) {
            ret += chars[i];
        }
        return ret;
    }

    private void format() {
        for(Grammar g : buildGrammars()) {
            title = title.replace(g.oldSequence, g.newSequence);
            artist = artist.replace(g.oldSequence,g.newSequence);
        }
    }

    private LinkedList<Grammar> buildGrammars() {
        grammars = new LinkedList<Grammar>();
        grammars.add(new Grammar("To"));
        grammars.add(new Grammar("With"));
        grammars.add(new Grammar("By"));
        grammars.add(new Grammar("For"));
        grammars.add(new Grammar("The"));
        grammars.add(new Grammar("On"));
        grammars.add(new Grammar("Of"));
        grammars.add(new Grammar("Or"));
        grammars.add(new Grammar("In"));
        grammars.add(new Grammar("Van"));
        grammars.add(new Grammar("Pres."));
        grammars.add(new Grammar("Presents", "pres."));
        grammars.add(new Grammar("presents", "pres."));
        grammars.add(new Grammar("Feat."));
        grammars.add(new Grammar("Featuring", "feat."));
        grammars.add(new Grammar("featuring", "feat."));
        grammars.add(new Grammar("Vs."));
        return grammars;
    }

    @Override
    public String toString() {
        String s = "";
        s += number + ". ";
        s += artist + " - ";
        s += title;
        return s;
    }

    public String toStringWithLength() {
        String s = toString();
        s += " (" + length + ")";
        return s;
    }

    private class Grammar {

        public String oldSequence;
        public String newSequence;

        /**
         * Creates a new grammar holding the old sequence and the one
         * that is to replace it. This class is used for formatting
         * tracks. A typical usage would be to pass "With" as oldSequence,
         * "with" as newSequence, and true to addSpaces, which will pad
         * both sides of both these with a space.
         * @param oldSequence the sequence to be replaced
         * @param newSequence the new sequence to take the old one's place
         * @param addSpaces whether a space should be added to both ends of each sequence
         */
        private Grammar(String oldSequence, String newSequence, boolean addSpaces) {
            if (addSpaces) {
                oldSequence = " " + oldSequence + " ";
                newSequence = " " + newSequence + " ";
            }
            this.oldSequence = oldSequence;
            this.newSequence = newSequence;
        }

        /**
         * Creates a new grammar holding the old sequence and the one
         * that is to replace it. This class is used for formatting
         * tracks. A typical usage would be to pass "With" as oldSequence
         * and "with" as newSequence. Each of these will automatically be
         * padded with a single space.
         * @param oldSequence the sequence to be replaced
         * @param newSequence the new sequence to take the old one's place
         */
        private Grammar(String oldSequence, String newSequence) {
            this(oldSequence, newSequence, true);
        }

        /**
         * Creates a new grammar holding the old sequence and the one
         * that is to replace it. This class is used for formatting
         * tracks. A typical usage would be to pass "With" as oldSequence.
         * This constructor will use the lower case version of oldSequence
         * as the newSequence. It will also pad the ends of both of these
         * with a single space.
         * @param oldSequence the sequence to be replaced
         */
        private Grammar(String oldSequence) {
            this(oldSequence, oldSequence.toLowerCase(), true);
        }

    }
}
