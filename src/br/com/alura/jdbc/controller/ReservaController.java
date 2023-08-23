package br.com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.alura.jdbc.DAO.ReservaDAO;
import br.com.alura.jdbc.Modelo.Reserva;
import br.com.alura.jdbc.factory.ConnectionFactory;

public class ReservaController {

	private ReservaDAO reservaDAO;
	
	public ReservaController(){
		try{
			Connection connection = new ConnectionFactory().recuperaConexao();
			this.reservaDAO = new ReservaDAO(connection);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public List<Reserva> listar() {
		return this.reservaDAO.listar();
	}
	public List<Reserva> buscar(String id) {
        return reservaDAO.buscar(id);
    }
	public void inserir(Reserva reserva) throws SQLException {
	    this.reservaDAO.inserir(reserva);
	}
    public void alterar(LocalDate dataE, LocalDate dataS, String valor, String formaPagamento, Integer id) {
        this.reservaDAO.alterar(dataE, dataS, valor, formaPagamento, id);
    }
    public void deletar(Integer id) {
    	this.reservaDAO.deletar(id);
    }
}
