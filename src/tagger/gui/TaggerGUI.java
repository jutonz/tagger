package tagger.gui;

import tagger.core.TrackGetter;
import tagger.core.Tracklist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Author: Justin Toniazzo
 * Date:   14 December 2013
 */
public class TaggerGUI implements ActionListener {
    private JTextField enterAURLTextField;
    private JTextArea textArea1;
    private JPanel panel;

    private TrackGetter trackGetter;

    public TaggerGUI() {
        enterAURLTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String url = enterAURLTextField.getText();
                if(TrackGetter.isValidURL(url))
                    getTracks(url);
            }
        });
    }

    private void getTracks(String fromURL) {
        textArea1.setText("Reading tracks...");
        trackGetter = new TrackGetter(fromURL, this);
        trackGetter.start();
    }

    public void actionPerformed(ActionEvent e) {
        textArea1.setText(trackGetter.toString());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        JFrame frame = new JFrame("Tagger");
        frame.setContentPane(new TaggerGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
