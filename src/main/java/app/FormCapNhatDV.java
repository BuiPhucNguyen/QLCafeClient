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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DAO_DichVu;
import entity.Nuoc;


public class FormCapNhatDV extends JFrame implements  KeyListener {
	private JTextField txtTenDV;
	private JTextField txtGiaDV;
	private JComboBox<String> cmbDonVi;
	private JButton btnCapNhat;
	private static DAO_DichVu dao_DichVu;

	public FormCapNhatDV() throws RemoteException {
		try {
			dao_DichVu = (DAO_DichVu) Naming.lookup("rmi://192.168.101.35:9999/dao_DichVu");
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
		// --------------
		setTitle("CẬP NHẬT DỊCH VỤ");
		setSize(380, 300);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(252, 242, 217));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenDV = new JLabel("TÊN DỊCH VỤ :");
		lblTenDV.setBounds(50, 36, 150, 20);
		lblTenDV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenDV);
		txtTenDV = new JTextField("Bia");
		txtTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTenDV.setBounds(160, 28, 150, 30);
		pnlContentPane.add(txtTenDV);

		JLabel lblGia = new JLabel("GIÁ TIỀN :");
		lblGia.setBounds(50, 90, 150, 20);
		lblGia.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGia);
		txtGiaDV = new JTextField("100000");
		txtGiaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGiaDV.setBounds(160, 82, 150, 30);
		pnlContentPane.add(txtGiaDV);

		JLabel lblLoai = new JLabel("TRẠNG THÁI: ");
		lblLoai.setBounds(50, 144, 150, 14);
		lblLoai.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblLoai);
		String[] loai = { "Còn món", "Hết món"};
		cmbDonVi = new JComboBox<String>(loai);
		cmbDonVi.setBounds(160, 136, 150, 30);
		cmbDonVi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbDonVi);

		

		btnCapNhat = new JButton("CẬP NHẬT DỊCH VỤ", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(70, 190, 220, 45);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBackground(new Color(131, 77, 30));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		int row = FrameDichVu.table.getSelectedRow();
		txtTenDV.setText(FrameDichVu.tableModel.getValueAt(row, 1).toString());
		cmbDonVi.setSelectedIndex(FrameDichVu.tableModel.getValueAt(row, 3).toString()=="Còn món"?0:1);
		String gia[] = FrameDichVu.tableModel.getValueAt(row, 2).toString().split(",");
		String giaTien = "";
		for (int i = 0; i < gia.length; i++)
			giaTien += gia[i];
		txtGiaDV.setText(giaTien);
		

		txtGiaDV.addKeyListener(this);
		txtTenDV.addKeyListener(this);
		
		btnCapNhat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!validInput() || trungTen()) {
						return;
					} else {
						int row = FrameDichVu.table.getSelectedRow();
						String maDV = FrameDichVu.tableModel.getValueAt(row, 0).toString();
						String tenDV = txtTenDV.getText();
						double giaDV = Double.parseDouble(txtGiaDV.getText());
						Boolean trangThai = cmbDonVi.getSelectedIndex()==0?true:false;
						Nuoc dv = new Nuoc(maDV, tenDV, giaDV, trangThai);
						dao_DichVu.capnhatDichVu(dv);

						FrameDichVu.xoaHetDL();
						FrameDichVu.docDuLieuDatabaseVaoTable();
						int itemCount = FrameDichVu.cmbTenDV.getItemCount();
						for (int i = 0; i < itemCount; i++) {
							FrameDichVu.cmbTenDV.removeItemAt(0);
						}
						FrameDichVu.docDuLieuVaoCmbTen();
						FrameDichVu.table.getSelectionModel().clearSelection();

						dispose();
					}
				} catch (NumberFormatException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}

	private boolean trungTen() throws RemoteException {
		int row = FrameDichVu.table.getSelectedRow();
		String tenHienTai = FrameDichVu.tableModel.getValueAt(row, 1).toString().trim();
		String tenDV = txtTenDV.getText().trim();
		List<Nuoc> listPhong = dao_DichVu.getAllDichVu();
		for (Nuoc dv : listPhong) {
			if (dv.getTenNuoc().trim().equalsIgnoreCase(tenDV)) {
				if (dv.getTenNuoc().trim().equalsIgnoreCase(tenHienTai)) {
					return false;
				}
				JOptionPane.showMessageDialog(this, "Dịch vụ này đã có trong danh sách");
				return true;
			}
		}
		return false;
	}

	private boolean validInput() {
		String tenDV = txtTenDV.getText();
		String giaDV = txtGiaDV.getText();
		if (tenDV.trim().length() > 0) {
			if (!(tenDV.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên dịch vụ không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên dịch vụ không được để trống");
			return false;
		}
		if (giaDV.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaDV);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá dịch vụ phải lớn hơn 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá dịch vụ phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá dịch vụ không được để trống");
			return false;
		}
		
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnCapNhat.doClick();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
