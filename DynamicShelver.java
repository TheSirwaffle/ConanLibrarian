import java.util.ArrayList;
import java.util.List;

import edu.neumont.csc250.lab4.Book;
import edu.neumont.csc250.lab4.Bookcase;
import edu.neumont.csc250.lab4.Shelver;


public class DynamicShelver implements Shelver {

	@Override
	public void shelveBooks(Bookcase bookcase, List<Book> books) {
		reverseSortBooks(books);
		placeBooksOnShelf(bookcase, books);
		System.out.println("Shelf width: " + bookcase.getShelfWidth());
		for(int i=0; i<bookcase.getNumberOfShelves();i++)
		{
			System.out.println("Row #" + i + ":  Space Left: " + bookcase.getBookshelf(i).getSpaceLeft());
		}
	}
	
	private void placeBooksOnShelf(Bookcase bookcase, List<Book> books)
	{
		int maxValue = -1;
		int[][] theoreticalSpace = new int[books.size()][books.size()];
		int shelfWidth = bookcase.getShelfWidth();
		for(int i=0; i<books.size(); i++)
		{
			int spaceLeft = shelfWidth;
			for(int j=0; j<books.size(); j++)
			{
				if(j<i || spaceLeft <= 0)
				{
					theoreticalSpace[i][j] = maxValue;
				}
				else if(spaceLeft > 0)
				{
					theoreticalSpace[i][j] = (spaceLeft - books.get(j).getWidth() >= 0)? spaceLeft - books.get(j).getWidth():maxValue;
					spaceLeft -= shelfWidth - theoreticalSpace[i][j];
				}
			}
		}
		for(int i=0; i<books.size(); i++)
		{
			for(int j=i; j<books.size(); j++)
			{
				if(theoreticalSpace[i][j] != -1)
				{
					theoreticalSpace[i][j] = (int) Math.pow(theoreticalSpace[i][j], 2);
				}
			}
		}
		int[] cost = new int[books.size()+1];
		int[] p = new int[books.size()+1];
		cost[0] = 0;
		for(int j=0; j< books.size(); j++)
		{
			cost[j+1] = 90000;
			for(int i=0; i <= j; i++)
			{
				if(theoreticalSpace[i][j] != maxValue && (cost[i] + theoreticalSpace[i][j] < cost[j+1]))
				{
					cost[j+1] = cost[i] + theoreticalSpace[i][j];
					p[j+1] = i;
				}
			}
		}
		ArrayList<List<Book>> order = new ArrayList<List<Book>>();
		for(int i=p.length-1; i>0;)
		{
			List<Book> shelfBooks = new ArrayList<Book>();
			for(int j=i-p[i]-1; j>=0;j--)
			{
				shelfBooks.add(books.get(i-1-j));
			}
			order.add(shelfBooks);
			i = p[i];
		}
		
		for(int i=0; i<order.size()/2; i++)
		{
			List<Book> temp = order.get(i);
			order.set(i, order.get(order.size()-1-i));
			order.set(order.size()-1-i,  temp);
		}
		
		for(int i=0; i<order.size();i++)
		{
			for(int j=0; j<order.get(i).size(); j++)
			{
				System.out.print("["+order.get(i).get(j).getWidth()+"]");
				bookcase.addBook(i, order.get(i).get(j));
			}
			System.out.println();
		}
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
