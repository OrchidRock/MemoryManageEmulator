package inter;

public class NormalPage extends Page{
	private String[] blocks;
	private Integer[] diskAddress;
	public NormalPage(String[] data,Integer[] da){
		this.blocks=data;
		this.diskAddress=da;
	}
	public void setData(String[] string){
		this.blocks=string;
	}
	public String[] getData(){
		return blocks;
	}
	public Integer[] getDiskAddress(){
		return diskAddress;
	}
	public String toString(){
		String result="";
		for(int i=0;i<blocks.length;i++){
			result+=blocks[i];
		}
		return result;
	}
}
