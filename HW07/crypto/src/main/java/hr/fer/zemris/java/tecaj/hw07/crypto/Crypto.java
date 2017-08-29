package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class with main method that expects 2 or 3 command arguments. It uses for
 * crypting, decrypting file or checking file digest.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Crypto {
	private final static int bufferSize = 4096;

	/**
	 * Main method
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {

		if (args.length < 2 || args.length > 3) {
			System.out
					.println("Invalid number of arguments. Minimum is two and maximum three.");
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));
		Path path = null;

		path = Paths.get("./" + args[1].trim());
		if (!Files.exists(path)) {
			System.out.println("File " + args[1] + " doesnt exist.");
			return;
		}

		// --------------checking operation type---------------
		boolean encrypt = false;
		if (args[0].toLowerCase().trim().equals("checksha")) {
			if (args.length != 2) {
				System.out
						.println("Invalid number of arguments. Expected two.");
				return;
			}
			checksha(reader, path);
			return;
		} else if (args[0].toLowerCase().trim().equals("encrypt")) {
			encrypt = true;
		} else if (!args[0].toLowerCase().trim().equals("decrypt")) {
			System.out.println("Unsupported operation " + args[0]);
			return;
		}
		if (args.length != 3) {
			System.out.println("Invalid number of arguments. Expected three.");
			return;
		}

		// -----------providing key and initialization vector-----------------
		String keyText;
		String ivText;
		try {
			System.out
					.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			keyText = reader.readLine().trim();
			System.out
					.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			ivText = reader.readLine().trim();
			reader.close();
		} catch (IOException e1) {
			System.out.println("Reader error.");
			return;
		}

		// ------------creating SecretKeySpec and AlgorithmParameterSpec--------
		SecretKeySpec keySpec = null;
		AlgorithmParameterSpec paramSpec = null;
		try {
			keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
			paramSpec = new IvParameterSpec(hextobyte(ivText));
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return;
		}

		// ---------------- Cipher initialization ---------------------
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException ignorable) {
		}

		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
					keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			System.out.println(e.getMessage());
			return;
		}
		// ------------------calling method for creating crypted file----------

		Path sourcePath = Paths.get("./" + args[2]);
		boolean successfull = crypt(path, cipher, sourcePath);

		// ----------- printing crypting results --------------
		if (encrypt) {
			System.out.print("Encryption ");
		} else {
			System.out.print("Decryption ");
		}
		if (successfull) {
			System.out.print("completed successfuly.");
		} else {
			System.out.print("failed.");
		}
		System.out.println(" Generated file " + sourcePath.getFileName()
				+ " based on file " + path.getFileName());
	}

	/**
	 * Method encrypts or decrypts given source file and stores result in
	 * destination file.
	 * 
	 * @param sourcePath
	 *            file that will be crypted
	 * @param cipher
	 *            initialized cipher
	 * @param destinationPath
	 *            result file
	 * @return true if crypting was succesfull, false otherwise
	 */
	private static boolean crypt(Path sourcePath, Cipher cipher,
			Path destinationPath) {
		boolean successfull = true;
		try (InputStream is = new BufferedInputStream(Files.newInputStream(
				sourcePath, StandardOpenOption.READ));

				OutputStream os = new BufferedOutputStream(
						Files.newOutputStream(destinationPath,
								StandardOpenOption.CREATE))) {
			byte[] buff = new byte[bufferSize];
			while (true) {
				int r = is.read(buff);
				if (r < 1) {
					break;
				}
				os.write(cipher.update(buff, 0, r));
			}

			os.write(cipher.doFinal());
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
			successfull = false;
		}
		return successfull;

	}

	/**
	 * Method checks sha digest of entered path and writes message on screen.
	 * 
	 * @param reader
	 *            reader that is used to user enter expected sha digest
	 * @param path
	 *            file path which digest is checked
	 */
	private static void checksha(BufferedReader reader, Path path) {
		System.out.println("Please provide expected sha-256 digest for "
				+ path.getFileName() + ":");
		String expectedDigest = null;
		try {
			expectedDigest = reader.readLine().trim();
			reader.close();
		} catch (IOException e1) {
			System.out.println("Reader error");
			System.exit(-1);
		}
		System.out.print("Digest complete. Digest of " + path.getFileName());

		if (Arrays.equals(hextobyte(expectedDigest), (getDigest(path)))) {
			System.out.println(" matches expected digest.");
		} else {
			System.out
					.print(" does not match the expected digest. Digest was: ");
			System.out.println(expectedDigest);
		}
	}

	/**
	 * Method returns digest stored in byte array of given file.
	 * 
	 * @param path
	 *            file path
	 * @return byte array of digest.
	 */
	private static byte[] getDigest(Path path) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ignorable) {
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(
				path, StandardOpenOption.READ))) {
			byte[] buff = new byte[bufferSize];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				digest.update(buff, 0, r);
			}
		} catch (IOException ignorable) {
		}
		return digest.digest();
	}

	/**
	 * Method parses hexdecimal string to byte array.
	 * 
	 * @param str
	 *            hexdecimal string
	 * @return byte array of hexdecimal string
	 * @throws IllegalArgumentException
	 *             if string parameter does not conform to lexical value space
	 *             defined in XML Schema Part 2: Datatypes for xsd:hexBinary.
	 * @throws IllegalArgumentException
	 *             if string length is not divisible with 2.
	 */
	public static byte[] hextobyte(String str) {
		if (!str.matches("^[0-9A-Fa-f]+$")) {
			throw new IllegalArgumentException("Unsuportted hex chararacters.");
		}

		if (str.length() % 2 != 0) {
			throw new IllegalArgumentException(
					"Invalid number of hex numbers. Cann not"
							+ "parse into bytes.");
		}
		int len = str.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character
					.digit(str.charAt(i + 1), 16));
		}
		return data;
	}
}
