import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Light extends Button{

    private String colour;

    public Light(String colour){
        this.colour = colour;
        this.setStyle("-fx-background-color: TRANSPARENT ;");
    }

    /**
     * sets background colour to its "light"
     */
    public void turnOn(){
        this.setStyle("-fx-background-color:" + colour + " ;");
    }

    /**
     * sets background colour to transparent
     */
    public void turnOff(){
        this.setStyle("-fx-background-color: TRANSPARENT ;");
    }

    /**
     * turns the light on and after 1 second off
     * @param i, index of light to calculate delay in seconds
     *           otherwise lights will play at same time
     */
    public void fade(int i){
        Timeline timeline, timeline2;

        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000*i),
                ae -> turnOn()));

        timeline2= new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> turnOff()));

        timeline.setOnFinished(ae -> timeline2.play());
        timeline.play();
    }
}
