package mario;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class Processar extends DefaultHandler {
    private static final String professoretiqueta = "professor";
    private static final String nombreetiqueta = "nom";
    private static final String apellidoeiqueta = "cognom";
    private static final String sexoetiqueta = "sexe";
    private static final String edatetiqueta= "edat";
    private boolean nombre = false;
    private boolean apellido = false ;
    private boolean sexo = false;
    private boolean edat = false;
    private boolean professor = false;
    private String nombre_professor;
    private String apellido_professor;
    private String sexo_professor;
    private int edat_professor;

    private static ArrayList<Professor> profesores = new ArrayList<Professor>();

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase(professoretiqueta)){
            professor = true;
        }
        if (qName.equalsIgnoreCase(nombreetiqueta)){
            nombre = true;
        }
        if (qName.equalsIgnoreCase(apellidoeiqueta)) {
            apellido = true;
        }
        if (qName.equalsIgnoreCase(sexoetiqueta)){
            sexo = true;
        }
        if (qName.equalsIgnoreCase(edatetiqueta)){
            edat = true;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if(professor && nombre){
            nombre_professor = new String(ch, start,length);
        }
        if(professor && apellido){
            apellido_professor = new String(ch,start,length);
        }
        if(professor && sexo){
            sexo_professor = new String(ch,start,length);
        }
        if(professor && edat){
            edat_professor = Integer.parseInt(new String(ch,start,length));
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase(professoretiqueta)){
            professor = false;
            Professor professor = new Professor();
            professor.setNombre(nombre_professor);
            professor.setApellido(apellido_professor);
            professor.setSexo(sexo_professor);
            professor.setEdat(edat_professor);
            profesores.add(professor);
        }
        if (qName.equalsIgnoreCase(nombreetiqueta)){
            nombre = false;
        }
        if (qName.equalsIgnoreCase(apellidoeiqueta)) {
            apellido = false;
        }
        if (qName.equalsIgnoreCase(sexoetiqueta)){
            sexo = false;
        }
        if (qName.equalsIgnoreCase(edatetiqueta)){
            edat = false;
        }
    }

    public void endDocument() throws SAXException {
        System.out.println("final del documento");
        /*for(int i=0;i<profesores.size();i++){
            System.out.println(profesores.get(i).getSexo());
        }*/
    }

    public static ArrayList<Professor> getProfesores() {
        return profesores;
    }

    public void vaciarlistaprofesores(){
        profesores.clear();
    }

    public void setProfesores(ArrayList<Professor> profesores) {
        this.profesores = profesores;
    }
}
