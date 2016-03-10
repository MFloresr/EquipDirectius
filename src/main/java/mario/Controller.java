package mario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import jdk.internal.util.xml.impl.Input;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    @FXML
    private Button btnLeer;
    @FXML
    private Button btnRecalcular;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField textDireccion;
    @FXML
    private TextField textSecretari;
    @FXML
    private TextField textCoordinacio;
    @FXML
    private ListView listaCapEstudi;
    private ObservableList<String> items = FXCollections.observableArrayList ();
    private File fichero = new File (".","fichero.txt");
    ArrayList<Professor> profesores = new ArrayList<Professor>();
    Alert alert = new Alert(Alert.AlertType.WARNING);
    private int mujeres = 0;
    private int hombres = 0;
    private int homes = 0;
    private int dones = 0;
    private Random rand;
    private ArrayList<Professor> profesoresElegidos= new ArrayList<Professor>();

    @FXML
    public void cargarFichero(Event event) throws ParserConfigurationException, SAXException, IOException {
        resetVariables();
        FileChooser fichero= new FileChooser();
        fichero.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File archivoEscogido = fichero.showOpenDialog(null);

        if( archivoEscogido !=null){
            SAXParserFactory fabrica = SAXParserFactory.newInstance();
            fabrica.setNamespaceAware(true);
            SAXParser parser= fabrica.newSAXParser();
            InputStream archivo = new FileInputStream(archivoEscogido);
            try {
                parser.parse(archivo, new Processar());
                archivo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        profesores = Processar.getProfesores();
        if(personasNecesarias()){
            cantidadRequerida();
            ponerEnPantalla();
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error de personal");
            alert.setContentText("No hay personal suficientes para formar un equipo directivo");

            alert.showAndWait();
        }

    }

    //professores minimos que pueden haver
    public boolean personasNecesarias(){  //cantidat de personas en la lista
         if (profesores.size()>=4){
            return true;
        }else{
             return false;
         }
    }

    //cantidad de personas que se mostraran segun lo que tenga el Arraylist
    public void cantidadRequerida(){
        if(profesores.size()== 4){
            for(int i = 0; i < profesores.size();i++){
                if(profesores.get(i).getSexo().equals("Home")){
                    hombres = hombres+1;
                }else{
                    mujeres = mujeres+1;
                }
            }if(generosmenores()== true){
                anadirlista();
            }else{
                alertaMensaje();
            }

        }if(profesores.size()== 5) {
            for (int i = 0; i < profesores.size(); i++) {
                if (profesores.get(i).getSexo().equals("Home")) {
                    hombres = hombres + 1;
                } else {
                    mujeres = mujeres + 1;
                }
            }if(generosmenores()== true){
                anadirlista();
            }else{
                alertaMensaje();
            }
        }if(profesores.size()== 6) {
            for (int i = 0; i < profesores.size(); i++) {
                if (profesores.get(i).getSexo().equals("Home")) {
                    hombres = hombres + 1;
                } else {
                    mujeres = mujeres + 1;
                }
            }if(generosmenores()== true){
                anadirlista();
            }else{
                alertaMensaje();
            }
        }if(profesores.size()>6){
            if(igualdadGenero()){
                while (profesoresElegidos.size()<6){
                    Random r = new Random();
                    int numero = r.nextInt(profesores.size());
                    String persona =profesores.get(numero).getSexo();
                    if(persona.equals("Home") && hombres<3){
                        hombres=hombres+1;
                        profesoresElegidos.add(profesores.get(numero));
                    }if(persona.equals("Dona") && mujeres<3){
                        mujeres=mujeres+1;
                        profesoresElegidos.add(profesores.get(numero));
                    }
                }
            }else{
                alertaMensaje();
            }
        }

    }
    public void anadirlista(){
        for(int i=0;i<profesores.size();i++){
            profesoresElegidos.add(profesores.get(i));
        }
    }

    public boolean generosmenores(){
        if(mujeres==2 && hombres==2){
            return true;
        }if(mujeres==1 && hombres==3 || mujeres==3 && hombres==1) {
            return true;
        }if(mujeres==2 && hombres==3 || mujeres==3 && hombres==2){
            return true;
        }if(mujeres==3 && hombres==3){
            return true;
        }else{
            return false;
        }

    }
    public void alertaMensaje(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error ");
        alert.setHeaderText("Error de personal");
        alert.setContentText("Esta plantilla no cumple la igualdad de genero");

        alert.showAndWait();
    }
    public void ponerEnPantalla(){
        rand = new Random();
        int numerodirec= rand.nextInt(profesoresElegidos.size());
        textDireccion.setText(profesoresElegidos.get(numerodirec).getNombre() + " " + profesoresElegidos.get(numerodirec).getApellido());
        profesoresElegidos.remove(numerodirec);
        textDireccion.setEditable(false);


        int numerosecre= rand.nextInt(profesoresElegidos.size());
        textSecretari.setText(profesoresElegidos.get(numerosecre).getNombre() + " " + profesoresElegidos.get(numerosecre).getApellido());
        profesoresElegidos.remove(numerosecre);
        textDireccion.setEditable(false);

        int numerocord= rand.nextInt(profesoresElegidos.size());
        textCoordinacio.setText(profesoresElegidos.get(numerocord).getNombre() + " " + profesoresElegidos.get(numerocord).getApellido());
        profesoresElegidos.remove(numerocord);
        textCoordinacio.setEditable(false);

        for(int i = 0; i<profesoresElegidos.size();i++) {
            items.add(profesoresElegidos.get(i).getNombre() +" "+ profesoresElegidos.get(i).getApellido());
        }
        listaCapEstudi.setItems(items);
    }

    public void resetVariables(){
        profesores.clear();
        profesoresElegidos.clear();
        hombres=0;
        mujeres=0;
        homes=0;
        dones=0;
        items.clear();
    }

    public void recargarVariables(){
        profesoresElegidos.clear();
        hombres=0;
        mujeres=0;
        homes=0;
        dones=0;
        items.clear();
    }

    @FXML
    public void recalcular(Event event) {
        recargarVariables();
        cantidadRequerida();
        for(int i= 0; i<profesoresElegidos.size();i++){
            System.out.println(profesoresElegidos.get(i));
        }
        ponerEnPantalla();
    }

    public void crearFichero(){
        if(!fichero.exists())
            try {
                // A partir del objeto File creamos el fichero físicamente
                if (fichero.createNewFile())
                    System.out.println("El fichero se ha creado correctamente");
                else
                    System.out.println("No ha podido ser creado el fichero");
            } catch (IOException ioe) {
                ioe.printStackTrace();
        }else{
            System.out.println("El fichero ya existe");
        }
    }
    public void escribirFichero(){
        if (fichero.exists()){
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));

                bw.write(" Director: " + textDireccion.getText());
                bw.newLine();
                bw.write(" Secretari: " + textSecretari.getText());
                bw.newLine();
                for(int i = 0; i<items.size(); i++){
                    bw.write(" Cap de estudi" +(i+1)+ ": " + items.get(i));
                    bw.newLine();
                }
                bw.write(" Coordinador: " + textCoordinacio.getText());
                System.out.println("se escribio");
                bw.newLine();
                bw.write("____________________________________________________");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("imposible escribir en el fichero");
            }
        }
    }

    private boolean igualdadGenero(){
        for (int i = 0; i<profesores.size();i++){
            if(profesores.get(i).getSexo().equals("Home")){
                homes=homes+1;
            }else{
                dones=dones+1;
            }
        }
        if(homes>3 && dones>3 ){
            return true;
        }else{
            return false;
        }
    }

    @FXML
    public void guardar(Event event) {
        crearFichero();
        escribirFichero();
    }
}
