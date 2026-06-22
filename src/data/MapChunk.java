package data;

public class MapChunk {
	
	private int[][] tiles;
    private int[][] objects;
    private int[][] npcs;
    private int[][] walkArea;
    private int cols, rows;

    public MapChunk(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        tiles = new int[rows][cols];
        objects = new int[rows][cols];
        npcs = new int[rows][cols];
        walkArea = new int[rows][cols];
    }

	public int[][] getTiles() {
		return tiles;
	}

	public int[][] getObjects() {
		return objects;
	}

	public int[][] getNpcs() {
		return npcs;
	}
	
	public int[][] getWalkArea() {
		return walkArea;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}
}
