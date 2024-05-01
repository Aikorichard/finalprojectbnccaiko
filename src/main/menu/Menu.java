package main.menu;

public class Menu {

	private String kode;
	private String nama;
	private int stok;
	private int harga;
	

	public Menu(String kode, String nama, int stok, int harga) {
		this.kode = kode;
		this.nama = nama;
		this.stok = stok;
		this.harga = harga;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getStok() {
		return stok;
	}

	public void setStok(int stok) {
		this.stok = stok;
	}

	public int getHarga() {
		return harga;
	}

	public void setHarga(int harga) {
		this.harga = harga;
	}
	
	@Override
	public String toString() {
		return nama;
	}

	
}
