package org.wuprotos.wrappers;

import WUProtos.Data.*;
import org.wuprotos.protos.*;

import java.io.*;
import java.nio.file.*;

public class GenerateJSON {
	String OUTPUT_FILE_NAME = "GameDataWrapper.json";

	GameDataWrapperReader reader;
	File baseDir;
	File latestDir;
	File versionDir;

	public GenerateJSON() {
		reader = new GameDataWrapperReader();
		baseDir = new File("versions");
		latestDir = new File(baseDir, "latest");
		latestDir.mkdirs();

	}

	public void writeJSON(InputStream is) throws Exception {
		GameDataWrapperOuterClass.GameDataWrapper response = reader.read(is);
		long version = response.getBasisBatchId();


		File outputFile = new File(latestDir, OUTPUT_FILE_NAME);

		try (FileOutputStream fos = new FileOutputStream(outputFile)) {
			reader.writeJSON(response, fos);
		}
		System.out.println(outputFile.getCanonicalPath() + " written successfully");

		versionDir = new File(baseDir, Long.toString(version));
		versionDir.mkdirs();
		outputFile = new File(versionDir, OUTPUT_FILE_NAME);

		try (FileOutputStream fos = new FileOutputStream(outputFile)) {
			reader.writeJSON(response, fos);
		}
		System.out.println(outputFile.getCanonicalPath() + " written successfully");

	}

	public void writeBin(File f) throws IOException {
		File outputFile = new File(latestDir, f.getName());
		Files.copy(f.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		System.out.println(outputFile.getCanonicalPath() + " written successfully");

		outputFile = new File(versionDir, f.getName());
		Files.copy(f.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		System.out.println(outputFile.getCanonicalPath() + " written successfully");

	}

	public static void main(String[] args) throws Exception {
		GenerateJSON gen = new GenerateJSON();
		if (args.length != 1) {
			System.err.println("USAGE: java -jar gameDataWrapper-0.6.0-SNAPSHOT.jar BINARY_INPUT_FILE");
			return;
		}
		File f = new File(args[0]);
		if (!f.exists()) {
			System.err.println("File not found: " + args[0]);
		}
		try (FileInputStream is = new FileInputStream(f)) {
			gen.writeJSON(is);
		}
		gen.writeBin(f);
	}
}
