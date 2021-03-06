package sample.twoDimensionalGrainGrowth.neighbourTypes;

import sample.twoDimensionalGrainGrowth.Board;
import sample.twoDimensionalGrainGrowth.Cell;
import java.util.List;
import java.util.Random;

public class Pentagonal implements Neighbourhood {
	@Override
	public List<Cell> addNeighbours(int x, int y, Cell[][] cellsGrid, Board board) {
		cellList.clear();
		Random generator = new Random();
		int endX=0;
		int endY=0;
		int startX=0;
		int startY=0;
		switch (generator.nextInt(4)){
			case 0: endX=-1; break;
			case 1: startX=1; break;
			case 2: endY=-1;break;
			case 3: startY=1;break;
		}
		for(int i=-1+startY;i<2+endY;i++)
			for(int j=-1+startX;j<2+endX;j++)
				if(!(i==0 && j==0))
					if(board.getBoundaryConditionComboBox().getValue().equals("Periodic"))
						cellList.add(cellsGrid[periodicBoundaryConverter(y+i,cellsGrid.length)]
								[periodicBoundaryConverter(x+j,cellsGrid[0].length)]);
					else
						cellList.add(cellsGrid[y+i][x+j]);

		return cellList;
	}

}
