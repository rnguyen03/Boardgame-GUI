package boardgame.game;

import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.IOException;

/**
 * It's a class that handles files
 */
public class FileHandler {
    /**
     * This function takes a string, a file name, and a file location, and saves the string to the file
     * 
     * @param mainUI The parent frame
     * @param saver The implemented saveable class
     */
    public static int save(JFrame mainUI, Saveable saver){
        JFileChooser fileSelector = new JFileChooser("./assets/");
        int choice = fileSelector.showOpenDialog(mainUI);

        if (choice == JFileChooser.APPROVE_OPTION) {

            Path fpath = FileSystems.getDefault().getPath(fileSelector.getSelectedFile().getParent(), 
                                                            fileSelector.getSelectedFile().getName());
            try{
                Files.writeString(fpath, saver.getStringToSave());
                JOptionPane.showMessageDialog(mainUI, 
                                                "Save Successful!", "Update", JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException exception){
                JOptionPane.showMessageDialog(mainUI,
                                                "Error retrieving file", "Save Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return choice;
    }

    /**
     * It takes a file name and a file location, and returns the contents of the file as a string
     * 
     * @param mainUI The parent frame
     * @param loader The implemented saveable class
     */
    public static int load(JFrame mainUI, Saveable loader){
        JFileChooser fileSelector = new JFileChooser("./assets/");
        int choice = fileSelector.showOpenDialog(mainUI);

        if (choice == JFileChooser.APPROVE_OPTION) {
            Path fpath = FileSystems.getDefault().getPath(fileSelector.getSelectedFile().getParent(), 
                                                            fileSelector.getSelectedFile().getName());
            try{
                String fileLines = String.join("\n", Files.readAllLines(fpath));   
                loader.loadSavedString(fileLines);
                JOptionPane.showMessageDialog(mainUI, 
                                                "Load File Found!", "Update", JOptionPane.INFORMATION_MESSAGE);
            } catch(IOException exception){
                JOptionPane.showMessageDialog(mainUI,
                                                "Error retrieving file", "Load Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return choice;
    }

    


    /**
     * This function is used to print the file handler object
     * 
     * @return The toString() method is being returned.
     */
    @Override
    public String toString() {
        return "FileHandler []";
    }
}
