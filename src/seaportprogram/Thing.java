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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Thing getParent() {
		return parent;
	}
	public void setParent(Thing parent) {
		this.parent = parent;
	}
}
