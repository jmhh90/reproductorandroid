package jmhh.reproductormelodiame;

//Clase que contiene los elementos que va a tener la lista de reproducción
public class Titular {
	//Se crean las variables que van a contener, el nombre de la canción, grupo y su tiempo
    private String date;
    private String title;
    private String description;

    public  Titular(String date, String title, String description) {
    	//Recibe el nombre de la canción, grupo y su tiempo
        this.date = date;
        this.title = title;
        this.description = description;
    }
//Devuelve el nombre de la canción, grupo y su tiempo
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 