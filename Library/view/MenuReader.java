package view;

import Dao.ReaderDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuReader extends JFrame {

    /**
     *  菜单
     */
    private JMenuBar menuBar=new JMenuBar();
    private JMenu menuInformation=new JMenu("读者信息");                        //读者信息
    private JMenuItem itemLoadBookInformation =new JMenuItem("借书信息管理");                //可还书
    private JMenu menuSelectBook=new JMenu("查找书籍");                         //可通过几种方式查找书籍，并选中书号借书
    private JMenuItem itemSelectBook =new JMenuItem("查找书籍");
    private JMenu menuResetReader=new JMenu("修改信息");
    private JMenuItem itemResetReader =new JMenuItem("修改读者信息");
    private JMenu menuRefresh=new JMenu("刷新");
    private JMenuItem itemRefresh =new JMenuItem("刷新读者信息");
    /**
     * 卡片布局
     */
    private CardLayout card=new CardLayout();
    private JPanel p=new JPanel();
    private JPanel panel=new JPanel(card);
    private JPanel p_ReaderInformation =null,               //读者借书
            p_ResetReader =null,                         //重置密码
            p_Selct = null;                                 //查找书籍

    /**
     *  表格
     */
    private JTable tableReaderInformation =null;                        //读者信息表
    private JTable tableReaderLoadBookInformation = null;               //读者借书表
    private JTable tableBookInformation = null;                         //书籍信息表
    private JTable tableBookID = null;                                  //书号表
    private JTable tableResetReader = null;                             //修改读者信息表

    /**
     * 读者信息
     */
    private JButton btnReturnBook =new JButton("还书");          //还书
    private JButton btnExit1 =new JButton("退出系统");

    /**
     * 查书信息
     */
    private JComboBox cbSelect = new JComboBox();
    private JTextField txtInformation = new JTextField(50);
    private JButton btnSelectBook = new JButton("查找");
    private JButton btnLoadBook = new JButton("借书");
    private JButton btnExit2 = new JButton("退出系统");

    /**
     *  修改信息
     */
    private JComboBox cbReset = new JComboBox();
    private JTextField txtResetInformation = new JTextField();                      //对输入长度有限制
    private JButton btnResetInforamtion = new JButton("修改");
    private JButton btnExit3 = new JButton("退出系统");

    /**
     *  连接数据库操作
     */
    private ReaderDao Dao= new ReaderDao();
    //构造函数，输入参数为读者证号
    public MenuReader(String id){


        /**
         * 表格
         */
        initTableReaderInformation(id);initTableReaderLoadBookInformation(id);
        initTableBookISBNInformation();initTableBookInformation();
        /**
         *  label居中
         */

        menuInformation.add(itemLoadBookInformation);menuInformation.addSeparator();
        menuSelectBook.add(itemSelectBook);menuSelectBook.addSeparator();
        menuResetReader.add(itemResetReader);menuResetReader.addSeparator();
        menuRefresh.add(itemRefresh);
        menuBar.add(menuInformation);menuBar.add(menuSelectBook);menuBar.add(menuResetReader);menuBar.add(menuRefresh);
        this.validate();	//确保组件具有有效的布局。
        p_ReaderInformation =new JPanel();
        p_ResetReader =new JPanel();
        p_Selct = new JPanel();
        /**
         *  读者信息界面
         */
        //设置布局为边界布局，东西南北中
        p_ReaderInformation.setLayout(new BorderLayout());
        //设置布局为网格布局
        JPanel jp_TableReader = new JPanel(new GridLayout(2,1));
        JPanel jp_ReaderSouth=new JPanel(new GridLayout(1,2));
        //有滚动条的滚动面板
        JScrollPane jspReaderInformation=new JScrollPane(tableReaderInformation);
        JScrollPane jspLoadBookInformation = new JScrollPane(tableReaderLoadBookInformation);
        
        jp_TableReader.add(jspReaderInformation);jp_TableReader.add(jspLoadBookInformation);
        jp_ReaderSouth.add(btnReturnBook);jp_ReaderSouth.add(btnExit1);
        p_ReaderInformation.add(jp_TableReader, BorderLayout.NORTH);
        p_ReaderInformation.add(jp_ReaderSouth, BorderLayout.SOUTH);
        panel.add(p_ReaderInformation,"ReaderInformation");
        itemLoadBookInformation.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "ReaderInformation");
            }
        });
        itemRefresh.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                itemRefresh_Clicked(id);
            }
        });
        btnReturnBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                btnReturnBook_Clicked(id);
            }
        });
        btnExit1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /**
         * 查找书籍界面
         */
        //
        cbSelect.addItem("书名");cbSelect.addItem("ISBN书号");cbSelect.addItem("作者");cbSelect.addItem("出版社");
        //设置布局为边界布局，东西南北中
        p_Selct.setLayout(new BorderLayout());
        JPanel jp_SelectNorth = new JPanel(new GridLayout(1,2));
        JPanel jp_tableBook = new JPanel(new GridLayout(2,1));
        JPanel jp_SelectSouth = new JPanel(new GridLayout(1,3));
        JScrollPane jspBookISBNInformaiton=new JScrollPane(tableBookInformation);
        JScrollPane jspBookInformation = new JScrollPane(tableBookID);

        jp_SelectNorth.add(cbSelect);jp_SelectNorth.add(txtInformation);
        jp_tableBook.add(jspBookISBNInformaiton);jp_tableBook.add(jspBookInformation);
        jp_SelectSouth.add(btnSelectBook);jp_SelectSouth.add(btnLoadBook);jp_SelectSouth.add(btnExit2);
        p_Selct.add(jp_SelectNorth, BorderLayout.NORTH);
        p_Selct.add(jp_tableBook, BorderLayout.CENTER);
        p_Selct.add(jp_SelectSouth, BorderLayout.SOUTH);
        panel.add(p_Selct,"SelectBook");
        itemSelectBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "SelectBook");
            }
        });

        btnSelectBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSelectBook_Clicked();
            }
        });

        btnLoadBook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLoadtBook_Clicked(id);
            }
        });

        btnExit2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /**
         *  修改信息
         */
        cbReset.addItem("密码");cbReset.addItem("家庭住址");cbReset.addItem("电话");

        p_ResetReader.setLayout(new BorderLayout());
        JPanel jp_ResetNorth = new JPanel(new GridLayout(1,2));
        JPanel jp_tableReset = new JPanel(new GridLayout(1, 1));
        JPanel jp_ResetSouth = new JPanel(new GridLayout(1,2));
        JScrollPane jspResetReader = new JScrollPane(tableResetReader);

        jp_ResetNorth.add(cbReset);jp_ResetNorth.add(txtResetInformation);
        jp_tableReset.add(jspResetReader);
        jp_ResetSouth.add(btnResetInforamtion);jp_ResetSouth.add(btnExit3);

        p_ResetReader.add(jp_ResetNorth, BorderLayout.NORTH);
        p_ResetReader.add(jp_tableReset);
        p_ResetReader.add(jp_ResetSouth, BorderLayout.SOUTH);
        panel.add(p_ResetReader,"ReaderReset");
        itemResetReader.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, "ReaderReset");
            }
        });

        //重置信息
        btnResetInforamtion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnResetInforamtion_Clicked(id);
            }
        });

        btnExit3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.setJMenuBar(menuBar);
        this.getContentPane().add(panel);
        this.getContentPane().add(p, BorderLayout.SOUTH);
        this.setVisible(true);
//        this.setSize(1200, 1000);
        double width = Toolkit.getDefaultToolkit().getScreenSize().width; //得到当前屏幕分辨率的高
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;//得到当前屏幕分辨率的宽
        this.setSize((int)width,(int)height);	//全屏幕的分辨率显示窗口
        this.setTitle("读者");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭此窗口则程序停止
    }
    //菜单刷新操作
    private void itemRefresh_Clicked(String id) {
        String SelectItem = String.valueOf(cbSelect.getSelectedItem());
        String Inforamtion = txtInformation.getText().trim();
        updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
        if(!Inforamtion.equals("")) {
        	if (SelectItem.equals("书名")){
                updateBookISBNInformation(Inforamtion , 0);updateBookInformation(Inforamtion , 0);
            }
            else if (SelectItem.equals("ISBN书号")){
                updateBookISBNInformation(Inforamtion , 1);updateBookInformation(Inforamtion , 1);
            }
            else if(SelectItem.equals("作者")){
                updateBookISBNInformation(Inforamtion , 2);updateBookInformation(Inforamtion , 2);
            }
            else if (SelectItem.equals("出版社")){
                updateBookISBNInformation(Inforamtion , 3);updateBookInformation(Inforamtion , 3);
            }
        }else {
        	updateBookISBNInformation2();
        	updateBookInformation2();
        }
    }


    /**
     *	还书事件
     * @param id
     */
    private void btnReturnBook_Clicked(String id) {
        int rowIndex= tableReaderLoadBookInformation.getSelectedRow();
        if(rowIndex>-1){
            int r=JOptionPane.showConfirmDialog(this, "是否还书", "还书",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String BookID= tableReaderLoadBookInformation.getValueAt(rowIndex, 1).toString().trim();
                if(Dao.ReturnBook(id, BookID)>0){
                    JOptionPane.showMessageDialog(this, "还书成功");
                    updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
                    UpdateSelectBook();
                }else{
                    JOptionPane.showMessageDialog(this, "还书失败");
                    updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
                    UpdateSelectBook();
                }
            }
        }else
        	JOptionPane.showMessageDialog(this, "未选中");
    }
    //更新查询到的信息
    private void UpdateSelectBook(){
        String SelectItem = String.valueOf(cbSelect.getSelectedItem()).trim();
        String Information = txtInformation.getText().trim();
        if (!Information.equals("")){
            if (SelectItem.equals("书名")){
                updateBookISBNInformation(Information, 0);
                updateBookInformation(Information, 0);
            }
            else if (SelectItem.equals("ISBN书号")){
                updateBookISBNInformation(Information , 1);
                updateBookInformation(Information , 1);
            }
            else if(SelectItem.equals("作者")) {
                updateBookISBNInformation(Information, 2);
                updateBookInformation(Information, 2);
            }
            else if (SelectItem.equals("出版社")) {
                updateBookISBNInformation(Information, 3);
                updateBookInformation(Information, 3);
            }
        }
        else {
        	updateBookISBNInformation2();
        	updateBookInformation2();
        }
    }
    //查询书籍信息
    private void btnSelectBook_Clicked() {
        String SelectItem = String.valueOf(cbSelect.getSelectedItem());
        String Information = txtInformation.getText().trim();
        if (Information.equals(""))
            JOptionPane.showMessageDialog(this,"查询条件不能为空");
        else{
            if (SelectItem.equals("书名")){
                if(Dao.SelectBook(Information, 0)>0){
                    JOptionPane.showMessageDialog(this, "查找成功");
                    updateBookISBNInformation(Information , 0);updateBookInformation(Information , 0);
                }
                else
                    JOptionPane.showMessageDialog(this, "查找失败");
            }
            else if (SelectItem.equals("ISBN书号")){
                if(Dao.SelectBook(Information, 1)>0){
                    JOptionPane.showMessageDialog(this, "查找成功");
                    updateBookISBNInformation(Information , 1);updateBookInformation(Information , 1);
                }
                else
                    JOptionPane.showMessageDialog(this, "查找失败");
            }
            else if(SelectItem.equals("作者")){
                if(Dao.SelectBook(Information, 2)>0){
                    JOptionPane.showMessageDialog(this, "查找成功");
                    updateBookISBNInformation(Information , 2);updateBookInformation(Information , 2);
                }
                else
                    JOptionPane.showMessageDialog(this, "查找失败");
            }
            else if (SelectItem.equals("出版社")){
                if(Dao.SelectBook(Information, 3)>0){
                    JOptionPane.showMessageDialog(this, "查找成功");
                    updateBookISBNInformation(Information , 3);updateBookInformation(Information, 3);
                }
                else
                    JOptionPane.showMessageDialog(this, "查找失败");
            }
        }


    }
    //借书
    private void btnLoadtBook_Clicked(String id) {
        String SelectItem = String.valueOf(cbSelect.getSelectedItem());
        int rowIndex= tableBookID.getSelectedRow();
        String Information = txtInformation.getText().trim();
        if(rowIndex>-1){
            int r=JOptionPane.showConfirmDialog(this, "是否借书", "借书",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String BookID= tableBookID.getValueAt(rowIndex, 1).toString().trim();
                if(Dao.SelectNoReturnBooksByReaderID(id)) {
                	JOptionPane.showMessageDialog( this, "有书逾期未还，不可借书");
                }
                else {
                	if(Dao.LoadBook(id, BookID)>0){
                        JOptionPane.showMessageDialog(this, "借书成功");
                        updateTableReaderInformation(id);updateReaderLoadBookInformation(id);

                        if (!Information.equals("")){
                            if (SelectItem.equals("书名")){
                                updateBookISBNInformation(Information, 0);
                                updateBookInformation(Information, 0);
                            }
                            else if (SelectItem.equals("ISBN书号")){
                                updateBookISBNInformation(Information , 1);
                                updateBookInformation(Information , 1);
                            }
                            else if(SelectItem.equals("作者")) {
                                updateBookISBNInformation(Information, 2);
                                updateBookInformation(Information, 2);
                            }
                            else if (SelectItem.equals("出版社")) {
                                updateBookISBNInformation(Information, 3);
                                updateBookInformation(Information, 3);
                            }
                        }
                        else {
                        	updateBookISBNInformation2();
                        	updateBookInformation2();
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "借书失败");
                        updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
                    }
                }
                
            }
        }else
        	JOptionPane.showMessageDialog(this, "未选中");
    }

    //重置信息
    private void btnResetInforamtion_Clicked(String id) {
        String SelectItem = String.valueOf(cbReset.getSelectedItem());
        String Information = txtResetInformation.getText().trim();
        if (Information.equals(""))
            JOptionPane.showMessageDialog(this, "更改信息不可为空");
        else{
            if (SelectItem.equals("密码")){
                if (Information.length() <= 18){
                    if(Dao.ResetInformation(Information, id,  0)>0){
                        JOptionPane.showMessageDialog(this, "修改成功");
                        updateTableReaderInformation(id);
                        txtResetInformation.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "修改失败");
                }
                else
                    JOptionPane.showMessageDialog(this, "密码位数不能多于18位");
            }
            else if (SelectItem.equals("家庭住址")){
                if(Dao.ResetInformation(Information, id,  1)>0){
                    JOptionPane.showMessageDialog(this, "修改成功");
                    updateTableReaderInformation(id);
                    txtResetInformation.setText("");
                }
                else
                    JOptionPane.showMessageDialog(this, "修改失败");
            }
            else if (SelectItem.equals("电话")){

                if (Information.length() == 11 && isNumericInt(Information)){
                    if(Dao.ResetInformation(Information, id, 2)>0){
                        JOptionPane.showMessageDialog(this, "修改成功");
                        updateTableReaderInformation(id);
                        txtResetInformation.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "修改失败");
                }
                else
                    JOptionPane.showMessageDialog(this, "电话号码必须为11位正整数");

            }
        }

    }

    //初始化信息
    private void initTableReaderInformation(String id){
        String[] cols={"读者证号","密码","姓名","性别","出生日期","身份证号","读书等级","可借书数量","已借书数量","工作部门","家庭住址","联系方式"};
        String[][] rows=Dao.queryReader(id);
        tableReaderInformation=new JTable(rows,cols);
        tableResetReader = new JTable(rows, cols);
    }
    //初始化信息
    private void initTableReaderLoadBookInformation(String id){
        String[] cols={"读者证号","书号","借书时间","应还书时间","是否还书","罚金"};
        String[][] rows=Dao.queryLoadBook(id);
        tableReaderLoadBookInformation = new JTable(rows,cols);
    }
    //初始化信息
    private void initTableBookISBNInformation(){
        String[] cols={"ISBN号","书名","书籍类别","作者","出版社","可借书数量","书籍介绍"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation=new JTable(rows,cols);
    }
    //初始化信息
    private void initTableBookInformation(){
        String[] cols={"ISBN号","书号","是否可借"};
        String[][] rows=Dao.queryBook();
        tableBookID = new JTable(rows, cols);
    }
    //更新信息
    private void updateReaderLoadBookInformation(String id) {
        String[] cols={"读者证号","书号","借书时间","应还书时间","是否还书","罚金"};
        String[][] rows=Dao.queryLoadBook(id);
        tableReaderLoadBookInformation.setModel(new DefaultTableModel(rows, cols));
    }
    //更新信息
    private void updateTableReaderInformation(String id) {
        String[] cols={"读者证号","密码","姓名","性别","出生日期","身份证号","读书等级","可借书数量","已借书数量","工作部门","家庭住址","联系方式"};
        String[][] rows=Dao.queryReader(id);
        tableReaderInformation.setModel(new DefaultTableModel(rows, cols));
        tableResetReader.setModel(new DefaultTableModel(rows, cols));
    }


    //更新信息
    private void updateBookISBNInformation(String SelectItem, int n) {
        String[] cols={"ISBN号","书名","书籍类别","作者","出版社","可借书数量","书籍介绍"};
        String[][] rows=Dao.SelectISBNBooks(SelectItem, n);
        tableBookInformation.setModel(new DefaultTableModel(rows,cols));

    }
    //更新信息
    private void updateBookISBNInformation2() {
        String[] cols={"ISBN号","书名","书籍类别","作者","出版社","可借书数量","书籍介绍"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation.setModel(new DefaultTableModel(rows,cols));

    }
    //更新信息
    private void updateBookInformation(String selectItem, int n) {
        String[] cols={"ISBN号","书号","是否可借"};
        String[][] rows=Dao.SelectBooksID(selectItem, n);
        tableBookID.setModel(new DefaultTableModel(rows, cols));
    }
    //更新信息
    private void updateBookInformation2() {
        String[] cols={"ISBN号","书号","是否可借"};
        String[][] rows=Dao.queryBook();
        tableBookID.setModel(new DefaultTableModel(rows, cols));
    }
    //用正则表达式验证
    private boolean isNumericInt(String str){
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
