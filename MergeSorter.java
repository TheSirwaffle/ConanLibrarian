import java.util.ArrayList;
import java.util.List;

import edu.neumont.csc250.lab4.Sorter;


public class MergeSorter<T extends Comparable<T>> implements Sorter<T> {

	@Override
	public void sort(List<T> toBeSorted) 
	{
		ArrayList<List<T>> sections = new ArrayList<List<T>>();
		sections.add(toBeSorted);
		sections = splitList(sections);
		toBeSorted.removeAll(toBeSorted);
		toBeSorted.addAll(sortLists(sections));
	}
	
	private List<T> sortLists(ArrayList<List<T>> lists)
	{
		if(lists.size() == 1)
		{
			return lists.get(0);
		}
		int index = 0;
		ArrayList<List<T>> newLists = new ArrayList<List<T>>();
		while(lists.size() > index+1)
		{
			List<T> first = lists.get(index++);
			List<T> second = lists.get(index++);
			List<T> merged = new ArrayList<T>();
			int firstIndex =0;
			int secondIndex = 0;
			for(int i=0; i<first.size()+second.size(); i++)
			{
				if(firstIndex < 0)
				{
					merged.add(second.get(secondIndex++));
				}
				else if(secondIndex < 0)
				{
					merged.add(first.get(firstIndex++));
				}
				else if(first.get(firstIndex).compareTo(second.get(secondIndex)) > 0)
				{
					merged.add(second.get(secondIndex++));
					secondIndex = (secondIndex == second.size())?-1:secondIndex;
				}
				else
				{
					merged.add(first.get(firstIndex++));
					firstIndex = (firstIndex == first.size())?-1:firstIndex;
				}
			}
			newLists.add(merged);
		}
		if(lists.size()%2 != 0)
		{
			newLists.add(lists.get(lists.size()-1));
		}
		return sortLists(newLists);
	}
	
	private ArrayList<List<T>> splitList(ArrayList<List<T>> lists)
	{
		if(lists.get(0).size() < 2)
		{
			return lists;
		}
		ArrayList<List<T>> newLists = new ArrayList<List<T>>();
		for(List<T> list: lists)
		{
			List<T> firstList = new ArrayList<T>();
			List<T> secondList = new ArrayList<T>();
			for(int i=0; i<(list.size()+1)/2; i++)
			{
				firstList.add(list.get(i));
			}
			for(int j=(list.size()+1)/2;j<list.size();j++)
			{
				secondList.add(list.get(j));
			}
			newLists.add(firstList);
			if(secondList.size() > 0)
			{
				newLists.add(secondList);
			}
		}
		return splitList(newLists);
	}

}
