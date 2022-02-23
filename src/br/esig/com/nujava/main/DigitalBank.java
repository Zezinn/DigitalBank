package br.esig.com.nujava.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import br.esig.com.nujava.dominio.Conta;
import br.esig.com.nujava.dominio.Genero;
import br.esig.com.nujava.negocio.TransacaoHelper;
import br.esig.com.nujava.negocio.ValidacaoHelper;


public class DigitalBank {

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        TransacaoHelper transacaoHelper = new TransacaoHelper();

        List<Conta> contas = new ArrayList<Conta>();

        String opcao = "";

        boolean sair = false;

        Scanner entrada = new Scanner(System.in);

        System.out.println("*------------------------------------------------------------*");
        System.out.println("|Java Digital Bank                                       |");
        System.out.println("|Nesta aplicação você poderá: criar conta, depositar, sacar e transferir.|");
        System.out.println("*------------------------------------------------------------*");

        try {
            do {
                System.out.println("*------------------------------------------------------------*");
                System.out.println("|0 - Sair da aplicação                                                  |");
                System.out.println("|1 - Criar nova conta                                          |");
                System.out.println("|2 - Listar todas as contas criadas                               |");
                System.out.println("|3 - Exibir dados bancários por CPF                       |");
                System.out.println("|4 - Consultar saldo por CPF                          |");
                System.out.println("|5 - Solicitar cartão de crédito                           |");
                System.out.println("|6 - Sacar                                                   |");
                System.out.println("|7 - Depositar                                               |");
                System.out.println("|8 - Transferir                                              |");
                System.out.println("*------------------------------------------------------------*");

                System.out.print("Informe a opção: ");
                opcao = entrada.nextLine();

                switch (opcao) {
                    case "0":
                        System.out.println("*------------------------------------------------------------*");
                        System.out.println("|ENCERRANDO APLICAÇÃO...                                   |");
                        System.out.println("|OBRIGADO POR USAR NOSSO BANCO!                                                   |");
                        System.out.println("*------------------------------------------------------------*");
                        sair = true;

                        break;
                    case "1":
                        System.out.println("-> CRIAR CONTA");

                        Conta c1 = new Conta();
                        System.out.print("Informe o nome da pessoa: ");
                        c1.getPessoa().setNome(entrada.nextLine());

                        System.out.print("Informe o CPF da pessoa sem numero e sem traço: ");
                        c1.getPessoa().setCpf(entrada.nextLine());

                        System.out.print("Informe o genêro da pessoa (Masculino ou Feminino): ");
                        c1.getPessoa().setGenero(Genero.modificarStringToGenero(entrada.nextLine()));

                        System.out.print("Informe a data de nascimento da pessoa: ");
                        String dataNascimento = entrada.nextLine();

                        if(!dataNascimento.isEmpty()) {
                            c1.getPessoa().setDataNascimento(simpleDateFormat.parse(dataNascimento));
                        }

                        if(ValidacaoHelper.isPossivelCadastrarConta(c1)) {
                            contas.add(c1);

                            System.out.println("Conta criada com sucesso!");
                        }

                        break;
                    case "2":
                        System.out.println("-> LISTAR CONTAS");

                        if(!contas.isEmpty()) {
                            for (Conta conta : contas) {
                                System.out.println("Número:" + conta.getNumero() + "Agência: " + conta.getCodigo());
                            }
                        }

                        break;
                    case "3":
                        System.out.println("-> DADOS BANCÁRIOS");

                        System.out.print("Informe o CPF da pessoa: ");
                        String cpfDados = entrada.nextLine();

                        if(ValidacaoHelper.isContaExistente(contas, cpfDados)) {
                            for (Conta conta : contas) {
                                if(conta.getPessoa().getCpf().equals(cpfDados)) {
                                    transacaoHelper.exibirDadosBancarios(conta);
                                }
                            }
                        }

                        break;
                    case "4":
                        System.out.println("-> SALDO");

                        System.out.print("Informe o CPF da pessoa: ");
                        String cpfSaldo = entrada.nextLine();

                        if(ValidacaoHelper.isContaExistente(contas, cpfSaldo)) {
                            for (Conta conta : contas) {
                                if(conta.getPessoa().getCpf().equals(cpfSaldo)) {
                                    System.out.println("O saldo é de: " + conta.getSaldo());
                                }
                            }
                        }
                        break;
                    case "5":
                        System.out.println("-> SOLICITAR CARTÃO");

                        System.out.print("Informe o CPF da pessoa: ");
                        String cpfCartao = entrada.nextLine();

                        if(ValidacaoHelper.isContaExistente(contas, cpfCartao)) {
                            for (Conta conta : contas) {
                                if(conta.getPessoa().getCpf().equals(cpfCartao)) {
                                    transacaoHelper.solicitarCartao(conta);
                                }
                            }
                        }

                        break;
                    case "6":
                        System.out.println("-> SAQUE");

                        System.out.print("Informe o CPF da pessoa: ");
                        String cpfSacar = entrada.nextLine();

                        if(ValidacaoHelper.isContaExistente(contas, cpfSacar)) {
                            String valor;

                            System.out.print("Informe o valor do saque: ");
                            valor = entrada.nextLine();

                            for (Conta conta : contas) {
                                if(conta.getPessoa().getCpf().equals(cpfSacar)) {
                                    transacaoHelper.sacar(conta, Double.parseDouble(valor));
                                }
                            }
                        }

                        break;
                    case "7":
                        System.out.println("-> DEPÓSITO");

                        System.out.print("Informe o CPF da pessoa: ");
                        String cpfDeposito = entrada.nextLine();

                        if(ValidacaoHelper.isContaExistente(contas, cpfDeposito)) {
                            String valor;

                            System.out.print("Informe o valor do depósito: ");
                            valor = entrada.nextLine();

                            for (Conta conta : contas) {
                                if(conta.getPessoa().getCpf().equals(cpfDeposito)) {
                                    transacaoHelper.depositar(conta, Double.parseDouble(valor));
                                }
                            }
                        }

                        break;
                    case "8":
                        System.out.println("-> TRANSFERÊNCIA");

                        System.out.print("Informe o CPF do depositante: ");
                        String cpfDepositante = entrada.nextLine();

                        System.out.print("Informe o CPF do recebedor: ");
                        String cpfRecebedor = entrada.nextLine();

                        if(ValidacaoHelper.isContaExistente(contas, cpfDepositante)
                                && ValidacaoHelper.isContaExistente(contas, cpfRecebedor)) {
                            Conta contaDepositante = null, contaRecebedor = null;
                            String valor;

                            System.out.print("Informe o valor do depósito: ");
                            valor = entrada.nextLine();

                            for (Conta conta : contas) {
                                if(conta.getPessoa().getCpf().equals(cpfDepositante)) {
                                    contaDepositante = conta;
                                }else if (conta.getPessoa().getCpf().equals(cpfRecebedor)){
                                    contaRecebedor = conta;
                                }
                            }

                            transacaoHelper.transferir(contaDepositante, contaRecebedor, Double.parseDouble(valor));
                        }

                        break;
                    default:
                        break;
                }
            }while(!sair);

        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            entrada.close();
        }
    }

}