/**
*	Classe para armazenar itens no formato de fila. Segue a norma FIFO, ou seja, o primeiro item armazenado � o primeiro a ser recuperado.
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
	/**
	*	@param tamanho � o tamanho da fila espec�ficado. O vetor dentro da classe t�m propriedade .lenght igualada ao tamanho.
	*	@throws Exception quando o tamanho � menor ou igual que zero. O vetor n�o pode ter menos de 1 posi��o.
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
	*	M�todo que copia uma fila para this. Com isso, this ser� exatamente igual � fila a ser copiada.
	*	@param modelo � uma fila que ser� copiada para a fila atual.
	*	@throws Exception quando a fila que seria copiada � null.
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
	* M�todo obrigat�rio
	* @return uma string que cont�m o n�mero de itens guardados na fila e o primeiro deles.
	*/
 	public String toString() {
 		if (this.qtd == 0)
 			return "Vazia";
 		return this.qtd + "elementos, sendo o primeiro " + this.vetor[this.comeco].toString();
 	}
	/**
	*	M�todo obrigat�rio.
	*	@return Um boolean, sendo que quando verdadeiro, a fila passada como par�metro � igual a this e, quando falso, s�o diferentes.
	*	@param obj � o objeto a ser comparado com this.
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
	*	M�todo Obrigat�rio clone(). Copia a Fila atual (this) atrav�s do construtor de c�pia
	*	@return um objeto que � exatamente igual � this.
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
	*	M�todo obrigat�rio hashCode().
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
	*	M�todo que devolve um boolean que diz se a fila est� cheia ou n�o.
	*	@return boolean de compara��o com o tamanho do vetor da fila. Se a quantidade de itens na fila foi igual ao tamanho do vetor, retorna true.
	*/
 	public boolean isCheia() {
 		return this.qtd == this.vetor.length;
 	}
	/**
	*	M�todo que devolve um boolean que diz se a fila est� vazia ou n�o.
	*	@return boolean de compara��o com a quantidade de itens da fila. Se a quantidade de itens na fila foi igual � 0, retorna true.
	*/
 	public boolean isVazia() {
 		return this.qtd == 0;
 	}
	/**
	*	M�todo que guarda coisas na fila. Ele guarda sempre no final + 1 da fila.
	*	@throws Exception quando o que for ser guardado � null ou quando a fila j� est� cheia.
	*	@param x � o objeto que ser� guardado.
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
	*	M�todo que recupera o primeiro item da fila. Ele clona o objeto armazenado e devolve o objeto clonado.
	*	@throws Exception quando n�o houver nada na fila.
	*	@return o item clonado referente a primeira posi��o do vetor.
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
	*	M�todo que exclui um item da fila. � sempre o primeiro o item a ser exclu�do.
	*	@throws Exception quando a fila j� est� vazia. � imposs�vel excluir coisas de algo vazio.
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