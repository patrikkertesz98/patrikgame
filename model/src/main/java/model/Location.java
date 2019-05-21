package model;

import java.util.Random;

/**
 * This is the class that handles the location operations.
 */
public class Location {
	private Integer row, column;

	/**
	 *Generates a random location
	 *
	 * @param rowUpperBound the upper boundary of the rows of the grid.
	 * @param columnUpperBound the upper boundary of the columns of the grid.
	 * @return returns a new location with the row and col coordinates.
	 */
	static Location createRandomLocation(Integer rowUpperBound, Integer columnUpperBound) {
		Random rnd = new Random();
		Integer row = rnd.nextInt(rowUpperBound);
		Integer col = rnd.nextInt(columnUpperBound);

		return new Location(row, col);
	}

	/**
	 * Sets the location of an object.
	 *
	 * @param row the row of the current object's location.
	 * @param column the column of the current object's location.
	 */
	public Location(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	}


	public Integer getColumn() {
		return column;
	}

	public Integer getRow() {
		return row;
	}

	/**
	 * Checks if an object location equals
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Location.class)
			return this.row.equals(((Location) obj).getRow()) && this.column.equals(((Location) obj).getColumn());

		return super.equals(obj);
	}
}
