package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import dao.DAO_TaiKhoan;
import entity.TaiKhoan;

public class FrameDoiMatKhau extends JFrame implements KeyListener{

	private JPasswordField txtMatkhauCu;
	private JPasswordField txtMatkhauMoi;
	private JPasswordField txtXacNhan;
	private JButton btnDoiMatKhau;
	private static DAO_TaiKhoan dao_TaiKhoan;

	public FrameDoiMatKhau() throws RemoteException {
		try {
			dao_TaiKhoan = (DAO_TaiKhoan) Naming.lookup("rmi://192.168.101.35:9999/dao_TaiKhoan");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		setTitle("ĐỔI MẬT KHẨU");
		setSize(400, 220);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel(null);
		pnlContentPane.setBackground(new Color(252, 242, 217));
		setContentPane(pnlContentPane);

		txtMatkhauCu = new JPasswordField();
		txtMatkhauCu.setBounds(150, 15, 204, 30);
		pnlContentPane.add(txtMatkhauCu);

		txtMatkhauMoi = new JPasswordField();
		txtMatkhauMoi.setBounds(150, 50, 204, 30);
		pnlContentPane.add(txtMatkhauMoi);

		txtXacNhan = new JPasswordField();
		txtXacNhan.setBounds(150, 85, 204, 30);
		pnlContentPane.add(txtXacNhan);

		JLabel lblMKcu = new JLabel("MẬT KHẨU CŨ:");
		lblMKcu.setBounds(30, 20, 120, 20);
		lblMKcu.setForeground(Color.BLACK);
		lblMKcu.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMKcu);

		JLabel lblMKmoi = new JLabel("MẬT KHẨU MỚI:");
		lblMKmoi.setBounds(30, 55, 120, 20);
		lblMKmoi.setForeground(Color.BLACK);
		lblMKmoi.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMKmoi);

		JLabel lblXn = new JLabel("XÁC NHẬN:");
		lblXn.setBounds(30, 90, 120, 20);
		lblXn.setForeground(Color.BLACK);
		lblXn.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblXn);

		btnDoiMatKhau = new JButton("ĐỔI MẬT KHẨU");
		btnDoiMatKhau.setBounds(110, 125, 180, 42);
		btnDoiMatKhau.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDoiMatKhau.setIcon(new ImageIcon("image/matkhau.png"));
		btnDoiMatKhau.setForeground(Color.WHITE);
		btnDoiMatKhau.setBackground(new Color(131, 77, 30));
		btnDoiMatKhau.setFocusPainted(false);

		pnlContentPane.add(btnDoiMatKhau);

		txtMatkhauCu.addKeyListener(this);
		txtMatkhauMoi.addKeyListener(this);
		txtXacNhan.addKeyListener(this);
		btnDoiMatKhau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String matKhauCu = txtMatkhauCu.getText().trim();
				String matKhauMoi = txtMatkhauMoi.getText().trim();
				String xacNhan = txtXacNhan.getText().trim();

				
				if (!ktraMatKhau())
					return;
				else {
					try {
						TaiKhoan tk = FrameDangNhap.getTaiKhoan();
						tk.setMatKhau(xacNhan);
						if (dao_TaiKhoan.capnhatTaiKhoan(tk) == true) {
							JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

	}

//	public static void main(String[] args) {
//		new FrameDoiMatKhau().setVisible(true);
//	}

	public boolean ktraMatKhau() {
		String matKhauCu = txtMatkhauCu.getText().trim();
		String matKhauMoi = txtMatkhauMoi.getText().trim();
		String xacNhan = txtXacNhan.getText().trim();

		if (matKhauCu.equals("") || matKhauMoi.equals("") || xacNhan.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mật khẩu");
			return false;
		}

		TaiKhoan tk = FrameDangNhap.getTaiKhoan();

		if (!matKhauCu.equals(tk.getMatKhau().trim())) {
			JOptionPane.showMessageDialog(this, "Mật khẩu cũ không chính xác");
			return false;
		}
		if (matKhauMoi.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}")) {
			if (!matKhauMoi.equals(xacNhan)) {
				JOptionPane.showMessageDialog(this, "Xác nhận không chính xác");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Mật khẩu mới từ 8 đến 20 kí tự gồm cả chữ và số");
			return false;
		}
		return true;
	}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		btnDoiMatKhau.doClick();
	}
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}
