class Record {
	private String key;
	private String mdate;
	private String author;
	private String paper_name;
	private String data;
	private String url;
	private String ee;
	public Record(String key, String mdate, String author, String paper_name,String data, String url, String ee) {
		this.key = key;
		this.mdate = mdate;
		this.author = author;
		this.paper_name = paper_name;
		this.data = data;
		this.url = url;
		this.ee = ee;
	}
	public String getKey() {
		return key;
	}
	public String getMdate() {
		return mdate;
	}
	public String getAuthor() {
		return author;
	}
	public String getPaper_name() {
		return paper_name;
	}
	public String getData() {
		return data;
	}
	public String getUrl() {
		return url;
	}
	public String getEe() {
		return ee;
	}
}
