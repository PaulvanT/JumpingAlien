package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class SlimeID implements Comparable<SlimeID>{

	
	public SlimeID(long id) {
		this.id = id;
	}
	
	public long getSlimeID() {
		return this.id;
	}
	
	private final long id;
	
	
	/**
	 * @param	otherID
	 * @return	
	 * 		|	if (otherID.getSlimeID() < this.getSlimeID())
	 * 		|	then result == -1
	 * 		|	else if (otherID.getSlimeID() > this.getSlimeID())
	 * 		|	then result == 1
	 * 		|	else
	 * 		|	then result == 0
	 * @throws 	ClassCastException
	 * 		|	(otherID == null)
	 */
	@Override
	public int compareTo(SlimeID otherID) throws ClassCastException { 
		
		if (otherID == null) throw new ClassCastException("Non-effective ID");
		
		if (otherID.getSlimeID() < this.getSlimeID())
			return -1;
		else if (otherID.getSlimeID() > this.getSlimeID())
			return 1;
		else
			return 0;
	}

}
