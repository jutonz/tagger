package tagger.core;

import org.junit.Test;

/**
 * Author: Justin Toniazzo
 * Date:   13 December 2013
 */
public class TrackGetterTest {

    private String url = "http://cuenation.com/?page=tracklist&folder=abgt&filename=Above+%26+Beyond+-+Group+Therapy+Radio+045+%28guest+Orjan+Nilsen%29+%282013-09-13%29.cue";

    @Test
    public void testTrackGetter() {
        try {
            new TrackGetter(url);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + " in " + getClass().getName() + ": " + e.getMessage());
        }
    }
}
