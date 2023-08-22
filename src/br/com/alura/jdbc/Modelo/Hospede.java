package br.com.alura.jdbc.Modelo;

import java.sql.Date;
import java.time.LocalDate;

public class Hospede {

	private Integer id;
	private String nome;
	private String sobrenome;
	private LocalDate data_nascimento;
	private String nacionalidade;
	private String telefone;
	private Integer reserva_id;

	public Hospede(String nome, String sobrenome, LocalDate data_nascimento, String nacionalidade, String telefone, Integer reserva_id) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.data_nascimento = data_nascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
		this.reserva_id = reserva_id;
	}
	public Hospede(Integer id, String nome, String sobrenome, LocalDate data_nascimento, String nacionalidade,
			String telefone, Integer reserva_id) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.data_nascimento = data_nascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
		this.reserva_id = reserva_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public LocalDate getData_nascimento() {
		return data_nascimento;
	}
	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Integer getReserva_id() {
		return reserva_id;
	}
	public void setReserva_id(Integer reserva_id) {
		this.reserva_id = reserva_id;
	}
	@Override
	public String toString() {
		return String.format("{Hospede ID: %s, Nome: %s, Sobrenome: %s, Reserva ID: %d}",
				this.id, this.nome, this.sobrenome, this.reserva_id);
	}
}
