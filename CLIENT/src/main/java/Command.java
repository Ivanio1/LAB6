import java.io.Serializable;

/**
 * Класс для передачи команд в виде объекта
 */
public class Command implements Serializable {
    private static final long serialVersionUID = 17L;
    String name;
    String args;

    public Command(String name, String args) {
        this.name = name;
        this.args = args;
    }

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getArgs() {
        return args;
    }
    @Override
    public String toString(){
        if (getArgs()!=null){
            return getName()+" "+getArgs();
        }
        else{
            return getName();
        }
    }
}