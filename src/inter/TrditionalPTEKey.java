package inter;

import java.util.Objects;

public class TrditionalPTEKey extends Key {
	private int la;
	public TrditionalPTEKey(int la) {
		// TODO Auto-generated constructor stub
		this.la=la;
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(la);
	}

}
