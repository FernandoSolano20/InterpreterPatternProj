package Utils;

import InterpreterPatternDemo.AbstractExpression;
import InterpreterPatternDemo.ComposedExpression;
import InterpreterPatternDemo.LeafExpression;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Hugo
 */
public class MyXMLParser {
    private Collection<MyAttribute> namespaces = null;
    
    public AbstractExpression parseXML(String fileName){
        try{
            File f = new File(fileName);
            DocumentBuilderFactory docBuilderFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFac.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            doc.getDocumentElement().normalize();
            
            // namespaces
            Element rootElem = doc.getDocumentElement();
            this.namespaces = readAttributes(rootElem.getAttributes());
            
            NodeList childNodes = rootElem.getChildNodes();
            // Because
            // #text    <- First node
            // Element  <- Second node
            // #text    <- Third node
            for (int i = 0; i < childNodes.getLength(); i++){
                if (childNodes.item(i).getNodeName().equals("JFrame")){
                    return buildExpressionTree(childNodes.item(i));
                }
            }
            
            return null;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }    
    
    private Collection<MyAttribute> readAttributes(NamedNodeMap attrs){
        if (attrs != null){
            Collection<MyAttribute> result = new ArrayList<>();
            
            String attrName, attrValue;
            
            for (int i = 0; i < attrs.getLength(); i++){      
                
                attrName = attrs.item(i).getNodeName();
                attrValue = attrs.item(i).getNodeValue();
                
                result.add(new MyAttribute(attrName, attrValue));
            }
            return result;
        }
        return null;
    }
    
    private AbstractExpression buildExpressionTree(Node node){
            AbstractExpression exp = null;
            
            String nodeName = getElementFullName(node.getNodeName());
            
            if (nodeName == null){
                return null;
            }
            
            if(node.hasChildNodes()){
                ComposedExpression comp = new ComposedExpression(nodeName);

                NodeList child = node.getChildNodes();
                for(int i = 0; i < child.getLength(); i++){
                    Node n = child.item(i);
                    
                    if (n.getNodeType() != Node.TEXT_NODE){
                        comp.addSubExpression(buildExpressionTree(n));
                    }    
                }
                exp = comp;
            }
            else{
                exp = new LeafExpression(nodeName);
            }
            
            exp.setAttributes(readAttributes(node.getAttributes()));
            
            return exp;
    }

    private String getElementFullName(String nodeName) {      
        String namespaceToSearchFor;
        int idx;
        
        if ((idx = nodeName.indexOf('.')) > 0){
            namespaceToSearchFor = nodeName.substring(0, idx);
            nodeName = nodeName.substring(idx);
        } 
        else{
            namespaceToSearchFor = "default";
            nodeName = "." + nodeName;
        }

        for (MyAttribute attr : namespaces){
           if (attr.name.equals(namespaceToSearchFor)){     
               return attr.value + nodeName;  
           }
        }         

        return null;
    }
}
