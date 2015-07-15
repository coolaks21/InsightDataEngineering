package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Action {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 BufferedReader br = null;
		 BufferedWriter bw = null,bw2 = null;
		  
		  try {
		 
		   TreeMap<String,Integer> map = new TreeMap<String,Integer>();
		   HashSet<String> lineOfMsg = null;
		   ArrayList<Integer> lengthsOfTweetsUnsorted = new ArrayList<Integer>();
		   ArrayList<Integer> lengthsOfTweetsSorted = new ArrayList<Integer>();
		   String sCurrentLine;
		   
		   //Getting input file address from user
		   String addressOfInputFile = null;
		   JFrame frame = new JFrame();
		   addressOfInputFile = JOptionPane.showInputDialog(frame, "Enter address directory of tweets.txt file");

		   br = new BufferedReader(new FileReader(addressOfInputFile +"/Tweets.txt"));
		            //This while loop runs for each line
		   while ((sCurrentLine = br.readLine()) != null) {
		    String word = null;
		    //Creating new HashSet object
	    	lineOfMsg = new HashSet<String>();
		    //this for loop runs for a length of a line
		    for(int rowCounter = 0; rowCounter < sCurrentLine.length(); rowCounter++){
		    
		    	if((int)sCurrentLine.charAt(rowCounter) != 32 ){
		    	if(word == null){
	        		word = String.valueOf(sCurrentLine.charAt(rowCounter));
	        	}
	        	else{
	        		word = word + sCurrentLine.charAt(rowCounter);
	        	}
		    	}
		    	
		    	//rowCounter != 0 avoids the condition when first char of a word is space
		        if(((int)sCurrentLine.charAt(rowCounter) == 32 && rowCounter != 0 && word != null) || ((rowCounter == sCurrentLine.length() - 1) && word != null)){
		        	//Adding the new DISTINCT word to line, distinct is used because hashset allows only distinct entries
		            lineOfMsg.add(word);
		            if(map.containsKey(word)){
		            map.replace(word, map.get(word) + 1);
		            word = null;
		            }
		            else{
		        	map.put(word, 1);
		        	word = null;
		            }
		           
		        }
		    	
		        
		    }
		    //Adding the length of current line
		    lengthsOfTweetsUnsorted.add(lineOfMsg.size());
		    lineOfMsg = null;
		   }
		   
		   
		   //Writing to text file, which we store beside input file.
		   
			File file = new File(addressOfInputFile+"/ft1.txt");
			

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for(String word : map.keySet()){
			    System.out.println(word +" "+map.get(word));
			    bw.write(word +" "+String.valueOf(map.get(word)));
			    bw.newLine();
			}
			
			File file2 = new File(addressOfInputFile+"/ft2.txt");
			// if file doesnt exists, then create it
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			bw2 = new BufferedWriter(fw2);
			double medianOfUniqueWordsPerTweet = 0;
			for(int tweetCounter = 0; tweetCounter < lengthsOfTweetsUnsorted.size(); tweetCounter++){
				//As we want a running median, the sorted list is kept empty first and elements are added
				// to it from unsorted list step by step and then it is sorted at each step
				lengthsOfTweetsSorted.add(lengthsOfTweetsUnsorted.get(tweetCounter));
				Collections.sort(lengthsOfTweetsSorted);
				//for even entries
				if((tweetCounter+1) % 2 == 0){
						medianOfUniqueWordsPerTweet = (double)(lengthsOfTweetsSorted.get(((tweetCounter+1)/2) - 1) + lengthsOfTweetsSorted.get((tweetCounter+1)/2))/2;
				}
				//for odd entries
				else{
					medianOfUniqueWordsPerTweet = lengthsOfTweetsSorted.get((tweetCounter+1)/2);
				}
				//Writing the medians to file
				bw2.write(String.valueOf(medianOfUniqueWordsPerTweet));
			    bw2.newLine();
			    System.out.println("No of unique words in this tweet are "+lengthsOfTweetsUnsorted.get(tweetCounter)+" and median is "+medianOfUniqueWordsPerTweet);
			}	
				
			
		  } catch (IOException e) {
		   e.printStackTrace();
		  } finally {
		   try {
		    if (br != null)br.close();
		    if(bw != null)bw.close();
		    if(bw2 != null)bw2.close();
		   } catch (IOException ex) {
		    ex.printStackTrace();
		   }
		  }
		  
		  
		  

	}

}
