package tagger.core;

import junit.framework.AssertionFailedError;
import org.junit.Test;
import sun.print.resources.serviceui_zh_TW;

import static junit.framework.Assert.*;

/**
 * Author: Justin Toniazzo
 * Date:   13 December 2013
 */
public class TrackTest {

//    @Test
    public void weee() {
        Track t = new Track("[15:11] 05. Arty, Above & Beyond feat. Zoe Johnston - You Got To Believe (Above & Beyond Mashup) [Record of the Week] (04:45)");
    }

    @Test
    public void testTrack() {
        String input = "";
        String expected = "";
        String actual = "";
        Track t = null;

        try {
            input = "[05:27] 03. Craig Connelly & Shaun Gregory - Midnight Circus (05:06)";
            t = new Track(input);
            expected = "03. Craig Connelly & Shaun Gregory - Midnight Circus";
            actual = t.toString();
            assertTrue(actual.equals(expected));

            expected = "03. Craig Connelly & Shaun Gregory - Midnight Circus (05:06)";
            actual = t.toStringWithLength();
            assertTrue(actual.equals(expected));


            input = "[05:27] 14. Above & Beyond pres. Oceanlab - Sirens Of The Sea (04:42)";
            t = new Track(input);
            expected = "14. Above & Beyond pres. Oceanlab - Sirens of the Sea";
            actual = t.toString();
            assertTrue(actual.equals(expected));

            expected = "14. Above & Beyond pres. Oceanlab - Sirens of the Sea (04:42)";
            actual = t.toStringWithLength();
            assertTrue(actual.equals(expected));


            input = "[15:11] 05. Arty, Above & Beyond feat. Zoe Johnston - You Got To Believe (Above & Beyond Mashup) [Record of the Week] (04:45)";
            t = new Track(input);
            expected = "05. Arty, Above & Beyond feat. Zoe Johnston - You Got to Believe (Above & Beyond Mashup) [Record of the Week]";
            actual = t.toString();
            assertTrue(actual.equals(expected));

            expected = "05. Arty, Above & Beyond feat. Zoe Johnston - You Got to Believe (Above & Beyond Mashup) [Record of the Week] (04:45)";
            actual = t.toStringWithLength();
            assertTrue(actual.equals(expected));
        } catch (AssertionFailedError e) {
            System.out.println("Input:    " + input);
            System.out.println("Expected: " + expected);
            System.out.println("Actual:   " + actual + "\n");
            throw e;
        }
    }
}
