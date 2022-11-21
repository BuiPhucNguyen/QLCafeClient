package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import dao.DAO_Ban;
import dao.DAO_CTHD;
import dao.DAO_DichVu;
import dao.DAO_HoaDon;
import dao.DAO_NhanVien;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhanVien;
import entity.Nuoc;

public class FrameDatBan extends JFrame {
	public static JTextField txtTenKH;
	public static JTextField txtCmnd;
	public static JTextField txtLienLac;
	public static DefaultTableModel tableModelPhongTrong;
	public static JTable tablePhongTrong;
	public static String maPhongMoiDat = "";
	public static String maHDMoiDat = "";
	public static String maKHDatPhong = "";
	private JButton btnLamMoi;
	public static JDateChooser txtNgaySinh;
	public static JComboBox<String> cmbDanhSachSdt;
	public static JComboBox<String> cmbGioiTinh;
	public static JComboBox<String> cmbTimKhachHang;
	public static DefaultTableModel tableModelListDV;
	public static JTable tableListDV;
	public static DefaultTableModel tableModelHoaDonDV;
	public static JTable tableHoaDonDV;
	private static JComboBox<String> cmbDanhSachDV;
	private JButton btnDatDichVu;
	private JButton btnHuyDichVu;
	public static JTextField txtSoLuong;
	public static JLabel lblBanDangChon;
	private JLabel lblMuaMangVe;
	public static JCheckBox chkMangVe;
	public static DefaultTableModel tableModelBanDaDat;
	public static JTable tableBanDaDat;
	private JButton btnDatBan;
	private JButton btnHuyBan;
	private JButton btnTimDV;
	private JButton btnCapNhapSL;
	private JButton btnDoiBan;
	
	private static DAO_HoaDon dao_HD;
	private static DAO_NhanVien dao_NV;
	private static DAO_CTHD dao_CTHD;
	private static DAO_Ban dao_Ban;
	private static DAO_DichVu dao_DichVu;

	public JPanel createPanelDatPhong() throws RemoteException {
		
		try {
			dao_HD = (DAO_HoaDon) Naming.lookup("rmi://192.168.101.35:9999/dao_HoaDon");
			dao_NV = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
			dao_CTHD = (DAO_CTHD) Naming.lookup("rmi://192.168.101.35:9999/dao_CTHD");
			dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
			dao_DichVu = (DAO_DichVu) Naming.lookup("rmi://192.168.101.35:9999/dao_DichVu");
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
		

		setTitle("ĐẶT PHÒNG");
		setSize(948, 660);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(248, 227, 182));
		setContentPane(pnlContentPane);
		pnlContentPane.setLayout(null);

		/*
		 * Danh sách bàn trống
		 */
		JPanel pnlPhongTrong = new JPanel();
		pnlPhongTrong.setBounds(20, 15, 475, 275);
		pnlContentPane.add(pnlPhongTrong);
		pnlPhongTrong.setBackground(Color.WHITE);
		pnlPhongTrong.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH BÀN TRỐNG: "));
		pnlPhongTrong.setLayout(new GridLayout(1, 0, 0, 0));

		String[] header = { "Mã bàn", "Tên bàn", };
		tableModelPhongTrong = new DefaultTableModel(header, 0);
		tablePhongTrong = new JTable(tableModelPhongTrong) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tablePhongTrong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tablePhongTrong.setGridColor(getBackground());
		tablePhongTrong.setRowHeight(tablePhongTrong.getRowHeight() + 20);
		tablePhongTrong.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = tablePhongTrong.getTableHeader();
		tableHeader.setBackground(new Color(173, 119, 72));
		tableHeader.setForeground(new Color(255, 255, 255));
		tableHeader.setFont(new Font("Arial", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 40));
		tableHeader.setResizingAllowed(false);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

		pnlPhongTrong.add(new JScrollPane(tablePhongTrong));

		/*
		 * Danh sách bàn đã đặt
		 */
		JPanel pnlBanDaDat = new JPanel();
		pnlBanDaDat.setBounds(720, 15, 580, 275);
		pnlContentPane.add(pnlBanDaDat);
		pnlBanDaDat.setBackground(Color.WHITE);
		pnlBanDaDat.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"DANH SÁCH BÀN ĐÃ ĐẶT: "));
		pnlBanDaDat.setLayout(new GridLayout(1, 0, 0, 0));

		String[] header4 = { "Mã bàn", "Tên bàn", };
		tableModelBanDaDat = new DefaultTableModel(header4, 0);
		tableBanDaDat = new JTable(tableModelBanDaDat) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tableBanDaDat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableBanDaDat.setGridColor(getBackground());
		tableBanDaDat.setRowHeight(tableBanDaDat.getRowHeight() + 20);
		tableBanDaDat.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader4 = tableBanDaDat.getTableHeader();
		tableHeader4.setBackground(new Color(173, 119, 72));
		tableHeader4.setForeground(new Color(255, 255, 255));
		tableHeader4.setFont(new Font("Arial", Font.BOLD, 15));
		tableHeader4.setPreferredSize(new Dimension(WIDTH, 40));
		tableHeader4.setResizingAllowed(false);

		pnlBanDaDat.add(new JScrollPane(tableBanDaDat));
		// -----------------------------------------------------------------------------------------------------------------
		btnDatBan = new JButton("ĐẶT BÀN");
		btnDatBan.setBounds(510, 30, 200, 42);
		btnDatBan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDatBan.setIcon(new ImageIcon("image/datdichvu.png"));
		btnDatBan.setBackground(new Color(131, 77, 30));
		btnDatBan.setForeground(Color.WHITE);
		btnDatBan.setFocusPainted(false);
		pnlContentPane.add(btnDatBan);

		btnHuyBan = new JButton("HỦY ĐẶT BÀN");
		btnHuyBan.setBounds(510, 90, 200, 42);
		btnHuyBan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuyBan.setIcon(new ImageIcon("image/huydatphong.png"));
		btnHuyBan.setBackground(new Color(131, 77, 30));
		btnHuyBan.setForeground(Color.WHITE);
		btnHuyBan.setFocusPainted(false);
		pnlContentPane.add(btnHuyBan);
		
		btnDoiBan = new JButton("ĐỔI BÀN");
		btnDoiBan.setBounds(510, 150, 200, 42);
		btnDoiBan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDoiBan.setIcon(new ImageIcon("image/lammoi.png"));
		btnDoiBan.setBackground(new Color(131, 77, 30));
		btnDoiBan.setForeground(Color.WHITE);
		btnDoiBan.setFocusPainted(false);
		pnlContentPane.add(btnDoiBan);

		JPanel pnlMuaMangVe = new JPanel();
		pnlMuaMangVe.setLayout(null);
		pnlMuaMangVe.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlMuaMangVe.setBounds(510, 210, 200, 42);
		pnlMuaMangVe.setBackground(Color.WHITE);
		lblMuaMangVe = new JLabel("Mua mang về");
		lblMuaMangVe.setBounds(10, 5, 170, 33);
		lblMuaMangVe.setBackground(Color.WHITE);
		lblMuaMangVe.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblMuaMangVe.setForeground(Color.BLACK);
		pnlMuaMangVe.add(lblMuaMangVe);
		chkMangVe = new JCheckBox();
		chkMangVe.setBounds(150, 5, 33, 33);
		chkMangVe.setBackground(Color.WHITE);
		pnlMuaMangVe.add(chkMangVe);
		pnlContentPane.add(pnlMuaMangVe);
		// -----------------------------------------------------------------------------------------------------------------

		JPanel pnlPhongDaDat = new JPanel();
		pnlPhongDaDat.setBackground(new Color(248, 227, 182));
		pnlPhongDaDat.setBounds(20, 290, (int) (d.getWidth() - 250), (int) (d.getHeight() - 380));
		pnlPhongDaDat.setLayout(null);
		pnlContentPane.add(pnlPhongDaDat);

		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH DỊCH VỤ: "));
		pnlDanhSach.setBounds(0, 25, 475, 460);
		pnlDanhSach.setBackground(Color.WHITE);
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlPhongDaDat.add(pnlDanhSach);

		String[] header1 = { "Mã đồ uống", "Tên đồ uống", "Giá tiền" };
		tableModelListDV = new DefaultTableModel(header1, 0);
		tableListDV = new JTable(tableModelListDV) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tableListDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableListDV.setGridColor(getBackground());
		tableListDV.setRowHeight(tableListDV.getRowHeight() + 20);
		tableListDV.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader1 = tableListDV.getTableHeader();
		tableHeader1.setBackground(new Color(173, 119, 72));
		tableHeader1.setForeground(new Color(255, 255, 255));
		tableHeader1.setFont(new Font("Arial", Font.BOLD, 15));
		tableHeader1.setPreferredSize(new Dimension(WIDTH, 40));
		tableHeader1.setResizingAllowed(false);

		DefaultTableCellRenderer rightRenderer4 = new DefaultTableCellRenderer();
		rightRenderer4.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		tableListDV.getColumn("Giá tiền").setCellRenderer(rightRenderer4);

		pnlDanhSach.add(new JScrollPane(tableListDV));

		JPanel pnlDatDichVu = new JPanel();
		pnlDatDichVu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"HÓA ĐƠN DỊCH VỤ CỦA BÀN: "));
		pnlDatDichVu.setBounds(700, 25, 580, 460);
		pnlDatDichVu.setBackground(Color.WHITE);
		pnlDatDichVu.setLayout(new GridLayout(1, 0, 0, 0));
		pnlPhongDaDat.add(pnlDatDichVu);

		String[] header2 = { "Mã đồ uống", "Tên đồ uống", "Số lượng", "Giá tiền", "Thành tiền" };
		tableModelHoaDonDV = new DefaultTableModel(header2, 0);
		tableHoaDonDV = new JTable(tableModelHoaDonDV) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tableHoaDonDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableHoaDonDV.setGridColor(getBackground());
		tableHoaDonDV.setRowHeight(tableHoaDonDV.getRowHeight() + 20);
		tableHoaDonDV.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader2 = tableHoaDonDV.getTableHeader();
		tableHeader2.setBackground(new Color(173, 119, 72));
		tableHeader2.setForeground(new Color(255, 255, 255));
		tableHeader2.setFont(new Font("Arial", Font.BOLD, 15));
		tableHeader2.setPreferredSize(new Dimension(WIDTH, 40));
		tableHeader2.setResizingAllowed(false);

		pnlDatDichVu.add(new JScrollPane(tableHoaDonDV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		tableHoaDonDV.getColumnModel().getColumn(0).setPreferredWidth(20);
		tableHoaDonDV.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableHoaDonDV.getColumnModel().getColumn(2).setPreferredWidth(5);
		tableHoaDonDV.getColumnModel().getColumn(3).setPreferredWidth(5);
		tableHoaDonDV.getColumnModel().getColumn(4).setPreferredWidth(20);



		JPanel pnlCmbPhong = new JPanel();
		pnlCmbPhong.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "ĐANG CHỌN: "));
		pnlCmbPhong.setBounds(490, 32, 200, 50);
		pnlCmbPhong.setBackground(Color.WHITE);
		pnlCmbPhong.setLayout(new GridLayout(1, 0, 0, 0));
		lblBanDangChon = new JLabel("");
		lblBanDangChon.setBounds(0, 0, 170, 33);
		lblBanDangChon.setBackground(Color.WHITE);
		lblBanDangChon.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblBanDangChon.setForeground(new Color(131, 77, 30));
		pnlCmbPhong.add(lblBanDangChon);
		pnlPhongDaDat.add(pnlCmbPhong);

		JPanel pnlCmbDV = new JPanel();
		pnlCmbDV.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"NHẬP TÊN DỊCH VỤ CẦN TÌM: "));
		pnlCmbDV.setBounds(490, 92, 200, 50);
		pnlCmbDV.setBackground(Color.WHITE);
		pnlCmbDV.setLayout(new GridLayout(1, 0, 0, 0));
		cmbDanhSachDV = new JComboBox<String>();
		cmbDanhSachDV.setBounds(0, 0, 170, 33);
		cmbDanhSachDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbDanhSachDV.setBackground(Color.WHITE);
		cmbDanhSachDV.setEditable(true);
		AutoCompleteDecorator.decorate(cmbDanhSachDV);
		pnlCmbDV.add(cmbDanhSachDV);
		pnlPhongDaDat.add(pnlCmbDV);

		JLabel lblSoLuong = new JLabel("NHẬP SỐ LƯỢNG: ");
		lblSoLuong.setBounds(490, 150, 220, 42);
		lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 13));
		pnlPhongDaDat.add(lblSoLuong);
		txtSoLuong = new JTextField("1");
		txtSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoLuong.setBounds(610, 155, 40, 30);
		pnlPhongDaDat.add(txtSoLuong);
		btnCapNhapSL = new JButton("");
		btnCapNhapSL.setBounds(660, 155, 30, 30);
		btnCapNhapSL.setIcon(new ImageIcon("image/capnhat.png"));
		btnCapNhapSL.setBackground(new Color(131, 77, 30));
		btnCapNhapSL.setForeground(Color.WHITE);
		btnCapNhapSL.setFocusPainted(false);
		pnlPhongDaDat.add(btnCapNhapSL);

		// Button
		btnTimDV = new JButton("TÌM DỊCH VỤ");
		btnTimDV.setBounds(490, 200, 200, 42);
		btnTimDV.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTimDV.setIcon(new ImageIcon("image/timkiem.png"));
		btnTimDV.setBackground(new Color(131, 77, 30));
		btnTimDV.setForeground(Color.WHITE);
		btnTimDV.setFocusPainted(false);
		pnlPhongDaDat.add(btnTimDV);

		btnDatDichVu = new JButton("ĐẶT DỊCH VỤ");
		btnDatDichVu.setBounds(490, 260, 200, 42);
		btnDatDichVu.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDatDichVu.setIcon(new ImageIcon("image/datdichvu.png"));
		btnDatDichVu.setBackground(new Color(131, 77, 30));
		btnDatDichVu.setForeground(Color.WHITE);
		btnDatDichVu.setFocusPainted(false);
		pnlPhongDaDat.add(btnDatDichVu);

		btnHuyDichVu = new JButton("HỦY DỊCH VỤ");
		btnHuyDichVu.setBounds(490, 320, 200, 42);
		btnHuyDichVu.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuyDichVu.setIcon(new ImageIcon("image/huydatphong.png"));
		btnHuyDichVu.setBackground(new Color(131, 77, 30));
		btnHuyDichVu.setForeground(Color.WHITE);
		btnHuyDichVu.setFocusPainted(false);
		pnlPhongDaDat.add(btnHuyDichVu);

		btnLamMoi = new JButton("LÀM MỚI");
		btnLamMoi.setBounds(490, 380, 200, 42);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLamMoi.setIcon(new ImageIcon("image/lammoi.png"));
		btnLamMoi.setBackground(new Color(131, 77, 30));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlPhongDaDat.add(btnLamMoi);

		tableHoaDonDV.setDefaultEditor(Object.class, null);
		tableListDV.setDefaultEditor(Object.class, null);
		tableHoaDonDV.getTableHeader().setReorderingAllowed(false);
		tableListDV.getTableHeader().setReorderingAllowed(false);
		tablePhongTrong.setDefaultEditor(Object.class, null);
		tablePhongTrong.getTableHeader().setReorderingAllowed(false);

		docDuLieuDatabaseVaoTableBan();
		docDuLieuDatabaseVaoTableBanDaDat();
		docDuLieuDatabaseVaoTableDV();
		docDLCmbDV();
		tableBanDaDat.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (tableBanDaDat.isEnabled()) {
					
					try {
						int r = tableBanDaDat.getSelectedRow();
						lblBanDangChon.setText(tableModelBanDaDat.getValueAt(r, 1).toString());

						DefaultTableModel dm = (DefaultTableModel) tableHoaDonDV.getModel();
						int rowCount = dm.getRowCount();
						// Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--) {
							dm.removeRow(i);
						}

						docDuLieuDatabaseVaoTableHD(tableModelBanDaDat.getValueAt(r, 1).toString());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		chkMangVe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chkMangVe.isSelected()) {
					lblBanDangChon.setText("MUA MANG VỀ");
					DefaultTableModel dm = (DefaultTableModel) tableHoaDonDV.getModel();
					int rowCount = dm.getRowCount();
					// Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
						dm.removeRow(i);
					}
					tableBanDaDat.clearSelection();
					tablePhongTrong.clearSelection();
					tablePhongTrong.enable(false);
					tableBanDaDat.enable(false);
					tableListDV.clearSelection();
					String maHB="";
					List<HoaDon> list;
					try {
						list = dao_HD.getAllHD();
						Ban b = new Ban("B9999");
						NhanVien nv_temp = FrameDangNhap.getTaiKhoan().getTenTaiKhoan();
						NhanVien nv = dao_NV.getNhanVienTheoMa(nv_temp.getMaNV());
						if (list.size() == 0)
							maHB = "HD1001";
						else {
							String maPHCuoi = list.get(list.size() - 1).getMaHD().trim();
							int layMaSo = Integer.parseInt(maPHCuoi.substring(2, maPHCuoi.length()));
							maHB = "HD" + (layMaSo + 1);
						}
						HoaDon hd = new HoaDon(maHB, nv, b, false, new Date());
						dao_HD.themHD(hd);
						b.setTenBan("Mua mang về");
						b.setTrangThai(true);
						dao_Ban.capnhatBan(b);
						
						FrameThanhToan.XoaDLTableBanDaDat();
						FrameThanhToan.docDuLieuDatabaseVaoTableBanDaDat();
						FrameThanhToan.docDLCmbBan();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else {
					lblBanDangChon.setText("");
					tablePhongTrong.enable(true);
					tableBanDaDat.enable(true);
					
					try {
						List<HoaDon> list = dao_HD.getHDChuaThanhToan();
						HoaDon hd_temp = null;
						for (HoaDon hd : list) {
							if (hd.getMaBan().getMaBan().equalsIgnoreCase("B9999")) {
								hd_temp = hd;
								break;
							}
						}
						if (dao_HD.xoaHD(hd_temp.getMaHD())) {
							Ban b = new Ban("B9999", "Mua mang về", false);
							dao_Ban.capnhatBan(b);
							FrameThanhToan.XoaDLTableBanDaDat();
							FrameThanhToan.docDuLieuDatabaseVaoTableBanDaDat();
							FrameThanhToan.docDLCmbBan();
						}
						
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});

		btnLamMoi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel dm = (DefaultTableModel) tableHoaDonDV.getModel();
				int rowCount = dm.getRowCount();
				// Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
					dm.removeRow(i);
				}

				XoaDLTableDV();
				
				if (!chkMangVe.isSelected()) {
					lblBanDangChon.setText("");
				}
				txtSoLuong.setText("1");
				cmbDanhSachDV.setSelectedIndex(0);

				tableBanDaDat.clearSelection();
				tablePhongTrong.clearSelection();
				tableListDV.clearSelection();

				try {

					docDuLieuDatabaseVaoTableDV();

				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnDatBan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = tablePhongTrong.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn cần đặt!");
					return;
				}

				String maBan = tableModelPhongTrong.getValueAt(r, 0).toString();
				String tenBan = tableModelPhongTrong.getValueAt(r, 1).toString();

				Ban b = new Ban(maBan, tenBan, true);

				String maHB;
				List<HoaDon> list;

				try {
					NhanVien nv_temp = FrameDangNhap.getTaiKhoan().getTenTaiKhoan();
					NhanVien nv = dao_NV.getNhanVienTheoMa(nv_temp.getMaNV());

					list = dao_HD.getAllHD();
					if (list.size() == 0)
						maHB = "HD1001";
					else {
						String maPHCuoi = list.get(list.size() - 1).getMaHD().trim();
						int layMaSo = Integer.parseInt(maPHCuoi.substring(2, maPHCuoi.length()));
						maHB = "HD" + (layMaSo + 1);
					}
					HoaDon hd = new HoaDon(maHB, nv, b, false, new Date());
					

					try {
						if (dao_Ban.capnhatBan(b) && dao_HD.themHD(hd)) {
							tableModelPhongTrong.removeRow(r);
							XoaDLTableBanDaDat();
							docDuLieuDatabaseVaoTableBanDaDat();
							
							FrameThanhToan.XoaDLTableBanDaDat();
							FrameThanhToan.docDuLieuDatabaseVaoTableBanDaDat();
							FrameThanhToan.docDLCmbBan();
							
							JOptionPane.showMessageDialog(null, "Đặt bàn thành công!");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});

		btnHuyBan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = tableBanDaDat.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn cần hủy đặt!");
					return;
				}
				String maBan = tableModelBanDaDat.getValueAt(r, 0).toString();
				String tenBan = tableModelBanDaDat.getValueAt(r, 1).toString();
				Ban b = new Ban(maBan, tenBan, false);

				try {
					List<HoaDon> list = dao_HD.getHDChuaThanhToan();
					HoaDon hd_temp = null;
					for (HoaDon hd : list) {
						if (hd.getMaBan().getMaBan().equalsIgnoreCase(maBan)) {
							hd_temp = hd;
							break;
						}
					}
					try {
						if (dao_HD.xoaHD(hd_temp.getMaHD())) {
							JOptionPane.showMessageDialog(null, "Hủy đặt bàn thành công!");
							if (dao_Ban.capnhatBan(b)) {
								tableModelBanDaDat.removeRow(r);

								XoaDLTableBan();
								docDuLieuDatabaseVaoTableBan();

								lblBanDangChon.setText("");
								
								FrameThanhToan.XoaDLTableBanDaDat();
								FrameThanhToan.docDuLieuDatabaseVaoTableBanDaDat();
								FrameThanhToan.docDLCmbBan();
							}
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Chỉ bàn trống mới được hủy!");
					}
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Chỉ bàn trống mới được hủy!");
				}
			}
		});
		
		btnDoiBan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int vitri_banDaDat = tableBanDaDat.getSelectedRow();
				if (vitri_banDaDat == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn cần đổi!");
					return;
				}
				
				int vitri_banTrong = tablePhongTrong.getSelectedRow();
				if (vitri_banTrong == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn trống cần đổi qua!");
					return;
				}
				
				String maBanTrong = tableModelPhongTrong.getValueAt(vitri_banTrong, 0).toString();
				String tenBanTrong = tableModelPhongTrong.getValueAt(vitri_banTrong, 1).toString();
				
				String maBanDaDat = tableModelBanDaDat.getValueAt(vitri_banDaDat, 0).toString();
				String tenBanDaDat = tableModelBanDaDat.getValueAt(vitri_banDaDat, 1).toString();
				String maHD = "";
				try {
					List<HoaDon> list = dao_HD.getHDChuaThanhToan();
					for (HoaDon hd_temp : list) {
						Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
						if (b.getTenBan().equalsIgnoreCase(tenBanDaDat)) {
							maHD = hd_temp.getMaHD();
						}
					}
					if (dao_HD.capNhatBan(maHD, maBanTrong)) {
						if (dao_Ban.capnhatBan(new Ban(maBanDaDat, tenBanDaDat, false))&&dao_Ban.capnhatBan(new Ban(maBanTrong, tenBanTrong, true))) {
							XoaDLTableBan();
							docDuLieuDatabaseVaoTableBan();
							XoaDLTableBanDaDat();
							docDuLieuDatabaseVaoTableBanDaDat();
							tablePhongTrong.clearSelection();
							tableBanDaDat.clearSelection();
							lblBanDangChon.setText(tenBanTrong);
							
							for (int i = 0; i < tableModelBanDaDat.getRowCount(); i++) {
								if (tableModelBanDaDat.getValueAt(i, 0).toString().equalsIgnoreCase(maBanTrong)) {
									tableBanDaDat.changeSelection(i, i, false, false);
									break;
								}
							}
							FrameThanhToan.XoaDLTableBanDaDat();
							FrameThanhToan.docDuLieuDatabaseVaoTableBanDaDat();
							FrameThanhToan.docDLCmbBan();
							JOptionPane.showMessageDialog(null, "Đổi bản thành công!");
						}
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});

		btnDatDichVu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = tableListDV.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ cần đặt!");
					return;
				}
				String maNuoc = tableModelListDV.getValueAt(r, 0).toString();

				for (int i = 0; i < tableHoaDonDV.getRowCount(); i++) {
					if (tableModelHoaDonDV.getValueAt(i, 0).toString().equalsIgnoreCase(maNuoc)) {
						JOptionPane.showMessageDialog(null, "Dịch vụ đã được đặt!");
						return;
					}
				}
				if (txtSoLuong.getText().trim().length() != 0) {
					try {
						int sl = Integer.parseInt(txtSoLuong.getText());
						if (sl <= 0 || sl > 100) {
							JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 và nhỏ hơn 100!");
							return;
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Số lượng phải nhập chữ số!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng dịch vụ cần đặt");
					return;
				}
				if (lblBanDangChon.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn cần đặt");
					return;
				}

				String tenNuoc = tableModelListDV.getValueAt(r, 1).toString();
				try {
					Nuoc n = dao_DichVu.getDVTheoTen(tenNuoc);

					String maHD = "";
					Date date_temp = null;
					List<HoaDon> list = dao_HD.getHDChuaThanhToan();
					for (HoaDon hd_temp : list) {
						Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
						if (b.getTenBan().equalsIgnoreCase(lblBanDangChon.getText())) {
							maHD = hd_temp.getMaHD();
						}
					}
					NhanVien nv_temp = FrameDangNhap.getTaiKhoan().getTenTaiKhoan();
					NhanVien nv = dao_NV.getNhanVienTheoMa(nv_temp.getMaNV());
					Ban b = dao_Ban.getBanTheoTen(lblBanDangChon.getText().trim());
					HoaDon hd = new HoaDon(maHD);

					String soLuong = txtSoLuong.getText();

					ChiTietHoaDon cthd = new ChiTietHoaDon(hd, n, Integer.parseInt(soLuong));

					if (dao_CTHD.themHD(cthd)) {
						JOptionPane.showMessageDialog(null, "Thêm thành công!");
						XoaDLTableHoaDon();
						docDuLieuDatabaseVaoTableHD(lblBanDangChon.getText());
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnCapNhapSL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = tableHoaDonDV.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ cần cập nhật số lượng!");
					return;
				}
				if (txtSoLuong.getText().trim().length() != 0) {
					try {
						int sl = Integer.parseInt(txtSoLuong.getText());
						if (sl <= 0 || sl > 100) {
							JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 và nhỏ hơn 100!");
							return;
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Số lượng phải nhập chữ số!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng dịch vụ cần đặt");
					return;
				}

				try {
					String soLuong = txtSoLuong.getText();					
					String maHD = "";
					List<HoaDon> list = dao_HD.getHDChuaThanhToan();
					for (HoaDon hd_temp : list) {
						Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
						if (b.getTenBan().equalsIgnoreCase(lblBanDangChon.getText())) {
							maHD = hd_temp.getMaHD();
						}
					}
					
					String maNuoc = tableModelHoaDonDV.getValueAt(r, 0).toString();
					
					if (dao_CTHD.capNhatSL(maHD, maNuoc, Integer.parseInt(soLuong))) {
						DefaultTableModel dm = (DefaultTableModel) tableHoaDonDV.getModel();
						int rowCount = dm.getRowCount();
						// Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--) {
							dm.removeRow(i);
						}
//						try {
//							dao_HD = (DAO_HoaDon) Naming.lookup("rmi://192.168.101.35:9999/dao_HoaDon");
//							dao_NV = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
//							dao_CTHD = (DAO_CTHD) Naming.lookup("rmi://192.168.101.35:9999/dao_CTHD");
//							dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
//							dao_DichVu = (DAO_DichVu) Naming.lookup("rmi://192.168.101.35:9999/dao_DichVu");
//						} catch (MalformedURLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						} catch (RemoteException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						} catch (NotBoundException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
						
						
						docDuLieuDatabaseVaoTableHD(lblBanDangChon.getText());
						JOptionPane.showMessageDialog(null, "Cập nhật số lượng thánh công");
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});

		btnHuyDichVu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = tableHoaDonDV.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ cần hủy!");
					return;
				}
				try {
					String maHD = "";
					List<HoaDon> list = dao_HD.getHDChuaThanhToan();
					for (HoaDon hd_temp : list) {
						if (hd_temp.getMaBan().getTenBan().equalsIgnoreCase(lblBanDangChon.getText())) {
							maHD = hd_temp.getMaHD();
						}
					}
					
					String maNuoc = tableModelHoaDonDV.getValueAt(r, 0).toString();
					
					if (dao_CTHD.xoaCTHD(maHD, maNuoc)) {
						tableModelHoaDonDV.removeRow(r);
						JOptionPane.showMessageDialog(null, "Hủy dịch vụ thành công!");
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				
			}
		});

		btnTimDV.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (cmbDanhSachDV.getSelectedIndex() == 0) {
					XoaDLTableDV();
					try {
						docDuLieuDatabaseVaoTableDV();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						String s = cmbDanhSachDV.getSelectedItem().toString();
						DecimalFormat df = new DecimalFormat("#,##0.0");
						Nuoc dv = dao_DichVu.getDVTheoTen(s);
						XoaDLTableDV();
						tableModelListDV
								.addRow(new Object[] { dv.getMaNuoc(), dv.getTenNuoc(), df.format(dv.getGiaTien()) });
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		tableHoaDonDV.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int r = tableHoaDonDV.getSelectedRow();
				txtSoLuong.setText(tableModelHoaDonDV.getValueAt(r, 2).toString());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return pnlContentPane;
	}

	public static void docDuLieuDatabaseVaoTableBan() throws RemoteException {
		List<Ban> list = dao_Ban.getAllBanTrong();

		for (Ban b : list) {
			if (!b.getMaBan().equalsIgnoreCase("B9999")) {
				tableModelPhongTrong.addRow(new Object[] { b.getMaBan(), b.getTenBan() });
			}
		}
	}

	public static void docDuLieuDatabaseVaoTableBanDaDat() throws RemoteException {
		List<Ban> list = dao_Ban.getAllBanDaDat();

		for (Ban b : list) {
			if (!b.getMaBan().equalsIgnoreCase("B9999")) {
				tableModelBanDaDat.addRow(new Object[] { b.getMaBan(), b.getTenBan() });
			}
		}
	}

	public static void docDuLieuDatabaseVaoTableDV() throws RemoteException {
		List<Nuoc> list = dao_DichVu.getAllDichVuConMon();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (Nuoc dv : list) {
			tableModelListDV.addRow(new Object[] { dv.getMaNuoc(), dv.getTenNuoc(), df.format(dv.getGiaTien()) });
		}
	}

	public static void docDuLieuDatabaseVaoTableHD(String tenBan) throws RemoteException {
//		try {
//			dao_HD = (DAO_HoaDon) Naming.lookup("rmi://192.168.101.35:9999/dao_HoaDon");
//			dao_NV = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
//			dao_CTHD = (DAO_CTHD) Naming.lookup("rmi://192.168.101.35:9999/dao_CTHD");
//			dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
//			dao_DichVu = (DAO_DichVu) Naming.lookup("rmi://192.168.101.35:9999/dao_DichVu");
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			dao_CTHD = (DAO_CTHD) Naming.lookup("rmi://192.168.101.35:9999/dao_CTHD");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HoaDon hd = null;
		List<HoaDon> listhd = dao_HD.getHDChuaThanhToan();
		for (HoaDon hd_temp : listhd) {
			Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
			if (b.getTenBan().equalsIgnoreCase(tenBan)) {
				hd = hd_temp;
			}
		}
		List<ChiTietHoaDon> list = dao_CTHD.getCTHDTheoMa(hd.getMaHD());
		
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (ChiTietHoaDon cthd : list) {
			Nuoc n = dao_DichVu.getDVTheoMa(cthd.getMaNuoc().getMaNuoc());
			tableModelHoaDonDV.addRow(
					new Object[] { n.getMaNuoc(), n.getTenNuoc(), cthd.getSoLuong(),
							n.getGiaTien(), n.getGiaTien() * cthd.getSoLuong() });
		}
	}

	public static void docDLCmbDV() throws RemoteException {
		cmbDanhSachDV.addItem("");

		List<Nuoc> list = dao_DichVu.getAllDichVuConMon();
		for (Nuoc dv : list) {
			cmbDanhSachDV.addItem(dv.getTenNuoc());
		}
	}

	public static void XoaDLTableBan() {
		DefaultTableModel md = (DefaultTableModel) tablePhongTrong.getModel();
		md.getDataVector().removeAllElements();
	}

	public static void XoaDLTableBanDaDat() {
		DefaultTableModel md = (DefaultTableModel) tableBanDaDat.getModel();
		md.getDataVector().removeAllElements();
	}

	public static void XoaDLTableDV() {
		DefaultTableModel md = (DefaultTableModel) tableListDV.getModel();
		md.getDataVector().removeAllElements();
	}

	public static void XoaDLTableHoaDon() {
		DefaultTableModel md = (DefaultTableModel) tableHoaDonDV.getModel();
		md.getDataVector().removeAllElements();
	}

}