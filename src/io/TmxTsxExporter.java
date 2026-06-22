package io;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Autotile;
import core.Tile;
import core.TileObject;
import utils.Utils;
import view.TileCanvas;

public class TmxTsxExporter {

	private static final String AUTOTILE_EXTENSION = "_autotile";
	private TileCanvas tileCanvas;
    private final int rows;
    private final int cols;
    private final int tileSize;
    
    private Map<Integer, Tile> originalTiles;
    private Map<Integer, Tile> originalObjects;
    private Map<Integer, Tile> originalNpcs;
    private Map<Integer, Autotile> originalAutotiles = new HashMap<>();
    
    //key will be normal ID and value will be the new unique general ID
    private Map<Integer, Integer> newTileConvertedIDs = new HashMap<>();
    private Map<Integer, Integer> newObjectConvertedIDs = new HashMap<>();
    private Map<Integer, Integer> newNpcConvertedIDs = new HashMap<>();
    private Map<Integer, Integer> newAutotilesConvertedIDs = new HashMap<>();
    
    private int tileCount = 0;
    private int autotileCount = 0;
    private StringBuilder textureDescriptions;
    private int maxTileWidth = 0;
    private int maxTileHeight = 0;

    public TmxTsxExporter(TileCanvas tileCanvas, int rows, int cols, int tileSize) {
    	this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;
        this.tileCanvas = tileCanvas;
        textureDescriptions = new StringBuilder();

    	originalTiles = tileCanvas.getMapState().getUniqueSortedTiles();
    	originalObjects = tileCanvas.getMapState().getUniqueSortedObjects();
    	originalNpcs = tileCanvas.getMapState().getUniqueSortedNpcs();
    	originalAutotiles = tileCanvas.getMapState().getUniqueSortedAutotiles();
    	
        setNewConvertedIDs(originalTiles, newTileConvertedIDs);
        setNewConvertedIDs(originalObjects, newObjectConvertedIDs);
        setNewConvertedIDs(originalNpcs, newNpcConvertedIDs);

    	if (textureDescriptions.length() > 0 && textureDescriptions.charAt(textureDescriptions.length() - 1) == '\n') {
    	    textureDescriptions.deleteCharAt(textureDescriptions.length() - 1);
    	}
    }
    
    private void setNewConvertedIDs(Map<Integer, Tile> tileMap, 
    		Map<Integer, Integer> convertedMap) {
    	
    	for (Map.Entry<Integer, Tile> entry : tileMap.entrySet()) {
    		Integer id = entry.getKey();
    	    Tile tile = entry.getValue();
    		
    		convertedMap.put(id, tileCount);
    		
    		maxTileWidth = Math.max(maxTileWidth, tile.getImage().getWidth());
    		maxTileHeight = Math.max(maxTileHeight, tile.getImage().getHeight());
    		//build the object descriptions for .TSX
    		textureDescriptions.append(
    				  " <tile id=\"" + tileCount + "\">\n"
    				+ "  <image source=\"" + tile.getPath() + "\" width=\"" + tile.getImage().getWidth() + "\" height=\"" + tile.getImage().getHeight() + "\"/>\n"
    				+ " </tile>\n");
    		
    		tileCount ++;
    	}
    }
    
    //generates a .TSX file
    public void exportTSX(File file, List<Tile> allTiles) {
    	exportAutotiles(file);
    	
    	//export every texture tile, NPC and object
        try (PrintWriter writer = new PrintWriter(file)) {
        	String fileName = file.getName().replace(Utils.TSX_FILE_EXTENSION, "");
        	
        	writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        	
            //note to myself: objectalignment="topleft" is very important to set for my tool
            writer.printf("<tileset version=\"1.10\" tiledversion=\"1.11.2\" name=\"%s\" tilewidth=\"%d\" tileheight=\"%d\" tilecount=\"%d\" columns=\"0\" objectalignment=\"topleft\">\n",
            		fileName, maxTileWidth, maxTileHeight, tileCount);
            
            writer.println(" <grid orientation=\"orthogonal\" width=\"1\" height=\"1\"/>");
            
            writer.println(textureDescriptions);
            
            writer.println("</tileset>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //export the autoTiles only if they are available
	private void exportAutotiles(File file) {
		int countAutotiles = 0;
		for (Map.Entry<Integer, Autotile> entry : originalAutotiles.entrySet()) {
			Autotile autotile = entry.getValue();
			
			countAutotiles++;

			int width = autotile.getImage().getWidth() / tileSize;
			int height = autotile.getImage().getHeight() / tileSize;
			int nrOfTiles = width * height;
			
			File autotilePath = new File(file.getPath().replace(Utils.TSX_FILE_EXTENSION, "") + AUTOTILE_EXTENSION 
					+ countAutotiles + Utils.TSX_FILE_EXTENSION);
			
			try (PrintWriter writer = new PrintWriter(autotilePath)) {
				String fileName = autotilePath.getName().replace(Utils.TSX_FILE_EXTENSION, "");

				writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				
				writer.printf("<tileset version=\"1.10\" tiledversion=\"1.11.2\" name=\"%s\" tilewidth=\"%d\" tileheight=\"%d\" tilecount=\"%d\" columns=\"4\">\n",
						fileName, tileSize, tileSize, nrOfTiles);

				writer.printf(" <image source=\"%s\" width=\"%d\" height=\"%d\"/>\n",
						autotile.getPath(), autotile.getImage().getWidth(), autotile.getImage().getHeight());
				
				writer.println("</tileset>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    //generates a .TMX file map that references the external .TSX
    public void exportTMX(File file, String tsxFileName, int[][] tileData, List<TileObject> allObjects) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.printf("<map version=\"1.10\" tiledversion=\"1.11.2\" orientation=\"orthogonal\" renderorder=\"right-down\" width=\"%d\" height=\"%d\" tilewidth=\"%d\" tileheight=\"%d\" infinite=\"0\" nextlayerid=\"3\" nextobjectid=\"%d\">\n",
                    cols, rows, tileSize, tileSize, allObjects.size() + 1);
            
            //reference the external .TSX file
            writer.printf(" <tileset firstgid=\"1\" source=\"%s\"/>\n", tsxFileName);

    		autotileCount = tileCount;
    		int countAutotiles = 0;
    		
            //go through every existing autoTile and add if they exist
        	for (Map.Entry<Integer, Autotile> entry : originalAutotiles.entrySet()) {
        		Integer id = entry.getKey();
        		Autotile autotile = entry.getValue();
        		
        		countAutotiles ++;
        		String autotileName = tsxFileName.replace(Utils.TSX_FILE_EXTENSION, "") 
        		+ AUTOTILE_EXTENSION + countAutotiles + Utils.TSX_FILE_EXTENSION;
        		
    			int width = autotile.getImage().getWidth() / tileSize;
    			int height = autotile.getImage().getHeight() / tileSize;
    			int nrOfTiles = width * height;
    			
    		    newAutotilesConvertedIDs.put(id, autotileCount);

        		writer.printf(" <tileset firstgid=\"%d\" source=\"%s\"/>\n", autotileCount, autotileName);
        	
        		autotileCount += nrOfTiles;
        	}
            
            writer.printf(" <layer id=\"1\" name=\"Tile Layer 1\" width=\"%d\" height=\"%d\">\n", cols, rows);
            writer.println("  <data encoding=\"csv\">");

            for (int r = 0; r < rows; r++) {
                StringBuilder rowString = new StringBuilder();
                for (int c = 0; c < cols; c++) {
                    //add the empty value which is 0 and everything offsets by +1
                    if(tileData[r][c] == -1) {
                    	rowString.append(0);
                    }
                    else {
                    	//Tiled IDs start at 1 (0 is empty)
                    	int tileValue = 1 + newTileConvertedIDs.get(tileData[r][c]);
                    	
                    	
    					int tileId = tileData[r][c];
    					Tile tile = tileCanvas.getMapState().findTileById(tileId);

    					if (tile.getAutotiles() != null) {
    						int autotiledId = tileCanvas.getMapState().getAutoTiledSubimageId(tile, r, c);
    						//if not autoTiled ID found then just add normal ID
    						if (autotiledId == -1) {
    	                        //Tiled IDs start at 1 (0 is empty)
    	                    	rowString.append(tileValue);
    						}
    						//else add autoTiled generated ID
    						else {
    							Autotile autotile = tileCanvas.getMapState().getCurrentAutotile(tile, r, c);
    							int convertedAutotileID = newAutotilesConvertedIDs.get(autotile.getIdStartSubTile());
    							int difference = autotile.getIdStartSubTile() - convertedAutotileID;

    							int gid = autotiledId - difference;
    							rowString.append(gid);
    						}
    					}
    					//else add autoTiled generated ID
    					else {
                            //Tiled IDs start at 1 (0 is empty)
                        	rowString.append(tileValue);
    					}
                    }
                    if (!(r == rows - 1 && c == cols - 1)) {
                        rowString.append(",");
                    }
                }
                writer.println(rowString.toString());
            }

            writer.println("  </data>");
            writer.println(" </layer>");
            
            //here we print all objects sorted
            writer.print(" <objectgroup id=\"2\" name=\"Object Layer 1\">\n");
            
            for(int i = 1; i <= allObjects.size(); i++) {
            	//set the object
            	TileObject object = allObjects.get(i-1);
            	
            	int gid;
            	
            	//set gid for object
            	if(object.isObject()) {
            		gid = 1 + newObjectConvertedIDs.get(object.getId());
            	}
            	//set gid for NPC
            	else {
            		gid = 1 + newNpcConvertedIDs.get(object.getId());
            	}
            	
                writer.printf("  <object id=\"%d\" gid=\"%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\"/>\n",
                		i, gid, object.getX() * tileSize, object.getY() * tileSize, 
                		object.getWidth(), object.getHeight());
            }
            
            writer.println(" </objectgroup>");
            writer.println("</map>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
