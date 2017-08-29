package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command expects a single argument: file name, and produces hex-output. On the
 * right side only a standard subset of characters is shown; for all other
 * characters a '.' is printed instead
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class HexdumpCommand extends AbstractCommand {
	
	/**
	 * Creates hexdump command with command name "hexdump" and command description.
	 */
	public HexdumpCommand() {
		super(
				"hexdump",
				Collections
						.unmodifiableList(Arrays
								.asList("Command expects a single argument: file name, and produces hex-output.",
										" On the right side only a standard subset of characters is shown; "
												+ "for all other characters a'.' is printed instead")));
	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {
		List<String> arguments = ArgumentsUtility.parseCommand(command, e);

		if (arguments == null) {
			return ShellStatus.CONTINUE;
		}

		if (!ArgumentsUtility.checkArgumentsSize(e, arguments, 1)) {
			return ShellStatus.CONTINUE;
		}

		Path source = Paths.get(arguments.get(0)).toAbsolutePath().normalize();
		if (!ArgumentsUtility.canOpenFile(e, source)) {
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(
				source, StandardOpenOption.READ))) {
			byte[] buff = new byte[8];
			byte[] buff2 = new byte[8];

			int adress = 0;
			while (true) {
				int r = is.read(buff);
				int r2 = is.read(buff2);
				if (r < 1) {
					break;
				}
				e.write(bytesToHex(intToByteArray(adress)) + ": ");
				buff = replaceUnsupportedChars(buff);
				String hex = normalHexOutput(bytesToHex(buff), r * 2);
				e.write(hex);

				String hex2;
				if (r2 > 0) {
					buff2 = replaceUnsupportedChars(buff2);

				} else {
					r2 = 0;
				}
				hex2 = normalHexOutput(bytesToHex(buff2), r2 * 2);
				e.write("|");
				e.write(hex2);
				e.write("|");
				e.write(new String(buff, 0, r));
				if (r2 > 0) {
					e.writeln(new String(buff2, 0, r2));
				} else {
					e.writeln("");
				}
				adress += 16;
			}
		} catch (IOException ignorable) {
		}

		return ShellStatus.CONTINUE;
	}

	private byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) value };
	}

	private byte[] replaceUnsupportedChars(byte[] buff) {

		for (int i = 0; i < buff.length; i++) {
			if (buff[i] < 32 || buff[i] > 127) {
				buff[i] = '.';
			}
		}
		return buff;
	}

	private String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	private String normalHexOutput(String hex, int r) {
		String normalHex = "";
		for (int i = 0; i < r; i += 2) {
			normalHex += hex.charAt(i);
			normalHex += hex.charAt(i + 1);
			normalHex += " ";
		}

		for (int i = 0; i < 16 - r; i += 2) {
			normalHex += "   ";
		}

		return normalHex;
	}
}
