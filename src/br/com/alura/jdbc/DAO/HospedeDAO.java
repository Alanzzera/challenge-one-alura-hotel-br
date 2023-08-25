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
	public void alterar(String nome, String sobrenome, Date data_nascimento, String nacionalidade, String telefone, Integer id_reserva, Integer id) {
		try {
			String sql = 
					"UPDATE HOSPEDE SET nome = ?, sobrenome = ?, data_nascimento = ?, nacionalidade = ?, telefone = ?, id_reserva = ? WHERE id = ?";	
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, nome);
				pstm.setString(2, sobrenome);
				pstm.setObject(3, data_nascimento);
				pstm.setString(4, nacionalidade);
				pstm.setString(5, telefone);
				pstm.setInt(6, id_reserva);
				pstm.setInt(7, id);
				pstm.execute();
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void deletar(Integer id	){
		try{
			String sql = "DELETE FROM HOSPEDE WHERE ID = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, id);
				pstm.execute();
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Hospede> listar(){
		List<Hospede> hospedes = new ArrayList<>();
		
		try {
			String sql = "SELECT ID, NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE, ID_RESERVA FROM HOSPEDE";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				transformar(hospedes, pstm);
			}
			return hospedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Hospede> buscar(String id){
		List<Hospede> hospedes = new ArrayList<>();
		try {
			String sql = "SELECT ID, NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE, ID_RESERVA FROM HOSPEDE WHERE ID = ?";
			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.setString(1, id);
				pstm.execute();
				transformar(hospedes, pstm);
			}
			return hospedes;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private void transformar(List<Hospede> hospedes, PreparedStatement pstm)throws SQLException {
		try(ResultSet rst = pstm.getResultSet()){
			while(rst.next()) {
				int id = rst.getInt("id");
				String nome = rst.getString("nome");
				String sobrenome = rst.getString("sobrenome");
				LocalDate data_nascimento = rst.getDate("data_nascimento").toLocalDate().plusDays(1);
				String nacionalidade = rst.getString("nacionalidade");
				String telefone = rst. getString("telefone");
				int id_reserva = rst.getInt("id_reserva");

				Hospede hospede = new Hospede(id, nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva);
				hospedes.add(hospede);
			}
		}
	}
}

