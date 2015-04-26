package dbTesting;

public class Book {
	
	String title;
	String author;
	
	Book (){
	}
	
	Book (String newTitle, String newAuthor){
		title = newTitle;
		author = newAuthor;
	}
	
	public void setTitle (String newTitle){
		title = newTitle;
	}
	
	public String returnTitle (){
		return title;
	}
	
	public void setAuthor (String newAuthor){
		author = newAuthor;
	}
	
	public String returnAuthor (){
		return author;
	}

}
