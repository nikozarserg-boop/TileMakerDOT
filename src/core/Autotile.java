package core;

import java.awt.image.BufferedImage;

	
public class Autotile extends Tile {
	
	private static final int GRID_WIDTH = 4;
	private static final int GRID_HEIGHT = 4;
	
	private int idTile1;
	private int idTile2;
	private int idStartSubTile;
	private int sectionWidth;
	private int sectionHeight;
	
	public Autotile(int idTile1, int idTile2, int idStartSubTile, 
			String name, BufferedImage image, String path) {
		super(Autotile.concatenate(idTile1, idTile2), name, image, path);
		
		this.idTile1 = idTile1;
		this.idTile2 = idTile2;
		this.idStartSubTile = idStartSubTile;
		
		sectionWidth = image.getWidth() / GRID_WIDTH;
		sectionHeight = image.getWidth() / GRID_HEIGHT;
	}
	
	//combine the strings of the 2 id, should always be unique
	private static int concatenate(int id1, int id2) {
		return Integer.valueOf(id1 + "" + id2);
	}

	public int getIdTile1() {
		return idTile1;
	}

	public int getIdTile2() {
		return idTile2;
	}
	
	public int getIdStartSubTile() {
		return idStartSubTile;
	}

	public boolean containsIds(int id) {
		if(id == this.idTile1 || id == this.idTile2) return true;
		return false;
	}
	
	public BufferedImage getSection(int sectionY, int sectionX) {
		if (sectionX < 0 || sectionX < 0 || 
				sectionX * sectionWidth > image.getWidth() - sectionWidth || 
				sectionY + sectionHeight > image.getHeight() - sectionHeight) {
            throw new IllegalArgumentException("Requested section is out of bounds.");
        }
		
		return image.getSubimage(sectionX * sectionWidth, sectionY * sectionHeight, sectionWidth, sectionHeight);
	}
	
	public int getSectionId(int sectionY, int sectionX) {
		if (sectionX < 0 || sectionX < 0 || 
				sectionX * sectionWidth > image.getWidth() - sectionWidth || 
				sectionY + sectionHeight > image.getHeight() - sectionHeight) {
            throw new IllegalArgumentException("Requested section is out of bounds.");
        }
		
		return idStartSubTile + sectionY * GRID_HEIGHT + sectionX;
	}
}
