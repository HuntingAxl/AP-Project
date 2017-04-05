import java.io.*;
import java.util.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
public class Searcher{
	public static final String filename = "dblp.xml";
	private List<Record> searchResults;
	private Record person;
	private boolean personFound;
	public List<Record> getSearchResults()
	{
		return searchResults;
	}
	public Record getPerson(){
		return person;
	}
	public void searchRecords(String searchString,String searchType){
		Record record = null;
		if(searchType.equalsIgnoreCase("author")) {
			searchPerson(searchString);
			//System.out.println(person);
			if (person == null) {
				searchResults = new ArrayList<>();
				return;
			}
		}
		String type,key,mdate,title,pages,year,crossref,journal,volume,number,url,ee;
		ArrayList<String> author = new ArrayList<String>();
		searchResults = new ArrayList<Record>();
   		type = key = mdate = title = pages = year = crossref = journal = volume = number = url = ee = null;
		boolean bAuthor = false;
		boolean bTitle = false;
		boolean bPages = false;
		boolean bYear = false;
		boolean bCrossref = false;
		boolean bJournal = false;
		boolean bVolume = false;
		boolean bUrl = false;
		boolean bEe = false;
	    try {
	    	XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty("javax.xml.stream.isCoalescing",true);
	    	XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(filename));
	       	while(eventReader!=null && eventReader.hasNext()){
	       		XMLEvent event = eventReader.nextEvent();
	            switch(event.getEventType()) {
					case XMLStreamConstants.START_ELEMENT:
						StartElement startElement = event.asStartElement();
						String qName = startElement.getName().getLocalPart();
						if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("proceedings")
								|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("phdthesis")
								|| qName.equalsIgnoreCase("mastersthesis") || qName.equalsIgnoreCase("www")) {
							Iterator<Attribute> attributes = startElement.getAttributes();
							type = qName;
							mdate = attributes.next().getValue();
							key = attributes.next().getValue();
						} else if (qName.equalsIgnoreCase("author") || qName.equalsIgnoreCase("editor")) {
							bAuthor = true;
						} else if (qName.equalsIgnoreCase("title")) {
							bTitle = true;
						} else if (qName.equalsIgnoreCase("pages")) {
							bPages = true;
						} else if (qName.equalsIgnoreCase("year")) {
							bYear = true;
						} else if (qName.equalsIgnoreCase("crossref")) {
							bCrossref = true;
						} else if (qName.equalsIgnoreCase("journal") || qName.equalsIgnoreCase("booktitle")) {
							bJournal = true;
						} else if (qName.equalsIgnoreCase("volume")) {
							bVolume = true;
						} else if (qName.equalsIgnoreCase("url")) {
							bUrl = true;
						} else if (qName.equalsIgnoreCase("ee")) {
							bEe = true;
						}
						break;
					case XMLStreamConstants.CHARACTERS:
						Characters characters = event.asCharacters();
						if (bAuthor) {
							author.add(characters.getData());
							bAuthor = false;
						} else if (bTitle) {
							title = characters.getData();
							bTitle = false;
						} else if (bPages) {
							pages = characters.getData();
							bPages = false;
						} else if (bYear) {
							year = characters.getData();
							bYear = false;
						} else if(bCrossref){
	                    	crossref = characters.getData();
	                    	bCrossref = false;
	            		} else if(bJournal){
	                    	journal = characters.getData();
	                    	bJournal = false;
	            		} else if(bVolume){
	                    	volume = characters.getData();
	                    	bVolume = false;
	            		} else if(bUrl){
	                    	url = characters.getData();
	                    	bUrl = false;
	            		} else if(bEe){
	                    	ee = characters.getData();
	                    	bEe = false;
	            		}
	                break;
	                case XMLStreamConstants.END_ELEMENT:
	                    EndElement endElement = event.asEndElement();
	                    String eName = endElement.getName().getLocalPart();
	                    if(eName.equalsIgnoreCase("article") || eName.equalsIgnoreCase("inproceedings") || eName.equalsIgnoreCase("proceedings")
	            				|| eName.equalsIgnoreCase("book")|| eName.equalsIgnoreCase("incollection")|| eName.equalsIgnoreCase("phdthesis")
	            				|| eName.equalsIgnoreCase("mastersthesis")|| eName.equalsIgnoreCase("www")){
	                       record = new Record(type,key,mdate,author,title,pages,year,crossref,journal,volume,number,url,ee);
	                       if(searchType.equals("author")){
	                    	   for(String dataAuthor : record.getAuthor()){
									for(String name : person.getAuthor())
	                    	   			if(!record.getType().equalsIgnoreCase("www") && dataAuthor.equalsIgnoreCase(name)){
											searchResults.add(record);
									   		break;
										}
							   }
	                       }else if(searchType.equals("title")){
	                    	   if(record.getTitle()!=null && found(record.getTitle(),searchString))
	                    		   searchResults.add(record);
	                       }
	                       author = new ArrayList<String>();
	                  	   type = key = mdate = title = pages = year = crossref = journal = volume = number = url = ee = null;
	                    }
	                break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	public boolean found(String mainString, String searchString) {
		String[] mainStringParts = mainString.split(" ");
		String[] searchStringParts = searchString.split(" ");
		for(String main : mainStringParts)
			for(String search : searchStringParts)
				if (main.equalsIgnoreCase(search + "."))
					return true;
				else if (main.equalsIgnoreCase(search))
					return true;
		return false;
	}
	public void searchPerson(String searchString){
		Record record = null;
		String type,key,mdate,title,pages,year,crossref,journal,volume,number,url,ee;
		ArrayList<String> author = new ArrayList<String>();
		type = key = mdate = title = pages = year = crossref = journal = volume = number = url = ee = null;
		boolean bAuthor = false;
		boolean bTitle = false;
		boolean bPages = false;
		boolean bYear = false;
		boolean bCrossref = false;
		boolean bJournal = false;
		boolean bVolume = false;
		boolean bUrl = false;
		boolean bEe = false;
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty("javax.xml.stream.isCoalescing",true);
			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(filename));
			while(eventReader!=null && eventReader.hasNext()){
				XMLEvent event = eventReader.nextEvent();
				switch(event.getEventType()) {
					case XMLStreamConstants.START_ELEMENT:
						StartElement startElement = event.asStartElement();
						String qName = startElement.getName().getLocalPart();
						if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("proceedings")
								|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("phdthesis")
								|| qName.equalsIgnoreCase("mastersthesis") || qName.equalsIgnoreCase("www")) {
							Iterator<Attribute> attributes = startElement.getAttributes();
							type = qName;
							mdate = attributes.next().getValue();
							key = attributes.next().getValue();
						} else if (qName.equalsIgnoreCase("author") || qName.equalsIgnoreCase("editor")) {
							bAuthor = true;
						} else if (qName.equalsIgnoreCase("title")) {
							bTitle = true;
						} else if (qName.equalsIgnoreCase("pages")) {
							bPages = true;
						} else if (qName.equalsIgnoreCase("year")) {
							bYear = true;
						} else if (qName.equalsIgnoreCase("crossref")) {
							bCrossref = true;
						} else if (qName.equalsIgnoreCase("journal") || qName.equalsIgnoreCase("booktitle")) {
							bJournal = true;
						} else if (qName.equalsIgnoreCase("volume")) {
							bVolume = true;
						} else if (qName.equalsIgnoreCase("url")) {
							bUrl = true;
						} else if (qName.equalsIgnoreCase("ee")) {
							bEe = true;
						}
						break;
					case XMLStreamConstants.CHARACTERS:
						Characters characters = event.asCharacters();
						if (bAuthor) {
							author.add(characters.getData());
							bAuthor = false;
						} else if (bTitle) {
							title = characters.getData();
							bTitle = false;
						} else if (bPages) {
							pages = characters.getData();
							bPages = false;
						} else if (bYear) {
							year = characters.getData();
							bYear = false;
						} else if(bCrossref){
							crossref = characters.getData();
							bCrossref = false;
						} else if(bJournal){
							journal = characters.getData();
							bJournal = false;
						} else if(bVolume){
							volume = characters.getData();
							bVolume = false;
						} else if(bUrl){
							url = characters.getData();
							bUrl = false;
						} else if(bEe){
							ee = characters.getData();
							bEe = false;
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
						EndElement endElement = event.asEndElement();
						String eName = endElement.getName().getLocalPart();
						if(eName.equalsIgnoreCase("article") || eName.equalsIgnoreCase("inproceedings") || eName.equalsIgnoreCase("proceedings")
								|| eName.equalsIgnoreCase("book")|| eName.equalsIgnoreCase("incollection")|| eName.equalsIgnoreCase("phdthesis")
								|| eName.equalsIgnoreCase("mastersthesis")|| eName.equalsIgnoreCase("www")){
							record = new Record(type,key,mdate,author,title,pages,year,crossref,journal,volume,number,url,ee);
							for(String name : record.getAuthor())
								if(record.getType().equalsIgnoreCase("www") && name.equalsIgnoreCase(searchString)){
									person = record;
									personFound = true;
									break;
								}
							author = new ArrayList<String>();
							type = key = mdate = title = pages = year = crossref = journal = volume = number = url = ee = null;
						}
						break;
				}
				if(personFound)
					break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	public void displayCount(){
		int i=0;
		Record record = null;
		String type,key,mdate,title,pages,year,crossref,journal,volume,number,url,ee;
		ArrayList<String> author = new ArrayList<String>();
		type = key = mdate = title = pages = year = crossref = journal = volume = number = url = ee = null;
		boolean bAuthor = false;
		boolean bTitle = false;
		boolean bPages = false;
		boolean bYear = false;
		boolean bCrossref = false;
		boolean bJournal = false;
		boolean bVolume = false;
		boolean bUrl = false;
		boolean bEe = false;
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty("javax.xml.stream.isCoalescing",true);
			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(filename));
			while(eventReader!=null && eventReader.hasNext()){
				XMLEvent event = eventReader.nextEvent();
				switch(event.getEventType()) {
					case XMLStreamConstants.START_ELEMENT:
						StartElement startElement = event.asStartElement();
						String qName = startElement.getName().getLocalPart();
						if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("proceedings")
								|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("phdthesis")
								|| qName.equalsIgnoreCase("mastersthesis") || qName.equalsIgnoreCase("www")) {
							Iterator<Attribute> attributes = startElement.getAttributes();
							type = qName;
							mdate = attributes.next().getValue();
							key = attributes.next().getValue();
						} else if (qName.equalsIgnoreCase("author") || qName.equalsIgnoreCase("editor")) {
							bAuthor = true;
						} else if (qName.equalsIgnoreCase("title")) {
							bTitle = true;
						} else if (qName.equalsIgnoreCase("pages")) {
							bPages = true;
						} else if (qName.equalsIgnoreCase("year")) {
							bYear = true;
						} else if (qName.equalsIgnoreCase("crossref")) {
							bCrossref = true;
						} else if (qName.equalsIgnoreCase("journal") || qName.equalsIgnoreCase("booktitle")) {
							bJournal = true;
						} else if (qName.equalsIgnoreCase("volume")) {
							bVolume = true;
						} else if (qName.equalsIgnoreCase("url")) {
							bUrl = true;
						} else if (qName.equalsIgnoreCase("ee")) {
							bEe = true;
						}
						break;
					case XMLStreamConstants.CHARACTERS:
						Characters characters = event.asCharacters();
						if (bAuthor) {
							author.add(characters.getData());
							bAuthor = false;
						} else if (bTitle) {
							title = characters.getData();
							bTitle = false;
						} else if (bPages) {
							pages = characters.getData();
							bPages = false;
						} else if (bYear) {
							year = characters.getData();
							bYear = false;
						} else if(bCrossref){
							crossref = characters.getData();
							bCrossref = false;
						} else if(bJournal){
							journal = characters.getData();
							bJournal = false;
						} else if(bVolume){
							volume = characters.getData();
							bVolume = false;
						} else if(bUrl){
							url = characters.getData();
							bUrl = false;
						} else if(bEe){
							ee = characters.getData();
							bEe = false;
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
						EndElement endElement = event.asEndElement();
						String eName = endElement.getName().getLocalPart();
						if(eName.equalsIgnoreCase("article") || eName.equalsIgnoreCase("inproceedings") || eName.equalsIgnoreCase("proceedings")
								|| eName.equalsIgnoreCase("book")|| eName.equalsIgnoreCase("incollection")|| eName.equalsIgnoreCase("phdthesis")
								|| eName.equalsIgnoreCase("mastersthesis")|| eName.equalsIgnoreCase("www")){
							i++;
							System.out.println(i);
							record = new Record(type,key,mdate,author,title,pages,year,crossref,journal,volume,number,url,ee);
							author = new ArrayList<String>();
							type = key = mdate = title = pages = year = crossref = journal = volume = number = url = ee = null;
						}
						break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
}