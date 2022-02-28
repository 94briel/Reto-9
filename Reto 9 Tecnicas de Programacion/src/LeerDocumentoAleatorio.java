import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.WRITE;


public class LeerDocumentoAleatorio {
  public static void main(String[] args) throws IOException {
    final String RUTA = ("C:\\Users\\Usuario\\Desktop\\Documento\\Bitcoin.csv");
    final String RUTA_NUEVA = ("C:\\Users\\Usuario\\Desktop\\Documento\\Bitcoin-newaleatorio.txt");
    leerAleatorio(RUTA, RUTA_NUEVA);
  }
  public static void leerAleatorio(String RUTA, String RUTA_NUEVA) {
    String[] documento;

    int posicionInicial = 7;
    double valorMayor = 0.0;
    double valorMenor = 99999.9999;
    double valorPromedio = 0;
    double totalSuma = 0;
    String fechaAlta = "";
    String fechaBaja = "";


    Path miRuta = Paths.get(RUTA);
    Path miRuta2 = Paths.get(RUTA_NUEVA);

    List<String> lineasArchivo;
    ArrayList<String> guardarArchivo = new ArrayList<>();


    try {
      lineasArchivo = Files.readAllLines(miRuta);

      int numBytes = RUTA.getBytes().length;
      int byteInicial = numBytes * posicionInicial;

      FileChannel canal = FileChannel.open(miRuta2, WRITE);
      canal.position(byteInicial);
      ByteBuffer buffer = ByteBuffer.allocate(byteInicial);

      do {
        canal.read(buffer);
      } while (buffer.hasRemaining());

      for (String lineaActual : lineasArchivo) {
        documento = lineaActual.split(",");
        try {
          double close = Double.parseDouble(documento[3]);
          double open =  Double.parseDouble(documento[1]);
          if (close < 30000) {
            guardarArchivo.add(documento[0] + "   " + "Muy Bajo" + " " + documento[4]);
          } else if (close >= 30000 && close < 40000) {
            guardarArchivo.add(documento[0] + "   " + "Bajo" + " " + documento[4]);
          } else if (close >= 40000 && close < 50000) {
            guardarArchivo.add(documento[0] + "   " + "Medio" + " " + documento[4]);
          } else if (close >= 50000 && close < 60000) {
            guardarArchivo.add(documento[0] + "   " + "Alto" + " " + documento[4]);
          } else if (close >= 60000) {
            guardarArchivo.add(documento[0] + "   " + "Muy Alto" + " " + documento[4]);
          }
          double precioAlto = Double.parseDouble(documento[2]);
          if (precioAlto > valorMayor) {
            valorMayor = precioAlto;
            fechaAlta = documento[0];
          }
          double precioBajo = Double.parseDouble(documento[3]);
          if (precioBajo < valorMenor) {
            valorMenor = precioBajo;
            fechaBaja = documento[0];
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
      System.out.println("Error " + e.getMessage());
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
