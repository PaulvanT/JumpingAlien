package jumpingalien.model;

public enum Feature {

	AIR {
		
		/**
		 * Return the symbol of the AIR feature.
	     *
		 * @return  The integer 0.
		 *        | result == 0
		 */
		public int getSymbol() {
			return 0;
		}
		
		/**
		 * Return the type of the AIR feature.
	     *
		 * @return  The string PASSABLE.
		 *        | result == "PASSABLE"
		 */
		public String getType() {
			return "PASSABLE";
		}
		
	},
	
	SOLID_GROUND {
		
		/**
		 * Return the symbol of the SOLID_GROUND feature.
	     *
		 * @return  The integer 1.
		 *        | result == 1
		 */
		public int getSymbol() {
			return 1;
		}
		
		/**
		 * Return the type of the SOLID GROUND feature.
	     *
		 * @return  The string IMPASSABLE.
		 *        | result == "IMPASSABLE"
		 */
		public String getType() {
			return "IMPASSABLE";
		}
		
	},
	
	WATER {
		
		/**
		 * Return the symbol of the WATER feature.
	     *
		 * @return  The integer 2.
		 *        | result == 2
		 */
		public int getSymbol() {
			return 2;
		}
		
		/**
		 * Return the type of the WATER feature.
	     *
		 * @return  The string PASSABLE.
		 *        | result == "PASSABLE"
		 */
		public String getType() {
			return "PASSABLE";
		}
		
	},
	
	MAGMA {
		
		/**
		 * Return the symbol of the MAGMA feature.
	     *
		 * @return  The integer 3.
		 *        | result == 3
		 */
		public int getSymbol() {
			return 3;
		}
		
		/**
		 * Return the type of the MAGMA feature.
	     *
		 * @return  The string PASSABLE.
		 *        | result == "PASSABLE"
		 */
		public String getType() {
			return "PASSABLE";
		}
		
	},
	
	ICE {
		
		/**
		 * Return the symbol of the ICE feature.
	     *
		 * @return  The integer 4.
		 *        | result == 4
		 */
		public int getSymbol() {
			return 4;
		}

		/**
		 * Return the type of the ICE feature.
	     *
		 * @return  The string IMPASSABLE.
		 *        | result == "IMPASSABLE"
		 */
		public String getType() {
			return "IMPASSABLE";
		}
	},
	
	GAS {
		
		/**
		 * Return the symbol of the GAS feature.
	     *
		 * @return  The integer 5.
		 *        | result == 5
		 */
		public int getSymbol() {
			return 5;
		}
		
		/**
		 * Return the type of the GAS feature.
	     *
		 * @return  The string PASSABLE.
		 *        | result == "PASSABLE"
		 */
		public String getType() {
			return "PASSABLE";
		}
		
	};
	
	/**
	 * Return the symbol of this feature.
	 */
	public abstract int getSymbol();
	
	/**
	 * Return the type of this feature.
	 */
	public abstract String getType();
	
}
