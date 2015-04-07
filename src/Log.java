public class Log {
	
	private static boolean logging = false;
	private static boolean makingChart = false;

	public static void startLogging() {
		logging = true;
	} 

	public static void makingChart() {
		makingChart = true;
	}

	public static void log(String message) {
		if(logging)
			System.out.println(message);
	}

	public static void chartLog(String message) {
		if(makingChart)
			System.out.println(message);
	}

}