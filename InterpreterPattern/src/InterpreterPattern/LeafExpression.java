
package InterpreterPattern;

/**
 *
 * @author Hugo
 */
public class LeafExpression extends AbstractExpression{

    @Override
    public void interpret(Context ctx) {
        // Specific code here.
        
        // Call AbstractExpression.interpret(Context ctx) method.
        super.interpret(ctx); 
    } 
}
