package marvelkinator;

public class Persontages {
    int id;
    String name;
    int node;
    public Persontages(int id, String name, int node) {
        this.id = id;
        this.name = name;
        this.node = node;
    }
    @Override
    public String toString() {
        return name;
    }
}
