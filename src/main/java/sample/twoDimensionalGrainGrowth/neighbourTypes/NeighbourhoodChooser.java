package sample.twoDimensionalGrainGrowth.neighbourTypes;

import sample.twoDimensionalGrainGrowth.Board;
import sample.twoDimensionalGrainGrowth.Cell;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeighbourhoodChooser {
	private static final Map<String, Neighbourhood> neighbourMap = new HashMap<>();
	static{
		neighbourMap.put("Von Neumann",new VonNeumann());
		neighbourMap.put("Moore", new Moore());
		neighbourMap.put("Hexagonal - Random", new HexagonalRandom());
		neighbourMap.put("Hexagonal - Left", new HexagonalRandom());
		neighbourMap.put("Hexagonal - Right", new HexagonalRandom());
		neighbourMap.put("Pentagonal", new Pentagonal());
	}

	private String type;
	private static Board board;

	public NeighbourhoodChooser() {
		board = Board.getInstance();
		this.type = board.getNeighbourComboBox().getValue();
	}

	public List<Cell> addNeighbours(int x, int y,Cell[][] cellsGrid){
		return neighbourMap.get(this.type).addNeighbours(x,y,cellsGrid,board);
	}
}
