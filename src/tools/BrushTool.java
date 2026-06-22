package tools;

import java.util.Random;

import data.MapState;

public class BrushTool {
	
	private int[] brushObjectIds;
	private int brushSpread;
	
	private MapState mapState;
	private HistoryFunction historyFunction;
	
	public BrushTool(MapState mapState, HistoryFunction historyFunction) {
		this.mapState = mapState;
		this.historyFunction = historyFunction;
	}
    
    public void brushRectObjects(int r1, int c1, int r2, int c2) {
		r1 = Math.max(0, Math.min(r1, mapState.getData().getRows() - 1));
		r2 = Math.max(0, Math.min(r2, mapState.getData().getRows() - 1));
		c1 = Math.max(0, Math.min(c1, mapState.getData().getCols() - 1));
		c2 = Math.max(0, Math.min(c2, mapState.getData().getCols() - 1));

		int startRow = Math.min(r1, r2);
		int endRow = Math.max(r1, r2);
		int startCol = Math.min(c1, c2);
		int endCol = Math.max(c1, c2);

		historyFunction.saveState();
		
	    Random rand = new Random();

		for (int r = startRow; r <= endRow; r++) {
			for (int c = startCol; c <= endCol; c++) {
				
				//only place an object if the random roll hits 0
	            if (rand.nextInt(brushSpread) == 0) {
	                boolean neighborFound = false;
	                
					//define the 4 directions to check
					int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

					for (int[] dir : directions) {
						int nr = r + dir[0];
						int nc = c + dir[1];

						//stay within map bounds
						if (nr >= 0 && nr < mapState.getData().getRows() && nc >= 0 && nc < mapState.getData().getCols()) {
							if (mapState.getData().getObjectMap(nr, nc) != -1) {
								neighborFound = true;
								break;
							}
						}
					}
	                
					if (!neighborFound) {
						//pick a random object from the selection
						int randomId = brushObjectIds[rand.nextInt(brushObjectIds.length)];

						if (mapState.getData().getObjectMap(r, c) == -1) {
							mapState.getData().setObjectMap(r, c, randomId);
							mapState.addNewSortedObject(r, c, true);
						}
					}
	    		}
			}
		}
	}

	public void setBrushObjectIds(int[] brushObjectIds) {
		this.brushObjectIds = brushObjectIds;
	}

	public void setBrushSpread(int brushSpread) {
		this.brushSpread = brushSpread;
	}
}
