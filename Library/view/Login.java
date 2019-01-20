package view;

import Dao.LoginDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public static String Id;
//    public static String Pwd;
    private JLabel lblName=new JLabel("用户名");
    private JLabel lblPwd=new JLabel("密    码");
    private JTextField txtName=new JTextField(10);
    private JPasswordField txtPwd=new JPasswordField(10);
    private JButton btnLogin=new JButton("登陆");
    private JButton btnCance =new JButton("取消");
    private JRadioButton RBtnAdmin =new JRadioButton("管理员");
    private JRadioButton RBtnReader =new JRadioButton("读  者");
    private ButtonGroup btnGroup=new ButtonGroup();
    private LoginDao LD=new LoginDao();
    public Login(){
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
        this.setTitle("登陆");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 300);
        this.setVisible(true);
        this.setLocation(500,300);
        btnLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin_Clicked();
            }
        });
        btnCance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
    }

    public void btnLogin_Clicked(){
        if(RBtnAdmin.isSelected()){
            String password=LD.SelectPassword(txtName.getText(), "管理员");
            if(txtPwd.getText().equals(password)){
                MenuAdmin menuAdmin = new MenuAdmin();
//                this.setVisible(false);
                dispose();
                JOptionPane.showMessageDialog( this, "登陆管理员成功");
            }
            else{
                JOptionPane.showMessageDialog( this, "账号或密码错误");
                txtPwd.setText("");
            }
        }
        else if(RBtnReader.isSelected()){
            String password=LD.SelectPassword(txtName.getText(), "读者");
            if(txtPwd.getText().equals(password)){
                Id=txtName.getText();
                MenuReader frm_Reader=new MenuReader(Id);
//                this.setVisible(false);
                dispose();
                Id=txtName.getText();
                JOptionPane.showMessageDialog( this, "登陆读者成功");
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
