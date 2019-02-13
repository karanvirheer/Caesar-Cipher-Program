package cipher;

	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.OutputStreamWriter;
	import java.io.PrintWriter;
	import java.io.Writer;
	import java.util.Random;
	import java.util.Scanner;

	import javax.swing.JFrame;

	/* Project Info -------------------
	 * 
	 * @author Karanvir Heer
	 * 
	 * @task Creating a Caesar Cipher which can decrypt and encrypt messages
	 * 
	 */

	public class cipher {
		// Creates a string containing the alphabet
		public static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		/*
		 * About Shift----------------------------------------
		 * 
		 * @description Shifts the alphabet to the left or the right, depending on the
		 * key given
		 * 
		 * @param1 string alphabet - the alphabet in uppercase
		 * 
		 * @param 2 int key - the amount that the letters/characters needs to shift
		 * 
		 * @return encrAlpha (the encrypted alphabet)
		 */

		private static String shift(String alphabet, int key) {
			// TODO

			key = key % 26;

			String encrAlpha = "";

			// Creates a character array which is the length of the alphabet
			// Then fills the array with the values in the alphabet String
			char[] alpha = new char[alphabet.length()];
			alpha = alphabet.toCharArray();

			// If key is negative, it's added to length of the alphabet
			if (key < 0) {

				key = 26 + key;

				// Loops through the length of the newly made alphabet array
				for (int index = 0; index < alpha.length; index++) {

					if (index + key < alpha.length) {
						encrAlpha += alpha[index + key];

						// In case the index + key is larger than the length of the
						// char array,
						// it is subtracted from the length of the alphabet so it
						// stays within
						// the index of the array itself.
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

		/*
		 * About ----------------------------------------
		 * 
		 * @description Encrypts the message
		 * 
		 * @param1 string message - the message that needs to be encrypted
		 * 
		 * @param2 int key - the amount that the letters/characters needs to shift
		 * 
		 * @return cipherText - the encrypted message
		 */

		public static String encrypt(String message, int key) {
			// TODO

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
					// ___ in the alphabet.
					int charPosition = alpha.indexOf(message.charAt(i));

					// Adds the keyshift to the index of the character, and modulus'
					// it by 26
					// This is since any number that is % by 26 would be less than
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

		/*
		 * About ----------------------------------------
		 * 
		 * @description Decrypts the message
		 * 
		 * @param1 string cipherText - the text that needs to be decrypted is here
		 * 
		 * @param 2 int key - the amount that the letters/characters needs to shift
		 * 
		 * @return decrText - the decrypted text
		 */

		public static String decrypt(String cipherText, int key) {
			// TODO

			key = key % 26;

			String decrText = "";

			cipherText = cipherText.toUpperCase();

			for (int i = 0; i < cipherText.length(); i++) {

				int charPosition = alpha.indexOf(cipherText.charAt(i));

				int keyVal = (charPosition - key) % 26;

				if (keyVal < 0) {
					keyVal = alpha.length() + keyVal;
				}

				char replaceVal = alpha.charAt(keyVal);

				decrText += replaceVal;
			}

			return decrText.toLowerCase();
		}

		/*
		 * About ----------------------------------------
		 * 
		 * @description Brute forces through the encrypted message, whilst not given a
		 * key, and outputs the decrpyted message
		 * 
		 * @param1 string encrText - the text that needs to be decrypted is here
		 * 
		 * @return decrText - the decrypted text
		 */
		public static String hackerman(String encrText) {

			String decrText = "";

			int HighScore = 0;

			encrText = encrText.toUpperCase();

				// iterates through the key values of 0 to 25
				for (int genKey = 0; genKey < 26; genKey++) {

					int score = 0;

					// reads the dictionary file line by line and compares it to the
					// results given
					try (BufferedReader reader = new BufferedReader(
							new FileReader("C:\\Eclipse Workplace\\Hacker\\Docs\\Dictionary.txt"))) {

						String word = "";

						while ((word = reader.readLine()) != null) {

							if (decrypt(encrText, genKey).contains(word)) {	
								
								score++;
							}
						}

					// catches any errors caused by the BufferedReader
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (score > HighScore) {
						HighScore = score;

						decrText = decrypt(encrText, genKey);
					}
				}
				
			// System.out.println(decrText);
			return decrText;
		}

		// Test BufferedReader
		public static void buffer() {
			// reads the dictionary file line by line and compares it to the results
			// given
			try (BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Eclipse Workplace\\Hacker\\Docs\\Dictionary.txt"))) {

				String word = "";

				while ((word = reader.readLine()) != null) {
					System.out.println(word);
				}

				// catches any errors caused by the BufferedReader
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public static void callHack() {

			Scanner reader = new Scanner(System.in);

			System.out.println("\nINPUT ------------- \nInput message: ");
			String decrmessage = reader.nextLine();

			// System.out.println("Input Message: " + decrmessage);

			reader.close();

			System.out.println("\nMessage: " + hackerman(decrmessage.replace(" ", "")));
		}

		public static void callCaesar() {

			Scanner reader = new Scanner(System.in);

			// Get message from user
			System.out.println("Input message: ");
			String message = reader.nextLine();

			// Get key from user
			System.out.println("Desired key: ");
			int key = reader.nextInt();

			// Print the message and the key inputed
			System.out.println("Input Message: " + message);

			System.out.println("Key: " + key);

			reader.close();

			// Printing Cipher, Message, Encrypted, and Decrypted text.
			System.out.println("Cipher: " + (shift(alpha, key)));

			System.out.println("Initial Message: " + message);

			String encryptedMsg = encrypt(message.replace(" ", ""), key);

			System.out.println("Encrypted message: " + encryptedMsg);

			String decryptedMsg = decrypt(encryptedMsg.replace(" ", ""), key);

			System.out.println("Decrypted message: " + decryptedMsg);

		}

		public static void callEncrypt() {
			Scanner reader = new Scanner(System.in);

			int encrChoice = 0;

			// Get message from user
			System.out.println("\nINPUT------------ \nInput message: ");
			String message = reader.nextLine();

			// Get key from user
			System.out.println("Desired key: ");
			int key = reader.nextInt();

			// reader.close();

			// Printing Cipher, Message, Encrypted, and Decrypted text.
			System.out.println("\nOUTPUT ---------------- \nCipher: " + (shift(alpha, key)));

			System.out.println("Initial Message: " + message);

			String encryptedMsg = encrypt(message.replace(" ", ""), key);

			System.out.println("Encrypted message: " + encryptedMsg);

		}

		public static void encrOptions() {

			int encrChoice = 0;

			Scanner reader = new Scanner(System.in);

			System.out.println("\nNEXT STEPS---------------- \n0. Decrypt this message (copy the encrypted message)"
					+ "\n1. Exit the program");

			try {
				encrChoice = reader.nextInt();
				reader.nextLine();

			} catch (Exception e) {

				System.out.println("Incorrect value. \nPlease restart.");
			}

			switch (encrChoice) {

			case 0:
				callHack();

				break;

			case 1:
				System.exit(0);

				break;

			default:
				break;
			}
		}

		/*
		 * @param args
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			
			 // CreateChart CC = new CreateChart("Pie Chart Test", "OS Comparison");
			 // CC.pack(); CC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 // CC.setVisible(true);
			 

			// Introduction and prompt to the program
			int choice = 0;
			Scanner reader = new Scanner(System.in);

			System.out.println("Welcome to the Cryptography program! \nPlease select an option: "
					+ "\n0. Exit \n1. Encrypt a message \n2. Decrypt a message (key known) "
					+ "\n3. Decrypt a message (key unknown)");

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

				encrOptions();

				break;

			case 2:

				// calling the basic cipher
				callCaesar();

				break;

			case 3:

				callHack();

				// buffer();

				break;

			default:
				break;
			}
		}
	}
