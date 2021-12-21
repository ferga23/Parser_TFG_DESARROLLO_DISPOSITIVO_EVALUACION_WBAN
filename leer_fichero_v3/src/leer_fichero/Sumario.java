package leer_fichero;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.math3.stat.StatUtils;

public final class Sumario {
	private double minimo = 0;
	private double maximo = 0;
	private double media = 0;
	private double mediana = 0;
	private double moda = 0;
	private double varianza = 0;
	private double sd = 0;
	private double cuartil1 = 0;
	private double cuartil3 = 0;
	private double CV = 0;
 
	public Sumario(ArrayList<Medida> al) {
		double[] potencia = new double[al.size()];
		for (int i = 0; i < al.size(); i++) {
			potencia[i] = al.get(i).getRSSI();
		}
		try {
			this.minimo = StatUtils.min(potencia);
			this.maximo = StatUtils.max(potencia);
			this.media = StatUtils.mean(potencia);
			this.mediana = StatUtils.percentile(potencia, 50.0);
			this.moda = StatUtils.max(StatUtils.mode(potencia));
			this.varianza = StatUtils.variance(potencia);
			this.sd = Math.sqrt(this.varianza);
			this.cuartil1 = StatUtils.percentile(potencia, 25.0);
			this.cuartil3 = StatUtils.percentile(potencia, 75.0);
			this.CV = Math.abs(this.sd / this.media) * 100.0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡¡¡ERROR!!!", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	public double getMinimo() {
		return minimo;
	}

	public void setMinimo(double minimo) {
		this.minimo = minimo;
	}

	public double getMaximo() {
		return maximo;
	}

	public void setMaximo(double maximo) {
		this.maximo = maximo;
	}

	public double getMedia() {
		return (Math.round(this.media * 100.0)) / 100.0;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public double getMediana() {
		return mediana;
	}

	public void setMediana(double mediana) {
		this.mediana = mediana;
	}

	public double getCuartil1() {
		return cuartil1;
	}

	public void setCuartil1(double cuartil1) {
		this.cuartil1 = cuartil1;
	}

	public double getCuartil3() {
		return cuartil3;
	}

	public void setCuartil3(double cuartil3) {
		this.cuartil3 = cuartil3;
	}

	public double getVarianza() {
		return (Math.round(this.varianza * 100.0)) / 100.0;
	}

	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}

	public double getSd() {
		return (Math.round(this.sd * 100.0) / 100.0);
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	public double getModa() {
		return moda;
	}

	public void setModa(double moda) {
		this.moda = moda;
	}

	public double getCV() {
		return (Math.round(this.CV * 1000.0)) / 1000.0;
	}

	public void setCV(double cV) {
		this.CV = cV;
	}

	@Override
	public String toString() {
		return "Sumario: [minimo = " + minimo + " dB, maximo = " + maximo + " dB, media = " + this.getMedia()
				+ " dB, mediana = " + mediana + " dB, moda = " + moda + " dB, varianza = " + this.getVarianza()
				+ ", sd = " + this.getSd() + " dB, cuartil1 = " + cuartil1 + " dB, cuartil3 = " + cuartil3
				+ " dB, CV = " + this.getCV() + " %]";
	}
}
