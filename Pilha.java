import java.lang.reflect.*;

public class Pilha<X> implements Cloneable {
	private Object[] vetor;
	private int qtd;
	/*

	String str = "COTUCA";
	char chr = str.charAt(2);

	o código acima é bem simples; suponham agora
	que queiramos SOFRER... o que fazer? como tornar
	DEMONIACO o codigo acima?

	String str = "COTUCA";
	Class<?> classe = str.getClass();
	Integer parametroReal = 2; //porque quero usar 2 como parametro do charAt
	Class<?>[] tiposDosParametrosFormais = new Class<?>[1];    //porque charAt tem 1 parametro
	tiposDosParametrosFormais[0] = parametroReal.getClass();
	Method metodo = classe.getMethod("charAt", tiposDosParametrosFormais);
	Object[] parametrosReais = new Object[1]; //pq charAt tem 1 parametro
	parametrosReais[0] = parametroReal;
	char chr = ((Character)metodo.invoke(parametrosReais)).charValue();

	`~´
	*/

	//versao preventiva
	public Pilha(int capacidade) throws Exception{			//construtor
		if (capacidade <= 0)
			throw new Exception("Capacidade invalida");

		this.vetor = new Object[capacidade];
		qtd = 0;
	}

	//versao remediadora
/*	public Pilha(int capacidade) throws Exception {			//construtor
			try {
				this.vetor = new Object[capacidade];
				qtd = 0;
			}
			catch (NegativeArraySizeException erro) {
				throw new Exception("Capacidade invalida");
			}
	} */

	public void guarde (X x) throws Exception{
		if (x == null) //(s.equals("") || s == null) dá errado porque ele tenta fazer um método de algo
																							//nulo, se s for null
			throw new Exception("Informacao ausente");
		if (this.isCheia())
			throw new Exception("Armazenamento cheio");
		if (x instanceof Cloneable) {
			this.vetor[this.qtd] = meuCloneDeX(x);
		}
		else
			this.vetor[this.qtd] = x;
		this.qtd++;
	}

	public X getUmItem() throws Exception {
		if (this.isVazia())
			throw new Exception("Nada a recuperar");
		if((X)this.vetor[this.qtd - 1] instanceof Cloneable){
			return meuCloneDeX((X)this.vetor[this.qtd - 1]);
		}
		else
			return (X)this.vetor[this.qtd - 1];
	}

	public void jogueForaUmItem() throws Exception{
		if (!this.isVazia()) {
			this.qtd--;
			this.vetor[this.qtd] = null;
		}
		else {
			Exception problema;				//é igual a throw new Exception("Nada a jogar fora");
			problema = new Exception("Nada a jogar fora");
			throw problema;
		}
	}

	public boolean isCheia() {
		return this.qtd == this.vetor.length;
	}

	public boolean isVazia() {
		return this.qtd == 0;
	}

	/* Classes obrigatórias */

	public String toString() {
		if(this.qtd == 0)
			return "Vazia";
		return this.qtd + " elementos, sendo o último " + this.vetor[this.qtd-1];
	}

	public boolean equals(Object obj) { //se o parâmentro não for Object, não é o "equals", é so um equals qualquer

		if (this==obj) // verificar se a instância é a mesma (é desnecessário)
			return true;

		if (obj == null) //primeiro, verificar se é null
			return false;

		if (this.getClass() != obj.getClass()) //depois, verificar se a classe é igual
			return false;

		Pilha<X> pil = (Pilha<X>)obj; //forçar o object ser a classe em questão

		if (this.qtd != pil.qtd) //testar cada atributo
			return false;
		for (int i = 0; i < this.qtd; i++)
			if (!this.vetor[i].equals(pil.vetor[i])) //tem que ser equals para comparar conteúdo
				return false;
		return true; //se tudo der certo, eles são iguais
	}

	public int hashCode() {
		int ret = 666; //qualquer valor, menos 0
			// colocar ret * [número primo qualquer que pode variar] + [hashCode de cada coisa guardada no método] n vezes
			for (int i = 0; i < this.qtd; i++)
				ret = ret * 2 + this.vetor[i].hashCode();
			ret = ret * 2 + new Integer(this.qtd).hashCode(); // não é this.qtd.hashCode() porque int não tem métodos, mas a classe Wrapper tem
		return ret;
	}

	public Pilha(Pilha modelo) throws Exception{
		if (modelo == null)
			throw new Exception("Modelo esta null");
		this.qtd = modelo.qtd;
		this.vetor = new Object[modelo.vetor.length]; //pra criar um novo vetor, diferente do vetor do modelo
		for (int i = 0; i < qtd; i++)
			this.vetor[i] = modelo.vetor[i];
	}

	public Object clone() {
		Pilha<X> ret = null;
		try {
			ret = new Pilha<X>(this);
		}
		catch (Exception erro) {}
		return ret;
	}

	private X meuCloneDeX(X x) {
		//agora, o que quero fazer do jeito DEMONIACO é
		// return x.clone();
		X ret = null;
		try {
			Class<?> classe = x.getClass();
			Class<?>[] tiposDosParametrosFormais = null;    //porque não tem parametro
			Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
			Object[] parametrosReais = null; //pq nao se passam parametros
			ret = (X)metodo.invoke(parametrosReais);
		}
		catch (NoSuchMethodException erro) {}
		catch (IllegalAccessException erro) {}
		catch (InvocationTargetException erro) {}
		return ret;
	}

} //Andre Luis Reis Gomes Carvalho