public class Coordenada implements Cloneable{
	private int x, y;

	public Coordenada(int x, int y) throws Exception{
		if (x < 0 || y < 0)
			throw new Exception("Valores das coordenadas invalidos.");
		this.x = x;
		this.y = y;
	}
	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
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
	public int hashCode(){
		int ret = 1;
		ret = ret * 2 + new Integer(this.x).hashCode();
		ret = ret * 2 + new Integer(this.y).hashCode();
		return ret;
	}
	public Object clone(){
		Coordenada coord = null;
		try {
			coord = new Coordenada(this);
		}
		catch (Exception e) {}
		return coord;
	}
	public Coordenada(Coordenada c) throws Exception{
		if (c == null)
			throw new Exception("Objeto é inválido!");
		this.x = c.x;
		this.y = c.y;
	}

	public void setX(int x) throws Exception{
		if (new Integer(x) == null || x < 0)
			throw new Exception("O valor de x é inválido!");
		this.x = x;
	}
	public void setY(int y) throws Exception{
		if (new Integer(y) == null || y < 0)
			throw new Exception("O valor de y é inválido!");
		this.y = y;
	}
	public int getX() throws Exception{
		if (this.x < 0)
			throw new Exception("x era menor que 0!");
		return this.x;
	}
	public int getY() throws Exception{
		if (this.y < 0)
			throw new Exception("y era menor que 0!");
		return this.y;
	}
}