package br.com.alura.jdbc.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Reserva {

	private Object id;
	private LocalDate data_entrada;
	private LocalDate data_saida;
	private String valor;
	private String forma_pagamento;
	private List<Hospede> hospedes = new ArrayList<Hospede>();

	public Reserva(LocalDate dataE, LocalDate dataS, String string, String forma_pagamento) {
		this.id = generarIdReserva();
		this.data_entrada = dataE;
		this.data_saida = dataS;
		this.valor = string;
		this.forma_pagamento = forma_pagamento;
	}
	public Reserva(String id, LocalDate data_entrada, LocalDate data_saida, String valor, String forma_pagamento) {
		this.id = id;
		this.data_entrada = data_entrada;
		this.data_saida = data_saida;
		this.valor = valor;
		this.forma_pagamento = forma_pagamento;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public LocalDate getData_entrada() {
		return data_entrada;
	}
	public void setData_entrada(LocalDate data_entrada) {
		this.data_entrada = data_entrada;
	}
	public LocalDate getData_saida() {
		return data_saida;
	}
	public void setData_saida(LocalDate data_saida) {
		this.data_saida = data_saida;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getForma_pagamento() {
		return forma_pagamento;
	}
	public void setForma_pagamento(String forma_pagamento) {
		this.forma_pagamento = forma_pagamento;
	}

	public void adicionar(Hospede hospede) {
		hospedes.add(hospede);
	}
	public List<Hospede> getHospedes() {
		return hospedes;
	}
    private String generarIdReserva() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
	public String toString() {
		return String.format("{ID Reserva: %s, Data Checkin: %s, Data Cherkout: %s, Total: %f, Forma ago: %s}",
				this.id, this.data_entrada, this.data_saida, this.valor, this.forma_pagamento);
	}
}
