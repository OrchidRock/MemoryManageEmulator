package inter;

public class PhysicalPage extends Page{
	public String data="hello";
	private int diskAddress;
	public PhysicalPage(String data,int da){
		this.data=data;
		this.diskAddress=da;
	}
	
}
