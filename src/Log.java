public class Log {
	
	private static boolean logging = false;

	public static void startLogging() {
		logging = true;
	} 

	public static void log(String message) {
		if(logging)
			System.out.println(message);
	}

}