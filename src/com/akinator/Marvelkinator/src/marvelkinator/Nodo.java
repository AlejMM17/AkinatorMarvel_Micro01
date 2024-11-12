package marvelkinator;

public class Nodo {
    private int id;
    private String pregunta;
    private String personaje;
    private Nodo izquierdo;
    private Nodo derecho;

    // Constructor para Nodo de pregunta
    public Nodo(String pregunta) {
        this.pregunta = pregunta;
    }

    // Constructor para Nodo de personaje
    public Nodo(int id, String personaje, String pregunta) {
        this.id = id;
        this.personaje = personaje;
        this.pregunta = pregunta;
    }

    public Nodo(int i, String nuevaPregunta) {
        this.id = i;
        this.pregunta = nuevaPregunta;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getPersonaje() { return personaje; }
    public void setPersonaje(String personaje) { this.personaje = personaje; }

    public Nodo getIzquierdo() {

        return izquierdo;
    }
    public void setIzquierdo(Nodo izquierdo) { this.izquierdo = izquierdo; }

    public Nodo getDerecho() { return derecho; }
    public void setDerecho(Nodo derecho) { this.derecho = derecho; }

    public boolean esPersonaje() {
        return personaje != null;  // Si tiene un personaje, es un nodo de personaje
    }
}

