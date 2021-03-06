package sample.oneDimensionalSimulation;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import sample.twoDimensionalGrainGrowth.Controller2d;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class Controller1d {

		private GridPane stageGrid;
		private Integer cells;
		private Integer iterations;
		private AtomicBoolean gridFlag;
		private Integer rule;
		private Cell[][] cellsMatrix;
		private final int BORDER_SIZE = 700;
		private static Board board;


	public Controller1d() {
		board = Board.getInstance();
		this.stageGrid = board.getStageGrid();
		this.gridFlag = new AtomicBoolean(false);
		board.getGridButton().setOnAction((event)-> {
		if(!this.gridFlag.get()) {
			board.getCellsGrid().setGridLinesVisible(true);
			this.gridFlag.set(true);
		}
		else{
			board.getCellsGrid().setGridLinesVisible(false);
			this.gridFlag.set(false);
		}});
		getValues(board.getCellsNumberField().getText(),board.getIterationField().getText());
		board.getRunButton().setOnAction((event) ->{
			getValues(board.getCellsNumberField().getText(),board.getIterationField().getText());
			this.rule = Integer.parseInt(board.getRuleChoice().getValue());
			this.cellsMatrix = new Cell[this.iterations][this.cells];
			simulation(this.rule,board.getCellsGrid());
		});
	}

	private void drawGrid(GridPane cellsGrid){
		for(int i = 0; i< this.cells; i++){
			for(int j = 0; j< this.iterations; j++){
				cellsGrid.add(new Rectangle(this.BORDER_SIZE / this.cells, this.BORDER_SIZE / this.cells, Cell.DEAD.getColor()),i,j);
			}
		}
	}

	private void simulation(Integer rule, GridPane cellsGrid){
		String binaryRule = String.format("%8s",Integer.toBinaryString(rule)).replace(" ","0");

		for(int x=0;x<this.cells;x++){
			cellsGrid.add(new Rectangle(this.BORDER_SIZE / this.cells, this.BORDER_SIZE / this.cells, (x==this.cells/2)?Cell.ALIVE.getColor():Cell.DEAD.getColor()),x,0);
			this.cellsMatrix[0][x]= (x==this.cells/2)? Cell.ALIVE : Cell.DEAD;
		}

		for(int y=1;y<this.iterations;y++) {
			for (int x = 0; x < this.cells; x++) {
				int position;
				ActiveCells activeCells = new ActiveCells(this.cellsMatrix[y-1][boundaryConverter(x-1)], this.cellsMatrix[y-1][x], this.cellsMatrix[y-1][boundaryConverter(x +1)]);
				if (activeCells.left.getFlag() && activeCells.center.getFlag() && activeCells.right.getFlag())position=0;
				else if (activeCells.left.getFlag() && activeCells.center.getFlag()) position=1;
				else if (activeCells.left.getFlag() && activeCells.right.getFlag()) position=2;
				else if (activeCells.left.getFlag())position=3;
				else if (activeCells.center.getFlag() && activeCells.right.getFlag()) position=4;
				else if (activeCells.center.getFlag())position=5;
				else if (activeCells.right.getFlag())position=6;
				else position=7;
				nextCell(binaryRule,x,y,position);
			}
		}
	}
	public static Cell ruleConverter(char sign){
		if(sign=='0') return Cell.DEAD;
		else if(sign=='1') return Cell.ALIVE;
		else throw new IllegalArgumentException();
	}

	private void nextCell(String binaryRule, int x, int y, int position){
		this.cellsMatrix[y][x]=ruleConverter(binaryRule.charAt(position));
		board.getCellsGrid().add(new Rectangle(this.BORDER_SIZE / this.cells,this.BORDER_SIZE / this.cells,
				this.cellsMatrix[y][x].getColor()),x,y);
	}

	private void getValues(String cellsNumber, String iterationsNumber){
		board.getCellsGrid().getChildren().clear();
		this.cells = Controller2d.checkInt(cellsNumber,1);
		this.iterations = Controller2d.checkInt(iterationsNumber,1);
		drawGrid(board.getCellsGrid());
	}

	private int boundaryConverter(int x){
		return x<0?this.cells-1 : x%this.cells;
	}

}
