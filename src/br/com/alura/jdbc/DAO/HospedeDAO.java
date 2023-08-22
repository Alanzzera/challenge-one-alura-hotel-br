package br.com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.alura.jdbc.Modelo.Hospede;

public class HospedeDAO {

	private Connection connection;

	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}

	public void inserir(Hospede hospede) {
		try {
			String sql = 
					"INSERT INTO HOSPEDE (NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE, ID_RESERVA) VALUES (?,?,?,?,?,?)";
			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setString(1, hospede.getNome());
				pstm.setString(2, hospede.getSobrenome());
				pstm.setObject(3, hospede.getData_nascimento());
				pstm.setString(4, hospede.getNacionalidade());
				pstm.setString(5, hospede.getTelefone());
				pstm.setInt(6, hospede.getReserva_id());

				pstm.execute();

				try(ResultSet rst = pstm.getGeneratedKeys()){
					while(rst.next()) {
						hospede.setId(rst.getInt(1));
					}
				}
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Erro ao realizar a reserva.","Erro ao guardar os dados.",JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(e);
		}
	}
	public int alterar(String nome, String sobrenome, Date data_nascimento, String nacionalidade, String telefone, Integer id) {
		try {
			String sql = 
					"UPDATE HOSPEDE SET nome = ?, sobrenome = ?, data_nascimento = ?, nacionalidade = ?, telefone = ? WHERE id = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setString(1, nome);
				pstm.setString(2, sobrenome);
				pstm.setDate(3, data_nascimento);
				pstm.setString(4, nacionalidade);
				pstm.setString(5, telefone);
				pstm.setInt(6, id);
				pstm.execute();
				int updateCount = pstm.getUpdateCount();
				return updateCount;
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Erro ao alterar os dados.","Tente mais tarde.",JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(e);
		}
	}
	public int deletar(Integer id, Integer reserva_id){
		try{
			String sql = "DELETE FROM HOSPEDE WHERE ID = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, id);
				pstm.execute();
				deletarReserva(reserva_id);
				int updateCount = pstm.getUpdateCount();
				return updateCount;
			}
		}catch (SQLException e) {JOptionPane.showMessageDialog(null, "Erro ao deletar Hospede", "Tente mais tarde.", JOptionPane.ERROR_MESSAGE
				);
		throw new RuntimeException(e);
		}
	}
	public void deletarReserva(Integer id){
		String sql = "DELETE FROM RESERVA WHERE ID = ?";
		try{
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, id);
				pstm.execute();
			}
		}catch (SQLException e) {JOptionPane.showMessageDialog(null, "Erro ao deletar Reserva", "Tente mais tarde.", JOptionPane.ERROR_MESSAGE
				);
		throw new RuntimeException(e);
		}
	}
//	public List<Hospede> listar(){
//		List<Hospede> hospedes = new ArrayList<>();
//		String sql = "SELECT ID, NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE, RESERVA_ID FROM HOSPEDE";
//		try {
//			try(PreparedStatement pstm = connection.prepareStatement(sql)){
//				pstm.execute();
//				ResultSet rst = pstm.getResultSet();
//				while(rst.next()) {
//					Hospede hospede = 
//							new Hospede(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDate(4), rst.getString(5), rst.getString(6), rst.getInt(7));
//					hospedes.add(hospede);
//				}
//				return hospedes;
//			}
//		}
//		catch (SQLException e) {
//			JOptionPane.showMessageDialog(null,"Tente mais tarde.","Erro ao trazer os dados.",JOptionPane.ERROR_MESSAGE);
//			throw new RuntimeException(e);
//		}
//	}
//	public List<Hospede> listar(String nome){
//		List<Hospede> hospedes = new ArrayList<>();
//		try {
//			String sql = "SELECT ID, NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE, RESERVA_ID FROM HOSPEDE WHERE NOME LIKE ?";
//			try(PreparedStatement pstm = connection.prepareStatement(sql)){
//				pstm.setString(1, nome.concat("%"));
//				pstm.execute();
//
//				try(ResultSet rst = pstm.getResultSet()){
//					while(rst.next()) {
//						Hospede hospede = 
//								new Hospede(rst.getInt(1), rst.getString(2), rst.getString(4), rst.getDate(5), rst.getString(6), rst.getString(7), rst.getInt(8));
//						hospedes.add(hospede);
//					}
//				}
//			}
//			return hospedes;
//		}catch (SQLException e) {
//			JOptionPane.showMessageDialog(null,"Tente mais tarde.","Erro ao trazer os dados.",JOptionPane.ERROR_MESSAGE);
//			throw new RuntimeException(e);
//		}
//	}
}

