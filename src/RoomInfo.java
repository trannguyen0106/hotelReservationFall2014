/**
* Information of room such as room type, room number and price
* 
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/
public class RoomInfo {
	//Room type 
    private String roomType; 
    //Room number 
    private int roomNumber;
    //Price of room 
    private int price;
    
    /** 
     * constructor for roomInfo
     * @param s is room type
     * @param r is room number
     * @param p is the price
     */
    public RoomInfo (String s, int r,int p)
    {
        roomType = s;
        roomNumber = r;
        price = p;
    }
    
    /**
     * Get room type as Luxury or Economy
     * @return
     */
    public String getRoomType()
    {
        return roomType;
    }
	
    /**
     * Get room number 1-20
     * @return
     */
    public int getRoomNumber()
    {
        return roomNumber;
    }
    
    /**
     * Get price of room as $200 for luxury or $80 for economy
     * @return
     */    
    public int getRoomPrice()
    {
        return price;
    }
    
    /**
     * Display information room 
     * @return
     */    
    public String toString()
    {
        return roomType + " room "+roomNumber+" :"+price;
    }
    
}// end roonInfo class
