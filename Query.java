import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

public class Query {
    private List<Record> results;
    private Searcher searcher;
    private Sorter sorter;
    private PublicationCounter publicationCounter;
    public static final String searchType1 = "author";
    public static final String searchType2 = "title";
    public static final String sortType1 = "date";
    public static final String sorttype2 = "relevance";
    public static final String selectionType1 = "since";
    public static final String selectionType2 = "between";
    public Query(){
        searcher = new Searcher();
        sorter = new Sorter();
        publicationCounter = new PublicationCounter();
    }
    public List<Record> getResults(){
        return results;
    }
    public List<Record> sinceYear(List<Record> _results,String year){
        List<Record> new_records = new ArrayList<>();
        for(Record r : _results){
            String recordYear = r.getYear();
            if(recordYear!=null && recordYear.compareTo(year)>=0)
                new_records.add(r);
        }
        return new_records;
    }
    public List<Record> betweenYear(List<Record> _results,String lowerYear,String upperYear){
        if(lowerYear.compareTo(upperYear)>0){
            String temp = lowerYear;
            lowerYear = upperYear;
            upperYear = temp;
        }
        _results = sinceYear(_results,lowerYear);
        List<Record> newRecords = new ArrayList<>();
        for(Record r : _results) {
            String recordYear = r.getYear();
            if(recordYear!=null && recordYear.compareTo(upperYear)<=0)
                newRecords.add(r);
        }
        return newRecords;
    }
    public void query1(String searchString, String searchType, String sortType, String selectionType,String lowerYear,String upperYear) {
        searcher.searchRecords(searchString, searchType);
        results = searcher.getSearchResults();
        if(selectionType.equals(selectionType1)){
            results = sinceYear(results,lowerYear);
        }else if(selectionType.equals(selectionType2)){
            results = betweenYear(results,lowerYear,upperYear);
        }
        if (sortType.equals(sortType1)) {
            setRelevanceParam(searchString, searchType);
            sorter.sortBydate(results);
        } else if (sortType.equals(sorttype2)){
            setRelevanceParam(searchString, searchType);
            sorter.sortByRelevance(results);
        }
    }
    public void query2(int k){
		publicationCounter.greaterThanKPublications(k);
		System.out.println("retrieving results");
		//for(Record record : publicationCounter.getSearchResults())
		//	System.out.println(record.getAuthor().get(0));
		results = publicationCounter.getSearchResults();
	}
	public static void main(String[] args){
    	int k;
    	Scanner in = new Scanner(System.in);
    	k = in.nextInt();
    	Query q = new Query();
    	q.query2(k);
		System.out.println(q.getResults().size());
    	for(Record r : q.getResults())
    		System.out.println(r.getAuthor().get(0));
	}
    public void setRelevanceParam(String searchData,String searchType){
        sorter.setRelevanceParam(searchData,searchType);
    }
}
