import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;
// @Author : Nishant Rana 2015152
/*
    This class handles all the GUI operation of the DBLP Query Engine
 */
public class dblp_gui{
    private JFrame frame1;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JLabel no = new JLabel();
    JButton next = new JButton("Next");
    JButton prev = new JButton("Previous");
    private JComboBox<String> box1;
    /*
     ====================================================================CREATES THE MAIN FRAME==========================================================
             */
    public void createFrame()
    {
        frame1=new JFrame();
        frame1.setSize(1024, 768);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setBackground(Color.black);
        start();
    }
    public JPanel createPanel(int width,int height) {
        JPanel panel=new JPanel();
        panel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension arcs = new Dimension(15,15);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                graphics.setColor(getBackground());
                graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
                graphics.setColor(getForeground());
                graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
    /*
      ======-==================================================ADDS PANELS: PANEL 1 AND 2 TO FRAME AND COMBOBOX===============================================
     */
    public void start()
    {
        panel1 = createPanel(1000, 100);
        panel2 = createPanel( 500, 500);
        panel3 = createPanel(500, 800);
        panel4 = createPanel(500, 500);
        panel5=createPanel(500,500);
        //=======================================================PANEL 1====================================================================

        JLabel text_panel1=new JLabel();
        text_panel1.setText("<html><h1>DBLP QUERY ENGINE</h1></html>");                     // edit the font using HTML tags
        panel1.add(text_panel1);
        frame1.add(panel1, BorderLayout.NORTH);

        //======================================================PANEL 2====================================================================

        box1=new JComboBox<String>();
        box1.setPreferredSize(new Dimension(100, 20));
        box1.addItem("Queries");
        box1.addItem("Query 1");
        box1.addItem("Query 2");
        box1.addItem("Query 3");
        box1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=(String)box1.getSelectedItem();
                switch(s)
                {
                    case "Query 1":
                        panel_query1();
                        panel4.setVisible(true);
                        panel5.removeAll();
                        panel3.removeAll();
                        panel5.setVisible(false);
                        break;
                    case "Query 2":
                        panel_query2();
                        panel4.setVisible(false);
                        panel5.setVisible(true);
                        panel3.removeAll();
                        panel4.removeAll();
                        break;
                    case "Query 3":
                        //panel_query3();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        });
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel2.add(box1, BorderLayout.CENTER);
        frame1.add(panel2, BorderLayout.WEST);
        frame1.setVisible(true);
    }

    public void createTable_Query1(List<Record> result){
        JTable table;
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String columnnames[]=new String[]{"<html><h3>S.NO</h3></html>", "<html><h3>AUTHOR</h3></html>", "<html><h3>TITLE</h3></html>", "<html><h3>PAGES</h3></html>", "<html><h3>YEAR</h3></html>", "<html><h3>VOLUME</h3></html>", "<html><h3>JOURNAL/BOOKTITLE</h3></html>", "<html><h3>URL</h3></html>"};
        model.setColumnIdentifiers(columnnames);
        table = new JTable();
        table.setModel(model);
        int count=0;
        for(Record r:result)
        {
            count++;
            if(r!=null)
            {
                model.addRow(new Object[]{count,r.getAuthor(),r.getTitle(),r.getPages(),r.getYear(),r.getVolume(),r.getTitle(),r.getUrl()});
            }
            table.setRowHeight(count-1,20);
        }
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.getColumnModel().getColumn(6).setPreferredWidth(200);
        table.getColumnModel().getColumn(7).setPreferredWidth(200);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(new Dimension(1000,700));
        panel3.add(scroll);
        panel3.add(next);
        panel3.add(prev);
        panel3.add(no);
        frame1.add(panel3);
        panel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame1.pack();

    }

    public void createTable_Query2(List<String> result)
    {
        JTable table;
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String columnames[]=new String[]{
                "<html><h3>S.NO.</h3></html>","<html><h3>AUTHORS</h3></html>"
        };
        model.setColumnIdentifiers(columnames);
        table = new JTable();
        table.setModel(model);
        int count=0;
        for(String r:result)
        {
            count++;
            if(r!=null)
            {
                model.addRow(new Object[]{count, r});
            }
        }
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(new Dimension(1000,700));
        panel3.removeAll();
        panel3.add(scroll);
        frame1.add(panel3);
        frame1.pack();
    }
    //==========================================================================================================================================================
    //================================================================METHODS CALLED UPON QUERY CHOICE==========================================================
    //==========================================================================================================================================================

    /*
        ============================================================WHEN QUERY 1 IS SELECTED IN COMBOBOX 1====================================================
     */
    public void panel_query1()
    {
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        /*
           ----------------------------------COMBOBOX AND BUTTONS--------------------------------------
         */
        JComboBox box3 = new JComboBox();
        box3.addItem("Search By");
        box3.addItem("Author Name");
        box3.addItem("Title");
        final String[] searchBy = new String[1];
        box3.setMinimumSize(new Dimension(100,20));
        box3.setMaximumSize(new Dimension(100,20));
        box3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=(String)box3.getSelectedItem();
                switch(s)
                {
                    case "Author Name":
                        searchBy[0] = "author";
                        break;
                    case "Title":
                        searchBy[0] = "title";
                        break;
                    default:
                        break;
                }
        }
        });
        JButton Search = new JButton("Search");
        JButton Reset = new JButton("Reset");
        JRadioButton relevance=new JRadioButton("Sort by Relevance");
        final JRadioButton[] year = {new JRadioButton("Sort by Year")};
        ButtonGroup select=new ButtonGroup();
        select.add(relevance);
        select.add(year[0]);

        /*
         *----------------------------------------------TEXT AREA AND LABELS----------------------------
         */

        JTextArea name_title=new JTextArea("");
        name_title.setColumns(10);
        name_title.setRows(1);
        name_title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextArea since_yr = new JTextArea("YYYY");
        since_yr.setColumns(3);
        since_yr.setRows(1);
        since_yr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextArea bet_yr1 = new JTextArea("YYYY");
        bet_yr1.setColumns(3);
        bet_yr1.setRows(1);
        bet_yr1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextArea bet_yr2 = new JTextArea("YYYY");
        bet_yr2.setColumns(3);
        bet_yr2.setRows(1);
        bet_yr2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel nametitle = new JLabel("<html><h3>Name/Title</h3></html>");
        JLabel bet_yr = new JLabel("<html><h3>Between Year</h3></html>");
        JLabel hyphon = new JLabel("<html><h3>-</h3><html>");
        JLabel s_yr = new JLabel("<html><h3>Since Year</h3></html>");

        /*
         *----------------------------------------------PANELS INSIDE PANEL4----------------------------
         */
        JPanel panel_bet_yr = new JPanel();
        JPanel panel_name = new JPanel();
        JPanel panel_buttons = new JPanel();
        JPanel panel_since_year = new JPanel();

        final boolean[] rele = {false};
        final boolean[] yr = {false};
        ActionListener radiobutton=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(relevance.isSelected())
                {
                    rele[0] =true;
                }
                if(year[0].isSelected())
                {
                    yr[0] =true;
                }
            }
        };
        relevance.addActionListener(radiobutton);
        year[0].addActionListener(radiobutton);
        panel_buttons.add(Search);
        panel_buttons.add(Reset);
        Search.addActionListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
               if(name_title.getText().equalsIgnoreCase(""))
               {
                   JFrame frame_error=new JFrame();
                   showMessageDialog(frame_error,"It is mandatory to fill Name/Title Field for Searching","Dialog",JOptionPane.ERROR_MESSAGE);
                   frame_error.setVisible(true);
               }
               else{
               String search=searchBy[0];
                   boolean val= rele[0];
                   boolean val2= yr[0];

                   start_query1(search, name_title.getText(), since_yr.getText(), bet_yr1.getText(), bet_yr2.getText(),val,val2);
            }}
        });
        Reset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                name_title.setText("");
                since_yr.setText("YYYY");
                bet_yr1.setText("YYYY");
                bet_yr2.setText("YYYY");
            }
        });



        panel_name.add(nametitle);
        panel_name.add(name_title);

        panel_since_year.add(s_yr);
        panel_since_year.add(since_yr);

        panel_bet_yr.add(bet_yr);
        panel_bet_yr.add(bet_yr1);
        panel_bet_yr.add(hyphon);
        panel_bet_yr.add(bet_yr2);
        panel4.add(Box.createVerticalStrut(20));
        panel4.add(box3);
        panel4.add(Box.createVerticalStrut(20));
        panel4.add(panel_name);
        panel4.add(panel_since_year);
        panel4.add(relevance);
        panel4.add(year[0]);
        panel4.add(panel_bet_yr);
        panel4.add(panel_buttons);
        frame1.add(panel3);
        panel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel2.add(panel4, BorderLayout.CENTER);
        frame1.pack();
    }
    /*
     *================================================================WHEN QUERY2 IS SELECTED IN COMBOBOX 1==============================================
     */
    public void panel_query2()
    {
        JLabel publicationNo = new JLabel("<html><h3>No of Publication</h3></html>");
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        JPanel text=new JPanel();
        JTextArea publication=new JTextArea("");
        publication.setRows(1);
        publication.setColumns(20);
        publication.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JButton Search = new JButton("<html><h3>Search</h3></html>");
        JButton Reset = new JButton("<html><h3>Reset</h3></html>");
        text.add(publicationNo);
        text.add(publication);
        JPanel panel_buttons = new JPanel();
        panel_buttons.add(Search);
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(publication.getText().equalsIgnoreCase(""))
                {
                    JFrame frame_error=new JFrame();
                    showMessageDialog(frame_error,"It is mandatory to fill No of Publication Field for Searching","Dialog",JOptionPane.ERROR_MESSAGE);
                    frame_error.setVisible(true);
                }
                else{
                    start_query2(Integer.parseInt(publication.getText()));
                }
            }
        });
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel_buttons.add(Reset);
        panel5.add(Box.createVerticalStrut(20));
        panel5.add(text);
        panel5.add(panel_buttons);
        panel2.add(panel5, BorderLayout.CENTER);
        frame1.pack();
    }

    /*
        ================================================================WHEN QUERY3 IS SELECTED IN COMBOBOX 1=================================================
     */
    /*

     */
    public void start_query1(String searchBy,String name,String since,String bet1,String bet2,boolean relevance,boolean year) {
        panel3.removeAll();
        Query q = new Query();
        int count = 0;
        final int[] next_count = {0};
        if (relevance == false && year == false) {
            if (since.equalsIgnoreCase("YYYY") && bet1.equalsIgnoreCase("YYYY")) {
                q.query1(name, searchBy, "none", "none", "none", "none");
            } else if (bet1.equalsIgnoreCase("YYYY")) {
                q.query1(name, searchBy, "none", "since", since, "none");
            } else {
                q.query1(name, searchBy, "none", "between", bet1, bet2);
            }
        } else if (relevance == true && year == false) {
            if (since.equalsIgnoreCase("YYYY") && bet1.equalsIgnoreCase("YYYY")) {
                q.query1(name, searchBy, "relevance", "none", "none", "none");
            } else if (bet1.equalsIgnoreCase("YYYY")) {
                q.query1(name, searchBy, "relevance", "since", since, "none");
            } else {
                q.query1(name, searchBy, "relevance", "between", bet1, bet2);
            }
        } else if (relevance == false && year == true) {
            if (since.equalsIgnoreCase("YYYY") && bet1.equalsIgnoreCase("YYYY")) {
                q.query1(name, searchBy, "year", "none", "none", "none");
            } else if (bet1.equalsIgnoreCase("YYYY")) {
                q.query1(name, searchBy, "year", "since", since, "none");
            } else {
                q.query1(name, searchBy, "year", "between", bet1, bet2);
            }
        }
        boolean flag = true;
        List<Record> result = q.getResults();
        if (!relevance && !year) {
            Collections.reverse(result);
        }
        result(result, count, next_count[0]);
        panel3.add(next);
        panel3.add(prev);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next_count[0] = next_count[0] + 20;
                panel3.removeAll();
                result(result, count, next_count[0]);
            }
        });


        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(next_count[0]!=0) {
                    next_count[0] = next_count[0] - 20;
                    panel3.removeAll();
    result(result, count, next_count[0]);
                }
                else
                {
                    next_count[0] = -1;
                }
            }
        });
    }

    public void result(List<Record> result,int count,int next_count)
    {

        if(next_count==-1)
        {
            JFrame frame_notfound = new JFrame();
            JOptionPane.showMessageDialog(frame_notfound, "No More Record", "Search", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
        List<Record> result20 = new ArrayList<Record>();
        {
            count=next_count;
            while (count <count+20 && count<result.size()) {

                Record r = result.get(count);
                if (r == null) {
                    break;
                }
                count++;
                result20.add(r);
            }
            if (result20.get(0) == null) {
                JFrame frame_notfound = new JFrame();
                JOptionPane.showMessageDialog(frame_notfound, "No Record Found", "Search", JOptionPane.INFORMATION_MESSAGE);
            } else {
                createTable_Query1(result20);
            }
        }}
        no.setText("TOTAL RECORDS FOUND: " + result.size());

    }
    public void start_query2(int publication)
    {
        Query q = new Query();
        q.query2(publication);
        List<String> result20 = new ArrayList<>();
        for (Record r : q.getResults()) {
            result20.add(r.getAuthor().get(0));
        }
        createTable_Query2(result20);
    }
}


