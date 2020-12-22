/*

Name: Prasad Hajare
CWID: A20232707
Course: CS5313
Assignment: 1
Question: 1
Program Name: DFSM Simulation

Description: This program takes two arguments DFSM configuration file name and a string cosisting alphabets accepted by the DFSM.
And outputs if the string is accepted by the DFSM or not.

Execution instructions:

javac prog1.java
java prog1

target.txt aabb

Sample output: Accepted

After first run keep passing strings to check if those will be accepted or not.

*/


//importing relevent java packages
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//defining the main class
public class prog1{

	public static void main(String[] args) {
		//declaring requried variables and datastructures
		String arguments, filename, input;
		String[] lastLine = null;
		Set<String> finalStates = new HashSet<>();
		Map<Integer, List> map = new HashMap<>();
		Map<String, Integer> alphabetsMap = new HashMap<>();

	    System.out.println("Please enter space seperated configuration file name and input string\neg. file.txt aaaa");
	    Scanner scanner = new Scanner(System.in);
	    arguments = scanner.nextLine();
	    String[] inputs = arguments.split(" ");

	//check if input argument is appropriate
	    if(inputs.length < 2) {
	    	System.out.println("Either filename or/and input string missing... please re-enter the input argument... execution terminated");
	    	System.exit(0);
	    }
	    filename = inputs[0];
	    input = inputs[1];
	    try {
	    	File text = new File(filename);
	        //Creating Scanner instnace to read File in Java
	        Scanner scnr = new Scanner(text);
	        //Reading each line of file using Scanner class
	        int lineNumber = 0;
	        while(scnr.hasNextLine()){
	            String line = scnr.nextLine();
	            if(line == null || line == "")
	            	continue;
	            if(lineNumber == 0) {
	            	String[] alphabets = line.split(" "); //["a", "b"]
	            	int index = 0;
	            	for(String s: alphabets) {
	            		alphabetsMap.put(s, index++);
	            	}
	            	lastLine = alphabets;
	            }else {
	            	String[] states = line.split(" ");// [1 2]
	            	lastLine = states;
	            	List<String> outputs = new ArrayList<String>(Arrays.asList(states));
	            	map.put(lineNumber, outputs);
	            }
	            lineNumber++;
	        }
	        for(int i = 0; i < lastLine.length; i++) {
	        	finalStates.add(lastLine[i]);
	        }
	    }catch(FileNotFoundException f) {	
        	System.out.println("Invalid file name... Re-enter the input");
        	System.exit(0);
        }

	//check if input file has adequate information
	    if(map.size() < 2) {
	    	System.out.println("Input file doesn't contain adequate information... program exiting");
	    	System.exit(0);
	    }

	    //System.out.println(finalStates);
	//strings will be accepted until user inputs ctrl+c or "exit"
	    while (!input.equalsIgnoreCase("exit")) {
			int intialState = 1, currentState = intialState;
			for (char c : input.toCharArray()) {
				String temp = c + "";
				if (alphabetsMap.containsKey(temp)) {
					currentState = Integer.parseInt((String) map.get(currentState).get(alphabetsMap.get(temp)));
				} else {
					System.out.println("One or more alphabets are invalid in a given string.... program exiting");
					System.exit(0);
				}
			}
			System.out.println(finalStates.contains(currentState + "") ? "Accepted" : "Rejected");
			System.out.println("Try someother string or if you want to exit please enter \"exit\"");
			input = scanner.nextLine();
		}
	    System.out.println("Exited Successfully!");
	}
}
