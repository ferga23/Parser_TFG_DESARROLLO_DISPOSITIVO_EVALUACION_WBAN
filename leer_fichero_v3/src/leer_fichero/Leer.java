package leer_fichero;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Leer {
	public static void main(String[] args) {
		final boolean VERBOSE = false;
		final boolean IMPRIMIR = true;
		int error = 0;
		try {
			ArrayList<Medida> al = new ArrayList<Medida>();
			File fichero_entrada = Leer.cargarArchivo();
			File fichero_salida;
			Scanner sc = new Scanner(fichero_entrada);
			Medida medida;
			String st, pw, pwtx = null;

			try {
				pw = fichero_entrada.getName().replaceAll("[^-¿?0-9]+", "");
				if (Integer.valueOf(pw) > 10)
					pwtx = "-".concat(pw);
				else
					pwtx = pw;
			} catch (Exception e) {
				pwtx = null;
			}

			while (sc.hasNextLine()) {
				int j, k = 0;
				medida = new Medida();
				st = new String(sc.nextLine());
				if (st.contains("##RX")) {
					k = st.indexOf("|1l");
					if (k < 6) {
						JOptionPane.showMessageDialog(null,
								"ERROR: El formato del fichero de entrada, no es el esperado...", "¡¡¡ERROR!!!",
								JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					} else if (k == 6) {
						medida.setPKT_ID(Integer.valueOf(st.substring(5, 9).replace("|1l", "")));
						j = st.indexOf("-");
						if (j < 0) {
							j = st.indexOf(".");
							medida.setRSSI(Float.valueOf(st.substring(j - 2, j + 2)));
						} else {
							medida.setRSSI(Float.valueOf(st.substring(j, j + 6)));
						}
					} else if (k == 7) {
						medida.setPKT_ID(Integer.valueOf(st.substring(5, 10).replace("|1l", "")));
						j = st.indexOf("-");
						if (j < 0) {
							j = st.indexOf(".");
							medida.setRSSI(Float.valueOf(st.substring(j - 2, j + 2)));
						} else {
							medida.setRSSI(Float.valueOf(st.substring(j, j + 6)));
						}
					} else if (k == 8) {
						medida.setPKT_ID(Integer.valueOf(st.substring(5, 11).replace("|1l", "")));
						j = st.indexOf("-");
						if (j < 0) {
							j = st.indexOf(".");
							medida.setRSSI(Float.valueOf(st.substring(j - 2, j + 2)));
						} else {
							medida.setRSSI(Float.valueOf(st.substring(j, j + 6)));
						}
					} else {
						JOptionPane.showMessageDialog(null, "ERROR: No se admiten más de 999 tramas...", "¡¡¡ERROR!!!",
								JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					}
					al.add(medida);
					Leer.imprimir_valores(st, medida, VERBOSE);
				}
			}
			sc.close();
 
			try {
				Sumario sm = new Sumario(al);
				TablaFinal frame = new TablaFinal(sm);
				frame.setTitle("Estadísticos Descriptivos");
				frame.pack();
				frame.setVisible(true);
				frame.toFront();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERROR: No se pudo calcular el sumario " + e.getMessage(),
						"¡¡¡ERROR!!!", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			} finally {
				sc.close();
			}

			fichero_salida = Leer.escribeFicheroFileWriter(fichero_entrada.getName().replace(".txt", ".csv"), pwtx, al);
			JOptionPane.showMessageDialog(null, "¡HECHO! -> " + fichero_salida.getAbsolutePath() + "\n");

			if (Desktop.isDesktopSupported() && IMPRIMIR) {
				Desktop.getDesktop().edit(fichero_salida);
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero de entrada", "INFORMACION",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡¡¡ERROR!!!", JOptionPane.ERROR_MESSAGE);
			error = -1;
		} finally {
			System.gc();
			System.exit(error);
		}
	}

	public static File escribeFicheroPrintWriter(String nombre, String pw, ArrayList<Medida> al) {
		File fichero_salida = new File(nombre);
		try {
			if (!fichero_salida.exists()) {
				fichero_salida.createNewFile();
			}
			PrintWriter writer = new PrintWriter(fichero_salida, "UTF-8");
			if (pw != null) {
				writer.print("dB");
				writer.print(", ");
			}
			writer.print("PKT_ID");
			writer.print(", ");
			writer.print("RSSI");
			writer.println("\n");

			for (int i = 0; i < al.size(); i++) {
				if (pw != null) {
					writer.print(pw);
					writer.print(", ");
				}
				Medida md = al.get(i);
				writer.print(String.valueOf(md.getPKT_ID()));
				writer.print(", ");
				writer.print(String.valueOf(md.getRSSI()));
				writer.println("\n");
			}
			writer.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡¡¡ERROR!!!", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return (fichero_salida);
	}

	public static File escribeFicheroFileWriter(String nombre, String pw, ArrayList<Medida> al) {
		File fichero_salida = new File(nombre);
		try {
			if (!fichero_salida.exists()) {
				fichero_salida.createNewFile();
			}
			FileWriter csvWriter = new FileWriter(fichero_salida);
			if (pw != null) {
				csvWriter.write("dB");
				csvWriter.write(", ");
			}
			csvWriter.write("PKT_ID");
			csvWriter.write(", ");
			csvWriter.write("RSSI");
			csvWriter.write("\n");

			for (int i = 0; i < al.size(); i++) {
				if (pw != null) {
					csvWriter.write(pw);
					csvWriter.write(", ");
				}
				Medida md = al.get(i);
				csvWriter.write(String.valueOf(md.getPKT_ID()));
				csvWriter.write(", ");
				csvWriter.write(String.valueOf(md.getRSSI()));
				csvWriter.write("\n");
			}
			csvWriter.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡¡¡ERROR!!!", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return (fichero_salida);
	}

	private static File cargarArchivo() {
		JFileChooser fichero = null;
		try {
			fichero = new JFileChooser();
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de Texto", "txt");
			fichero.setFileFilter(filtro);
			fichero.setDialogTitle("Selecciona el fichero a procesar...");
			fichero.showOpenDialog(null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "" + "\nNo se ha encontrado el archivo", "ADVERTENCIA!!!",
					JOptionPane.WARNING_MESSAGE);
			System.exit(-1);
		}
		return (fichero.getSelectedFile());
	}

	private static void imprimir_valores(String st, Medida medida, boolean imprimir) {
		if (imprimir) {
			System.out.println(st);
			System.out.println(medida.getPKT_ID());
			System.out.println(medida.getRSSI());
		}
	}

	public static void imprimirArrayList(ArrayList<Medida> al) {
		for (int i = 0; i < al.size(); i++) {
			Medida md = al.get(i);
			System.out.println(md.getPKT_ID() + ", " + md.getRSSI());
		}
	}

}