import java.util.ArrayList;
import java.util.List;

import edu.neumont.csc250.lab4.Book;
import edu.neumont.csc250.lab4.Bookcase;
import edu.neumont.csc250.lab4.Shelver;


public class GreedyShelver implements Shelver {

	@Override
	public void shelveBooks(Bookcase bookcase, List<Book> books) 
	{
		reverseSortBooks(books);
		for(Book b: books)
		{
			System.out.println("Height: " + b.getHeight() + " Width: " + b.getWidth());
		}
		placeOnShelf(bookcase, books);
		for(int i=0; i<bookcase.getNumberOfShelves();i++)
		{
			System.out.println(bookcase.getBookshelf(i).getSpaceLeft());
		}
	}
	
	private void placeOnShelf(Bookcase bookcase,List<Book> books)
	{
		int widthLeft = bookcase.getShelfWidth();
		int currentShelf = 0;
		for(int i=0; i<books.size(); i++)
		{
			if(widthLeft - books.get(i).getWidth() >= 0)
			{
				bookcase.addBook(currentShelf, books.get(i));
				System.out.print("["+books.get(i).getWidth()+"]");
				widthLeft -= books.get(i).getWidth();
			}
			else if(currentShelf+1<bookcase.getNumberOfShelves())
			{
				System.out.println();
				currentShelf++;
				widthLeft = bookcase.getShelfWidth();
				if(widthLeft - books.get(i).getWidth() < 0)
				{
					System.out.println("A book was too wide to fit on a shelf by itself, so it was skipped.");
				}
				else
				{
					bookcase.addBook(currentShelf, books.get(i));
					System.out.print("["+books.get(i).getWidth()+"]");
					widthLeft -= books.get(i).getWidth();
				}
			}
			else
			{
				System.out.println("Ran out of shelf space");
			}
		}
		System.out.println();
	}
	
	private void reverseSortBooks(List<Book> books)
	{
		List<BookHeightTable> heights = new ArrayList<BookHeightTable>();
		for(int i=0; i<books.size(); i++)
		{
			heights.add(new BookHeightTable(books.get(i).getHeight(), i));
		}
		MergeSorter<BookHeightTable> sorter = new MergeSorter<BookHeightTable>();
		sorter.sort(heights);
		for(int i=0; i<books.size()/2; i++)
		{
			BookHeightTable temp = heights.get(i);
			heights.set(i, heights.get(heights.size()-1-i));
			heights.set(heights.size()-1-i, temp);
		}
		List<Book> sortedBooks = new ArrayList<Book>();
		for(int i=0; i<heights.size(); i++)
		{
			sortedBooks.add(books.get(heights.get(i).bookIndex));
		}
		books.removeAll(books);
		books.addAll(sortedBooks);
	}

}
