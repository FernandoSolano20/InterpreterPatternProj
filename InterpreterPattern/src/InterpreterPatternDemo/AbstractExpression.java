
package InterpreterPatternDemo;

import Utils.MyAttribute;
import Utils.MyUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 *
 * @author Hugo
 */
public abstract class AbstractExpression {
    protected final String _name;
    protected Collection<MyAttribute> _attrs;
    
    // constructor
    // This will be use only via super(String) because this is an abstract class.
    public AbstractExpression(String name){
        _name = name;
        _attrs = null;
    }
    
    // Set just once.
    public void setAttributes(Collection<MyAttribute> attrs){
        if (_attrs == null){
            _attrs = attrs;
        }
    }

    public Object interpret(){
        Object result = null;
        try {
            // Using reflection.
            Class c = Class.forName(_name);
            result = c.newInstance();
            
            Method[] methods = c.getMethods();

            for (MyAttribute attr : this._attrs){
                Method m = MyUtils.getMethodByName("set" + attr.name, methods);

                // Different arguments are separated by commas ',' on XML file.
                Object[] argsCandidates = attr.value.split(",");
                Class[] paramsC = m.getParameterTypes();
                
                // number of params mismatch...ignore current attribute.
                if (argsCandidates.length != paramsC.length){
                    continue; 
                }
                
                Object[] args = new Object[paramsC.length]; 
                
                for (int i = 0; i < paramsC.length; i++){        
                    args[i] = this.parse(paramsC[i], (String)argsCandidates[i]);
                }
                
                // Invoke method m of object result with arguments args.
                m.invoke(result, args);
            }
        } 
        catch 
        (
            SecurityException 
            | ClassNotFoundException 
            | IllegalAccessException 
            | InstantiationException 
            | InvocationTargetException
            | IllegalArgumentException
            ex
        ) {
            System.out.println(ex.getMessage());
        } 
        // Equivalent Java 6 code.
//        catch(SecurityException ex){
//            System.out.println(ex.getMessage());
//        }        
//        catch(ClassNotFoundException ex){
//            System.out.println(ex.getMessage());
//        }
//        catch(IllegalAccessException ex){
//            System.out.println(ex.getMessage());
//        }
//        catch(InstantiationException ex){
//            System.out.println(ex.getMessage());
//        }  
//        catch(InvocationTargetException ex){
//            System.out.println(ex.getMessage());
//        }  
//        catch(IllegalArgumentException ex){
//            System.out.println(ex.getMessage());
//        }          
        
        return result;
    }

    private Object parse(Class aClass, String value) {
        if (aClass == int.class){
            return (Object) Integer.parseInt(value);
        }   
        
        if (aClass == float.class){
            return (Object) Float.parseFloat(value);
        }
        
        if (aClass == double.class){
            return (Object) Double.parseDouble(value);
        }        
        
        if (aClass == boolean.class){
            return (Object) Boolean.parseBoolean(value);
        }        
        
        // And so on...
        
        return aClass.cast(value);  
    }
}
