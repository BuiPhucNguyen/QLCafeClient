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
import entity.NhanVien;
import entity.TaiKhoan;

public class FrameDatLaiMatKhau extends JFrame implements KeyListener{

	private JPasswordField txtMatKhauMoi;
	private JPasswordField txtXacNhan;
	private JButton btnDoiMatKhau;

	private NhanVien nvDoiMatKhau;
	private static DAO_TaiKhoan dao_TaiKhoan;

	public FrameDatLaiMatKhau(NhanVien nv) throws RemoteException {
		
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
		
		setTitle("ĐẶT LẠI MẬT KHẨU");
		setSize(330, 210);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				try {
					new FrameDangNhap().setVisible(true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBounds(0, 0, 400, 365);
		pnlContentPane.setBackground(new Color(248, 227, 182));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblMatKhauMoi = new JLabel("MẬT KHẨU MỚI:");
		lblMatKhauMoi.setBounds(25, 25, 120, 20);
		lblMatKhauMoi.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMatKhauMoi);

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setBounds(140, 15, 150, 30);
		pnlContentPane.add(txtMatKhauMoi);

		JLabel lblXacNhan = new JLabel("XÁC NHẬN:");
		lblXacNhan.setBounds(25, 65, 120, 14);
		lblXacNhan.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblXacNhan);

		txtXacNhan = new JPasswordField();
		txtXacNhan.setBounds(140, 55, 150, 30);
		pnlContentPane.add(txtXacNhan);

		btnDoiMatKhau = new JButton("ĐỔI MẬT KHẨU");
		btnDoiMatKhau.setBounds(70, 105, 180, 42);
		btnDoiMatKhau.setForeground(Color.WHITE);
		btnDoiMatKhau.setBackground(new Color(131, 77, 30));
		btnDoiMatKhau.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDoiMatKhau.setFocusPainted(false);
		btnDoiMatKhau.setIcon(new ImageIcon("image/matkhau.png"));
		pnlContentPane.add(btnDoiMatKhau);

		nvDoiMatKhau = nv;
		
		txtMatKhauMoi.addKeyListener(this);
		txtXacNhan.addKeyListener(this);
		btnDoiMatKhau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String matKhauMoi = txtMatKhauMoi.getText().trim();
				if (validInput()) {
					TaiKhoan tk = nvDoiMatKhau.getTaiKhoan();
					tk.setMatKhau(matKhauMoi);
					try {
						if (dao_TaiKhoan.capnhatTaiKhoan(tk) == true) {
							JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
							new FrameDangNhap().setVisible(true);
							setVisible(false);
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

	public static void main(String[] args) throws RemoteException {
		new FrameDatLaiMatKhau(new NhanVien()).setVisible(true);
	}

	
	public boolean validInput() {
		String matKhauMoi = txtMatKhauMoi.getText().trim();
		String xacNhan = txtXacNhan.getText().trim();

		if (matKhauMoi.equals("") || xacNhan.equals("")) {
			JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận không được để trống");
			return false;
		}

//		TaiKhoan tk = doimatkhau_dao.getTaiKhoanTheoTenTaiKhoan(nvDoiMatKhau.getTaiKhoan().getTenTaiKhoan());

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