package algorithm;

public class RANDOM extends ReplaceAlgorithm{
	/*
	 *
	 */
	@Override
	public int newPageReference(int startpn,int endpn,int type) {
		return (int) Math.round(Math.random() * (endpn - startpn) + startpn);
	}
}
