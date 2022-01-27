package Model;

import java.util.Map;

public class GameOfLife extends Simulation {

  private Map<Coordinate, Integer> setup;
  public GameOfLife(int numberOfColumns, int numberOfRows, Map<Coordinate, Integer> setup) {
    super(numberOfColumns, numberOfRows);
    this.setup = setup;
    initializeGridCells();
  }


  protected void createGrid() {grid = new GameOfLifeGrid(numberOfColumns, numberOfRows);}


  protected void initializeGridCells() {
    if (setup == null) return;
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.GameOfLife.DEAD;
        case 1 -> state = States.GameOfLife.ALIVE;
      }
      grid.getCellMap().put(c, new GameOfLifeCell(c, state));
    }
  }
}