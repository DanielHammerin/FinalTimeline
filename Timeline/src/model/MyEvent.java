package model;

import javafx.scene.layout.StackPane;

public abstract class MyEvent {

	String title = "";
	String description = "";
	StackPane geometricFigure;

	public MyEvent (String t, String d){ // Constructor
		title = t;
		description = d;
	}

	public String getTitle() { // Getters and setters
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void deleteEvent(String title,String description){

	}
	public void editEvent(String title,String description){

	}

	public StackPane getGeometricFigure() {
		return geometricFigure;
	}

	public void setGeometricFigure(StackPane geometricFigure) {
		this.geometricFigure = geometricFigure;
	}
	public boolean equals(Object obj) {
		//there are compare rules
		return true;
	}

}