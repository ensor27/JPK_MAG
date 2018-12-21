package XML;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import XML.Parameters;





public class jpk_mag {
	static Connection connection= WB.Connection2DB.dbConnector();
	
public static final String xmlFilePath = "//192.168.90.203/Logistyka/Raporty godzin/jpk.XML";

private static final BigDecimal ONE = null;

private static final BigDecimal Null = null;
	
static SimpleDateFormat godz = new SimpleDateFormat("HH;mm");	
static SimpleDateFormat yyymmdd = new SimpleDateFormat("yyyy-MM-dd");
	
		    public static void main() throws SQLException, ParseException {
	
		        try {
		        	
		        	
		    		// configure directory and files
		        	Parameters.createDirectory();
		        	File f = Parameters.createFile("jpk_MAG.xml");
		        	
					
					
					// create the xml data:
		            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		            Document document = documentBuilder.newDocument();

		            // root element
	 
		            Element root = document.createElementNS("http://jpk.mf.gov.pl/wzor/2016/03/09/03093/","JPK");
		            root.setAttribute("xmlns:etd", "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/");
		            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		            root.setAttribute("xsi:schemaLocation", "http://jpk.mf.gov.pl/wzor/2016/03/09/03093/ Schemat_JPK_MAG(1)_v1-0.xsd");
	 	            document.appendChild(root);
	  
		            // set an attribute 
//	 	            Attr attr1 = document.createAttribute("xsi:schemaLoacation");
//	 	            attr1.setValue("\"http://jpk.mf.gov.pl/wzor/2016/03/09/03093/Schemat_JPK_MAG(1)_v1-0.xsd\"");
//	 	            root.setAttributeNode(attr1);
	 	       //	root.appendChild(document.createElementNS("http://jpk.mf.gov.pl/wzor/2016/03/09/03093/", "blabla"));
	 	       	//root.setAttributeNS("http://jpk.mf.gov.pl/wzor/2016/03/09/03093/", "blabla", "blabla2");
//	 	       	root.setPrefix("http://jpk.mf.gov.pl/wzor/2016/03/09/03093/");
//	 	       	root.
//	 	         
	 
	 
		            document = naglowek(document,root,"2018-09-01","2018-09-30","2018-12-17T14:13:00");
		            document = podmiot(document, root);
		            document = magazyn(document, root);;
		            
		            document = WZ(document,root,"2018-09-01","2018-09-30");
		            
		         
			       	 
			       
			       		
					     			       
			       
					Element PZ = document.createElement("PZ");
					root.appendChild(PZ);	
					
						Element PZWartosc = document.createElement("PZWartosc");
						PZ.appendChild(PZWartosc);
						
						
						
						
				
	
		            //transform the DOM Object to an XML File
	
		            TransformerFactory transformerFactory = TransformerFactory.newInstance();
		            Transformer transformer = transformerFactory.newTransformer();
		            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // to create everywhere a new line in the output
		            DOMSource domSource = new DOMSource(document);
		            
		           // StreamResult streamResult = new StreamResult(new File(xmlFilePath));
	
		            StreamResult streamResult = new StreamResult(f);

		            // If you use

		            // StreamResult result = new StreamResult(System.out);
	
		            // the output will be pushed to the standard output ...
	
		            // You can use that for debugging
	
		 
		            transformer.setOutputProperty("http://www.oracle.com/xml/is-standalone", "yes");
		            transformer.transform(domSource, streamResult);
	
		 
	
		            System.out.println("Done creating XML File");
	
		 
	
		        } catch (ParserConfigurationException pce) {
	
		            pce.printStackTrace();
	
		        } catch (TransformerException tfe) {
	
		            tfe.printStackTrace();
		        }
		        
	
		    }
		    
		    private static Document naglowek(Document doc, Element root, String from, String till, String timestamp){
		    	   // Naglowek element
		   	 
	            Element Naglowek = doc.createElement("Naglowek");
	            root.appendChild(Naglowek);
	 
			            // NAGLOWEK
	            		Element KodFormularza = doc.createElement("KodFormularza");
	            		KodFormularza.setAttribute("kodSystemowy", "JPK_MAG (1)");
	            		KodFormularza.setAttribute("wersjaSchemy", "1-0");
	            		KodFormularza.appendChild(doc.createTextNode("JPK_MAG"));
		 	            Naglowek.appendChild(KodFormularza);
		 
			            Element WariantFormularza = doc.createElement("WariantFormularza");
			            WariantFormularza.appendChild(doc.createTextNode("1"));
			            Naglowek.appendChild(WariantFormularza);
		 
			            Element CelZlozenia = doc.createElement("CelZlozenia");
			            CelZlozenia.appendChild(doc.createTextNode("1"));
			            Naglowek.appendChild(CelZlozenia);
		 	            
			            Element DataWytworzeniaJPK = doc.createElement("DataWytworzeniaJPK");
			            DataWytworzeniaJPK.appendChild(doc.createTextNode(timestamp));
			            Naglowek.appendChild(DataWytworzeniaJPK);
			            
			            Element DataOd = doc.createElement("DataOd");
			            DataOd.appendChild(doc.createTextNode(from));
			            Naglowek.appendChild(DataOd);
			            
			            Element DataDo = doc.createElement("DataDo");
			            DataDo.appendChild(doc.createTextNode(till));
			            Naglowek.appendChild(DataDo);
			            
			            Element DomyslnyKodWaluty = doc.createElement("DomyslnyKodWaluty");
			            DomyslnyKodWaluty.appendChild(doc.createTextNode("PLN"));
			            Naglowek.appendChild(DomyslnyKodWaluty);
			            
			            Element KodUrzedu = doc.createElement("KodUrzedu");
			            KodUrzedu.appendChild(doc.createTextNode("3023"));
			            Naglowek.appendChild(KodUrzedu);
			            
		      // Naglowek element
		    	
		    	return doc;
		    	
		    }
		    private static Document podmiot(Document doc, Element root ){
		    	Element Podmiot1 = doc.createElement("Podmiot1");
			       root.appendChild(Podmiot1);	           
			            
			       		//  Podmiot1

			       		Element IdentyfikatorPodmiotu = doc.createElement("IdentyfikatorPodmiotu");
			       		Podmiot1.appendChild(IdentyfikatorPodmiotu);
			       		
					       		Element etdNIP = doc.createElement("etd:NIP");
					       		etdNIP.appendChild(doc.createTextNode("8960000138"));
					       		IdentyfikatorPodmiotu.appendChild(etdNIP);
					       		
				       		
					       		Element etdPelnaNazwa = doc.createElement("etd:PelnaNazwa");
					       		etdPelnaNazwa.appendChild(doc.createTextNode("Fabryka Automatow Tokarskich"));
					       		IdentyfikatorPodmiotu.appendChild(etdPelnaNazwa);
					       		
					       		Element etdREGON = doc.createElement("etd:REGON");
					       		etdREGON.appendChild(doc.createTextNode("222222222"));
					       		IdentyfikatorPodmiotu.appendChild(etdREGON);
					       		
			       		
			       		Element AdresPodmiotu = doc.createElement("AdresPodmiotu");
			       		Podmiot1.appendChild(AdresPodmiotu);
			       		
			       
					       		Element etdKodKraju = doc.createElement("etd:KodKraju");
					       		etdKodKraju.appendChild(doc.createTextNode("PL"));
					       		AdresPodmiotu.appendChild(etdKodKraju);
					       
					       		Element etdWojewodztwo = doc.createElement("etd:Wojewodztwo");
					       		etdWojewodztwo.appendChild(doc.createTextNode("DOLNASLASK"));
					       		AdresPodmiotu.appendChild(etdWojewodztwo);
					       
					       		Element etdPowiat = doc.createElement("etd:KodKraju");
					       		etdPowiat.appendChild(doc.createTextNode("WROCLAW"));
					       		AdresPodmiotu.appendChild(etdPowiat);
					       
					       		Element etdGmina = doc.createElement("etd:Gmina");
					       		etdGmina.appendChild(doc.createTextNode("WROCLAW"));
					       		AdresPodmiotu.appendChild(etdGmina);
					       					       		
					       		Element etdUlica = doc.createElement("etd:Ulica");
					       		etdUlica.appendChild(doc.createTextNode("GRABISZYNSKA"));
					       		AdresPodmiotu.appendChild(etdUlica);
					       		
					       		Element etdNrDomu = doc.createElement("etd:NrDomu");
					       		etdNrDomu.appendChild(doc.createTextNode("281"));
					       		AdresPodmiotu.appendChild(etdNrDomu);
					       		
//					       		Element etdNrLokalu = document.createElement("etd:NrLokalu");
//					       		etdNrLokalu.appendChild(document.createTextNode(""));
//					       		AdresPodmiotu.appendChild(etdNrLokalu);
					       		
					       		Element etdMiejscowosc = doc.createElement("etd:Miejscowosc");
					       		etdMiejscowosc.appendChild(doc.createTextNode("WROCLAW"));
					       		AdresPodmiotu.appendChild(etdMiejscowosc);
					       		
					       		Element etdKodPocztowy = doc.createElement("etd:KodPocztowy");
					       		etdKodPocztowy.appendChild(doc.createTextNode("53-234"));
					       		AdresPodmiotu.appendChild(etdKodPocztowy);
					       		
					       		Element etdPoczta = doc.createElement("etd:Poczta");
					       		etdPoczta.appendChild(doc.createTextNode("WROCLAW"));
					       		AdresPodmiotu.appendChild(etdPoczta);
		    	return doc;
		    	
		    }
		    private static Document magazyn(Document doc, Element root){
			   Element Magazyn = doc.createElement("Magazyn");
				Magazyn.appendChild(doc.createTextNode("MCG"));
				root.appendChild(Magazyn);	
			   return doc;
			   
		   }
		    private static Document WZ(Document doc, Element root, String start , String stop) throws SQLException, ParseException{
		    	
		    	BigDecimal totalamountWZ = BigDecimal.ZERO;
		    	int countWZ = 0;
		    	Element WZ = doc.createElement("WZ");
			       root.appendChild(WZ);	           
			            
			       		//  Podmiot1

			       		
			       		
			       		
			       		String sql1 = "select Bonnr as NR, Volgnummer,datum, Klantnr, Artikelcode,Artikelomschrijving, Geleverd, besteleenheid,Eenheidsprijs,totaal,Klantnaam,munt, "
			       				+ "tekst, (select SUM(totaal) from leverbondetail where Bonnr = NR) as summ"
			       				+ "	from leverbondetail  where datum between '"+ start +"' and '"+ stop +"'  order by Bonnr ,Volgnummer + 0 ASC";

			    		Statement st1 = connection.createStatement();
			    		ResultSet rs1 = st1.executeQuery(sql1);
			    		while(rs1.next()){
			    			
			    			if (rs1.getInt("Volgnummer")==1){
			    				countWZ++;
			    				Element wzWartosc = doc.createElement("WZWartosc");
					       		WZ.appendChild(wzWartosc);
					       		
				    				Element NumerWZ = doc.createElement("NumerWZ");
						       		NumerWZ.appendChild(doc.createTextNode(rs1.getString("NR")+"/"+rs1.getString("Volgnummer")));
				    				wzWartosc.appendChild(NumerWZ);
						       		
				    						String datum = rs1.getString("datum");
						       		Element DataWZ = doc.createElement("DataWZ");
						       		DataWZ.appendChild(doc.createTextNode(datum));
						       		wzWartosc.appendChild(DataWZ);
						       		
						       		
						       		// if munt <> PLN  then search currency exchange that day and convert the total
						       		String strCena = null;
						       		BigDecimal summ =  new BigDecimal(rs1.getString("summ"));
						       		if(rs1.getString("munt").equals("PLN")){
						       			strCena = rs1.getString("summ");
						       			
						       		}else{
						       				System.out.println("detected not PLN Valuta : " + rs1.getString("munt"));
						       				String exchange = null;
						       				do{
								       			String sql2 = "select tarief from dagkoersen where munt = '"+ rs1.getString("munt") +"' and MUNT_STANDAARD = 'pln' and DATUM = '"+ datum +"'";
								       			Statement st2 = connection.createStatement();
									    		ResultSet rs2 = st2.executeQuery(sql2);
									    		while(rs2.next()){	exchange = rs2.getString("tarief");	}
									    		
									    		System.out.println("searching on date: " + datum);
									    		Date date = yyymmdd. parse(datum);
									    		System.out.println("searching on date: " + date.toString());
									    		Calendar cal = Calendar. getInstance();
									    		cal. setTime(date);
									    		cal.add(Calendar.DAY_OF_MONTH,-1);
									    		
									    		date = Calendar.getInstance().getTime();
									    		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
									    		datum = dateFormat.format(date);
									    		
									    		
									    		
							       				}while (!exchange.equals(null));  //end do
						       				
						       				
						       					BigDecimal bdexchange =  new BigDecimal(exchange);
						       					strCena = summ.multiply(bdexchange).toString();
						       					summ.setScale(2,BigDecimal.ROUND_UP);
						       					
						       				
						       		}// end else if
						       		
						       		BigDecimal bdAmount = new BigDecimal(rs1.getString("summ"));
						       		totalamountWZ = totalamountWZ.add(bdAmount) ;
						       		Element WartoscWZ = doc.createElement("WartoscWZ");
						       		WartoscWZ.appendChild(doc.createTextNode(strCena));
						       		wzWartosc.appendChild(WartoscWZ);
						       		
						       		//add totaal to final sumation of all WZ
						       		
						       		Element DataWydaniaWZ = doc.createElement("DataWydaniaWZ");
						       		DataWydaniaWZ.appendChild(doc.createTextNode(rs1.getString("datum")));
						       		wzWartosc.appendChild(DataWydaniaWZ);
						       		
						       		Element OdbiorcaWZ = doc.createElement("OdbiorcaWZ");
						       		OdbiorcaWZ.appendChild(doc.createTextNode(rs1.getString("Klantnaam")));
						       		wzWartosc.appendChild(OdbiorcaWZ);
						       		
						       		Element NumerFaWZ = doc.createElement("NumerFaWZ");
						       		NumerFaWZ.appendChild(doc.createTextNode("8960000138"));
						       		wzWartosc.appendChild(NumerFaWZ);
						       		
						       		Element DataFaWZ = doc.createElement("DataFaWZ");
						       		DataFaWZ.appendChild(doc.createTextNode("8960000138"));
						       		wzWartosc.appendChild(DataFaWZ);
				    				
			    				
			    			} //ENDIF for WZWartosz
			    			
			    			Element WZWiersz = doc.createElement("WZWiersz");
				       		WZ.appendChild(WZWiersz);
				       		
					       		Element Numer2WZ = doc.createElement("Numer2WZ");
					       		Numer2WZ.appendChild(doc.createTextNode(rs1.getString("NR")+"/"+rs1.getString("Volgnummer")));
					       		WZWiersz.appendChild(Numer2WZ);
					       		
					       		Element KodTowaruWZ = doc.createElement("KodTowaruWZ");
					       		KodTowaruWZ.appendChild(doc.createTextNode(rs1.getString("Artikelcode")));
					       		WZWiersz.appendChild(KodTowaruWZ);
					       		
					       		
					       		
								String StrNazwaTowaruWZ = rs1.getString("Artikelomschrijving");
								if (rs1.getString("Artikelcode").equals("M") )	{
									String StrTekst = rs1.getString("Tekst");
									System.out.println("for bonnummer "+rs1.getString("NR")+"/"+rs1.getString("Volgnummer")+ " we have following text: "+StrTekst);
									StrNazwaTowaruWZ=StrTekst;
								}
										
					       		
					       		Element NazwaTowaruWZ = doc.createElement("NazwaTowaruWZ");
					       		NazwaTowaruWZ.appendChild(doc.createTextNode(StrNazwaTowaruWZ));
					       		WZWiersz.appendChild(NazwaTowaruWZ);
					       		
					       		Element IloscWydanaWZ = doc.createElement("IloscWydanaWZ");
					       		IloscWydanaWZ.appendChild(doc.createTextNode(rs1.getString("Geleverd")));
					       		WZWiersz.appendChild(IloscWydanaWZ);
					       		
					       		Element JednostkaMiaryWZ = doc.createElement("JednostkaMiaryWZ");
					       		JednostkaMiaryWZ.appendChild(doc.createTextNode(rs1.getString("besteleenheid")));
					       		WZWiersz.appendChild(JednostkaMiaryWZ);
					       		
					       		Element CenaJednWZ = doc.createElement("CenaJednWZ");
					       		CenaJednWZ.appendChild(doc.createTextNode(rs1.getString("Eenheidsprijs")));
					       		WZWiersz.appendChild(CenaJednWZ);
					       		
						       		BigDecimal bdprice = new BigDecimal(rs1.getString("Eenheidsprijs"));
						       		BigDecimal bdqty = new BigDecimal(rs1.getString("Geleverd"));
						       		BigDecimal total = bdprice.multiply(bdqty);
						       				total.setScale(2,BigDecimal.ROUND_UP);
					       		
					       		Element WartoscPozycjiWZ = doc.createElement("WartoscPozycjiWZ");
					       		WartoscPozycjiWZ.appendChild(doc.createTextNode(total.toString()));
					       		WZWiersz.appendChild(WartoscPozycjiWZ);
					       		
				    			
			    			
			    			
			    			
			    			
			    			
			    			
			    			
			    		} //END WHILE
			    		
			    		Element WZCtrl = doc.createElement("WZCtrl");
			       		WZ.appendChild(WZCtrl);
			    		
			       		Element LiczbaWZ = doc.createElement("LiczbaWZ");
			       		LiczbaWZ.appendChild(doc.createTextNode(String.valueOf(countWZ)));
			       		WZCtrl.appendChild(LiczbaWZ);
			       		
			       		
			       			totalamountWZ.setScale(2,BigDecimal.ROUND_UP);
			       		Element SumaWZ = doc.createElement("SumaWZ");
			       		SumaWZ.appendChild(doc.createTextNode(totalamountWZ.toString()));
			       		WZCtrl.appendChild(SumaWZ);
			    		
			    		
			    		
			    		
			    		st1.close();
			    		rs1.close();
			       		
					       		
					       		
					       		
					       		return doc;
					       		
		    }

			private static BigDecimal BigDecimal(String string) {
				// TODO Auto-generated method stub
				return null;
			}
					       		
					       		
}
