/**
*	Classe que armazena uma coordenada x e y de uma matriz. O valor de X e Y tem que ser necess�riamente maior ou igual a 0.
*	@author Felipe Scherer Vicentin
*	@author Guilherme Salim de Barros
*	@since 2018
*/

package Coordenada;
public class Coordenada implements Cloneable{
	protected int x, y;
	/**
	*	@param x � o n�mero da linha da coordenada. � referente � posi��o vertical come�a sempre no 0.
	*	@param y � o n�mero da coluna da coordenad. � referente � posi��o horizontal e come�a sempre no 0.
	*	@throws Exception quando o tamanho � menor ou igual que zero. O vetor n�o pode ter menos de 1 posi��o.
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
	* M�todo obrigat�rio
	* @return uma string que cont�m a coordenada x e y. Segue o padr�o: (X, Y)
	*/
	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
	/**
	*	M�todo obrigat�rio.
	*	@return Um boolean, sendo que quando verdadeiro, a coordenada passada como par�metro � igual a this e, quando falso, s�o diferentes.
	*	@param obj � o objeto a ser comparado com this.
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
	*	M�todo obrigat�rio hashCode().
	*	@return o hashCode de this.
	*/
	public int hashCode(){
		int ret = 1;
		ret = ret * 2 + new Integer(this.x).hashCode();
		ret = ret * 2 + new Integer(this.y).hashCode();
		return ret;
	}
	/**
	*	M�todo Obrigat�rio clone(). Copia a Coordenada atual (this) atrav�s do construtor de c�pia
	*	@return um objeto que � exatamente igual � this.
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
	*	M�todo que copia uma coordenada para this. Com isso, this ser� exatamente igual � coordenada a ser copiada.
	*	@param c � uma coordenada que ser� copiada para a coordenada atual (this).
	*	@throws Exception quando a coordenada que seria copiada � null.
	*/
	public Coordenada(Coordenada c) throws Exception{
		if (c == null)
			throw new Exception("Objeto � inv�lido!");
		this.x = c.x;
		this.y = c.y;
	}
	/**
	*	M�todo que retorna o x da coordenada. O x � a linha da coordenada e � referente � vertical da matriz.
	*	@throws Exception quando a linha � menor que 0.
	*	@return a linha x da coordenada
	*/
	public int getX() throws Exception{
		if (this.x < 0)
			throw new Exception("x era menor que 0!");
		return this.x;
	}
	/**
	*	M�todo que retorna o y da coordenada. O y � a coluna da coordenada e � referente � horizontal da matriz.
	*	@throws Exception quando a coluna � menor que 0.
	*	@return a coluna y da coordenada
	*/
	public int getY() throws Exception{
		if (this.y < 0)
			throw new Exception("y era menor que 0!");
		return this.y;
	}
	/**
	*	M�todo que modifica a coordenada x. Ao ser modificada, a posi��o vertical da coordenada muda.
	*	@throws Exception quando a coordenada fornecida � menor que 0.
	*/
	public void setX(int x) throws Exception{
		if (x < 0)
			throw new Exception("valor do x invalido!");
		this.x = x;
	}
	/**
	*	M�todo que modifica a coordenada y. Ao ser modificada, a posi��o horizontal da coordenada muda.
	*	@throws Exception quando a coordenada fornecida � menor que 0.
	*/
	public void setY(int y) throws Exception{
		if (y < 0)
			throw new Exception("valor do y invalido!");
		this.y = y;
	}
}