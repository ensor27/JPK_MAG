package XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import org.apache.commons.io.FilenameUtils


public class exchangerates {
	private String currency; // waluta
    private String date_1; // data poczatkowa
    private String date_2; // data koncowa
 
    //private HttpFile hf; // klasa umozliwiajaca sciagniecie pliku txt ze strony
    private String content; // plik txt
 
    private List<String> xmlFileNames = new ArrayList<String>(); // lista na nazwy plikow z literka 'c'
 
    //private Parser parser = new Parser(); // parser xml
   // private Exchange exchange;
	
	 private static final BigDecimal Null = null;

	public static void ReadXMLFile(String Valuta , String datum) {

 	    try {

 	    	
 	    	URL xmlURL = new URL("http://www.nbp.pl/kursy/xml/a024z100205.xml");
 	    	InputStream xml = xmlURL.openStream();
 	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 	    	DocumentBuilder db = dbf.newDocumentBuilder();
 	    	Document doc = db.parse(xml);
 	    	xml.close();
 	    	
 				
 	    	//optional, but recommended
 	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
 	    	doc.getDocumentElement().normalize();

 	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
 				
 	    	NodeList nList = doc.getElementsByTagName("pozycja");  // get all the node with name "pozycja"
 		
 	    	System.out.println("----------------------------");

 	    	for (int temp = 0; temp < nList.getLength(); temp++) {

 	    		Node nNode = nList.item(temp);
 					
 	    		System.out.println("\nCurrent Element :" + nNode.getNodeName());
 					
 	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

 	    			Element eElement = (Element) nNode;

 	    			
 	    			//examples 
 	    			//System.out.println("nazwa_waluty: " + eElement.getAttribute("nazwa_waluty"));
 	    			System.out.println("nazwa_waluty: " + eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent());
 	    			System.out.println("przelicznik: " + eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
 	    			System.out.println("kod_waluty: " + eElement.getElementsByTagName("kod_waluty").item(0).getTextContent());
 	    			System.out.println("kurs_sredni: " + eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent());
 				
// 				System.out.println("przelicznik : " + eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
// 				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
// 				System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
// 				System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

 	    		} //end if
 	    	} //end if
 	    } catch (Exception e) {
 		e.printStackTrace();
 	    }
 	  }

	
	/**
	 * Method that returns a BigDecimal value in PLN converted on a certain day according NBP
	 *
	 * <p>Use {@link #doMove(int theFromFile, int theFromRank, int theToFile, int theToRank)} to move a piece.
	 *
	 * @param value 	the string value to be converted
	 * @param valuta 	the valuta to be converted from in String format eg "EUR"
	 * @param datum    	String  format as "YYYY-MM-DD"
	 * @return            converted value in PLN with two digits behind the comma
	 * @since             1.0
	 */	
 public static BigDecimal convertToPln(String value, String valuta , String datum) {

	SimpleDateFormat YYYYMMDDformatter = new SimpleDateFormat("yyyy-MM-dd"); 
	SimpleDateFormat YYMMDDformatter = new SimpleDateFormat("yyMMdd"); 
	
	Calendar calendar = Calendar.getInstance();
 	BigDecimal result = Null ;
 	BigDecimal bdvalue =  new BigDecimal ( value) ;
 	BigDecimal bdexchangerate = null ;
 	BigDecimal bdexchangerate1 = null ;
 	Date date = null;
	 	try {
			date = YYYYMMDDformatter.parse(datum);
			calendar.setTime(date);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 	
 	
	    try {
	    	//compose correct file  
	    		//	char 1	"a" for average exchange rate
	    		//  char 2-4 "day of year"
	    		//	char 5   fixed to "z"
	    		//  char 6-7 year
	    		//	char 8-9 month
	    		//	char 10-11 day
	    	int dayofyear =  calendar.get(Calendar.DAY_OF_YEAR);
	    	String dayofyear1 = String.format("%03d", dayofyear);
	    	String yearformat = YYMMDDformatter.format(date);
	    	String plik = "a" + dayofyear1 + "z" + yearformat + ".xml";
	    	System.out.println(plik);
	    	
	    	
//	    	URL url = new URL("http://www.example.com/some/path/to/a/file.xml?foo=bar#test");
//
//	        System.out.println(FilenameUtils.getBaseName(url.getPath())); // -> file
	    	//URL url1 = new URL("http://http://www.nbp.pl/Kursy/xml/dir.aspx?tt=A");
//	    	String url = "http://www.nbp.pl/Kursy/xml/";
//	    			String filename = Paths.get(new URI(url).getPath()).getFileName().toString();
//	    	System.out.println(filename);
	    	


//	    	URL url1 = new URL("http://www.nbp.pl/Kursy/xml/");
//	    	//URL url = new URL("http://.............../pages/");
//	        File f=new File(url1.getFile());
//	        String list[]=f.list();
//	        for(String x:list)
//	        {
//	            System.out.println(x);
//	        }
	    	
	    	


URL url = new URL("http://www.nbp.pl/kursy/xml/dir.aspx?tt=A");
URLConnection spoof = url.openConnection();

//Spoof the connection so we look like a web browser
spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)" );
BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
String strLine = "";
String finalHTML = "";
//Loop through every line in the source
while ((strLine = in.readLine()) != null){
   finalHTML += strLine + "/n";
}
System.out.println(finalHTML);

	    	
	    	// go to log in NBP website and take average exchange rate of that certain day
	    URL xmlURL = new URL("http://www.nbp.pl/kursy/xml/a108z180606.xml");
	    	//URL xmlURL = new URL("http://www.nbp.pl/kursy/xml/" + plik);
	    	InputStream xml = xmlURL.openStream();
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	Document doc = db.parse(xml);
	    	xml.close();
	    	
			
	    	
	    	
	    	//optional, but recommended
	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    	doc.getDocumentElement().normalize();  // to put all blocks nicely together

	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				
	    	NodeList nList = doc.getElementsByTagName("pozycja");  // get all the node with name "pozycja"
		
	    	System.out.println("----------------------------");

	    	for (int temp = 0; temp < nList.getLength(); temp++) {

	    		Node nNode = nList.item(temp);
					
	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
					
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;
	    			
	    			if (valuta.equals(eElement.getElementsByTagName("kod_waluty").item(0).getTextContent())){
	    					
	    				bdexchangerate = new BigDecimal(eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent().replace(',', '.'));
	    				bdexchangerate1 = new BigDecimal(eElement.getElementsByTagName("przelicznik").item(0).getTextContent().replace(',', '.'));
	    				bdexchangerate = bdexchangerate.divide(bdexchangerate1, 4, RoundingMode.HALF_EVEN);
	    				result = bdvalue.multiply(bdexchangerate).setScale(2, RoundingMode.HALF_EVEN);
	    				
	    				//bdexchangerate = new BigDecimal(eElement.getElementsByTagName("przelicznik").item(0).getTextContent().replace(',', '.')).divide( new BigDecimal(eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent().replace(',', '.')));
 	    			
	    				//examples 
	    			//System.out.println("nazwa_waluty: " + eElement.getAttribute("nazwa_waluty"));
//	    			System.out.println("nazwa_waluty: " + eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent());
//	    			System.out.println("przelicznik: " + eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
//	    			System.out.println("kod_waluty: " + eElement.getElementsByTagName("kod_waluty").item(0).getTextContent());
//	    			System.out.println("kurs_sredni: " + eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent());
//	    			System.out.println("test Jurgen: " + bdexchangerate);
//	    			System.out.println("test Jurgen: " + bdexchangerate1);
//	    			
	    			
	    		}
		
//				System.out.println("przelicznik : " + eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
//				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//				System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//				System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

	    		} else{
	    			result =  null;
	    		}
	    		
	    		
	    	} //end if
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		return result;
	  }
 
 private static StringBuffer getStringFromFile(File fileName, String charSetOfFile) {
     StringBuffer strBuffer = new StringBuffer();
     
     
     
     try(FileInputStream fis = new FileInputStream(fileName)) {
         byte[] buffer = new byte[10240]; //10K buffer;
         int readLen = -1;

         while( (readLen = fis.read(buffer)) != -1) {
             strBuffer.append( new String(buffer, 0, readLen, Charset.forName(charSetOfFile)));
         }

     } catch(Exception ex) {
         ex.printStackTrace();
         strBuffer = new StringBuffer();
     }

     return strBuffer;
 }
 
 exchangerates(String _currency, String _date_1, String _date_2) {
     this.currency = _currency;
     this.date_1 = changeData(_date_1);
     this.date_2 = changeData(_date_2);

    // Run();
 }

// public void Run() {
//     // Pobranie pliku txt z nazwami plikow i wprowadzenie ich do String
//     try {
//         hf = new HttpFile(new URL("http://www.nbp.pl/kursy/xml/dir.txt")); 
//         content = new String(hf.getData()); // pobranie danych z sieci jako string
//     } catch (MalformedURLException e) {
//         e.printStackTrace();
//     } catch (IOException e) {
//         e.printStackTrace();
//     }
//
//     // Wyszukanie i wstawienie do listy plikow ktore zaczynaja sie na 'c'
//     for (int i = 0; i < content.length(); i++) {
//         if (content.charAt(i) == 'c') {
//             xmlFileNames.add(content.substring(i, i + 11) + ".xml");
//         }
//     }
//
//     // jesli pierwsza data wieksza od drugiej to zamien
//     if(date_1.compareTo(date_2) > 0) { 
//         String tmp;
//         tmp = date_1;
//         date_1 = date_2;
//         date_2 = tmp;
//     }
//
//     // Wyszukanie w liscie odpowiedniego przedzialu dat
//     int indexBegin = 0;
//     int indexEnd = xmlFileNames.size() - 1;
//     for (int i = 0; i < xmlFileNames.size(); i++) {
//         if (xmlFileNames.get(i).substring(5, 11).equals(date_1)) {
//             indexBegin = i; // poczatek 
//         }
//         if (xmlFileNames.get(i).substring(5, 11).equals(date_2)) {
//             indexEnd = i; // koniec
//         }
//     }
//
//     // Poobieranie danych ze strony NBP i obliczanie sredniej kupna i odchylenia standardowego dla sprzedazy
//     double mediumExchangePurchaseRate = 0.0;
//     double ex2 = 0.0; // dla odchylenia standardowego (wartosc oczekiwana)
//     double ex = 0.0; // dla odchylenia standardowego (wartosc oczekiwana)
//
//     int difference = (indexEnd + 1) - indexBegin;
//     for (int i = indexBegin; i < (indexEnd + 1); i++) {
//         try {
//             exchange = parser.getData(xmlFileNames.get(i), currency);
//             mediumExchangePurchaseRate += exchange.getPurchaseRate(); // suma dla sredniej (exchange)
//             ex += exchange.getSellingRate(); // suma dla war oczekiwanej (sprzedaz)
//             ex2 += (exchange.getSellingRate() * exchange.getSellingRate()); // suma dla wart oczekiwanej (sprzedaz)
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
//
//     // liczenie wartosci oczekiwanej (sredniej)
//     ex2 = ex2 / difference; 
//     ex = ex / difference; 
//
//     // wyswietlenie danych
//     System.out.printf("%,.4f\n", (mediumExchangePurchaseRate / difference));
//     System.out.printf("%,.4f\n", Math.sqrt(ex2 - (ex * ex)));
// }
 
 
	 // zmiana wygladu daty
	 private String changeData(String oldData) {
	     return oldData.substring(2, 4) + oldData.substring(5, 7)
	             + oldData.substring(8, 10);
	 }
	
	 private void swap(String str1, String str2) {
	     String tmp;
	     tmp = str1;
	     str1 = str2;
	     str2 = tmp;
	 }

}
