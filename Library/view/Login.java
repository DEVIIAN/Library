package view;

import Dao.LoginDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {//继承Java窗口类
    public static String Id;
//    public static String Pwd;
    private JLabel lblName=new JLabel("用户名");
    private JLabel lblPwd=new JLabel("密    码");
    private JTextField txtName=new JTextField(10);
    private JPasswordField txtPwd=new JPasswordField(10);//密文文本框
    private JButton btnLogin=new JButton("登陆");
    private JButton btnCance =new JButton("取消");
    private JRadioButton RBtnAdmin =new JRadioButton("管理员");
    private JRadioButton RBtnReader =new JRadioButton("读  者");
    private ButtonGroup btnGroup=new ButtonGroup();
    private LoginDao LD=new LoginDao();
    public Login(){
    	//label全部居中显示
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblPwd.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel jp=(JPanel)this.getContentPane();
        JPanel jp_Center=new JPanel();
        jp_Center.setLayout(new GridLayout(3,2));
        jp_Center.add(lblName);jp_Center.add(txtName);
        jp_Center.add(lblPwd);jp_Center.add(txtPwd);
        jp_Center.add(btnLogin);jp_Center.add(btnCance);
        JPanel jp_South=new JPanel();
        btnGroup.add(RBtnAdmin);btnGroup.add(RBtnReader);
        jp_South.add(RBtnAdmin);jp_South.add(RBtnReader);
        jp.add(jp_Center);jp.add(jp_South,BorderLayout.SOUTH);
        this.setTitle("登陆");			//设置标题
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//界面一旦关闭，程序停止
        this.setSize(450, 300);			//设置登录界面大小
        this.setVisible(true);
        this.setLocation(500,300);		//设置出现的位置
        //点击登录
        btnLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin_Clicked();
            }
        });
        //点击退出
        btnCance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
    }
    
    //鼠标点击登录按钮事件
    public void btnLogin_Clicked(){
        if(RBtnAdmin.isSelected()){
            String password=LD.SelectPassword(txtName.getText(), "管理员");
            if(txtPwd.getText().equals(password)){
                MenuAdmin menuAdmin = new MenuAdmin();
//                this.setVisible(false);		//此窗口不可视
                //销毁此窗口，但不停止程序
                dispose();
                JOptionPane.showMessageDialog( this, "管理员登陆成功");
            }
            else{
                JOptionPane.showMessageDialog( this, "账号或密码错误");
                txtPwd.setText("");
            }
        }
        else if(RBtnReader.isSelected()){
            String password=LD.SelectPassword(txtName.getText().trim(), "读者");
            if(txtPwd.getText().equals(password)){
                Id=txtName.getText();
//                this.setVisible(false);
                dispose();
                JOptionPane.showMessageDialog( this, "读者登陆成功");
                //检查读者有没有逾期未还的书，并弹出相应的对话框
                if(LD.SelectNoReturnBooksByReaderID(Id)) {
                	MenuReader frm_Reader=new MenuReader(Id);
                	JOptionPane.showMessageDialog( this, "有书逾期未还，不可借书");
                }else {
                	MenuReader frm_Reader=new MenuReader(Id);
                	JOptionPane.showMessageDialog( this, "没有书逾期未还，可借书");
                }
                	
                
            }
            else{
                JOptionPane.showMessageDialog( this, "账号或密码错误");
                txtPwd.setText("");
            }
        }
        else
            JOptionPane.showMessageDialog( this, "未选择登陆类型");
    }


}
