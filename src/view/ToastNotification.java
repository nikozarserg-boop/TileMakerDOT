package view;

public class ToastNotification extends ToastNotificationAbstract {
	
	private CanvasRenderer canvasRenderer;
	
	public ToastNotification(CanvasRenderer canvasRenderer) {
		this.canvasRenderer = canvasRenderer;
	}

	@Override
	public void repaintCanvas() {
		canvasRenderer.repaint();
	}
	
	@Override
    public void showToastNotification(String message) {
    	super.showToastNotification(message);
    	
    	//immediately redraw to show the toast
    	canvasRenderer.repaint();
    }
}
