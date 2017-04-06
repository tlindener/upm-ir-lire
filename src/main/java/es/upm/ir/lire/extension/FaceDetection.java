package es.upm.ir.lire.extension;

import java.awt.image.BufferedImage;
import java.util.List;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

public class FaceDetection {
	FaceDetector<DetectedFace, FImage> fd = new HaarCascadeDetector(60);
	FaceDetector<KEDetectedFace, FImage> fd2 = new FKEFaceDetector();

	private static FaceDetection instance = null;

	protected FaceDetection() {

	}

	public static FaceDetection getInstance() {
		if (instance == null) {
			instance = new FaceDetection();
		}
		return instance;
	}

	public int getNumberOfFaces(BufferedImage image) {
		MBFImage frame = ImageUtilities.createMBFImage(image, true);
		List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));
		List<KEDetectedFace> faces2 = fd2.detectFaces(Transforms.calculateIntensity(frame));
		//use different algorithms, choose higher result => better safe than sorry
		return (faces.size() > faces2.size()) ? faces.size() : faces2.size();

	}

}
