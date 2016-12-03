
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
* Use the Strategy pattern to design two different format of the receipt
* Print receipt for room reservation.  There are 2 types of receipts are Simple and Comprehensive
* A simple receipt shows user id, name, and reserved rooms and corresponding total amount dues only made through this particular transaction.
* A comprehensive receipt shows user id, name, and all valid reservations made by this user with corresponding total amount due.
* 
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
 */

 //concrete class
public class Receipt implements PrintReceipt
{
    private GuestAccount guest;
    private ArrayList<Reservation> listRe;
    SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
    
	/**
     * Constructor Receipt with Guest account and reservation list
     * @param g is guest account information
     * @param l is arraylist of reservation
     */
    public Receipt(GuestAccount g,ArrayList<Reservation> l )
    {
        guest = g;
        listRe =  (ArrayList<Reservation>) l.clone();    
    }
    
	/**
     * Print simple receipt with particular transaction
     * @return String
     */
    public String simpleReceipt()
    {
        String receipt = "User ID: "+ guest.getId()+"\n"+"User name: "+ guest.getName();
        int total=0;
        for (Reservation r : listRe)
        {
            receipt = receipt+ "\n- Room number: "+r.getRoomInfo().getRoomNumber() + "\n  Check in: "+ date.format(r.getCheckin())
                    + " Check Out: " +date.format(r.getCheckout());
            total = total+ r.getTotalPrice();
        }
        receipt = receipt+ "\n"+ "* Total: $"+ total;
        return receipt;
                
    }
    
    /**
     * Print comprehensive receipt with all valid reservation
     * @return String
     */
    public String comprehensive()
    {
        String receipt = "User ID: "+ guest.getId()+"\n"+"User name: "+ guest.getName();
        int total=0;
        for (Reservation r : guest.getReservation())
        {
            receipt = receipt+"\n- Room number: "+r.getRoomInfo().getRoomNumber() + 
                    "\n  Check in: "+ date.format(r.getCheckin())
                    + " Check Out: " +date.format(r.getCheckout());
            total = total+ r.getTotalPrice();
        }
        receipt = receipt+ "\n"+ "* Total: $"+ total;
        return receipt;
    }
}
