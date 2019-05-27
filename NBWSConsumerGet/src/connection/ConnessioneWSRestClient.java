/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author maspes_marco
 */
public class ConnessioneWSRestClient {
    
    private String baseUrl;
    private int statusChiamata;
    private Vector<String> valoriRichieste;
    
    ConnessioneWSRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        
        this.statusChiamata = 0;
        
        valoriRichieste = new Vector<>();
    }
    
    
    public int ricercaAuto(String targa) throws ParserConfigurationException, SAXException
    {
        String doc = "<?xml version = \"1.0\" encoding= \"UTF-8\" ?>";
        
        doc += "<entry>\r\n";
        doc += "<operazione>checkAuto</operazione>\r\n";
        doc += "<targa>" + targa + "</targa>\r\n";
        doc += "</entry>\r\n";
        
        int n = doc.length();
        
        try {
            //invio richiesta al web server
            
            URL server = new URL(baseUrl);
            HttpURLConnection service = (HttpURLConnection) server.openConnection();
            
            service.setRequestProperty("Content-type", "application/xml");
            service.setRequestProperty("Content-length", Integer.toString(n));
            
            service.setDoOutput(true);
            service.setRequestMethod("GET");
            
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(service.getOutputStream(), "UTF-8"));
            output.write(doc);
            output.flush();
            output.close();
            
            service.connect();
            
            statusChiamata = service.getResponseCode();
            if (statusChiamata != 200) {
                return statusChiamata;
            }
            
            
            //ottenimento informazioni dal web server
            
            BufferedReader input = new BufferedReader(new InputStreamReader(service.getInputStream(), "UTF-8"));
            BufferedWriter file = new BufferedWriter(new FileWriter("entry.xml"));
            
            String line;
            while ((line = input.readLine()) != null) {
                file.write(line);
                file.newLine();
            }
            
            input.close();
            file.flush();
            file.close();
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("entry.xml");
            
            Element root = document.getDocumentElement();
            valoriRichieste.clear();
            
            NodeList list = root.getElementsByTagName("persona");
            if (list != null && list.getLength() > 0) {
                for(int i = 0; i< list.getLength(); i++)
                {
                    Element persona = (Element) list.item(i);
                    
                    valoriRichieste.add("Utente:");
                    
                    NodeList nome = persona.getElementsByTagName("nome");
                    if (nome != null && nome.getLength() > 0) {
                        valoriRichieste.add(nome.item(0).getFirstChild().getNodeValue());
                    }
                    
                    NodeList cognome = persona.getElementsByTagName("cognome");
                    if (cognome != null && cognome.getLength() > 0) {
                        valoriRichieste.add(cognome.item(0).getFirstChild().getNodeValue());
                    }
                    
                    valoriRichieste.add("");
                }
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statusChiamata;
    }
    
    public int inserisciAuto(String targa, String assicurazione, String bollo, String inquinamento) throws ParserConfigurationException, SAXException
    {
        String doc = "<?xml version = \"1.0\" encoding= \"UTF-8\" ?>";
        
        doc += "<entry>\r\n";
        doc += "<operazione>inserisciAuto</operazione>\r\n";
        doc += "<targa>" + targa + "</targa>\r\n";
        doc += "<assicurazione>" + assicurazione + "</assicurazione>\r\n";
        doc += "<bollo>" + bollo + "</bollo>\r\n";
        doc += "<inquinamento>" + inquinamento + "</inquinamento>\r\n";
        doc += "</entry>\r\n";
        
        int n = doc.length();
        
        try {
            //invio richiesta al web server
            
            URL server = new URL(baseUrl);
            HttpURLConnection service = (HttpURLConnection) server.openConnection();
            
            service.setRequestProperty("Content-type", "application/xml");
            service.setRequestProperty("Content-length", Integer.toString(n));
            
            service.setDoOutput(true);
            service.setRequestMethod("POST");
            
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(service.getOutputStream(), "UTF-8"));
            output.write(doc);
            output.flush();
            output.close();
            
            service.connect();
            
            statusChiamata = service.getResponseCode();

            return statusChiamata;            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statusChiamata;
    }
    
    void printResult() {
        for(String a : valoriRichieste)
            System.out.println(a);
    }

    int checkAuto(String targa) {
     String doc = "<?xml version = \"1.0\" encoding= \"UTF-8\" ?>";
        
        doc += "<entry>\r\n";
        doc += "<operazione>checkAuto</operazione>\r\n";
        doc += "<targa>" + targa + "</targa>\r\n";
        doc += "</entry>\r\n";
        
        int n = doc.length();
        
        try {
            //invio richiesta al web server
            
            URL server = new URL(baseUrl);
            HttpURLConnection service = (HttpURLConnection) server.openConnection();
            
            service.setRequestProperty("Content-type", "application/xml");
            service.setRequestProperty("Content-length", Integer.toString(n));
            
            service.setDoOutput(true);
            service.setRequestMethod("PUT");
            
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(service.getOutputStream(), "UTF-8"));
            output.write(doc);
            output.flush();
            output.close();
            
            service.connect();
            
            statusChiamata = service.getResponseCode();

            return statusChiamata;            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnessioneWSRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statusChiamata;
    }
}
