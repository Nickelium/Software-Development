package Controller.UserController;


import java.lang.reflect.Method;

/**
 * Created by Karina on 05.03.2016.
 */
public class FunctionWrap{

    private String name;
    private Method function;

    public FunctionWrap(String name, Method function){
        setName(name);
        setFunction(function);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getFunction() {
        return function;
    }

    public void setFunction(Method function) {
        this.function = function;
    }
}
