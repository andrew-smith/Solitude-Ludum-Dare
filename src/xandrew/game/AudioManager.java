package xandrew.game;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



/**
 * Simple Sound Library that stores and plays sounds
 * @author Andrew
 */
public class AudioManager
{


    /** Database of sounds */
    private HashMap<String, Clip> soundLib;



    /**
     * Creates a new Sound Library
     */
    public AudioManager()
    {
        soundLib = new HashMap<String, Clip>();
    }


    /**
     * Loads a file from HDD and stores it in the library
     * @param filename the filename to load
     * @param label the label to save this file to in the library
     * @return true if file added to library - false if not
     */
    public boolean loadSound(String filename, String label)
    {
        boolean added = false;
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));

            soundLib.put(label, clip);
            added = soundLib.containsValue(clip);
        }


        catch(LineUnavailableException ex)
        {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }        catch(UnsupportedAudioFileException ex)
        {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }        catch(IOException ex)
        {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return added;
    }

    /**
     * Plays a sound from the library - or nothing if the sound isn't found
     * @param label the label of the sound to play
     */
    public void playSound(String label)
    {
        Clip clip = soundLib.get(label);

        if(clip != null)
        {
            if(!clip.isRunning())
            {
                clip.setFramePosition(0);
                clip.start();
            }

        }
    }
}
