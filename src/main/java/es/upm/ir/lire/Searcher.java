package es.upm.ir.lire;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
	public static void main(String[] args) throws IOException {

		// create a Lucene IndexReader and the according IndexSearcher:
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("index")));
		IndexSearcher searcher = new IndexSearcher(reader);
	
		//Create a range query to find images with faces
		Query query = IntPoint.newRangeQuery("faces", 1, Integer.MAX_VALUE);
		TopDocs results = searcher.search(query, 10);
		// here we print the results of the text search, just for the win.
		System.out.println("-----------> SEARCH RESULTS Faces");
		for (int i = 0; i < results.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = results.scoreDocs[i];
			System.out.print(scoreDoc.score + "\t: ");
			System.out
					.println(reader.document(scoreDoc.doc).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0] + " -> ");
			System.out.println("Number of faces: " + reader.document(scoreDoc.doc).get("faces"));

			System.out.println();
		}
		System.out.println();
		
		//Create a range query to find images with bulletproof vest
		query = FloatPoint.newRangeQuery("bulletproof vest", 20f, Float.MAX_VALUE);
		results = searcher.search(query, 10);
		// here we print the results of the text search, just for the win.
		System.out.println("-----------> SEARCH RESULTS Objects=Bulletproof Vest");
		for (int i = 0; i < results.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = results.scoreDocs[i];
			System.out.print(scoreDoc.score + "\t: ");
			System.out
					.print(reader.document(scoreDoc.doc).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0] + " -> ");
			System.out.println("Probability of armored individual: " + reader.document(scoreDoc.doc).get("bulletproof vest"));

			System.out.println();
		}
		
		System.out.println();
		
		//Create a range query to find images with revolver
		query = FloatPoint.newRangeQuery("revolver", 10f, Float.MAX_VALUE);
		results = searcher.search(query, 10);
		// here we print the results of the text search, just for the win.
		System.out.println("-----------> SEARCH RESULTS Objects=Revolver");
		for (int i = 0; i < results.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = results.scoreDocs[i];
			System.out.print(scoreDoc.score + "\t: ");
			System.out
					.print(reader.document(scoreDoc.doc).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0] + " -> ");
			System.out.println("Probability of dangerous individual: " + reader.document(scoreDoc.doc).get("revolver"));
			System.out.println();
		}
	}
}