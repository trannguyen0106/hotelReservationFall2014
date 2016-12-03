
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
*
*Information of reservation rooms such as check-in date, check-out date, room number, User ID, information 
* of rooms, total price
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/

public class Reservation 
{	
	//Check-in date
    private Date checkin;
    //Check-out date
    private Date checkout;
    //Format days 
    private SimpleDateFormat formaterDay;
    //Room Number
    private int roomN;
    //User ID 
    private int userID;
    //Information of Room
    private RoomInfo room;
    //Name of User
    private String name;
    //Total price of reservation
    private int totalPrice;
    
   /**
    * Constructor for Reservation include check-in date , check-out date, room number, User ID , and name.
    * @param in  check-in date
    * @param out check out date
    * @param number room  number
    * @param id is User ID
    * @param n is name of users
    * @throws ParseException
    */
    public Reservation(String in,String out, int number, int id, String n) throws ParseException
    {
        roomN = number;
        // Room number 1-10 for Luxury type
        if(number < 11)
        {
            room = new RoomInfo("Luxury", roomN, 200);
        }
        else
        {
        // Room number 11-20  for Economy type
            room = new RoomInfo("Economy", roomN, 80);
        }

        userID = id;
        name = n;
        
        //Set date pattern
        formaterDay = new SimpleDateFormat("MM/dd/yyyy");
        checkin =  formaterDay.parse(in);
        checkout =  formaterDay.parse(out);
        
        long startDateTime = checkin.getTime();
        long endDateTime = checkout.getTime();
        long milPerDay = 1000*60*60*24; 
        
        // calculate vacation duration in days
        int numOfDays = (int) ((endDateTime - startDateTime) / milPerDay); 
        numOfDays = numOfDays +1 ;  // add one to include start day
        totalPrice = (numOfDays * room.getRoomPrice());
        
    }
	
    /**
     * Get check-in date
     * @return
     */
    public Date getCheckin()
    {
        return checkin;
    }
    
    /**
     * Get check-out date
     * @return
     */
    public Date getCheckout()
    {
        return checkout;
    }
    
    /**
     * Get information of room
     * @return
     */
    public RoomInfo getRoomInfo()
    {
        return room;
    }
    
    /**
     * Get room number
     * @return
     */
    public int getRoomN()
    {
        return roomN;
    }
    
    /**
     * Get total price of reservation
     * @return
     */
    public int getTotalPrice()
    {
        return totalPrice;
    }
    
    /**
     * Get user ID to log-in reservation system
     * @return
     */
    public int getID()
    {
        return userID;
    }
    
    /**
     * Get user name
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * String for saving the file into .txt
     * @return
     */
    public String toString()
    {
        SimpleDateFormat day  = new SimpleDateFormat("MM/dd/yyyy");
        String in = day.format(checkin);
        String out = day.format(checkout);
        return in+" "+out+" "+ room.getRoomNumber() + " " + userID +" "+name;
    }
	
    /**
     * View room number, check-in , check-out of reservation
     * @return
     */
    public String toView()
   {
        SimpleDateFormat day  = new SimpleDateFormat("MM/dd/yyyy");
        String in = day.format(checkin);
        String out = day.format(checkout);
        return "Room: "+roomN+ "   Check in: "+ in + "   Check out: "+ out;
   }
}
