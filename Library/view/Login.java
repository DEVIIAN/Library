package view;

import Dao.LoginDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {//�̳�Java������
    public static String Id;
//    public static String Pwd;
    private JLabel lblName=new JLabel("�û���");
    private JLabel lblPwd=new JLabel("��    ��");
    private JTextField txtName=new JTextField(10);
    private JPasswordField txtPwd=new JPasswordField(10);//�����ı���
    private JButton btnLogin=new JButton("��½");
    private JButton btnCance =new JButton("ȡ��");
    private JRadioButton RBtnAdmin =new JRadioButton("����Ա");
    private JRadioButton RBtnReader =new JRadioButton("��  ��");
    private ButtonGroup btnGroup=new ButtonGroup();
    private LoginDao LD=new LoginDao();
    public Login(){
    	//labelȫ��������ʾ
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
        this.setTitle("��½");			//���ñ���
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//����һ���رգ�����ֹͣ
        this.setSize(450, 300);			//���õ�¼�����С
        this.setVisible(true);
        this.setLocation(500,300);		//���ó��ֵ�λ��
        //�����¼
        btnLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin_Clicked();
            }
        });
        //����˳�
        btnCance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
    }
    
    //�������¼��ť�¼�
    public void btnLogin_Clicked(){
        if(RBtnAdmin.isSelected()){
            String password=LD.SelectPassword(txtName.getText(), "����Ա");
            if(txtPwd.getText().equals(password)){
                MenuAdmin menuAdmin = new MenuAdmin();
//                this.setVisible(false);		//�˴��ڲ�����
                //���ٴ˴��ڣ�����ֹͣ����
                dispose();
                JOptionPane.showMessageDialog( this, "����Ա��½�ɹ�");
            }
            else{
                JOptionPane.showMessageDialog( this, "�˺Ż��������");
                txtPwd.setText("");
            }
        }
        else if(RBtnReader.isSelected()){
            String password=LD.SelectPassword(txtName.getText().trim(), "����");
            if(txtPwd.getText().equals(password)){
                Id=txtName.getText();
//                this.setVisible(false);
                dispose();
                JOptionPane.showMessageDialog( this, "���ߵ�½�ɹ�");
                //��������û������δ�����飬��������Ӧ�ĶԻ���
                if(LD.SelectNoReturnBooksByReaderID(Id)) {
                	MenuReader frm_Reader=new MenuReader(Id);
                	JOptionPane.showMessageDialog( this, "��������δ�������ɽ���");
                }else {
                	MenuReader frm_Reader=new MenuReader(Id);
                	JOptionPane.showMessageDialog( this, "û��������δ�����ɽ���");
                }
                	
                
            }
            else{
                JOptionPane.showMessageDialog( this, "�˺Ż��������");
                txtPwd.setText("");
            }
        }
        else
            JOptionPane.showMessageDialog( this, "δѡ���½����");
    }


}
