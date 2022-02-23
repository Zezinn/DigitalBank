package br.esig.com.nujava.interfaces;

import java.text.ParseException;

import br.esig.com.nujava.dominio.Conta;


public interface Operacoes {


    public void sacar(Conta conta, double valor);


    public void depositar(Conta conta, double valor);


    public void transferir(Conta suaConta, Conta contaDestino, double valor);


    public void solicitarCartao(Conta conta) throws ParseException;


    public void exibirDadosBancarios(Conta conta);
}