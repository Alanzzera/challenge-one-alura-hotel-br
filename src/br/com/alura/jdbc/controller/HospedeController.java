package br.com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.jdbc.DAO.HospedeDAO;
import br.com.alura.jdbc.Modelo.Hospede;
import br.com.alura.jdbc.factory.ConnectionFactory;

public class HospedeController {

	private HospedeDAO hospedeDAO;

	public HospedeController(){
		try{
			Connection connection =	new ConnectionFactory().recuperaConexao();
			this.hospedeDAO = new HospedeDAO(connection);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void inserir(Hospede hospede) {
		hospede.getReserva_id();
		this.hospedeDAO.inserir(hospede);
	}
	public void alterar(String nome, String sobrenome, Date data_nascimento, String nacionalidade, String telefone, Integer id_reserva, Integer id) {
		this.hospedeDAO.alterar(nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva, id);
	}
	public void deletar(Integer id_reserva) {
		this.hospedeDAO.deletar(id_reserva);
	}
	public List<Hospede> listar(){
		return this.hospedeDAO.listar();
	}
	public List<Hospede> buscar(String id){
		return this.hospedeDAO.buscar(id);
	}
	
}

