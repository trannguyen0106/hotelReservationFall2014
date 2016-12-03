import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * Hotel Reservation System - Group TCK
* 	Set information for rooms as room type, room number, and price 
* 	Design buttons, panel, text field to reserve or view reservation room
* 	Create "Create Account" button so that users can create new accounts to log in  
* 	Check ID of users to log-in, and program will show message if users input wrong ID
* 	If users create ID is existing, users have to select other IDs 
* 	Users can go back to make reservation or view through "Back" Button
* 	Create "view and cancel" buttons to users can view or cancel reserved rooms 
* 	Users can make new account through "Make Reservation" button
*	Show available rooms through buttons 
* 	Create "Confirm" and "Transaction Done" buttons so that users can complete reservation
* 	Create buttons and text fields so that users input check-in date , check out date , and  select room type
* 	Check room available 
* 	Design MVC pattern on changing of available room
* 	Give condition for reservation such as if users reserve any rooms above 60 days, transaction will be fail
*  	A user can reserve more than one room with the same account
*  	Create buttons to print "simple"  and "comprehensive" receipts
* 	Create "Manager" buttons to access Manager page 
* 	Display calendar
Member:
 * Tran Lam
 * Khanh Nguyen
 * BuuChau Phan
 */
public class Menu {
    private CardLayout cardlayout;
    private JPanel home = new JPanel();
    private Manager calview;
    private ArrayList<GuestAccount> listGuest = new ArrayList<GuestAccount>();
    private int currentGuestID;
    private String name;
    private GuestAccount gu;
    private boolean loginStatus=false;
    private boolean checkID;
    private Date inD;
    private Date outD;
    private SimpleDateFormat formaterDay;
    private RoomInfo[] listRoom = new RoomInfo[20];
    private ArrayList<Reservation> listReservation = new ArrayList<Reservation>();
    private ArrayList<Integer> roomNum = new ArrayList<Integer>();
    private int managerID = 0;
    private ArrayList<Reservation> currentGuestReserv = new ArrayList<Reservation>();
    private Reservation temp;
    private Reservation tempRe;
    private ArrayList<Reservation> simpleReservationList = new ArrayList<Reservation>();
    private Receipt receiptPrint;
    
//MVC model
    private DataModel model;
    private ViewMVC view;
    private Reservation selectRe;
    private Date selectIn;
    private int selectRo;
    private JPanel viewPanel;
    private JPanel tempPanel=new JPanel();

    /*
     * Set Room type, Room number and price of room
     * @param listRe
     * @param listGu
     * @throws ParseException
     */
    public Menu(ArrayList<Reservation> listRe, ArrayList<GuestAccount> listGu ) throws ParseException
    {
        listGuest = listGu;
        listReservation = listRe;
        
        formaterDay = new SimpleDateFormat("MM/dd/yyyy");
        //input for listRoom
        for (int i=0;i<10;i++)
        {
            listRoom[i] = new RoomInfo("Luxury", i+1, 200);
        }
        for (int i=10;i<20;i++)
        {
            listRoom[i] = new RoomInfo("Economy", i+1, 80);
        }
        
        // main frame which contain every panels
        JFrame mainFrame = new JFrame("Welcome to TCK HOTEL");
        // set card lay out for home panel
        home.setLayout(cardlayout = new CardLayout());
        
	// create home page for hotel system
        JPanel homePage = new JPanel(new FlowLayout());
        JButton guest = new JButton("GUEST");
        guest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                cardlayout.show(home, "guestLogin");
            }
        });
        JButton manager = new JButton("MANAGER");
        manager.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "manag");
            }
        });
        homePage.add(guest);
        homePage.add(manager);
        
	// create first page for guest
        JPanel guestLogin = new JPanel(new FlowLayout());
        JButton createAcct = new JButton("Create Account");
        createAcct.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "signup");
            }
        });
        
        JButton glogin = new JButton("Log in");
        glogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               cardlayout.show(home, "askID");
            }
        });
        guestLogin.add(createAcct);
        guestLogin.add(glogin);
        
	// Ask ID to log in
        final JPanel askID = new JPanel();
        JLabel label  = new JLabel("Enter your ID: ");
        final JPasswordField id = new JPasswordField(30);
        JButton b = new JButton("Log In");
        askID.add(label);
        askID.add(id);
        askID.add(b);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    currentGuestID = Integer.parseInt(id.getText());
                    for (GuestAccount g: listGuest)
                    {
                        if (currentGuestID == g.getId())
                        {
                            gu = g;
                            name = gu.getName();
                            currentGuestReserv = g.getReservation();
                            loginStatus = true;
                        }
                    }
                    if (loginStatus ==true)
                    {
                        cardlayout.show(home, "userIn");
                    }
                    else
                    {
			// fail log in show required information message
                        JOptionPane.showMessageDialog(askID, "Wrong ID. Try Again");
                        cardlayout.show(home,"askID");   
                    }
                }
                catch(NumberFormatException except){ 
                    JOptionPane.showMessageDialog(askID, "Wrong format. ID is integer. Try again");
                    cardlayout.show(home,"askID");
                }
                
            }
        });
         
	// create sign up acct
        final JPanel signup = new JPanel();
        JLabel userName = new JLabel("Enter user name: ");
        final JTextField enterName = new JTextField(30);
        JLabel userID = new JLabel("Choose user ID by enter 5 numbers: ");
        final JTextField enterID = new JTextField(30);
        
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name = enterName.getText();
                try{
                    currentGuestID = Integer.parseInt(enterID.getText());
                    checkID = true;
                    for (GuestAccount g: listGuest)
                    {
                        if (currentGuestID == g.getId())
                            checkID = false;               
                    }
                    // if ID is taken
                    if (checkID == false)
                    {
                        JOptionPane.showMessageDialog(signup, "This ID is taken. Enter another ID (5 integers)");
                        cardlayout.show(home, "sigup");
                    }    
                    else
                    {
                    // the id and the name is qualify
                        gu = new GuestAccount(currentGuestID, name);
                        currentGuestReserv = gu.getReservation();
                        listGuest.add(gu);
                        cardlayout.show(home,"createSus");   
                    }
                }
                catch(NumberFormatException except){ 
                    JOptionPane.showMessageDialog(signup, "Wrong format. ID is an integer. Try again");
                    cardlayout.show(home,"signup");
                }
            }
        });
        signup.add(userName);
        signup.add(enterName);
        signup.add(userID);
        signup.add(enterID);
        signup.add(createButton);
    
	// After create the id
        JPanel createSus = new JPanel();
        JLabel success = new JLabel("Your account is successfully created");
        JButton conti = new JButton("Continue");
        conti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home,"userIn");
            }
        });
        createSus.add(success);
        createSus.add(conti);
        
       // back button of view cancel panel
        final JButton backUser = new JButton("Back");
        backUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "userIn");
            }
        });
        // log out and back to home page
        final JButton hp = new JButton("Home Page");
        hp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleReservationList.clear();
                cardlayout.show(home, "homePage");
            }
        });
        viewPanel = new JPanel();
        viewPanel.setLayout(null);
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollView = new JScrollPane(tempPanel);
		
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(Reservation r: currentGuestReserv)
                {
                    if (r.getCheckin().equals(selectIn)&&r.getRoomN()==selectRo){
                        selectRe = r;
                    }
                }                    
                currentGuestReserv.remove(selectRe);
                gu.getReservation().remove(selectRe);    // for comprehensive receipt
                listReservation.remove(selectRe);
                simpleReservationList.remove(selectRe);
                tempPanel.removeAll();
                createRoomButtonForViewCancel();
                tempPanel.repaint();
                tempPanel.setVisible(true);
                viewPanel.revalidate();
                viewPanel.setVisible(true);    
            }
        });// end cancel button
        
        scrollView.setBounds(10, 10, 400, 350);
        cancelButton.setBounds(420,250 , 100, 50);
        backUser.setBounds(420, 300, 100, 50);
        hp.setBounds(420, 350, 100, 50);
        viewPanel.setSize(600, 700);
        viewPanel.add(scrollView);
        viewPanel.add(cancelButton);
        viewPanel.add(backUser);
        viewPanel.add(hp);
  
	//create making reservation view or cancel
        JPanel userIn = new JPanel();
        JButton reservation = new JButton("Make reservation");
        reservation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "reserv");
            }
        });
		
	// create view canel button for first user in panel        
        JButton viewCancel = new JButton("View/Cancel Reservation");
        viewCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               
				tempPanel.removeAll();                       
				createRoomButtonForViewCancel();
				tempPanel.repaint();
				viewPanel.revalidate();   
				cardlayout.show(home, "viewPanel");
            }
        });
        
        userIn.add(reservation);
        userIn.add(viewCancel);
		
	//show room availabale 
        final JPanel available = new JPanel();
        available.setLayout(new BoxLayout(available, BoxLayout.PAGE_AXIS));
        
        final JButton confirm = new JButton ("Confirmed");
        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {  
              
                if (tempRe !=null)
                {
                    simpleReservationList.add(tempRe);

                    gu.addReservation(tempRe);

                    currentGuestReserv = gu.getReservation();

                    listReservation.add(tempRe);

                    tempRe=null;
                }
                
                cardlayout.show(home, "makemore");
            }
        });
        final JButton done = new JButton ("Transaction Done");
        done.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                cardlayout.show(home, "receipt");
            }
        });
        available.add(confirm);
        available.add(done);
        
	//Make reservation page with check in, check out, room type
        final JPanel reserv = new JPanel();
        JLabel in = new JLabel("Check In: ");
        final JTextField inTxt = new JTextField(20);
        
        JLabel out = new JLabel("Check Out: ");
        final JTextField outTxt = new JTextField(20);
        
        JLabel type = new JLabel("Room Type");
        JButton lux = new JButton("$200");
        final Date current = new Date();
        
   // MVC Pattern
        model = new DataModel(listRoom, listReservation);
        view = new ViewMVC();
        model.addObserver(view);
        
        final SimpleDateFormat date =  new SimpleDateFormat("MM/dd/yyyy");
        lux.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            // edit from MVC model
                roomNum = new ArrayList<Integer>();
                available.removeAll();
   
            // get check in date and check for wrong input
                final String ins = inTxt.getText();
                try {
                    inD = formaterDay.parse(ins);
                } catch (ParseException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(reserv, "Wrong input format. Try again (mm/dd/yyyy)");
                    cardlayout.show(home, "reserv");
                    
                }

            //get check out date and check for wrong input
                final String outs = outTxt.getText();
                try {
                    outD = formaterDay.parse(outs);
                } catch (ParseException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(reserv, "Wrong input format. Try again (mm/dd/yyyy)");
                    cardlayout.show(home, "reserv");
                }
				
            // calculate days of from check in and check out
                long startDateTime = inD.getTime();
                long endDateTime = outD.getTime();
                long milPerDay = 1000*60*60*24; 
                int numOfDays = (int) ((endDateTime - startDateTime) / milPerDay);
                
            // condition for check in date can be in the past
                if(inD.before(current) && !(date.format(current).equals(date.format(inD))))
                {
                    JOptionPane.showMessageDialog(reserv, "Check In Date is past. Change Check In Date");
                    cardlayout.show(home, "reserv"); 
                }
            //condition check out has to be after check in 
                else if(inD.after(outD) && !(date.format(current).equals(date.format(outD))))
                {
                    JOptionPane.showMessageDialog(reserv, "Check out date cannot be before check in date.\nTry Again");
                    cardlayout.show(home, "reserv");
                }
            // condition the stay can be over 60 nights
                else if( numOfDays > 60)
                {
                    JOptionPane.showMessageDialog(reserv, "Your stay cannot be longer than 60 nights.\nTry Again");
                    cardlayout.show(home, "reserv");
                }
                else{
                // look for available room
                    boolean status = false;
                    for(int i = 0;i<10;i++)
                    {
                        boolean status1=true;
                        for (Reservation r : listReservation)
                        {
                            if (listRoom[i].getRoomNumber() != r.getRoomInfo().getRoomNumber()){
                                status = true;
                            }
                            else if (inD.after(r.getCheckout()) || outD.before(r.getCheckin()))
                                status = true;
                            else
                            {
                                status = false;
                                status1=false;
                            }      
                        }

                        if (status==true&& status1==true)
                        {    
                            roomNum.add(listRoom[i].getRoomNumber());                           
                        }
                    }
                    
                    //when the list is empty
                    if(listReservation.isEmpty())
                    {
                        for(int j =1; j <11; j++)
                            roomNum.add(j);
                    }
            // update model
                    model.setAvaiRoom(roomNum);
                    
                    JButton[] button = new JButton[roomNum.size()];
                    for (int i = 0;i<roomNum.size();i++)
                    {
                        String text = "Room"+ roomNum.get(i);
                        button [i] = new JButton(text);
                        button[i].addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {                             
                            
                            JButton b = (JButton) e.getSource();
                            int ru = Integer.parseInt((String) b.getText().substring(4));
                
                            try {
                                tempRe = new Reservation(ins, outs, ru, currentGuestID, name);
                            } catch (ParseException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }                
                        }
                        }); // end adding actionlistener to button[i]
                        available.add(button[i]);
                    }// end for loop to adding button[i] 
                    available.add(confirm);
                    available.add(done);   
                    cardlayout.show(home, "available");
                }
            }        
        });
        
        JButton eco = new JButton("$80");
        eco.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                roomNum = new ArrayList<Integer>();
                available.removeAll();

                // get checkin date
                final String ins = inTxt.getText();
                try {
                    inD = formaterDay.parse(ins);
                } catch (ParseException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                //get checkout date
                final String outs = outTxt.getText();
                try {
                    outD = formaterDay.parse(outs);
                } catch (ParseException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            // calculate days of from check in and check out
                long startDateTime = inD.getTime();
                long endDateTime = outD.getTime();
                long milPerDay = 1000*60*60*24; 
                int numOfDays = (int) ((endDateTime - startDateTime) / milPerDay);
                
                if(inD.before(current) && !(date.format(current).equals(date.format(inD))))
                {
                    JOptionPane.showMessageDialog(reserv, "Check In Date is past. Change Check In Date");
                    cardlayout.show(home, "reserv"); 
                }
                else if(inD.after(outD) || (date.format(current).equals(date.format(outD))))
                {
                    JOptionPane.showMessageDialog(reserv, "Check out date cannot be before or the same check in date.\nTry Again");
                    cardlayout.show(home, "reserv");
                }
                else if(numOfDays >= 60)
                {
                    JOptionPane.showMessageDialog(reserv, "Your stay cannot be longer than 60 nights.\nTry Again");
                    cardlayout.show(home, "reserv");
                }
                else{
                
                    boolean status = false;

                    for(int i = 10;i<20;i++)
                    {

                        boolean status1=true;
                        for (Reservation r : listReservation)
                        {
                            if (listRoom[i].getRoomNumber() != r.getRoomInfo().getRoomNumber()){
                                status = true;
                            }
                            else if (inD.after(r.getCheckout()) || outD.before(r.getCheckin()))
                                status = true;
                            else
                            {
                                status = false;
                                status1=false;
                            }      
                        }

                        if (status==true&& status1==true)
                        {    
                            roomNum.add(listRoom[i].getRoomNumber());
                        }
                    }
                    
                    //when the list is empty
                    if(listReservation.isEmpty())
                    {
                        for(int j =11; j <21; j++)
                            roomNum.add(j);
                    }
                    
                // update model
                    model.setAvaiRoom(roomNum);

                    JButton[] button = new JButton[roomNum.size()];

                    for (int i = 0;i<roomNum.size();i++)
                    {
                        String text = "Room"+ roomNum.get(i);
                        button [i] = new JButton(text);
                        button[i].addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                            JButton b = (JButton) e.getSource();
                            int ru = Integer.parseInt((String) b.getText().substring(4));
                            try {
                                tempRe = new Reservation(ins, outs, ru, currentGuestID,name);
                            } catch (ParseException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }                  
                        }
                        }); // end adding actionlistener to button[i]

                        available.add(button[i]);
                    }// end for loop to adding button[i]   
                available.add(confirm);
                available.add(done);
                cardlayout.show(home, "available");
                }
            }
        });
        reserv.add(in);
        reserv.add(inTxt);
        reserv.add(out);
        reserv.add(outTxt);
        reserv.add(type);
        reserv.add(lux);
        reserv.add(eco);
        
	// Make more page
        JPanel makemore = new JPanel();
        JLabel make = new JLabel("Make More Reservation?");
        JButton yes = new JButton("Yes");
        yes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "reserv");
            }
        });
        JButton no = new JButton("No");
        no.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "receipt");
            }
        });
        makemore.add(make);
        makemore.add(yes);
        makemore.add(no);
        
	//Simple receipt
        final JPanel simp = new JPanel();
        final JTextArea txt = new JTextArea(10,10);
        final JButton back = new JButton("Back");
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "userIn");
            }
        });
        
        final JButton logout = new JButton("Home Page");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleReservationList.clear();
                cardlayout.show(home, "homePage");
            }
        });
        simp.add(txt);
        simp.add(back);
        simp.add(logout);
        
	//Comprehensive receipt
        final JPanel com = new JPanel();
        final JTextArea t = new JTextArea(10,10);
        JButton bac = new JButton("Back");
        bac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout.show(home, "userIn");
            }
        });
        final JButton logO = new JButton("Home page");
        logO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleReservationList.clear();
                cardlayout.show(home, "homePage");
            }
        });
        com.add(t);
        com.add(bac);
        com.add(logO);
        
	//Receipt Page
        JPanel receipt = new JPanel();
        JButton simple = new JButton("Simple");
        final JLabel s = new JLabel("Simple Receipt");
        simple.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                simp.removeAll();
                txt.removeAll();
                receiptPrint = new Receipt(gu, simpleReservationList);
                txt.setText(receiptPrint.simpleReceipt());
                simp.add(s);
                simp.add(txt);
                simp.add(back);
                simp.add(logO);
                cardlayout.show(home, "simp");
            }
        });
        final JLabel c = new JLabel("Comprehensive Receipt");
        JButton compre = new JButton("Comprehensive");
        compre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                com.removeAll();
                t.removeAll();
                receiptPrint = new Receipt(gu, simpleReservationList);
                t.setText(receiptPrint.comprehensive());
                com.add(c);
                com.add(t);
                com.add(back);
                com.add(logO);
                cardlayout.show(home, "com");
            }
        });
        receipt.add(simple);
        receipt.add(compre);
         
	// Manager page
        final JPanel manag = new JPanel();
        JLabel la = new JLabel("Enter Manager ID: ");
        final JPasswordField pass = new JPasswordField(20);
        JButton bt = new JButton("Manager Log In");
        manag.add(la);
        manag.add(pass);
        manag.add(bt);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(managerID == Integer.parseInt(pass.getText()))
                {
					// call to display calendar
                    calview = new Manager();
                    try {
                        calview.monthViewCalendar(listReservation, listGuest);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {   
                    JOptionPane.showMessageDialog(manag, "Wrong ID. Try Again");
                    cardlayout.show(home, "manag");
                }
            }
        });
          
	// adding cardlayout to home panel
        home.add("homePage", homePage);
        home.add("guestLogin", guestLogin);
        home.add("signup", signup);
        home.add("userIn", userIn);
        home.add("viewPanel", viewPanel);
        home.add("createSus", createSus);
        home.add("askID", askID);
        home.add("reserv", reserv);
        home.add("available", available);
        home.add("makemore",makemore);
        home.add("receipt", receipt);
        home.add("simp", simp);
        home.add("com", com);
        home.add("manag", manag);
        cardlayout.show(home, "homepage");
            
	// main frame setting
        mainFrame.add(home);
        mainFrame.setSize(650, 500);
        mainFrame.setLocation(600, 40);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
    }// end MENU constructor
    
    /*
	* To create buttons for the reservation list in View/ Cancel panel
	*/
    
    public void createRoomButtonForViewCancel()
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
                    JButton button = (JButton)e.getSource();
                    try {
                        selectIn =  formaterDay.parse((String)button.getText().substring(20, 31));
                    } catch (ParseException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    selectRo = Integer.parseInt((String)button.getText().substring(6, 8).trim());
                }
            });
            tempPanel.add(viewButton[i]);
            i++;
            
        }// end for loop to create click-able room button   
    }
}