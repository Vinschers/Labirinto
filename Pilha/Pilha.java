/**
*	Classe para armazenar itens no formato de pilha. Segue a norma LIFO, ou seja, o �ltimo item armazenado � o primeiro a ser recuperado.
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
	*	@param capacidade � o tamanho da pilha espec�ficada. O vetor dentro da classe t�m propriedade .lenght igualada ao tamanho.
	*	@throws Exception quando o tamanho � menor ou igual que zero. O vetor n�o pode ter menos de 1 posi��o.
	*/
	public Pilha(int capacidade) throws Exception{
		if (capacidade <= 0)
			throw new Exception("Capacidade invalida");

		this.vetor = new Object[capacidade];
		qtd = 0;
	}
	/**
	*	M�todo que guarda coisas na pilha. Ele guarda sempre no final + 1 da pilha.
	*	@throws Exception quando o que for ser guardado � null ou quando a pilha j� est� cheia.
	*	@param x � o objeto que ser� guardado.
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
	*	M�todo que recupera o ultimo item da pilha. Ele clona o objeto armazenado e devolve o objeto clonado.
	*	@throws Exception quando n�o houver nada na pilha.
	*	@return o item clonado referente a primeira posi��o do vetor.
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
	*	M�todo que exclui um item da pilha. � sempre o ultimo o item a ser exclu�do.
	*	@throws Exception quando a pilha j� est� vazia. � imposs�vel excluir coisas de algo vazio.
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
	*	M�todo que devolve um boolean que diz se a pilha est� cheia ou n�o.
	*	@return boolean de compara��o com o tamanho do vetor da pilha. Se a quantidade de itens na pilha foi igual ao tamanho do vetor, retorna true.
	*/
	public boolean isCheia() {
		return this.qtd == this.vetor.length;
	}
	/**
	*	M�todo que devolve um boolean que diz se a pilha est� vazia ou n�o.
	*	@return boolean de compara��o com a quantidade de itens da pilha. Se a quantidade de itens na pilha foi igual � 0, retorna true.
	*/
	public boolean isVazia() {
		return this.qtd == 0;
	}
	/**
	* M�todo obrigat�rio
	* @return uma string que cont�m o n�mero de itens guardados na pilha e o �ltimo deles.
	*/
	public String toString() {
		if(this.qtd == 0)
			return "Vazia";
		return this.qtd + " elementos, sendo o �ltimo " + this.vetor[this.qtd-1];
	}
	/**
	*	M�todo obrigat�rio.
	*	@return Um boolean, sendo que quando verdadeiro, a pilha passada como par�metro � igual a this e, quando falso, s�o diferentes.
	*	@param obj � o objeto a ser comparado com this.
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
	*	M�todo obrigat�rio hashCode().
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
	*	M�todo que copia uma pilha para this. Com isso, this ser� exatamente igual � pilha a ser copiada.
	*	@param modelo � uma pilha que ser� copiada para a pilha atual.
	*	@throws Exception quando a pilha que seria copiada � null.
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
	*	M�todo Obrigat�rio clone(). Copia a Pilha atual (this) atrav�s do construtor de c�pia
	*	@return um objeto que � exatamente igual � this.
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
	*	M�todo que serve para clonar o x, ou objeto gen�rico. Sem o m�todo meuCloneDeX(), o programa acusa erro, j� que, embora n�s, programadores,
	*	tenhamos certeza que x � cloneable, o programa n�o sabe. O m�todo ent�o for�a o .clone().
	*	@return O clone do objeto da classe gen�rica. Ele � usado para acessar o .clone() ap�s o programa ter certeza que a classe gen�rica � cloneable.
	*	@param x a inst�nica do objeto � passada para ser clonada.
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