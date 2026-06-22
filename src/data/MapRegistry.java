package data;

import java.util.ArrayList;
import java.util.List;

import core.Tile;
import core.TileObject;

public class MapRegistry {
	
	private List<Tile> allTiles;
	private List<Tile> allObjects;
	private List<Tile> allNpcs;
	
	private List<TileObject> allSortedItems;
	
	public MapRegistry(List<Tile> allTiles, List<Tile> allObjects, List<Tile> allNpcs) {
		setAll(allTiles, allObjects, allNpcs);
		
		allSortedItems = new ArrayList<>();
	}
	
	public List<Tile> getAllTiles() {
		return allTiles;
	}
	public void setAllTiles(List<Tile> allTiles) {
		this.allTiles = allTiles;
	}
	public List<Tile> getAllObjects() {
		return allObjects;
	}
	public void setAllObjects(List<Tile> allObjects) {
		this.allObjects = allObjects;
	}
	public List<Tile> getAllNpcs() {
		return allNpcs;
	}
	public void setAllNpcs(List<Tile> allNpcs) {
		this.allNpcs = allNpcs;
	}
	
	public List<TileObject> getAllSortedItems() {
		return allSortedItems;
	}

	public void setAllSortedItems(List<TileObject> allSortedItems) {
		this.allSortedItems = allSortedItems;
	}

	public void setAll(List<Tile> allTiles, List<Tile> allObjects, List<Tile> allNpcs) {
		this.allTiles = allTiles;
		this.allObjects = allObjects;
		this.allNpcs = allNpcs;
	}
	
	public void clear() {
		allTiles.clear();
		allObjects.clear();
		allNpcs.clear();
	}
}
