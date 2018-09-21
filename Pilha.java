import java.lang.reflect.*;

public class Pilha<X> implements Cloneable {
	private Object[] vetor;
	private int qtd;
	public Pilha(int capacidade) throws Exception{
		if (capacidade <= 0)
			throw new Exception("Capacidade invalida");

		this.vetor = new Object[capacidade];
		qtd = 0;
	}

	public void guarde (X x) throws Exception{
		if (x == null)
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
			Exception problema;
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

	public String toString() {
		if(this.qtd == 0)
			return "Vazia";
		return this.qtd + " elementos, sendo o último " + this.vetor[this.qtd-1];
	}

	public boolean equals(Object obj) {
		if (this==obj)
			return true;

		if (obj == null)
			return false;

		if (this.getClass() != obj.getClass())
			return false;

		Pilha<X> pil = (Pilha<X>)obj;

		if (this.qtd != pil.qtd)
			return false;
		for (int i = 0; i < this.qtd; i++)
			if (!this.vetor[i].equals(pil.vetor[i]))
				return false;
		return true;
	}

	public int hashCode() {
		int ret = 666;
			for (int i = 0; i < this.qtd; i++)
				ret = ret * 2 + this.vetor[i].hashCode();
			ret = ret * 2 + new Integer(this.qtd).hashCode();
		return ret;
	}

	public Pilha(Pilha modelo) throws Exception{
		if (modelo == null)
			throw new Exception("Modelo esta null");
		this.qtd = modelo.qtd;
		this.vetor = new Object[modelo.vetor.length];
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
		X ret = null;
		try {
			Class<?> classe = x.getClass();
			Class<?>[] tiposDosParametrosFormais = null;
			Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
			Object[] parametrosReais = null;
			ret = (X)metodo.invoke(x, parametrosReais);
		}
		catch (NoSuchMethodException erro) {}
		catch (IllegalAccessException erro) {}
		catch (InvocationTargetException erro) {}
		return ret;
	}

}