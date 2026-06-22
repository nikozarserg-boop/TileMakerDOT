package core;

import javax.swing.JScrollPane;

public class TabData {
	
	private String title;
	private JScrollPane component;

    public TabData(String title, JScrollPane component) {
        this.title = title;
        this.component = component;
    }

	public String getTitle() {
		return title;
	}

	public JScrollPane getComponent() {
		return component;
	}
}
