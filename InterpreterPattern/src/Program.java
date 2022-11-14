
import InterpreterPatternDemo.AbstractExpression;
import Utils.MyXMLParser;
import java.awt.Component;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Hugo
 */
public class Program {

    public static void main(String[] args){
        // Using Interpreter pattern.
        createGUI_Interpreter();
        
        // Creating by hand all the components.
        //createGUI_JFrame();
    }
 
    private static void createGUI_Interpreter(){
        // Read the XML file and translate it to a hierarchical tree form.
        // Note: The parser do not belong to the Interpreter Pattern!
        AbstractExpression expr = new MyXMLParser().parseXML("GUI.xml");
        
        if (expr != null){ 
            // i.e. create all the structure described in XML.
            Object result = expr.interpret();

            // If successful interpretation.
            if (result != null){
                JFrame frame = (JFrame)result;

                frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                
                frame.setLocation(600, 200);
                
                frame.setVisible(true);
            }            
        }         
    }
    
    private static void createGUI_JFrame(){
        JFrame frame = new JFrame("Interpreter");
        
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         
        int 
                width = 200
                , height = 100
                ;
        
        frame.setSize(width, height);
        
        addComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);       
    }
    
    private static void addComponents(Container ctn){
        ctn.setLayout(new BoxLayout(ctn, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel("some label");
        
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        ctn.add(label);
        
        JTextField text = new JTextField();
        text.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn.add(text);
        
        JButton button = new JButton("B");
        button.setAlignmentX(Component.TOP_ALIGNMENT);
        ctn.add(button);         
    }
}
