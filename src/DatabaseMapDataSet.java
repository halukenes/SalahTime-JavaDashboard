
public class DatabaseMapDataSet {

	private String cityName;
	private int    numberofUsers;

	public DatabaseMapDataSet(String cityName, int numberofUsers) {
		this.cityName      = cityName;
		this.numberofUsers = numberofUsers;
	}
	
	public DatabaseMapDataSet() {
		this.cityName      = null;
		this.numberofUsers = 0;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getNumberofUsers() {
		return numberofUsers;
	}

	public void setNumberofUsers(int numberofUsers) {
		this.numberofUsers = numberofUsers;
	}

}
