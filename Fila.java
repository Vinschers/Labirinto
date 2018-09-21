 import java.lang.reflect.*;
 //FIFO = First In First Out
 public class Fila<X> implements Cloneable{
 	private int qtd, comeco, fim;
 	private Object[] vetor;

	private X meuCloneDeX(X x) {
		X ret = null;
		try {
			Class<?> classe = x.getClass();
			Class<?>[] tiposDosParametrosFormais = null;    //porque não tem parametro
			Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
			Object[] parametrosReais = null; //pq nao se passam parametros
			ret = (X)metodo.invoke(x, parametrosReais);
		}
		catch (NoSuchMethodException erro) {}
		catch (IllegalAccessException erro) {}
		catch (InvocationTargetException erro) {}
		return ret;
	}

 	public Fila(int tamanho) throws Exception{
 		if (tamanho <= 0)
 			throw new Exception("Tamanho invalido");
 		this.vetor = new Object[tamanho];
 		this.qtd = 0;
 		this.comeco = 0;
 		this.fim = 0;
 	}

 	public Fila(Fila<X> modelo) throws Exception {
 		if (modelo == null)
 			throw new Exception("Modelo invalido");
 		this.qtd = modelo.qtd;
 		this.comeco = modelo.comeco;
 		this.fim = modelo.fim;
 		this.vetor = new Object[modelo.vetor.length];
 		for (int i = 0; i < vetor.length; i++) {
 			this.vetor[i] = meuCloneDeX((X)modelo.vetor[i]);
 		}
 	}

 	public String toString() {
 		if (this.qtd == 0)
 			return "Vazia";
 		return this.qtd + "elementos, sendo o primeiro " + this.vetor[this.comeco].toString();
 	}

 	public boolean equals(Object obj) {
 		if (this == obj)
 			return true;
 		if (obj == null)
 			return false;
 		if (this.getClass() != obj.getClass())
 			return false;
 		Fila<X> f = (Fila<X>)obj;
 		if (this.qtd != f.qtd)
 			return false;
 		for (int i = 0, posThis = this.comeco, posFila = f.comeco; i < this.qtd;
 		i++, posThis = (posThis<this.vetor.length-1?posThis+1:0),
 		posFila = (posFila<f.vetor.length-1?posFila+1:0)) {
 			if (!this.vetor[posThis].equals(f.vetor[posFila]))
 				return false;
 		}
 		return true;
 	}

 	public Object clone() {
 		Fila ret = null;
 		try {
 			ret = new Fila<X>(this);
 		}
 		catch (Exception ex) {}
 		return ret;
 	}

 	public int hashCode() {
 		int ret = 3;
 		ret = ret * 3 + new Integer(this.qtd).hashCode();
 		ret = ret * 3 + new Integer(this.fim).hashCode();
 		ret = ret * 3 + new Integer(this.comeco).hashCode();
 		for (int i = 0, pos = comeco; i < this.qtd; i++, pos=((pos<vetor.length)?pos+1:0)) {
			if (this.vetor[pos] != null)
 				ret = ret * 2 + this.vetor[pos].hashCode();
 		}
 		return ret;
 	}

 	public boolean isCheia() {
 		return this.qtd == this.vetor.length;
 	}

 	public boolean isVazia() {
 		return this.qtd == 0;
 	}

 	public void guarde(X x) throws Exception{
 		if (x == null)
 			throw new Exception("Instancia invalida");
 		if (this.isCheia())
 			throw new Exception("Sem espaco de armazenamento");
 		if (x instanceof Cloneable) {
			if (this.fim == this.vetor.length - 1) {
				this.fim = 0;
				this.vetor[this.fim] = meuCloneDeX(x);
			}
			else
				this.vetor[this.fim++] = meuCloneDeX(x);
		}
 		else {
			if (this.fim == this.vetor.length - 1) {
				this.fim = 0;
				this.vetor[this.fim] = x;
			}
			else
				this.vetor[this.fim++] = x;
		}
 		this.qtd++;
 	}

 	public X getUmItem() throws Exception{
 		if (this.isVazia()) {
 			throw new Exception("Armazenamento vazio");
 		}
 		if (this.vetor[comeco] instanceof Cloneable)
 			return meuCloneDeX((X)this.vetor[this.comeco]);
 		else
 			return (X)this.vetor[this.comeco];
 	}

 	public void jogueForaUmItem() throws Exception {
 		if (this.isVazia()) {
 			throw new Exception("Nao ha nada para excluir");
 		}
 		this.vetor[this.comeco] = null;
 		if (this.comeco == this.vetor.length - 1)
 			this.comeco = 0;
 		else
 			this.comeco++;
 		this.qtd--;
 	}
}