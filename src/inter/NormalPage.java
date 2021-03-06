package inter;

public class NormalPage extends Page{
	private String[] blocks;
	private Integer[] diskAddress;
	private long referencetime=-1;
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
		String address="";
		for(int i=0;i<diskAddress.length-1;i++){
			address=address+diskAddress[i]+",";
		}
		address+=diskAddress[diskAddress.length-1];
		return super.toString()+"&"+address+"&"+referencetime+"&"+result;
	}
	public void setNewReferenceTime(long time){
		referencetime=time;
	}
	public long getNewReferenceTime(){
		return referencetime;
	}
}
