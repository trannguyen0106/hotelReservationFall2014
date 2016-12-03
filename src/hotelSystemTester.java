import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
/**
* Hotel Reservation System Tester
* 	Load reservation information from reservation.txt 
* 	Scan ID from user.txt and add into resevation list
*	Call the GUI in Menu class 
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/

public class hotelSystemTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, FileNotFoundException 
    {
        // load the information from the reservation
        Scanner in = new Scanner(new File("reservation.txt"));
        ArrayList<Reservation> listReserv = new ArrayList<Reservation>();
        ArrayList<GuestAccount> listAccount = new ArrayList<GuestAccount>();
        Date current = new Date();
        SimpleDateFormat formaterDay = new SimpleDateFormat("MM/dd/yyyy");
        
        while(in.hasNext())
        {
            String checkIn = in.next();
            String checkOut = in.next();
            int roomNumber  = Integer.parseInt(in.next());
            int ID = Integer.parseInt(in.next());
            String name = in.next();
            Date out = formaterDay.parse(checkOut);
            if(!(out.before(current)))
            {
                Reservation load = new Reservation(checkIn, checkOut, roomNumber, ID, name);
                listReserv.add(load);
            }
        }
        
        // Scanner the user txt and add the reservation list in
        Scanner inAccount = new Scanner(new File("user.txt"));
        while(inAccount.hasNext())
        {
            ArrayList<Reservation> reserPerson = new ArrayList<Reservation>();
            int id = Integer.parseInt(inAccount.next());
            String name = inAccount.next();
            
            // check if user have reservation or not to add it in
            for(Reservation r: listReserv)
            {
                if(r.getID() == id)
                {
                    reserPerson.add(r);
                }
            }
            GuestAccount user = new GuestAccount(id, name, reserPerson);
            listAccount.add(user);
        }
        in.close();
        inAccount.close();
        
    // Call the GUI interface of Hotel Reservation System
        Menu menu = new Menu(listReserv,listAccount);
    }
}
