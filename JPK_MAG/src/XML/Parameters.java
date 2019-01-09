package XML;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import PDF.Parameters;

public class Parameters {

	private static String PathToSave= "//192.168.90.203/Logistyka/Listy";
	private static String PathToSaveHours= "//192.168.90.203/Logistyka/Bookkeeping/JPK_MAG";
	private static String PathToDB = "//192.168.90.203/Logistyka/Tosia/Projekty JAVA";
	
	public static String getPathToSave(){
		return PathToSave;
	}
	
	public static String getPathToSaveHours(){
		return PathToSaveHours;
	}
	
	public static void setPathToSave (String s){
		PathToSave = s;
	}

	public static String getPathToDB() {
		return PathToDB;
	}

	public static void setPathToDB(String pathToDB) {
		PathToDB = pathToDB;
	}
	
	public static void createDirectory(){
		String path = getPath();
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		        //handle it
		    }
		}
	}

public static File createFile(String name){
	String path = getPath();
	SimpleDateFormat godz = new SimpleDateFormat("HH;mm");
	Calendar date = Calendar.getInstance();
	
	File f = new File(path+name);
	if(f.exists() && !f.isDirectory()){
		f = new File(path+ godz.format(date.getTime())+" "+name);
	}
	
	return f;
	
}

public static String getPath(){
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
	String path = Parameters.getPathToSaveHours()+"/"+doNazwy.format(calendar.getTime())+"/";
	
	return path;
}





}
