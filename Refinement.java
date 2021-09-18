package software;

import java.io.*;
import java.util.*;

/**
 * The class Refinement has the method readTextFile which reads the text from
 * the input file using BufferedReader. It throws relevant file handling
 * exceptions if required
 * 
 * @author Pragada Anurag - 1223151007
 * @author Gollapalli Varun - 1223130805
 * 
 */
public class Refinement {

	public static int currentElement;
	public static ArrayList<String> list = new ArrayList<>();

	/**
	 * The method readTextFile takes path as the input and reads the file using
	 * BufferedReader. Returns the complete text by reading line by line.
	 * 
	 * @param It accepts the file path as input.
	 * @return It returns text after reading line by line from the input file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readTextFile(String path) throws FileNotFoundException, IOException {

		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		String text = "";
		while ((str = br.readLine()) != null) {
			text = text + str + "A";
		}

		return text;
	}

	/**
	 * The method tokenizer takes in text as input and appends the Arraylist to
	 * return a list of tokens
	 * 
	 * @param It takes the text as input and segregates the tokens to add them into
	 *           the Arraylist.
	 * @return It returns the list of tokens
	 */
	public static ArrayList<String> tokenizer(String text) {
		StringBuilder temp = new StringBuilder();

		for (Character iterator : text.toCharArray()) {
			if (iterator == ' ') {
				continue;
			} else if (iterator == 'A') {
				if (!temp.isEmpty())
					list.add(temp.toString());
				temp = new StringBuilder();
			} else if (iterator == '(' || iterator == ')' || iterator == '{' || iterator == '}') {
				if (!temp.isEmpty())
					list.add(temp.toString());
				temp = new StringBuilder();
				list.add(iterator.toString());
			} else
				temp = temp.append(iterator);
		}
		return list;
	}

	/**
	 * Translate method checks for end of list and gives the control to
	 * methodHandler as method should be the starting point
	 * 
	 * @param We pass the tokens stored in the list as parameter to the translate
	 *           method.
	 */
	public static void translate(List<String> list) {

		while (currentElement < list.size()) {
			++currentElement;

			methodHandler();
			System.out.println();
		}

	}

	/**
	 * The ifHandler method prints the symbol("<") and gives the control to the next
	 * appropriate method. It throws an exception if it does not have the correct
	 * syntax.
	 */

	public static void ifHandler() {
		++currentElement;
		if (syntaxCheck()) {
			System.out.print("<");

			while (!list.get(currentElement).equals("}")) {
				if (list.get(currentElement).equals("if")) {
					ifHandler();
				} else if (list.get(currentElement).equals("while")) {
					whileHandler();
				} else if (list.get(currentElement).equals("\n")) {
					currentElement++;
				} else {
					instructionHandler();
				}
			}
			System.out.print(">");
			currentElement++;
		}

		else {
			throw new IllegalStateException("Could not translate. Syntax Error found");
		}
	}

	/**
	 * The whileHandler method prints the symbol("(") and gives the control to the
	 * next appropriate method. It throws an exception if it does not have the
	 * correct syntax.
	 */

	public static void whileHandler() {
		++currentElement;
		if (syntaxCheck() == true) {
			System.out.print("(");
			while (!list.get(currentElement).equals("}")) {
				if (list.get(currentElement).equals("if")) {
					ifHandler();
				} else if (list.get(currentElement).equals("while")) {
					whileHandler();
				} else if (list.get(currentElement).equals("\n")) {
					currentElement++;
				} else {
					instructionHandler();
				}
			}
			System.out.print(")");
			currentElement++;
		}

		else {
			throw new IllegalStateException("Could not translate. Syntax Error found");
		}
	}

	/**
	 * The instructionHandler method prints the symbol("-") and gives the control to
	 * the next appropriate method. It throws an exception if it does not have the
	 * correct syntax.
	 */

	public static void instructionHandler() {

		if (list.get(currentElement).equals("if")) {
			ifHandler();
		}
		if (list.get(currentElement).equals("while")) {
			whileHandler();
		}

		if (list.get(currentElement).equals("\n")) {
			currentElement++;
		} else if (list.get(currentElement).equals("(") || list.get(currentElement).equals(")")
				|| list.get(currentElement).equals("{")) {
			throw new IllegalStateException("Could not translate. Syntax Error found");
		} else {
			System.out.print("-");
			currentElement++;
		}
	}

	/**
	 * The methodHandler method prints the symbol("[") and gives the control to the
	 * next appropriate method. It throws an exception if it does not have the
	 * correct syntax.
	 */
	public static void methodHandler() {

		if (syntaxCheck()) {
			System.out.print("[");

			while (!list.get(currentElement).equals("}")) {

				if (list.get(currentElement).equals("\n") || list.get(currentElement).equals(" ")) {
					currentElement++;
				}

				else if (list.get(currentElement).equals("while")) {
					whileHandler();
				} else if (list.get(currentElement).equals("if")) {
					ifHandler();
				} else {
					instructionHandler();
				}
			}
			System.out.print("]");
			currentElement++;
		}

		else {
			throw new IllegalStateException("Could not translate. Syntax Error found");
		}

	}

	/**
	 * The method syntaxCheck is for checks for the syntax
	 * 
	 * @return It returns true if syntax is correct else returns false
	 */

	public static boolean syntaxCheck() {

		if (list.get(currentElement).equals("\n")) {
			currentElement++;
		}

		if (list.get(currentElement).equals("(")) {
			currentElement++;
			if (list.get(currentElement).equals(")")) {

				currentElement++;
				if (list.get(currentElement).equals("{")) {
					currentElement++;
					return true;
				}
			}
		}
		return false;

	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String path = sc.nextLine();
		String text = readTextFile(path);
		ArrayList<String> input = tokenizer(text);
		translate(input);

	}
}
