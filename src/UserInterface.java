
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class UserInterface {
    
    private Stage stage;
    
    private BorderPane root;
    
    private Course course;
    
    private Label popupText, studentPicked, title, createCourseBtn, loadCourseBtn, settingsBtn, exitBtn, loadCourseTitle;
    
    private TextField courseName, studentName;
    
    private Button tally, back, next, done, addStudent, pickStudent, load;
    
    private HBox logoBox, popupBox;
    
    private ImageView letuLogo, incorrect, correct, absent;

    private SoundPlayer soundPlayer;
    
    private final URL onHoverSound, styleURL;
    
    private final Media onHoverMedia;
    
    private MediaPlayer playOnHoverSound;
    
    private String css;
    
    private ScrollPane loadCourseContent;
    
    Thread thread;
    
    //Font font;
    
    //--------------------------------------------------------------------------
    
    public UserInterface(BorderPane root, Stage primaryStage){
        
        this.root = root;
        
        this.stage = primaryStage;
        
        styleURL = this.getClass().getResource("/resources/style.css");
        css = styleURL.toExternalForm(); 
        root.getStylesheets().add(css);
        
        studentPicked = new Label("             ");
        studentPicked.getStyleClass().add("studentPicked");
        
        title = new Label("Pick a Student v2.0");
        title.getStyleClass().add("title");
        
        createCourseBtn = new Label("Create Course");
        createCourseBtn.getStyleClass().add("menuBtn");
        
        loadCourseBtn = new Label("Load Course");
        loadCourseBtn.getStyleClass().add("menuBtn");
                    
        settingsBtn = new Label("Settings");
        settingsBtn.getStyleClass().add("menuBtn");
        
        exitBtn = new Label("Exit");
        exitBtn.getStyleClass().add("menuBtn");
        
        courseName = new TextField("Course Name");
        courseName.setMaxWidth(400);
        courseName.getStyleClass().add("textfield");
        
        studentName = new TextField("Student Name");
        studentName.setMaxWidth(400);
        studentName.getStyleClass().add("textfield");
        
        tally = new Button("Tally");
        tally.getStyleClass().add("button");
        
        back = new Button("Back");
        back.getStyleClass().add("button");
        
        load = new Button("Load");
        load.getStyleClass().add("button");
        
        next = new Button("Next");
        next.getStyleClass().add("button");
        
        addStudent = new Button("Add Student");
        addStudent.getStyleClass().add("button");
        
        done = new Button("Done");
        done.getStyleClass().add("button");
        
        pickStudent = new Button("Next");
        pickStudent.getStyleClass().add("pick_button");
        
        logoBox = new HBox();
        letuLogo = new ImageView(new Image("/resources/images/letu.png"));
        letuLogo.setFitWidth(220.0);
        letuLogo.setFitHeight(80.0);
        logoBox.getChildren().add(letuLogo);
        logoBox.setPadding(new Insets(10, 10, 10, 10));
        logoBox.setAlignment(Pos.BOTTOM_RIGHT);
        root.setBottom(logoBox);
        
        absent = new ImageView(new Image("/resources/images/absent.png"));
        absent.getStyleClass().add("gradeBtn");
        absent.setFitHeight(120.0);
        absent.setFitWidth(120.0);
        
        incorrect = new ImageView(new Image("/resources/images/incorrect.png"));
        incorrect.getStyleClass().add("gradeBtn");
        incorrect.setFitHeight(135.0);
        incorrect.setFitWidth(135.0);
        
        correct = new ImageView(new Image("/resources/images/correct.png"));
        correct.getStyleClass().add("gradeBtn");
        correct.setFitHeight(120.0);
        correct.setFitWidth(120.0);
        
        onHoverSound = getClass().getResource("/resources/sounds/on_hover_pop.wav");
        onHoverMedia = new Media(onHoverSound.toString());
        playOnHoverSound = new MediaPlayer(onHoverMedia);
        
        popupText = new Label("Reshuffling List");
        popupText.getStyleClass().add("popup");
        popupBox = new HBox();
        popupBox.getChildren().add(popupText);
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10, 10, 10, 10));
        
        loadCourseTitle = new Label("Load Course");
        loadCourseTitle.getStyleClass().add("title");
        
        loadCourseContent = new ScrollPane();
        loadCourseContent.getStyleClass().add("scrollpane");
        loadCourseContent.setMaxWidth(1100);
        loadCourseContent.setMaxHeight(650);
        
        soundPlayer = new SoundPlayer();
        
    }//End of constructor
    
    //--------------------------------------------------------------------------
    
    public void start(){
    
        introScreen();
    
    }
    
    //--------------------------------------------------------------------------
    
    //Displays main menu
    public void introScreen(){
        
        VBox launchScreenButtons = new VBox(50);
        launchScreenButtons.getChildren().addAll(title, createCourseBtn, loadCourseBtn, settingsBtn, exitBtn);
        launchScreenButtons.setAlignment(Pos.CENTER);
        
        root.setCenter(launchScreenButtons);
        
        //createCourseBtn: Remove gold shadow on exit
        createCourseBtn.setOnMouseClicked((event) -> {
                
            root.setCenter(null);
                    
            createCoursePt1();
                
        });
        
        //loadCourseBtn: Remove gold shadow on exit
        loadCourseBtn.setOnMouseClicked((event) -> {
                
            root.setCenter(null);
                    
            loadCourse();
                
        });
                     
        //Play Bckground Music
        /*final URL backgroundMusicFile = getClass().getResource("resources/sounds/backgroundmusic.mp3");
        final Media backgroundMusic = new Media(backgroundMusicFile.toString());
        MediaPlayer playBackgroundMusic = new MediaPlayer(backgroundMusic);
        
        soundPlayer.playBackgroundMusic(playBackgroundMusic);*/
        
    }
    
    //--------------------------------------------------------------------------
    
    //Load in a previous save
    public void loadCourse(){
    
        ToggleGroup group = new ToggleGroup();
        
        File folder = new File("saves");
        
        File[] listOfFiles = folder.listFiles();      
        
        ArrayList fileLabels = new ArrayList();
        
        ArrayList radioBtns = new ArrayList();
        
        VBox viewFileLabels = new VBox(20);
        viewFileLabels.getChildren().add(loadCourseTitle);
        
        Label label;
        
        RadioButton radBtn;
        
        HBox hbox, btns;
        
        for(int x = 0;x < listOfFiles.length;x++){
            
            radBtn = new RadioButton();
            radBtn.setToggleGroup(group);
            radioBtns.add(radBtn);
            
            label = new Label(listOfFiles[x].toString().replace("saves\\", ""));
            label.getStyleClass().add("loadClassOption");
            
            fileLabels.add(label);
            
            hbox = new HBox(30);
            hbox.getChildren().addAll(radBtn, label);
            
            viewFileLabels.getChildren().add(hbox);
            
        }   
        
        loadCourseContent.setContent(viewFileLabels);
        
        btns = new HBox(30);
        btns.getChildren().addAll(back, load);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(0,0,45,0));
        
        root.setBottom(btns);
        
        root.setCenter(loadCourseContent);
            
        //back: Return to previous menu
        back.setOnMouseClicked((event) -> {

            root.setCenter(null);
            
            root.setBottom(null);
            
            introScreen();

        });
        
        //load: Loads the selected course
        load.setOnMouseClicked((event) -> {

            root.setCenter(null);
            
            root.setBottom(null);
            
            //load selected file

        });
        
    }
    
    //--------------------------------------------------------------------------
    
    //Gets the course name from the user
    public void createCoursePt1(){
        
        VBox createCourseContent = new VBox(50);
        HBox createCourseButtons = new HBox(100);
        createCourseButtons.getChildren().addAll(back, next);
        createCourseContent.getChildren().addAll(courseName, createCourseButtons);
        createCourseContent.setAlignment(Pos.CENTER);
        createCourseButtons.setAlignment(Pos.CENTER);
        
        root.setCenter(createCourseContent);
        
        //courseName: Clear test on click
        courseName.setOnMouseClicked((event) -> {

            courseName.setText("");

        });

        //back: Return to previous menu
        back.setOnMouseClicked((event) -> {

            root.setCenter(null);
            
            introScreen();

        });
        
        //next: Moves on to get student names
        next.setOnMouseClicked((event) -> {

            course = new Course(courseName.getText());
            
            root.setCenter(null);
            
            createCoursePt2();

        });
                
    }
    
    //--------------------------------------------------------------------------
    
    //Gets student names
    public void createCoursePt2(){
        
        VBox createCourseContent = new VBox(50);
        HBox createCourseButtons = new HBox(100);
        createCourseButtons.getChildren().addAll(done, addStudent);
        createCourseContent.getChildren().addAll(studentName, createCourseButtons);
        createCourseContent.setAlignment(Pos.CENTER);
        createCourseButtons.setAlignment(Pos.CENTER);
        
        root.setCenter(createCourseContent);
        
        //studentName: Clear test on click
        studentName.setOnMouseClicked((event) -> {

            studentName.setText("");

        });

        //done: Complete course creation
        done.setOnMouseClicked((event) -> {

            root.setCenter(null);
            
            course.constructTally();
            
            startGame();

        });
        
        //next: Moves on to get student names
        addStudent.setOnMouseClicked((event) -> {

            course.addStudent(studentName.getText());
            
            studentName.setText("");
            
            root.setCenter(null);
            
            createCoursePt2();

        });
                
    }
    
    //--------------------------------------------------------------------------

    //Start picking students
    public void startGame(){
    
        VBox gameContent = new VBox(60);
        HBox gradeButtons = new HBox(100);
        gradeButtons.getChildren().addAll(absent, incorrect, correct);
        gameContent.getChildren().addAll(title, studentPicked, gradeButtons, pickStudent);
        gameContent.setAlignment(Pos.CENTER);
        gradeButtons.setAlignment(Pos.CENTER);
        
        root.setCenter(gameContent);
        
        VBox leftBox = new VBox(25);
        leftBox.setPadding(new Insets(25, 25, 25, 25));
        leftBox.getChildren().addAll(back); 
        
        root.setLeft(leftBox);
        
        VBox rightBox = new VBox(25);
        rightBox.setPadding(new Insets(25, 25, 25, 25));
        rightBox.getChildren().addAll(tally); 
        
        root.setRight(rightBox);
        
        //pickStudent: Pick a new student
        pickStudent.setOnMouseClicked((event) -> {
            
            root.setTop(null);

            if(course.getQuestionQueue().size() <= 0){
            
                course.refillList();
                
                root.setTop(popupBox);
                
                System.out.println("Woot");
                
            }
            
            studentPicked.setText(course.pickAStudent());

        });
        
        //stage: Save & Exit Listener
        stage.setOnCloseRequest((WindowEvent event) -> {
            
            try{
                
                course.downloadTally();
                
                course.saveCourse(course);
                
            }catch(NullPointerException ex){
                
                System.err.println("No class was ever instantiated so nothing was saved.");
                
            }
            
            System.exit(0);
            
        });
        
        //back: Return to menu
        back.setOnMouseClicked((event) -> {
            
            course.saveCourse(course);
            
            root.setCenter(null);
            
            root.setLeft(null);
            
            root.setRight(null);
            
            introScreen();
            
        });
        
        //correct: Increment current student's correct tally
        correct.setOnMouseClicked((event) -> {
            
            course.updateTally(studentPicked.getText(), 0);
            
        });
        
        //incorrect: Increment current student's incorrect tally
        incorrect.setOnMouseClicked((event) -> {
            
            course.updateTally(studentPicked.getText(), 1);
            
        });
        
        //absent: Increment current student's correct tally
        absent.setOnMouseClicked((event) -> {
            
            course.updateTally(studentPicked.getText(), 2);
            
        });
        
        //tally: Show tally page
        tally.setOnMouseClicked((event) -> {
            
            root.setCenter(null);
            
            tallyPage();
            
        });
            
    }
    
    //--------------------------------------------------------------------------
    
    public void tallyPage(){
    
        
    
    }
    
    //--------------------------------------------------------------------------
    
    public Label getTitle() {
        return title;
    }

    public Label getCreateCourseBtn() {
        return createCourseBtn;
    }

    public Label getLoadCourseBtn() {
        return loadCourseBtn;
    }

    public Label getSettingsBtn() {
        return settingsBtn;
    }

    public Label getExitBtn() {
        return exitBtn;
    }

    public ImageView getLetuLogo() {
        return letuLogo;
    }

    public ImageView getIncorrect() {
        return incorrect;
    }

    public ImageView getCorrect() {
        return correct;
    }

    public ImageView getAbsent() {
        return absent;
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public void setCreateCourseBtn(Label createCourseBtn) {
        this.createCourseBtn = createCourseBtn;
    }

    public void setLoadCourseBtn(Label loadCourseBtn) {
        this.loadCourseBtn = loadCourseBtn;
    }

    public void setSettingsBtn(Label settingsBtn) {
        this.settingsBtn = settingsBtn;
    }

    public void setExitBtn(Label exitBtn) {
        this.exitBtn = exitBtn;
    }

    public void setLetuLogo(ImageView letuLogo) {
        this.letuLogo = letuLogo;
    }

    public void setIncorrect(ImageView incorrect) {
        this.incorrect = incorrect;
    }

    public void setCorrect(ImageView correct) {
        this.correct = correct;
    }

    public void setAbsent(ImageView absent) {
        this.absent = absent;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }
    
}
