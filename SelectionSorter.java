import java.util.ArrayList;
import java.util.List;

import edu.neumont.csc250.lab4.Sorter;


public class SelectionSorter<T extends Comparable<T>> implements Sorter<T> {

	@Override
	public void sort(List<T> unsorted) {
		int i,j;
		for(i=0; i<unsorted.size()-1; i++)
		{
			T value = unsorted.get(i);
			int index = i;
			for(j=i+1;j<unsorted.size();j++)
			{
				if(unsorted.get(j).compareTo(value) < 0)
				{
					value = unsorted.get(j);
					index = j;
				}
			}
			
			if(index != i)
			{
				T temp = unsorted.get(index);
				T iValue = unsorted.get(i);
				unsorted.set(i, temp);
				unsorted.set(index, iValue);
			}
			
		}
		
	}

}
