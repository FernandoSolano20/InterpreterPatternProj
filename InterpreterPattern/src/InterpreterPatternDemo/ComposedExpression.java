
package InterpreterPatternDemo;

import Utils.MyItem;
import java.awt.Component;
import java.awt.Container;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.RootPaneContainer;

/**
 *
 * @author Hugo
 */
public class ComposedExpression extends AbstractExpression{
    
    private final LinkedList<AbstractExpression> subExpressions;
    
    public ComposedExpression(String name){
       super(name);
       this.subExpressions = new LinkedList<>();
    }
    
    public void addSubExpression(AbstractExpression exp){
        subExpressions.add(exp);
    }
    
    @Override
    public Object interpret() {
        Object 
                result = super.interpret()
                , contentPane = null;
        
        if (result instanceof RootPaneContainer){

            Container container = ((RootPaneContainer) result).getContentPane();

            for (AbstractExpression exp : this.subExpressions){
                Object child = exp.interpret();

                if (child instanceof Component){
                    container.add((Component)child);
                }
            }                
        }
        
        if (result instanceof JComboBox){
            for (AbstractExpression exp : this.subExpressions){
                Object child = exp.interpret();

                if (child instanceof MyItem){
                    ((JComboBox)result).addItem(((MyItem)child).getName());
                }
            } 
        }
        
        return result;
    }
}
