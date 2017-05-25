import javafx.scene.media.MediaPlayer;

public class SoundPlayer{

    private Thread thread;
    
    private MediaPlayer backgroundMusic;
    
    public void playSoundEffect(MediaPlayer sound){
    
        thread = new Thread(() -> {
            try{
                
                sound.play();
                
                System.out.println("Aww yeah");
                
            }catch(Throwable ex){
                
                System.err.println("An error occured whilst playing the audio file.");
                
            }
            
        });
        
        thread.start();
                
    }
    
    public void playBackgroundMusic(MediaPlayer sound){
    
        backgroundMusic = sound;
        
        thread = new Thread(() -> {
            try{
                
                backgroundMusic.play();
                
            }catch(Throwable ex){
                
                System.err.println("An error occured whilst playing the audio file.");
                
            }
        });
        
        thread.start();
                
    }
    
    //--------------------------------------------------------------------------
    
    public void stopBackgroundMusic(){
    
        backgroundMusic.stop();
    
    }

}