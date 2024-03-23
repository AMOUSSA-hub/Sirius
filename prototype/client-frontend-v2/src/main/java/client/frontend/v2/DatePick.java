package client.frontend.v2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DatePick {
  int DATE_MONTH = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
  int DATE_YEAR = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
  ;
  JLabel J_Label = new JLabel("", JLabel.CENTER);
  String DATE_DAY = "";
  JDialog J_Dialog;
  JButton[] J_Button = new JButton[49];

  public DatePick(Container J_Frame_Parent) {
    J_Dialog = new JDialog();
    J_Dialog.setModal(true);
    String[] Header = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    JPanel J_Panel1 = new JPanel(new GridLayout(7, 7));
    J_Panel1.setPreferredSize(new Dimension(700, 120));

    for (int i = 0; i < J_Button.length; i++) {
      final int selection = i;
      J_Button[i] = new JButton();
      J_Button[i].setFocusPainted(false);
      J_Button[i].setBackground(Color.white);

       //ajouter les listener sur les "boutons-jour"
      if (i > 6){
        J_Button[i].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            DATE_DAY = J_Button[selection].getActionCommand();
            if(!DATE_DAY.equals("")){
            J_Dialog.dispose();
            }
          }
        });
      }
        //remplir le header
      if (i < 7) {
        J_Button[i].setText(Header[i]);
        J_Button[i].setForeground(Color.red);
      }
      J_Panel1.add(J_Button[i]);
    }
    JPanel J_Panel2 = new JPanel(new GridLayout(1, 3));
    JButton Previous_Button = new JButton("<< Previous");
    Previous_Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        DATE_MONTH--;
        Display_Date();
      }
    });
    J_Panel2.add(Previous_Button);
    J_Panel2.add(J_Label);
    JButton Next_Button = new JButton("Next >>");
    Next_Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        DATE_MONTH++;
        Display_Date();
      }
    });
    J_Panel2.add(Next_Button);
    J_Dialog.add(J_Panel1, BorderLayout.CENTER);
    J_Dialog.add(J_Panel2, BorderLayout.SOUTH);
    J_Dialog.pack();
    J_Dialog.setLocationRelativeTo(J_Frame_Parent);
    Display_Date();
    J_Dialog.setVisible(true);
  }

  public void Display_Date() {
    for (int i = 7; i < J_Button.length; i++) J_Button[i].setText("");
    java.text.SimpleDateFormat Simple_Date_Format = new java.text.SimpleDateFormat("MMMM yyyy");
    java.util.Calendar Calendar = java.util.Calendar.getInstance();
    Calendar.set(DATE_YEAR, DATE_MONTH, 1);
    int Day_Of_Week = Calendar.get(java.util.Calendar.DAY_OF_WEEK);
    int Days_In_Month = Calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    for (int i = 5 + Day_Of_Week, Day = 1; Day <= Days_In_Month; i++, Day++)
      J_Button[i].setText("" + Day);
    J_Label.setText(Simple_Date_Format.format(Calendar.getTime()));
    J_Dialog.setTitle("Date Picker");
  }

  public String Set_Picked_Date() {
    if (DATE_DAY.equals("")){
      return DATE_DAY;
    }
    java.text.SimpleDateFormat Simple_Date_Format = new java.text.SimpleDateFormat("dd-MM-yyyy");
    java.util.Calendar Calendar = java.util.Calendar.getInstance();
    Calendar.set(DATE_YEAR, DATE_MONTH, Integer.parseInt(DATE_DAY));
    //System.out.println(Simple_Date_Format.format(Calendar.getTime()));
    return Simple_Date_Format.format(Calendar.getTime());
  }
}

// public class Date_Picker {
//   public static void main(String[] args) {
//     JLabel J_Label = new JLabel("Date Selected:");
//     final JTextField J_Text_Field = new JTextField(20);
//     JButton J_Button = new JButton("Choose the Date");
//     JPanel J_Panel = new JPanel();
//     J_Panel.add(J_Label);
//     J_Panel.add(J_Text_Field);
//     J_Panel.add(J_Button);
//     final JFrame J_Frame = new JFrame();
//     J_Frame.getContentPane().add(J_Panel);
//     J_Frame.pack();
//     J_Frame.setVisible(true);
//     J_Button.addActionListener(new ActionListener() {
//       public void actionPerformed(ActionEvent ae) {
//         J_Text_Field.setText(new DatePick(J_Frame).Set_Picked_Date());
//       }
//     });
//   }
// }