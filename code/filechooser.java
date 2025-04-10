package code;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;

public class filechooser extends JFrame {


    private final JFileChooser fileCH;

    filechooser()
    {
        
        fileCH = new JFileChooser();
        fileCH.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileCH.showOpenDialog(null);
        if(option == fileCH.APPROVE_OPTION)
        {
            File Selectedfile = fileCH.getSelectedFile();
            guiframe.label.setText(Selectedfile.getName());
        }
        add(fileCH);
        
    }
}