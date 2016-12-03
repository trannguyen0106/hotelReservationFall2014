import java.util.ArrayList;
import java.util.Observable;

/**
* Data Model for Reservation
* 
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/

public class DataModel extends Observable
{
    // Room list
    private RoomInfo[] roomList;
    // Reservation List
    private ArrayList<Reservation> reList;
    // Available room list
    private ArrayList<Integer> availableRoom;
    
    /**
     * Display available room in list
     * 
     * @param r is an array of room number
     * @param rel an arraylist of reservations
     */
    public DataModel(RoomInfo[] r, ArrayList<Reservation> rel)
    {
        roomList = (RoomInfo[]) r.clone();
        reList = (ArrayList<Reservation>) rel.clone();
           
    }// end DataModel
    
    /**
     * Set available room
     * @param av is arrayList  of all available room
     */
    public void setAvaiRoom(ArrayList<Integer> av)
    {
        availableRoom = (ArrayList<Integer>) av.clone();
     //update model change Listener
        setChanged();
        notifyObservers();
    }
	
   /**
    * get available room 
    * @return
    */
    public ArrayList<Integer> getAvaiRoom()
    {
        return availableRoom;   
    }
    
    
}
