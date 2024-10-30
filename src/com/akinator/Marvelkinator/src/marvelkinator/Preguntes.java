package marvelkinator;

public class Preguntes {
    int id;
    String pregunta;
    int nodeEsquerra;
    int nodeDreta;

    public Preguntes(int id, String pregunta, int nodeEsquerra, int nodeDreta) {
        this.id = id;
        this.pregunta = pregunta;
        this.nodeEsquerra = nodeEsquerra;
        this.nodeDreta = nodeDreta;
    }
    @Override
    public String toString() {
        return pregunta;
    }
}
