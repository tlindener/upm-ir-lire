package es.upm.ir.lire;

import java.io.IOException;

public class LireExtension {

	public static void main(String[] args) throws IOException {
		
		if(args.length < 1){
			System.out.println("Please choose \"Indexer\" or \"Search\" as parameter");
			System.exit(0);
		}
		String mode = args[0].toLowerCase();
		switch(mode){
		case "indexer":
			Indexer.main(null);
			break;
		case "search":
			Searcher.main(null);
			break;
			default:
				System.out.println("No acceptable mode!");
				System.exit(0);
		}
		

	}

}
