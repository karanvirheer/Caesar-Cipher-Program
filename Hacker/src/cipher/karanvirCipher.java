package cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * Contains the most crucial methods needed for deciphering and encrypting.
 * 
 * @author Karanvir Heer
 * 
 */

public class karanvirCipher {
	// Creates a string containing the alphabet
	public static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Shifts the alphabet to the left or the right, depending on the
	 * key given
	 * 
	 * @param alphabet The alphabet in uppercase
	 * 
	 * @param key The amount that the letters/characters needs to shift
	 * 
	 * @return encrAlpha (the encrypted alphabet)
	 */
	private static String shift(String alphabet, int key) {
		// TODO

		
		// Allows there to be keys greater than 26
		key = key % 26;

		String encrAlpha = "";

		// Creates a character array which is the length of the alphabet
		// Then fills the array with the values in the alphabet string
		char[] alpha = new char[alphabet.length()];
		alpha = alphabet.toCharArray();

		
		if (key < 0) {
			
			// If key is negative, it's added to length of the alphabet
			key = 26 + key;

			// Loops through the length of the newly made alphabet array
			for (int index = 0; index < alpha.length; index++) {

				if (index + key < alpha.length) {
					encrAlpha += alpha[index + key];

					// In case the index + key is larger than the length of the
					// char array, it is subtracted from the length of the alphabet so it
					// stays within the index of the array itself
				} else {
					encrAlpha += alpha[index - (26 - key)];
				}
			}
		}
		// If the key is positive
		else {

			// Loop documented above, as it is the same code.
			for (int index = 0; index < alpha.length; index++) {

				if (index + key < alpha.length) {
					encrAlpha += alpha[index + key];
				} else {
					encrAlpha += alpha[index - (26 - key)];
				}
			}
		}
		return encrAlpha;
	}

	/**
	 * Encrypts a message depending on the key given
	 * 
	 * @param message The message that needs to be encrypted
	 * 
	 * @param key The amount that the letters/characters needs to shift
	 * 
	 * @return cipherText The encrypted message
	 */
	public static String encrypt(String message, int key) {
		// TODO

		// Allows there to be keys greater than 26
		key = key % 26;

		String cipherText = "";

		// Converts the message to the uppercase, to match the alphabet string,
		// as it is case sensitive
		message = message.toUpperCase();

		// Generates a random number.
		Random rand = new Random();

		// If negative key
		if (key < 0) {

			key = 26 + key;

			for (int i = 0; i < message.length(); i++) {
				// Gets the index of the character from the message that matches
				// with the character in the alphabet
				// Example: Message says 'Hi', now the index of H is __ and I is
				// __ in the alphabet.
				int charPosition = alpha.indexOf(message.charAt(i));

				// Adds the keyshift to the index of the character, and mods
				// it by 26
				// This is since any number that is mod by 26 would be less than
				// 26.
				int keyVal = (key + charPosition) % 26;

				// Takes the calculated value above and makes it an index
				// number.
				// Now the character at that index value in the alphabet is
				// selected.
				// Example: We get a value of 2 from the calculation above. Now
				// find the character with the index 2 in alphabet.
				char replaceVal = alpha.charAt(keyVal);

				// That new character now adds onto the cipherText.
				cipherText += replaceVal;

				// Generates a random number between 0 and 100
				// Gives a 25% chance of adding space to the encrypted message
				if (rand.nextInt(100) < 25) {
					cipherText += " ";
				}
			}

			// If positive key
		} else {

			// Loop is documented above.
			for (int i = 0; i < message.length(); i++) {

				int charPosition = alpha.indexOf(message.charAt(i));

				int keyVal = (key + charPosition) % 26;

				char replaceVal = alpha.charAt(keyVal);

				cipherText += replaceVal;

				// Adds a 25% chance of creating spaces in the encrypted text
				if (rand.nextInt(100) < 25)
					cipherText += " ";
			}
		}

		return cipherText;
	}

	/**
	 * Decrypts the message with given key
	 * 
	 * @param cipherText The text that needs to be decrypted 
	 * 
	 * @param key The amount that the letters/characters needs to shift
	 * 
	 * @return decrText The decrypted text
	 */
	public static String decrypt(String cipherText, int key) {
		// TODO

		
		// Allows there to be keys greater than 26
		key = key % 26;

		String decrText = "";

		cipherText = cipherText.toUpperCase();

		// Runs for as long as the length of the given message 
		for (int i = 0; i < cipherText.length(); i++) {

			// Gets the index of the character that is both in the alphabet and the given text
			int charPosition = alpha.indexOf(cipherText.charAt(i));

			// Reversing the method that the encryption used here
			// Instead of adding the key and the character's index, 
			// We subtract
			int keyVal = (charPosition - key) % 26;

			// If the value given is less than 0, we make it positive
			// by simply adding it to the length of the alphabet
			if (keyVal < 0) {
				keyVal = alpha.length() + keyVal;
			}

			// Finds the character in its original place and stores it
			char replaceVal = alpha.charAt(keyVal);

			decrText += replaceVal;
		}
		return decrText.toLowerCase();
	}

	/**
	 * Brute forces through the encrypted message, whilst not given a
	 * key, and outputs the decrypted message in the selected language. 
	 * 
	 * @param encrText The text that needs to be decrypted is here
	 * 
	 * @param lang The language chosen by the user
	 * 
	 * @param search The type of search (advanced or simple) chosen by the user
	 * 
	 * @return decrText The decrypted text
	 */
	public static String hackerman(String encrText, int lang, int search) {

		// Initializes the BufferedWriter
		BufferedWriter writer;

		String decrText = "";

		int HighScore = 0;

		encrText = encrText.toUpperCase();

		// Sets the file path for which the .txt file containing the words will be made
		// and what it will be named
		File file = new File(".\\Dictionary\\MessageWord.txt");

		// Initializes and starts the BufferedWriter which will write to a file
		try {
			file.createNewFile();
			writer = new BufferedWriter(new FileWriter(file));

			// iterates through the key values of -25 to 25
			for (int genKey = 0; genKey < 26; genKey++) {
				
				int score = 0;

				// Chooses a dictionary file for the specified language
				String chosenLang = "";

				switch (lang) {
				
				case 1:
					
					//Lets you choose between a larger dictionary file, or a smaller dictionary file
					switch (search) {

					case 1:
						String ITL = ".\\Dictionary\\ItalianDictionary.txt";
						chosenLang = ITL;
						break;

					case 2:
						String quickITL = ".\\Dictionary\\500Italian.txt";
						chosenLang = quickITL;
						break;
					}
					
					break;
				case 2:

					switch (search) {
					case 1:
						String FR = ".\\Dictionary\\FrenchDictionary.txt";
						chosenLang = FR;
						break;
					case 2:
						String quickFR = ".\\Dictionary\\500French.txt";
						chosenLang = quickFR;
						break;
					}
					break;
				case 3:
					
					switch (search) {
					case 1:
						String ENG = ".\\Dictionary\\EnglishDictionary.txt";
						chosenLang = ENG;
					break;
					
					case 2:
						String quickENG = ".\\Dictionary\\500English.txt";
						chosenLang = quickENG;
						break;
					}
					break;
				default:
					System.out.println("Please restart and choose a language.");
					break;
				}

				// reads the dictionary file line by line and compares it to the
				// results given
				try (BufferedReader reader = new BufferedReader(

						new FileReader(chosenLang))) {

					String word = "";

					while ((word = reader.readLine()) != null) {
						
						if (decrypt(encrText, genKey).contains(word)) {
							
							score++;
					
							// Writes whatever words match with the decrypted words onto a file
							writer.write(word);
							writer.newLine();
						}
					}

				// catches any errors caused by the BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}

				// If the high score of the newest word is larger 
				//than the score, then make that the new HighScore
				if (score > HighScore) {
					HighScore = score;
					
					decrText = decrypt(encrText, genKey);
				}
			}
			// closes the PrintWriter
			writer.close();

		// Catches any errors from the PrintWriter
		} catch (IOException e) {
			System.out.println(e);
		}
		
		return decrText;
	}

	/**
	 * A method used for testing the functionality of the BufferedReader
	 * 
	 * @param fName The name of the files directory.
	 * 
	 */
	public static void buffer(String fName) {
		
		// reads the dictionary file line by line and compares it to the results given
		try (BufferedReader reader = new BufferedReader(new FileReader(fName))) {

			String word = "";

			while ((word = reader.readLine()) != null) {
				System.out.println(word);
			}

			// catches any errors caused by the BufferedReader
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initializes the hackerman() method and gets the message from the
	 * user.
	 * 
	 * @param lang The language the user wants to encrypt/decrypt
	 * 
	 * @param graph The type of graph that user wants displayed
	 * 
	 * @param search The type of search (advanced or simple) chosen by the user
	 * 
	 */

	public static void callHack(int lang, int graph, int search) {

		Scanner reader = new Scanner(System.in);

		System.out.println("\nINPUT ------------- \nInput message: ");
		String decrmessage = reader.nextLine();

		reader.close();

		System.out.println("\nMessage: " + hackerman(
				decrmessage.replace(" ", "").replaceAll("[1234567—890!@#$%^&*()_+|\\-=~`{}\\[\\]:;\"<>,.?/]", ""),
				lang, search));

		callGraph(graph);
	}
	
	/**
	 * Takes the message and key, then outputs the cipher, initial message, 
	 * encrypted message, and decrypted message in console.
	 */

	public static void callCaesar() {

		Scanner reader = new Scanner(System.in);

		// Get message from user
		System.out.println("\nInput message: ");
		String message = reader.nextLine();

		// Get key from user
		System.out.println("Desired key: ");
		int key = reader.nextInt();

		// Print the message and the key inputed
		System.out.println("\nInput Message: " + message);

		System.out.println("Key: " + key);

		reader.close();

		// Printing Cipher, Message, Encrypted, and Decrypted text.
		System.out.println("Cipher: " + (shift(alpha, key)));

		System.out.println("Initial Message: " + message);

		String encryptedMsg = encrypt(
				message.replace(" ", "").replaceAll("[1234567—890!@#$%^&*()_+|\\-=~`{}\\[\\]:;\"<>,.?/]", ""), key);

		System.out.println("Encrypted message: " + encryptedMsg);

		String decryptedMsg = decrypt(
				encryptedMsg.replace(" ", "").replaceAll("[1234567—890!@#$%^&*()_+|\\-=~`{}\\[\\]:;\"<>,.?/]", ""),
				key);

		System.out.println("Decrypted message: " + decryptedMsg);

	}

	/**
	 * Takes the message and key, then outputs just the 
	 * encrypted message in the console.
	 */
	public static void callEncrypt() {
		Scanner reader = new Scanner(System.in);

		int encrChoice = 0;

		// Get message from user
		System.out.println("\nINPUT------------ \nInput message: ");
		String message = reader.nextLine();

		// Get key from user
		System.out.println("Desired key: ");
		int key = reader.nextInt();

		// Printing Cipher, Message, Encrypted, and Decrypted text.
		System.out.println("\nOUTPUT ---------------- \nCipher: " + (shift(alpha, key)));

		System.out.println("Initial Message: " + message);

		String encryptedMsg = encrypt(
				message.replace(" ", "").replaceAll("[1234567—890!@#$%^&*()_+|\\-=~`{}\\[\\]:;\"<>,.?/]", ""), key);

		System.out.println("Encrypted message: " + encryptedMsg);

	}

	/**
	 * After the user has encrypted their message,
	 * prompts them to hack that message or allow them to exit the program
	 * 
	 * @param lang The language chosen by the user
	 * 
	 * @param graph The graph chosen by the user
	 * 
	 * @param search The type of search (advanced or simple) chosen by the user
	 * 
	 */
	public static void encrOptions(int lang, int graph, int search) {

		int encrChoice = 0;

		Scanner reader = new Scanner(System.in);

		System.out.println("\nNEXT STEPS---------------- \n0. Decrypt this message (copy the encrypted message)"
				+ "\n1. Exit the program");

		try {
			encrChoice = reader.nextInt();
			reader.nextLine();

			switch (encrChoice) {

			case 0:
				callHack(lang, graph, search);

				break;

			case 1:
				System.exit(0);

				break;

			default:
				break;
			}
		} catch (Exception e) {

			System.out.println("Incorrect value. \nPlease restart.");
		}
	}
	
	 /**
	 * Reads a .txt file that contains the history
	 * of the Caesar Cipher and prints it to the console.
	 * 
	 */
	public static void callHistory() {

		try (BufferedReader reader = new BufferedReader(new FileReader(".\\Dictionary\\CaesarHistory.txt"))) {

			String word = "";

			while ((word = reader.readLine()) != null) {
				System.out.println("\n"+ word);
			}

		// catches any errors caused by the BufferedReader
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens up a window that showcases 
	 * the frequency of the words in the decrypted message
	 * in a Pie Chart or a Bar Graph (depending on what the user chose)
	 * 
	 * @param graph The graph that the user chose
	 * 
	 */
	public static void callGraph(int graph) {
		
		Scanner reader = new Scanner(System.in);
		
		try {
			graph = reader.nextInt();
			reader.nextLine();

		} catch (Exception e) {

			System.out.println("Incorrect value. \nPlease restart.");
		}
		
		switch (graph) {
		
		// Opens a Bar Graph
		case 1:
			barGraph barG = new barGraph("Pie Chart", "Word Frequency Graph");
			barG.pack();
			barG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			barG.setVisible(true);
			break;
		
		// Opens a Pie Graph
		case 2:
			piGraph piG = new piGraph("Pie Chart", "Word Frequency Graph");
			piG.pack();
			piG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			piG.setVisible(true);
			break;
		
		default:
			break;
		}
	}
	
	/**
	 * Prompts the user to choose what they would like
	 * to do with the program. From just encrypting, to decrypting with a 
	 * known key, decrypting without a known key (hacking of sorts), and
	 * also learning more about the history of the Caesar Cipher itself.
	 * 
	 * @param lang The language chosen by the user
	 * 
	 * @param graph The graph chosen by the user
	 * 
	 * @param search The type of search (advanced or simple) chosen by the user
	 * 
	 */
	public static void callIntro(int lang, int graph, int search) {
		
		// Introduction and prompt to the program
		int choice = 0;
		
		Scanner reader = new Scanner(System.in);

		System.out.println("\nWelcome to the Cryptography program! \nPlease select an option: "
				+ "\n0. Exit \n1. Encrypt a message \n2. Decrypt a message (key known) "
				+ "\n3. Decrypt a message (key unknown) \n4. Learn the history of the Cipher");

		try {
			choice = reader.nextInt();
			reader.nextLine();

		} catch (Exception e) {

			System.out.println("Incorrect value. \nPlease restart.");
		}

		switch (choice) {

		case 0:
			System.exit(0);
			break;

		case 1:
			callEncrypt();
			encrOptions(lang, graph, search);
			break;

		case 2:

			// calling the basic cipher
			callCaesar();
			break;

		case 3:
			callHack(lang, graph, search);
			break;

		case 4:
			callHistory();
			break;

		default:
			break;
		}
	}

	/**
	 * Initializes the methods depending on what
	 * language the user chose to use. Also, takes user input 
	 * for what graph and language they want to use.
	 * 
	 * @param args Command line uses no arugments,
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int lang = 0;
		int graph = 0;
		int search = 0;

		Scanner reader = new Scanner(System.in);

		System.out
				.println("Before beginning, please select the language in which you would like to encrypt and decrypt."
						+ "\n1. Italian \n2. French \n3. English");
		
		// Takes user input for what language they would like to use
		try {
			lang = reader.nextInt();
			reader.nextLine();

		} catch (Exception e) {

			System.out.println("Incorrect value. \nPlease restart.");
		}
		
		//Advanced and simple search
		System.out.println("\n1. Advanced Dictionary Search \n2. Simple Dictionary Search");
		
		try {
			search = reader.nextInt();
			reader.nextLine();

		} catch (Exception e) {
			System.out.println("Incorrect value. \nPlease restart.");
		}

		//Choosing charts
		System.out.println(
				"\nWhat graph would you like to display your data? (Will only apply for *decrypting without key* option)"
						+ "\n1. Bar Graph \n2. Pie Graph \nNOTICE: Don't use for large paragaphs or sentences!!");
		
		// Takes user input for which graph they would like to use
		try {
			graph = reader.nextInt();
			reader.nextLine();

		} catch (Exception e) {

			System.out.println("Incorrect value. \nPlease restart.");
		}

		// Calls all methods 
		switch (lang) {

		case 1:
			callIntro(lang, graph, search);
			break;

		case 2:
			callIntro(lang, graph, search);
			break;

		case 3:
			callIntro(lang, graph, search);
			break;

		}
	}
}