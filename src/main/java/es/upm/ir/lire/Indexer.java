package es.upm.ir.lire;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import es.upm.ir.lire.extension.FaceDetection;
import es.upm.ir.lire.extension.ObjectRecognition;
import es.upm.ir.lire.extension.ObjectRecognitionResult;
import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.utils.FileUtils;

public class Indexer {

	   static List<String> concepts = Arrays.asList(new String[]{"bulletproof vest","passenger car","suit","military uniform","revolver"});
	   public static void main(String[] args) throws IOException {

	        // Getting all images from a directory and its sub directories.
	        ArrayList<String> images = FileUtils.getAllImages(new File("./input"), true);

	        // Creating a CEDD document builder and indexing all files.
	        GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(false, false);
	        /*
	            If you want to use DocValues, which makes linear search much faster, then use.
	            However, you then have to use a specific searcher!
	         */
	        // GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(false, true);

	        /*
	            Then add those features we want to extract in a single run:
	         */
	        globalDocumentBuilder.addExtractor(CEDD.class);
	        globalDocumentBuilder.addExtractor(FCTH.class);
	        globalDocumentBuilder.addExtractor(AutoColorCorrelogram.class);

	        // Creating an Lucene IndexWriter
	        IndexWriterConfig conf = new IndexWriterConfig(new WhitespaceAnalyzer());
	        IndexWriter iw = new IndexWriter(FSDirectory.open(Paths.get("index")), conf);
	        // Iterating through images building the low level features
	        for (Iterator<String> it = images.iterator(); it.hasNext(); ) {
	            String imageFilePath = it.next();
	            System.out.println("Indexing " + imageFilePath);
	            try {
	                BufferedImage img = ImageIO.read(new FileInputStream(imageFilePath));
	                Document document = globalDocumentBuilder.createDocument(img, imageFilePath);
	                
	                //detect faces
	                int numFaces =FaceDetection.getInstance().getNumberOfFaces(img);
	                document.add(new IntPoint("faces", numFaces));
	                document.add(new StoredField("faces", numFaces));
	                //detect objects
	                List<ObjectRecognitionResult> objects = ObjectRecognition.getInstance().identifyObjects(imageFilePath);
	                for(ObjectRecognitionResult result : objects){
	                	if(concepts.contains(result.getLabel())){
	                		document.add(new FloatPoint(result.getLabel(), result.getProbability()));
	                		document.add(new StoredField(result.getLabel(), result.getProbability()));
	                	}
	                }
	                iw.addDocument(document);
	            } catch (Exception e) {
	                System.err.println("Error reading image or indexing it.");
	                e.printStackTrace();
	            }
	        }
	        // closing the IndexWriter
	        iw.close();
	        System.out.println("Finished indexing.");
	}

}
