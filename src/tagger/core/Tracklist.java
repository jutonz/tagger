package tagger.core;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

/**
 * Author: Justin Toniazzo
 * Date:   14 December 2013
 */
public class Tracklist {

    private LinkedList<Track> tracks;

    public Tracklist(Elements tracks) {
        this.tracks = new LinkedList<Track>();
        if (tracks != null) {
            for(Element e : tracks)
                add(new Track(e.text()));
        }
    }

    public Tracklist() {
        this(null);
    }

    public void add(Track t) {
        tracks.add(t);
    }

    @Override
    public String toString() {
        String s = "";
        for(Track t : tracks)
            s += t.toString() + "\n";
        return tracks.size() == 0 ? "" : s.substring(0, s.length() - 1);
    }

    public String toStringWithLength() {
        String s = "";
        for(Track t : tracks)
            s += t.toStringWithLength() + "\n";
        return tracks.size() == 0 ? "" : s.substring(0, s.length() - 1);
    }
}
