package poly.bean;

import org.springframework.stereotype.Component;

@Component
public class MEAL {
	int maMon;
	String tenMon, hinhAnh,moTa;
	public int getMaMon() {
		return maMon;
	}
	public void setMaMon(int maMon) {
		this.maMon = maMon;
	}
	public String getTenMon() {
		return tenMon;
	}
	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public MEAL() {
		super();
	}
	public MEAL(int maMon, String tenMon, String hinhAnh, String moTa) {
		super();
		this.maMon = maMon;
		this.tenMon = tenMon;
		this.hinhAnh = hinhAnh;
		this.moTa = moTa;
	}
	
}
