import java.util.HashMap;
import java.util.Scanner;

public class Thing {
	
	Integer id;
	String name;
	Thing parent;
	
    //constructors
    public Thing (){        
    }
    public Thing(Scanner sc, HashMap<Integer, Thing> elements){
        name = sc.next();
        id = sc.nextInt();
        parent = elements.get(sc.nextInt());
    }
}
