
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

public class guiframe extends JFrame {

    protected File file;
    private JFileChooser fileCS;
    private final JButton button;
    private final JButton button2;
    protected static JLabel label;

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

        setTitle("Program");
        setSize(400, 200);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        label = new JLabel("null");
        label.setForeground(Color.red);

        button = new JButton("Choose File");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileCS = new JFileChooser();
                fileCS.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileCS.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    file = fileCS.getSelectedFile();
                    label.setText(file.getName());
                }

            }
        });

        button2 = new JButton("Convert");
        button2.addActionListener(new ActionListener() {
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

                        ImageIO.write(image, "JPEG", new File(imgFile, String.format("PageNumber_%d.jpg", i)));
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

        add(button);
        add(button2);

        revalidate();
    }
}