package visualizer;


import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexagonalGridVisualizer extends GridVisualizer {

  private double cellWidth;
  private double cellHeight;
  private double hexEdgeWidth;

  public HexagonalGridVisualizer(int width, int height, int numberOfRows, int numberOfColumns,
      Grid grid, boolean gridRule, boolean cellStateDisplayRule) {
    super(width, height, numberOfRows, numberOfColumns, grid, gridRule, cellStateDisplayRule);
    calculateCellSize();

  }

  @Override
  protected void calculateCellSize() {
    cellHeight = getHeight() / Double.valueOf(getNumRows());
    cellWidth = getWidth() / Double.valueOf(getNumColumns());
    hexEdgeWidth = cellWidth / 2;
  }

  @Override
  public Group makeRoot() {
    Group gridRoot = new Group();

    gridRoot.getChildren().add(arrangeCells());
    return gridRoot;
  }

  @Override
  protected Group arrangeCells() {
    Group cellGroup = new Group();
    double xPos;
    double yPos = 0;
    for (int i = 0; i < getNumRows(); i++) {
      xPos = 0;
      for (int j = 0; j < getNumColumns(); j++) {
        Coordinate c = new Coordinate(i, j);
        cellGroup.getChildren().add(createCell(xPos, yPos, c));
        addStateTagsToDisplay(xPos, yPos, j, c, cellGroup);
        xPos = xPos + cellWidth;
        if (j % 2 == 1) {
          xPos = xPos - hexEdgeWidth;
        }
      }
      yPos = yPos + cellHeight;
    }
    return cellGroup;
  }


  protected double[] getTextCoordinates(double xPos, double yPos, int j) {
    double[] textCoordinate = new double[2];
    if (j % 2 == 1) {
      textCoordinate[0] = xPos - hexEdgeWidth / 2;
      textCoordinate[1] = yPos + cellHeight;
    } else {
      textCoordinate[0] = xPos;
      textCoordinate[1] = yPos + cellHeight / 2;
    }
    return textCoordinate;
  }


  @Override
  protected Polygon createCell(double xPos, double yPos, Coordinate c) {
    Polygon newCell;
    if (c.getColumn() % 2 == 1) {
      newCell = new Polygon(xPos - hexEdgeWidth / 2, yPos + cellHeight / 2, xPos - hexEdgeWidth,
          yPos + cellHeight, xPos - hexEdgeWidth / 2, yPos + cellHeight + hexEdgeWidth,
          xPos + hexEdgeWidth / 2, yPos + cellHeight + hexEdgeWidth, xPos + hexEdgeWidth,
          yPos + cellHeight,
          xPos + hexEdgeWidth / 2, yPos + cellHeight / 2);

    } else {
      newCell = new Polygon(xPos, yPos, xPos - hexEdgeWidth / 2, yPos + cellHeight / 2, xPos,
          yPos + cellHeight, xPos + hexEdgeWidth, yPos + cellHeight, xPos + 0.75 * cellWidth,
          yPos + cellHeight / 2, xPos + hexEdgeWidth, yPos);

    }
    if (getGridRule()) {
      newCell.setStroke(Color.BLACK);
    }
    newCell.setFill(getColorMap().getStateMatch(getCellStateString(c)));
    return newCell;

  }

}
