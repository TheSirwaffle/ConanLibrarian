import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.neumont.csc250.lab4.Sorter;


public class QuickSorter<T extends Comparable<T>> implements Sorter<T> {

	@Override
	public void sort(List<T> toBeSorted) 
	{
		List<T> result = new ArrayList<T>();
		sortList(toBeSorted, null, result);
		toBeSorted.removeAll(toBeSorted);
		toBeSorted.addAll(result);
	}
	
	private void sortList(List<T> lessThan, List<T> greaterThan, List<T> result)
	{
		T first = lessThan.get(0);
		boolean allEqual = true;
		for(int i=1; i<lessThan.size() && allEqual;i++)
		{
			allEqual = lessThan.get(i) == first;
		}
		if(lessThan.size() < 2 || allEqual)
		{
			result.addAll(lessThan);
		}
		else
		{
			List<T> newLessThan = new ArrayList<T>();
			List<T> newGreaterThan = new ArrayList<T>();
			T pivot = findMedian(lessThan);
			for(int i=0; i<lessThan.size(); i++)
			{
				if(lessThan.get(i).compareTo(pivot) <= 0)
				{
					newLessThan.add(lessThan.get(i));
				}
				else
				{
					newGreaterThan.add(lessThan.get(i));
				}
			}
			
			sortList(newLessThan, newGreaterThan, result);
		}
		if(greaterThan != null)
		{
			if(greaterThan.size() < 2)
			{
				result.addAll(greaterThan);
				return;
			}
			
			if(greaterThan.size() > 0)
			{
				List<T> newLessThan2 = new ArrayList<T>();
				List<T> newGreaterThan2 = new ArrayList<T>();
				T pivot = findMedian(greaterThan);
				for(int i=0; i<greaterThan.size(); i++)
				{
					if(greaterThan.get(i).compareTo(pivot) <= 0)
					{
						newLessThan2.add(greaterThan.get(i));
					}
					else
					{
						newGreaterThan2.add(greaterThan.get(i));
					}
				}
				sortList(newLessThan2, newGreaterThan2, result);
			}
		}
	}
	
	private T findMedian(List<T> list)
	{
		int smallestIndex = 0;
		int largestIndex = 0;
		for(int i=0; i<list.size();i++)
		{
			smallestIndex = (list.get(i).compareTo(list.get(smallestIndex)) < 0 ) ? i:smallestIndex;
			largestIndex = (list.get(i).compareTo(list.get(largestIndex)) > 0 ) ? i:largestIndex;
		}
		Random rand = new Random();
		int value = smallestIndex;
		boolean skip = false;
		while((value == smallestIndex || value == largestIndex) && !skip)
		{
			value = rand.nextInt(list.size());
			skip = (list.size() < 3);
		}
		return list.get(value);
	}

}
