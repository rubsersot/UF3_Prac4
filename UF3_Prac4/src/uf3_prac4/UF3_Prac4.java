package uf3_prac4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

public class UF3_Prac4 {

    private static class Clients {

        int codi;
        String nom;
        String cognoms;
        int diaNaixement;
        int mesNaixement;
        int anyNaixement;
        String adrecaPostal;
        String email;
        boolean vip;
    }

    public static Scanner scan = new Scanner(System.in);
    static final String NOM_FITX_BIN = "clients.bin";
    static final String NOM_FITX_TEMP = "temporal.bin";
    static final String NOM_FITX_INDEX = "clients_index.bin";
    static final String INDEX_ORDENAT = "clients_index_ordenat.bin";
    static final String INDEX_ORDENAT_TEMP = "clients_index_ordenat_temp.bin";
    static final String DEMANAR_POSICIO = "Introdueix la posició del client (pos inicial = 0): ";
    static final String DEMANAR_CODI = "Introdueix el codi: ";
    static final int LONG_REG = 12;

    public static void main(String[] args) {
        Utils.AbrirFichero(NOM_FITX_BIN, true);

        mostrarMenu();

        int opcio = Utils.LlegirInt();
        gestionarOpcions(opcio);

        scan.close();
    }

    private static void gestionarOpcions(int opcio) {
        while (opcio != 0) {
            switch (opcio) {
                case 1:
                    altaClient(NOM_FITX_BIN);
                    actualitzarIndex();
                    break;
                case 2:
                    consultarClientPosicio();
                    break;
                case 3:
                    consultarClientCodi();
                    break;
                case 4:
                    modificarClient();
                    actualitzarIndex();
                    break;
                case 5:
                    esborrarClient();
                    actualitzarIndex();
                    break;
                case 6:
                    llistarClients();
                    break;
                case 7:
                    posicioDirecte();
                    break;
                case 8:
                    codiDirecte();
                    break;
                case 9:
                    esborrarDirecte();
                    actualitzarIndex();
                    break;
                case 10:
                    modificarDirecte();
                    actualitzarIndex();
                    break;
                case 11:
                    ordenarClients();
                    llistarPerCodi();
                    break;
                default:
                    System.out.println("ERROR, opció no vàlida");
                    break;
            }

            mostrarMenu();
            opcio = Utils.LlegirInt();
        }
    }

    private static void mostrarMenu() {
        System.out.println("Menu");
        System.out.println("0. Sortir del programa");
        System.out.println("1. Alta d'un client");
        System.out.println("2. Consulta d'un client per posició");
        System.out.println("3. Consulta d'un client per codi");
        System.out.println("4. Modificar un client");
        System.out.println("5. Esborrar un client");
        System.out.println("6. Llistar tots els clients");
        System.out.println("7. Accedir de forma directa per posició");
        System.out.println("8. Accedir de forma directa per codi");
        System.out.println("9. Esborrar registre sense reconstruir");
        System.out.println("10. Modificar registre sense reconstruir");
        System.out.println("11. Llistar els clients ordenats per codi");
        System.out.print("Introdueix una opció: ");
    }

    private static void altaClient(String nomFitxer) {
        Clients client = new Clients();
        demanarDades(client);
        afegirDades(client, nomFitxer);
    }

    private static boolean existeixClient(int codi) {
        boolean existeix = false;
        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_BIN, true);
        Clients cli = leerCodigo(dis);
        leerCliente(dis, cli);
        while (cli != null && !existeix) {
            if (cli.codi == codi) {
                existeix = true;
            } else {
                cli = leerCodigo(dis);
                leerCliente(dis, cli);
            }
        }
        Utils.CerrarFicheroBinario(dis);
        return existeix;
    }

    private static void afegirDades(Clients client, String nomFitxer) {
        try {
            DataOutputStream dos = Utils.AbrirFicheroEscrituraBinario(nomFitxer, true, true);
            dos.writeInt(client.codi);
            dos.writeUTF(client.nom);
            dos.writeUTF(client.cognoms);
            dos.writeInt(client.diaNaixement);
            dos.writeInt(client.mesNaixement);
            dos.writeInt(client.anyNaixement);
            dos.writeUTF(client.adrecaPostal);
            dos.writeUTF(client.email);
            dos.writeBoolean(client.vip);
            Utils.CerrarFicheroBinario(dos);
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void actualitzarIndex() {
        DataOutputStream dos = Utils.AbrirFicheroEscrituraBinario(NOM_FITX_INDEX, true, false);
        RandomAccessFile raf = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "rw");
        try {
            long posicio = raf.getFilePointer();
            while (posicio < raf.length()) {
                int codi = raf.readInt();
                dos.writeInt(codi);
                dos.writeLong(posicio);
                leerResto(raf);
                posicio = raf.getFilePointer();
            }

        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.CerrarFicheroBinario(dos);
        Utils.cerrarAccesoDirecto(raf);
    }

    private static void leerResto(RandomAccessFile raf) {
        try {
            raf.readUTF();
            raf.readUTF();
            raf.readInt();
            raf.readInt();
            raf.readInt();
            raf.readUTF();
            raf.readUTF();
            raf.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void demanarDades(Clients client) {
        client.codi = Utils.LlegirInt(DEMANAR_CODI);
        boolean valid = false;
        while (!valid) {
            if (existeixClient(client.codi)) {
                client.codi = Utils.LlegirInt("ERROR! el client amb aquest codi ja existeix torna a introduir-lo: ");
            } else {
                System.out.print("Introdueix el nom: ");
                client.nom = scan.nextLine();
                System.out.print("Introdueix els cognoms: ");
                client.cognoms = scan.nextLine();
                client.diaNaixement = Utils.LlegirInt("Introdueix el dia de naixement: ");
                client.mesNaixement = Utils.LlegirInt("Introdueix el mes de naixement: ");
                client.anyNaixement = Utils.LlegirInt("Introdueix l'any de naixement: ");
                System.out.print("Introdueix l'adreça postal: ");
                client.adrecaPostal = scan.nextLine();
                System.out.print("Introdueix l'email: ");
                client.email = scan.nextLine();
                client.vip = llegirVip();
                valid = true;
            }
        }
    }

    private static boolean llegirVip() {
        boolean vip = false;
        boolean valid = false;
        System.out.println("Introdueix si és VIP (si/no): ");
        String input = scan.nextLine();
        while (!valid) {
            switch (input) {
                case "si":
                    vip = true;
                    valid = true;
                    break;
                case "no":
                    vip = false;
                    valid = true;
                    break;
                default:
                    System.out.print("ERROR, entrada no vàlida, introdueix (si/no): ");
                    input = scan.nextLine();
                    break;
            }
        }

        return vip;
    }

    private static void consultarClientPosicio() {
        int posicio = Utils.LlegirInt(DEMANAR_POSICIO);
        int contador = 0;
        boolean trobat = false;

        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_BIN, true);
        Clients cli = leerCodigo(dis);
        leerCliente(dis, cli);
        while (cli != null && !trobat) {
            if (contador == posicio) {
                mostrarDades(cli);
                trobat = true;
            } else {
                ++contador;
                cli = leerCodigo(dis);
                leerCliente(dis, cli);
            }
        }
        Utils.CerrarFicheroBinario(dis);
    }

    private static void posicioDirecte() {
        int posicio = Utils.LlegirInt(DEMANAR_POSICIO);
        RandomAccessFile raf = Utils.AbrirAccesoDirecto(NOM_FITX_INDEX, "r");
        try {
            long posIndice = posicio * LONG_REG;
            raf.seek(posIndice);
            raf.readInt();
            long posFinal = raf.readLong();
           
            RandomAccessFile raf2 = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "r");
            Utils.moverPuntero(raf2, posFinal);
            Clients cli = leerCodigo(raf2);
            leerCliente(raf2, cli);
            mostrarDades(cli);
           
            Utils.cerrarAccesoDirecto(raf);
            Utils.cerrarAccesoDirecto(raf2);
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void consultarClientCodi() {
        int codi = Utils.LlegirInt("Introdueix el codi del client: ");
        boolean trobat = false;
        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_BIN, true);
        Clients cli = leerCodigo(dis);
        leerCliente(dis, cli);
        while (cli != null && !trobat) {
            if (cli.codi == codi) {
                mostrarDades(cli);
                trobat = true;
            } else {
                cli = leerCodigo(dis);
                leerCliente(dis, cli);
            }
        }
        Utils.CerrarFicheroBinario(dis);
    }

    private static void codiDirecte() {
        int codi = Utils.LlegirInt(DEMANAR_CODI);
        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_INDEX, true);
        RandomAccessFile raf = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "r");
        try {
            int codiBuscar = dis.readInt();
            boolean trobat = false;
            long posicio;
            if (existeixClient(codi)) {
                while (!trobat) {
                    if (codiBuscar == codi) {
                        trobat = true;
                        posicio = dis.readLong();
                        Utils.moverPuntero(raf, posicio);
                        Clients cli;
                        cli = leerCodigo(raf);
                        leerCliente(raf, cli);
                        mostrarDades(cli);
                    } else {
                        dis.readLong();
                        codiBuscar = dis.readInt();
                    }
                }
            } else {
                System.out.println("No s'ha pogut trobar un client amb aquest codi");
            }

        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.CerrarFicheroBinario(dis);
        Utils.cerrarAccesoDirecto(raf);
    }

    private static void modificarClient() {
        int codi = Utils.LlegirInt(DEMANAR_CODI);

        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_BIN, true);
        Clients cli = leerCodigo(dis);
        leerCliente(dis, cli);
        while (cli != null) {
            if (cli.codi == codi) {
                mostrarDades(cli);
                altaClient(NOM_FITX_TEMP);

            } else {
                afegirDades(cli, NOM_FITX_TEMP);
            }
            cli = leerCodigo(dis);
            leerCliente(dis, cli);
        }
        Utils.CerrarFicheroBinario(dis);
        Utils.BorrarFichero(NOM_FITX_BIN);
        Utils.RenombrarFichero(NOM_FITX_TEMP, NOM_FITX_BIN);
        Utils.BorrarFichero(NOM_FITX_TEMP);
        Utils.CerrarFicheroBinario(dis);
    }

    private static void esborrarClient() {
        int codi = Utils.LlegirInt(DEMANAR_CODI);

        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_BIN, true);
        Clients cli = leerCodigo(dis);
        leerCliente(dis, cli);
        while (cli != null) {
            if (cli.codi != codi) {
                afegirDades(cli, NOM_FITX_TEMP);
            }
            cli = leerCodigo(dis);
            leerCliente(dis, cli);
        }
        Utils.CerrarFicheroBinario(dis);
        Utils.BorrarFichero(NOM_FITX_BIN);
        Utils.RenombrarFichero(NOM_FITX_TEMP, NOM_FITX_BIN);
        Utils.BorrarFichero(NOM_FITX_TEMP);
        Utils.CerrarFicheroBinario(dis);
    }

    private static void modificarDirecte() {
        int codi = Utils.LlegirInt(DEMANAR_CODI);
        RandomAccessFile raf = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "rw");
        if (existeixClient(codi)) {
            long posicio = buscarPosicion(codi);
            Utils.moverPuntero(raf, posicio);
            Clients cli = new Clients();
            demanarDades(cli);
            escriureClient(raf, cli);
            Utils.cerrarAccesoDirecto(raf);
        } else {
            System.out.println("No s'ha trobat un client amb aquest codi");
        }
        Utils.cerrarAccesoDirecto(raf);
    }

    private static void esborrarDirecte() {
        try {
            int codi = Utils.LlegirInt(DEMANAR_CODI);
            RandomAccessFile raf = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "rw");
            RandomAccessFile raf2 = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "r");
            if (existeixClient(codi)) {
                long posicio = buscarPosicion(codi);
                Utils.moverPuntero(raf, posicio);
                Utils.moverPuntero(raf2, posicio);
                //Saltem el client actual, el segon punter el volem un client avançat
                Clients cli = leerCodigo(raf2);
                leerCliente(raf2, cli);

                cli = leerCodigo(raf2);
                leerCliente(raf2, cli);
                while (cli != null) {
                    escriureClient(raf, cli);
                    cli = leerCodigo(raf2);
                    leerCliente(raf2, cli);
                }
                long pos_final = raf.getFilePointer();
                raf.setLength(pos_final);

            } else {
                System.out.println("No s'ha trobat un client amb aquest codi");
            }
            Utils.cerrarAccesoDirecto(raf);
            Utils.cerrarAccesoDirecto(raf2);
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void escriureClient(RandomAccessFile raf, Clients cli) {
        try {
            raf.writeInt(cli.codi);
            raf.writeUTF(cli.nom);
            raf.writeUTF(cli.cognoms);
            raf.writeInt(cli.diaNaixement);
            raf.writeInt(cli.mesNaixement);
            raf.writeInt(cli.anyNaixement);
            raf.writeUTF(cli.adrecaPostal);
            raf.writeUTF(cli.email);
            raf.writeBoolean(cli.vip);
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static long buscarPosicion(int codiBuscar) {
        long posicio = -1;
        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_INDEX, true);
        boolean trobat = false;
        try {
            while (!trobat) {
                int codi = dis.readInt();
                if (codi == codiBuscar) {
                    posicio = dis.readLong();
                    trobat = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.CerrarFicheroBinario(dis);
       
        return posicio;
    }

    private static Clients leerCodigo(RandomAccessFile raf) {
        Clients cli = new Clients();

        try {
            cli.codi = raf.readInt();
        } catch (IOException ex) {
            cli = null;
        }
        return cli;
    }

    private static Clients leerCodigo(DataInputStream dis) {
        Clients cli = new Clients();

        try {
            cli.codi = dis.readInt();
        } catch (IOException ex) {
            cli = null;
        }
        return cli;
    }

    private static void leerCliente(RandomAccessFile raf, Clients cli) {
        try {
            cli.nom = raf.readUTF();
            cli.cognoms = raf.readUTF();
            cli.diaNaixement = raf.readInt();
            cli.mesNaixement = raf.readInt();
            cli.anyNaixement = raf.readInt();
            cli.adrecaPostal = raf.readUTF();
            cli.email = raf.readUTF();
            cli.vip = raf.readBoolean();
        } catch (IOException ex) {
            cli = null;
        }
    }

    private static void leerCliente(DataInputStream dis, Clients cli) {
        try {
            cli.nom = dis.readUTF();
            cli.cognoms = dis.readUTF();
            cli.diaNaixement = dis.readInt();
            cli.mesNaixement = dis.readInt();
            cli.anyNaixement = dis.readInt();
            cli.adrecaPostal = dis.readUTF();
            cli.email = dis.readUTF();
            cli.vip = dis.readBoolean();

        } catch (IOException ex) {
            cli = null;
        }
    }

    private static void mostrarDades(Clients cli) {
        System.out.println("Codi: " + cli.codi);
        System.out.println("Nom: " + cli.nom);
        System.out.println("Cognoms: " + cli.cognoms);
        System.out.println("Data de Naixement (DD/MM/YYYY): " + cli.diaNaixement + "/"
                + cli.mesNaixement + "/" + cli.anyNaixement);
        System.out.println("Adreça postal: " + cli.adrecaPostal);
        System.out.println("E-mail: " + cli.email);
        boolean vip = cli.vip;
        System.out.print("VIP: ");
        if (vip) {
            System.out.println("Si");
        } else {
            System.out.println("No");
        }
    }

    private static void llistarClients() {
        DataInputStream dis = Utils.AbrirFicheroLecturaBinario(NOM_FITX_BIN, true);
        Clients cli = leerCodigo(dis);
        leerCliente(dis, cli);
        int contador = 1;
        while (cli != null) {
            System.out.println("Client " + contador);
            mostrarDades(cli);
            ++contador;
            cli = leerCodigo(dis);
            leerCliente(dis, cli);
        }

        Utils.CerrarFicheroBinario(dis);
    }

    private static void llistarPerCodi() {
       RandomAccessFile raf = Utils.AbrirAccesoDirecto(NOM_FITX_BIN, "r");
       DataInputStream dis = Utils.AbrirFicheroLecturaBinario(INDEX_ORDENAT, true);
        try {
            while(dis.available() > 0){
                dis.readInt();
                long posicio = dis.readLong();
                raf.seek(posicio);
                Clients cli = leerCodigo(raf);
                leerCliente(raf, cli);
                mostrarDades(cli);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private static void ordenarClients(){
        DataInputStream disIndex = Utils.AbrirFicheroLecturaBinario(NOM_FITX_INDEX, true);
        DataOutputStream dosOrdenat = Utils.AbrirFicheroEscrituraBinario(INDEX_ORDENAT, true, false);
        
        try {
            int codi = disIndex.readInt();
            long posicio = disIndex.readLong();
            dosOrdenat.writeInt(codi);
            dosOrdenat.writeLong(posicio);
            while(disIndex.available() > 0){
                codi = disIndex.readInt();
                posicio = disIndex.readLong();
                DataInputStream disOrdenat = Utils.AbrirFicheroLecturaBinario(INDEX_ORDENAT, true);
                DataOutputStream dosTemporal = Utils.AbrirFicheroEscrituraBinario(INDEX_ORDENAT_TEMP, true, true);
                boolean trobat = false;
                while(disOrdenat.available() > 0){
                    int codiComparar = disOrdenat.readInt();
                    if(codi < codiComparar && !trobat){
                        dosTemporal.writeInt(codi);
                        dosTemporal.writeLong(posicio);
                        trobat = true;
                    }
                    
                    dosTemporal.writeInt(codiComparar);
                    dosTemporal.writeLong(disOrdenat.readLong());
                }
                if(!trobat){
                    dosTemporal.writeInt(codi);
                    dosTemporal.writeLong(posicio);
                }
                
                Utils.CerrarFicheroBinario(dosTemporal);
                Utils.CerrarFicheroBinario(disOrdenat);
                Utils.BorrarFichero(INDEX_ORDENAT);
                Utils.RenombrarFichero(INDEX_ORDENAT_TEMP, INDEX_ORDENAT);
                
            }
            Utils.CerrarFicheroBinario(dosOrdenat);
            Utils.CerrarFicheroBinario(disIndex);
            
        } catch (IOException ex) {
            Logger.getLogger(UF3_Prac4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
