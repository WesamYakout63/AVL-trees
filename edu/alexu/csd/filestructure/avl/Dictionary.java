package eg.edu.alexu.csd.filestructure.avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements IDictionary {
	private AVLTree T = new AVLTree();
	
	public void printDictionary(){
		T.print((node) T.getTree());
	}
	
	public void load(File file) {
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(input.hasNextLine()){
			T.insert(input.nextLine());
			T.print((node) T.getTree());
			System.out.println();
		}
	}

	public boolean insert(String word) {
		if(exists(word)){
			System.out.println("Word already exists!!");
			return false;
		}
		else{
			T.insert(word);
			System.out.println("Successfully inserted!!");
			return true;
		}
	}
	
	public boolean exists(String word) {
		if(T.search(word)){
			System.out.println("Word exists!!");
			return true;
		}
		else{
			System.out.println("Word doesn`t exist!!");
			return false;
		}
	}

	public boolean delete(String word) {
		if(!exists(word)){
			System.out.println("Word doesn`t exist!!");
			return false;
		}
		else{
			T.delete(word);
			System.out.println("Successfully deleted!!");
			return true;
		}
	}

	public int size() {
		return T.nodesNumber((node) T.getTree());
	}

	public int height() {
		return T.height();
	}
	
	public static void main(String[] args) throws IOException{
		Dictionary d = new Dictionary();
		File file = new File("E://Engineering//C++//DataStructure2//src//eg//edu//alexu//csd//filestructure//avl//Dictionary.txt");
		d.load(file);
		d.insert("wesam");
		d.delete("Grapes");
		d.printDictionary();
		System.out.println(d.size());
	}

}
