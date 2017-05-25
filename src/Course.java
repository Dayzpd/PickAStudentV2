
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import javafx.scene.control.Alert;

public class Course implements Serializable{
    
    private ArrayList students;
    
    private LinkedList questionQueue;
    
    private String name;

    private int size;
    
    private int[][] tally;
    
    //--------------------------------------------------------------------------
    
    public Course(String name){
    
        this.name = name;
        
        this.size = 0;
        
        students = new ArrayList();
        
        questionQueue = new LinkedList();
                
    }
    
    //--------------------------------------------------------------------------
    
    public void constructTally(){
    
        //instantiating tally now that we have the size of the course
        tally = new int[this.size][3];
        
        //Setting up tally 
        for(int x = 0;x < 3;x++){
        
            for(int y = 0;y < (this.size);y++){
            
                tally[y][x] = 0;
            
            }
            
        }
    
    }
    
    //--------------------------------------------------------------------------
    
    //updateTally
    //If column == 0, then it indicates a correct answer
    //If column == 1, then it indicates a neutral answer
    //If column == 2, then it indicates absent
    public void updateTally(String name, int column){
    
        int index = students.indexOf(name.trim());
                     
        this.tally[index][column]++;
        
        System.out.println(name + " has " + this.tally[index][column]);
        
    }//End of updateTally 
    
    //--------------------------------------------------------------------------
    
    public void downloadTally(){
    
        FileWriter writer = null;
        
        try{
            
            String dwnldFileName = this.name + ".csv";
            writer = new FileWriter(dwnldFileName);
            
            writer.append("Student Name,Correct,Neutral,Absent");
            
            for(int x = 0;x < this.size;x++){
            
                writer.append("\n");
                writer.append(this.students.get(x).toString() + ",");    
                writer.append(Integer.toString(tally[x][0]) + ","); 
                writer.append(Integer.toString(tally[x][1]) + ",");
                writer.append(Integer.toString(tally[x][2]) + ",");
                
            }
            
            writer.flush();
            writer.close();
        
        }catch(IOException ex){
        
            Alert fileWriteFailed = new Alert(Alert.AlertType.INFORMATION);
            fileWriteFailed.setTitle("Tally Download Failed");
            fileWriteFailed.setHeaderText(null);
            fileWriteFailed.setContentText("The tally could not be downloaded.");
            fileWriteFailed.showAndWait();
                        
        }
            
    }//End of downloadTally
    
    //--------------------------------------------------------------------------
    
    //loadCourse - allows users to load a previously saved course
    public Course loadCourse(String absolutePath){
    
        Course courseTemp;
        
        //Read in from file
        try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(absolutePath));){
           
            courseTemp = (Course)objectIn.readObject();
        
        }catch(IOException ex){
        
            ex.printStackTrace();
            
            return null;
                
        }catch(ClassNotFoundException ex){
        
            ex.printStackTrace();
        
            return null;
            
        }
        //Done reading from file
        
        return courseTemp;
        
    }//End of loadCourse
    
    //--------------------------------------------------------------------------
    
    public void saveCourse(Course course){
                
        try(ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(new File("saves/" + this.name + ".dat")))) {
                
            objectOut.writeObject(course);
            
        }catch(FileNotFoundException ex){
            
            System.err.println("An error occurred while writing the file.");
            System.err.println("Caught FileNotFoundException: " + ex.getMessage());
        
        }catch(IOException ex){
            
            System.err.println("An error occurred while writing the file.");
            System.err.println("Caught IOException: " + ex.getMessage());
        
        }
    
    }//End of saveCourse method
        
    //--------------------------------------------------------------------------
    
    public void addStudent(String name){
    
        students.add(name);
        
        this.size++;
        
        for(int x = 0;x < this.size;x++){
        
            System.out.println(x + ". " + students.get(x));
        
        }
    
    }
    
    //--------------------------------------------------------------------------
    
    public void removeStudent(String name){
    
        students.remove(name);
        
        this.size--;
    
    }
    
    //--------------------------------------------------------------------------
    
    public String pickAStudent(){
    
        if(this.questionQueue.size() <= 0){
        
            refillList();
        
        }
        
        return (" " + this.questionQueue.pollFirst().toString() + " ");
    
    }
    
    //--------------------------------------------------------------------------
    
    public void refillList(){
    
        this.questionQueue.clear();
        
        for(int x = 0;x < this.students.size();x++){
        
            this.questionQueue.addLast(students.get(x));
            
        }
                
        Collections.shuffle(this.questionQueue);
                            
    }
    
    //--------------------------------------------------------------------------
    
    public ArrayList getStudents(){
    
        return this.students;
    
    }
    
    //--------------------------------------------------------------------------
    
    public void setName(String name){
        
        this.name = name;
    
    }
    
    //--------------------------------------------------------------------------
    
    public String getName(){
        
        return this.name;
    
    }
    
    //--------------------------------------------------------------------------
    
    public void setSize(int size){
    
        this.size = size;
    
    }
    
    //--------------------------------------------------------------------------
    
    public int getSize(){
    
        return this.size;
    
    }
    
    //--------------------------------------------------------------------------

    public LinkedList getQuestionQueue() {
        
        return questionQueue;
        
    }
    
    
    
}
