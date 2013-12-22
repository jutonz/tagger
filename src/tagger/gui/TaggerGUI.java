package tagger.gui;

import tagger.core.TrackGetter;

import javax.swing.*;
import java.awt.event.*;
import java.util.prefs.Preferences;

/**
 * Author: Justin Toniazzo
 * Date:   14 December 2013
 */
public class TaggerGUI extends JFrame implements ActionListener {
    private JTextField enterAURLTextField;
    private JTextArea textArea1;
    private JPanel panel;

    private Preferences preferences;
    private TrackGetter trackGetter;

    public TaggerGUI() {
        super("Tagger");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panel);
        enterAURLTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String url = enterAURLTextField.getText();
                if(TrackGetter.isValidURL(url))
                    getTracks(url);
            }
        });

        // Load or save preferences.
        preferences = Preferences.userRoot().node(getClass().getName());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                preferences.put("winPosX", getLocation().getX() + "");
                preferences.put("winPosY", getLocation().getY() + "");
                preferences.put("winHeight", getSize().getHeight() + "");
                preferences.put("winWidth", getSize().getWidth() + "");
            }

            @Override
            public void windowOpened(WindowEvent e) {
                int x = (int) Double.parseDouble(preferences.get("winPosX", "200"));
                int y = (int) Double.parseDouble(preferences.get("winPosY", "200"));
                setLocation(x, y);

                int height = (int) Double.parseDouble(preferences.get("winHeight", getPreferredSize().getHeight() + ""));
                int width = (int) Double.parseDouble(preferences.get("winWidth", getPreferredSize().getWidth() + ""));
                setSize(width, height);
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

        JFrame frame = new TaggerGUI();
        frame.pack();
        frame.setVisible(true);
    }
}
