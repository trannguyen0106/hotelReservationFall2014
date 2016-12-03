
import java.util.*;
import javax.swing.*;

/**
* 
* View MVC pattern 
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/

public class ViewMVC implements Observer
{
    private ArrayList<Integer> avaiR;
    private DataModel model;
    private JFrame mvcFrame ;
    private JPanel mvcPanel = new JPanel();
    
    /**
    * Constructor view
    */
    public ViewMVC()
    {
        avaiR = new ArrayList<Integer>();  
        //adding text button room avai to panel
        for (int i = 0;i<avaiR.size();i++)
        {
            String text = "Room "+ avaiR.get(i);
            JButton button = new JButton(text);
            mvcPanel.add(button);
        }
        mvcFrame = new JFrame("MVC pattern Frame ");
        mvcFrame.setSize(500, 500);
        mvcFrame.add(mvcPanel);
        mvcFrame.setVisible(true);
        mvcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     
   /**
    * update View
    * @param a is array list of all available room
    */
    public void updateView(ArrayList<Integer> a)
    {    
        mvcPanel.removeAll();
        for (int i = 0;i<avaiR.size();i++)
        {
            String text = "Room "+ avaiR.get(i);
            JButton button = new JButton(text);
            mvcPanel.add(button);
        }   
        mvcFrame.setVisible(true);
    }
    
  /**
   * Call the Model and update the view
   * @param ob is Observable
   * @param object is obj
   */
   public void update(Observable ob, Object obj)
   {
       model = (DataModel)ob;
       avaiR = model.getAvaiRoom();
       updateView(avaiR);
       
   }
    
}
