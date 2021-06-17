package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class MyGraph extends JFrame implements ActionListener {

	// for enable and disable button
	boolean[] key = { true, true, true, true, true, true, true, true };

	// for initial Dot ID
	int dotID = 0;
	boolean type = true; // mặc định vô hướng

	// container
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JPanel panelForBellman = new JPanel();
	// button
	JRadioButton rbtnVoHuong = new JRadioButton("Vô hướng");
	JRadioButton rbtnCoHuong = new JRadioButton("Có hướng");

	JButton btnThemDinh = new JButton("Thêm Đỉnh");
	JButton btnThemCanh = new JButton("Thêm cạnh");
	JButton btnMoveDinh = new JButton("Di chuyển đỉnh");
	JButton btnShortestPath = new JButton("Duyệt Bellman-Ford");

	JButton btnBFS = new JButton("Duyệt BFS");
	JButton btnDFS = new JButton("Duyệt DFS");
	JButton btnFile = new JButton("Duyệt File");
	JButton btnClean = new JButton("Clean");

	// label result
	JLabel label = new JLabel("");
	JLabel labelw = new JLabel("Ma trận trọng số:");
	JLabel labelPL = new JLabel("Đường đi P, Nhãn L:");

	JTextArea textMatranW = new JTextArea();
	JTextArea textPL = new JTextArea();

	// Dot dot Bellman-Ford
	Dot dot1 = new Dot();
	Dot dot2 = new Dot();

	// list
	ArrayList<Edge> dsCanh = new ArrayList<>();
	ArrayList<Dot> dsDiem = new ArrayList<>();

	// list for DFS and BFS
	ArrayList<ArrayList<Integer>> ke = new ArrayList<ArrayList<Integer>>();
	ArrayList<Boolean> chuaXet = new ArrayList<>();

	// mảng cập nhật nhãn
	int[] L;

	// mảng lưu đường đi
	int[] P;

	// mảng ma trận trọng số
	ArrayList<ArrayList<Integer>> w = new ArrayList<>();

	// mảng đường đi
	ArrayList<Integer> way = new ArrayList<>();

	// handle
	HandlePoint hdlPoint = new HandlePoint();
	HandleEdge hdlEdge = new HandleEdge();
	HandleMoveDot hdlMove = new HandleMoveDot();
	HandleDFS hdlDFS = new HandleDFS();
	HandleBFS hdlBFS = new HandleBFS();
	HandleBellman hdlBellman = new HandleBellman();

	public MyGraph() {
		super("Lý thuyết đồ thị (Vô hướng)");
	}

	public void setFrame() {
		this.setSize(1000, 560);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.addPanel();
		this.setButtons();
		this.setLabel();
		this.addBtnEvent();
	}

	public void addPanel() {
		panel.setBackground(Color.WHITE);
		panel.setBounds(230, 10, 440, 500);
		panel.setVisible(true);
		this.add(panel);
	}

	public void setLabelForBellman() {
		labelw.setBounds(700, 10, 200, 20);
		labelw.setForeground(Color.BLACK);

		textMatranW.setBounds(700, 40, dsDiem.size() * 34, dsDiem.size() * 30);
		textMatranW.setEditable(false);

		labelPL.setBounds(700, dsDiem.size() * 36 + 30, 200, 20);
		labelPL.setForeground(Color.BLACK);

		textPL.setBounds(700, dsDiem.size() * 36 + 60, dsDiem.size() * 36, 20);
		textPL.setEditable(false);

		this.add(labelw);
		this.add(textMatranW);
		this.add(labelPL);
		this.add(textPL);
	}

	public void setLabel(Color c, String message) {
		label.setForeground(c);
		label.setText(message);
	}

	public void setLabel() {
		label.setBounds(350, 670, 300, 30);
		label.setForeground(Color.RED);
		panel.add(label);
	}

	public void setTextMatranW(String textW, String textpl) {
		setLabelForBellman();
		textMatranW.setText(textW);
		textPL.setText(textpl);
	}

	public void setButtons() {

		JLabel typeLabel = new JLabel("Loại đồ thị:");
		typeLabel.setBounds(10, 0, 100, 30);
		this.add(typeLabel);

		rbtnVoHuong.setBounds(40, 30, 100, 20);
		this.add(rbtnVoHuong);
		rbtnCoHuong.setBounds(40, 60, 100, 20);
		this.add(rbtnCoHuong);

		ButtonGroup type = new ButtonGroup();
		type.add(rbtnVoHuong);
		type.add(rbtnCoHuong);
		rbtnVoHuong.setSelected(true);

		btnDFS.setBounds(10, 400, 200, 50);
		this.add(btnDFS);

		btnBFS.setBounds(10, 350, 200, 50);
		this.add(btnBFS);

		// btn tim duong di ngan nhat giua 2 dinh
		btnShortestPath.setBounds(10, 300, 200, 50);
		this.add(btnShortestPath);

		btnClean.setBounds(10, 450, 200, 50);
		this.add(btnClean);

		btnThemDinh.setBounds(10, 100, 200, 50);
		this.add(btnThemDinh);

		btnThemCanh.setBounds(10, 150, 200, 50);
		this.add(btnThemCanh);

		btnMoveDinh.setBounds(10, 200, 200, 50);
		this.add(btnMoveDinh);
	}

	public void addBtnEvent() {
		btnThemDinh.addActionListener(this);
		btnThemCanh.addActionListener(this);
		btnMoveDinh.addActionListener(this);
		btnDFS.addActionListener(this);
		btnBFS.addActionListener(this);
		btnShortestPath.addActionListener(this);
		btnClean.addActionListener(this);
		btnFile.addActionListener(this);
		rbtnVoHuong.addActionListener(this);
		rbtnCoHuong.addActionListener(this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		// **draws diem**
		if (dsDiem.size() > 0) {
			for (int i = 0; i < dsDiem.size(); i++) {
				dsDiem.get(i).draw(g2);
			}
		}

		// **draws canh**
		if (dsCanh.size() > 0) {
			for (int i = 0; i < dsCanh.size(); i++) {
				dsCanh.get(i).draw(g2);
			}
		}
	}

	public void setBtnEnable() {
		btnThemDinh.setEnabled(true);
		btnThemCanh.setEnabled(true);
		btnMoveDinh.setEnabled(true);
		btnDFS.setEnabled(true);
		btnBFS.setEnabled(true);
		btnShortestPath.setEnabled(true);
		btnClean.setEnabled(true);
	}

	public void setBtnDisable(JButton btn) {
		btnThemDinh.setEnabled(false);
		btnThemCanh.setEnabled(false);
		btnMoveDinh.setEnabled(false);
		btnDFS.setEnabled(false);
		btnBFS.setEnabled(false);
		btnShortestPath.setEnabled(false);
		btnClean.setEnabled(false);
		btn.setEnabled(true);
	}

	public void setStatusBtn(int i, JButton btn) {
		key[i] = !key[i];
		if (key[i])
			setBtnEnable();
		else
			setBtnDisable(btn);
	}

	public void removeAllHandle() {
		panel.removeMouseListener(hdlPoint);
		panel.removeMouseListener(hdlEdge);
		panel.removeMouseMotionListener(hdlMove);
		panel.removeMouseListener(hdlDFS);
		panel.removeMouseListener(hdlBFS);
		panel.removeMouseListener(hdlBellman);
	}

	public void dotDefaultColor() {
		for (int i = 0; i < dsDiem.size(); i++) {
			dsDiem.get(i).setColor(Color.BLACK);
		}
	}

	public void edgeDefaultColor() {
		for (int i = 0; i < dsCanh.size(); i++) {
			dsCanh.get(i).setColor(Color.BLACK);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rbtnCoHuong) {
			type = false;
			System.out.println(type);
		}

		if (e.getSource() == rbtnVoHuong) {
			type = true;
			System.out.println(type);
		}

		if (e.getSource() == btnThemDinh) {
			setStatusBtn(0, btnThemDinh);
			setLabel(Color.GREEN, "Click vào màn hình để tạo 1 điểm");
			removeAllHandle();
			panel.addMouseListener(hdlPoint);

			if (key[0]) {
				removeAllHandle();
				setLabel(Color.GREEN, "");
			}

		} else if (e.getSource() == btnThemCanh) {
			setStatusBtn(1, btnThemCanh);
			removeAllHandle();
			setLabel(Color.GREEN, "Chọn 1 điểm để tạo thành cạnh");
			panel.addMouseListener(hdlEdge);

			if (key[1]) {
				removeAllHandle();
				setLabel(Color.GREEN, "");
			}

		} else if (e.getSource() == btnMoveDinh) {
			setStatusBtn(2, btnMoveDinh);
			setLabel(Color.GREEN, "Di chuyển đỉnh");
			removeAllHandle();
			panel.addMouseMotionListener(hdlMove);

			if (key[2]) {
				removeAllHandle();
				setLabel(Color.GREEN, "");
			}

		} else if (e.getSource() == btnDFS) {
			setStatusBtn(3, btnDFS);
			setLabel(Color.GREEN, "Chọn điểm bắt đầu duyệt DFS");
			removeAllHandle();

			// handle duyet DFS
			panel.addMouseListener(hdlDFS);

			if (key[3]) {
				dotDefaultColor();
				removeAllHandle();
				setLabel(Color.GREEN, "");
				repaint();
			}
		} else if (e.getSource() == btnBFS) {
			setStatusBtn(4, btnBFS);
			setLabel(Color.GREEN, "Chọn điểm bắt đầu duyệt BFS");
			removeAllHandle();

			// handle duyet BFS
			panel.addMouseListener(hdlBFS);

			if (key[4]) {
				dotDefaultColor();
				removeAllHandle();
				setLabel(Color.GREEN, "");
				repaint();
			}
		} else if (e.getSource() == btnShortestPath) {
			setStatusBtn(5, btnShortestPath);
			setLabel(Color.GREEN, "Chọn điểm nguồn để bắt đầu thuật toán");
			removeAllHandle();

			// handle Bellman-Ford
			panel.addMouseListener(hdlBellman);

			if (key[5]) {
				dotDefaultColor();
				edgeDefaultColor();
				removeAllHandle();
				setLabel(Color.GREEN, "");
				repaint();
				way = new ArrayList<>();
				dot1 = new Dot();
				dot2 = new Dot();
				L = null;
				P = null;
				w = new ArrayList<>();
			}
		} else if (e.getSource() == btnClean) {
//			setStatusBtn(6, btnClean);
			removeAllHandle();
			setLabel(Color.GREEN, "Clean!!!");

			dsDiem = new ArrayList<>();
			dsCanh = new ArrayList<>();
			dotID = 0;
			repaint();
			if (key[6]) {
				removeAllHandle();
				setLabel(Color.GREEN, "");
			}
		}
	}

	public void setUp() {
		for (int i = 0; i < dsDiem.size(); i++)
			chuaXet.add(true);

		for (int i = 0; i < dsDiem.size(); i++) {
			Dot d = dsDiem.get(i);
			int dx = d.getX(), dy = d.getY();
			ke.add(new ArrayList<Integer>());
			for (Edge e : dsCanh) {
				int x1 = e.getDot1().getX(), y1 = e.getDot1().getY();
				int x2 = e.getDot2().getX(), y2 = e.getDot2().getY();
				if (Math.abs(x1 - dx) < 25 && Math.abs(y1 - dy) < 25) {
					ke.get(i).add(e.getDot2().getId());
				} else if (Math.abs(x2 - dx) < 25 && Math.abs(y2 - dy) < 25) {
					ke.get(i).add(e.getDot1().getId());
				}

			}
		}
	}

	protected class HandlePoint extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			Dot dot = new Dot();
			int dx = e.getX() + 225;
			int dy = e.getY() + 25;
			dot.setColor(Color.BLACK);
			dot.setX(dx);
			dot.setY(dy);
			dot.setId(dotID);
			dotID++;
			dsDiem.add(dot);
			repaint();
		}
	}

	protected class HandleEdge extends MouseAdapter {
		Edge edge = new Edge();

		public void mouseClicked(MouseEvent e) {
			Dot dots = new Dot();

			// index mouse
			int x = e.getX() + 225;
			int y = e.getY() + 25;

			for (Dot d : dsDiem) {
				int dx = d.getX(), dy = d.getY();
				if ((x <= dx + 25) && (x >= dx - 25) && (y <= dy + 25) && (y >= dy - 25)) {
					d.setColor(Color.GREEN);
					setLabel(Color.RED, "Chọn điểm còn lại để tạo thành cạnh");
					repaint();

					if (edge.has1()) {
						dots.setX(dx);
						dots.setY(dy);
						dots.setId(d.getId());
						edge.setDot2(dots);
						break;
					} else {
						dots.setX(dx);
						dots.setY(dy);
						dots.setId(d.getId());
						edge.setDot1(dots);
						break;
					}
				}
			}

			if (edge.has1() && edge.has2()) {
				edge.setWeight(edge.calcWeight());
				dsCanh.add(edge);
				setLabel(Color.GREEN, "Đã thêm cạnh mới");
				edge = new Edge();
				dotDefaultColor();
				repaint();
			}
		}
	}

	protected class HandleMoveDot extends MouseAdapter implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// index mouse
			int x = e.getX() + 225;
			int y = e.getY() + 25;

			for (Dot d : dsDiem) {
				int dx = d.getX(), dy = d.getY();
				if ((x <= dx + 25) && (x >= dx - 25) && (y <= dy + 25) && (y >= dy - 25)) {
					// handle Edge when move Dot
					moveEdge(d, x, y);
					d.setX(x);
					d.setY(y);
					repaint();
				}
			}
		}

		public void moveEdge(Dot d, int x, int y) {
			int dx = d.getX(), dy = d.getY();
			for (Edge edge : dsCanh) {
				int e1x = edge.getDot1().getX(), e1y = edge.getDot1().getY();
				int e2x = edge.getDot2().getX(), e2y = edge.getDot2().getY();
				if ((e1x <= dx + 25) && (e1x >= dx - 25) && (e1y <= dy + 25) && (e1y >= dy - 25)) {
					edge.getDot1().setX(x);
					edge.getDot1().setY(y);
				} else if ((e2x <= dx + 25) && (e2x >= dx - 25) && (e2y <= dy + 25) && (e2y >= dy - 25)) {
					edge.getDot2().setX(x);
					edge.getDot2().setY(y);
				}
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	// cc
	protected class HandleDFS extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			// setup array ke, array chuaXet for DFS
			setUp();
			// index mouse
			int x = e.getX() + 225;
			int y = e.getY() + 25;
			System.out.println(chuaXet.toString());
			System.out.println(ke.toString());

			for (Dot d : dsDiem) {
				int dx = d.getX(), dy = d.getY();
				if ((x <= dx + 25) && (x >= dx - 25) && (y <= dy + 25) && (y >= dy - 25)) {
					dotDefaultColor();
					repaint();
					d.setColor(Color.GREEN);
					Stack<Integer> stack = new Stack<>();
					StringBuilder result = new StringBuilder();
					result.append("Kết quả Duyệt DFS: ");
					stack.push(d.getId());
					chuaXet.set(d.getId(), false);
					while (!stack.empty()) {
						Integer dot = stack.pop();
						result.append(dot + " ");
						for (Integer u : ke.get(dot)) {
							if (chuaXet.get(u)) {
								stack.push(u);
								chuaXet.set(u, false);
							} else if (!chuaXet.get(u) && stack.contains(u)) {
								stack.push(u);
								stack.remove(stack.indexOf(u));
							}
						}
					}

					setLabel(Color.BLUE, result.toString());
					chuaXet = new ArrayList<>();
					ke = new ArrayList<ArrayList<Integer>>();
					repaint();
				}

			}

		}
	}

	protected class HandleBFS extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// setup arr ke, arr chuaXet for BFS
			setUp();
			// index mouse
			int x = e.getX() + 225;
			int y = e.getY() + 25;
			System.out.println(chuaXet.toString());
			System.out.println(ke.toString());

			for (Dot d : dsDiem) {
				int dx = d.getX(), dy = d.getY();
				if ((x <= dx + 25) && (x >= dx - 25) && (y <= dy + 25) && (y >= dy - 25)) {
					dotDefaultColor();
					repaint();
					d.setColor(Color.GREEN);
					Queue<Integer> queue = new LinkedList<>();
					StringBuilder result = new StringBuilder();
					result.append("Kết quả Duyệt BFS: ");
					queue.offer(d.getId());
					chuaXet.set(d.getId(), false);
					while (!queue.isEmpty()) {
						Integer dot = queue.poll();
						result.append(dot + " ");
						for (Integer u : ke.get(dot)) {
							if (chuaXet.get(u)) {
								queue.offer(u);
								chuaXet.set(u, false);
							}
						}
					}

					setLabel(Color.BLUE, result.toString());
					chuaXet = new ArrayList<>();
					ke = new ArrayList<ArrayList<Integer>>();
					repaint();
				}

			}

		}
	}

	protected class HandleBellman extends MouseAdapter {

		public void setUpBellmanFord() {
			// setUp L, P
			L = new int[dsDiem.size()];
			P = new int[dsDiem.size()];

			for (int i = 0; i < dsDiem.size(); i++) {
				P[i] = -1;
				L[i] = 1000;
			}

			// setUp w
			for (int i = 0; i < dsDiem.size(); i++)
				w.add(new ArrayList<Integer>());

			for (int i = 0; i < dsDiem.size(); i++)
				for (int j = 0; j < dsDiem.size(); j++) {
					if (i == j)
						w.get(i).add(0);
					else
						w.get(i).add(getWeighById(i, j));
				}
		}

		public void startBellManFord(int start) {
			boolean stop = false;
			int k = 0;

			while (!stop) {
				stop = true;
				k = k + 1;
				for (int i = 0; i < L.length; i++) {
					for (int j = 0; j < L.length; j++) {
						if (w.get(i).get(j) > 0 && w.get(i).get(j) < 1000) {
							if (L[j] > L[i] + w.get(i).get(j)) {
								L[j] = L[i] + w.get(i).get(j);
								P[j] = i;
								stop = false;
							}
						}
					}
				}
				if (k > dsDiem.size()) {
					if (stop == false) {
						System.out.println("Đồ thị có chu trình âm");
						stop = true;
					}
				}
			}
		}

		public int getWeighById(int start, int end) {
			int w = 0;
			for (Edge edge : dsCanh) {
				if ((edge.getDot1().getId() == start && edge.getDot2().getId() == end)
						|| (edge.getDot1().getId() == end && edge.getDot2().getId() == start)) {
					w = edge.getWeight();
					break;
				}
			}

			return w;
		}

		public Edge getEdgeById(int id1, int id2) {
			Edge res = new Edge();
			for (Edge edge : dsCanh)
				if ((edge.getDot1().getId() == id1 && edge.getDot2().getId() == id2)
						|| (edge.getDot1().getId() == id2 && edge.getDot2().getId() == id1)) {
					res = edge;
					break;
				}
			return res;
		}

		public String getMartrixW() {
			StringBuilder martrix = new StringBuilder();

			for (ArrayList<Integer> item : w) {
				for (Integer i : item) {
					if (i < 10)
						martrix.append("  " + i + "       ");
					else
						martrix.append(i + "       ");
				}
				martrix.append("\n\n");
			}

			return martrix.toString();
		}

		public String getPL() {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < L.length; i++) {
				result.append("[" + P[i] + "," + L[i] + "]" + "  ");
			}

			return result.toString();
		}

		public ArrayList<Integer> getArrWay() {
			ArrayList<Integer> arr = new ArrayList<>();
			arr.add(dot2.getId());

			while (arr.get(0) != -1)
				arr.add(0, P[arr.get(0)]);

			arr.remove(0);
			return arr;
		}

		public void mouseClicked(MouseEvent e) {

			// index mouse
			int x = e.getX() + 225;
			int y = e.getY() + 25;
			dot1.setColor(Color.GREEN);
			dot2.setColor(Color.BLACK);
			edgeDefaultColor();
			repaint();
			for (Dot d : dsDiem) {
				int dx = d.getX(), dy = d.getY();
				if ((x <= dx + 25) && (x >= dx - 25) && (y <= dy + 25) && (y >= dy - 25)) {
					// set dot1
					if (dot1.getX() == 0 && dot1.getX() == 0) {
						dot1 = d;
						dotDefaultColor();
						edgeDefaultColor();
						repaint();
						dot1.setColor(Color.GREEN);

						// set up w, L, P
						setUpBellmanFord();

						// set điểm bắt đầu
						L[d.getId()] = 0;

						// bắt đâu thuật toán
						startBellManFord(d.getId());

						// view
						setTextMatranW(getMartrixW(), getPL());

					} else {
						dot2 = d;
						dot2.setColor(Color.BLUE);

						// setLabel đường đi từ dot1 -> dot2
						StringBuilder duongdi = new StringBuilder();
						duongdi.append("(" + dot1.getId() + ")" + " -> " + "(" + dot2.getId() + "):  ");
						way = getArrWay();

						// vẽ đường đi từ dot1 tới dot2
						for (int i = 0; i < way.size() - 1; i++)
							getEdgeById(way.get(i), way.get(i + 1)).setColor(Color.RED);
						;

						setLabel(Color.BLUE,duongdi + way.toString() + "    L = " + L[dot2.getId()]);
						repaint();
					}

				}
			}
		}

	}
}
