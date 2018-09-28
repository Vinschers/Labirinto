/**
*	Classe com as caracter�sticas do labirinto. Todas as suas partes est�o encapsuladas em m�todos ou vari�veis globais no private.
*	@author Felipe Scherer Vicentin & Guilherme Salim de Barros
*	@since 2018
*/


package Labirinto;
import Coordenada.*;
import Fila.*;
import Pilha.*;
import java.io.*;
public class Labirinto{
	protected String arq;
	protected int rows = 0, columns = 0, dimensao = 0;
	protected char[][] labirinto;
	protected Coordenada atual;
	protected Fila<Coordenada> fila;
	protected Pilha<Fila<Coordenada>> possibilidades;
	protected Pilha<Coordenada> caminho;
	protected boolean progressivo = true, terminou = false;



	/**
			*	@param arquivo que possui seu valor atribu�do � vari�vel arq,
			*	que ser� o arquivo a ser lido
		*/

		public Labirinto (String arquivo) {
			arq = arquivo;
	}


	/**
		*	O m�todo equals() � um obrigat�rio.
		*	@return Um boolean, sendo que, quando verdadeiro, o labirinto passado como par�metro � igual ao labirinto
		*	do this e, quando falso, s�o diferentes.
		*	@param obj � o objeto a ser comparado com this.
	*/

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Labirinto l = (Labirinto)obj;
		if(l.rows != this.rows || l.columns != this.columns || l.dimensao != this.dimensao)
			return false;
		if (!l.labirinto.equals(this.labirinto))
			return false;
		if (l.atual != this.atual)
			return false;
		if (!l.fila.equals(this.fila))
			return false;
		if (!l.possibilidades.equals(this.possibilidades))
			return false;
		if (!l.caminho.equals(this.caminho))
			return false;
		if (l.progressivo != this.progressivo)
			return false;
		if (l.terminou != this.terminou)
			return false;
		return true;
	}

	/**
		*	M�todo que copia um labirinto para this. Com isso, this ser� exatamente igual ao labirinto a ser copiado.
		*	@param modelo � um labirinto que ser� copiado para o labirinto atual.
		*	@throws Exception quando o labirinto que seria copiado � null.
	*/
	public Labirinto (Labirinto modelo) throws Exception {
		if (modelo == null)
			throw new Exception("Modelo nulo!");
		this.rows = modelo.rows;
		this.columns = modelo.columns;
		this.dimensao = modelo.dimensao;
		this.labirinto = modelo.labirinto.clone();
		this.atual = modelo.atual;
		this.fila = (Fila<Coordenada>) modelo.fila.clone();
		this.possibilidades = (Pilha<Fila<Coordenada>>) modelo.possibilidades.clone();
		this.caminho = (Pilha<Coordenada>) modelo.caminho.clone();
		this.progressivo = modelo.progressivo;
		this.terminou = modelo.terminou;
	}

	/**
		*	M�todo obrigat�rio clone(). Copia o labirinto atual (this) atrav�s do construtor de c�pia.
		*	@return um objeto que � exatamente igual � this.
	*/

	public Object clone() {
		Labirinto ret = null;
		try {ret = new Labirinto(this);} catch(Exception e){}
		return ret;
	}

	/**
		*	M�todo obrigat�rio hashCode().
		*	@return o hashCode de this.
	*/

	public int hashCode() {
		int ret = 1;
		ret = ret * 2 + (new Integer(rows).hashCode());
		ret = ret * 2 + (new Integer(columns).hashCode());
		ret = ret * 2 + (new Integer(dimensao).hashCode());
		for (int i = 0; i < rows; i++)
			for (int k = 0; k < columns; k++)
				ret = ret * 2 + (new Character(labirinto[i][k]).hashCode());
		ret = ret * 2 + (atual.hashCode());
		ret = ret * 2 + (fila.hashCode());
		ret = ret * 2 + (possibilidades.hashCode());
		ret = ret * 2 + (caminho.hashCode());
		return ret;
	}

	/**
				* O m�todo toString() obrigat�rio.
				* @return uma string que cont�m o pr�prio desenho do labirinto.
		*/

		public String toString() {
			String ret = "";
			for (int i = 0; i < rows; i++){
				for (int k = 0; k < columns; k++)
					if (k < columns - 1)
						ret += (labirinto[i][k]);
					else
						ret += ((labirinto[i][k]) + "\r\n");
			}
			return ret;
	}

	/**
		*		O m�todo resolver chama as tr�s fun��es: readFile(), initialize() e fazer() para
		*	facilitar a ultiliza��o do conjunto em outros m�todos.
	*/

	public void resolver() throws Exception{
		readFile(arq);
		initialize();
		fazer();
	}

	/**
		*		O m�todo readFile() l� o labirinto e atribui � matriz
		*	labirinto as colunas e linhas, a entrada e a sa�da, verificando
		*	a exist�ncia de 'E' e 'S'.
		*
		*	@throws  Exception se n�o houver sa�da do labirinto.
		*	@param nomeArquivo que indica o arquivo a ser lido.
	*/
	protected void readFile(String nomeArquivo) throws Exception{
		BufferedReader file = new BufferedReader(new FileReader(nomeArquivo));
		rows = Integer.parseInt(file.readLine());
		columns = Integer.parseInt(file.readLine());
		labirinto = new char[rows][columns];
		String line = "";
		for (int i = 0; i < rows; i++){
			line = file.readLine();
			for (int k = 0; k < columns; k++)
				labirinto[i][k] = line.charAt(k);
		}
		boolean existe = false;
		for (int i = 0; i < rows; i++)
			for(int k = 0; k < columns; k++)
				if (labirinto[i][k] == 'S')
					existe = true;
		if (!existe) {
			terminou = true;
			throw new Exception("N�o h� sa�da para o labirinto");
		}
	}


	/**
		*		O m�todo initialize() declara uma pilha de coordenadas, que � o
		*	caminho a ser percorrido no labirinto, e uma pilha de filas de coordenadas,
		*	a qual indica as possibilidadesde rota atuais. Al�m dessas a��es, ele encontra a entrada do percurso
		*	chamando o m�todo findE(). A aplica��o do initialize() � realizada no come�o da execu��o do programa.
	*/

	protected void initialize() throws Exception{
		dimensao = rows * columns;
		caminho = new Pilha<Coordenada>(dimensao);
		possibilidades = new Pilha<Fila<Coordenada>>(dimensao);
		findE();
	}

	/**

		*		O m�todo findE(), como surgere o seu pr�rio nome, tem a fun��o
		*	de encontrar o caracter 'E' no labirinto, que, na realidade, � a entrada
		*	do trageto a ser percorrido. Retorna nada para sair da busca quando achar 'E', pois � desnecess�rio continu�-la.
		*	@throws Exception caso a entrada n�o seja encontrada.
	*/

	protected void findE() throws Exception{
		for (int i = 0; i < columns; i++) {			//bordas horizontais
			if (labirinto[0][i] == 'E'){		//cima
				atual = new Coordenada(0, i);
				return;
			}
			else if (labirinto[rows - 1][i] == 'E'){ //baixo
				atual = new Coordenada(rows - 1, i);
				return;
			}
		}
		for (int i = 0; i < rows; i++) {			//bordas verticais
			if (labirinto[i][0] == 'E'){	//esqueda
				atual = new Coordenada(i, 0);
				return;
			}
			else if (labirinto[i][columns - 1] == 'E'){ //direita
				atual = new Coordenada(i, columns - 1);
				return;
			}
		}
		if (atual == null)
			throw new Exception("Entrada do labirinto n�o encontrada!");
	}


	/**
			*			O m�todo testarPosicoes() delclara uma fila de coordenadas e testa
			*		as possibilidades, caso sejam v�lidas (diferentes de '#'), as armazena.
	*/

	protected void testarPosicoes() throws Exception {
		fila = new Fila<Coordenada>(3);
		int row = atual.getX();
		int column = atual.getY();
		if ((column + 1 < columns) && (labirinto[row][column + 1] == ' ' || labirinto[row][column + 1] == 'S'))
			fila.guarde(new Coordenada(row, column + 1));
		if ((column - 1 >= 0) && (labirinto[row][column - 1] == ' ' || labirinto[row][column - 1] == 'S'))
			fila.guarde(new Coordenada(row, column - 1));
		if ((row + 1 < rows) && (labirinto[row + 1][column] == ' ' || labirinto[row + 1][column] == 'S'))
			fila.guarde(new Coordenada(row + 1, column));
		if ((row - 1 >= 0) && (labirinto[row - 1][column] == ' ' || labirinto[row - 1][column] == 'S'))
			fila.guarde(new Coordenada(row - 1, column));
	}



	/**
				*			O m�todo atualizarVarizaveis() possui um nome sugestivo em rela��o a sua fun��o,
				*		ele atribui um novo valor para a variavel "atual" com base nos dados da fila de posi��es
				*		dispon�veis e jogar� fora um �tem dela, por�m, caso a fila esteja vazia, nada ocorrer�.
				*		Todos os passos descritos anteriormente s� ocorrer�o caso o movimento esteja normal, mas, se ele for regressivo,
				*		atual receber� uma coordenada do caminho j� percorrido e marcar� a posi��o atual no labirinto
				*		com um espa�o em branco e uma das possibilidades ser� jogada fora.
	*/

	protected void atualizarVariaveis() throws Exception{
		if (progressivo) {
			if (!fila.isVazia()) {
				atual = fila.getUmItem();
				fila.jogueForaUmItem();
				caminho.guarde(atual);
				possibilidades.guarde(fila);
			}
			else {
				progressivo = false;
			}
		}
		else {
			atual = caminho.getUmItem();
			caminho.jogueForaUmItem();
			labirinto[atual.getX()][atual.getY()] = ' ';
			fila = possibilidades.getUmItem();
			possibilidades.jogueForaUmItem();
		}
	}


	/**
		*		O m�todo resolver() aplica os m�todos do modo regressivo e progressivo at� o fim da execu��o do programa,
		*	que � quando a sa�da � encontrada ou quando ela n�o existe (nesse �ltimo caso, nenhum modo pe feito).
		*	Caso a vari�vel "progressivo" seja verdadeira, ele entrar� no modo progressivo e, se tiver
		*	um valor falso, partir� para o modo regressivo.
	*/

	protected void fazer() throws Exception{
		while (!terminou) {
			while (progressivo && !terminou)
				modoProgressivo();
			while (!progressivo && !terminou)
				modoRegressivo();
		}
	}


	/**
		*		O m�todo modoProgressivo() testa as posi��es dispon�veis, atualiza as vari�veis
		*	e escreve o caracter "*" nas posi��es do labirinto at� achar a sa�da, o caracter 'S',
		*	usando as as coordenadas X e Y atuais.
		*
		*	@retorn nada quando o programa parar de ser executado, ou seja,
		*	quando a sa�da for encontrada, para sair do m�todo.
		*
	*/

	protected void modoProgressivo() throws Exception{
		testarPosicoes();
		atualizarVariaveis();
		if (labirinto[atual.getX()][atual.getY()] == 'S'){
			terminou = true;
			return;
		}
		labirinto[atual.getX()][atual.getY()] = '*';
	}


	/**
			*		O m�todo modoRegressivo() verifica se h� uma sa�da no labirinto, caso n�o,
			*	ele retorna uma exe��o e termina o programa. Em uma situa��o oposta, ele atualiza as
			*	vari�veis do programa, v erifica se a fila n�o est� vazia, ocorre uma segunda atualiza��o de
			*	vari�veis e ele compara os dados das cordenadas X e Y da posi��o atual, vendo se seus dados n�o
			*	s�o a sa�da para remarcar um asterisco e retornar ao modo progressivo do programa.
			*
			*	@throws Exception caso n�o haja o caracter 'S', ou seja, a sa�da do labirinto.
			*
	*/

	protected void modoRegressivo() throws Exception{
		if (possibilidades.isVazia()){
			terminou = true;
			throw new Exception("N�o h� sa�da para o labirinto!");
		}
		atualizarVariaveis();
		if (!fila.isVazia()) {
			progressivo = true;
			atualizarVariaveis();
			if (labirinto[atual.getX()][atual.getY()] != 'S')
				labirinto[atual.getX()][atual.getY()] = '*';
		}
	}







	/**
		*	O objeto isCompleto() � do tipo boolean.
		*
		*	@return uma vari�vel boolean (nomeada "terminou") que, caso
		*	ela teja o valor true, o labirinto estar� completo, por�m, se for false, estar� incompleto.
	*/

	public boolean isCompleto() {
		return terminou;
	}

	/**
				*		A String caminho() possui um nome quase auto-explicativo, quando o programa chega
				*	no fim do labirinto, ou seja, no momento em que acabar os procedimentos
				*	de percorrer o labirinto, ele exibir� as coordenadas do trajeto
				*	percorrido a partir de uma pilha de coordenadas chamada "inverso",
				*	nome dado por conta seus dados estarem na ordem oposta aos do caminho, faendo uma
				*	exibi��o de dados na ordem correta.
				*
				*	@return a String "ret" com todo o percurso no final da execu��o.
	*/


	public String caminho() throws Exception{
		Pilha<Coordenada> inverso = new Pilha<Coordenada>(dimensao);
		String ret = "";
		while (!caminho.isVazia()) {
			inverso.guarde(caminho.getUmItem());
			caminho.jogueForaUmItem();
		}
		while (!inverso.isVazia()) {
			ret += (inverso.getUmItem()) + " ";
			inverso.jogueForaUmItem();
		}
		return ret;
	}


}