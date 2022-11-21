package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import dao.DAO_Ban;
import dao.DAO_HoaDon;
import dao.DAO_NhanVien;
import entity.Ban;
import entity.HoaDon;
import entity.NhanVien;

public class FrameThongKeDoanhThu extends JFrame {
	private JComboBox<String> cmbTieuChi;
	private JButton btnTimKiem;
	private JButton btnLamMoi;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField txtDoanhThu;
	private JRadioButton radHomNay;
	private JRadioButton radMotTuan;
	private JRadioButton radMotThang;
	private JRadioButton radLuaChonKhac;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private JButton btnXuatExcel;
	private JButton btnHoaDon;
	private JDateChooser txtNgayMin;
	private JDateChooser txtNgayMax;
	public static DAO_Ban dao_Ban;
	private static JComboBox<String> cmbMaHD;
	public static DAO_HoaDon dao_HD;
	public static DAO_NhanVien dao_NV;

	public JPanel createPanelDoanhThu() throws RemoteException {

		try {
			dao_HD = (DAO_HoaDon) Naming.lookup("rmi://192.168.101.35:9999/dao_HoaDon");
			dao_NV = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
			dao_Ban = (DAO_Ban) Naming.lookup("rmi://192.168.101.35:9999/dao_Ban");
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
		

		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(248, 227, 182));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);
		// Chức năng
		btnHoaDon = new JButton("XEM HÓA ĐƠN", new ImageIcon("image/hoadon.png"));
		btnHoaDon.setBounds((int) (d.getWidth() - 600), 15, 190, 45);
		btnHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHoaDon.setBackground(new Color(131, 77, 30));
		btnHoaDon.setForeground(Color.WHITE);
		btnHoaDon.setFocusPainted(false);
		pnlContentPane.add(btnHoaDon);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(131, 77, 30));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);
		/*
		 * Lọc
		 */
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, (int) (d.getHeight() - 109));
		pnlTimKiem.setBackground(new Color(252, 242, 217));
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblThongKe = new JLabel("<html><div style='text-align: center;'>THỐNG KÊ THEO: </div></html>",
				SwingConstants.CENTER);
		lblThongKe.setOpaque(true);
		lblThongKe.setBackground(new Color(173, 119, 72));
		lblThongKe.setBounds(1, 10, 193, 30);
		lblThongKe.setForeground(Color.WHITE);
		lblThongKe.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblThongKe);
		radHomNay = new JRadioButton("Hôm nay");
		radHomNay.setBounds(20, 50, 100, 30);
		radHomNay.setBackground(Color.WHITE);
		radHomNay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radHomNay.setSelected(true);
		radMotTuan = new JRadioButton("1 tuần");
		radMotTuan.setBounds(20, 80, 100, 30);
		radMotTuan.setBackground(Color.WHITE);
		radMotTuan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radMotThang = new JRadioButton("1 tháng");
		radMotThang.setBounds(20, 110, 100, 30);
		radMotThang.setBackground(Color.WHITE);
		radMotThang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radLuaChonKhac = new JRadioButton("Lựa chọn khác");
		radLuaChonKhac.setBounds(20, 140, 120, 30);
		radLuaChonKhac.setBackground(Color.WHITE);
		radLuaChonKhac.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radHomNay);
		bg.add(radMotTuan);
		bg.add(radMotThang);
		bg.add(radLuaChonKhac);
		pnlTimKiem.add(radHomNay);
		pnlTimKiem.add(radMotTuan);
		pnlTimKiem.add(radMotThang);
		pnlTimKiem.add(radLuaChonKhac);

		JLabel lblNgayMin = new JLabel("Từ: ");
		lblNgayMin.setBounds(30, 175, 120, 30);
		lblNgayMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMin);
		txtNgayMin = new JDateChooser();
		txtNgayMin.setDateFormatString("yyyy-MM-dd");
		txtNgayMin.setBounds(75, 175, 100, 30);
		txtNgayMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMin);

		JLabel lblNgayMax = new JLabel("Đến: ");
		lblNgayMax.setBounds(30, 215, 120, 30);
		lblNgayMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMax);
		txtNgayMax = new JDateChooser();
		txtNgayMax.setDateFormatString("yyyy-MM-dd");
		txtNgayMax.setBounds(75, 215, 100, 30);
		txtNgayMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMax);

		JLabel lblMaHD = new JLabel("MÃ HÓA ĐƠN:", SwingConstants.CENTER);
		lblMaHD.setOpaque(true);
		lblMaHD.setBackground(new Color(173, 119, 72));
		lblMaHD.setBounds(1, 265, 193, 30);
		lblMaHD.setForeground(Color.WHITE);
		lblMaHD.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaHD);
		cmbMaHD = new JComboBox<String>();
		cmbMaHD.setBounds(12, 310, 170, 30);
		cmbMaHD.setBackground(Color.WHITE);
		cmbMaHD.setEditable(true);
		cmbMaHD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(cmbMaHD);
		pnlTimKiem.add(cmbMaHD);
		cmbMaHD.setMaximumRowCount(2);

		JLabel lblSX = new JLabel("SẮP XẾP THEO:", SwingConstants.CENTER);
		lblSX.setOpaque(true);
		lblSX.setBackground(new Color(173, 119, 72));
		lblSX.setBounds(1, 355, 193, 30);
		lblSX.setForeground(Color.WHITE);
		lblSX.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSX);
		String[] loai = { "Mã hóa đơn", "Tổng số tiền thanh toán" };
		cmbTieuChi = new JComboBox<String>(loai);
		cmbTieuChi.setBounds(12, 400, 170, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);

		radTangDan = new JRadioButton("Tăng dần");
		radTangDan.setBounds(15, 440, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Giảm dần");
		radGiamDan.setBounds(95, 440, 90, 30);
		radGiamDan.setBackground(Color.WHITE);
		radGiamDan.setFont(new Font("Arial", Font.ITALIC, 13));
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(radTangDan);
		bg2.add(radGiamDan);
		pnlTimKiem.add(radTangDan);
		pnlTimKiem.add(radGiamDan);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(131, 77, 30));
		btnTimKiem.setBounds(13, 490, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(131, 77, 30));
		btnLamMoi.setBounds(13, 550, 170, 45);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách hóa đơn
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH HÓA ĐƠN: "));
		pnlDanhSach.setBounds(230, 75, (int) (d.getWidth() - 455), (int) (d.getHeight() - 210));
		pnlDanhSach.setBackground(Color.WHITE);
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã hóa đơn", "Tên nhân viên", "Tên bàn", "Ngày đặt", "Tổng thanh toán" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel) {
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
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setGridColor(getBackground());
		table.setRowHeight(table.getRowHeight() + 20);
		table.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(173, 119, 72));
		tableHeader.setForeground(new Color(255, 255, 255));
		tableHeader.setFont(new Font("Arial", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 40));
		tableHeader.setResizingAllowed(false);
		pnlDanhSach.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		/*
		 * Tổng doanh thu
		 */
		JLabel lblTongDT = new JLabel("<html>TỔNG DOANH THU: </html>");
		lblTongDT.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTongDT.setBounds(600, (int) (d.getHeight() - 130), 500, 50);
		pnlContentPane.add(lblTongDT);
		txtDoanhThu = new JTextField("0.0 VNĐ");
		txtDoanhThu.setFont(new Font("Times New Roman", Font.BOLD, 25));
		txtDoanhThu.setBounds(850, (int) (d.getHeight() - 130), 400, 50);
		txtDoanhThu.setEditable(false);
		txtDoanhThu.setBorder(BorderFactory.createEmptyBorder());
		txtDoanhThu.setBackground(new Color(248, 227, 182));
		pnlContentPane.add(txtDoanhThu);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Tổng thanh toán").setCellRenderer(rightRenderer);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		setEditableJDateChooder(false);

		radHomNay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setEditableJDateChooder(false);
			}
		});

		radMotTuan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setEditableJDateChooder(false);
			}
		});

		radMotThang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setEditableJDateChooder(false);
			}
		});

		radLuaChonKhac.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setEditableJDateChooder(true);
			}
		});

		btnLamMoi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				radHomNay.setSelected(true);
				txtNgayMin.setDate(null);
				txtNgayMax.setDate(null);
				cmbMaHD.setSelectedIndex(0);
				cmbTieuChi.setSelectedIndex(0);
				radTangDan.setSelected(true);
				txtDoanhThu.setText("0.0 VNĐ");
				xoaHetDL();
				List<HoaDon> list;
				try {
					list = dao_HD.getHoaDonTrongNgay();
					docDuLieuDatabaseVaoTableTheoList(list);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					
					Date ngayMin = txtNgayMin.getDate();
					Date ngayMax = txtNgayMax.getDate();
					String maHD = cmbMaHD.getSelectedItem().toString().trim();
					int tieuChi = cmbTieuChi.getSelectedIndex();
					boolean tangDan = radTangDan.isSelected();

					xoaHetDL();
					List<HoaDon> list = new ArrayList<HoaDon>();
					if (radHomNay.isSelected()) {
						list = dao_HD.getHoaDonTrongNgay();
					} else if (radMotTuan.isSelected()) {
						list = dao_HD.getHoaDonTrongTuan();
					} else if (radMotThang.isSelected()) {
						list = dao_HD.getHoaDonTrongThang();
					} else if (radLuaChonKhac.isSelected()) {
						Date denNgay = txtNgayMax.getDate();
						Date tuNgay = txtNgayMin.getDate();
						if (tuNgay != null && denNgay != null) {
							Date min = new Date(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDate());
							Date max = new Date(denNgay.getYear(), denNgay.getMonth(), denNgay.getDate());
							if (min.after(max)) {
								JOptionPane.showMessageDialog(null, "Ngày MIN phải nhỏ hơn ngày MAX", "Lỗi",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						list = dao_HD.getHDDAThanhToan();
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						if (ngayMin != null) {
							List<HoaDon> listTemp = new ArrayList<HoaDon>();
							for (HoaDon hd : list) {
								listTemp.add(hd);
							}
							list.clear();
							for (HoaDon hd : listTemp) {
								try {
									String output = df.format(hd.getNgayDat());
									Date date = new SimpleDateFormat("yyyy-MM-dd").parse(output);
									if (date.compareTo(ngayMin) >= 0) {
										list.add(hd);
									}
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
						}
						if (ngayMax != null) {
							List<HoaDon> listTemp = new ArrayList<HoaDon>();
							for (HoaDon hd : list) {
								listTemp.add(hd);
							}
							list.clear();
							for (HoaDon hd : listTemp) {
								try {
									String output = df.format(hd.getNgayDat());
									Date date = new SimpleDateFormat("yyyy-MM-dd").parse(output);
									if (date.compareTo(ngayMax) <= 0)
										list.add(hd);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
					if (!maHD.trim().equals("")) {
						List<HoaDon> listTemp = new ArrayList<HoaDon>();
						for (HoaDon hd : list) {
							listTemp.add(hd);
						}
						list.clear();
						for (HoaDon hd : listTemp) {
							if (hd.getMaHD().trim().contains(maHD)) {
								list.add(hd);
							}
						}
					}
					if (list.size() == 0) {
						txtDoanhThu.setText("0.0 VNĐ");
						JOptionPane.showMessageDialog(null, "Không có hóa đơn nào phù hợp với tiêu chí", "Lỗi",
								JOptionPane.ERROR_MESSAGE);

						return;
					}
					if (tieuChi == 0)
						sapXepTheoMaHD(list, tangDan);
					else if (tieuChi == 1)
						sapXepTheoDoanhThu(list, tangDan);

					DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
					for (HoaDon hd : list) {
						Ban b = dao_Ban.getBanTheoMa(hd.getMaBan().getMaBan());
						NhanVien nv = dao_NV.getNhanVienTheoMa(hd.getMaNV().getMaNV());
						HoaDon hd_temp = dao_HD.getHBTheoMa(hd.getMaHD());
						tableModel.addRow(new Object[] { hd_temp.getMaHD(), nv.getTenNV(), b.getTenBan(),
								hd_temp.getNgayDat(), dfMoney.format(hd_temp.getTongTien()) });
					}
					/*
					 * Tính tổng tiền thanh toán
					 */
					int row = table.getRowCount();

					double total = 0;
					for (int i = 0; i < row; i++) {
						String stringTotal = "";
						String tongTienThanhToan = tableModel.getValueAt(i, 4).toString();
						String a[] = tongTienThanhToan.split(",");
						for (int j = 0; j < a.length; j++) {
							stringTotal += a[j];
						}
						total += Double.parseDouble(stringTotal);
					}
					if (total == 0)
						txtDoanhThu.setText("0.0 VNĐ");
					else
						txtDoanhThu.setText(dfMoney.format(total) + " VNĐ");
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});

		btnHoaDon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = table.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui phòng chọn bàn cần xem hóa đơn!");
				} else {
					String tenBan = tableModel.getValueAt(r, 2).toString();
					String maHD = "";
					Date date_temp = null;
//					NhanVien nv_temp = null;
					String maNV = "";
					try {
						List<HoaDon> list = dao_HD.getHDDAThanhToan();
						for (HoaDon hd_temp : list) {
							Ban b = dao_Ban.getBanTheoMa(hd_temp.getMaBan().getMaBan());
							if (b.getTenBan().equalsIgnoreCase(tenBan)) {
								maHD = hd_temp.getMaHD();
								date_temp = hd_temp.getNgayDat();
//								nv_temp = hd_temp.getMaNV();
								maNV = hd_temp.getMaNV().getMaNV();
							}
						}

						NhanVien nv = dao_NV.getNhanVienTheoMa(maNV);

						new FrameHoaDonTinhTien(maNV, tableModel.getValueAt(r, 0).toString(), date_temp, tenBan).setVisible(true);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		btnXuatExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = table.getRowCount();
				if (r != 0) {
					JFileChooser fileDialog = new JFileChooser("excel") {
						@Override
						protected JDialog createDialog(Component parent) throws HeadlessException {
							JDialog dialog = super.createDialog(parent);
							ImageIcon icon = new ImageIcon("image/logodark.png");
							dialog.setIconImage(icon.getImage());
							return dialog;
						}
					};
					FileFilter filter = new FileNameExtensionFilter("Excel(.xls)", ".xls");
					fileDialog.setAcceptAllFileFilterUsed(false);
					fileDialog.addChoosableFileFilter(filter);
					int returnVal = fileDialog.showSaveDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						java.io.File file = fileDialog.getSelectedFile();
						String filePath = file.getAbsolutePath();
						if (!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
							filePath += ".xls";
						}
						if (xuatExcel(filePath))
							JOptionPane.showMessageDialog(null, "Ghi file thành công!!", "Thành công",
									JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "Ghi file thất bại!!", "Lỗi",
									JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Không được xuất danh sách rỗng!");
				}

			}
		});

		docDuLieuVaoCmbMaHD();

		return pnlContentPane;
	}

	public void sapXepTheoMaHD(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaHD().compareToIgnoreCase(o2.getMaHD());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaHD().compareToIgnoreCase(o1.getMaHD());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoDoanhThu(List<HoaDon> list, boolean tangDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return Double.compare(o1.getTongTien(), o2.getTongTien());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Double.compare(o2.getTongTien(), o1.getTongTien());
					return 0;
				}
			}
		});
	}

	private void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public void docDuLieuDatabaseVaoTableTheoList(List<HoaDon> list) {
		for (HoaDon hd : list) {
			try {
				DecimalFormat df = new DecimalFormat("#,##0.0");
				double tongDoanhThu = 0;
				NhanVien nv = dao_NV.getNhanVienTheoMa(hd.getMaNV().getMaNV());
				Ban b = dao_Ban.getBanTheoMa(hd.getMaBan().getMaBan());
				tableModel.addRow(new Object[] { hd.getMaHD(), nv.getTenNV(), b.getTenBan(), hd.getNgayDat(),
						df.format(hd.getTongTien()) });
				tongDoanhThu += hd.getTongTien();
				if (tongDoanhThu == 0)
					txtDoanhThu.setText("0.0 VNĐ");
				else
					txtDoanhThu.setText(df.format(tongDoanhThu) + " VNĐ");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void docDuLieuDatabaseVaoTable() throws RemoteException {
		List<HoaDon> list = dao_HD.getHDDAThanhToan();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		double tongDoanhThu = 0;
		for (HoaDon hd : list) {
			Ban b = dao_Ban.getBanTheoMa(hd.getMaBan().getMaBan());
			NhanVien nv = dao_NV.getNhanVienTheoMa(hd.getMaNV().getMaNV());
			tableModel.addRow(new Object[] { hd.getMaHD(), nv.getTenNV(), b.getTenBan(), hd.getNgayDat(),
					df.format(hd.getTongTien()) });
			tongDoanhThu += hd.getTongTien();
		}
		if (tongDoanhThu == 0)
			txtDoanhThu.setText("0.0 VNĐ");
		else
			txtDoanhThu.setText(df.format(tongDoanhThu) + " VNĐ");
	}

	public void docDuLieuVaoCmbMaHD() throws RemoteException {
		List<HoaDon> list = dao_HD.getHDDAThanhToan();
		cmbMaHD.addItem("");
		for (HoaDon hd : list) {
			cmbMaHD.addItem(hd.getMaHD());
		}
	}

	public void setEditableJDateChooder(boolean trangThai) {
		txtNgayMax.setEnabled(trangThai);
		txtNgayMin.setEnabled(trangThai);
	}

	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH HÓA ĐƠN");

			HSSFRow row;
			HSSFCell cell;

			// Dòng 1 tên
			cell = worksheet.createRow(1).createCell(1);

			HSSFFont newFont = cell.getSheet().getWorkbook().createFont();
			newFont.setBold(true);
			newFont.setFontHeightInPoints((short) 13);
			CellStyle styleTenDanhSach = worksheet.getWorkbook().createCellStyle();
			styleTenDanhSach.setAlignment(HorizontalAlignment.CENTER);
			styleTenDanhSach.setFont(newFont);

			cell.setCellValue("DANH SÁCH HÓA ĐƠN");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã hóa đơn", "Tên nhân viên", "Tên bàn", "Ngày đặt", "Thanh toán" };
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// Dòng 2 người lập
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Người lập:");
			cell = row.createCell(2);

			NhanVien nv = FrameDangNhap.getTaiKhoan().getTenTaiKhoan();

			cell.setCellValue(dao_NV.getNhanVienTheoMa(nv.getMaNV()).getTenNV());
			worksheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

			// Dòng 3 ngày lập
			row = worksheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("Ngày lập:");
			cell = row.createCell(2);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			cell.setCellValue(df.format(new Date()));
			worksheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));

			// Dòng 4 tổng thanh toán
			row = worksheet.createRow(4);

			cell = row.createCell(1);
			cell.setCellValue("Tổng thanh toán:");
			cell = row.createCell(2);

			double tong = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				String luong[] = tableModel.getValueAt(i, 4).toString().split(",");
				String tienLuong = "";
				for (int t = 0; t < luong.length; t++)
					tienLuong += luong[t];
				tong += Double.parseDouble(tienLuong);
			}

			cell.setCellValue(tong + " VNĐ");
			worksheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3));

			// Dòng 5 tên các cột
			row = worksheet.createRow(5);

			HSSFFont fontHeader = cell.getSheet().getWorkbook().createFont();
			fontHeader.setBold(true);

			CellStyle styleHeader = worksheet.getWorkbook().createCellStyle();
			styleHeader.setFont(fontHeader);
			styleHeader.setBorderBottom(BorderStyle.THIN);
			styleHeader.setBorderTop(BorderStyle.THIN);
			styleHeader.setBorderLeft(BorderStyle.THIN);
			styleHeader.setBorderRight(BorderStyle.THIN);
			styleHeader.setAlignment(HorizontalAlignment.CENTER);

			styleHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			for (int i = 0; i < header.length; i++) {
				cell = row.createCell(i + 1);
				cell.setCellValue(header[i]);
				cell.setCellStyle(styleHeader);
			}

			if (table.getRowCount() == 0) {
				return false;
			}

			HSSFFont fontRow = cell.getSheet().getWorkbook().createFont();
			fontRow.setBold(false);

			CellStyle styleRow = worksheet.getWorkbook().createCellStyle();
			styleRow.setFont(fontRow);
			styleRow.setBorderBottom(BorderStyle.THIN);
			styleRow.setBorderTop(BorderStyle.THIN);
			styleRow.setBorderLeft(BorderStyle.THIN);
			styleRow.setBorderRight(BorderStyle.THIN);

			// Ghi dữ liệu vào bảng
			int STT = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				row = worksheet.createRow(6 + i);
				for (int j = 0; j < header.length; j++) {
					cell = row.createCell(j + 1);
					if (STT == i) {
						cell.setCellValue(STT + 1);
						STT++;
					} else {
						if (table.getValueAt(i, j - 1) != null) {
							if (j == header.length - 1) {
								String luong[] = tableModel.getValueAt(i, j - 1).toString().split(",");
								String tienLuong = "";
								for (int t = 0; t < luong.length; t++)
									tienLuong += luong[t];
								cell.setCellValue(Double.parseDouble(tienLuong));
							} else
								cell.setCellValue(table.getValueAt(i, j - 1).toString().trim());
						}
					}
					cell.setCellStyle(styleRow);
				}
			}

			for (int i = 1; i < header.length + 1; i++) {
				worksheet.autoSizeColumn(i);
			}

			workbook.write(fileOut);
			workbook.close();
			fileOut.flush();
			fileOut.close();
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tên file đã tồn tại!");
			return false;
		}
	}
}
