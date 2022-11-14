
package InterpreterPatternDemo;

/**
 *
 * @author Hugo
 */
public class LeafExpression extends AbstractExpression{

    public LeafExpression(String name){
        super(name);
    }
    
    @Override
    public Object interpret() {
        return super.interpret();
    }   
}
