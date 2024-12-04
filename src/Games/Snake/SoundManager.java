package Games.Snake;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.HashMap;

public class SoundManager {
    protected static HashMap<String, AudioClip> sounds = new HashMap<>();
    public SoundManager() {
        loadSound("eat", "Snake/eat.wav");
    }

    protected static void play(String sound){

        try{
            sounds.get(sound).play();
        }
        catch (NullPointerException e){
            System.out.println("No se encontró el sonido");
        }

    }


    private void loadSound(String name, String path) {
        try {
            File soundFile = new File(path);
            AudioClip audioClip = new AudioClip(soundFile.toURI().toString());
            sounds.put(name, audioClip);
        } catch (Exception e) {
            System.out.println("Error al cargar el sonido: " + e.getMessage());
        }
    }

}
