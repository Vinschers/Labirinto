/**
*	Classe que armazena uma coordenada x e y de uma matriz. O valor de X e Y tem que ser necessáriamente maior ou igual a 0.
*	@author Felipe Scherer Vicentin
*	@author Guilherme Salim de Barros
*	@since 2018
*/

package Coordenada;
public class Coordenada implements Cloneable{
	protected int x, y;
	/**
	*	@param x É o número da linha da coordenada. É referente à posição vertical começa sempre no 0.
	*	@param y É o número da coluna da coordenad. É referente à posição horizontal e começa sempre no 0.
	*	@throws Exception quando o tamanho é menor ou igual que zero. O vetor não pode ter menos de 1 posição.
	*/
	public Coordenada(int x, int y) throws Exception{
		if (x < 0)
			throw new Exception("Valor do X invalido");
		if (y < 0)
			throw new Exception("Valor do Y invalido");
		this.x = x;
		this.y = y;
	}
	/**
	* Método obrigatório
	* @return uma string que contêm a coordenada x e y. Segue o padrão: (X, Y)
	*/
	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
	/**
	*	Método obrigatório.
	*	@return Um boolean, sendo que quando verdadeiro, a coordenada passada como parâmetro é igual a this e, quando falso, são diferentes.
	*	@param obj é o objeto a ser comparado com this.
	*/
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != this.getClass())
			return false;
		Coordenada c = (Coordenada)obj;
		if (c.x != this.x)
			return false;
		if (c.y != this.y)
			return false;
		return true;
	}
	/**
	*	Método obrigatório hashCode().
	*	@return o hashCode de this.
	*/
	public int hashCode(){
		int ret = 1;
		ret = ret * 2 + new Integer(this.x).hashCode();
		ret = ret * 2 + new Integer(this.y).hashCode();
		return ret;
	}
	/**
	*	Método Obrigatório clone(). Copia a Coordenada atual (this) através do construtor de cópia
	*	@return um objeto que é exatamente igual à this.
	*/
	public Object clone(){
		Coordenada coord = null;
		try {
			coord = new Coordenada(this);
		}
		catch (Exception e) {}
		return coord;
	}
	/**
	*	Método que copia uma coordenada para this. Com isso, this será exatamente igual à coordenada a ser copiada.
	*	@param c é uma coordenada que será copiada para a coordenada atual (this).
	*	@throws Exception quando a coordenada que seria copiada é null.
	*/
	public Coordenada(Coordenada c) throws Exception{
		if (c == null)
			throw new Exception("Objeto é inválido!");
		this.x = c.x;
		this.y = c.y;
	}
	/**
	*	Método que retorna o x da coordenada. O x é a linha da coordenada e é referente à vertical da matriz.
	*	@throws Exception quando a linha é menor que 0.
	*	@return a linha x da coordenada
	*/
	public int getX() throws Exception{
		if (this.x < 0)
			throw new Exception("x era menor que 0!");
		return this.x;
	}
	/**
	*	Método que retorna o y da coordenada. O y é a coluna da coordenada e é referente à horizontal da matriz.
	*	@throws Exception quando a coluna é menor que 0.
	*	@return a coluna y da coordenada
	*/
	public int getY() throws Exception{
		if (this.y < 0)
			throw new Exception("y era menor que 0!");
		return this.y;
	}
	/**
	*	Método que modifica a coordenada x. Ao ser modificada, a posição vertical da coordenada muda.
	*	@throws Exception quando a coordenada fornecida é menor que 0.
	*/
	public void setX(int x) throws Exception{
		if (x < 0)
			throw new Exception("valor do x invalido!");
		this.x = x;
	}
	/**
	*	Método que modifica a coordenada y. Ao ser modificada, a posição horizontal da coordenada muda.
	*	@throws Exception quando a coordenada fornecida é menor que 0.
	*/
	public void setY(int y) throws Exception{
		if (y < 0)
			throw new Exception("valor do y invalido!");
		this.y = y;
	}
}