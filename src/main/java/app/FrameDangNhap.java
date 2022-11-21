package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.DAO_TaiKhoan;
import entity.TaiKhoan;

public class FrameDangNhap extends JFrame implements KeyListener, MouseListener {
	private static JTextField txtTaiKhoan;
	private JPasswordField txtMatKhau;
	private JButton btnDangNhap;
	private static DAO_TaiKhoan dao_TaiKhoan;
	public static TaiKhoan taiKhoanHienTai = null;
	public FrameDangNhap() throws RemoteException {
		try {
			dao_TaiKhoan = (DAO_TaiKhoan) Naming.lookup("rmi://192.168.101.35:9999/dao_TaiKhoan");
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (RemoteException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (NotBoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		setTitle("ĐĂNG NHẬP");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(290, 320);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlContentPane.setBackground(new Color(248, 227, 182));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		
		JLabel lblTieuDe = new JLabel("ĐĂNG NHẬP");
		lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTieuDe.setForeground(new Color(131, 77, 30));
		lblTieuDe.setBounds(75, 14, 157, 23);
		pnlContentPane.add(lblTieuDe);
		/*
		 * Tài khoản, mật khẩu
		 */
		JLabel lblTaiKhoan = new JLabel("Tên đăng nhập");
		lblTaiKhoan.setBounds(30, 35, 100, 43);
		lblTaiKhoan.setForeground(new Color(131, 77, 30));
		lblTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblTaiKhoan);

		txtTaiKhoan = new JTextField();
		txtTaiKhoan.setBounds(30, 70, 220, 35);
		txtTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTaiKhoan.setBackground(new Color(245, 245, 245));
		pnlContentPane.add(txtTaiKhoan);

		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setBounds(30, 105, 70, 43);
		lblMatKhau.setForeground(new Color(131, 77, 30));
		lblMatKhau.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblMatKhau);

		txtMatKhau = new JPasswordField();
		txtMatKhau.setBounds(30, 140, 220, 35);
		txtMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMatKhau.setBackground(new Color(245, 245, 245));
		pnlContentPane.add(txtMatKhau);
		/*
		 * Chức năng
		 */
		btnDangNhap = new JButton("ĐĂNG NHẬP");
		btnDangNhap.setBounds(30, 190, 220, 40);
		btnDangNhap.setForeground(new Color(255, 255, 255));
		btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDangNhap.setBackground(new Color(131, 77, 30));
		btnDangNhap.setFocusPainted(false);
		pnlContentPane.add(btnDangNhap);

		JLabel lblQuenMatKhau = new JLabel("<HTML><U>Quên mật khẩu?</U></HTML>");
		lblQuenMatKhau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblQuenMatKhau.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblQuenMatKhau.setForeground(new Color(131, 77, 30));
		lblQuenMatKhau.setBounds(95, 230, 135, 43);
		pnlContentPane.add(lblQuenMatKhau);

		txtTaiKhoan.addKeyListener(this);
		txtMatKhau.addKeyListener(this);
		lblQuenMatKhau.addMouseListener(this);
		btnDangNhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String taikhoan = txtTaiKhoan.getText();
				String matkhau = txtMatKhau.getText();

				int flag = 0;
				
				try {
					List<TaiKhoan> listTK = dao_TaiKhoan.getAllTaiKhoan();
					for (TaiKhoan tk : listTK) {
						if (tk.getTenTaiKhoan().getMaNV().trim().equals(taikhoan) && tk.getMatKhau().trim().equals(matkhau)) {
							flag = 1;
							taiKhoanHienTai = tk;
							break;
						}
					}
					if (flag == 0) {
						JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!!!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						txtTaiKhoan.requestFocus();
						return;
					} else {
						if (taikhoan.substring(0, 2).equals("NV")) {
							GUI_NhanVien guiNV = new GUI_NhanVien();
							guiNV.setVisible(true);
							guiNV.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							guiNV.setLocationRelativeTo(null);
//							guiNV.setExtendedState(JFrame.MAXIMIZED_BOTH);
							dispose();
						}
						if (taikhoan.substring(0, 2).equals("QL")) {
							GUI_QuanLy guiQL;
							try {
								guiQL = new GUI_QuanLy();
								guiQL.setVisible(true);
								guiQL.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
								guiQL.setLocationRelativeTo(null);
//								guiQL.setExtendedState(JFrame.MAXIMIZED_BOTH);
								dispose();
							} catch (ParseException e1) {
								e1.printStackTrace();
							}

						}
					}
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
			}
		});
		
	}

	public static void main(String[] args) throws RemoteException {
		new FrameDangNhap().setVisible(true);
	}
	
	public static TaiKhoan getTaiKhoan() {
		return taiKhoanHienTai;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnDangNhap.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		FrameXacNhanTaiKhoan frameXN;
		try {
			frameXN = new FrameXacNhanTaiKhoan();
			frameXN.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			frameXN.setVisible(true);
			frameXN.setLocationRelativeTo(null);
			dispose();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
