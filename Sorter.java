import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter {
	private String[] relevanceParam;
	private String searchType;
	private static final String type1 = "author";
	private static final String type2 = "title";
    public List<Record> sortBydate(List<Record> searchResults){
        Collections.sort(searchResults,new Comparator<Record>(){
            @Override
            public int compare(Record r1,Record r2){
                if(r1.getYear() == null && r2.getYear() == null)
                    return 0;
                else if(r1.getYear() == null)
                    return -1;
                else if(r2.getYear() == null)
                    return 1;
                return r1.getYear().compareTo(r2.getYear());
            }
        });
        return searchResults;
    }
    public void setRelevanceParam(String searchData,String searchType){
    	this.relevanceParam = searchData.split(" ");
    	this.searchType = searchType;
	}
    public List<Record> sortByRelevance(List<Record> searchResults) {
		List<RelevanceContainer> list = new ArrayList<RelevanceContainer>();
		for (Record r : searchResults) {
			int relevance = 0;
			if (searchType.equals(type1)) {
				for (String s : r.getAuthor()) {
					String[] authorStringParts = s.split(" ");
					for (String word : relevanceParam) {
						for (String authorNamePart : authorStringParts)
							if (authorNamePart.equalsIgnoreCase(word))
								relevance++;
					}
				}
			} else if (searchType.equals(type2)) {
				String s = r.getTitle();
				String[] authorStringParts = s.split(" ");
				for (String word : relevanceParam) {
					for (String authorNamePart : authorStringParts)
						if (authorNamePart.equalsIgnoreCase(word))
							relevance++;
				}
			}list.add(new RelevanceContainer(r, relevance));
		}

		Collections.sort(list,new Comparator<RelevanceContainer>(){
			@Override
			public int compare(RelevanceContainer r1,RelevanceContainer r2){
				if(r1.getRelevance()> r2.getRelevance())
					return 1;
				else if(r1.getRelevance() == r2.getRelevance())
					return 0;
				return -1;
			}
		});
		List<Record> results = new ArrayList<Record>();
		for(RelevanceContainer rc : list){
			results.add(rc.getRecord());
		}
		return results;
    }
	public boolean match(String mainString, String searchString) {
		if (mainString.equalsIgnoreCase(searchString + "."))
			return true;
		else if (mainString.equalsIgnoreCase(searchString))
			return true;
		return false;
	}
}
class RelevanceContainer{
    private Record record;
	private int relevance;
	public RelevanceContainer(Record record,int relevance){
		this.record = record;
		this.relevance = relevance;
	}
	public Record getRecord() {
		return record;
	}
	public int getRelevance() {
		return relevance;
	}
}