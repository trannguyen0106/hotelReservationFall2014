import java.util.ArrayList;

/**
*
*	Create Guest Account with name and ID
*	Add or Remove Guest Account To or From Reservation list
*
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/
public class GuestAccount {
	// unique key
    private int guestID;   
    // Guest name
    private String name;
    // reservation list 
    private ArrayList<Reservation> reservation;
    
    /**
     * 
     * constructor without reservation when create a new account
     * @param id is user id
     * @param n is name
     * 
     */
    public GuestAccount(int id, String n)
    {
        guestID = id;
        name = n;
        reservation = new ArrayList<Reservation>();  
    }
    
    /**
     * constructor with reservation when load data into system when program start
     * @param id is user id
     * @param n is name
     * @param r is an array list of reservation
     */
    public GuestAccount(int id, String n, ArrayList<Reservation> r)
    {
        guestID = id;
        name = n;
        reservation = new ArrayList<Reservation>();
        reservation = r;
        
    }
    
    /**
     * adding reservation to array reservation
     * @param r
     * 
     */
    public void addReservation(Reservation r)
    {
        reservation.add(r);
    }
    
    /**
     * get reservation
     * @return
     * 
     */
    public ArrayList<Reservation> getReservation()
    {
        return  (ArrayList<Reservation>) reservation;
    }
    
  /**
   * work on data base
   * @return
   */
    public int getId()
    {
        return guestID;
    }
    
   /**
    * get Guest name
    * @return
    */
    public String getName()
    {
        return name;
    }
  
    /**
     *  Display the user id with name for user.txt
     *  @return
     */
    public String toString()
    {
        return guestID+ " "+name;
    }
   
    /**
     * 
     * remove the Guest Account from Reservation list 
     * @param r1
     * 
     */
    public void removeRe(Reservation r1)
    {
        for (Reservation r : reservation)
        {
            if (r1.equals(r))
                reservation.remove(r1);
        }
    }
}
