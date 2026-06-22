package data;

import java.util.Arrays;

import core.Tile;

public class MapData {
	
	private int rows;
	private int cols;
	
	//map Layers
	private int[][] tileMap;
	private int[][] objectMap;
	private int[][] npcMap;
	private int[][] npcWalkAreaMap;
	
	private Tile[][] autotileMap;
	
	public MapData(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		cleanMap();
	}
	
	//make a deep copy of the values of the parsed mapData
	public MapData(MapData mapData) {
		this.rows = mapData.rows;
		this.cols = mapData.cols;
		
		this.autotileMap = new Tile[rows][cols];
		this.tileMap = new int[rows][cols];
		this.objectMap = new int[rows][cols];
		this.npcMap = new int[rows][cols];
		this.npcWalkAreaMap = new int[rows][cols];
		
		for (int r = 0; r < rows; r++) {
			System.arraycopy(mapData.tileMap[r], 0, tileMap[r], 0, cols);
			System.arraycopy(mapData.objectMap[r], 0, objectMap[r], 0, cols);
			System.arraycopy(mapData.npcMap[r], 0, npcMap[r], 0, cols);
			System.arraycopy(mapData.npcWalkAreaMap[r], 0, npcWalkAreaMap[r], 0, cols);
		}
	}
	
	public void cleanMap() {
		this.autotileMap = new Tile[rows][cols];
		this.tileMap = new int[rows][cols];
		this.objectMap = new int[rows][cols];
		this.npcMap = new int[rows][cols];
		this.npcWalkAreaMap = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			Arrays.fill(tileMap[i], -1);
			Arrays.fill(objectMap[i], -1);
			Arrays.fill(npcMap[i], -1);
			Arrays.fill(npcWalkAreaMap[i], -1);
		}
	}
	
	public void applyState(MapData state) {
		if (state == null)
			return;
		for (int r = 0; r < rows; r++) {
			System.arraycopy(state.getTileMap()[r], 0, tileMap[r], 0, cols);
			System.arraycopy(state.getObjectMap()[r], 0, objectMap[r], 0, cols);
			System.arraycopy(state.getNpcMap()[r], 0, npcMap[r], 0, cols);
			System.arraycopy(state.getNpcWalkAreaMap()[r], 0, npcWalkAreaMap[r], 0, cols);
		}
	}
	
	public void applyState(int[][] newTileMap, int[][] newObjectMap, 
			int[][] newNpcMap, int[][] newNpcWalkAreaMap) {
		this.tileMap = newTileMap;
		this.objectMap = newObjectMap;
		this.npcMap = newNpcMap;
		this.npcWalkAreaMap = newNpcWalkAreaMap;
	}
	
	public int[][] getTileMap() {
		return tileMap;
	}
	
	public int getTileMap(int row, int col) {
		return tileMap[row][col];
	}
	
	public void setTileMap(int row, int col, int value) {
		tileMap[row][col] = value;
	}

	public int[][] getObjectMap() {
		return objectMap;
	}
	
	public int getObjectMap(int row, int col) {
		return objectMap[row][col];
	}
	
	public void setObjectMap(int row, int col, int value) {
		objectMap[row][col] = value;
	}

	public int[][] getNpcMap() {
		return npcMap;
	}
	
	public int getNpcMap(int row, int col) {
		return npcMap[row][col];
	}
	
	public void setNpcMap(int row, int col, int value) {
		npcMap[row][col] = value;
	}

	public int[][] getNpcWalkAreaMap() {
		return npcWalkAreaMap;
	}
	
	public int getNpcWalkAreaMap(int row, int col) {
		return npcWalkAreaMap[row][col];
	}
	
	public void setNpcWalkAreaMap(int row, int col, int value) {
		npcWalkAreaMap[row][col] = value;
	}
	
	public Tile[][] getAutotileMap() {
		return autotileMap;
	}

	public Tile getAutotileMap(int row, int col) {
		return autotileMap[row][col];
	}
	
	public void setAutotileMap(int row, int col, Tile tile) {
		autotileMap[row][col] = tile;
	}
	
	public void initAutotileMap() {
		this.autotileMap = new Tile[rows][cols];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getCols() {
		return cols;
	}
}
