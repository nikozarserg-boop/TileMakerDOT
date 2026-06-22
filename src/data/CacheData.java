package data;

import java.awt.image.BufferedImage;
import java.io.File;

import core.Tile;

public class CacheData {
	
	public static final String NOT_FOUND_NAME = "not_found";
	//used for when there is no texture found
	private final BufferedImage notFoundIcon; 
	private final String resourceBasePath;
	
	//we will use this to fast save after loading a save
	private File cachedSavedLocation = null;
	private boolean canQuickSave = false;
	
	//used to save the found id so that it does not search every call / frame
	private Tile cachedTile = new Tile(-2, "randomName", null, "");
	private Tile cachedObject = new Tile(-2, "randomName", null, "");
	private Tile cachedNPC = new Tile(-2, "randomName", null, "");
	
	public CacheData(BufferedImage notFoundIcon, String resourceBasePath) {
		this.notFoundIcon = notFoundIcon;
		this.resourceBasePath = resourceBasePath;
	}
	
	public Tile getCachedTile() {
		return cachedTile;
	}
	
	public void setCachedTile(Tile cachedTile) {
		this.cachedTile = cachedTile;
	}
	
	public Tile getCachedObject() {
		return cachedObject;
	}
	
	public void setCachedObject(Tile cachedObject) {
		this.cachedObject = cachedObject;
	}
	
	public Tile getCachedNPC() {
		return cachedNPC;
	}
	
	public void setCachedNPC(Tile cachedNPC) {
		this.cachedNPC = cachedNPC;
	}

	public BufferedImage getNotFoundIcon() {
		return notFoundIcon;
	}

	public String getResourceBasePath() {
		return resourceBasePath;
	}
	
	public File getCachedSavedLocation() {
		return cachedSavedLocation;
	}

	public void setCachedSavedLocation(File cachedSavedLocation) {
		this.cachedSavedLocation = cachedSavedLocation;
	}

	public boolean isCanQuickSave() {
		return canQuickSave;
	}

	public void setCanQuickSave(boolean canQuickSave) {
		this.canQuickSave = canQuickSave;
	}
}
