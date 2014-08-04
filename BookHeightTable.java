
public class BookHeightTable implements Comparable<BookHeightTable>
	{
		public int bookIndex;
		public int height;
		
		public BookHeightTable(int height, int index)
		{
			this.height = height;
			bookIndex = index;
		}

		@Override
		public int compareTo(BookHeightTable o) {
			// TODO Auto-generated method stub
			return height-o.height;
		}
	}
