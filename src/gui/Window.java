package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Stack;
import java.util.TimerTask;
import java.awt.event.ActionEvent;

public class Window {

	private JFrame frame;
	private JTable processerT;
	private JTable memoryT;
	private JTable tlbT;
	private JTable pagetableT;
	private JTable diskT;
	private DefaultTableModel processerU;
	private DefaultTableModel memoryU;
	private DefaultTableModel tlbU;
	private DefaultTableModel pagetableU;
	private DefaultTableModel diskU;
	private JPanel TLBpanel;
	private JScrollPane TLBscrollPane;
	private JPanel pageTablepanel;
	private JScrollPane ptscrollPane;
	private JPanel Diskpanel;
	private JScrollPane DiskscrollPane;
	private JPanel memorypanel;
	private JScrollPane memoryscrollPane;
	private JRadioButton process1;
	private JRadioButton process2;
	private JRadioButton process3;
	private JRadioButton process4;
	private JRadioButton process5;
	private JRadioButton allprocesses;
	private JButton onebutton;
	private JButton allbutton;
	private int currentProcessIndex = -1;

	private JScrollPane processerscrollPane;
	// connect Main
	private ProcessExeOneInstruct processExeOneInstruct;

	// for pagetable
	private Hashtable<String, Integer> pagetableindex = new Hashtable<>();
	private Hashtable<Integer, Integer> memoryindex = new Hashtable<>();
	private static Window window;
	private JLabel lblMode;

	public static Window createNewInstance(ProcessExeOneInstruct peo) {
		if (window == null)
			window = new Window(peo);
		window.frame.setVisible(true);
		return window;
	}

	public static Window getInstance() {
		return window;
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private Window(ProcessExeOneInstruct pro) {
		this.processExeOneInstruct = pro;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1150, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel Processerpanel = new JPanel();
		Processerpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processer",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		processerT = new JTable();
		Processerpanel.setBounds(24, 12, 306, 565);
		frame.getContentPane().add(Processerpanel);
		Processerpanel.setLayout(null);

		processerscrollPane = new JScrollPane();
		processerscrollPane.setBounds(12, 25, 282, 508);
		processerU = new DefaultTableModel(new Object[][] {},
				new String[] { "PID", "PC", "LA", "wait time", "run time" });
		processerT.setModel(processerU);
		processerscrollPane.setViewportView(processerT);
		Processerpanel.add(processerscrollPane);

		TLBpanel = new JPanel();
		TLBpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "TLB", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		TLBpanel.setBounds(342, 12, 350, 159);
		frame.getContentPane().add(TLBpanel);
		TLBpanel.setLayout(null);
		TLBscrollPane = new JScrollPane();
		TLBscrollPane.setBounds(12, 30, 326, 117);
		TLBpanel.add(TLBscrollPane);
		tlbT = new JTable();
		tlbU = new DefaultTableModel(new Object[][] {}, new String[] { "I", "TAG", "PPN", "Dirty", "valid" });
		//
		// this.initTLB(TLBs);
		//
		tlbT.setModel(tlbU);
		TLBscrollPane.setViewportView(tlbT);

		pageTablepanel = new JPanel();
		pageTablepanel.setBounds(342, 167, 350, 410);
		pageTablepanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PageTable",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(pageTablepanel);
		pageTablepanel.setLayout(null);

		ptscrollPane = new JScrollPane();
		ptscrollPane.setBounds(12, 34, 320, 333);
		pageTablepanel.add(ptscrollPane);
		pagetableT = new JTable();
		/*
		 * if(false){ pagetableU=new DefaultTableModel(new Object[][]{}, new
		 * String[]{"PID","LA","PPN"}); lblMode = new JLabel("Mode: inverted");
		 * }else{ pagetableU=new DefaultTableModel(new Object[][]{}, new
		 * String[]{"LA","PPN"}); lblMode = new JLabel("Mode: traditional"); }
		 * lblMode.setBounds(12, 379, 128, 17); pageTablepanel.add(lblMode);
		 * pagetableT.setModel(pagetableU);
		 */

		ptscrollPane.setViewportView(pagetableT);

		Diskpanel = new JPanel();
		Diskpanel.setBounds(704, 12, 284, 159);
		Diskpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Disk", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(Diskpanel);
		Diskpanel.setLayout(null);

		DiskscrollPane = new JScrollPane();
		DiskscrollPane.setBounds(12, 26, 260, 121);
		Diskpanel.add(DiskscrollPane);
		diskT = new JTable();
		diskU = new DefaultTableModel(new Object[][] {}, new String[] { " DA", "盘片号", "磁道号", "扇区号", "Block" });
		diskT.setModel(diskU);
		DiskscrollPane.setViewportView(diskT);

		memorypanel = new JPanel();
		memorypanel.setBounds(704, 167, 284, 404);
		memorypanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Memory",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(memorypanel);
		memorypanel.setLayout(null);

		memoryscrollPane = new JScrollPane();
		memoryscrollPane.setBounds(12, 33, 260, 341);
		memorypanel.add(memoryscrollPane);
		memoryT = new JTable();
		memoryU = new DefaultTableModel(new Object[][] {},
				new String[] { "PPN", "Dirtybit", "Freebit", "Timestamp", "Content" });
		memoryT.setModel(memoryU);
		memoryscrollPane.setViewportView(memoryT);

		JPanel buttonpanel = new JPanel();
		buttonpanel.setBounds(989, 12, 149, 565);
		buttonpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Choose",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(buttonpanel);
		buttonpanel.setLayout(null);

		process1 = new JRadioButton("process 1 ");
		process1.setBounds(32, 25, 89, 25);
		process1.setHorizontalAlignment(SwingConstants.CENTER);
		buttonpanel.add(process1);
		processsAddActionListener(process1, 1);

		process2 = new JRadioButton("process 2");
		process2.setBounds(32, 54, 85, 25);
		process2.setHorizontalAlignment(SwingConstants.CENTER);
		buttonpanel.add(process2);
		processsAddActionListener(process2, 2);

		process3 = new JRadioButton("process 3");
		process3.setBounds(32, 84, 85, 25);
		process3.setHorizontalAlignment(SwingConstants.CENTER);
		buttonpanel.add(process3);
		processsAddActionListener(process3, 3);

		process4 = new JRadioButton("process 4 ");
		process4.setBounds(30, 113, 89, 25);
		process4.setHorizontalAlignment(SwingConstants.CENTER);
		buttonpanel.add(process4);
		processsAddActionListener(process4, 4);

		process5 = new JRadioButton("process 5");
		process5.setBounds(30, 144, 85, 25);
		process5.setHorizontalAlignment(SwingConstants.CENTER);
		buttonpanel.add(process5);
		processsAddActionListener(process5, 5);

		allprocesses = new JRadioButton("all processes");
		allprocesses.setBounds(30, 173, 105, 25);
		allprocesses.setHorizontalAlignment(SwingConstants.CENTER);
		buttonpanel.add(allprocesses);
		allprocesses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onebutton.setEnabled(false);
				allbutton.setEnabled(true);
				currentProcessIndex = -1;
			}
		});

		ButtonGroup processChoose = new ButtonGroup();
		processChoose.add(process1);
		processChoose.add(process2);
		processChoose.add(process3);
		processChoose.add(process4);
		processChoose.add(process5);
		processChoose.add(allprocesses);

		onebutton = new JButton("Run one");
		onebutton.setBounds(14, 296, 107, 27);
		buttonpanel.add(onebutton);

		allbutton = new JButton("Run all");
		allbutton.setBounds(14, 373, 107, 27);
		buttonpanel.add(allbutton);
		onebutton.setEnabled(false);
		allbutton.setEnabled(false);

		onebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onebutton.setEnabled(false);
				processExeOneInstruct.ProcessExeInstruct(currentProcessIndex, false);
				onebutton.setEnabled(true);
			}
		});
		allbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				allbutton.setEnabled(false);
				onebutton.setEnabled(false);
				if (currentProcessIndex == -1) {
					processExeOneInstruct.ProcessExeInstruct(0, true);
				} else {
					while (processExeOneInstruct.ProcessExeInstruct(currentProcessIndex, false))
						;
				}
				// allbutton.setEnabled(true);
			}
		});
		//makeFace(tlbT);
	}

	private void processsAddActionListener(JRadioButton process, int i) {
		process.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentProcessIndex = i;
				onebutton.setEnabled(true);
				allbutton.setEnabled(true);
			}
		});
	}

	public interface ProcessExeOneInstruct {
		boolean ProcessExeInstruct(int processindex, boolean isallprocess);
	}

	public void initPagetablePolicy(boolean isInverted) {
		if (isInverted) {
			pagetableU = new DefaultTableModel(new Object[][] {}, new String[] { "PID", "LA", "PPN" });
			lblMode = new JLabel("Mode: inverted");
		} else {
			pagetableU = new DefaultTableModel(new Object[][] {}, new String[] { "LA", "PPN" });
			lblMode = new JLabel("Mode: traditional");
		}
		lblMode.setBounds(12, 379, 128, 17);
		pageTablepanel.add(lblMode);
		pagetableT.setModel(pagetableU);
	}

	public void initDiskunits(ArrayList<String> disks) {
		Object[] object = new Object[5];
		for (int i = 0; i < disks.size(); i++) {
			String targets[] = disks.get(i).split("&");
			for (int j = 0; j < targets.length; j++) {
				object[j] = targets[j];
			}
			diskU.addRow(object);
		}
	}

	public void processSwitch() {
		int last = tlbU.findColumn("valid");
		Object object = new String("false");
		for (int i = 0; i < tlbU.getRowCount(); i++) {
			tlbU.setValueAt(object, i, last);
		}
	}

	public void initTLBunits(ArrayList<String> TLBs) {
		Object[] object = new Object[5];
		for (int i = 0; i < TLBs.size(); i++) {
			int k = 0;
			object[k++] = new String(Integer.toString(i));
			String targets[] = TLBs.get(i).split("&");
			for (int j = 0; j < targets.length; j++, k++) {
				object[k] = targets[j];
			}
			tlbU.addRow(object);
		}
	}

	public void processerAddFinishedInstruct(String instruct) {
		String[] infos = instruct.split("&");
		Object[] objects = new Object[infos.length];
		for (int i = 0; i < objects.length; i++)
			objects[i] = infos[i];
		processerU.addRow(objects);
		jTableMoveToRow(processerT, processerT.getRowCount() - 1);
		/*
		 * int rowCount=processerT.getRowCount();
		 * processerT.getSelectionModel().setSelectionInterval(rowCount-1,
		 * rowCount-1); Rectangle rect = processerT.getCellRect(rowCount-1, 0,
		 * true); processerT.scrollRectToVisible(rect);
		 */

		// JScrollBar bar=processerscrollPane.getVerticalScrollBar();
		// bar.setValue(bar.getMaximum()+1);
	}

	public void memoryPageAdd(String newpage) {
		String[] infos = newpage.split("&");
		Object[] objects = new Object[infos.length];// traditional
		for (int i = 0; i < objects.length; i++) {
			objects[i] = infos[i];
		}
		memoryU.addRow(objects);
		int key = Integer.valueOf(infos[0]);
		memoryindex.put(key, memoryU.getRowCount() - 1);
		jTableMoveToRow(memoryT, memoryT.getRowCount() - 1);
	}

	public void memoryPageUpdate(String page) {
		String[] infos = page.split("&");
		int key = Integer.valueOf(infos[0]);
		int index = memoryindex.get(key);
		for (int i = 0; i < infos.length; i++) {
			Object object = infos[i];
			memoryU.setValueAt(object, index, i);
		}
		jTableMoveToRow(memoryT, index);
	}

	public void pageTableadd(int keyindex, String target) {
		String key = "";
		String[] targets = target.split("&");
		for (int i = 0; i < keyindex; i++) {
			key = key + targets[i];
		}
		Object[] objects = new Object[targets.length];// traditional
		for (int i = 0; i < objects.length; i++) {
			objects[i] = targets[i];
		}
		pagetableU.addRow(objects);
		pagetableindex.put(key, pagetableU.getRowCount() - 1);
		jTableMoveToRow(pagetableT, pagetableT.getRowCount() - 1);
		/*
		 * int rowCount=processerT.getRowCount();
		 * processerT.getSelectionModel().setSelectionInterval(rowCount-1,
		 * rowCount-1); Rectangle rect = processerT.getCellRect(rowCount-1, 0,
		 * true); processerT.scrollRectToVisible(rect);
		 */
	}

	public void clearPageTableAndReInit(int keyindex, ArrayList<String> ptptes) {
		int rowcount = pagetableU.getRowCount();
		for (int i = 0; i < rowcount; i++)
			pagetableU.removeRow(0);
		for (int i = 0; i < ptptes.size(); i++) {
			pageTableadd(keyindex, ptptes.get(i));
		}
	}

	public void pagetableUpdate(int keyindex, String target) {
		String key = "";
		String[] targets = target.split("&");
		for (int i = 0; i < keyindex; i++) {
			key = key + targets[i];
		}
		int index = pagetableindex.get(key);
		for (int i = 0; i < targets.length; i++) {
			Object object = new String(targets[i]);
			tlbU.setValueAt(object, index, i);
		}
		jTableMoveToRow(pagetableT, index);
	}

	public void TLBUpdate(int index, String target) {
		int j = 1;
		String[] targets = target.split("&");
		for (int i = 0; i < targets.length; i++, j++) {
			Object object = new String(targets[i]);
			tlbU.setValueAt(object, index, j);
		}
		jTableMoveToRow(tlbT, index);
	}

	private void jTableMoveToRow(JTable table, int index) {
		table.getSelectionModel().setSelectionInterval(index, index);
		Rectangle rect = table.getCellRect(index, 0, true);
		table.scrollRectToVisible(rect);
	}
	private void makeFace(JTable table){
		DefaultTableCellRenderer tcr=new DefaultTableCellRenderer(){
			 public Component getTableCellRendererComponent(JTable table,  
                     Object value, boolean isSelected, boolean hasFocus,  
                     int row, int column) {  
                 if (row % 2 == 0) {  
                     setBackground(Color.white); //设置奇数行底色  
                 } else if (row % 2 == 1) {  
                     setBackground(new Color(206, 231, 255)); //设置偶数行底色  
                 }  
                 if (Double.parseDouble(table.getValueAt(row, 11).toString()) > 0) {  
                     setBackground(Color.red);  
                 }                   //如果需要设置某一个Cell颜色，需要加上column过滤条件即可  
                 return super.getTableCellRendererComponent(table, value,  
                         isSelected, hasFocus, row, column);  
             }  
         };  
         for (int i = 0; i < table.getColumnCount(); i++) {  
             table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);  
         }  
		}
	
}
