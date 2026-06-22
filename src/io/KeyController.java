package io;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import view.CanvasRenderer;

public class KeyController extends KeyAdapter {

	private CanvasRenderer canvasRenderer;
	
	private boolean isShiftPressed = false;
	private boolean isCtrlPressed = false;
	private boolean isAltPressed = false;
	
	public KeyController(CanvasRenderer canvasRenderer) {
		this.canvasRenderer = canvasRenderer;
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = true;
            //repaint is necessary to immediately show the visual change
            canvasRenderer.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ALT) {
            isAltPressed = true;
            canvasRenderer.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            isCtrlPressed = true;
            canvasRenderer.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = false;
            canvasRenderer.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ALT) {
        	isAltPressed = false;
        	canvasRenderer.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
        	isCtrlPressed = false;
        	canvasRenderer.repaint();
        }
    }
    
	public void keyRelease() {
        isShiftPressed = false;
    	isAltPressed = false;
    	isCtrlPressed = false;
	}

	public boolean isShiftPressed() {
		return isShiftPressed;
	}

	public void setShiftPressed(boolean isShiftPressed) {
		this.isShiftPressed = isShiftPressed;
	}

	public boolean isCtrlPressed() {
		return isCtrlPressed;
	}

	public void setCtrlPressed(boolean isCtrlPressed) {
		this.isCtrlPressed = isCtrlPressed;
	}

	public boolean isAltPressed() {
		return isAltPressed;
	}

	public void setAltPressed(boolean isAltPressed) {
		this.isAltPressed = isAltPressed;
	}
}
