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

import dao.DAO_DichVu;
import entity.Nuoc;


public class FormThemDV extends JFrame implements KeyListener {
	private JButton btnThem;
	private JTextField txtTenDV;
	private JTextField txtGiaDV;
	private static DAO_DichVu dao_DichVu;
	

	public FormThemDV() throws RemoteException {
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
		//--------------
		setTitle("THÊM DỊCH VỤ");
		setSize(380, 240);
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
		txtTenDV = new JTextField("");
		txtTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTenDV.setBounds(160, 28, 150, 30);
		pnlContentPane.add(txtTenDV);

		JLabel lblGiaDV = new JLabel("GIÁ TIỀN :");
		lblGiaDV.setBounds(50, 90, 150, 20);
		lblGiaDV.setFont(new Font("Arial", Font.BOLD, 13));
		pnlContentPane.add(lblGiaDV);
		txtGiaDV = new JTextField("");
		txtGiaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGiaDV.setBounds(160, 82, 150, 30);
		pnlContentPane.add(txtGiaDV);

		btnThem = new JButton("THÊM DỊCH VỤ MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(70, 130, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(131, 77, 30));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);
		
		
		
		txtGiaDV.addKeyListener(this);
		txtTenDV.addKeyListener(this);
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!validInput() || trungTen()) {
						return;
					} else {
						String maDV;
						List<Nuoc> listDV = dao_DichVu.getAllDichVu();
						if(listDV.size() == 0)
							maDV = "DV1001";
						else {
							String maDVCuoi = listDV.get(listDV.size()-1).getMaNuoc().trim();
							int layMaSo = Integer.parseInt(maDVCuoi.substring(2, maDVCuoi.length()));
							maDV = "DV" + (layMaSo + 1);
						}
						String tenDV = txtTenDV.getText();
						double giaDV = Double.parseDouble(txtGiaDV.getText());
						
						Nuoc dv = new Nuoc(maDV, tenDV, giaDV, true);
						dao_DichVu.themDichVu(dv);
						
						FrameDichVu.xoaHetDL();
						FrameDichVu.docDuLieuDatabaseVaoTable();
						int itemCount = FrameDichVu.cmbTenDV.getItemCount();
						for (int i = 0; i < itemCount; i++) {
							FrameDichVu.cmbTenDV.removeItemAt(0);
						}
						int itemCount2 = FrameDichVu.cmbTenDV.getItemCount();
						for (int i = 0; i < itemCount2; i++) {
							FrameDichVu.cmbMaDV.removeItemAt(0);
						}
						FrameDichVu.docDuLieuVaoCmbTen();
						FrameDichVu.docDuLieuVaoCmbMaDV();
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
		List<Nuoc> listDV = dao_DichVu.getAllDichVu();
		String tenDV = txtTenDV.getText();
		for (Nuoc dv : listDV) {
			if(dv.getTenNuoc().trim().equalsIgnoreCase(tenDV)) {
				JOptionPane.showMessageDialog(this, "Dịch vụ này đã có trong danh sách", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(this, "Tên dịch vụ không chứa ký tự đặc biệt", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên dịch vụ không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (giaDV.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaDV);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá dịch vụ phải lớn hơn 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá dịch vụ phải nhập số", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá dịch vụ không được để trống", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
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
