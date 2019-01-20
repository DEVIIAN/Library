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
     *菜单控件
     */
    private JMenu menuInformation=new JMenu("修改信息");
    private JMenuItem itemReader =new JMenuItem("读者信息管理");
    private JMenuItem itemBook =new JMenuItem("书籍信息管理");
    private JMenu menuResetPwd=new JMenu("重置密码");
    private JMenuItem itemResetReaderPWD =new JMenuItem("重置读者密码");
    private JMenu menuSelect = new JMenu("查询");
    private JMenuItem itemSelectInformation = new JMenuItem("查询信息");
    private JMenu menuRefresh = new JMenu("刷新");
    private JMenuItem itemRefresh = new JMenuItem("刷新信息");


    //表格
    private JTable tableReaderInformation =null;                        //读者信息表
    private JTable tableReaderLoadBookInformation = null;               //读者借书表
    private JTable tableBookInformation = null;                         //书籍信息表
    private JTable tableBookID = null;                                  //书号表
    private JTable tableResetReaderPWD =null;                           //读者重置密码表
    private JTable tableSelect = null;                                  //查询界面读者表
    /**
     * p_ReaderInformation控件，读者信息
     */
    private JButton btnResetReaderPWD =new JButton("重置密码");
    private JButton btnRefreshReset = new JButton("刷新");
    private JButton btnAddReader =new JButton("增加读者");
    private JButton btnDeleteReader =new JButton("删除读者");
    private JButton btnExit1 =new JButton("退出系统");
    private JLabel lblIdReader =new JLabel("读者证号");
    private JLabel lblPwdReader =new JLabel("密码");
    private JLabel lblName=new JLabel("姓名");
    private JLabel lblSex=new JLabel("性别");
    private JLabel lblBirth =new JLabel("出生日期");
    private JLabel lblID =new JLabel("身份证号");
    private JLabel lblReadClass =new JLabel("借书等级");
    private JLabel lblDepartment=new JLabel("工作部门");
    private JLabel lblAdressHome=new JLabel("家庭住址");
    private JLabel lblPhone=new JLabel("联系方式");

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
     * p_BookInformation控件，书籍信息
     */
    private JLabel lblISBNId =new JLabel("书籍ISBN号");
    private JLabel lblBookCategories =new JLabel("书籍类别");
    private JLabel lblBookName=new JLabel("书名");
    private JLabel lblAuthor=new JLabel("作者");
    private JLabel lblPublishingHouse =new JLabel("出版社");
    private JLabel lblPublictionDate =new JLabel("出版日期");
    private JLabel lblNumberLibrary =new JLabel("图书馆藏书数量");
    private JLabel lblPrice=new JLabel("书籍价格");
    private JLabel lblBookIntroduce=new JLabel("书籍描述");

    private JTextField txtISBNID =new JTextField(8);
    private JTextField txtBookCategories =new JTextField(50);
    private JTextField txtBookName =new JTextField(50);
    private JTextField txtAuthor =new JTextField(50);
    private JTextField txtPublishingHouse =new JTextField(50);
    private JTextField txtPublictionDate =new JTextField(10);
    private JTextField txtNumberLibrary =new JTextField(5);
    private JTextField txtPrice =new JTextField(10);
    private JTextField txtBookIntroduce =new JTextField(100);

    private JButton btnAddBook =new JButton("增加书籍");
    private JButton btnDeleteBook =new JButton("删除书籍");
    private JButton btnExit2 =new JButton("退出系统");

    /**
     *  查询界面
     */
    private JComboBox cbSelect = new JComboBox();
    private JButton btnSelect = new JButton("查询");
    private JLabel lbltemp1 = new JLabel("项目一");
    private JLabel lbltemp2 = new JLabel("项目二");
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
         *  label居中显示
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
         *菜单显示
         */
        menuInformation.add(itemReader);menuInformation.addSeparator();
        menuInformation.add(itemBook);
        menuResetPwd.add(itemResetReaderPWD);menuResetPwd.addSeparator();
        menuSelect.add(itemSelectInformation);
        menuRefresh.add(itemRefresh);
        menuBar.add(menuInformation);menuBar.add(menuResetPwd);menuBar.add(menuSelect);menuBar.add(menuRefresh);


        this.validate();			//确保组件具有有效的布局。
        p_ReaderInformation =new JPanel();
        p_ReaderResetPWD =new JPanel();
        p_BookInformation = new JPanel();
        p_Selct = new JPanel();

        //初始化表格
        initTableReaderInformation();initTableLoadBookInformation();
        initTableBookISBNInformation();initTableBookInformation();initTableSelectReader();
        
        //布局为卡片布局
        
        /**
         *读者信息布局
         */
        //边界布局，东西南北中
        p_ReaderInformation.setLayout(new BorderLayout());
        JPanel jp_ReaderAdd=new JPanel();
        JPanel jp_Table = new JPanel();
        JPanel jp_ReaderSouth=new JPanel();
        JScrollPane jspReaderInformation=new JScrollPane(tableReaderInformation);
        JScrollPane jspLoadBookInformation = new JScrollPane(tableReaderLoadBookInformation);
        
        cbReadClass.addItem("本科生");cbReadClass.addItem("研究生");cbReadClass.addItem("博士");cbReadClass.addItem("教师");
        cbSex.addItem("男");cbSex.addItem("女");
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

        /******************增加一个人*********************************/
        btnAddReader.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnAddReader_Clicked();
            }
        });

        /*******************删除一个人*****************************/
        btnDeleteReader.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnDeleteReader_Clicked();
            }
        });

        /**********************退出系统***************************/
        btnExit1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /**
         * 重置读者密码
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
        /******************重置密码********************/
        btnResetReaderPWD.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnResetReaderPWD_Clicked();
            }
        });

        /******************退出系统*********************/
        btnExit2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /**
         *  书籍信息
         */
        //边界布局，东西南北中
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

        /*******************增加一本书***************************/
        btnAddBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnAddBook_Clicked();
            }
        });
        /*******************删除一本书****************************/
        btnDeleteBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnDeleteBook_Clicked();
            }
        });

        /**
         * 查询信息
         */
        cbSelect.addItem("查询读者已借书信息");
        cbSelect.addItem("查询读者个人信息");
        cbSelect.addItem("查询已到归还时间但未还书的读者");
        cbSelect.addItem("查询到期未还的图书");
        cbSelect.addItem("统计某一段时间内所有类别图书的借阅次数");
        
        //边界布局，东西南北中
        p_Selct.setLayout(new BorderLayout());
        //网格布局
        JPanel jp_SelectNorth = new JPanel(new GridLayout(3,2));
        JPanel jp_SelectTable = new JPanel(new GridLayout(1,1));
        JPanel jp_SelectSouth = new JPanel(new GridLayout(6,1));
        JScrollPane jsp_tableSelect = new JScrollPane(tableSelect);
        jp_SelectNorth.add(cbSelect);jp_SelectNorth.add(btnSelect);
        jp_SelectNorth.add(lbltemp1);jp_SelectNorth.add(txtSelect1);
        jp_SelectNorth.add(lbltemp2);jp_SelectNorth.add(txtSelect2);
        jp_SelectTable.add(jsp_tableSelect);
        txtHint1.setText("填空提示:(未使用前，默认查询已到归还时间但未还书的读者)");
        txtHint2.setText(" 1、查询读者已借书信息时，只需填写项目一，项目为学号");
        txtHint3.setText(" 2、查询读者个人信息，需填写项目一、项目二，项目一为学号，项目二为姓名");
        txtHint4.setText(" 3、查询已到归还时间但未还书的读者，不用填写");
        txtHint5.setText(" 4、查询到期未还的图书，不用填写");
        txtHint6.setText(" 5、统计某一段时间内所有类别图书的借阅次数，需填写项目一、项目二，项目一和项目二均为时间，项目一时间应在项目二之前。日期格式为yyyy-mm-dd");
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
        double width = Toolkit.getDefaultToolkit().getScreenSize().width; //得到当前屏幕分辨率的高
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;//得到当前屏幕分辨率的宽
        this.setSize((int)width,(int)height);
        this.setTitle("管理员");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //菜单中刷新按钮点击事件
    private void itemRefresh_Clicked() {
        updateTableReader();
        updateTableBook();
        updateTableISBNBook();
        updateTableLoadBook();
        String selectItem = String.valueOf(cbSelect.getSelectedItem());
        if (selectItem.equals("查询读者已借书信息")){
            updateTableSelect(1);
        }else if (selectItem.equals("查询读者个人信息")){
            updateTableSelect(2);
        }else if (selectItem.equals("查询已到归还时间但未还书的读者")){
            updateTableSelect(3);
        }else if (selectItem.equals("查询到期未还的图书")){
            updateTableSelect(4);
        }else if (selectItem.equals("统计某一段时间内所有类别图书的借阅次数")){
            updateTableSelect(5);
        }
    }

    //增加书籍
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
                                    JOptionPane.showMessageDialog(this, "注册成功");
                                    updateTableISBNBook();updateTableBook();
                                    clearBook();
                                }
                                else
                                    JOptionPane.showMessageDialog(this, "注册失败");
                            }
                            else
                                JOptionPane.showMessageDialog(this, "时间格式不正确，必须为yyyy-mm-dd,并在1900-1-1至今");
                        }
                        else
                            JOptionPane.showMessageDialog(this, "书籍价格不得超过一万");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "价格必须为正浮点数");
                }else
                    JOptionPane.showMessageDialog(this, "馆藏数量必须为正整数,并且不能超过一千本");
            }
           else
                JOptionPane.showMessageDialog(this, "ISBN号必须为正整数并且为5位");


        }else
            JOptionPane.showMessageDialog(this, "信息均为必填");

    }
    //增加读者
    private void btnAddReader_Clicked(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.YEAR, -16);
        String ReaderID = txtIdReader.getText().trim();
        //对密码进行加密
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
                                    JOptionPane.showMessageDialog(this, "注册成功");
                                    updateTableReader();updateTableLoadBook();
                                    clearReader();
                                }
                                else
                                    JOptionPane.showMessageDialog(this, "注册失败");
                            }
                            else
                                JOptionPane.showMessageDialog(this, "读者的身份证号前17位必须是正整数");
                        }
                        else
                            JOptionPane.showMessageDialog(this, "读者的身份证必须为18位");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "读者联系方式必须为11位整数");
                }
                else
                    JOptionPane.showMessageDialog(this, "读者必须满16周岁，并且出生日期格式为 yyyy-mm-dd,出生日期不得早于1900-1-1");
            }
            else
                JOptionPane.showMessageDialog(this, "读者证号必须为8位整数");
        }
        else
            JOptionPane.showMessageDialog(this, "信息均为必填");
    }
    //重置读者密码
    private void btnResetReaderPWD_Clicked(){
        int rowIndexReader= tableResetReaderPWD.getSelectedRow();
        if(rowIndexReader>-1){
            int r=JOptionPane.showConfirmDialog(this, "是否重置密码", "重置密码",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String id= tableResetReaderPWD.getValueAt(rowIndexReader, 0).toString();
                if(Dao.ResetReaderPwdById(id)>0){
                    JOptionPane.showMessageDialog(this, "重置密码成功");
                    updateTableReader();
                }else
                    JOptionPane.showMessageDialog(this, "重置密码失败");
            }
        }else
        	JOptionPane.showMessageDialog(this, "未选中");
    }
    //删除读者
    private void btnDeleteReader_Clicked(){

        int rowIndexReader= tableReaderInformation.getSelectedRow();

         if(rowIndexReader>-1){
            int r=JOptionPane.showConfirmDialog(this, "是否删除", "删除读者",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String id= tableReaderInformation.getValueAt(rowIndexReader, 0).toString();
                if(Dao.deleteReaderById(id)>0){
                    JOptionPane.showMessageDialog(this, "删除成功");
                    updateTableReader();updateTableLoadBook();
                }else{
                    JOptionPane.showMessageDialog(this, "删除失败");
                    updateTableReader();updateTableLoadBook();
                }
            }
        }else
        	JOptionPane.showMessageDialog(this, "未选中");
    }
    //删除书籍
    private void btnDeleteBook_Clicked() {
        int rowIndexReader= tableBookInformation.getSelectedRow();
        if(rowIndexReader>-1){
            int r=JOptionPane.showConfirmDialog(this, "是否删除", "删除书籍",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String id= tableBookInformation.getValueAt(rowIndexReader, 0).toString();
                if(Dao.deleteBook(id)>0){
                    JOptionPane.showMessageDialog(this, "删除成功");
                    updateTableBook();updateTableISBNBook();
                }else{
                    JOptionPane.showMessageDialog(this, "删除失败");
                    updateTableISBNBook();updateTableBook();
                }

            }
        }else
        	JOptionPane.showMessageDialog(this, "未选中");
    }
    //查询
    private void btnSelect_Clicked() {
        String selectItem = String.valueOf(cbSelect.getSelectedItem());
        String selectInformation1 = txtSelect1.getText().trim();
        String selectInformation2 = txtSelect2.getText().trim();
        if (selectItem.equals("查询读者已借书信息")){
            if (selectInformation1.equals("")){
                JOptionPane.showMessageDialog(this, "查询条件不能为空");
            }else{
                updateTableSelect(1);
            }
        }else if (selectItem.equals("查询读者个人信息")){
            if (!selectInformation1.equals("") && !selectInformation2.equals("")){
                updateTableSelect(2);
            }
           else
               JOptionPane.showMessageDialog(this, "查询条件不能为空");
        }else if (selectItem.equals("查询已到归还时间但未还书的读者")){
            txtSelect1.setText("");txtSelect2.setText("");
            updateTableSelect(3);
        }else if (selectItem.equals("查询到期未还的图书")){
            txtSelect1.setText("");txtSelect2.setText("");
            updateTableSelect(4);
        }else if (selectItem.equals("统计某一段时间内所有类别图书的借阅次数")){
            if (!selectInformation1.equals("") && !selectInformation2.equals("")){
                if (isValidDate(txtSelect1.getText().trim()) && isValidDate(txtSelect2.getText().trim())){
                    if (isBefore(txtSelect1.getText().trim(), txtSelect2.getText().trim()))
                        updateTableSelect(5);
                    else
                        JOptionPane.showMessageDialog(this, "项目一的日期不在项目二的日期之前");
                }
                else
                    JOptionPane.showMessageDialog(this, "查询日期格式不正确，必须为yyyy-mm-dd");
            }
           else
               JOptionPane.showMessageDialog(this, "查询条件不能为空");
        }
    }
    //将增加读者界面的所有文本框清空
    private void clearReader(){
        txtPhone.setText("");
        txtIdReader.setText("");
        txtName.setText("");
        txtPwdReader.setText("");
        txtBirth.setText("");
        txtID.setText("");
        txtDepartment.setText("");
    }
    //将增加书籍界面的所有文本框清空
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
    //初始化读者信息表
    private void initTableReaderInformation(){
        String[] cols={"读者证号","密码","姓名","性别","出生日期","身份证号","读书等级","可借书数量","已借书数量","工作部门","家庭住址","联系方式"};
        String[][] rows=Dao.queryReader();
        tableResetReaderPWD=new JTable(rows,cols);
        tableReaderInformation=new JTable(rows,cols);
    }
    //初始化借书信息表
    private void initTableLoadBookInformation(){
        String[] cols={"读者证号","书号","借书时间","应还书时间","是否还书","罚金"};
        String[][] rows=Dao.queryLoadBook();
        tableReaderLoadBookInformation = new JTable(rows, cols);
    }
    //初始化书籍ISBN表
    private void initTableBookISBNInformation(){
        String[] cols={"ISBN号","书籍类别","书名","作者","出版社","出版日期","图书馆藏书","可借书数量","书籍价格","书籍介绍"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation=new JTable(rows,cols);
    }
    //初始化书籍信息表
    private void initTableBookInformation(){
        String[] cols={"ISBN号","书号","是否还书"};
        String[][] rows=Dao.queryBook();
        tableBookID = new JTable(rows, cols);
    }
    //初始化查询表
    private void initTableSelectReader(){
        String[] cols={"图书编号", "书名", "读者证号", "读者姓名", "借出日期"};
        String[][] rows=Dao.SelectReader(null, null, 4);
        tableSelect = new JTable(rows, cols);
    }
    //更新读者表
    private void updateTableReader(){
        String[] cols={"读者证号","密码","姓名","性别","出生日期","身份证号","读书等级","可借书数量","已借书数量","工作部门","家庭住址","联系方式"};
        String[][] rows=Dao.queryReader();
        tableResetReaderPWD.setModel(new DefaultTableModel(rows,cols));
        tableReaderInformation.setModel(new DefaultTableModel(rows,cols));
    }
    //更新借书表
    private void updateTableLoadBook(){
        String[] cols={"读者证号","书号","借书时间","应还书时间","是否还书","罚金"};
        String[][] rows=Dao.queryLoadBook();
        tableReaderLoadBookInformation.setModel(new DefaultTableModel(rows,cols));
    }
    //更新书籍ISBN表
    private void updateTableISBNBook(){
        String[] cols={"ISBN号","书籍类别","书名","作者","出版社","出版日期","图书馆藏书","可借书数量","书籍价格","书籍介绍"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation.setModel(new DefaultTableModel(rows,cols));
    }
    //更新书号表
    private void updateTableBook(){
        String[] cols={"ISBN号","书号","是否可借"};
        String[][] rows=Dao.queryBook();
        tableBookID.setModel(new DefaultTableModel(rows,cols));
    }
    //更新查询表 输入参数为n，n的值与选择那种类别的查询有关
    private void updateTableSelect(int n){
        if (n == 1){
            String[] cols={"图书编号","书名","读者证号","是否还书"};
            String[][] rows=Dao.SelectReader(txtSelect1.getText().trim(), null, n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n == 2){
            String[] cols={"读者证号", "姓名", "性别", "出生日期", "身份证号", "借书等级",
            "可借书数量", "已借书数量", "所在部门", "家庭住址", "联系方式"};
            String[][] rows=Dao.SelectReader(txtSelect1.getText().trim(), txtSelect2.getText().trim(), n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n ==3){
            String[] cols={"读者证号", "姓名", "书号"};
            String[][] rows=Dao.SelectReader(null, null, n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n == 4){
            String[] cols={"图书编号", "书名", "读者证号", "读者姓名", "借出日期"};
            String[][] rows=Dao.SelectReader(null, null, n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }else if (n == 5){
            String[] cols={"图书类别", "外借次数"};
            String[][] rows=Dao.SelectReader(txtSelect1.getText().trim(), txtSelect2.getText().trim(), n);
            tableSelect.setModel(new DefaultTableModel(rows,cols));
            txtSelect1.setText("");txtSelect2.setText("");
        }
    }

    /**
     *  检查时间格式是否正确
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            sdf.setLenient(false);
            sdf.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }
    /**
     * 输入两个时间段。检查时间一是否在时间二之前
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isBefore(String str1, String str2) {
        boolean convertSuccess=true;
        boolean isbefore = false;
        Date d1 =new Date(), d2 = new Date();
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            sdf.setLenient(false);
            d1 = sdf.parse(str1);
            d2 = sdf.parse(str2);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
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
     * 用正则表达式检查字符串是否为正整数
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
     *  用正则表达式检查字符串是否为正浮点数
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
