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
     *  �˵�
     */
    private JMenuBar menuBar=new JMenuBar();
    private JMenu menuInformation=new JMenu("������Ϣ");                        //������Ϣ
    private JMenuItem itemLoadBookInformation =new JMenuItem("������Ϣ����");                //�ɻ���
    private JMenu menuSelectBook=new JMenu("�����鼮");                         //��ͨ�����ַ�ʽ�����鼮����ѡ����Ž���
    private JMenuItem itemSelectBook =new JMenuItem("�����鼮");
    private JMenu menuResetReader=new JMenu("�޸���Ϣ");
    private JMenuItem itemResetReader =new JMenuItem("�޸Ķ�����Ϣ");
    private JMenu menuRefresh=new JMenu("ˢ��");
    private JMenuItem itemRefresh =new JMenuItem("ˢ�¶�����Ϣ");
    /**
     * ��Ƭ����
     */
    private CardLayout card=new CardLayout();
    private JPanel p=new JPanel();
    private JPanel panel=new JPanel(card);
    private JPanel p_ReaderInformation =null,               //���߽���
            p_ResetReader =null,                         //��������
            p_Selct = null;                                 //�����鼮

    /**
     *  ���
     */
    private JTable tableReaderInformation =null;                        //������Ϣ��
    private JTable tableReaderLoadBookInformation = null;               //���߽����
    private JTable tableBookInformation = null;                         //�鼮��Ϣ��
    private JTable tableBookID = null;                                  //��ű�
    private JTable tableResetReader = null;                             //�޸Ķ�����Ϣ��

    /**
     * ������Ϣ
     */
    private JButton btnReturnBook =new JButton("����");          //����
    private JButton btnExit1 =new JButton("�˳�ϵͳ");

    /**
     * ������Ϣ
     */
    private JComboBox cbSelect = new JComboBox();
    private JTextField txtInformation = new JTextField(50);
    private JButton btnSelectBook = new JButton("����");
    private JButton btnLoadBook = new JButton("����");
    private JButton btnExit2 = new JButton("�˳�ϵͳ");

    /**
     *  �޸���Ϣ
     */
    private JComboBox cbReset = new JComboBox();
    private JTextField txtResetInformation = new JTextField();                      //�����볤��������
    private JButton btnResetInforamtion = new JButton("�޸�");
    private JButton btnExit3 = new JButton("�˳�ϵͳ");

    /**
     *  �������ݿ����
     */
    private ReaderDao Dao= new ReaderDao();
    //���캯�����������Ϊ����֤��
    public MenuReader(String id){


        /**
         * ���
         */
        initTableReaderInformation(id);initTableReaderLoadBookInformation(id);
        initTableBookISBNInformation();initTableBookInformation();
        /**
         *  label����
         */

        menuInformation.add(itemLoadBookInformation);menuInformation.addSeparator();
        menuSelectBook.add(itemSelectBook);menuSelectBook.addSeparator();
        menuResetReader.add(itemResetReader);menuResetReader.addSeparator();
        menuRefresh.add(itemRefresh);
        menuBar.add(menuInformation);menuBar.add(menuSelectBook);menuBar.add(menuResetReader);menuBar.add(menuRefresh);
        this.validate();	//ȷ�����������Ч�Ĳ��֡�
        p_ReaderInformation =new JPanel();
        p_ResetReader =new JPanel();
        p_Selct = new JPanel();
        /**
         *  ������Ϣ����
         */
        //���ò���Ϊ�߽粼�֣������ϱ���
        p_ReaderInformation.setLayout(new BorderLayout());
        //���ò���Ϊ���񲼾�
        JPanel jp_TableReader = new JPanel(new GridLayout(2,1));
        JPanel jp_ReaderSouth=new JPanel(new GridLayout(1,2));
        //�й������Ĺ������
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
         * �����鼮����
         */
        //
        cbSelect.addItem("����");cbSelect.addItem("ISBN���");cbSelect.addItem("����");cbSelect.addItem("������");
        //���ò���Ϊ�߽粼�֣������ϱ���
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
         *  �޸���Ϣ
         */
        cbReset.addItem("����");cbReset.addItem("��ͥסַ");cbReset.addItem("�绰");

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

        //������Ϣ
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
        double width = Toolkit.getDefaultToolkit().getScreenSize().width; //�õ���ǰ��Ļ�ֱ��ʵĸ�
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;//�õ���ǰ��Ļ�ֱ��ʵĿ�
        this.setSize((int)width,(int)height);	//ȫ��Ļ�ķֱ�����ʾ����
        this.setTitle("����");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//�رմ˴��������ֹͣ
    }
    //�˵�ˢ�²���
    private void itemRefresh_Clicked(String id) {
        String SelectItem = String.valueOf(cbSelect.getSelectedItem());
        String Inforamtion = txtInformation.getText().trim();
        updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
        if(!Inforamtion.equals("")) {
        	if (SelectItem.equals("����")){
                updateBookISBNInformation(Inforamtion , 0);updateBookInformation(Inforamtion , 0);
            }
            else if (SelectItem.equals("ISBN���")){
                updateBookISBNInformation(Inforamtion , 1);updateBookInformation(Inforamtion , 1);
            }
            else if(SelectItem.equals("����")){
                updateBookISBNInformation(Inforamtion , 2);updateBookInformation(Inforamtion , 2);
            }
            else if (SelectItem.equals("������")){
                updateBookISBNInformation(Inforamtion , 3);updateBookInformation(Inforamtion , 3);
            }
        }else {
        	updateBookISBNInformation2();
        	updateBookInformation2();
        }
    }


    /**
     *	�����¼�
     * @param id
     */
    private void btnReturnBook_Clicked(String id) {
        int rowIndex= tableReaderLoadBookInformation.getSelectedRow();
        if(rowIndex>-1){
            int r=JOptionPane.showConfirmDialog(this, "�Ƿ���", "����",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String BookID= tableReaderLoadBookInformation.getValueAt(rowIndex, 1).toString().trim();
                if(Dao.ReturnBook(id, BookID)>0){
                    JOptionPane.showMessageDialog(this, "����ɹ�");
                    updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
                    UpdateSelectBook();
                }else{
                    JOptionPane.showMessageDialog(this, "����ʧ��");
                    updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
                    UpdateSelectBook();
                }
            }
        }else
        	JOptionPane.showMessageDialog(this, "δѡ��");
    }
    //���²�ѯ������Ϣ
    private void UpdateSelectBook(){
        String SelectItem = String.valueOf(cbSelect.getSelectedItem()).trim();
        String Information = txtInformation.getText().trim();
        if (!Information.equals("")){
            if (SelectItem.equals("����")){
                updateBookISBNInformation(Information, 0);
                updateBookInformation(Information, 0);
            }
            else if (SelectItem.equals("ISBN���")){
                updateBookISBNInformation(Information , 1);
                updateBookInformation(Information , 1);
            }
            else if(SelectItem.equals("����")) {
                updateBookISBNInformation(Information, 2);
                updateBookInformation(Information, 2);
            }
            else if (SelectItem.equals("������")) {
                updateBookISBNInformation(Information, 3);
                updateBookInformation(Information, 3);
            }
        }
        else {
        	updateBookISBNInformation2();
        	updateBookInformation2();
        }
    }
    //��ѯ�鼮��Ϣ
    private void btnSelectBook_Clicked() {
        String SelectItem = String.valueOf(cbSelect.getSelectedItem());
        String Information = txtInformation.getText().trim();
        if (Information.equals(""))
            JOptionPane.showMessageDialog(this,"��ѯ��������Ϊ��");
        else{
            if (SelectItem.equals("����")){
                if(Dao.SelectBook(Information, 0)>0){
                    JOptionPane.showMessageDialog(this, "���ҳɹ�");
                    updateBookISBNInformation(Information , 0);updateBookInformation(Information , 0);
                }
                else
                    JOptionPane.showMessageDialog(this, "����ʧ��");
            }
            else if (SelectItem.equals("ISBN���")){
                if(Dao.SelectBook(Information, 1)>0){
                    JOptionPane.showMessageDialog(this, "���ҳɹ�");
                    updateBookISBNInformation(Information , 1);updateBookInformation(Information , 1);
                }
                else
                    JOptionPane.showMessageDialog(this, "����ʧ��");
            }
            else if(SelectItem.equals("����")){
                if(Dao.SelectBook(Information, 2)>0){
                    JOptionPane.showMessageDialog(this, "���ҳɹ�");
                    updateBookISBNInformation(Information , 2);updateBookInformation(Information , 2);
                }
                else
                    JOptionPane.showMessageDialog(this, "����ʧ��");
            }
            else if (SelectItem.equals("������")){
                if(Dao.SelectBook(Information, 3)>0){
                    JOptionPane.showMessageDialog(this, "���ҳɹ�");
                    updateBookISBNInformation(Information , 3);updateBookInformation(Information, 3);
                }
                else
                    JOptionPane.showMessageDialog(this, "����ʧ��");
            }
        }


    }
    //����
    private void btnLoadtBook_Clicked(String id) {
        String SelectItem = String.valueOf(cbSelect.getSelectedItem());
        int rowIndex= tableBookID.getSelectedRow();
        String Information = txtInformation.getText().trim();
        if(rowIndex>-1){
            int r=JOptionPane.showConfirmDialog(this, "�Ƿ����", "����",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            if(r==JOptionPane.OK_OPTION){
                String BookID= tableBookID.getValueAt(rowIndex, 1).toString().trim();
                if(Dao.SelectNoReturnBooksByReaderID(id)) {
                	JOptionPane.showMessageDialog( this, "��������δ�������ɽ���");
                }
                else {
                	if(Dao.LoadBook(id, BookID)>0){
                        JOptionPane.showMessageDialog(this, "����ɹ�");
                        updateTableReaderInformation(id);updateReaderLoadBookInformation(id);

                        if (!Information.equals("")){
                            if (SelectItem.equals("����")){
                                updateBookISBNInformation(Information, 0);
                                updateBookInformation(Information, 0);
                            }
                            else if (SelectItem.equals("ISBN���")){
                                updateBookISBNInformation(Information , 1);
                                updateBookInformation(Information , 1);
                            }
                            else if(SelectItem.equals("����")) {
                                updateBookISBNInformation(Information, 2);
                                updateBookInformation(Information, 2);
                            }
                            else if (SelectItem.equals("������")) {
                                updateBookISBNInformation(Information, 3);
                                updateBookInformation(Information, 3);
                            }
                        }
                        else {
                        	updateBookISBNInformation2();
                        	updateBookInformation2();
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "����ʧ��");
                        updateTableReaderInformation(id);updateReaderLoadBookInformation(id);
                    }
                }
                
            }
        }else
        	JOptionPane.showMessageDialog(this, "δѡ��");
    }

    //������Ϣ
    private void btnResetInforamtion_Clicked(String id) {
        String SelectItem = String.valueOf(cbReset.getSelectedItem());
        String Information = txtResetInformation.getText().trim();
        if (Information.equals(""))
            JOptionPane.showMessageDialog(this, "������Ϣ����Ϊ��");
        else{
            if (SelectItem.equals("����")){
                if (Information.length() <= 18){
                    if(Dao.ResetInformation(Information, id,  0)>0){
                        JOptionPane.showMessageDialog(this, "�޸ĳɹ�");
                        updateTableReaderInformation(id);
                        txtResetInformation.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "�޸�ʧ��");
                }
                else
                    JOptionPane.showMessageDialog(this, "����λ�����ܶ���18λ");
            }
            else if (SelectItem.equals("��ͥסַ")){
                if(Dao.ResetInformation(Information, id,  1)>0){
                    JOptionPane.showMessageDialog(this, "�޸ĳɹ�");
                    updateTableReaderInformation(id);
                    txtResetInformation.setText("");
                }
                else
                    JOptionPane.showMessageDialog(this, "�޸�ʧ��");
            }
            else if (SelectItem.equals("�绰")){

                if (Information.length() == 11 && isNumericInt(Information)){
                    if(Dao.ResetInformation(Information, id, 2)>0){
                        JOptionPane.showMessageDialog(this, "�޸ĳɹ�");
                        updateTableReaderInformation(id);
                        txtResetInformation.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(this, "�޸�ʧ��");
                }
                else
                    JOptionPane.showMessageDialog(this, "�绰�������Ϊ11λ������");

            }
        }

    }

    //��ʼ����Ϣ
    private void initTableReaderInformation(String id){
        String[] cols={"����֤��","����","����","�Ա�","��������","���֤��","����ȼ�","�ɽ�������","�ѽ�������","��������","��ͥסַ","��ϵ��ʽ"};
        String[][] rows=Dao.queryReader(id);
        tableReaderInformation=new JTable(rows,cols);
        tableResetReader = new JTable(rows, cols);
    }
    //��ʼ����Ϣ
    private void initTableReaderLoadBookInformation(String id){
        String[] cols={"����֤��","���","����ʱ��","Ӧ����ʱ��","�Ƿ���","����"};
        String[][] rows=Dao.queryLoadBook(id);
        tableReaderLoadBookInformation = new JTable(rows,cols);
    }
    //��ʼ����Ϣ
    private void initTableBookISBNInformation(){
        String[] cols={"ISBN��","����","�鼮���","����","������","�ɽ�������","�鼮����"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation=new JTable(rows,cols);
    }
    //��ʼ����Ϣ
    private void initTableBookInformation(){
        String[] cols={"ISBN��","���","�Ƿ�ɽ�"};
        String[][] rows=Dao.queryBook();
        tableBookID = new JTable(rows, cols);
    }
    //������Ϣ
    private void updateReaderLoadBookInformation(String id) {
        String[] cols={"����֤��","���","����ʱ��","Ӧ����ʱ��","�Ƿ���","����"};
        String[][] rows=Dao.queryLoadBook(id);
        tableReaderLoadBookInformation.setModel(new DefaultTableModel(rows, cols));
    }
    //������Ϣ
    private void updateTableReaderInformation(String id) {
        String[] cols={"����֤��","����","����","�Ա�","��������","���֤��","����ȼ�","�ɽ�������","�ѽ�������","��������","��ͥסַ","��ϵ��ʽ"};
        String[][] rows=Dao.queryReader(id);
        tableReaderInformation.setModel(new DefaultTableModel(rows, cols));
        tableResetReader.setModel(new DefaultTableModel(rows, cols));
    }


    //������Ϣ
    private void updateBookISBNInformation(String SelectItem, int n) {
        String[] cols={"ISBN��","����","�鼮���","����","������","�ɽ�������","�鼮����"};
        String[][] rows=Dao.SelectISBNBooks(SelectItem, n);
        tableBookInformation.setModel(new DefaultTableModel(rows,cols));

    }
    //������Ϣ
    private void updateBookISBNInformation2() {
        String[] cols={"ISBN��","����","�鼮���","����","������","�ɽ�������","�鼮����"};
        String[][] rows=Dao.queryISBNBook();
        tableBookInformation.setModel(new DefaultTableModel(rows,cols));

    }
    //������Ϣ
    private void updateBookInformation(String selectItem, int n) {
        String[] cols={"ISBN��","���","�Ƿ�ɽ�"};
        String[][] rows=Dao.SelectBooksID(selectItem, n);
        tableBookID.setModel(new DefaultTableModel(rows, cols));
    }
    //������Ϣ
    private void updateBookInformation2() {
        String[] cols={"ISBN��","���","�Ƿ�ɽ�"};
        String[][] rows=Dao.queryBook();
        tableBookID.setModel(new DefaultTableModel(rows, cols));
    }
    //��������ʽ��֤
    private boolean isNumericInt(String str){
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
