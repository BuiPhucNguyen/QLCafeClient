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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import dao.DAO_NhanVien;
import dao.DAO_TaiKhoan;
import entity.NhanVien;
import entity.TaiKhoan;

public class FormThemNV extends JFrame implements KeyListener {
	private JTextField txtTenNV;
	private JComboBox<String> cmbGioiTinh;
	private JTextField txtCmnd;
	private JTextField txtSdt;
	private JComboBox<String> cmbChucVu;
	private JTextField txtLuong;
	private JButton btnThem;
	private JDateChooser txtNgaySinh;
	private static DAO_NhanVien dao_NhanVien;
	private static DAO_TaiKhoan dao_TaiKhoan;

	public FormThemNV() throws RemoteException {
		try {
			dao_NhanVien = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
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
		
		// Xác định đăng nhập

		setTitle("THÊM NHÂN VIÊN");
		setSize(570, 360);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(252, 242, 217));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenNV = new JLabel("HỌ TÊN: ");
		lblTenNV.setBounds(18, 36, 120, 20);
		lblTenNV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenNV);
		txtTenNV = new JTextField("");
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTenNV.setBounds(118, 28, 150, 30);
		pnlContentPane.add(txtTenNV);

		JLabel lblGioitinh = new JLabel("GIỚI TÍNH: ");
		lblGioitinh.setBounds(18, 92, 120, 20);
		lblGioitinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGioitinh);
		String[] gioitinh = { "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioitinh);
		cmbGioiTinh.setBounds(118, 85, 150, 30);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbGioiTinh);

		JLabel lblCmnd = new JLabel("CMND/CCCD: ");
		lblCmnd.setBounds(18, 147, 120, 20);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblCmnd);
		txtCmnd = new JTextField("");
		txtCmnd.setBounds(118, 139, 150, 30);
		txtCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCmnd);

		JLabel lblNgaysinh = new JLabel("NGÀY SINH: ");
		lblNgaysinh.setBounds(295, 36, 120, 20);
		lblNgaysinh.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblNgaysinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(385, 30, 150, 30);
		txtNgaySinh.setDate(new Date(1999 - 1900, 1 - 1, 1));
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtNgaySinh);

		JLabel lblSdt = new JLabel("SĐT :");
		lblSdt.setBounds(295, 92, 120, 14);
		lblSdt.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblSdt);
		txtSdt = new JTextField("");
		txtSdt.setBounds(385, 84, 150, 30);
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtSdt);

		JLabel lblChucvu = new JLabel("CHỨC VỤ: ");
		lblChucvu.setBounds(295, 148, 120, 20);
		lblChucvu.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblChucvu);
		String[] chucvu = { "Quản lý", "Nhân viên" };
		cmbChucVu = new JComboBox<String>(chucvu);

		cmbChucVu.setBounds(385, 140, 150, 30);
		cmbChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbChucVu.setSelectedIndex(1);
		pnlContentPane.add(cmbChucVu);

		JLabel lblLuong = new JLabel("LƯƠNG: ");
		lblLuong.setBounds(18, 204, 120, 14);
		lblLuong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblLuong);
		txtLuong = new JTextField("");
		txtLuong.setBounds(118, 196, 150, 30);
		txtLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtLuong);

		btnThem = new JButton("THÊM NHÂN VIÊN MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(165, 260, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(131, 77, 30));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);

		txtCmnd.addKeyListener(this);
		txtLuong.addKeyListener(this);
		txtNgaySinh.addKeyListener(this);
		txtSdt.addKeyListener(this);
		txtTenNV.addKeyListener(this);
		cmbChucVu.addKeyListener(this);
		cmbGioiTinh.addKeyListener(this);

		btnThem.addActionListener(new ActionListener() {

			private List<NhanVien> listNV = new ArrayList<NhanVien>();
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!validInput()) {
					return;
				} else {
					String maNV = null;
					String maNVCuoi = null;
					
					String chucVu = cmbChucVu.getSelectedItem().toString();
					
					try {
						
						listNV = dao_NhanVien.getAllNhanVienTheoChucVụ(chucVu);
						System.out.println(listNV.size());
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					
					if (chucVu.equalsIgnoreCase("Nhân viên")) {
						if (listNV.size() == 0)
							maNV = "NV1001";
						else {
							maNVCuoi = listNV.get(listNV.size() - 1).getMaNV().trim();
							int layMaSo = Integer.parseInt(maNVCuoi.substring(2, maNVCuoi.length()));
							maNV = "NV" + (layMaSo + 1);
						}
					} 
					if(chucVu.equalsIgnoreCase("Quản lý")){
						if (listNV.size() == 0)
							maNV = "QL1001";
						else {
							maNVCuoi = listNV.get(listNV.size() - 1).getMaNV().trim();
							int layMaSo = Integer.parseInt(maNVCuoi.substring(2, maNVCuoi.length()));
							maNV = "QL" + (layMaSo + 1);
						}
					}

					String tenNV = txtTenNV.getText();
					String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
					String cmnd = txtCmnd.getText();
					String sdt = txtSdt.getText();

					double luong = Double.parseDouble(txtLuong.getText());
					Date ngaySinh = txtNgaySinh.getDate();
					java.sql.Date date = new java.sql.Date(ngaySinh.getYear(), ngaySinh.getMonth(), ngaySinh.getDate());

					NhanVien nv = new NhanVien(maNV, tenNV, sdt, cmnd, gioiTinh == "Nam" ? true : false, date, chucVu,
							luong);
					TaiKhoan tk = new TaiKhoan(nv, "123");

					try {
						dao_NhanVien.themNhanVien(nv);
						dao_TaiKhoan.themTaiKhoan(tk);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Thêm thành công!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);

					FrameNhanVien.xoaHetDL();
					try {
						FrameNhanVien.docDuLieuDatabaseVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int itemCount = FrameNhanVien.cmbTenNV.getItemCount();
					for (int i = 0; i < itemCount; i++) {
						FrameNhanVien.cmbTenNV.removeItemAt(0);
					}
					int itemCount2 = FrameNhanVien.cmbMaNV.getItemCount();
					for (int i = 0; i < itemCount2; i++) {
						FrameNhanVien.cmbMaNV.removeItemAt(0);
					}
					int itemCount3 = FrameNhanVien.cmbCmnd.getItemCount();
					for (int i = 0; i < itemCount3; i++) {
						FrameNhanVien.cmbCmnd.removeItemAt(0);
					}
					int itemCount4 = FrameNhanVien.cmbSdt.getItemCount();
					for (int i = 0; i < itemCount4; i++) {
						FrameNhanVien.cmbSdt.removeItemAt(0);
					}
					try {
						FrameNhanVien.docDuLieuVaoCmbMaNV();
						FrameNhanVien.docDuLieuVaoCmbTenNV();
						FrameNhanVien.docDuLieuVaoCmbCmnd();
						FrameNhanVien.docDuLieuVaoCmbSdt();
						FrameNhanVien.table.getSelectionModel().clearSelection();
						FrameNhanVien.lamMoiDL();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					dispose();
				}
			}
		});

		pnlContentPane.add(btnThem);

	}

	private boolean validInput() {
		String tenNV = txtTenNV.getText();
		Date ngaySinh = txtNgaySinh.getDate();
		String cmnd = txtCmnd.getText();
		String sdt = txtSdt.getText();
		String luong = txtLuong.getText();
		if (tenNV.trim().length() > 0) {
			if (!(tenNV.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên nhân viên không chứa ký tự đặc biệt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayHienTai.getYear() - ngaySinh.getYear() < 18) {
				JOptionPane.showMessageDialog(this, "Nhân viên chưa đủ 18 tuổi", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if (cmnd.trim().length() > 0) {
			if (!(cmnd.matches("[0-9]{9}")) && !(cmnd.matches("[0-9]{12}"))) {
				JOptionPane.showMessageDialog(this, "CMND phải gồm 9 hoặc 12 số", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "CMND không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (sdt.trim().length() > 0) {
			if (!(sdt.matches("[0-9]{10,11}"))) {
				JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 đến 11 số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (luong.trim().length() > 0) {
			try {
				double x = Double.parseDouble(luong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Lương phải nhập số", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Lương không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
			btnThem.doClick();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
