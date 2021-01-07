package inventario.telefonos;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class InventarioTelefonos {

    public static void main(String[] args) {
        int opcion, filaMatriz = 0;
        String ruta, inventario[][] = new String[100][3], salida;
        JTextArea pantalla = new JTextArea();
        //Ingreso de la ruta del archivo de texto
        do {
            ruta = JOptionPane.showInputDialog("Bienvenido a su sistema de inventario de telefonos\nIngrese la ruta en donde se encuentra su archivo de inventarios.");
        } while (ruta.equals("") || ruta.isEmpty());

        do {
            //Cargar los dato del archivo de texto
            try {
                String linea;
                salida = "Posicion\tMarca\tPrecio\tStock\n";
                BufferedReader readerb = new BufferedReader(new FileReader(ruta));
                filaMatriz = 0;
                while ((linea = readerb.readLine()) != null) {
                    salida += (filaMatriz + 1) + "\t";
                    String lineas[] = linea.split("-");
                    for (int i = 0; i < 3; i++) {
                        salida += lineas[i] + "\t";
                        inventario[filaMatriz][i] = lineas[i];
                    }
                    filaMatriz++;
                    salida += "\n";
                }
                pantalla.setText(salida);
                readerb.close();
            } catch (IOException | HeadlessException ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error\nDescripcion del error:\n" + ex.getMessage());
            }

            //Menu principal del programa
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de su opcion a elegir\n1 - Ver inventario general"
                    + "\n2 - Agregar telefono a inventario\n3 - Vender telefono\nOtro numero para salir"));
            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null, pantalla);

                    break;
                case 2:
                    //Menu para agregar stock o agregar nuevo telefono
                    int opcionAgregar;
                    do {
                        opcionAgregar = Integer.parseInt(JOptionPane.showInputDialog("Que desea hacer\n1 - Agregar inventario a un telefono\n2 - Agregar nuevo telefono"));
                    } while (opcionAgregar < 1 || opcionAgregar > 2);
                    switch (opcionAgregar) {
                        case 1:
                            //Modificar stock en la matriz
                            int posicion,
                             precio;
                            do {
                                posicion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la posicion del telefono para cambiar su stock"));
                            } while (posicion < 1 || posicion > filaMatriz);
                            do {
                                precio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuveo numero de stock del telefono Marca: " + inventario[posicion - 1][0]));
                            } while (precio <= 0);
                            inventario[posicion - 1][2] = "" + precio;
                            break;
                        case 2:
                            //Agregar nuevo registro en la matriz
                            String nTelefono = "";
                            double nPrecio;
                            int nStock;
                            do {
                                nTelefono = JOptionPane.showInputDialog("Ingrese el nombre del nuevo telefono");
                            } while (nTelefono.isEmpty());
                            inventario[filaMatriz][0] = nTelefono;
                            do {
                                nPrecio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del nuevo telefono\nRecuerde debe ser mayor a cero"));
                            } while (nPrecio <= 0);
                            inventario[filaMatriz][1] = "" + nPrecio;
                            do {
                                nStock = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Stock del nuevo telefono\nRecuerde debe ser mayor a cero"));
                            } while (nStock <= 0);
                            inventario[filaMatriz][2] = "" + nStock;
                            filaMatriz++;
                            break;
                    }
                    //modificar el archivo de texto
                    try {
                        BufferedWriter writterb = new BufferedWriter(new FileWriter(ruta));
                        writterb.write("");
                        for (int i = 0; i < filaMatriz; i++) {
                            writterb.write(inventario[i][0] + "-" + inventario[i][1] + "-" + inventario[i][2]);
                            writterb.newLine();
                        }
                        writterb.close();
                    } catch (IOException | HeadlessException e) {
                        JOptionPane.showMessageDialog(null, "Se ha producido un error\nDescripcion del error:\n" + e.getMessage());
                    }
                    break;
                case 3:
                    //Vender telefono
                    //Validar si existe registro de telefonos para evitar bucle infinito
                    if (filaMatriz > 0) {
                        int posicion;
                        //Validar seleccion correcta de telefono
                        do {
                            posicion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la posicion del telefono a comprar"));
                        } while (posicion < 1 || posicion > filaMatriz);
                        //Validar si se posee stock del telefono seleccionado para evitar stock negativo
                        if (Integer.parseInt(inventario[posicion - 1][2]) > 0) {
                            inventario[posicion - 1][2] = (Integer.parseInt(inventario[posicion - 1][2]) - 1) + "";
                            try {
                                BufferedWriter writterb = new BufferedWriter(new FileWriter(ruta));
                                writterb.write("");
                                for (int i = 0; i < filaMatriz; i++) {
                                    writterb.write(inventario[i][0] + "-" + inventario[i][1] + "-" + inventario[i][2]);
                                    writterb.newLine();
                                }
                                writterb.close();
                            } catch (IOException | HeadlessException e) {
                                JOptionPane.showMessageDialog(null, "Se ha producido un error\nDescripcion del error:\n" + e.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "El telefono seleccionado no cuenta con stock por el momento");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encuentran registros de inventario");
                    }
                    break;
                default:
                    //Saludo de despedida para indicar en donde se guarda el documento de texto
                    JOptionPane.showMessageDialog(null, "Saliendo del programa de inventarios\nSu informacion esta a salvo en un archivo de texto en la siguiente direccion:\n"
                            + ruta);
            }
        } while (opcion >= 1 && opcion <= 3);

    }

}
