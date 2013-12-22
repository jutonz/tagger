package tagger.core;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Author: Justin Toniazzo
 * Date:   13 December 2013
 */
public class TrackGetter extends Thread{

    private String  url;
    private Tracklist tracklist;
    private ActionListener listener;

    public TrackGetter(String url) {
        this.url = url;
    }

    public TrackGetter(String url, ActionListener listener) {
        this.url = url;
        this.listener = listener;
    }

    /**
     * Connects to the URL associated with this object and retrieves
     * the tracklist it specifies. The caller will be notified of this
     * process' completion via an event sent to the listener associated
     * with this object.
     */
    @Override
    public void run() {
//        tracklist = getTracks(url);
        Elements tracks = null;
        try {
            Document doc = Jsoup.connect(url).timeout(30000).get();
            Element tlist = doc.getElementsByClass("tracklist").first();
            tracks = tlist.getElementsByClass("odd");
            Elements evenTracks = tlist.getElementsByClass("even");
            for(int i = 1, j = 0; j < evenTracks.size(); i += 2, j++)
                tracks.add(i, evenTracks.get(j));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + " in " + getClass().getName() + ": " + e.getMessage());
        } finally {
            tracklist = new Tracklist(tracks);
            ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Tracks found");
            listener.actionPerformed(e);
        }
    }

    /**
     * Returns the tracklist enumerated in the specified url, or
     * an empty tracklist if an error occurred attempting to connect.
     * @param url A url specifying a tracklist (must be from cuenation.com)
     * @return the tracks at the url, encapsulated as a Tracklist
     */
    private Tracklist getTracks(String url) {
        Elements tracks = null;
        try {
            Document doc = Jsoup.connect(url).timeout(30000).get();
            Element tlist = doc.getElementsByClass("tracklist").first();
            tracks = tlist.getElementsByClass("odd");
            Elements evenTracks = tlist.getElementsByClass("even");
            for(int i = 1, j = 0; j < evenTracks.size(); i += 2, j++)
                tracks.add(i, evenTracks.get(j));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + " in " + getClass().getName() + ": " + e.getMessage());
        } finally {
            return new Tracklist(tracks);
        }
    }

    public Tracklist getTracks() {
        return tracklist;
    }

    public static boolean isValidURL(String url) {
        String newUrl = url;
        if (!url.startsWith("http://"))
            newUrl = "http://" + url;
        return new UrlValidator().isValid(newUrl);
    }

    @Override
    public String toString() {
        return tracklist.toString();
    }

    public String toStringWithLengths() {
        return tracklist.toStringWithLength();
    }

    private class TrackGetterThread extends Thread {

        private ActionListener listener;

        public TrackGetterThread(ActionListener listener) {
            this.listener = listener;
        }

        public void run() {
            tracklist = getTracks(url);
            ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Tracks found");
            listener.actionPerformed(e);
        }

        /**
         * Returns the tracklist enumerated in the specified url, or
         * an empty tracklist if an error occurred attempting to connect.
         * @param url A url specifying a tracklist (must be from cuenation.com)
         * @return the tracks at the url, encapsulated as a Tracklist
         */
        private Tracklist getTracks(String url) {
            Elements tracks = null;
            try {
                Document doc = Jsoup.connect(url).timeout(30000).get();
                Element tlist = doc.getElementsByClass("tracklist").first();
                tracks = tlist.getElementsByClass("odd");
                Elements evenTracks = tlist.getElementsByClass("even");
                for(int i = 1, j = 0; j < evenTracks.size(); i += 2, j++)
                    tracks.add(i, evenTracks.get(j));
            } catch (IOException e) {
                System.err.println(e.getClass().getName() + " in " + getClass().getName() + ": " + e.getMessage());
            } finally {
                return new Tracklist(tracks);
            }
        }
    }
}
