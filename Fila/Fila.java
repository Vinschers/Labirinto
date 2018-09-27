/**
*	Classe para armazenar itens no formato de fila. Segue a norma FIFO, ou seja, o primeiro item armazenado é o primeiro a ser recuperado.
*	@author Felipe Scherer Vicentin
*	@author Guilherme Salim de Barros
*	@since 2018
*/

package Fila;
import java.lang.reflect.*;
 public class Fila<X> implements Cloneable{
 	protected int qtd, comeco, fim;
 	protected Object[] vetor;

	/**
	*	Método que serve para clonar o x, ou objeto genérico. Sem o método meuCloneDeX(), o programa acusa erro, já que, embora nós, programadores,
	*	tenhamos certeza que x é cloneable, o programa não sabe. O método então força o .clone().
	*	@return O clone do objeto da classe genérica. Ele é usado para acessar o .clone() após o programa ter certeza que a classe genérica é cloneable.
	*	@param x a instânica do objeto é passada para ser clonada.
	*/
	protected X meuCloneDeX(X x)
    {
        X ret = null;
        try{
            Class<?> classe = x.getClass();
            Class<?>[] tiposDoParametroFormal;
            tiposDoParametroFormal = null;
            Method metodo = classe.getMethod("clone", tiposDoParametroFormal);
            Object[] parametrosReais = null;
            ret = (X)metodo.invoke(x, parametrosReais);
        }
        catch(InvocationTargetException erro){}
        catch(NoSuchMethodException erro2){}
        catch(IllegalAccessException erro3){}
        return ret;
    }
	/**
	*	@param tamanho É o tamanho da fila específicado. O vetor dentro da classe têm propriedade .lenght igualada ao tamanho.
	*	@throws Exception quando o tamanho é menor ou igual que zero. O vetor não pode ter menos de 1 posição.
	*/
 	public Fila(int tamanho) throws Exception{
 		if (tamanho <= 0)
 			throw new Exception("Tamanho invalido");
 		this.vetor = new Object[tamanho];
 		this.qtd = 0;
 		this.comeco = 0;
 		this.fim = 0;
 	}
	/**
	*	Método que copia uma fila para this. Com isso, this será exatamente igual à fila a ser copiada.
	*	@param modelo é uma fila que será copiada para a fila atual.
	*	@throws Exception quando a fila que seria copiada é null.
	*/
 	public Fila(Fila<X> modelo) throws Exception {
 		if (modelo == null)
 			throw new Exception("Modelo invalido");
 		this.qtd = modelo.qtd;
 		this.comeco = modelo.comeco;
 		this.fim = modelo.fim;
 		this.vetor = new Object[modelo.vetor.length];
 		for (int i = 0; i < vetor.length; i++) {
 			this.vetor[i] = modelo.vetor[i];
 		}
 	}
	/**
	* Método obrigatório
	* @return uma string que contêm o número de itens guardados na fila e o primeiro deles.
	*/
 	public String toString() {
 		if (this.qtd == 0)
 			return "Vazia";
 		return this.qtd + "elementos, sendo o primeiro " + this.vetor[this.comeco].toString();
 	}
	/**
	*	Método obrigatório.
	*	@return Um boolean, sendo que quando verdadeiro, a fila passada como parâmetro é igual a this e, quando falso, são diferentes.
	*	@param obj é o objeto a ser comparado com this.
	*/
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
	/**
	*	Método Obrigatório clone(). Copia a Fila atual (this) através do construtor de cópia
	*	@return um objeto que é exatamente igual à this.
	*/
 	public Object clone() {
 		Fila ret = null;
 		try {
 			ret = new Fila<X>(this);
 		}
 		catch (Exception ex) {}
 		return ret;
 	}
	/**
	*	Método obrigatório hashCode().
	*	@return o hashCode de this.
	*/
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
	/**
	*	Método que devolve um boolean que diz se a fila está cheia ou não.
	*	@return boolean de comparação com o tamanho do vetor da fila. Se a quantidade de itens na fila foi igual ao tamanho do vetor, retorna true.
	*/
 	public boolean isCheia() {
 		return this.qtd == this.vetor.length;
 	}
	/**
	*	Método que devolve um boolean que diz se a fila está vazia ou não.
	*	@return boolean de comparação com a quantidade de itens da fila. Se a quantidade de itens na fila foi igual à 0, retorna true.
	*/
 	public boolean isVazia() {
 		return this.qtd == 0;
 	}
	/**
	*	Método que guarda coisas na fila. Ele guarda sempre no final + 1 da fila.
	*	@throws Exception quando o que for ser guardado é null ou quando a fila já está cheia.
	*	@param x é o objeto que será guardado.
	*/
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
	/**
	*	Método que recupera o primeiro item da fila. Ele clona o objeto armazenado e devolve o objeto clonado.
	*	@throws Exception quando não houver nada na fila.
	*	@return o item clonado referente a primeira posição do vetor.
	*/
 	public X getUmItem() throws Exception{
 		if (this.isVazia()) {
 			throw new Exception("Armazenamento vazio");
 		}
 		if (this.vetor[comeco] instanceof Cloneable) {
 			return meuCloneDeX((X)this.vetor[this.comeco]);
		}
 		else
 			return (X)this.vetor[this.comeco];
 	}
	/**
	*	Método que exclui um item da fila. É sempre o primeiro o item a ser excluído.
	*	@throws Exception quando a fila já está vazia. É impossível excluir coisas de algo vazio.
	*/
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