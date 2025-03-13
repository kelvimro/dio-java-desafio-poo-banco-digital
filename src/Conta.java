
public abstract class Conta implements InterfaceConta {

	private static final int AGENCIA_PADRAO = 1;
	private static int SEQUENCIAL = 1;

	protected int agencia;
	protected int numero;
	protected double saldo;
	protected Cliente cliente;

	public Conta(Cliente cliente) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.numero = SEQUENCIAL++;
		this.cliente = cliente;
	}

	@Override
	public void sacar(double valor) {
		saldo -= valor;
		System.out.println(String.format("Saque R$%.2f realizado com sucesso.", valor));
	}

	@Override
	public void depositar(double valor) {
		saldo += valor;
		System.out.println(String.format("Depósito R$%.2f realizado com sucesso.", valor));
	}

	@Override
	public void transferir(double valor, InterfaceConta contaDestino) {
		this.sacar(valor);
		contaDestino.depositar(valor);
		System.out.println("Transferência realizada com sucesso.");
	}

	public int getAgencia() {
		return agencia;
	}

	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	protected void imprimirInfosComuns() {
		System.out.println(String.format("Titular: %s", this.cliente.getNome()));
		System.out.println(String.format("Agencia: %d", this.agencia));
		System.out.println(String.format("Numero: %d", this.numero));
		System.out.println(String.format("Saldo: %.2f", this.saldo));
	}
}
