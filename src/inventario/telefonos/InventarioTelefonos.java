package inventario.telefonos;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class InventarioTelefonos {

    public static void main(String[] args) {
        int opcion;
        String ruta, inventario[][] = new String[100][3];
        JTextArea pantalla = new JTextArea();
        do {
            ruta = JOptionPane.showInputDialog("Bienvenido a su sistema de inventario de telefonos\nIngrese la ruta en donde se encuentra su archivo de inventarios.");
        } while (ruta.equals("") || ruta.isEmpty());
        File file = new File(ruta);

        do {
            try {
                String linea, salida = "Marca\tPrecio\tStock\n";
                FileReader readerf = new FileReader(file);
                try {
                    BufferedReader readerb = new BufferedReader(readerf);
                    int temp = 0;
                    while ((linea = readerb.readLine()) != null) {
                        String lineas[] = linea.split("-");
                        for (int i = 0; i < 3; i++) {
                            salida += lineas[i] + "\t";
                            inventario[temp][i] = lineas[i];
                        }
                        temp++;
                        salida += "\n";
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Se ha producido un error\nDescripcion del error:\n" + ex.getMessage());
                }
                pantalla.setText(salida);
            } catch (HeadlessException | IOException ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error\nDescripcion del error:\n" + ex.getMessage());
            }
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de su opcion a elegir\n1 - Ver inventario general"
                    + "\n2 - Agregar telefono a inventario\n3 - Vender telefono\n4 - Ver caja general"));

            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null, pantalla);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa de inventarios\nSu informacion esta a salvo en un archivo de texto en la siguiente direccion:\n"
                            + file.getPath());
            }

        } while (opcion >= 1 && opcion <= 4);

    }

}
