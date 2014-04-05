package interfaces;

/**
 * An interface that maintains instance variables that allows an entity of the game
 * to know "directions". It can also be used by any class that may need to know all
 * possible directions an entity has available to it in order to do calculations.
 * 
 * @author Marcos Davila
 *
 */
public interface Orientable {
	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;
	static final int RANGE = 4;
}
