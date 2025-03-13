import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banco {
	private static final int AGENCIA_PADRAO = 1;
	private String nome;
	private List<Conta> contas;

	public Banco() {
		this.nome = "Meu banco.";
		this.contas = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public void adicionarConta() {
		Scanner scanner = new Scanner(System.in);
		// Create a new object of the class Cliente
		Cliente novoCliente = new Cliente();
		System.out.println("Informe o nome do cliente: ");
		novoCliente.setNome(scanner.nextLine());

		// Variable to store the option selected by the user
		int opcao = -1;
		System.out.println("Qual tipo de conta deseja adicionar?");
		System.out.println("1 - Conta Corrente");
		System.out.println("2 - Conta Poupança");
		System.out.println("0 - Sair");
		System.out.println("Digite uma opção.:");
		opcao = scanner.nextInt();
		scanner.nextLine();
		while (opcao != 0) {
			switch (opcao) {
				case 1:
					Conta contaCorrente = new ContaCorrente(novoCliente);
					this.contas.add(contaCorrente);
					System.out.println("Conta " + contaCorrente.numero + " adicionada com sucesso.");
					opcao = 0;
					break;
				case 2:
					Conta contaPConta = new ContaPoupanca(novoCliente);
					this.contas.add(contaPConta);
					System.out.println("Conta " + contaPConta.numero + " adicionada com sucesso.");
					opcao = 0;
					break;
				case 0:
					System.out.println("Saindo...");
					break;
				default:
					System.out.println("Opção inválida");
					break;
			}
		}
		scanner.close();
	}

	public Conta buscarConta(int agencia, int conta) {
		if (this.contas.isEmpty()) {
			System.out.println("Não existem contas cadastradas.");
			return null;
		}

		return this.contas.stream()
				.filter(c -> c.getAgencia() == agencia && c.getNumero() == conta)
				.findFirst()
				.orElseGet(() -> {
					System.out.println("Conta não encontrada.");
					return null;
				});
	}

	private void menuBanco() {
		System.out.println("== Bem-vindo ao " + this.nome + " ==");
		System.out.println("1 - Adicionar conta.");
		System.out.println("2 - Selecionar conta.");
		System.out.println("0 - Sair.");

	}

	public void abrirBanco() {
		Scanner scanner = new Scanner(System.in);
		int opcao = -1;
		this.menuBanco();
		System.out.println("Digite uma opção.:");
		opcao = scanner.nextInt();
		scanner.nextLine();

		while (opcao != 0) {
			if (opcao == 1) {
				this.adicionarConta();
				this.menuBanco();
			} else if (opcao == 2) {
				System.out.println("Informar o numero da conta: ");
				int conta = scanner.nextInt();
				scanner.nextLine();
				Conta contaSelecionada = this.buscarConta(AGENCIA_PADRAO, conta);
				if (contaSelecionada != null) {
					this.opcoesDaConta(contaSelecionada);
					this.menuBanco();
				}
			} else if (opcao == 0) {
				System.out.println("Saindo...");
			} else {
				System.out.println("Opção inválida.");
			}
			System.out.println("Digite uma opção.:");
			opcao = scanner.nextInt();
			scanner.nextLine();
		}
		scanner.close();
	}

	private void menuDaConta(Conta conta) {
		System.out.println("== Olá " + conta.cliente.getNome() + " ==");
		System.out.println("1 - Sacar.");
		System.out.println("2 - Depositar.");
		System.out.println("3 - Transferir.");
		System.out.println("4 - Imprimir extrato.");
		System.out.println("0 - Sair.");
	}

	public void opcoesDaConta(Conta conta) {
		Scanner scanner = new Scanner(System.in);
		int opcao = -1;
		this.menuDaConta(conta);
		System.out.println("Digite uma opção.:");
		opcao = scanner.nextInt();
		scanner.nextLine();

		while (opcao != 0) {
			switch (opcao) {
				case 1:
					System.out.println("Informe o valor do saque: ");
					double valorSaque = scanner.nextDouble();
					scanner.nextLine();
					conta.sacar(valorSaque);
					break;
				case 2:
					System.out.println("Informe o valor do depósito: ");
					double valorDeposito = scanner.nextDouble();
					scanner.nextLine();
					conta.depositar(valorDeposito);
					break;
				case 3:
					System.out.println("Informe o valor da transferência: ");
					double valorTransferencia = scanner.nextDouble();
					scanner.nextLine();
					System.out.println("Informe o número da conta de destino: ");
					int numeroContaDestino = scanner.nextInt();
					scanner.nextLine();

					Conta contaDestino = this.buscarConta(AGENCIA_PADRAO, numeroContaDestino);
					if (contaDestino != null) {
						conta.transferir(valorTransferencia, contaDestino);
					}
					break;
				case 4:
					conta.imprimirExtrato();
					break;
				case 0:
					System.out.println("Saindo...");
					break;
				default:
					System.out.println("Opção inválida.");
					break;
			}
			System.out.println("Digite uma opção.:");
			opcao = scanner.nextInt();
			scanner.nextLine();
		}
		scanner.close();
	}

}
