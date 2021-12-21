package leer_fichero;

final public class Medida {

	private int PKT_ID;
	private float RSSI;

	public Medida() {
		this.setPKT_ID(0);
		this.setRSSI(0);
	}

	public float getRSSI() {
		return RSSI;
	}

	public void setRSSI(float rSSI) {
		RSSI = rSSI;
	}

	public int getPKT_ID() {
		return PKT_ID;
	}

	public void setPKT_ID(int pKT_ID) {
		PKT_ID = pKT_ID;
	}
}
