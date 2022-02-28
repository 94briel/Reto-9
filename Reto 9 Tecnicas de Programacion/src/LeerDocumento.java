import java.nio.file.Path;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

public class LeerDocumento {
  public static void main(String[] args) {
    final String RUTA = ("C:\\Users\\Usuario\\Desktop\\Documento\\Bitcoin.csv");
    final String RUTA_NUEVA = ("C:\\Users\\Usuario\\Desktop\\Documento\\Bitcoin-new.txt");
    leerPorLineas(RUTA, RUTA_NUEVA);
  }
  public static void leerPorLineas(String RUTA, String RUTA_NUEVA) {
    String[] palabras;
    Path miRuta = Paths.get(RUTA);
    List<String> lineasArchivo;
    ArrayList<String> guardarArchivo = new ArrayList<>();
    double valorMayor = 0.0;
    double valorMenor = 99999.9999;
    double valorPromedio = 0;
    double totalSuma = 0;
    String fechaAlta = "";
    String fechaBaja = "";
    try {
      lineasArchivo = Files.readAllLines(miRuta);
      for (String lineaActual : lineasArchivo) {
        palabras = lineaActual.split(",");
        try {
          double close = Double.parseDouble(palabras[4]);
          double open =  Double.parseDouble(palabras[1]);
          if (close < 30000) {
            guardarArchivo.add(palabras[0] + "   " + "Muy Bajo" + " " + palabras[4]);
          } else if (close >= 30000 && close < 40000) {
            guardarArchivo.add(palabras[0] + "   " + "Bajo" + " " + palabras[4]);
          } else if (close >= 40000 && close < 50000) {
            guardarArchivo.add(palabras[0] + "   " + "Medio" + " " + palabras[4]);
          } else if (close >= 50000 && close < 60000) {
            guardarArchivo.add(palabras[0] + "   " + "Alto" + " " + palabras[4]);
          } else if (close >= 60000) {
            guardarArchivo.add(palabras[0] + "   " + "Muy Alto" + " " + palabras[4]);
          }
          double precioAlto = Double.parseDouble(palabras[2]);
          if (precioAlto > valorMayor) {
            valorMayor = precioAlto;
            fechaAlta = palabras[0];
          }
          double precioBajo = Double.parseDouble(palabras[3]);
          if (precioBajo < valorMenor) {
            valorMenor = precioBajo;
            fechaBaja = palabras[0];
          }
            totalSuma += open;
            valorPromedio = totalSuma / 365;

        } catch (NumberFormatException e) {
          System.out.println("Excepción Capturada Exitosamente");
        }

      }
      System.out.println("El valor Promedio fue: " + valorPromedio);
      System.out.println("El Valor de la Acción mas alta es: " + valorMayor + " En la Fecha: " + fechaAlta);
      System.out.println("El Valor de la Acción mas baja es: " + valorMenor + " En la Fecha: " + fechaBaja);
    } catch (IOException e) {
      System.out.println("Hubo un error al acceder el archivo: " + e.getMessage());
    }
    try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_NUEVA))) {
      for (String linea : guardarArchivo) {
        escritor.println(linea);
      }
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}

