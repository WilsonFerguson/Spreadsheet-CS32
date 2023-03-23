// CS3 Spreadsheet Spreadsheet class.  Fill in the details.

import java.util.Scanner;

public class Spreadsheet {
	public static Scanner console = new Scanner(System.in);

	public static void main(String[] args) {
		Sheet sheet = new Sheet();
		// TODO in checkpoint 1: finish implementing by adding your own code here
		// with a loop to read lines of user input until the user enters quit,
		// calling sheet.processCommand with each line and printing the result to the
		// console.
		while (true) {
			System.out.print("Enter a command: ");
			String command = console.nextLine();
			if (command.toLowerCase().equals("quit")) {
				break;
			}
			System.out.println(sheet.processCommand(command));
		}
	}
}
