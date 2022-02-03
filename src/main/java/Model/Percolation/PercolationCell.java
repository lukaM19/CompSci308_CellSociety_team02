package Model.Percolation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;

/**
 * Extension of `Cell` superclass for modeling Percolation. Properly updates the states of the cells
 * given the algorithm of the model
 *
 * @author Matthew Giglio
 */
public class PercolationCell extends Cell {


  /**
   * @param position     `Coordinate` representing position of the cell in the grid
   * @param initialState initializing state of the cell
   */
  public PercolationCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, int numberOfRows, int numberOfColumns) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns);
  }

  protected void updateState() {
    currentState = futureState;
  }


  protected void determineNextState(Grid grid) {
    if (currentState == States.Percolation.BLOCKED ||
        currentState == States.Percolation.PERCOLATED) {
      futureState = currentState;
      return;
    }
    if (canPercolate(grid)) {
      futureState = States.Percolation.PERCOLATED;
    } else {
      futureState = States.Percolation.OPEN;
    }
  }

  private boolean canPercolate(Grid grid) {
    int[] rowDelta = Neighbors.getRowDelta(direction);
    int[] colDelta = Neighbors.getColDelta(direction);
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i], edgeType,
          numberOfRows, numberOfColumns);
      if (grid.isInBounds(neighbor)) {
        if (getNeighborState(neighbor, grid) == States.Percolation.PERCOLATED) {
          return true;
        }
      }
    }
    return false;
  }
}
