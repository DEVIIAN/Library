package view;

import Dao.AES;
import Dao.AdminDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuAdmin extends JFrame {
    private AdminDao Dao=new AdminDao();
    private JMenuBar menuBar=new JMenuBar();
    private CardLayout card=new CardLayout();
    private JPanel p=new JPanel();
    private JPanel p_ReaderInformation =null, p_ReaderResetPWD =null, p_BookInformation = null, p_Selct = null;
    private JPanel panel=new JPanel(card);
    /**
     *�˵��ؼ�
     */
    private JMenu menuInformation=new JMenu("�޸���Ϣ");
    private JMenuItem itemReader =new JMenuItem("������Ϣ����");
    private JMenuItem itemBook =new JMenuItem("�鼮��Ϣ����");
    private JMenu menuResetPwd=new JMenu("��������");
    private JMenuItem itemResetReaderPWD =new JMenuItem("���ö�������");
    private JMenu menuSelect = new JMenu("��ѯ");
    private JMenuItem itemSelectInformation = new JMenuItem("��ѯ��Ϣ");
    private JMenu menuRefresh = new JMenu("ˢ��");
    private JMenuItem itemRefresh = new JMenuItem("ˢ����Ϣ");


    //���
    private JTable tableReaderInformation =null;                        //������Ϣ��
    private JTable tableReaderLoadBookInformation = null;               //���߽����
    private JTable tableBookInformation = null;                         //�鼮��Ϣ��
    private JTable tableBookID = null;                                  //��ű�
    private JTable tableResetReaderPWD =null;                           //�������������
    private JTable tableSelect = null;                                  //��ѯ������߱�
    /**
     * p_ReaderInformation�ؼ���������Ϣ
     */
    private JButton btnResetReaderPWD =new JButton("��������");
    private JButton btnRefreshReset = new JButton("ˢ��");
    private JButton btnAddReader =new JButton("���Ӷ���");
    private JButton btnDeleteReader =new JButton("ɾ������");
    private JButton btnExit1 =new JButton("�˳�ϵͳ");
    private JLabel lblIdReader =new JLabel("����֤��");
    private JLabel lblPwdReader =new JLabel("����");
    private JLabel lblName=new JLabel("����");
    private JLabel lblSex=new JLabel("�Ա�");
    private JLabel lblBirth =new JLabel("��������");
    private JLabel lblID =new JLabel("���֤��");
    private JLabel lblReadClass =new JLabel("����ȼ�");
    private JLabel lblDepartment=new JLabel("��������");
    private JLabel lblAdressHome=new JLabel("��ͥסַ");
    private JLabel lblPhone=new JLabel("��ϵ��ʽ");

    private JTextField txtIdReader =new JTextField(10);
    private JTextField txtPwdReader =new JTextField(18);
    private JTextField txtName=new JTextField(10);
    private JComboBox cbSex = new JComboBox();
    private JTextField txtBirth =new JTextField(10);
    private JTextField txtID =new JTextField(18);
    private JComboBox cbReadClass = new JComboBox();
    private JTextField txtDepartment =new JTextField(20);
    private JTextField txtAddressHome =new JTextField(20);
    private JTextField txtPhone =new JTextField(11);



    /**
     * p_BookInformation�ؼ����鼮��Ϣ
     */
    private JLabel lblISBNId =new JLabel("�鼮ISBN��");
    private JLabel lblBookCategories =new JLabel("�鼮���");
    private JLabel lblBookName=new JLabel("����");
    private JLabel lblAuthor=new JLabel("����");
    private JLabel lblPublishingHouse =new JLabel("������");
    private JLabel lblPublictionDate =new JLabel("��������");
    private JLabel lblNumberLibrary =new JLabel("ͼ��ݲ�������");
    private JLabel lblPrice=new JLabel("�鼮�۸�");
    private JLabel lblBookIntroduce=new JLabel("�鼮����");

    private JTextField txtISBNID =new JTextField(8);
    private JTextField txtBookCategories =new JTextField(50);
    private JTextField txtBookName =new JTextField(50);
    private JTextField txtAuthor =new JTextField(50);
    private JTextField txtPublishingHouse =new JTextField(50);
    private JTextField txtPublictionDate =new JTextField(10);
    private JTextField txtNumberLibrary =new JTextField(5);
    private JTextField txtPrice =new JTextField(10);
    private JTextField txtBookIntroduce =new JTextField(100);

    private JButton btnAddBook =new JButton("�����鼮");
    private JButton btnDeleteBook =new JButton("ɾ���鼮");
    private JButton btnExit2 =new JButton("�˳�ϵͳ");

    /**
     *  ��ѯ����
     */
    private JComboBox cbSelect = new JComboBox();
    private JButton btnSelect = new JButton("��ѯ");
    private JLabel lbltemp1 = new JLabel("��Ŀһ");
    private JLabel lbltemp2 = new JLabel("��Ŀ��");
    private JTextField txtSelect1 = new JTextField(50);
    private JTextField txtSelect2 = new JTextField(50);
    private JTextField txtHint1 = new JTextField(50);
    private JTextField txtHint2 = new JTextField(50);
    private JTextField txtHint3 = new JTextField(50);
    private JTextField txtHint4 = new JTextField(50);
    private JTextField txtHint5 = new JTextField(50);
    private JTextField txtHint6 = new JTextField(50);

    public MenuAdmin(){
        /**
         *  label������ʾ
         */
        lblIdReader.setHorizontalAlignment(SwingConstants.CENTER);
        lblPwdReader.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblSex.setHorizontalAlignment(SwingConstants.CENTER);
        lblBirth.setHorizontalAlignment(SwingConstants.CENTER);
        lblID.setHorizontalAlignment(SwingConstants.CENTER);
        lblReadClass.setHorizontalAlignment(SwingConstants.CENTER);
        lblDepartment.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdressHome.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
        lblISBNId.setHorizontalAlignment(SwingConstants.CENTER);
        lblBookCategories.setHorizontalAlignment(SwingConstants.CENTER);
        lblBookName.setHorizontalAlignment(SwingConstants.CENTER);
        lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
        lblPublishingHouse.setHorizontalAlignment(SwingConstants.CENTER);
        lblPublictionDate.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumberLibrary.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
        lblBookIntroduce.setHorizontalAlignment(SwingConstants.CENTER);
        lbltemp1.setHorizontalAlignment(SwingConstants.CENTER);
        lbltemp2.setHorizontalAlignment(SwingConstants.CENTER);
        /**
         *�˵���ʾ
         */
        menuInformation.add(itemReader);menuInformation.addSeparator();
        menuInformation.add(itemBook);
        menuResetPwd.add(itemResetReaderPWD);menuResetPwd.addSeparator();
        menuSelect.add(itemSelectInformation);
        menuRefresh.add(itemRefresh);
        menuBar.add(menuInformation);menuBar.add(menuResetPwd);menuBar.add(menuSelect);menuBar.add(menuRefresh);


        this.validate();			//ȷ�����������Ч�Ĳ��֡�
        p_ReaderInformation =new JPanel();
        p_ReaderResetPWD =new JPanel();
        p_BookInformation = new JPanel();
        p_Selct = new JPanel();

        //��ʼ�����
        initTableReaderInformation();initTableLoadBookInformation();
        initTableBookISBNInformation();initTableBookInformation();initTableSelectReader();
        
        //����Ϊ��Ƭ����
        
        /**
         *������Ϣ����
         */
        //�߽粼�֣������ϱ���
        p_ReaderInformation.setLayout(new BorderLayout());
        JPanel jp_ReaderAdd=new JPanel();
        JPanel jp_Table = new JPanel();
        JPanel jp_ReaderSouth=new JPanel();
        JScrollPane jspReaderInformation=new JScrollPane(tableReaderInformation);
        JScrollPane jspLoadBookInformation = new JScrollPane(tableReaderLoadBookInformation);
        
        cbReadClass.addItem("������");cbReadClass.addItem("�о���");cbReadClass.addItem("��ʿ");cbReadClass.addItem("��ʦ");
        cbSex.addItem("��");cbSex.addItem("Ů");
        txtPwdReader.setEditable(false);
        jp_Table.setLayout(new GridLayout(2,1));
        jp_Table.add(jspReaderInformation);jp_Table.add(jspLoadBookInformation);
        jp_ReaderAdd.setLayout(new GridLayout(5,4));jp_ReaderSouth.setLayout(new GridLayout(1,3));
        jp_ReaderAdd.add(lblIdReader);jp_ReaderAdd.add(txtIdReader);jp_ReaderAdd.add(lblName);jp_ReaderAdd.add(txtName);
        jp_ReaderAdd.add(lblPwdReader);jp_ReaderAdd.add(txtPwdReader);jp_ReaderAdd.add(lblSex);jp_ReaderAdd.add(cbSex);
        jp_ReaderAdd.add(lblBirth);jp_ReaderAdd.add(txtBirth);jp_ReaderAdd.add(lblID);jp_ReaderAdd.add(txtID);
        jp_ReaderAdd.add(lblReadClass);jp_ReaderAdd.add(cbReadClass);jp_ReaderAdd.add(lblPhone);jp_ReaderAdd.add(txtPhone);
        jp_ReaderAdd.add(lblDepartment);jp_ReaderAdd.add(txtDepartment);jp_ReaderAdd.add(lblAdressHome);jp_ReaderAdd.add(txtAddressHome);
        jp_ReaderSouth.add(btnAddReader);jp_ReaderSouth.add(btnDeleteReader);jp_ReaderSouth.add(btnExit1);

        p_ReaderInformation.add(jp_ReaderAdd, BorderLayout.NORTH);
        p_ReaderInformation.add(jp_Table,BorderLayout.CENTER);
        p_ReaderInformation.add(jp_ReaderSouth,BorderLayout.SOUTH);
        panel.add(p_ReaderInformation,"ReaderInformation");
        itemReader.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "ReaderInformation");
            }
        });

        /******************����һ����*********************************/
        btnAddReader.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnAddReader_Clicked();
            }
        });

        /*******************ɾ��һ����*****************************/
        btnDeleteReader.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnDeleteReader_Clicked();
            }
        });

        /**********************�˳�ϵͳ***************************/
        btnExit1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /**
         * ���ö�������
         */
        JScrollPane jspResetPWDReader=new JScrollPane(tableResetReaderPWD);
        p_ReaderResetPWD.setLayout(new BorderLayout());
        p_ReaderResetPWD.add(btnResetReaderPWD, BorderLayout.SOUTH);
        p_ReaderResetPWD.add(jspResetPWDReader,BorderLayout.CENTER);
        panel.add(p_ReaderResetPWD,"ReaderResetPWD");
        itemResetReaderPWD.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "ReaderResetPWD");
            }
        });
        itemRefresh.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                itemRefresh_Clicked();
            }
        });
        /******************��������********************/
        btnResetReaderPWD.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnResetReaderPWD_Clicked();
            }
        });

        /******************�˳�ϵͳ*********************/
        btnExit2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /**
         *  �鼮��Ϣ
         */
        //�߽粼�֣������ϱ���
        p_BookInformation.setLayout(new BorderLayout());
        JPanel jp_AddBook = new JPanel();JPanel jp_AddBookNorth = new JPanel(); JPanel jp_AddBookSouth = new JPanel();
        JPanel jp_Table_Book = new JPanel();
        JPanel jp_Btn = new JPanel();
        JScrollPane jspISBNBookInformation=new JScrollPane(tableBookInformation);
        JScrollPane jspBookInformation = new JScrollPane(tableBookID);

        jp_AddBook.setLayout(new BorderLayout());jp_Table_Book.setLayout(new GridLayout(2,1));
        jp_AddBookNorth.setLayout(new GridLayout(4,4));jp_AddBookSouth.setLayout(new GridLayout(1,2));
        jp_AddBookNorth.add(lblISBNId);jp_AddBookNorth.add(txtISBNID);jp_AddBookNorth.add(lblBookCategories);jp_AddBookNorth.add(txtBookCategories);
        jp_AddBookNorth.add(lblBookName);jp_AddBookNorth.add(txtBookName);jp_AddBookNorth.add(lblAuthor);jp_AddBookNorth.add(txtAuthor);
        jp_AddBookNorth.add(lblPublishingHouse);jp_AddBookNorth.add(txtPublishingHouse);jp_AddBookNorth.add(lblPublictionDate);jp_AddBookNorth.add(txtPublictionDate);
        jp_AddBookNorth.add(lblNumberLibrary);jp_AddBookNorth.add(txtNumberLibrary);jp_AddBookNorth.add(lblPrice);jp_AddBookNorth.add(txtPrice);
        jp_AddBookSouth.add(lblBookIntroduce);jp_AddBookSouth.add(txtBookIntroduce);
        jp_AddBook.add(jp_AddBookNorth,BorderLayout.CENTER);jp_AddBook.add(jp_AddBookSouth, BorderLayout.SOUTH);
        jp_Table_Book.add(jspISBNBookInformation);
        jp_Table_Book.add(jspBookInformation);
        jp_Btn.setLayout(new GridLayout(1,3));
        jp_Btn.add(btnAddBook);jp_Btn.add(btnDeleteBook);jp_Btn.add(btnExit2);
        p_BookInformation.add(jp_AddBook, BorderLayout.NORTH);
        p_BookInformation.add(jp_Table_Book,BorderLayout.CENTER);
        p_BookInformation.add(jp_Btn, BorderLayout.SOUTH);

        panel.add(p_BookInformation,"BookInformation");
        itemBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "BookInformation");
            }
        });

        /*******************����һ����***************************/
        btnAddBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnAddBook_Clicked();
            }
        });
        /*******************ɾ��һ����****************************/
        btnDeleteBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnDeleteBook_Clicked();
            }
        });

        /**
         * ��ѯ��Ϣ
         */
        cbSelect.addItem("��ѯ�����ѽ�����Ϣ");
        cbSelect.addItem("��ѯ���߸�����Ϣ");
        cbSelect.addItem("��ѯ�ѵ��黹ʱ�䵫δ����Ķ���");
        cbSelect.addItem("��ѯ����δ����ͼ��");
        cbSelect.addItem("ͳ��ĳһ��ʱ�����������ͼ��Ľ��Ĵ���");
        
        //�߽粼�֣������ϱ���
        p_Selct.setLayout(new BorderLayout());
        //���񲼾�
        JPanel jp_SelectNorth = new JPanel(new GridLayout(3,2));
        JPanel jp_SelectTable = new JPanel(new GridLayout(1,1));
        JPanel jp_SelectSouth = new JPanel(new GridLayout(6,1));
        JScrollPane jsp_tableSelect = new JScrollPane(tableSelect);
        jp_SelectNorth.add(cbSelect);jp_SelectNorth.add(btnSelect);
        jp_SelectNorth.add(lbltemp1);jp_SelectNorth.add(txtSelect1);
        jp_SelectNorth.add(lbltemp2);jp_SelectNorth.add(txtSelect2);
        jp_SelectTable.add(jsp_tableSelect);
        txtHint1.setText("�����ʾ:(δʹ��ǰ��Ĭ�ϲ�ѯ�ѵ��黹ʱ�䵫δ����Ķ���)");
        txtHint2.setText(" 1����ѯ�����ѽ�����Ϣʱ��ֻ����д��Ŀһ����ĿΪѧ��");
        txtHint3.setText(" 2����ѯ���߸�����Ϣ������д��Ŀһ����Ŀ������ĿһΪѧ�ţ���Ŀ��Ϊ����");
        txtHint4.setText(" 3����ѯ�ѵ��黹ʱ�䵫δ����Ķ��ߣ�������д");
        txtHint5.setText(" 4����ѯ����δ����ͼ�飬������д");
        txtHint6.setText(" 5��ͳ��ĳһ��ʱ�����������ͼ��Ľ��Ĵ���������д��Ŀһ����Ŀ������Ŀһ����Ŀ����Ϊʱ�䣬��Ŀһʱ��Ӧ����Ŀ��֮ǰ�����ڸ�ʽΪyyyy-mm-dd");
        txtHint1.setEditable(false);txtHint2.setEditable(false);txtHint3.setEditable(false);
        txtHint4.setEditable(false);txtHint5.setEditable(false);txtHint6.setEditable(false);
        jp_SelectSouth.add(txtHint1);jp_SelectSouth.add(txtHint2);jp_SelectSouth.add(txtHint3);
        jp_SelectSouth.add(txtHint4);jp_SelectSouth.add(txtHint5);jp_SelectSouth.add(txtHint6);
        p_Selct.add(jp_SelectNorth, BorderLayout.NORTH);
        p_Selct.add(jp_SelectTable, BorderLayout.CENTER);
        p_Selct.add(jp_SelectSouth, BorderLayout.SOUTH);
        panel.add(p_Selct,"SelectInformation");
        itemSelectInformation.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "SelectInformation");
            }
        });

        btnSelect.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnSelect_Clicked();
            }
        });

        this.setJMenuBar(menuBar);
        this.getContentPane().add(panel);
        this.getContentPane().add(p, BorderLayout.SOUTH);
        this.setVisible(true);
        double width = Toolkit.getDefaultToolkit().getScreenSize().width; //�õ���ǰ��Ļ�ֱ��ʵĸ�
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;//�õ���ǰ��Ļ�ֱ��ʵĿ�
        this.setSize((int)width,(int)height);
        this.setTitle("����Ա");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //�˵���ˢ�°�ť����¼�
    private void itemRefresh_Clicked() {
        updateTableReader();
        updateTableBook();
        updateTableISBNBook();
        updateTableLoadBook();
        String selectItem = String.valueOf(cbSelect.getSelectedItem());
        if (selectItem.equals("��ѯ�����ѽ�����Ϣ")){
            updateTableSelect(1);
        }else if (selectItem.equals("��ѯ���߸�����Ϣ")){
            updateTableSelect(2);
        }else if (selectItem.equals("��ѯ�ѵ��黹ʱ�䵫δ����Ķ���")){
            updateTableSelect(3);
        }else if (selectItem.equals("��ѯ����δ����ͼ��")){
            updateTableSelect(4);
        }else if (selectItem.equals("ͳ��ĳһ��ʱ�����������ͼ��Ľ��Ĵ���")){
            updateTableSelect(5);
        }
    }

    //�����鼮
    private void btnAddBook_Clicked() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String ISBNID = txtISBNID.getText().trim();
        String BookCategories = txtBookCategories.getText().trim();
        String BookName = txtBookName.getText().trim();
        String Author = txtAuthor.getText().trim();
        String PublishingHouse = txtPublishingHouse.getText().trim();
        String PublicationDate = txtPublictionDate.getText().trim();
        String NumberLibrary = txtNumberLibrary.getText().trim();
        String NumberLoad = NumberLibrary;
        String Price = txtPrice.getText().trim();
        String BookIntroduce = txtBookIntroduce.getText().trim();
        if (!ISBNID.isEmpty() && !BookCategories.isEmpty() && !BookName.isEmpty() &&!Author.isEmpty() &&!PublishingHouse.isEmpty() &&
                !PublicationDate.isEmpty() && !NumberLibrary.isEmpty() &&!Price.isEmpty() && !BookIntroduce.isEmpty()){
            if (isNumericInt(ISBNID) && ISBNID.length()==5){
                if (isNumericInt(NumberLibrary) && Integer.valueOf(NumberLibrary) < 1000){
                    if (isNumericFloat(Price)){
                        if (Double.valueOf(Price) <= 10000){
                            if (isValidDate(PublicationDate) && isBefore(PublicationDate, df.format(new Date())) &&
                                    isBefore("1900-1-1", PublicationDate)){
                                if(Dao.addBook(ISBNID,BookCategories,BookName,Author,PublishingHouse,PublicationDate,NumberLibrary,NumberLoad,Price,BookIntroduce)>0){
                                    JOptionPane.showMessageDialog(this, "ע��ɹ�");
                                    updateTableISBNBook();updateTableBook();
                                    clearBook();
                                }
                                else
                                    JOptionPane.showMessageDialog(this, "ע��ʧ��");
                            }
                            else
                                JOptionPane.showMessageDialog(this, "ʱ���ʽ����ȷ������Ϊyyyy-mm-dd,����1900-1-1����");
                        }
                        else
                            JOptionPane.showMessageDialog(this, "�鼮�۸񲻵ó���һ��");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "�۸����Ϊ��������");
                }else
                    JOptionPane.showMessageDialog(this, "�ݲ���������Ϊ������,���Ҳ��ܳ���һǧ��");
            }
           else
                JOptionPane.showMessageDialog(this, "ISBN�ű���Ϊ����������Ϊ5λ");


        }else
            JOptionPane.showMessageDialog(this, "��Ϣ��Ϊ����");

    }
    //���Ӷ���
    private void btnAddReader_Clicked(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.YEAR, -16);
        String ReaderID = txtIdReader.getText().trim();
        //��������м���
        String Password =AES.AESEncode("20162151", ReaderID) ;
        String Name =txtName.getText().trim();
        String Sex = String.valueOf(cbSex.getSelectedItem());
        String Birth = txtBirth.getText().trim();
        String ID = txtID.getText().trim();
        String ReadClass = String.valueOf(cbReadClass.getSelectedItem());
        String NumberLending ;
        String NumberLent = "0";
        if (ReadClass.equals("1"))
            NumberLending = "5";
        else
            NumberLending = "10";

        String Department = txtDepartment.getText().trim();
        String AddressHome = txtAddressHome.getText().trim();
        String Phone = txtPhone.getText().trim();
        if (!ReaderID.isEmpty() && !Password.isEmpty() && !Name.isEmpty() &&!Sex.isEmpty() && !Birth.isEmpty() &&
                !ID.isEmpty() && !ReadClass.isEmpty() && !Department.isEmpty() && !AddressHome.isEmpty() && !Phone.isEmpty()){
            if (isNumericInt(ReaderID) && ReaderID.length() == 8){
                if (isValidDate(Birth) && isBefore(Birth, df.format(c1.getTime())) && isBefore("1900-1-1", Birth)){
                    if (isNumericInt(Phone) && Phone.length() == 11){
                        if (ID.length() == 18){
                            String ID_1 = ID.substring(0,17);
                            if (isNumericInt(ID_1)){
                                if(Dao.addReader(ReaderID, Password, Name, Sex, Birth, ID, ReadClass,
                                        Department, AddressHome, Phone)>0){
                                    JOptionPane.showMessageDialog(this, "ע��ɹ�");
                                    updateTableReader();updateTableLoadBook();
                                    clearReader();
                                }
                                else
                                    JOptionPane.showMessageDialog(this, "ע��ʧ��");
                            }
                            else
                                JOptionPane.showMessageDialog(this, "���ߵ����֤��ǰ17λ������������");
                        }
                        else
                            JOptionPane.showMessageDialog(this, "���ߵ����֤����Ϊ18λ");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "������ϵ��ʽ����Ϊ11λ����");
                }
                else
                    JOptionPane.showMessageDialog(this, "���߱�����16���꣬���ҳ������ڸ�ʽΪ yyyy-mm-dd,�������ڲ�������1900-1-1");
            }
            else
                JOptionPane.showMessageDialog(this, "����֤�ű���Ϊ8λ����");
        }
        else
            JOptionPane.showMessageDialog(this, "��Ϣ��Ϊ����");
    }
    //���ö�������
    private void btnResetReaderPWD_Clicked(){
        int rowIndexReader= tableResetReaderPWD.getSelectedRow();
        if(rowIndexReader>-1){
            int r=JOptionPane.showConfirmDialog(this, "�Ƿ���������", "��������",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String id= tableResetReaderPWD.getValueAt(rowIndexReader, 0).toString();
                if(Dao.ResetReaderPwdById(id)>0){
                    JOptionPane.showMessageDialog(this, "��������ɹ�");
                    updateTableReader();
                }else
                    JOptionPane.showMessageDialog(this, "��������ʧ��");
            }
        }else
        	JOptionPane.showMessageDialog(this, "δѡ��");
    }
    //ɾ������
    private void btnDeleteReader_Clicked(){

        int rowIndexReader= tableReaderInformation.getSelectedRow();

         if(rowIndexReader>-1){
            int r=JOptionPane.showConfirmDialog(this, "�Ƿ�ɾ��", "ɾ������",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String id= tableReaderInformation.getValueAt(rowIndexReader, 0).toString();
                if(Dao.deleteReaderById(id)>0){
                    JOptionPane.showMessageDialog(this, "ɾ���ɹ�");
                    updateTableReader();updateTableLoadBook();
                }else{
                    JOptionPane.showMessageDialog(this, "ɾ��ʧ��");
                    updateTableReader();updateTableLoadBook();
                }
            }
        }else
        	JOptionPane.showMessageDialog(this, "δѡ��");
    }
    //ɾ���鼮
    private void btnDeleteBook_Clicked() {
        int rowIndexReader= tableBookInformation.getSelectedRow();
        if(rowIndexReader>-1){
            int r=JOptionPane.showConfirmDialog(this, "�Ƿ�ɾ��", "ɾ���鼮",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String id= tableBookInformation.getValueAt(rowIndexReader, 0).toString();
                if(Dao.deleteBook(id)>0){
                    JOptionPane.showMessageDialog(this, "ɾ���ɹ�");
                    updateTableBook();updateTableISBNBook();
                }else{
                    JOptionPane.showMessageDialog(this, "ɾ��ʧ��");
                    updateTableISBNBook();updateTableBook();
                }

            }
        }else
        	JOptionPane.showMessageDialog(this, "δѡ��");
    }
    //��ѯ
    private void btnSelect_Clicked() {
        String selectItem = String.valueOf(cbSelect.getSelectedItem());
        String selectInformation1 = txtSelect1.getText().trim();
        String selectInformation2 = txtSelect2.getText().trim();
        if (selectItem.equals("��ѯ�����ѽ�����Ϣ")){
            if (selectInformation1.equals("")){
                JOptionPane.showMessageDialog(this, "��ѯ��������Ϊ��");
            }else{
                updateTableSelect(1);
            }
        }else if (selectItem.equals("��ѯ���߸�����Ϣ")){
            if (!selectInformation1.equals("") && !selectInformation2.equals("")){
                updateTableSelect(2);
            }
           else
               JOptionPane.showMessageDialog(this, "��ѯ��������Ϊ��");
        }else if (selectItem.equals("��ѯ�ѵ��黹ʱ�䵫δ����Ķ���")){
            txtSelect1.setText("");txtSelect2.setText("");
            updateTableSelect(3);
        }else if (selectItem.equals("��ѯ����δ����ͼ��")){
            txtSelect1.setText("");txtSelect2.setText("");
            updateTableSelect(4);
        }else if (selectItem.equals("ͳ��ĳһ��ʱ�����������ͼ��Ľ��Ĵ���")){
            if (!selectInformation1.equals("") && !selectInformation2.equals("")){
                if (isValidDate(txtSelect1.getText().trim()) && isValidDate(txtSelect2.getText().trim())){
                    if (isBefore(txtSelect1.getText().trim(), txtSelect2.getText().trim()))
                        updateTableSelect(5);
                    else
                        JOptionPane.showMessageDialog(this, "��Ŀһ�����ڲ�����Ŀ��������֮ǰ");
                }
                else
                    JOptionPane.showMessageDialog(this, "��ѯ���ڸ�ʽ����ȷ������Ϊyyyy-mm-dd");
            }
           else
               JOptionPane.showMessageDialog(this, "��ѯ��������Ϊ��");
        }
    }
    //�����Ӷ��߽���������ı������
    private void clearReader(){
        txtPhone.setText("");
        txtIdReader.setText("");
        txtName.setText("");
        txtPwdReader.setText("");
        txtBirth.setText("");
        txtID.setText("");
        txtDepartment.setText("");
    }
    //�������鼮����������ı������
    private void clearBook(){
        txtISBNID.setText("");
        txtBookCategories.setText("");
        txtAuthor.setText("");
        txtBookName.setText("");
        txtPublishingHouse.setText("");
        txtPublictionDate.setText("");
        txtNumberLibrary.setText("");
        txtPrice.setText("");
        txtBookIntroduce.setText("");
    }
    //��ʼ��������Ϣ��
    private void initTableReaderInformation(){
        String[] cols={"����֤��","����","����","�Ա�","��������","���֤��","����ȼ�","�ɽ�������","�ѽ�������","��������","��ͥסַ","��ϵ��ʽ"};
        String[][] rows=Dao.queryReader();
        tableResetReaderPWD=new JTable(rows,cols);
        tableReaderInformation=new JTable(rows,cols);
    }
    //��ʼ��������Ϣ��
    private void initTableLoadBookInformation(){
        String[] cols={"����֤��","���","����ʱ��","Ӧ����ʱ��","�Ƿ���","����"};
        String[][] rows=Dao.queryLoadBook();
        tableReaderLoadBookInformation = new JTable(rows, cols);
    }
    //��ʼ���鼮ISBN��
    private void initTableBookISBNInformation(){
        String[] cols={"ISBN��","�鼮���","����","����","������","��������","ͼ��ݲ���","�ɽ�������","�鼮�۸�","�鼮����"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation=new JTable(rows,cols);
    }
    //��ʼ���鼮��Ϣ��
    private void initTableBookInformation(){
        String[] cols={"ISBN��","���","�Ƿ���"};
        String[][] rows=Dao.queryBook();
        tableBookID = new JTable(rows, cols);
    }
    //��ʼ����ѯ��
    private void initTableSelectReader(){
        String[] cols={"ͼ����", "����", "����֤��", "��������", "�������"};
        String[][] rows=Dao.SelectReader(null, null, 4);
        tableSelect = new JTable(rows, cols);
    }
    //���¶��߱�
    private void updateTableReader(){
        String[] cols={"����֤��","����","����","�Ա�","��������","���֤��","����ȼ�","�ɽ�������","�ѽ�������","��������","��ͥסַ","��ϵ��ʽ"};
        String[][] rows=Dao.queryReader();
        tableResetReaderPWD.setModel(new DefaultTableModel(rows,cols));
        tableReaderInformation.setModel(new DefaultTableModel(rows,cols));
    }
    //���½����
    private void updateTableLoadBook(){
        String[] cols={"����֤��","���","����ʱ��","Ӧ����ʱ��","�Ƿ���","����"};
        String[][] rows=Dao.queryLoadBook();
        tableReaderLoadBookInformation.setModel(new DefaultTableModel(rows,cols));
    }
    //�����鼮ISBN��
    private void updateTableISBNBook(){
        String[] cols={"ISBN��","�鼮���","����","����","������","��������","ͼ��ݲ���","�ɽ�������","�鼮�۸�","�鼮����"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation.setModel(new DefaultTableModel(rows,cols));
    }
    //������ű�
    private void updateTableBook(){
        String[] cols={"ISBN��","���","�Ƿ�ɽ�"};
        String[][] rows=Dao.queryBook();
        tableBookID.setModel(new DefaultTableModel(rows,cols));
    }
    //���²�ѯ�� �������Ϊn��n��ֵ��ѡ���������Ĳ�ѯ�й�
    private void updateTableSelect(int n){
        if (n == 1){
            String[] cols={"ͼ����","����","����֤��","�Ƿ���"};
            String[][] rows=Dao.SelectReader(txtSelect1.getText().trim(), null, n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n == 2){
            String[] cols={"����֤��", "����", "�Ա�", "��������", "���֤��", "����ȼ�",
            "�ɽ�������", "�ѽ�������", "���ڲ���", "��ͥסַ", "��ϵ��ʽ"};
            String[][] rows=Dao.SelectReader(txtSelect1.getText().trim(), txtSelect2.getText().trim(), n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n ==3){
            String[] cols={"����֤��", "����", "���"};
            String[][] rows=Dao.SelectReader(null, null, n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n == 4){
            String[] cols={"ͼ����", "����", "����֤��", "��������", "�������"};
            String[][] rows=Dao.SelectReader(null, null, n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n == 5){
            String[] cols={"ͼ�����", "������"};
            String[][] rows=Dao.SelectReader(txtSelect1.getText().trim(), txtSelect2.getText().trim(), n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }
    }

    /**
     *  ���ʱ���ʽ�Ƿ���ȷ
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд��
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
            sdf.setLenient(false);
            sdf.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // ���throw java.text.ParseException����NullPointerException����˵����ʽ����
            convertSuccess=false;
        }
        return convertSuccess;
    }
    /**
     * ��������ʱ��Ρ����ʱ��һ�Ƿ���ʱ���֮ǰ
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isBefore(String str1, String str2) {
        boolean convertSuccess=true;
        boolean isbefore = false;
        Date d1 =new Date(), d2 = new Date();
        // ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд��
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
            sdf.setLenient(false);
            d1 = sdf.parse(str1);
            d2 = sdf.parse(str2);
        } catch (ParseException e) {
            // e.printStackTrace();
            // ���throw java.text.ParseException����NullPointerException����˵����ʽ����
            convertSuccess=false;
        }
        if (convertSuccess) {
            if (d1.before(d2))
                isbefore = true;
        }else
            isbefore = false;

        return isbefore;
    }

    /**
     * ��������ʽ����ַ����Ƿ�Ϊ������
     * @param str
     * @return
     */
    private boolean isNumericInt(String str){
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     *  ��������ʽ����ַ����Ƿ�Ϊ��������
     * @param str
     * @return
     */
    private boolean isNumericFloat(String str){
        Pattern pattern = Pattern.compile("^(([0-9]+\\\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
