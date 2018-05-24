
public class TrafficLineGraphDataSet {
	
	private String  xAxisName, yAxisName, nameofChart;
	private double  xAxisStartNumber, xAxisLastNumber, yAxisStartNumber, yAxisLastNumber;
	private int[][] dataArray;

	public TrafficLineGraphDataSet(int[][] dataArray) {
		this.xAxisName        = "Days";
		this.yAxisName        = "Users' Interaction";
		this.nameofChart      = "Users";
		this.xAxisStartNumber = dataArray[dataArray.length - 1][0];
		this.xAxisLastNumber  = dataArray[0][0];
		this.yAxisStartNumber = 0;
		this.yAxisLastNumber  = findMax(dataArray) + 5;
		this.dataArray        = dataArray;
	}

	public int findMax(int[][] array) {
		int max = array[0][1];
		for (int[] value : array) {
			if (max < value[1]) {
				max = value[1];
			}
		}
		System.out.println(max);
		return max;
	}

	public String getxAxisName() {
		return xAxisName;
	}

	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}

	public String getyAxisName() {
		return yAxisName;
	}

	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

	public String getNameofChart() {
		return nameofChart;
	}

	public void setNameofChart(String nameofChart) {
		this.nameofChart = nameofChart;
	}

	public double getxAxisStartNumber() {
		return xAxisStartNumber;
	}

	public void setxAxisStartNumber(double xAxisStartNumber) {
		this.xAxisStartNumber = xAxisStartNumber;
	}

	public double getxAxisLastNumber() {
		return xAxisLastNumber;
	}

	public void setxAxisLastNumber(double xAxisLastNumber) {
		this.xAxisLastNumber = xAxisLastNumber;
	}

	public double getyAxisStartNumber() {
		return yAxisStartNumber;
	}

	public void setyAxisStartNumber(double yAxisStartNumber) {
		this.yAxisStartNumber = yAxisStartNumber;
	}

	public double getyAxisLastNumber() {
		return yAxisLastNumber;
	}

	public void setyAxisLastNumber(double yAxisLastNumber) {
		this.yAxisLastNumber = yAxisLastNumber;
	}

	public int[][] getDataArray() {
		return dataArray;
	}

	public void setDataArray(int[][] dataArray) {
		this.dataArray = dataArray;
	}

}
