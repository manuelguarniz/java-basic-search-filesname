import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa libro que quieres buscar:");
        String fileToSearch = scanner.nextLine();
        //searchFiles(fileToSearch);
        //searchLocal(fileToSearch);
        searchURL(fileToSearch);
    }

    private static void searchFiles(String nameToSearch) {
        boolean searched = false;
        List<String> list = new ArrayList<>();
        File directory = new File("files");
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No hay archivos");
            return;
        }

        for (File file : files) {
            list.add(file.getName());
        }

        for (String fileName : list) {
            if (fileName.equalsIgnoreCase(nameToSearch)) {
                searched = true;
                break;
            }
        }

        if (searched) {
            System.out.println("Nombre encontrado: " + nameToSearch);
        } else {
            System.out.println("No encontrado");
        }
    }

    private static void searchLocal(String nameToSearch) {
        boolean searched = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("datos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(nameToSearch)) {
                    searched = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        if (searched) {
            System.out.println("Nombre encontrado: " + nameToSearch);
        } else {
            System.out.println("No encontrado");
        }
    }

    private static void searchURL(String nameToSearch) {
        boolean searched = false;
        String textSearched = "";
        try {
            URL url = new URL("https://raw.githubusercontent.com/CalmRott7915/Nombres_Argentina/refs/heads/main/Nombres-Problema-Corregido-Sed.csv");
            InputStream input = url.openStream();
            String line = "";
            int byteData = input.read();

            while (byteData != -1) {
                char character = (char) byteData;

                if (byteData == 10) {
                    if (line.startsWith(nameToSearch)) {
                        searched = true;
                        textSearched = line;
                        break;
                    }

                    byteData = input.read();
                    line = "";
                    continue;
                } else {
                    line += character;
                }

                byteData = input.read();
            }
            input.close();
        } catch (MalformedURLException ex) {
            System.out.println("URL Invalida: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Contenido invalido: " + ex.getMessage());
        }

        if (searched) {
            System.out.println("Nombre encontrado (" + nameToSearch + "): " + textSearched);
        } else {
            System.out.println("No encontrado");
        }
    }
}
