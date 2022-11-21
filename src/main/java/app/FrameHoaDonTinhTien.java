package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.DAO_Ban;
import dao.DAO_CTHD;
import dao.DAO_DichVu;
import dao.DAO_HoaDon;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Nuoc;


public class FrameHoaDonTinhTien extends JFrame implements ActionListener {

	private DefaultTableModel tableModel;
	private JTable table;
	private JButton btnIn;
	private JLabel lblTenNV;
	private JLabel lblThoiGianDat;
	private JLabel lblTienDV;
	private JLabel lblTienPhong;
	private JLabel lblMaHoaDon;
	private JLabel lblTongHoaDon;
	private static DAO_DichVu dao_DichVu;
	private static DAO_Ban dao_Ban;
	private static DAO_CTHD dao_CTHD;
	private static DAO_HoaDon dao_HD;

	public FrameHoaDonTinhTien(String tenNV, String maHD, Date ngayDat, String tenBan) throws RemoteException {
		try {
			dao_CTHD = (DAO_CTHD) Naming.lookup("rmi://192.168.101.35:9999/dao_CTHD");
			dao_HD = (DAO_HoaDon) Naming.lookup("rmi://192.168.101.35:9999/dao_HoaDon");
			dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
			dao_DichVu = (DAO_DichVu) Naming.lookup("rmi://192.168.101.35:9999/dao_DichVu");
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
		
		
		setTitle("HÓA ĐƠN TÍNH TIỀN");
		setSize(500, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlTong = new JPanel(new BorderLayout());

		JPanel pnlTren = new JPanel();
		pnlTren.setBackground(Color.WHITE);
		// Thông tin quán
		JPanel panelTrenGiua = new JPanel(new BorderLayout());
		panelTrenGiua.setBackground(Color.WHITE);

		Box boxTren = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		Box b7 = Box.createHorizontalBox();
		Box b8 = Box.createHorizontalBox();
		Box b9 = Box.createHorizontalBox();

		JLabel lblTen = new JLabel("CAFE DIAMOND");
		lblTen.setFont(new Font("Times New Roman", Font.BOLD, 18));
		b1.add(lblTen);
		boxTren.add(b1);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblDiachi = new JLabel("Địa chỉ: Số 14 Nguyễn Huệ, Phường Bến Nghé, Quận 1, TPHCM");
		lblDiachi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b2.add(lblDiachi);
		boxTren.add(b2);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblSdt = new JLabel("SĐT: 0794861181");
		lblSdt.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b3.add(lblSdt);
		boxTren.add(b3);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblHoaDon = new JLabel("HOÁ ĐƠN TÍNH TIỀN");
		lblHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		b4.add(lblHoaDon);
		boxTren.add(b4);
		boxTren.add(Box.createVerticalStrut(5));
		// Thông tin hoá đơn khách hàng
		JLabel lbl1 = new JLabel("Tên nhân viên:");
		lbl1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b5.add(lbl1);
		lblTenNV = new JLabel(tenNV);
		lblTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b5.add(Box.createHorizontalStrut(88));
		b5.add(lblTenNV);
		b5.add(Box.createHorizontalGlue());
		boxTren.add(b5);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl3 = new JLabel("Thời gian đặt:");
		lbl3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b7.add(lbl3);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		lblThoiGianDat = new JLabel();
		lblThoiGianDat.setText(df.format(ngayDat));
		lblThoiGianDat.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b7.add(Box.createHorizontalStrut(91));
		b7.add(lblThoiGianDat);
		b7.add(Box.createHorizontalGlue());
		boxTren.add(b7);
		boxTren.add(Box.createVerticalStrut(5));


		JLabel lbl5 = new JLabel("Mã hóa đơn:");
		lbl5.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b9.add(lbl5);
		lblMaHoaDon = new JLabel(maHD);
		lblMaHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b9.add(Box.createHorizontalStrut(101));
		b9.add(lblMaHoaDon);
		b9.add(Box.createHorizontalGlue());
		boxTren.add(b9);
		boxTren.add(Box.createVerticalStrut(5));
		panelTrenGiua.add(boxTren, BorderLayout.NORTH);

		// Bảng dịch vụ sử dụng
		JPanel pnlTable = new JPanel(new BorderLayout());

		String[] header = { "Tên dịch vụ", "Số lượng", "Giá tiền", "Thành tiền" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		
		List<HoaDon> listhd = dao_HD.getHDChuaThanhToan();
		for (HoaDon hd_temp : listhd) {
			Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
			if (b.getTenBan().equalsIgnoreCase(tenBan)) {
				maHD = hd_temp.getMaHD();
			}
		}
		List<ChiTietHoaDon> list = dao_CTHD.getCTHDTheoMa(maHD);
		
		double tong = 0;
		
		for (ChiTietHoaDon cthd : list) {
			Nuoc n = dao_DichVu.getDVTheoMa(cthd.getMaNuoc().getMaNuoc());
			tong += n.getGiaTien() * cthd.getSoLuong();
			tableModel.addRow(
					new Object[] { n.getTenNuoc(), cthd.getSoLuong(),
							n.getGiaTien(), n.getGiaTien() * cthd.getSoLuong() });
		}

		table.setGridColor(getBackground());
		table.getTableHeader().setBackground(new Color(255, 255, 255));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBorder(null);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 15));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Số lượng").setCellRenderer(rightRenderer);
		table.getColumn("Giá tiền").setCellRenderer(rightRenderer);
		table.getColumn("Thành tiền").setCellRenderer(rightRenderer);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTable.add(table.getTableHeader(), BorderLayout.NORTH);
		pnlTable.add(table, BorderLayout.CENTER);

		panelTrenGiua.add(pnlTable, BorderLayout.CENTER);

		// Tính tiền
		Box boxDuoi = Box.createVerticalBox();

		Box boxDuoi1 = Box.createHorizontalBox();
		Box boxDuoi2 = Box.createHorizontalBox();
		Box boxDuoi3 = Box.createHorizontalBox();
		Box boxDuoi4 = Box.createHorizontalBox();
		Box boxDuoi5 = Box.createHorizontalBox();
		Box boxDuoi6 = Box.createHorizontalBox();

		
		JLabel lblTongTien1 = new JLabel("Tổng tiền dịch vụ:");
		lblTongTien1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		boxDuoi1.add(lblTongTien1);
		boxDuoi1.add(Box.createHorizontalGlue());
		DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
		lblTienDV = new JLabel(dfMoney.format(tong));
		lblTienDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		boxDuoi1.add(lblTienDV);

		JLabel lblTongTien2 = new JLabel("Tiền phụ thu:");
		lblTongTien2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		boxDuoi2.add(lblTongTien2);
		boxDuoi2.add(Box.createHorizontalGlue());
		lblTienPhong = new JLabel(""+dfMoney.format(tong*8/100));
		lblTienPhong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		boxDuoi2.add(lblTienPhong);

		JLabel lblTong = new JLabel("Tổng hoá đơn:");
		lblTong.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi3.add(lblTong);
		boxDuoi3.add(Box.createHorizontalGlue());
		lblTongHoaDon = new JLabel(""+dfMoney.format(tong+tong*8/100));
		lblTongHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi3.add(lblTongHoaDon);
//		boxDuoi3.add(boxDuoi.createVerticalStrut(50));

		boxTren.add(Box.createVerticalStrut(5));
		JLabel lblCamOn = new JLabel("XIN CẢM ƠN VÀ HẸN GẶP LẠI QUÝ KHÁCH!");
		lblCamOn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi4.add(lblCamOn);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi1);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi2);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi3);
		boxDuoi.add(Box.createVerticalStrut(50));
		boxDuoi.add(boxDuoi4);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi5);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi6);

		panelTrenGiua.add(boxDuoi, BorderLayout.SOUTH);
		pnlTren.add(panelTrenGiua);
		pnlTren.setAutoscrolls(true);
		pnlTong.add(new JScrollPane(pnlTren));

		JPanel pnlDuoi = new JPanel();

		btnIn = new JButton("IN HÓA ĐƠN", new ImageIcon("image/inhoadon.png"));
		btnIn.setPreferredSize(new Dimension(160, 35));
		btnIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnIn.setBackground(new Color(131, 77, 30));
		btnIn.setForeground(Color.WHITE);
		btnIn.setFocusPainted(false);

		pnlDuoi.add(btnIn);
		pnlDuoi.setBackground(Color.WHITE);
		pnlTong.add(pnlDuoi, BorderLayout.SOUTH);

		add(pnlTong);
		btnIn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnIn)) {
			JOptionPane.showMessageDialog(this, "In hoá đơn thành công");
		}
	}
}
