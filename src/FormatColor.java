
public class FormatColor {
	
	public static String in(String R, String B, String G){
		
		
		String out = "a10000" + fixLen(R) + fixLen(B) + fixLen(G);
		
		
		return out;
	}
	
	public static String fixLen(String s){
		
		if (s.length() == 1) {
			
			s = "00" + s;
		} else if (s.length() == 2) {
			s = "0" + s;
		}
		
		return s;
	}
	

}
