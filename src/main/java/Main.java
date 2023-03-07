import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //Conexión con Mongo
        MongoClient mc = new MongoClient("localhost", 27017);
        //Conexión con la base de datos deseada
        MongoDatabase badminton = mc.getDatabase("badminton");


        // Creación de un torneo {
        System.out.println("\tTORNEOS");
        System.out.print("Introduzca un nombre a insertar: ");
        String torneo = sc.nextLine();
        System.out.print("Introduzca una fecha a insertar: ");
        String fecha = sc.nextLine();

        boolean flag = true;
        ArrayList<String> participantes = new ArrayList<String>();

        System.out.println("Introduzca los participantes: (salir = 0)");
        while (flag) {
            System.out.print("  Introduzca un nombre a insertar: ");
            String nombre = sc.nextLine();
            participantes.add(nombre);
            System.out.print("  Introduzca un dni a insertar: ");
            String dni = sc.nextLine();
            participantes.add(dni);
            System.out.print("  Introduzca una fecha de nacimiento a insertar: ");
            String fechaNac = sc.nextLine();
            participantes.add(fechaNac);

            //Creación de un usuario {
            Document documentUsuarios = new Document("nombre", nombre)
                    .append("dni", dni)
                    .append("fechaNacimiento", fechaNac);
            //Aquí se crea la colección
            MongoCollection<Document> usuarios = badminton.getCollection("usuarios");
            //Aquí se asocia la colección con los datos clave-valor
            usuarios.insertOne(documentUsuarios);
            // }
            System.out.print("Desea introducir otro participante? ");
            String opcion = sc.nextLine().toUpperCase();
            if (opcion.equals("NO")) {
                flag = false;
            }

        }

        boolean flag2 = true;
        ArrayList<String> partido = new ArrayList<String>();
        //Creacion de un partido {
        do {
            System.out.print("Introduzca la categoría del partido: ");
            String categoria = sc.nextLine();
            System.out.print("Introduzca el lugar del partido: ");
            String lugar = sc.nextLine();
            partido.add(lugar);
            System.out.print("Introduzca la fecha del partido: ");
            String fechaPartido = sc.nextLine();
            partido.add(fechaPartido);
            System.out.print("Introduzca la hora del partido: ");
            String hora = sc.nextLine();
            partido.add(hora);

            //Creación de un resultado de partido {
            ArrayList<String> resultados = new ArrayList<String>();
            System.out.print("Resultado: ");
            String resultado = sc.nextLine();
            resultados.add(resultado);
            System.out.print("Ganador: ");
            String ganador = sc.nextLine();
            resultados.add(ganador);
            System.out.print("Perdedor: ");
            String perdedor = sc.nextLine();
            resultados.add(perdedor);

            Document documentResultado = new Document("resultado", resultado)
                    .append("ganador", ganador)
                    .append("perdedor", perdedor);
            //Aquí se crea la colección
            MongoCollection<Document> resultadoP = badminton.getCollection("resultado_partido");
            //Aquí se asocia la colección con los datos clave-valor
            resultadoP.insertOne(documentResultado);
            // }

            Document documentPartidos = new Document("lugar", lugar)
                    .append("fechaPartido", fechaPartido)
                    .append("hora", hora)
                    .append("categoria", categoria)
                    .append("resultados", new Document("resultado", resultadoP));
            //Aquí se crea la colección
            MongoCollection<Document> partidos = badminton.getCollection("partidos");
            //Aquí se asocia la colección con los datos clave-valor
            partidos.insertOne(documentPartidos);

            System.out.print("Desea crear otro partido? ");
            String opcion2 = sc.nextLine().toUpperCase();
            if (opcion2.equals("NO")) {
                //Aquí se establecen los pares clave-valor
                Document documentTorneos = new Document("nombre", torneo)
                        .append("fecha", fecha)
                        .append("participantes", new Document("participante", participantes))
                        .append("partidos", new Document("partido", partidos));
                //Aquí se crea la colección
                MongoCollection<Document> torneos = badminton.getCollection("torneos");
                //Aquí se asocia la colección con los datos clave-valor
                torneos.insertOne(documentTorneos);
                flag2 = false;
                // }
            }
        } while (flag2);
        // }

        mc.close();
    }
}
