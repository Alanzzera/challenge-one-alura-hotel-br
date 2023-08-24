package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.alura.jdbc.Modelo.Hospede;
import br.com.alura.jdbc.Modelo.Reserva;
import br.com.alura.jdbc.controller.HospedeController;
import br.com.alura.jdbc.controller.ReservaController;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("serial")
public class Buscar extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHospedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHospedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ReservaController reservaController;
	private HospedeController hospedeController;
	private ReservasView reservasView;
	String reserva;
	String hospede;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Buscar() throws SQLException{

		reservaController = new ReservaController();
		hospedeController = new HospedeController();
		reservasView = new ReservasView();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Buscar.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblTitulo = new JLabel("SISTEMA DE BUSCA");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblTitulo.setBounds(331, 62, 280, 42);
		contentPane.add(lblTitulo);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Data Check In");
		modelo.addColumn("Data Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		preencherTabelaReserva();

		tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("Numero de Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Numero de Reserva");
		preencherTabelaHospede();
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHospedes);
		panel.addTab("Hóspedes", new ImageIcon(Buscar.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Buscar.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { // Quando o usuário passa o mouse sobre o botão, ele muda de cor
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Quando o usuário remove o mouse do botão, ele retornará ao estado original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limparTabela();
				if(txtBuscar.getText().isEmpty()) {
					preencherTabelaReserva();
					preencherTabelaHospede();
				}else {
					buscarTabelaReserva();
					buscarTabelaHospede();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int listaReserva = tbReservas.getSelectedRow();
				if(listaReserva >= 0){
					alterarTabelaReserva();
					limparTabela();
					preencherTabelaReserva();
				}
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnDeletar = new JPanel();
		btnDeletar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int listaReservas = tbReservas.getSelectedRow();
				if(listaReservas >=0){
					reserva = tbReservas.getValueAt(listaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "Deseja excluir esse registro?");
					if(confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(listaReservas, 0).toString();
						reservaController.deletar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro deletado com sucesso.");
						limparTabela();
						preencherTabelaReserva();
						preencherTabelaHospede();
					
					}
				}
			}
		});
		
		btnDeletar.setLayout(null);
		btnDeletar.setBackground(new Color(12, 138, 199));
		btnDeletar.setBounds(767, 508, 122, 35);
		btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnDeletar);

		JLabel lblExcluir = new JLabel("DELETAR");
		lblExcluir.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluir.setForeground(Color.WHITE);
		lblExcluir.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblExcluir.setBounds(0, 0, 122, 35);
		btnDeletar.add(lblExcluir);
		setResizable(false);

	}
	private List<Reserva> listarReserva(){
		return this.reservaController.listar();
	}
	private List<Reserva> buscarReserva(){
		return this.reservaController.buscar(txtBuscar.getText());
	}

	private void preencherTabelaReserva(){
		List<Reserva> reservas = listarReserva();
		modelo.setRowCount(0);
		try {
			for (Reserva reserva : reservas) {
				modelo.addRow(new Object[] 
						{reserva.getId(), reserva.getData_entrada(), reserva.getData_saida(), reserva.getValor(), reserva.getForma_pagamento()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	private void buscarTabelaReserva(){
		List<Reserva> reservas = buscarReserva();
		try {
			for (Reserva reserva : reservas) {
				modelo.addRow(new Object[] 
						{reserva.getId(), reserva.getData_entrada(), reserva.getData_saida(), reserva.getValor(), reserva.getForma_pagamento()});
			}
		} catch (Exception e) {
			throw e;
		}

	}
	public void alterarTabelaReserva() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
		.ifPresent(fila ->{
			LocalDate dataE;
			LocalDate dataS;
			try {
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				dataE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(),dateFormat);
				dataS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(),dateFormat);
			}catch (DateTimeException e) {
				throw new RuntimeException(e);
			}
			if (dataE.isAfter(dataS)) {
				JOptionPane.showMessageDialog(this, "A data de check-in não pode ser posterior à data de check-out", "Erro", JOptionPane.ERROR_MESSAGE);
				return; // Retorna sem prosseguir com a alteração
			}
			this.reservasView.limparValor();
			String valor = calcularValorReserva(dataE, dataS);
			String formaPagamento = (String)modelo.getValueAt(tbReservas.getSelectedRow(), 4);
			Integer id = Integer.valueOf((String) modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
			if(tbReservas.getSelectedColumn()==0) {
				JOptionPane.showMessageDialog(contentPane, "Não pode Alterar o ID");
			}else {
				this.reservaController.alterar(dataE, dataS, valor, formaPagamento, id);
				JOptionPane.showMessageDialog(contentPane, "Alterado com Sucesso");
			}
		});
	}
	public String calcularValorReserva(LocalDate dataE, LocalDate dataS){
		if(dataE !=null && dataS !=null) {
			int dias = (int) ChronoUnit.DAYS.between(dataE, dataS);
			int valorDia = 80;
			int valor = dias * valorDia;
			return Integer.toString(valor);
		}else {
			return "";
		}
	}
	
	private List<Hospede> listarHospede(){
		return this.hospedeController.listar();
	}
	private List<Hospede> buscarHospede(){
		return this.hospedeController.buscar(txtBuscar.getText());
	}
	
	private void preencherTabelaHospede(){
		List<Hospede> hospedes = listarHospede();
		modeloHospedes.setRowCount(0);
		try {
			for (Hospede hospede : hospedes) {
				modeloHospedes.addRow(new Object[] 
						{hospede.getId(), hospede.getNome(), hospede.getSobrenome(), hospede.getData_nascimento(), hospede.getNacionalidade(), hospede.getTelefone(), hospede.getReserva_id()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	private void buscarTabelaHospede(){
		List<Hospede> hospedes = buscarHospede();
		modeloHospedes.setRowCount(0);
		try {
			for (Hospede hospede : hospedes) {
				modeloHospedes.addRow(new Object[] 
						{hospede.getId(), hospede.getNome(), hospede.getSobrenome(), hospede.getData_nascimento(), hospede.getNacionalidade(), hospede.getTelefone(), hospede.getReserva_id()});
			}
		} catch (Exception e) {
			throw e;
		}

	}
	private void limparTabela() {
		((DefaultTableModel) tbHospedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	//Código que permite movimentar a janela pela tela seguindo a posição de "x" e "y"	
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}
	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
