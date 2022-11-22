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

import dao.DAO_Ban;
import entity.Ban;


public class FormCapNhatBan extends JFrame implements KeyListener {
	private JTextField txtTenBan;
	private JButton btnCapNhat;
	private static DAO_Ban dao_Ban;

	public FormCapNhatBan() throws RemoteException {
		try {
			dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
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
		// ------------------
		setTitle("CẬP NHẬT BÀN");
		setSize(380, 170);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(252, 242, 217));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTen = new JLabel("TÊN BÀN:");
		lblTen.setBounds(50, 36, 150, 20);
		lblTen.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTen);
		txtTenBan = new JTextField("");
		txtTenBan.setBounds(160, 28, 150, 30);
		txtTenBan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenBan);

		

		btnCapNhat = new JButton("CẬP NHẬT BÀN", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(70, 70, 220, 45);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBackground(new Color(131, 77, 30));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		int row = FrameBan.table.getSelectedRow();
		txtTenBan.setText(FrameBan.tableModel.getValueAt(row, 1).toString().trim());

		
		txtTenBan.addKeyListener(this);
		btnCapNhat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!validInput() || trungTen()) {
						return;
					} else {
						
						int row = FrameBan.table.getSelectedRow();
						String maBan = FrameBan.tableModel.getValueAt(row, 0).toString();
						String tenBen = txtTenBan.getText();

						Ban b = new Ban(maBan, tenBen.trim(), false);
						dao_Ban.capnhatBan(b);

						FrameBan.xoaHetDL();
						FrameBan.docDuLieuDatabaseVaoTable();
						int itemCount = FrameBan.cmbTenPhong.getItemCount();
						for (int i = 0; i < itemCount; i++) {
							FrameBan.cmbTenPhong.removeItemAt(0);
						}
						int itemCount2 = FrameBan.cmbMaPhong.getItemCount();
						for (int i = 0; i < itemCount2; i++) {
							FrameBan.cmbMaPhong.removeItemAt(0);
						}
						FrameBan.docDuLieuVaoCmbMaBan();
						FrameBan.docDuLieuVaoCmbTenBan();
						FrameBan.table.getSelectionModel().clearSelection();

						dispose();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	private boolean trungTen() throws RemoteException {
		int row = FrameBan.table.getSelectedRow();
		String tenHienTai = FrameBan.tableModel.getValueAt(row, 1).toString().trim();
		String tenBan = txtTenBan.getText().trim();
		List<Ban> list = dao_Ban.getAllBan();
		for (Ban b : list) {
			if(b.getTenBan().trim().equalsIgnoreCase(tenBan)) {
				if (b.getTenBan().trim().equalsIgnoreCase(tenHienTai)) {
					return false;
				}
				JOptionPane.showMessageDialog(this, "Bàn này đã có trong danh sách", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}
	private boolean validInput() {
		String tenBan = txtTenBan.getText();
		
		if (tenBan.trim().length() > 0) {
			if (!(tenBan.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên phòng không chứa ký tự đặc biệt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên phòng không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
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
