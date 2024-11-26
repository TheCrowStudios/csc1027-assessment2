package part01;

public class Sign extends IdNameClass {
	private static int currentId;

    public Sign(String name) {
        super(currentId, name); // the name is the sign text
        currentId += 1;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}