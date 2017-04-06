package es.upm.ir.tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import es.upm.ir.lire.extension.FaceDetection;
import es.upm.ir.lire.extension.ObjectRecognition;
import es.upm.ir.lire.extension.ObjectRecognitionResult;

public class TestObjectRecognition {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() throws FileNotFoundException, IOException {

		File folder = new File("./input");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println("File: " + file.getName());
				List<ObjectRecognitionResult> objects = ObjectRecognition.getInstance()
						.identifyObjects(file.getAbsolutePath());
				for (ObjectRecognitionResult item : objects) {
					System.out.println(
							String.format("MATCH: %s (%.2f%% likely)", item.getLabel(), item.getProbability()));

				}

			}
		}

	}

}
