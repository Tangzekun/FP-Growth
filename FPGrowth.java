import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.Set;
import java.util.Map.Entry;

public class FPGrowth 
{
	
	static int minSup = 100;
	static int numTransaction = 0;
	
	public static void main(String[] args) 
	{	
		String csvFile = "/Users/tangzekun/Dropbox/CIS/CIS453/HW3/Code/FoodMart.csv";
		String [] foodName = null;
		int rowLength =0;
		 
		
		BufferedReader br = null;		
		ArrayList<int[]> fileContent = new ArrayList <int[]> ();

		try 
		{
			
			String line = "";
		    String nameLine = "";
            
			br = new BufferedReader(new FileReader(csvFile));
			nameLine = br.readLine();
    		foodName = nameLine.split(",");
    		rowLength = foodName.length;
			while ((line = br.readLine()) != null) 
			{

				String [] rowContentStr = line.split(",");
				int[] rowContent = new int[rowContentStr.length];
            	for(int i = 0;i < rowContentStr.length;i++)
            	{
            		rowContent[i] = Integer.parseInt(rowContentStr[i]);
            	}
            	fileContent.add(rowContent);
			}
			numTransaction = fileContent.size();
//			System.out.println(numTransaction);

		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (br != null) 
			{
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		HashMap <String, Integer> food = new HashMap <String, Integer>();
		for (int j = 0; j < numTransaction; j++)
		{	
			int [] rowStuff = fileContent.get(j);
			for (int i = 0; i< rowLength; i++)
			{					
				if (rowStuff[i] == 1)
				{
					if (food.containsKey(foodName[i]))
					{	
						food.put(foodName[i], food.get(foodName[i])+1);
					}
					else
					{	
						food.put(foodName[i], 1);
					}
				}
			}
			
		}
		
		HashMap<String, Integer> manyFood = new HashMap<String, Integer> ();
		for (int t= 0; t< rowLength; t++)
		{	
			if(food.get(foodName[t]) >= minSup)
			{	
				manyFood.put(foodName[t],food.get(foodName[t]));
			}
		}
		
		LinkedHashMap <String, Integer> highFrequentFood = new LinkedHashMap <String, Integer>();
		Set<Entry<String, Integer>> set = manyFood.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
		        {
		            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
		            {
		                return (o2.getValue()).compareTo( o1.getValue() );
		            }
		        } );
		
		        for(Map.Entry<String, Integer> entry:list)
				{
		            //System.out.println(entry.getKey()+" ==== "+entry.getValue());
					highFrequentFood.put(entry.getKey(),entry.getValue());
		        }
		
		
		
		
		
		ArrayList<List <String>> rawOrderedFileContent = new ArrayList <List <String>> ();
		
		for (int j = 0; j < numTransaction; j++)
		{	
			int [] rowStuff = fileContent.get(j);
			List <String> rawOrderedRowStuff = new ArrayList<String>();
			for (int i = 0; i< rowLength; i++)
			{
				if (rowStuff[i] == 1)
				{
					rawOrderedRowStuff.add(foodName[i]); 
				}
			}
			rawOrderedFileContent.add(rawOrderedRowStuff);
			//System.out.println((j+1)+ "~~~~~~~"+rawOrderedRowStuff);
		}
		
		
		ArrayList<List <String>> matureOrderedFileContent = new ArrayList <List <String>> ();
		
		
		for (int j = 0; j < numTransaction; j++)
		{
			List <String> rawRowStuff = rawOrderedFileContent.get(j);
			List <String> matureOrderedRowStuff = new ArrayList<String>();
			Set highFrequentFoodSet = highFrequentFood.entrySet();
			Iterator highFrequentFoodSetIterator = highFrequentFoodSet.iterator();
			while(highFrequentFoodSetIterator.hasNext())
			{	
				Map.Entry highFrequentFSI = (Map.Entry)highFrequentFoodSetIterator.next();
				
				for(int t= 0; t< rawRowStuff.size(); t++)
				{
					if ( highFrequentFSI.getKey().equals(rawRowStuff.get(t)))
					{
						matureOrderedRowStuff.add ((String) highFrequentFSI.getKey());
						//System.out.println((String) highFrequentFSI.getKey());
					}
					//System.out.println(highFrequentFSI.getKey());
				}
			}
			matureOrderedFileContent.add(matureOrderedRowStuff);
			System.out.println((j+1)+ "~~~~~~~"+matureOrderedRowStuff);
		}
		
		
		
		
	}
}




