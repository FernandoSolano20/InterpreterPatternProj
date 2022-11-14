
package InterpreterPattern;

import java.util.Collection;

/**
 *
 * @author Hugo
 */
public class ComposedExpression extends AbstractExpression{
    // Child expressions.
    private Collection<AbstractExpression> child;
    
    @Override
    public void interpret(Context ctx) {
        // This code doesn't have to be exactly like this!
        
        // Call AbstractExpression.interpret(Context ctx) method.
        super.interpret(ctx);
        
        // Get an interpretation of each child expression.
        for (AbstractExpression exp : this.child){
            exp.interpret(ctx);
        }
    }
    
}
