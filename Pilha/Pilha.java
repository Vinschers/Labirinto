/**
*	Classe para armazenar itens no formato de pilha. Segue a norma LIFO, ou seja, o último item armazenado é o primeiro a ser recuperado.
*	@author Felipe Scherer Vicentin
*	@author Guilherme Salim de Barros
*	@since 2018
*/

package Pilha;
import java.lang.reflect.*;

public class Pilha<X> implements Cloneable {
	protected Object[] vetor;
	protected int qtd;
	/**
	*	@param capacidade É o tamanho da pilha específicada. O vetor dentro da classe têm propriedade .lenght igualada ao tamanho.
	*	@throws Exception quando o tamanho é menor ou igual que zero. O vetor não pode ter menos de 1 posição.
	*/
	public Pilha(int capacidade) throws Exception{
		if (capacidade <= 0)
			throw new Exception("Capacidade invalida");

		this.vetor = new Object[capacidade];
		qtd = 0;
	}
	/**
	*	Método que guarda coisas na pilha. Ele guarda sempre no final + 1 da pilha.
	*	@throws Exception quando o que for ser guardado é null ou quando a pilha já está cheia.
	*	@param x é o objeto que será guardado.
	*/
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
	/**
	*	Método que recupera o ultimo item da pilha. Ele clona o objeto armazenado e devolve o objeto clonado.
	*	@throws Exception quando não houver nada na pilha.
	*	@return o item clonado referente a primeira posição do vetor.
	*/
	public X getUmItem() throws Exception {
		if (this.isVazia())
			throw new Exception("Nada a recuperar");
		if((X)this.vetor[this.qtd - 1] instanceof Cloneable){
			return meuCloneDeX((X)this.vetor[this.qtd - 1]);
		}
		else
			return (X)this.vetor[this.qtd - 1];
	}
	/**
	*	Método que exclui um item da pilha. É sempre o ultimo o item a ser excluído.
	*	@throws Exception quando a pilha já está vazia. É impossível excluir coisas de algo vazio.
	*/
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
	/**
	*	Método que devolve um boolean que diz se a pilha está cheia ou não.
	*	@return boolean de comparação com o tamanho do vetor da pilha. Se a quantidade de itens na pilha foi igual ao tamanho do vetor, retorna true.
	*/
	public boolean isCheia() {
		return this.qtd == this.vetor.length;
	}
	/**
	*	Método que devolve um boolean que diz se a pilha está vazia ou não.
	*	@return boolean de comparação com a quantidade de itens da pilha. Se a quantidade de itens na pilha foi igual à 0, retorna true.
	*/
	public boolean isVazia() {
		return this.qtd == 0;
	}
	/**
	* Método obrigatório
	* @return uma string que contêm o número de itens guardados na pilha e o último deles.
	*/
	public String toString() {
		if(this.qtd == 0)
			return "Vazia";
		return this.qtd + " elementos, sendo o último " + this.vetor[this.qtd-1];
	}
	/**
	*	Método obrigatório.
	*	@return Um boolean, sendo que quando verdadeiro, a pilha passada como parâmetro é igual a this e, quando falso, são diferentes.
	*	@param obj é o objeto a ser comparado com this.
	*/
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
	/**
	*	Método obrigatório hashCode().
	*	@return o hashCode de this.
	*/
	public int hashCode() {
		int ret = 666;
			for (int i = 0; i < this.qtd; i++)
				ret = ret * 2 + this.vetor[i].hashCode();
			ret = ret * 2 + new Integer(this.qtd).hashCode();
		return ret;
	}
	/**
	*	Método que copia uma pilha para this. Com isso, this será exatamente igual à pilha a ser copiada.
	*	@param modelo é uma pilha que será copiada para a pilha atual.
	*	@throws Exception quando a pilha que seria copiada é null.
	*/
	public Pilha(Pilha modelo) throws Exception{
		if (modelo == null)
			throw new Exception("Modelo esta null");
		this.qtd = modelo.qtd;
		this.vetor = new Object[modelo.vetor.length];
		for (int i = 0; i < qtd; i++)
			this.vetor[i] = modelo.vetor[i];
	}
	/**
	*	Método Obrigatório clone(). Copia a Pilha atual (this) através do construtor de cópia
	*	@return um objeto que é exatamente igual à this.
	*/
	public Object clone() {
		Pilha<X> ret = null;
		try {
			ret = new Pilha<X>(this);
		}
		catch (Exception erro) {}
		return ret;
	}
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
}