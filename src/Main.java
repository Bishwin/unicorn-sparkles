import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

    private static final int DEFAULT_LIGHTS = 20;
    private static int lightNum = 0;
    private Light[] lights;

    public static void main (String[] args){
        lightNum = getArgs(args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initBtns();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 800,600, Color.GRAY);

        primaryStage.setTitle("test");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),// Start after one second
                ae -> run()));

        Timeline timeline2 = new Timeline(new KeyFrame(
                Duration.millis(1000* lights.length), //loop again after seconds*total lights
                ae -> run()));

        timeline.setOnFinished(ae ->timeline2.play());
        timeline2.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    /**
     * runs lights,
     * delay is increased based on position of light
     * otherwise lights will play at the same time
     */
    private void run(){
        for(int i = 0; i< lights.length; i++) {
            lights[i].fade(i+1);
            lights[i].turnOff();
        }
    }

    /**
     * adds buttons to gridpane
     * @return pane
     */
    private Pane getGrid(){
        int i = 0;
        GridPane grid = new GridPane();
        for(Light b : lights){
            grid.add(b, i*(i+(int)b.getWidth()),0);
            i++;
        }
        return grid;
    }

    /**
     * Initialises lights inorder of colour enum
     */
    private void initBtns(){
        lights = new Light[lightNum];
        Colour[] colours = Colour.values();
        int j =0;
        for(int i = 0; i< lights.length; i++) {
            if(j>=colours.length) j=0;
            lights[i] = new Light(colours[j].toString());
            j++;
        }
    }

    /**
     * Parses command line arguments
     * @param arg, args to be parsed
     * @return int either number parsed or default value
     */
    private static int getArgs(String[] arg){
        if(arg.length == 0){
            System.out.println("No command line args, using default value");
            return DEFAULT_LIGHTS;
        }
        try{
            int num = Integer.parseInt(arg[0]);
            System.out.println("number " + num + " detected");
            return num;
        }catch (NumberFormatException e){
            System.out.println("Not a number, using default value");
            return DEFAULT_LIGHTS;
        }
    }
}

