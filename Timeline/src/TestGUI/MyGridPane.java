//package TestGUI;
//
//import javafx.geometry.HPos;
//import javafx.scene.Node;
//import javafx.scene.control.Label;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.RowConstraints;
//
//public class MyGridPane extends GridPane {	
//
//	public MyGridPane(){		
//		this.setStyle("-fx-background-color: #FAFBFF;");
//		this.getRowConstraints().add(addRow());
//		this.getRowConstraints().add(addRow());
//		this.getRowConstraints().add(addRow());
//	}
//	
//	public RowConstraints addRow(){
//		RowConstraints r = new RowConstraints();
//		r.setMaxHeight(50);
//		r.setMinHeight(50);
//		return r;
//	}
//	
//	public void addColumns(int amountColumns){		
//		for(int i=0;i<amountColumns;i++){
//			ColumnConstraints c = new ColumnConstraints();
//			c.setHalignment(HPos.CENTER);
//			c.setMaxWidth(100);
//			c.setMinWidth(100);
//			Label lab = new Label("Column"+ i);
//			this.getColumnConstraints().add(c);
//			this.add(lab, i, 0);
//		}	
//	}
//	
//	public void addEvent(GraphicalEvent ge, int columnIndex, int rowIndex, int colSpan){		
//		this.add(ge, columnIndex, rowIndex,colSpan, 1);
//		Node n = getNodeFromGridPane(this, columnIndex, rowIndex);
//		n.layoutXProperty().set(50);
//	}
//	
//	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
//	    for (Node node : gridPane.getChildren()) {
//	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
//	            return node;
//	        }
//	    }
//	    return null;
//	}
//	
//
//}
