package test;
import inter.*;

public class LRUtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LRUlist lrUlist=new LRUlist();
		lrUlist.pushPage(1);
		lrUlist.pushPage(2);
		lrUlist.pushPage(3);
		lrUlist.pushPage(4);
		lrUlist.pushPage(5);
		lrUlist.pushPage(6);
		lrUlist.pushPage(2);
		lrUlist.pushPage(7);
		lrUlist.printCache();

	}

}
