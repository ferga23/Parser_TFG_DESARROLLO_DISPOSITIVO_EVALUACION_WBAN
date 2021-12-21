package leer_fichero;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TablaFinal extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TablaFinal(Sumario sm) {

		String[] columnNames = { "Mínimo", "Máximo", "Media", "Mediana", "Moda", "Varianza", "Desviación Típica",
				"Cuartil 1", "Cuartil 3", "CV" };
		Object[][] datos = { { String.valueOf(sm.getMinimo()) + " dBm", String.valueOf(sm.getMaximo()) + " dBm",
				String.valueOf(sm.getMedia()) + " dBm", String.valueOf(sm.getMediana()) + " dBm",
				String.valueOf(sm.getModa()) + " dBm", String.valueOf(sm.getVarianza()),
				String.valueOf(sm.getSd()) + " dBm", String.valueOf(sm.getCuartil1()) + " dBm",
				String.valueOf(sm.getCuartil3()) + " dBm", String.valueOf(sm.getCV()) + " dBm" } };

		DefaultTableModel dtm = new DefaultTableModel(datos, columnNames);
		final JTable table = new JTable(dtm);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < columnNames.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(new Dimension(750, 25));
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
