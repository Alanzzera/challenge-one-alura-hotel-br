package br.com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.alura.jdbc.Modelo.Reserva;


public class ReservaDAO {

	private Connection connection;

	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}

	public void inserir(Reserva reserva){
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement pstm = 
					connection.prepareStatement
					("INSERT INTO RESERVA (DATA_ENTRADA, DATA_SAIDA, VALOR, FORMA_PAGAMENTO) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
				pstm.setObject(1, reserva.getData_entrada());
				pstm.setObject(2, reserva.getData_saida());
				pstm.setString(3, reserva.getValor());
				pstm.setString(4, reserva.getForma_pagamento());

				pstm.execute();
				
				try(ResultSet rst = pstm.getGeneratedKeys()){
					while(rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				connection.commit();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
            System.out.println("ROLLBACK EXECUTADO");
            try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int alterar(String id, Date data_entrada, Date data_saida, Double valor, String forma_pagamento) {
		try {
			String sql = 
					"UPDATE reserva SET data_entrada = ?, data_saida = ?, valor = ?, forma_pago = ? WHERE id_reserva = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setDate(1, data_entrada);
				pstm.setDate(2, data_saida);
				pstm.setDouble(3, valor);
				pstm.setString(4, forma_pagamento);
				pstm.setString(5, id);

				pstm.execute();
				int updateCount = pstm.getUpdateCount();
				return updateCount;
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o registro.");
			throw new RuntimeException(e);
		}
	}
	public List<Reserva> listar(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento FROM RESERVA";

			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.execute();
				
				ResultSet rst = pstm.getResultSet();
				while(rst.next()) {
					LocalDate dataE = rst.getDate("data_entrada").toLocalDate().plusDays(1);
					LocalDate dataS = rst.getDate("data_saida").toLocalDate().plusDays(1);
					String valor = rst.getString("valor");
					Reserva reserva = 
							new Reserva(rst.getString(1), dataE, dataS, valor, rst.getString("forma_pagamento"));
					//rst.getString("id"), rst.getDate("data_entrada"), rst.getDate("data_saida"), rst.getDouble("valor"), rst.getString("forma_pagamento")
					reservas.add(reserva);
				}
			}
			return reservas;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Reserva> buscar(String id){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento FROM RESERVA WHERE ID LIKE ?";

			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.setString(1, id);
				pstm.execute();
				
				ResultSet rst = pstm.getResultSet();
				while(rst.next()) {
					LocalDate dataE = rst.getDate("data_entrada").toLocalDate().plusDays(1);
					LocalDate dataS = rst.getDate("data_saida").toLocalDate().plusDays(1);
					String valor = rst.getString("valor");
					Reserva reserva = 
							new Reserva(rst.getString(1), dataE, dataS, valor, rst.getString("forma_pagamento"));
					//rst.getString("id"), rst.getDate("data_entrada"), rst.getDate("data_saida"), rst.getDouble("valor"), rst.getString("forma_pagamento")
					reservas.add(reserva);
				}
			}
			return reservas;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
//	public void deletar(Integer id){
//		try{
//			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM RESERVA WHERE ID = ?")) {
//				stm.setInt(1, id);
//				stm.execute();
//			}
//		}catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
}
