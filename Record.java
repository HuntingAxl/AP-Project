import java.util.*;
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// 			AP Project - DBLP
//
// 			Team Members : Gurpal Singh 	2015034
// 			Team Members : Nishant Rana 	2015152
//
// 			@author Nishant Rana
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////// Container to store all the attributes /////////////////////////////////////
public class Record {
	private final String type;
	private final String key;
	private final String mdate;
	private final List<String> author;			//Mutiple Authors are possible.
	private final String title;
	private final String pages;
	private final String year;
	private final String crossref;
	private final String journal;
	private final String volume;
	private final String number;
	private final String url;
	private final String ee;

	////////////////////////////// Constructor to create an object //////////////////////////

	public Record(String type, String key, String mdate, List<String> author, String title,
			String pages, String year,String crossref, String journal,
			String volume, String number, String url, String ee) {
		this.type = type;
		this.key = key;
		this.mdate = mdate;
		this.author = author;
		this.title = title;
		this.pages = pages;
		this.year = year;
		this.crossref = crossref;
		this.journal = journal;
		this.volume = volume;
		this.number = number;
		this.url = url;
		this.ee = ee;
	}

	/////////////////////////////// Getters only, no setters ////////////////////////////////////

	public String getType(){
		return type;
	}
	public String getKey() {
		return key;
	}
	public String getMdate() {
		return mdate;
	}
	public List<String> getAuthor() {
		return author;
	}
	public String getTitle() {
		return title;
	}
	public String getPages() {
		return pages;
	}
	public String getYear() {
		return year;
	}
	public String getCrossref() {
		return crossref;
	}
	public String getJournal() {
		return journal;
	}
	public String getVolume() {
		return volume;
	}
	public String getNumber() {
		return number;
	}
	public String getUrl() {
		return url;
	}
	public String getEe() {
		return ee;
	}

	//////////////////////////////// toString() to easily print the whole object for debugging purposes /////////////

	public String toString(){
		return type+"  "+key+"  "+mdate+"  "+author+"  "+title+"  "+pages+"  "+year+"  "+crossref+"  "+journal+"  "+volume+"  "+number+"  "+url+"  "+ee;
	}
}