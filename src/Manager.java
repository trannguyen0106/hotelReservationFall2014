
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;

/**
*
* Design Calendar when users log-in Manager page. Password is 0
* 	Create buttons, panels and text fields to show days, months and years
* 	Managers can select any months or years by click on buttons
*  	Managers can view information of room by click "View" button
*  	Managers can save reservation information into file.txt by click "Save" button
*  	Managers can exit system by click "Quit" button
* 
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/
public class Manager 
{
    //current day
    private int currentDay;
	//current year
    private int currentYear;
    //current month
    private int currentMonth;
    // current manager ID
    private int managerID;
    // Temporary Month
    private int tempMonth;
    // Temporary Year
    private int tempYear;
    // use to store compare day
    private int  dayClick;      
    private Date clickDay = new Date();
    //Days Button
    private JButton button[][];
    //Calendar Panel
    private  JPanel calPanel = new JPanel();
    //Manager Frame
    private final JFrame managerFrame = new JFrame("Manager Window");
    //Select months
    private JComboBox monComboBox;
    //Change year by click
    private JSpinner yearChanging; 
	//Reservation list
    private ArrayList<Reservation> listReservation = new ArrayList<Reservation>();
	//GuestAccount list
    private ArrayList<GuestAccount> listGuest = new ArrayList<GuestAccount>();
    private Date currentDate = new Date();
    
	/*
	* Create the month view calendar 
	* @param list is array list  of reservation
	* @param list guest is array list of guest account
	*/
    public void monthViewCalendar(ArrayList<Reservation> list, ArrayList<GuestAccount> listGu ) throws FileNotFoundException
    {
        listReservation = list;
        listGuest = listGu;
        //clickDay.setHours(23);
        //clickDay.setMinutes(59);
        
        managerFrame.setLayout(null);
        // Get current Day, Month, Year 
        GregorianCalendar cal = new GregorianCalendar();
        currentDay = cal.get(GregorianCalendar.DATE);
        currentMonth = cal.get(GregorianCalendar.MONTH);
        currentYear = cal.get(GregorianCalendar.YEAR);
        tempMonth = currentMonth;
        tempYear = currentYear;
        
        // create Jcombobox for changing month 
        // to get info of month using monComboBox.getModel();
        String [] listOfMonth = {"January","February","March","April","May","June","July","August","Septemper",
                                   "October","November","December"};
        monComboBox= new JComboBox(listOfMonth);
        monComboBox.setSelectedIndex(currentMonth);
        monComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox moncombo = (JComboBox) e.getSource();
                tempMonth = moncombo.getSelectedIndex();
                calPanel.removeAll();
                displayCal2(tempMonth, tempYear);
            }
        });
        
        // create spiner for changing year
        // using spiner , and get current value from model anytime using String value = (String) model.getValue();
        final SpinnerModel model =
        new SpinnerNumberModel(2014, //initial value
                                2014- 100, //min
                               2014 + 100, //max
                               1);                //step
        yearChanging = new JSpinner(model);
        yearChanging.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                tempYear = (Integer)model.getValue();
                calPanel.removeAll();
                displayCal2(tempMonth, tempYear);
            }
        });
		
        // create panel for calendar initial
        calPanel.setLayout(new GridLayout(7,7));
        displayCal2(currentMonth, currentYear);
        JButton view = new JButton("VIEW");
        view.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel roomInfo = new JPanel();
                //Top down
                roomInfo.setLayout(new BoxLayout(roomInfo, BoxLayout.Y_AXIS));
                
                JLabel label = new JLabel("                   Room Information");
                JTextArea roomInfo1 = new JTextArea();
                roomInfo1.setLayout(new FlowLayout());
                
                //adding scroll bar for room info text area
                JScrollPane scrolText = new JScrollPane(roomInfo1);
                scrolText.setPreferredSize(new Dimension(500, 600));
                
                //testing for inserting text in to roomfinfo1
                int po = roomInfo1.getCaretPosition();
                roomInfo1.append(clickDay+"\n\n");
                
                //compare click day and current day
                SimpleDateFormat date =  new SimpleDateFormat("MM/dd/yyyy");
                
                String invalid = "* Unable to access the previous info\n"
                        + "  Choose the current date or future date";
                
                //
                if(clickDay.before(currentDate) && !(date.format(currentDate).equals(date.format(clickDay))))
                {
                    roomInfo1.append(invalid);
                }
                else{
                // temperature arraylist to save reserved room number
                    ArrayList<Integer> roomBook = new ArrayList<Integer>();
                    roomInfo1.append("Rooms are reserved: \n");
                    for(Reservation r: listReservation)
                    {
                    	//
                        if((r.getCheckin().before(clickDay) && r.getCheckout().after(clickDay)) 
                                || date.format(r.getCheckin()).equals(date.format(clickDay))
                                || date.format(r.getCheckout()).equals(date.format(clickDay)))
                        {
                            String output = "- Room Number: "+r.getRoomN() +"      Guest Name: " +r.getName()+ 
                                    "      Price: $" +r.getTotalPrice()+"\n";
                            roomBook.add(r.getRoomN());
                            roomInfo1.append(output);
                        }
                    }// end for loop check day
                    
                    roomInfo1.append("\nRooms are available: \n");
                    if(roomBook.size() == 0)
                    {
                        for(int i= 1; i <=20; i++)
                        {
                            String out = "Room " + i+"\n";
                            roomInfo1.append(out);
                        }
                    }
                    else{
                    //ArrayList<Integer> temp = new ArrayList();
                        for(int i= 1; i <=20; i++)
                        {   
                            if(!(roomBook.contains(i)))
                            {
                                String out = "- Room " + i+"\n";
                                 roomInfo1.append(out);
                            }
                        }
                    }
                }// end else if
                
                roomInfo.add(label, BorderLayout.NORTH);
                roomInfo.add(scrolText,BorderLayout.CENTER);
                roomInfo.setBounds(500, 63, 500, 500);
                managerFrame.add(roomInfo);
                managerFrame.setVisible(true);
            }
        });
   
    // save info to reservation.txt and use.txt
        JButton save = new JButton("SAVE");
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PrintWriter outReserv = null;
                try {
                    outReserv = new PrintWriter(new File("reservation.txt"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter outUser = null;
                try {
                    outUser = new PrintWriter(new File("user.txt"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(Reservation r : listReservation)
                {
                    outReserv.println(r.toString());
                }
                outReserv.close();
                for(GuestAccount g: listGuest)
                {
                    outUser.println(g.toString());
                }
                outUser.close();
            }
        });
		
    // save info to reservation.txt + use.txt and then quit 
        JButton quit = new JButton("QUIT");
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PrintWriter outReserv = null;
                try {
                    outReserv = new PrintWriter(new File("reservation.txt"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter outUser = null;
                try {
                    outUser = new PrintWriter(new File("user.txt"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(Reservation r : listReservation)
                {
                    outReserv.println(r.toString());
                }
                outReserv.close();
                for(GuestAccount g: listGuest)
                {
                    outUser.println(g.toString());
                }
                outUser.close();
                System.exit(1);
            }
        });
        
        monComboBox.setBounds(20, 63, 150, 30);
        managerFrame.add(monComboBox);
        
        yearChanging.setBounds(220, 63, 200, 30);
        managerFrame.add(yearChanging);
        
        //hard code for calendar absolute layout
        calPanel.setBounds(20, 95, 400, 400);
        managerFrame.add(calPanel);
        
        //hard code for absolute layout for 3 buttons
        view.setBounds(20,  550, 100, 100);
        save.setBounds(170, 550, 100, 100);
        quit.setBounds(320, 550, 100, 100);
        managerFrame.add(view);
        managerFrame.add(save);
        managerFrame.add(quit);

        managerFrame.setSize(500, 500);
        managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        managerFrame.setVisible(true);
    }
    
    
    public void displayCal2(int mo,int year)
    { 
        JLabel sun = new JLabel("     Su");
        sun.setForeground(Color.red);
        sun.setBackground(Color.lightGray);
        sun.setOpaque(true);
        JLabel mon = new JLabel("     Mo");
        mon.setBackground(Color.LIGHT_GRAY);
        mon.setOpaque(true);
        JLabel tue = new JLabel("     Tu");
        tue.setBackground(Color.LIGHT_GRAY);
        tue.setOpaque(true);
        JLabel wed = new JLabel("     We");
        wed.setBackground(Color.LIGHT_GRAY);
        wed.setOpaque(true);
        JLabel th = new JLabel("     Th");
        th.setBackground(Color.LIGHT_GRAY);
        th.setOpaque(true);
        JLabel fri = new JLabel("     Fr");
        fri.setBackground(Color.LIGHT_GRAY);
        fri.setOpaque(true);
        JLabel sat = new JLabel("     Sa");
        sat.setBackground(Color.LIGHT_GRAY);
        sat.setOpaque(true);        
        calPanel.setLayout(new GridLayout(7, 7));
        calPanel.add(sun);
        calPanel.add(mon);
        calPanel.add(tue);
        calPanel.add(wed);
        calPanel.add(th);
        calPanel.add(fri);
        calPanel.add(sat);      
        button = new JButton[6][7];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++) {
                calPanel.add(button[i][j] = new JButton(""));
            }
	// calendar part 
        GregorianCalendar cal = new GregorianCalendar();
          
        cal.clear();
        cal.set(year,mo,1);
        
        int firstDayOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
        int numDayOfMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        for (int day =0 ;day<firstDayOfMonth-1;day++)
        {
            button[0][day].setEnabled(false);
        }// end for loop
                
        // insert day into their order
        for (int day = 1;day<=numDayOfMonth;day++)
        {
            dayClick = day;
            
           if (day==currentDay
                    && cal.get(GregorianCalendar.MONTH) ==currentMonth&& cal.get(GregorianCalendar.YEAR) == currentYear)
            {
                JButton dayButton = button [(firstDayOfMonth+day-2)/7][(firstDayOfMonth+day-2)%7];
                dayButton.setText(""+day);
                dayButton.setForeground(Color.BLUE);
                dayButton.setBackground(Color.LIGHT_GRAY);
                dayButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton todaybuton = (JButton) e.getSource();
                        int today= Integer.parseInt((String)todaybuton.getText());
                        clickDay.setDate(today);
                        clickDay.setMonth(tempMonth);
                        clickDay.setYear(tempYear-1900);
                    }
                });   
            }
            else
            {
                button [(firstDayOfMonth+day-2)/7][(firstDayOfMonth+day-2)%7].setText(""+day);
                button [(firstDayOfMonth+day-2)/7][(firstDayOfMonth+day-2)%7].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton todaybuton = (JButton) e.getSource();
                        int today= Integer.parseInt((String)todaybuton.getText());
                        clickDay.setDate(today);
                        clickDay.setMonth(tempMonth);
                        clickDay.setYear(tempYear-1900);
                    }
                });               
            }// end else statement            
        }//end for loop for print days of month
    }
    
}
