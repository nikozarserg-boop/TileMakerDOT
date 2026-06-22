package view;

import javax.swing.Timer;

public abstract class ToastNotificationAbstract {

	private String currentToastMessage = null;
    private Timer toastTimer;
    private static final int TOAST_DURATION_MS = 1500;
    
	public void initToastNotification() {
        //initialized the timer using a lambda
        toastTimer = new Timer(TOAST_DURATION_MS, e -> {
            currentToastMessage = null; //clear the message
            toastTimer.stop();          //stop the timer
            repaintCanvas();            //redraw the canvas to remove the toast
        });
        toastTimer.setRepeats(false); //ensure it only runs once
	}
	
	public abstract void repaintCanvas();

	//call this method to display a message
    public void showToastNotification(String message) {
        //if a message is already showing, stop the current timer
        if (toastTimer.isRunning()) {
            toastTimer.stop();
        }
        
        this.currentToastMessage = message;
        
        //start the timer relying on the duration set in the constructor
        toastTimer.start();
    }

	public String getCurrentToastMessage() {
		return currentToastMessage;
	}
}
