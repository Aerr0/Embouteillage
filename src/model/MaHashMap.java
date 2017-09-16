package model;

import java.util.HashMap;
import java.util.Map;

public class MaHashMap<E, T> extends HashMap<E, T> implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaHashMap() {
		super();
	}
	
	public MaHashMap(Map<E, T> m) {
		super(m);
	}
	
	public boolean equals(Object o) {
		if ( o != null && this.getClass() != o.getClass()) {
			return false;
		}
		for (E elem1 : this.keySet()) {
			for (E elem2 : ((MaHashMap<E,T>) o).keySet()) {
				if (elem1.equals(elem2)) {
					if (!(this.get(elem1).equals(((MaHashMap<E,T>) o).get(elem2)))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public Object clone() {
		MaHashMap<E,T> clone = null;
		clone = (MaHashMap<E,T>) super.clone();
		return clone;
	}
}
