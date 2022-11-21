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
import dao.DAO_Ban;
import entity.Ban;


public class FormThemBan extends JFrame implements KeyListener{
	private JButton btnThem;
	private JTextField txtTenBan;
	private static DAO_Ban dao_Ban;

	public FormThemBan() throws RemoteException {
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
		setTitle("THÊM BÀN");
		setSize(380, 170);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(252, 242, 217));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenPhong = new JLabel("TÊN BÀN :");
		lblTenPhong.setBounds(50, 36, 150, 20);
		lblTenPhong.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblTenPhong);
		txtTenBan = new JTextField("");
		txtTenBan.setBounds(160, 28, 150, 30);
		txtTenBan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenBan);

		btnThem = new JButton("THÊM BÁN MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(70, 70, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(131, 77, 30));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!validInput() || trungTen()) {
						return;
					} else {
						try {
							
							String maBan;
							List<Ban> list = dao_Ban.getAllBan();
							if (list.size() == 1)
								maBan = "B1001";
							else {
								String maPHCuoi = list.get(list.size() - 2).getMaBan().trim();
								int layMaSo = Integer.parseInt(maPHCuoi.substring(1, maPHCuoi.length()));
								maBan = "B" + (layMaSo + 1);
							}
							String tenBan = txtTenBan.getText();
							
							Ban b = new Ban(maBan, tenBan, false);
							dao_Ban.themBan(b);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						FrameBan.xoaHetDL();
						try {
							FrameBan.docDuLieuDatabaseVaoTable();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
				} catch (NumberFormatException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		txtTenBan.addKeyListener(this);
	}


	public static void main(String[] args) throws RemoteException {
		new FormThemBan().setVisible(true);
	}
	private boolean trungTen() throws RemoteException {
		List<Ban> list = dao_Ban.getAllBan();
		String tenBan = txtTenBan.getText();
		for (Ban b : list) {
			if(b.getTenBan().trim().equalsIgnoreCase(tenBan)) {
				JOptionPane.showMessageDialog(this, "Bàn này đã có trong danh sách");
				return true;
			}
		}
		return false;
	}
	private boolean validInput() {
		String tenPhong = txtTenBan.getText();
		if (tenPhong.trim().length() > 0) {
			if (!(tenPhong.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên Bàn không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên phòng không được để trống");
			return false;
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnThem.doClick();
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
