/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Llibreria d'utilitats.
 * Utilitats per l'assignatura de Programació de 1r de DAM
 * @author rubsersot
 * @version 16-02-2023
 *
 */
public class Utils {
 
// <editor-fold defaultstate="collapsed" desc="Colors">
    
    /**
     * Reiniciar colors
     */
    public static final String RESET = "\033[0m";  // Text Reset
    //Regulars
    /**
     * Negre regular
     */
    public static final String BLACK = "\033[0;30m";   // BLACK
    /**
     * Vermell regular
     */
    public static final String RED = "\033[0;31m";     // RED
    /**
     * Verd regular
     */
    public static final String GREEN = "\033[0;32m";   // GREEN
    /**
     * Groc regular
     */
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    /**
     * Blau regular
     */
    public static final String BLUE = "\033[0;34m";    // BLUE
    /**
     * Púrpura regular
     */
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    /**
     * Cian regular
     */
    public static final String CYAN = "\033[0;36m";    // CYAN
    /**
     * Blanc regular
     */
    public static final String WHITE = "\033[0;37m";   // WHITE
    
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Implementació de LlegirInt()">
    private static Scanner scan = null;

    /**
     * Llegeix un nombre enter.
     * Comprovarà que l'entrada del escàner no es buida i llegirà un nombre enter.
     * @return el nombre enter llegit
     */
    public static int LlegirInt() {
        int result;

        if (scan == null) {
            scan = new Scanner(System.in);
        }
        result = LlegirInt(scan);

        return result;
    }

    /**
     * Mostra el missatge passat com a paràmetre i llegeix un nombre enter.
     * @param missatge el missatge que mostrarem per pantalla
     * @return el nombre enter llegit
     */
    public static int LlegirInt(String missatge) {
        int result;

        if (scan == null) {
            scan = new Scanner(System.in);
        }
        result = LlegirInt(scan, missatge);

        return result;
    }

    /**
     * Llegeix un nombre enter per una entrada d'escàner determinada que passem com a paràmetre
     * @param scan l'entrada de dades
     * @return el nombre enter llegit
     */
    public static int LlegirInt(Scanner scan) {
        return LlegirInt(scan, null);
    }
    
    /**
     * Mostra un missatge i llegeix entrades fins que troba un nombre vàlid.
     * Un nombre serà vàlid si és un enter i es troba entre dos valors que haurem
     * passat com a paràmetres
     * @param valorMin el valor mínim que volem que tingui el nombre
     * @param valorMax el valor màxim que volem que tingui el nombre
     * @return el nombre enter llegit que es trobarà entre els dos valors proporcionats
     */
    public static int LlegirInt(int valorMin, int valorMax) {
        int result = 0;
        boolean correcte = false;
        if (valorMin > valorMax) {
            mostrarErrorLect(2);
        } else {
            while (!correcte) {
                result = LlegirInt(scan);
                if (result < valorMin || result > valorMax) {
                    mostrarErrorLect(3);
                } else {
                    correcte = true;
                }
            }
        }
        return result;
    }

    /**
     * Mostra un missatge i llegeix entrades fins que troba un nombre vàlid.
     * Un nombre serà vàlid si és un enter i es troba entre dos valors que haurem
     * passat com a paràmetres
     * @param scan l'entrada de dades
     * @param missatge el missatge que mostrarem per pantalla
     * @param valorMin el valor mínim que volem que tingui el nombre
     * @param valorMax el valor màxim que volem que tingui el nombre
     * @return el nombre enter llegit que es trobarà entre els dos valors proporcionats
     */
    public static int LlegirInt(Scanner scan, String missatge, int valorMin, int valorMax) {
        int result = 0;
        boolean correcte = false;
        if (valorMin > valorMax) {
            mostrarErrorLect(2);
        } else {
            while (!correcte) {
                result = LlegirInt(scan, missatge);
                if (result < valorMin || result > valorMax) {
                    mostrarErrorLect(3);
                } else {
                    correcte = true;
                }
            }
        }
        return result;
    }

    /**
     * Mostra el missatge passat com a paràmetre i llegeix entrades d'escàner
     * fins que llegeix un nombre enter vàlid. En cas de llegir alguna cosa que
     * no sigui un nombre enter mostrarà un missatge d'error
     *
     * @param scan l'entrada de dades
     * @param missatge el missatge que mostrarem per pantalla
     * @return el nombre enter llegit
     */
    public static int LlegirInt(Scanner scan, String missatge) {
        boolean dadesCorrectes;
        int result = 0;
        do {
            if (missatge != null) {
                System.out.print(missatge);
            }
            dadesCorrectes = scan.hasNextInt();
            if (dadesCorrectes) {
                result = scan.nextInt();
            } else if (scan.hasNext()) {
                mostrarErrorLect(1);
                scan.next();
            }
        } while (!dadesCorrectes);

        return result;
    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Implementació de LlegirFloat()">
    /**
     * Llegeix un nombre decimal.
     * Comprova que l'entrada de dades no és buida i llegirà un nombre decimal
     * @return el nombre decimal llegit
     */
    public static float LlegirFloat() {
        float result;

        if (scan == null) {
            scan = new Scanner(System.in);
        }
        result = LlegirFloat(scan);

        return result;
    }

    /**
     * Mostra un missatge passat com a paràmetre i llegeix un nombre decimal.
     * @param missatge el missatge que mostrarem per pantalla
     * @return el nombre decimal llegit
     */
    public static float LlegirFloat(String missatge) {
        float result;

        if (scan == null) {
            scan = new Scanner(System.in);
        }
        result = LlegirFloat(scan, missatge);

        return result;
    }

    /**
     * Llegeix un nombre decimal per una entrada d'escàner determinada que passem com a paràmetre
     * @param scan l'entrada de dades
     * @return el nombre decimal llegit
     */
    public static float LlegirFloat(Scanner scan) {
        return LlegirFloat(scan, null);
    }

    /**
     * Mostra un missatge i llegeix entrades fins que troba un nombre vàlid.
     * Un nombre serà vàlid si és un decimal i es troba entre dos valors que haurem
     * passat com a paràmetres
     * @param scan l'entrada de dades
     * @param missatge el missatge que mostrarem
     * @param valorMin el valor mínim que volem que tingui el nombre
     * @param valorMax el valor màxim que volem que tingui el nombre
     * @return el nombre enter llegit que es trobarà entre els dos valors proporcionats
     */
    public static float LlegirFloat(Scanner scan, String missatge, float valorMin, float valorMax) {
        float result = 0;
        boolean correcte = false;
        if (valorMin > valorMax) {
            mostrarErrorLect(2);
        } else {
            while (!correcte) {
                result = LlegirInt(scan, missatge);
                if (result < valorMin || result > valorMax) {
                    mostrarErrorLect(3);
                } else {
                    correcte = true;
                }
            }
        }
        return result;
    }

    /**
     * Mostra el missatge passat com a paràmetre i llegeix entrades d'escàner
     * fins que llegeix un nombre decimal vàlid. En cas de llegir alguna cosa que
     * no sigui un nombre decimal mostrarà un missatge d'error
     *
     * @param scan l'entrada de dades
     * @param missatge el missatge que mostrarem
     * @return el nombre decimal llegit
     */
    public static float LlegirFloat(Scanner scan, String missatge) {
        boolean dadesCorrectes;
        float result = 0;
        do {
            if (missatge != null) {
                System.out.print(missatge);
            }
            dadesCorrectes = scan.hasNextFloat();
            if (dadesCorrectes) {
                result = scan.nextFloat();
            } else if (scan.hasNext()) {
                mostrarErrorLect(1);
                scan.next();
            }
        } while (!dadesCorrectes);

        return result;
    }

// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Dates">
    
    /**
     * Comprova si una data és vàlida.
     * Passem un dia, mes i any per paràmetres i comprovem si és una data vàlida
     * @param dia dia de la data
     * @param mes mes de la data
     * @param any any de la data
     * @return true si la data és vàlida i false si no ho és
     */
    public static boolean ComprovarData(int dia, int mes, int any){
        boolean resultat;
        //Transformem la data en un unic String
        String dataString = dia + "/" + mes + "/" + any;
        //Establim el format de dates
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        Date data = null;
        //Comprovem si és vàlida
        try{
            data = formatter.parse(dataString); 
            resultat = true;
        }
        catch(ParseException e){
            resultat = false;
        }
        return resultat;
    }
    
    /**
     * Comprova si un any és de traspàs.
     * Anys amb 366 dies. Són els anys divisibles entre 4 
     * i (no és divisible entre 100 o és divisible entre 400)
     * @param any any que volem comprovar
     * @return true si l'any és de traspàs i false si no ho és
     */
    public static boolean esBisiesto(int any){
        boolean resultat = false;
        if(any % 4 == 0 && (any % 100 != 0 || any % 400 == 0)){
            resultat = true;
        }
        return resultat;
    }
    
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Vectors">
    // <editor-fold defaultstate="collapsed" desc="Lectura">
    
    /**
     * Lectura d'un vector de nombres enters.
     * @param vectInt vector a omplir amb les dades que llegim
     */
    public static void LlegirVectorInt(int[] vectInt) {
        for (int i = 0; i < vectInt.length; ++i) {
            vectInt[i] = LlegirInt();
        }
    }

    /**
     * Lectura d'un vector de nombres decimals.
     * @param vectFloat vector a omplir amb les dades que llegim
     */
    public static void LlegirVectorFloat(float[] vectFloat) {
        for (int i = 0; i < vectFloat.length; ++i) {
            vectFloat[i] = LlegirFloat();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Escritura">
    
    /**
     * Mostrar per pantalla un vector de nombres enters.
     * @param vectInt el vector que volem mostrar
     */
    public static void EscriureVectorInt(int[] vectInt) {
        for (int i = 0; i < vectInt.length; ++i) {
            System.out.print(vectInt[i] + " ");
        }
        System.out.println();
    }

    /**
     * Mostrar per pantalla un vector de nombres decimals.
     * @param vectFloat el vector que volem mostrar
     */
    public static void EscriureVectorFloat(float[] vectFloat) {
        for (int i = 0; i < vectFloat.length; ++i) {
            System.out.print(vectFloat[i] + " ");
        }
        System.out.println();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Ordenació">
    
    
    /**
     * Ordenació d'un vector de nombres enters pel mètode de la bombolla.
     * @param vectInt el vector a ordenar
     */
    public static void bubbleSortVectInt(int[] vectInt){
        boolean ordenat = false;
        int ultim = vectInt.length-1;
        while(!ordenat){
            ordenat = true;
            for(int i = 0; i < ultim; ++i){
                if(vectInt[i] > vectInt[i+1]){
                    int aux = vectInt[i];
                    vectInt[i] = vectInt[i+1];
                    vectInt[i+1] = aux;
                    ordenat = false;
                }
            }
            --ultim;
        }
    }
    
    /**
     * Ordenació d'un vector de nombres enters pel mètode quicksort.
     * @param vector el vector a ordenar
     * @param inici posició des d'on ordenarem
     * @param fi posició fins a on ordenarem
     */
    public static void quickSort(int[] vector, int inici, int fi){
        if(inici < fi){
            int partIndex = particio(vector, inici, fi);
            quickSort(vector, inici, partIndex-1);
            quickSort(vector, partIndex+1, fi);
        }
    }
    
    private static int particio(int[] vector, int inici, int fi){
        int pivot = vector[fi];
        int i = (inici-1);
        
        for(int j = inici; j < fi; ++j){
            if(vector[j] <= pivot){
                ++i;
                
                int aux = vector[i];
                vector[i] =  vector[j];
                vector[j] = aux;
            }
        }
        
        int aux = vector[i+1];
        vector[i+1] = vector[fi];
        vector[fi] = aux;
        
        return i+1;
        
    }
    
    /**
     * Ordenació d'un vector de nombres enters pel metode de counting sort.
     * @param vector el vector a ordenar
     */
    public static void countingSort(int[] vector){
        //Vector auxiliar
        int[] aux = new int[vector.length+1];
        
        //Trobar número més gran al vector
        int max = trobarMesGran(vector);

        //Vector per comptar números
        int[] comptar = new int[max+1];
        
        //Comptem quans números de cada trobem
        for(int i = 0; i < vector.length; ++i){
            ++comptar[vector[i]];
        }
        
        //Guardem la compta acumulativa
        for(int i = 1; i <= max; ++i){
            comptar[i] += comptar[i-1];
        }
        
        //Busquem l'index de cada número al vector original en el vector de comptar
        //i anem colocant els números a l'auxiliar
        for(int i = vector.length-1; i >= 0; --i){
            aux[comptar[vector[i]] - 1] = vector[i];
            --comptar[vector[i]];
        }
        
        //Passem els números organitzats en l'auxiliar al vector original
        for(int i = 0; i < vector.length; ++i){
            vector[i] = aux[i];
        }
    }
    
    private static int trobarMesGran(int[] vector){
        int max = vector[0];
        for(int i = 1; i < vector.length; ++i){
            if(vector[i] > max){
                max = vector[i];
            }
        }
        return max;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Cerca">
    
    public static int MaxInt(int[] vector){
        int maxim = Integer.MIN_VALUE;
        for(int i = 0; i < vector.length; ++i){
            if(vector[i] > maxim){
                maxim = vector[i];
            }
        }
        return maxim;
    }
    
    // </editor-fold>
    
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Matrius">
    // <editor-fold defaultstate="collapsed" desc="Lectura">
    
    /**
     * Lectura d'una matriu de nombres enters.
     * @param matInt matriu a omplir amb les dades llegides
     */
    public static void LlegirMatriuInt(int[][] matInt) {
        for (int i = 0; i < matInt.length; ++i) {
            for (int j = 0; j < matInt[0].length; ++j) {
                LlegirInt();
            }
        }
    }

    /**
     * Lectura d'una matriu de nombres decimals.
     * @param matFloat matriu a omplir amb les dades llegides
     */
    public static void LlegirMatriuFloat(float[][] matFloat) {
        for (int i = 0; i < matFloat.length; ++i) {
            for (int j = 0; j < matFloat[0].length; ++j) {
                LlegirFloat();
            }
        }
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Escritura">
    
    /**
     * Mostrar per pantalla una matriu de nombres enters.
     * @param matInt la matriu que volem mostrar
     */
    public static void EscriureMatriuInt(int[][] matInt) {
        for (int i = 0; i < matInt.length; ++i) {
            for (int j = 0; j < matInt[0].length; ++j) {
                System.out.print(matInt[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Mostrar per pantalla una matriu de nombres decimals.
     * @param matFloat la matriu que volem mostrar
     */
    public static void EscriureMatriuFloat(float[][] matFloat) {
        for (int i = 0; i < matFloat.length; ++i) {
            for (int j = 0; j < matFloat[0].length; ++j) {
                System.out.print(matFloat[i][j]);
            }
            System.out.println();
        }
    }

    // </editor-fold>
    
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Fitxers">
    
    // <editor-fold defaultstate="collapsed" desc="Fitxers de text">
    
    /**
     * Funcion que abre un fichero y, opcionalmente, lo crea si no existe
     *
     * @param nomFichero Nombre del fichero a abrir
     * @param crear Si lo que queremos crear en el caso que no exista
     * @return File con el fichero que se ha abierto o null si no existe o no se
     * ha podido crear
     */
    public static File AbrirFichero(String nomFichero, boolean crear) {
        File result = new File(nomFichero);

        if (!result.exists()) {
            if (crear) {
                try {
                    result.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    result = null;
                }
            } else {
                result = null;
            }
        }

        return result;
    }
    
    /**
     * Abre un fichero para lectura
     *
     * @param nomFichero Nombre del fichero
     * @param crear Indica si queremos crear el fichero o no, en el caso que no
     * exista
     * @return BufferedReader apuntando al fichero
     */
    public static BufferedReader AbrirFicheroLectura(String nomFichero, boolean crear) {
        BufferedReader br = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el reader para poder leer el fichero¡
            FileReader reader;
            try {
                reader = new FileReader(f);
                // Buffered reader para poder leer más comodamente
                br = new BufferedReader(reader);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return br;
    }
    
    /**
     * Abre un fichero para escritura
     *
     * @param nomFichero Nombre del fichero
     * @param crear Indica si queremos crear el fichero o no, en el caso que no
     * exista
     * @param blnAnyadir true per afegir el text al final i false per substituir el text del fitxer
     * @return BufferedReader apuntando al fichero
     */
    public static PrintWriter AbrirFicheroEscritura(String nomFichero, boolean crear, boolean blnAnyadir) {
        PrintWriter pw = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileWriter writer;
            try {
                writer = new FileWriter(f, blnAnyadir);
                // PrintWriter para poder escribir más comodamente
                pw = new PrintWriter(writer);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return pw;
    }
    
    /**
     * Cierra el fichero
     *
     * @param br fichero a cerrar
     */
    public static void CerrarFichero(BufferedReader br) {
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cierra el fichero
     *
     * @param pw fichero a cerrar
     */
    public static void CerrarFichero(PrintWriter pw) {
        pw.flush();
        pw.close();
    }
    
    /**
     * Lee una linea del fichero
     *
     * @param br BufferedReader con el fichero a leer
     * @return String
     */
    public static String LeerLinea(BufferedReader br) {
        String linea = null;

        try {
            linea = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linea;
    }
    
    /**
     * Escriu una linea al fitxer.
     *
     * @param pw PrintWrite con el fichero a leer
     * @param linea Linea a escribir
     */
    public static void EscribirLinea(PrintWriter pw, String linea) {
        pw.println(linea);
    }
    
    public static void BorrarFichero(String filename) {
        File f = new File(filename);
        f.delete();
    }
    
    public static void RenombrarFichero(String filename_origen, String filename_final) {
        File f = new File(filename_origen);
        File f2 = new File(filename_final);
        f.renameTo(f2);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fitxers binaris">
    
    public static DataInputStream AbrirFicheroLecturaBinario(String nomFichero, boolean crear) {
        DataInputStream dis = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileInputStream reader;
            try {
                reader = new FileInputStream(f);
                // PrintWriter para poder escribir más comodamente
                dis = new DataInputStream(reader);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dis;
    }
    
    public static DataOutputStream AbrirFicheroEscrituraBinario(String nomFichero, boolean crear, boolean blnAnyadir) {
        DataOutputStream dos = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileOutputStream writer;
            try {
                writer = new FileOutputStream(f, blnAnyadir);
                // PrintWriter para poder escribir más comodamente
                dos = new DataOutputStream(writer);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dos;
    }
    
    public static void CerrarFicheroBinario(DataOutputStream dos) {
        try {
            dos.flush();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void CerrarFicheroBinario(DataInputStream dis) {
        try {
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Accés Aleatori">
    
    public static RandomAccessFile AbrirAccesoDirecto(String nomFitxer, String mode){
        RandomAccessFile raf = null;
        
        File f = AbrirFichero(nomFitxer, true);
        try {
            raf = new RandomAccessFile(nomFitxer, mode);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return raf;
    }
    
    public static void moverPuntero(RandomAccessFile raf, long posicionMover){
        try {
            raf.seek(posicionMover);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void cerrarAccesoDirecto(RandomAccessFile raf){
        try {
            raf.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // <editor-fold>
    
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Missatges d'errors">
    
    /**
     * Mostra un missatge d'error diferent segons el codi introduït.
     * Errors de lectura de dades
     * @param codi identificador del error
     */
    public static void mostrarErrorLect(int codi){
        switch (codi) {
            case 1:
                System.out.println(RED + "ERROR! entrada no vàlida, si us plau torna a introduir-la" + RESET);
                break;
            case 2:
                System.out.println(RED + "ERROR, el valor mínim no pot ser més gran que el valor màxim" + RESET);
                break;
            case 3:
                System.out.println(RED + "ERROR, el nombre llegit no es troba entre els valors establerts, si us plau torna a introduir-lo" + RESET);
                break;
            default:
                break;
        }
    }
    
// </editor-fold>
}
