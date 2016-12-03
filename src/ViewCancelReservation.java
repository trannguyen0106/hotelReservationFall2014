
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author Windows
 */
public class ViewCancelReservation {
    
    private final JFrame frame = new JFrame("View/Cancel Reservation");
    private GuestAccount currentGuest;
    private ArrayList<Reservation> currentGuestReserv = new ArrayList<Reservation>();
    //edit more
    private ArrayList<Reservation> listRe = new ArrayList<Reservation>();
    private Reservation selectRe;
    private Date selectIn;
    private int selectRo;
    private SimpleDateFormat formaterDay;
    private JPanel viewPanel;
    
    public ViewCancelReservation(final GuestAccount g,final ArrayList<Reservation> listre )
    {
        //edit more
        listRe = listre;
        formaterDay = new SimpleDateFormat("MM/dd/yyyy");
        
        currentGuest = g;
        currentGuestReserv = currentGuest.getReservation();
        frame.setLayout(null);
        
        
        
        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollView = new JScrollPane(viewPanel);
        //adding data for current user reservation here  use for loop make button contain date and add to viewPanel
 /*       for (Reservation r: currentGuest.getReservation())
        {
            
            String text = r.getCheckin()+" "+r.getCheckout()+" "+r.getRoomInfo().getRoomType();
            JButton viewButton = new JButton(text);
            viewPanel.add(viewButton);
            
        }
   */   
        
        
   /*     
        JButton[] viewButton = new JButton[currentGuestReserv.size()];
        
        int i = 0;
        for (Reservation r: currentGuestReserv)
        {
            
            String text = r.toView();
            viewButton[i] = new JButton(text);
            viewButton[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //To change body of generated methods, choose Tools | Templates.
                    
                    JButton button = (JButton)e.getSource();
                    System.out.println("testing for susstring: "+(String)button.getText().substring(22, 31)+"   room"+(String)button.getText().substring(7, 9) );
                    try {
                        selectIn =  formaterDay.parse((String)button.getText().substring(22, 31));
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewCancelReservation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    selectRo = Integer.parseInt((String)button.getText().substring(7, 9));
               
                }
            });
            viewPanel.add(viewButton[i]);
            i++;
            
        }// end for loop to create clickable room button
    */
        createRoomButton();
       
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                 //To change body of generated methods, choose Tools | Templates.
                for(Reservation r: currentGuestReserv)
                {
                    if (r.getCheckin().equals(selectIn)&&r.getRoomN()==selectRo)
                        selectRe = r;
                }                    
                currentGuestReserv.remove(selectRe);
                listRe.remove(selectRe);
                g.getReservation().remove(selectRe);
                listre.remove(selectRe);
                System.out.println("TEsting for size of "+ currentGuestReserv.size());
                viewPanel.removeAll();
                createRoomButton();
       //         frame.add(viewPanel);
                frame.repaint();
                frame.setVisible(true);
                
            }
        });// end cancel button
        
        
        
        scrollView.setBounds(10, 10, 400, 350);
        cancelButton.setBounds(420,250 , 100, 50);
    //    JPanel scrollPanel = new JPanel();
    //    scrollPanel.add(scrollView);
    //    scrollPanel.setBounds(10, 30, 500, 500);
        frame.setSize(600, 600);
        frame.add(scrollView);
    //    frame.add(scrollPanel);
        frame.add(cancelButton);
        
      
  //      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }// end constructor for view and cancel reservation
    
    public void createRoomButton()
    {
        JButton[] viewButton = new JButton[currentGuestReserv.size()];
        
        int i = 0;
        for (Reservation r: currentGuestReserv)
        {
            
            String text = r.toView();
            viewButton[i] = new JButton(text);
            viewButton[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //To change body of generated methods, choose Tools | Templates.
                    
                    JButton button = (JButton)e.getSource();
                    System.out.println("testing for susstring: "+(String)button.getText().substring(20, 31)+"   room"+(String)button.getText().substring(6, 8) );
                    try {
                        selectIn =  formaterDay.parse((String)button.getText().substring(20, 31));
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewCancelReservation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    selectRo = Integer.parseInt((String)button.getText().substring(6, 8).trim());
                    System.out.println("testing for selectin "+ formaterDay.format(selectIn)+ "testing for room "+ selectRo);
                }
            });
            viewPanel.add(viewButton[i]);
            i++;
            
        }// end for loop to create clickable room button
        
        
    }
    
    
    
}
