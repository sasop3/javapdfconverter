package code;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
public class guiframe extends JFrame {
    
    protected File file;
    private final JButton button;
    private final JButton button2;
    protected static JLabel label;
    guiframe()
    {
        setTitle("Program");
        setSize(400, 200);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
       
        label = new JLabel("null");
        label.setForeground(Color.red);
        
        button = new JButton("Choose File");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new filechooser();
            }
        });
        
        button2 = new JButton("Convert");


        add(label);
        add(button);
        add(button2);
       
        


        revalidate();
    }
}