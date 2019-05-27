/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author vendramini_simone
 */
public class ConnessioneWSRest {
    
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        ConnessioneWSRestClient webService = new ConnessioneWSRestClient("http://localhost:8080/ProvaWeb");
        
        System.out.println("Inserisci l'operazione desiderata");
        System.out.println("1 - Inserisci auto");
        System.out.println("2 - Gestione auto");
        System.out.println("3 - Ricerca auto");
        System.out.println("0 - Termina programma");
        
        String scelta = br.readLine();
        System.out.println("");
        
        while(!scelta.equals("0"))
        {
            switch(Integer.parseInt(scelta)){
                /*case 1:
                    webService.visualizzaUtenti();
                    webService.printResult();
                    break;*/
                case 1:
                    System.out.println("Inserisci targa");
                    String targa = br.readLine();
                    
                    System.out.println("Inserisci scadenza assicurazione");
                    String assicurazione = br.readLine();
                    
                    System.out.println("Inserisci scadenza bollo");
                    String bollo = br.readLine();
                    
                    System.out.println("Inserisci classe inquinamento");
                    String inquinamento = br.readLine();
                    
                    
                    int ritorno = webService.inserisciAuto(targa,assicurazione,bollo,inquinamento);
                    if(ritorno == 200)
                        System.out.println("Inserimento effettuato");
                    else
                        System.out.println("Inserimento non effettuato");
                    
                    break;
                case 2:
                    System.out.println("Inserisci targa");
                    String targa1 = br.readLine();
                    
                    ritorno = webService.checkAuto(targa1);
                    
                    if(ritorno == 200)
                        System.out.println("Auto contassegnata");
                    else
                        System.out.println("Inserimento non effettuato");
                    
                    break;
                case 3:
                    System.out.println("Inserisci targa");
                    String targa2 = br.readLine();
                    
                    ritorno = webService.ricercaAuto(targa2);
                    
                    if(ritorno == 200){
                        webService.printResult();
                    }else{
                        System.out.println("Inserimento non effettuato");
                    }
                    break;
                default:
            }
            
            System.out.println("Inserisci l'operazione desiderata");
            System.out.println("1 - Inserisci auto");
            System.out.println("2 - Gestione auto");
            System.out.println("3 - Ricerca auto");
            System.out.println("0 - Termina programma");
            
            scelta = br.readLine();
            System.out.println("");
        }
    }
}
