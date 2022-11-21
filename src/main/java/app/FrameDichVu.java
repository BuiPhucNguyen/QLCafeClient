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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

import dao.DAO_DichVu;
import dao.DAO_NhanVien;
import entity.NhanVien;
import entity.Nuoc;


public class FrameDichVu extends JFrame{
	private JButton btnThem;
	private JButton btnXoa;
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JButton btnLamMoi;
	private JButton btnTimKiem;
	private JButton btnCapNhat;
	public static JComboBox<String> cmbMaDV;
	public static JComboBox<String> cmbTenDV;
	private JComboBox<String> cmbTinhTrang;
	private JTextField txtGiaMin;
	private JTextField txtGiaMax;
	private JButton btnXuatExcel;
	private JButton btnDocFile;
	private JComboBox<String> cmbTieuChi;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private static DAO_DichVu dao_DichVu;

	public JPanel createPanelDichVu() throws RemoteException {
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
		// --------------------------
		Toolkit toolkit = this.getToolkit(); /* Lấy độ phân giải màn hình */
		Dimension d = toolkit.getScreenSize();

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(248, 227, 182));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		/*
		 * Chức năng
		 */
		btnThem = new JButton("THÊM MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds((int) (d.getWidth() - 1080), 15, 165, 45);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnThem.setBackground(new Color(131, 77, 30));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnXoa = new JButton("XÓA", new ImageIcon("image/xoa.png"));
		btnXoa.setBounds((int) (d.getWidth() - 910), 15, 165, 45);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXoa.setBackground(new Color(131, 77, 30));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		pnlContentPane.add(btnXoa);

		btnCapNhat = new JButton("CẬP NHẬT", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds((int) (d.getWidth() - 740), 15, 165, 45);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(131, 77, 30));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnDocFile = new JButton("ĐỌC FILE", new ImageIcon("image/docfile.png"));
		btnDocFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDocFile.setBackground(new Color(131, 77, 30));
		btnDocFile.setForeground(Color.WHITE);
		btnDocFile.setBounds((int) (d.getWidth() - 570), 15, 165, 45);
		btnDocFile.setFocusPainted(false);
		pnlContentPane.add(btnDocFile);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(131, 77, 30));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds((int) (d.getWidth() - 400), 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);
		// Thông tin tìm kiếm
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, (int) (d.getHeight() - 109));
		pnlTimKiem.setBackground(new Color(252, 242, 217));
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblMaDV = new JLabel("<html><div style='text-align: center;'>MÃ ĐỒ UỐNG: </div></html>",
				SwingConstants.CENTER);
		lblMaDV.setOpaque(true);
		lblMaDV.setBackground(new Color(173, 119, 72));
		lblMaDV.setBounds(1, 10, 200, 30);
		lblMaDV.setForeground(Color.WHITE);
		lblMaDV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaDV);
		cmbMaDV = new JComboBox<String>();
		cmbMaDV.setBounds(26, 50, 150, 30);
		cmbMaDV.setBackground(Color.WHITE);
		cmbMaDV.setEditable(true);
		cmbMaDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(cmbMaDV);
		pnlTimKiem.add(cmbMaDV);
		cmbMaDV.setMaximumRowCount(3);

		JLabel lblTenDV = new JLabel("<html><div style='text-align: center;'>TÊN ĐỒ UỐNG: </div></html>",
				SwingConstants.CENTER);
		lblTenDV.setOpaque(true);
		lblTenDV.setBackground(new Color(173, 119, 72));
		lblTenDV.setForeground(Color.WHITE);
		lblTenDV.setBounds(1, 100, 193, 30);
		lblTenDV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTenDV);
		cmbTenDV = new JComboBox<String>();
		cmbTenDV.setBounds(22, 140, 150, 30);
		cmbTenDV.setBackground(Color.WHITE);
		cmbTenDV.setEditable(true);
		cmbTenDV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbTenDV.setMaximumRowCount(3);
		AutoCompleteDecorator.decorate(cmbTenDV);
		pnlTimKiem.add(cmbTenDV);

		JLabel lblTinhTrang = new JLabel("<html><div style='text-align: center;'>TÌNH TRẠNG: </div></html>",
				SwingConstants.CENTER);
		lblTinhTrang.setBounds(1, 190, 193, 30);
		lblTinhTrang.setOpaque(true);
		lblTinhTrang.setBackground(new Color(173, 119, 72));
		lblTinhTrang.setForeground(Color.WHITE);
		lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTinhTrang);
		String[] tinhtrang = { "Tất cả", "Còn món", "Hết món" };
		cmbTinhTrang = new JComboBox<String>(tinhtrang);
		cmbTinhTrang.setBounds(22, 230, 150, 30);
		cmbTinhTrang.setFocusable(false);
		cmbTinhTrang.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTinhTrang);

		JLabel lblGiaTien = new JLabel("<html><div style='text-align: center;'>GIÁ TIỀN: </div></html>",
				SwingConstants.CENTER);
		lblGiaTien.setBounds(1, 280, 193, 30);
		lblGiaTien.setOpaque(true);
		lblGiaTien.setBackground(new Color(173, 119, 72));
		lblGiaTien.setForeground(Color.WHITE);
		lblGiaTien.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblGiaTien);

		JLabel lblMin = new JLabel("Từ: ");
		lblMin.setBounds(22, 320, 150, 30);
		lblMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblMin);
		txtGiaMin = new JTextField();
		txtGiaMin.setBounds(60, 320, 110, 30);
		txtGiaMin.setBackground(Color.WHITE);
		txtGiaMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtGiaMin);
		JLabel lblMax = new JLabel("Đến: ");
		lblMax.setBounds(22, 360, 50, 30);
		lblMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblMax);
		txtGiaMax = new JTextField();
		txtGiaMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtGiaMax.setBounds(60, 360, 110, 30);
		txtGiaMax.setBackground(Color.WHITE);
		pnlTimKiem.add(txtGiaMax);

		JLabel lblSapXep = new JLabel("<html><div style='text-align: center;'>SẮP XẾP THEO: </div></html>",
				SwingConstants.CENTER);
		lblSapXep.setBounds(1, 410, 193, 30);
		lblSapXep.setOpaque(true);
		lblSapXep.setBackground(new Color(173, 119, 72));
		lblSapXep.setForeground(Color.WHITE);
		lblSapXep.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSapXep);
		String[] tieuChi = { "Mã đồ uống", "Tên đồ uống","Giá tiền" };
		cmbTieuChi = new JComboBox<String>(tieuChi);
		cmbTieuChi.setBounds(22, 450, 150, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);

		radTangDan = new JRadioButton("Tăng dần");
		radTangDan.setBounds(15, 490, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Giảm dần");
		radGiamDan.setBounds(95, 490, 90, 30);
		radGiamDan.setBackground(Color.WHITE);
		radGiamDan.setFont(new Font("Arial", Font.ITALIC, 13));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radTangDan);
		bg.add(radGiamDan);
		pnlTimKiem.add(radTangDan);
		pnlTimKiem.add(radGiamDan);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(131, 77, 30));
		btnTimKiem.setBounds(14, 540, 170, 45);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBounds(14, 600, 170, 45);
		btnLamMoi.setBackground(new Color(131, 77, 30));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách dịch vụ
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH DỊCH VỤ: "));
		pnlDanhSach.setBounds(230, 75, (int) (d.getWidth() - 450), (int) (d.getHeight() - 165));
		pnlDanhSach.setBackground(new Color(255, 255, 255));
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã đồ uống", "Tên đồ uống", "Giá tiền" ,"Trình trạng" };
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
		pnlDanhSach.add(new JScrollPane(table));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Giá tiền").setCellRenderer(rightRenderer);

		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		
		
		docDuLieuDatabaseVaoTable();
		docDuLieuVaoCmbMaDV();
		docDuLieuVaoCmbTen();
		
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					new FormThemDV().setVisible(true);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = table.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc sẽ xóa dịch vụ này không?", "Cảnh báo",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == 0) {
					String maDV = tableModel.getValueAt(r, 0).toString();
					try {
						dao_DichVu.xoaDichVu(maDV);
						tableModel.removeRow(r);
						JOptionPane.showMessageDialog(null, "Xóa thành công!");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Dịch vụ đang được sử dụng!");
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		btnCapNhat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = table.getSelectedRow();
				if (r == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ cần cập nhật!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					try {
						new FormCapNhatDV().setVisible(true);
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
				cmbMaDV.setSelectedIndex(0);
				cmbTenDV.setSelectedIndex(0);
				cmbTinhTrang.setSelectedIndex(0);
				txtGiaMin.setText("");
				txtGiaMax.setText("");
				cmbTieuChi.setSelectedIndex(0);
				radTangDan.setSelected(true);
				xoaHetDL();
				try {
					docDuLieuDatabaseVaoTable();
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
				String maDV = cmbMaDV.getSelectedItem().toString().trim();
				String tenDV = cmbTenDV.getSelectedItem().toString().trim();
				String tinhTrang = cmbTinhTrang.getSelectedItem().toString().trim();
				String giaMin = txtGiaMin.getText();
				String giaMax = txtGiaMax.getText();
				int tieuChi = cmbTieuChi.getSelectedIndex();
				boolean tangDan = radTangDan.isSelected();

				if (!validInput())
					return;
				else {
					xoaHetDL();
					List<Nuoc> listDV;
					try {
						listDV = dao_DichVu.getAllDichVu();
						if (!maDV.trim().equals("")) {
							List<Nuoc> listTemp = new ArrayList<Nuoc>();
							for (Nuoc dv : listDV) {
								listTemp.add(dv);
							}
							listDV.clear();
							for (Nuoc dv : listTemp) {
								if (dv.getMaNuoc().trim().contains(maDV)) {
									listDV.add(dv);
								}
							}
						}
						if (!tenDV.trim().equals("")) {
							List<Nuoc> listTemp = new ArrayList<Nuoc>();
							for (Nuoc dv : listDV) {
								listTemp.add(dv);
							}
							listDV.clear();
							for (Nuoc dv : listTemp) {
								if (dv.getTenNuoc().trim().contains(tenDV)) {
									listDV.add(dv);
								}
							}
						}
						if (!tinhTrang.trim().equals("Tất cả")) {
							List<Nuoc> listTemp = new ArrayList<Nuoc>();
							for (Nuoc dv : listDV) {
								listTemp.add(dv);
							}
							listDV.clear();
							if (tinhTrang.equalsIgnoreCase("Còn món")) {
								for (Nuoc dv : listTemp) {
									if (dv.isTrangThai()==true) {
										listDV.add(dv);
									}
										
								}
							}  else if (tinhTrang.equalsIgnoreCase("Hết món")) {
								for (Nuoc dv : listTemp) {
									if (dv.isTrangThai()==false) {
										listDV.add(dv);
									}
										
								}
							}
						}
						if (!giaMin.trim().equals("")) {
							double min = Double.parseDouble(giaMin.trim());
							List<Nuoc> listTemp = new ArrayList<Nuoc>();
							for (Nuoc dv : listDV) {
								listTemp.add(dv);
							}
							listDV.clear();
							for (Nuoc dv : listTemp) {
								if (dv.getGiaTien() >= min)
									listDV.add(dv);
							}
						}
						if (!giaMax.trim().equals("")) {
							double max = Double.parseDouble(giaMax.trim());
							List<Nuoc> listTemp = new ArrayList<Nuoc>();
							for (Nuoc dv : listDV) {
								listTemp.add(dv);
							}
							listDV.clear();
							for (Nuoc dv : listTemp) {
								if (dv.getGiaTien() <= max)
									listDV.add(dv);
							}
						}
						if (listDV.size() == 0) {
							JOptionPane.showMessageDialog(null, "Không có dịch vụ nào phù hợp với tiêu chí", "Lỗi",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (tieuChi == 0)
							sapXepTheoMaDV(listDV, tangDan);
						else if (tieuChi == 1)
							sapXepTheoTenDV(listDV, tangDan);
						else if (tieuChi == 2)
							sapXepTheoGiaTien(listDV, tangDan);
						DecimalFormat df = new DecimalFormat("#,##0.0");
						for (Nuoc dv : listDV) {
							tableModel.addRow(new Object[] { dv.getMaNuoc(), dv.getTenNuoc(), df.format(dv.getGiaTien()), dv.isTrangThai()==true?"Còn món":"Hết món" });
						}
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
					if(!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
						filePath += ".xls";
					}
					if (xuatExcel(filePath))
						JOptionPane.showMessageDialog(null, "Ghi file thành công!!", "Thành công",
								JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Ghi file thất bại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		btnDocFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileDialog = new JFileChooser("excel") {
					@Override
					protected JDialog createDialog(Component parent) throws HeadlessException {
						JDialog dialog = super.createDialog(parent);
						ImageIcon icon = new ImageIcon("image/logodark.png");
						dialog.setIconImage(icon.getImage());
						return dialog;
					}
				};
				int returnVal = fileDialog.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					java.io.File file = fileDialog.getSelectedFile();
					if (file.getName().endsWith(".xls")) {
						try {
							docFileExcel(file.getAbsolutePath());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn file Excel để đọc file", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		});
		return pnlContentPane;
	}
	
	public void sapXepTheoMaDV(List<Nuoc> list, boolean tangDan) {
		Collections.sort(list, new Comparator<Nuoc>() {
			@Override
			public int compare(Nuoc o1, Nuoc o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getMaNuoc().compareToIgnoreCase(o2.getMaNuoc());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaNuoc().compareToIgnoreCase(o1.getMaNuoc());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoTenDV(List<Nuoc> list, boolean tangDan) {
		Collections.sort(list, new Comparator<Nuoc>() {
			@Override
			public int compare(Nuoc o1, Nuoc o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return o1.getTenNuoc().compareToIgnoreCase(o2.getTenNuoc());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getTenNuoc().compareToIgnoreCase(o1.getTenNuoc());
					return 0;
				}
			}
		});
	}


	public void sapXepTheoGiaTien(List<Nuoc> list, boolean tangDan) {
		Collections.sort(list, new Comparator<Nuoc>() {
			@Override
			public int compare(Nuoc o1, Nuoc o2) {
				if (tangDan) {
					if (o1 != null && o2 != null)
						return Double.compare(o1.getGiaTien(), o2.getGiaTien());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Double.compare(o2.getGiaTien(), o1.getGiaTien());
					return 0;
				}
			}
		});
	}

	private boolean validInput() {
		String giaMin = txtGiaMin.getText();
		String giaMax = txtGiaMax.getText();
		if (giaMin.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaMin);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phải lớn hơn hoặc bằng 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					txtGiaMin.setText("");
					txtGiaMin.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá phải nhập số", "Lỗi", JOptionPane.ERROR_MESSAGE);
				txtGiaMin.setText("");
				txtGiaMin.requestFocus();
				return false;
			}
		}
		if (giaMax.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaMax);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phải lớn hơn hoặc bằng 0", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					txtGiaMax.setText("");
					txtGiaMax.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá phải nhập số", "Lỗi", JOptionPane.ERROR_MESSAGE);
				txtGiaMax.setText("");
				txtGiaMax.requestFocus();
				return false;
			}
		}
		if (giaMin.trim().length() > 0 && giaMax.trim().length() > 0) {
			double min = Double.parseDouble(giaMin);
			double max = Double.parseDouble(giaMax);
			if (max < min) {
				JOptionPane.showMessageDialog(this, "Giá MAX không được bé hơn giá MIN", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				txtGiaMax.requestFocus();
				return false;
			}
		}
		return true;
	}

	public static void docDuLieuVaoCmbTen() throws RemoteException {
		try {
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
		List<Nuoc> list = dao_DichVu.getAllDichVu();
		cmbTenDV.addItem("");
		for (Nuoc dv : list) {
			cmbTenDV.addItem(dv.getTenNuoc());
		}
	}

	public static void docDuLieuVaoCmbMaDV() throws RemoteException {
		try {
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
		List<Nuoc> list = dao_DichVu.getAllDichVu();
		cmbMaDV.addItem("");
		for (Nuoc dv : list) {
			cmbMaDV.addItem(dv.getMaNuoc());
		}
	}

	public static void docDuLieuDatabaseVaoTable() throws RemoteException {
		try {
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
		List<Nuoc> list = dao_DichVu.getAllDichVu();
		DecimalFormat df = new DecimalFormat("#,##0.0");
		for (Nuoc dv : list) {
			tableModel.addRow(new Object[] { dv.getMaNuoc(), dv.getTenNuoc(), df.format(dv.getGiaTien()), dv.isTrangThai() == true ? "Còn món" : "Hết món" });
		}
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}
	
	public boolean xuatExcel(String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH DỊCH VỤ");

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

			cell.setCellValue("DANH SÁCH DỊCH VỤ");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Giá tiền", "Trạng thái" };
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// Dòng 2 người lập
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Người lập:");
			cell = row.createCell(2);

			NhanVien nv = FrameDangNhap.getTaiKhoan().getTenTaiKhoan();
			DAO_NhanVien dao_NhanVien = (DAO_NhanVien) Naming.lookup("rmi://192.168.101.35:9999/dao_NhanVien");
			cell.setCellValue(dao_NhanVien.getNhanVienTheoMa(nv.getMaNV()).getTenNV());
			
			worksheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

			// Dòng 3 ngày lập
			row = worksheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("Ngày lập:");
			cell = row.createCell(2);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			cell.setCellValue(df.format(new Date()));
			worksheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));

			// Dòng 4 tên các cột
			row = worksheet.createRow(4);

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
				row = worksheet.createRow(5 + i);
				for (int j = 0; j < header.length; j++) {
					cell = row.createCell(j + 1);
					if (STT == i) {
						cell.setCellValue(STT + 1);
						STT++;
					} else {
						if (table.getValueAt(i, j - 1) != null) {
							if (j == header.length - 2) {
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
			e.printStackTrace();
			return false;
		}
	}
	
	public void docFileExcel(String filePath) throws RemoteException {
		List<Nuoc> dsXeDocTuFile = new ArrayList<Nuoc>();
		try {
			FileInputStream iStream = null;
			HSSFWorkbook workbook;
			try {
				iStream = new FileInputStream(filePath);
				workbook = new HSSFWorkbook(iStream);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "File không hợp lệ!!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			HSSFSheet worksheet = workbook.getSheet("DANH SÁCH DỊCH VỤ");

			if (worksheet == null) {
				JOptionPane.showMessageDialog(this, "File không có worksheet \"DANH SÁCH DỊCH VỤ\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
				workbook.close();
				return;
			}

			String[] title = {"STT","Tên dịch vụ", "Giá tiền"};
			HSSFRow row0 = worksheet.getRow(0);
			for (int i = 0; i < title.length; i++) {
				String temp = "";
				try {
					temp = row0.getCell(i).getStringCellValue();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "File không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
					workbook.close();
					return;
				}
				if (!temp.trim().equals(title[i])) {
					JOptionPane.showMessageDialog(this, "File excel không đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
					workbook.close();
					return;
				}
			}

			HSSFRow row = worksheet.getRow(1);
			int i = 1;
			// Đọc dữ liệu vào list
			String temp = "";
			try {
				temp = row.getCell(1).getStringCellValue();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Dòng "+(i+1)+" file Excel sai định dạngGGG!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				workbook.close();
				return;
			}
			while (!temp.trim().equals("")) {
				try {
					
					String maDV ="ma";
					String tenDV = row.getCell(1).getStringCellValue();
					double giaTien = row.getCell(2).getNumericCellValue();
					Nuoc x = new Nuoc(maDV, tenDV, giaTien, true);
					dsXeDocTuFile.add(x);
					
					row = worksheet.getRow(++i);
					if (row == null)
						break;
					temp = row.getCell(1).getStringCellValue();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Dòng "+(i+1)+" file Excel sai định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
					workbook.close();
					return;
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dsXeDocTuFile == null) {
			JOptionPane.showMessageDialog(this, "File không hợp lệ!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}	
		for(Nuoc xeDocTuFile : dsXeDocTuFile) {
			if (dao_DichVu.getAllDichVu().isEmpty()) {
				xeDocTuFile.setMaNuoc("DV1001");
			} else {
				String maDV;
				List<Nuoc> listN = dao_DichVu.getAllDichVu();
				String maDVCuoi = dao_DichVu.getAllDichVu().get(listN.size()-1).getMaNuoc().trim();
				System.out.println(maDVCuoi);
				int layMaSo = Integer.parseInt(maDVCuoi.substring(2, maDVCuoi.length()));
				maDV = "DV" + (layMaSo + 1);
				xeDocTuFile.setMaNuoc(maDV);
			}
			

			Nuoc dv = new Nuoc(xeDocTuFile.getMaNuoc(), xeDocTuFile.getTenNuoc(), xeDocTuFile.getGiaTien(), xeDocTuFile.isTrangThai());
			dao_DichVu.themDichVu(dv);
			xoaHetDL();
			docDuLieuDatabaseVaoTable();	
		}
		JOptionPane.showMessageDialog(this, "Đọc file thành công!!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
	}
}
