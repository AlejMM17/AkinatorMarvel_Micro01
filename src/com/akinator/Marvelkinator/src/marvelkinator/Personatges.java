package marvelkinator;

public class Personatges {
    int id;
    String name;
    int node;
    public Personatges(int id, String name, int node) {
        this.id = id;
        this.name = name;
        this.node = node;
    }
    @Override
    public String toString() {
        return name;
    }
}
