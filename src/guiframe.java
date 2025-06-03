
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;
public class guiframe extends JFrame {
    
    protected File file;
    private JFileChooser fileCS;
    private final JButton filechooserbutton;
    private final JButton convertbutton;
    protected static JLabel label;
    private JComboBox Formatbox;
    private String [] formatnames = {"JPEG","PNG","GIF","BMP","TIF"};
    String FormatType = "JPEG";
    guiframe() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            try {

                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception r) {
                System.err.println("Error code 123");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("PDF TO IMAGE");
        setSize(246, 382);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 75, 60);
        setLayout(layout);
        layout.setAlignment(FlowLayout.CENTER);

        label = new JLabel("null");
        label.setFont(new Font("comic sans",Font.BOLD, 20));
        label.setForeground(Color.red);

        filechooserbutton = new JButton("Choose File");
        filechooserbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileCS = new JFileChooser();
                fileCS.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileCS.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    file = fileCS.getSelectedFile();
                    label.setText(file.getName());
                }
                if(label.getText() != "null") //not sure if this is the best way
                label.setForeground(Color.BLACK);

                //Make font size change to accomidate different file lengths 




            }
        });

        Formatbox = new JComboBox(formatnames);
        Formatbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getSource() == Formatbox)
                FormatType = (String)Formatbox.getSelectedItem();
            }
        });


        


        convertbutton = new JButton("Convert");
        convertbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    PDDocument doc = PDDocument.load(file); 
                    PDFRenderer Prender = new PDFRenderer(doc);
                    for (int i = 0; i < doc.getNumberOfPages(); i++) {
                        BufferedImage image;
                        image = Prender.renderImage(i);
                        File imgFile = new File("Image Folder"); // This makes Output Folder
                        if (!imgFile.exists()) {
                            boolean created = imgFile.mkdirs();
                            if (!created) {
                                JOptionPane.showMessageDialog(null, "FOLDER ERROR");
                                System.exit(1);
                            }
                        }

                        ImageIO.write(image,FormatType, new File(imgFile, String.format("PageNumber_%d.%s", i,FormatType.toLowerCase())));
                    }

                    doc.close();
                } catch (InvalidPasswordException exception) {
                    JOptionPane.showMessageDialog(null, "The pdf is protected by a password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "An IO Error has occured", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(label);
        add(filechooserbutton);
        add(Formatbox);
        add(convertbutton);

        revalidate();
    }

}