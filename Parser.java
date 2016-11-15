import java.io.*;
import java.util.*;
public class Parser {
	private BufferedReader reader = null;
	public static final String filename = "dblp.xml"; 
	private boolean readAllRecords;
	public Parser(){
		this.readAllRecords = false ;
		try{
			reader = new BufferedReader(new FileReader(filename));
		}catch(FileNotFoundException fnfe){
			System.out.println("Shutting Down System because dataset was not found.");
			System.exit(0);
		}
	}
	public ArrayList<Record> parseFile(){
		ArrayList<Record> records = new ArrayList<Record>();
		while(this.readAllRecords == false){
			records.append(this.readNextRecord())
		}
		return records;
	}
	public Record readNewRecord(){
		Record record = new Record();
		return record;
	}
	public String readNextLine(){
		String line = null;
		try{
			line = reader.readLine();
		}catch(IOException ioe){
			System.out.println("Error occured while reading the database from the file.");
			System.exit(1);
		}
		return line ;
	}
}
