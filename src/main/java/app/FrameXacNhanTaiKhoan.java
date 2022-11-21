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
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DAO_NhanVien;
import entity.NhanVien;

public class FrameXacNhanTaiKhoan extends JFrame implements KeyListener{
	private JTextField txtMaNV;
	private JTextField txtCMND;
	private JButton btnXacNhan;
	private static DAO_NhanVien dao_NhanVien;
	

	public FrameXacNhanTaiKhoan() throws RemoteException {
		
		try {
			dao_NhanVien = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
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
		
		setTitle("XÁC NHẬN TÀI KHOẢN");
		setSize(350, 180);
		setResizable(false);
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
		pnlContentPane.setBounds(15, 0, 410, 365);
		pnlContentPane.setBackground(new Color(248, 227, 182));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblMaNV = new JLabel("MÃ NHÂN VIÊN: ");
		lblMaNV.setBounds(25, 20, 120, 20);
		lblMaNV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblMaNV);

		txtMaNV = new JTextField();
		txtMaNV.setBounds(150, 15, 160, 30);
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtMaNV);

		JLabel lblCMND = new JLabel("CMND/CCCD: ");
		lblCMND.setBounds(25, 55, 120, 20);
		lblCMND.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblCMND);

		txtCMND = new JTextField();
		txtCMND.setBounds(150, 50, 160, 30);
		txtCMND.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCMND);


		btnXacNhan = new JButton("XÁC NHẬN TÀI KHOẢN");
		btnXacNhan.setBounds(60, 90, 220, 42);
		btnXacNhan.setForeground(Color.WHITE);
		btnXacNhan.setBackground(new Color(131, 77, 30));
		btnXacNhan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnXacNhan.setFocusPainted(false);
		btnXacNhan.setIcon(new ImageIcon("image/matkhau.png"));
		pnlContentPane.add(btnXacNhan);
		
		txtMaNV.addKeyListener(this);
		txtCMND.addKeyListener(this);
		btnXacNhan.addActionListener(new ActionListener() {
			
			private NhanVien nv;

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String maNV = txtMaNV.getText().trim();
				String cmnd = txtCMND.getText().trim();
				
				if (maNV.equals("") || cmnd.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
					return;
				}
				
				
				try {

					List<NhanVien> list = dao_NhanVien.getAllNhanVien();
					for (NhanVien nhanVien : list) {
						if (nhanVien.getTaiKhoan().getTenTaiKhoan().getMaNV().equals(maNV)) {
							nv = nhanVien;
						}
					}
					if(nv != null) {
						if (!(maNV.equals(nv.getMaNV().trim()))) {
							JOptionPane.showMessageDialog(null, "Mã nhân viên không chính xác");
							return;
						}
						if (!(cmnd.equals(nv.getCmnd().trim()))) {
							JOptionPane.showMessageDialog(null, "Chứng minh nhân dân không chính xác");
							return;
						}
						JOptionPane.showMessageDialog(null, "Xác nhận thành công");
						new FrameDatLaiMatKhau(nv).setVisible(true);
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Mã nhân viên không chính xác");
						return;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnXacNhan.doClick();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

//	public static void main(String[] args) {
//		new FrameXacNhanTaiKhoan().setVisible(true);
//	}

}
