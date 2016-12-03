import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
* The system displays a Month view of current calendar where days are clickable.
* The manager is allowed to choose one day at a time to see the room availability of this particular day. 
* When the manager selects a different day, the view will change accordingly in real time. 
* The calendar can move back and forth as far as GregorianCalendar goes. It can be advanced by year and by month.
*
* @author  Tran Lam, Khanh Nguyen, Buuchau Phan
* @version 1.0
* @since   12-01-2014
*/
public class CarlendarView 
{
    private int currentDay;
    private int currentYear;
    private int currentMonth;
    private int managerID;
    private int tempMonth;
    private int tempYear;
// use to store compare day
    private int  dayClick;      
    private Date clickDay = new Date();
// create buton for days 
    private JButton button[][];
    private JPanel calPanel = new JPanel();
    private final JFrame managerFrame = new JFrame("Manager Window");
    private JComboBox monComboBox;
    private JSpinner yearChanging;
    
    /**
     * Create Calendar by using class GregorianCalendar to get current day, month and year
     * The method is performed when Manager button is pressed
     */
   public void monthViewCalendar()
    {
        managerFrame.setLayout(null);
         
        GregorianCalendar cal = new GregorianCalendar();
        currentDay = cal.get(GregorianCalendar.DATE);
        currentMonth = cal.get(GregorianCalendar.MONTH);
        currentYear = cal.get(GregorianCalendar.YEAR);
        tempMonth = currentMonth;
        tempYear = currentYear;
        
        // create Jcombobox for changing month 
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
        
        // create spinner for changing year
        // using spinner , and get current value from model anytime using String value = (String) model.getValue();
        final SpinnerModel model =
        new SpinnerNumberModel(2014, //initial value
                               2014- 100, 	//min
                               2014 + 100, //max
                               1);         //step
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
                roomInfo.setLayout(new BoxLayout(roomInfo, BoxLayout.Y_AXIS));
                
                JLabel label = new JLabel("                   Room Information");
                JTextArea roomInfo1 = new JTextArea();
                roomInfo1.setLayout(new FlowLayout());
                
                //adding scroll bar for room info text area
                JScrollPane scrolText = new JScrollPane(roomInfo1);
                scrolText.setPreferredSize(new Dimension(500, 600));
                //testint for inserting text in to roomfinfo1
                int po = roomInfo1.getCaretPosition();
                roomInfo1.append(clickDay.toString()+"\n\n");
                String khanh = "When we have data for room info\n"
                        + "Compare this date to the data\n"
                        + "Then pull out all the room available and booked\n"
                        + "Put them together in one string and append it like this";
                roomInfo1.append(khanh);
                roomInfo.add(label, BorderLayout.NORTH);
                roomInfo.add(scrolText,BorderLayout.CENTER);
                roomInfo.setBounds(500, 63, 500, 500);
                managerFrame.add(roomInfo);
                managerFrame.setVisible(true);
            }
        });
 
        JButton save = new JButton("SAVE");            
        JButton quit = new JButton("QUIT");
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
    /**
     * This method is called to displays the calendar in Gui
     * Design panel, label, and buttons for days in calendar
     * Mark current day in calendar
     * 
     * @param mo is month
     * @param year is year
     */
    public void displayCal2(int mo,int year)
    {	 	
        JLabel sun = new JLabel(" Su");
        sun.setForeground(Color.red);
        sun.setBackground(Color.lightGray);
        sun.setOpaque(true);
        JLabel mon = new JLabel(" Mo");
        mon.setBackground(Color.LIGHT_GRAY);
        mon.setOpaque(true);
        JLabel tue = new JLabel(" Tu");
        tue.setBackground(Color.LIGHT_GRAY);
        tue.setOpaque(true);
        JLabel wed = new JLabel(" We");
        wed.setBackground(Color.LIGHT_GRAY);
        wed.setOpaque(true);
        JLabel th = new JLabel(" Th");
        th.setBackground(Color.LIGHT_GRAY);
        th.setOpaque(true);
        JLabel fri = new JLabel(" Fr");
        fri.setBackground(Color.LIGHT_GRAY);
        fri.setOpaque(true);
        JLabel sat = new JLabel(" Sa");
        sat.setBackground(Color.LIGHT_GRAY);
        sat.setOpaque(true);
        
        //add to grid layout
        calPanel.setLayout(new GridLayout(7, 7));
        calPanel.add(sun);
        calPanel.add(mon);
        calPanel.add(tue);
        calPanel.add(wed);
        calPanel.add(th);
        calPanel.add(fri);
        calPanel.add(sat);
       
        //add day buttons 
        button = new JButton[6][7];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++) {
                calPanel.add(button[i][j] = new JButton(""));
            }
      
        //Create a object calendar
        GregorianCalendar cal = new GregorianCalendar();
          
        //reset value day 
         cal.clear();
        
        // set(year, month,1)  testing manually in/out for year and month-1
        cal.set(year,mo,1);
        
        //get day of week and number day of month
        int firstDayOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
        int numDayOfMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
        //insert the first day of the month on calendar
        for (int day =0 ;day<firstDayOfMonth-1;day++)
        {
            button[0][day].setEnabled(false);
        }// end for loop
                
        
        // insert day into the day order
        for (int day = 1;day<=numDayOfMonth;day++)
        {
            dayClick = day;
            if(day==currentDay
                    && cal.get(GregorianCalendar.MONTH) ==currentMonth&& cal.get(GregorianCalendar.YEAR) == currentYear)
            {	
            //	Mark current day
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
            }  //end else statement            
        }	//end for loop for print days of month
    }    
}
