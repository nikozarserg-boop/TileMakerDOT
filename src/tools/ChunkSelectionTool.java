package tools;

import data.MapChunk;
import data.MapState;

public class ChunkSelectionTool {

	private MapChunk mapChunk;
	private MapState mapState;
	
	public ChunkSelectionTool(MapState mapState) {
		this.mapState = mapState;
		
		//start with it null so it does not preview a big chunk with empty values
	    mapChunk = null;
	}
	
	public void copyChunkSelection(int r1, int c1, int r2, int c2) {
		r1 = Math.max(0, Math.min(r1, mapState.getData().getRows() - 1));
		r2 = Math.max(0, Math.min(r2, mapState.getData().getRows() - 1));
		c1 = Math.max(0, Math.min(c1, mapState.getData().getCols() - 1));
		c2 = Math.max(0, Math.min(c2, mapState.getData().getCols() - 1));

		int startRow = Math.min(r1, r2);
		int endRow = Math.max(r1, r2);
		int startCol = Math.min(c1, c2);
		int endCol = Math.max(c1, c2);

	    int cols = endCol - startCol + 1;
	    int rows = endRow - startRow + 1;
	    mapChunk = new MapChunk(cols, rows);
	    
	    for (int r = 0; r < rows; r++) {
	        for (int c = 0; c < cols; c++) {
	        	mapChunk.getTiles()[r][c] = mapState.getData().getTileMap(startRow + r, startCol + c);
	        	mapChunk.getObjects()[r][c] = mapState.getData().getObjectMap(startRow + r, startCol + c);
	        	mapChunk.getNpcs()[r][c] = mapState.getData().getNpcMap(startRow + r, startCol + c);
	        	mapChunk.getWalkArea()[r][c] = mapState.getData().getNpcWalkAreaMap(startRow + r, startCol + c);
	        }
	    }
	}
	
	public void pasteChunk(int startR, int startC, boolean showAutotile) {
		if(mapChunk == null) {
			return;
		}
		
	    for (int r = 0; r < mapChunk.getRows(); r++) {
	        for (int c = 0; c < mapChunk.getCols(); c++) {
	            int targetR = startR + r;
	            int targetC = startC + c;
	            if (targetR < mapState.getData().getRows() && targetC < mapState.getData().getCols()) {
	            	mapState.getData().setTileMap(targetR, targetC, mapChunk.getTiles()[r][c]);
	                mapState.getData().setObjectMap(targetR, targetC, mapChunk.getObjects()[r][c]);
	                mapState.getData().setNpcMap(targetR, targetC, mapChunk.getNpcs()[r][c]);
	                mapState.getData().setNpcWalkAreaMap(targetR, targetC, mapChunk.getWalkArea()[r][c]);
	                
	                //if it is an object or NPC we must update your sorting system
	                if (mapChunk.getObjects()[r][c] != -1) {
	                    mapState.addNewSortedObject(targetR, targetC, true);
	                }
	                else if (mapChunk.getNpcs()[r][c] != -1) {
	                	mapState.addNewSortedObject(targetR, targetC, false);
	                }
	            }
	        }
	    }
	    
		//if the autoTile is not active then do not reset autotileMap to save time
		if (showAutotile)
				mapState.refreshAutotileMap();
	    mapState.refreshAllObjectsList();
	}

	public MapChunk getMapChunk() {
		return mapChunk;
	}
	
	public void newMapChunk(int cols, int rows) {
	    mapChunk = new MapChunk(cols, rows);
	}
}
