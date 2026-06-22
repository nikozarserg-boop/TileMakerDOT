package view;

import java.awt.Point;

public class Camera {
	
	private double zoomLevel = 1.0;
	private double maxZoomOutLevel = 0.1;	//the smaller the more zoom out (further) you can go
	private double maxZoomInLevel = 5.0; 	//the bigger the more zoom in (closer) you can go
	private double zoomStep = 0.1; 			//the speed at witch you can zoom in and zoom out
	private Point viewOffset = new Point(0, 0);
	
	public void calculateZoomValues(int rows, int cols, int tileSize, boolean initZoomLevel) {
		int totalWidth = cols * tileSize;
		int totalHeight = rows * tileSize;
		int biggestSide = Math.max(totalWidth, totalHeight);

		//calculate zoom limits based on the map size
		maxZoomOutLevel = Math.min(1.0, 1000f / (double) biggestSide);
		maxZoomInLevel = 170f / (double) tileSize;
		zoomStep = 1800f / (double) biggestSide;
		
		//set it to start from the middle of the zoom
		if (initZoomLevel) {
			zoomLevel = maxZoomOutLevel + (maxZoomInLevel - maxZoomOutLevel) / 2;
		}
	}
	
	public void zoom(int wheelRotation, Point mouseCoord) {
		double newZoom = zoomLevel - (wheelRotation * zoomStep);
		newZoom = Math.max(maxZoomOutLevel, Math.min(maxZoomInLevel, newZoom));

		double ratio = newZoom / zoomLevel;
		viewOffset.x = (int) (mouseCoord.getX() - (mouseCoord.getX() - viewOffset.x) * ratio);
		viewOffset.y = (int) (mouseCoord.getY() - (mouseCoord.getY() - viewOffset.y) * ratio);

		zoomLevel = newZoom;
	}
	
	//offset to a new position for the view offset x and y
	public void transformView(int deltaX, int deltaY) {
		viewOffset.x += deltaX;
		viewOffset.y += deltaY;
	}
	
    public int posX(int delta, int tileSize) {
    	return (int) ((delta - viewOffset.x) / (tileSize * zoomLevel));
    }
    
    public int posY(int delta, int tileSize) {
    	return (int) ((delta - viewOffset.y) / (tileSize * zoomLevel));
    }

	public double getZoomLevel() {
		return zoomLevel;
	}
	
	public Point getViewOffset() {
		return viewOffset;
	}
}
