/*
Name: Prasad Hajare
Program Name: DFSM Builder

Description: This program takes two arguments target file name and a pattern to be recognized by the DFSM.
And saves the DFSM configuration i.e. alphabets, trasition table and final state in the target file.

Execution instructions:

javac prog2.java
java prog2

target.txt abc

Sample output: File Saved: target.txt
To chech the content of the file
cat target.txt or nano target.txt

*/


// relevent java packages import
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

//defining main class
public class prog2{
    @SuppressWarnings("resource")
	public static void main(String[] args) {
	//declaring required variables and datastructures
        String arguments, filename, input, alphabetStringtoWrite = "";
        Map<String, Integer> map = new HashMap<>();
        TreeSet<Character> set = new TreeSet<>();
	System.out.println("Please enter space seperated target file name and pattern\neg. file.txt abc");
	    Scanner scanner = new Scanner(System.in);
	    arguments = scanner.nextLine();

	    String[] inputs = arguments.split(" ");

	//input arguments validity check
	    if(inputs.length < 2) {
	    	System.out.println("Either filename or input string missing... please re-enter the input argument... execution terminated");
	    	System.exit(0);
	    }
        filename = inputs[0];
        input = inputs[1];

	//final implementation
        String temp = "";
        Integer rememberingState = 1;
        map.put(temp, rememberingState++);
        for(char c: input.toCharArray()) {
            set.add(c);
            temp= temp+c;
            map.put(temp, rememberingState++);
        }
        //set.add('*');
        Map<Integer, Character> alphabetsMap = new HashMap<>();
        Iterator it = set.iterator();
        int index = 0;
        while(it.hasNext()) {
        	Character c = (Character) it.next();
        	alphabetStringtoWrite += c + " ";
            alphabetsMap.put(index++, c);
        }
        int[][] array = new int[input.length()+1][set.size()];
        //boolean reachedFinalState = false;
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < array.length; i++) {
            String check = input.substring(0, i);
            for(int j = 0; j < array[0].length; j++) {
            	if(i == array.length-1)
            		array[i][j] = array.length;
            	else {
            		StringBuilder doubleCheck = new StringBuilder(check);
                    doubleCheck.append(alphabetsMap.get(j));
                    if(map.containsKey(doubleCheck.toString())) {
                        array[i][j] = map.get(doubleCheck.toString());
//                        if(set.contains(array[i][j])) {
//                        	reachedFinalState = true;
//                          System.out.println("Once it reaches to the final state it will always remain in final state in it will be accepted by DFSM");
//                        }
                    }else {
                        while(!map.containsKey(doubleCheck.toString())) {
                            doubleCheck.deleteCharAt(0);
                        }
                        array[i][j] = map.get(doubleCheck.toString());
                    }
            	}
            }
        }

	//creating the target file and writing the configurations to the file
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }

            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(alphabetStringtoWrite + "\n");
            for(int i = 0; i < array.length; i++) {
            	String write = "";
                for(int j = 0; j < array[0].length; j++) {
                    write += array[i][j] + " ";
                }
                myWriter.write(write+"\n");
            }
            myWriter.write(array.length+"");
            myWriter.close();
            System.out.println("Successfully saved the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
