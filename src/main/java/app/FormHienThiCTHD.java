package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import dao.DAO_Ban;
import dao.DAO_CTHD;
import dao.DAO_DichVu;
import dao.DAO_HoaDon;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Nuoc;

public class FormHienThiCTHD extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3887157344579679842L;
	private static DAO_CTHD dao_CTHD;
	private static DAO_HoaDon dao_HD;
	private JLabel lblTieuDe;
	private static DAO_Ban dao_Ban;
	private static DAO_DichVu dao_DichVu;
	private static DefaultTableModel tableModelHoaDonDV;
	private static JTable tableHoaDonDV;
	public FormHienThiCTHD(String tenBan) throws RemoteException {
		
		try {
			dao_CTHD = (DAO_CTHD) Naming.lookup("rmi://192.168.101.35:9999/dao_CTHD");
			dao_HD = (DAO_HoaDon) Naming.lookup("rmi://192.168.101.35:9999/dao_HoaDon");
			dao_DichVu = (DAO_DichVu) Naming.lookup("rmi://192.168.101.35:9999/dao_DichVu");
			dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
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
		
		
		setTitle("CHI TIẾT HÓA ĐƠN");
		setSize(600,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(new Color(252, 242, 217));
		this.add(p);
		
		JPanel pTop = new JPanel();
		pTop.setBackground(new Color(252, 242, 217));
		lblTieuDe = new JLabel(tenBan);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 30));
		lblTieuDe.setForeground(new Color(131, 77, 30));
		pTop.add(lblTieuDe);
		
		String[] header = { "Mã đồ uống", "Tên đồ uống", "Số lượng", "Giá tiền", "Thành tiền" };
		tableModelHoaDonDV = new DefaultTableModel(header, 0);
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
		JTableHeader tableHeader = tableHoaDonDV.getTableHeader();
		tableHeader.setBackground(new Color(173, 119, 72));
		tableHeader.setForeground(new Color(255, 255, 255));
		tableHeader.setFont(new Font("Arial", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 40));
		tableHeader.setResizingAllowed(false);
		
		p.add(new JScrollPane(tableHoaDonDV),BorderLayout.CENTER);
		
		p.add(pTop, BorderLayout.NORTH);
		
		docDuLieuDatabaseVaoTableHD(tenBan);
	}
	
	public static void docDuLieuDatabaseVaoTableHD(String tenBan) throws RemoteException {
		String maHD = "";
		List<HoaDon> listhd = dao_HD.getHDChuaThanhToan();
		for (HoaDon hd_temp : listhd) {
			Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
			if (b.getTenBan().equalsIgnoreCase(tenBan)) {
				maHD = hd_temp.getMaHD();
			}
		}
		List<ChiTietHoaDon> list = dao_CTHD.getCTHDTheoMa(maHD);
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (ChiTietHoaDon cthd : list) {
			Nuoc n = dao_DichVu.getDVTheoMa(cthd.getMaNuoc().getMaNuoc());
			tableModelHoaDonDV.addRow(
					new Object[] { n.getMaNuoc(), n.getTenNuoc(), cthd.getSoLuong(),
							n.getGiaTien(), n.getGiaTien() * cthd.getSoLuong() });
		}
	}
	
	public static void XoaDLTableHoaDon() {
		DefaultTableModel md = (DefaultTableModel) tableHoaDonDV.getModel();
		md.getDataVector().removeAllElements();
	}
}
