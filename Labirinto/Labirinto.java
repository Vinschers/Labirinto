/**
*	Classe com as características do labirinto. Todas as suas partes estão encapsuladas em métodos ou variáveis globais no private.
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
			*	@param arquivo que possui seu valor atribuído à variável arq,
			*	que será o arquivo a ser lido
		*/

		public Labirinto (String arquivo) {
			arq = arquivo;
	}


	/**
		*	O método equals() é um obrigatório.
		*	@return Um boolean, sendo que, quando verdadeiro, o labirinto passado como parâmetro é igual ao labirinto
		*	do this e, quando falso, são diferentes.
		*	@param obj é o objeto a ser comparado com this.
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
		*	Método que copia um labirinto para this. Com isso, this será exatamente igual ao labirinto a ser copiado.
		*	@param modelo é um labirinto que será copiado para o labirinto atual.
		*	@throws Exception quando o labirinto que seria copiado é null.
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
		*	Método obrigatório clone(). Copia o labirinto atual (this) através do construtor de cópia.
		*	@return um objeto que é exatamente igual à this.
	*/

	public Object clone() {
		Labirinto ret = null;
		try {ret = new Labirinto(this);} catch(Exception e){}
		return ret;
	}

	/**
		*	Método obrigatório hashCode().
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
				* O método toString() obrigatório.
				* @return uma string que contêm o próprio desenho do labirinto.
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
		*		O método resolver chama as três funções: readFile(), initialize() e fazer() para
		*	facilitar a ultilização do conjunto em outros métodos.
	*/

	public void resolver() throws Exception{
		readFile(arq);
		initialize();
		fazer();
	}

	/**
		*		O método readFile() lê o labirinto e atribui à matriz
		*	labirinto as colunas e linhas, a entrada e a saída, verificando
		*	a existência de 'E' e 'S'.
		*
		*	@throws  Exception se não houver saída do labirinto.
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
			throw new Exception("Não há saída para o labirinto");
		}
	}


	/**
		*		O método initialize() declara uma pilha de coordenadas, que é o
		*	caminho a ser percorrido no labirinto, e uma pilha de filas de coordenadas,
		*	a qual indica as possibilidadesde rota atuais. Além dessas ações, ele encontra a entrada do percurso
		*	chamando o método findE(). A aplicação do initialize() é realizada no começo da execução do programa.
	*/

	protected void initialize() throws Exception{
		dimensao = rows * columns;
		caminho = new Pilha<Coordenada>(dimensao);
		possibilidades = new Pilha<Fila<Coordenada>>(dimensao);
		findE();
	}

	/**

		*		O método findE(), como surgere o seu prório nome, tem a função
		*	de encontrar o caracter 'E' no labirinto, que, na realidade, é a entrada
		*	do trageto a ser percorrido. Retorna nada para sair da busca quando achar 'E', pois é desnecessário continuá-la.
		*	@throws Exception caso a entrada não seja encontrada.
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
			throw new Exception("Entrada do labirinto não encontrada!");
	}


	/**
			*			O método testarPosicoes() delclara uma fila de coordenadas e testa
			*		as possibilidades, caso sejam válidas (diferentes de '#'), as armazena.
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
				*			O método atualizarVarizaveis() possui um nome sugestivo em relação a sua função,
				*		ele atribui um novo valor para a variavel "atual" com base nos dados da fila de posições
				*		disponíveis e jogará fora um ítem dela, porém, caso a fila esteja vazia, nada ocorrerá.
				*		Todos os passos descritos anteriormente só ocorrerão caso o movimento esteja normal, mas, se ele for regressivo,
				*		atual receberá uma coordenada do caminho já percorrido e marcará a posição atual no labirinto
				*		com um espaço em branco e uma das possibilidades será jogada fora.
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
		*		O método resolver() aplica os métodos do modo regressivo e progressivo até o fim da execução do programa,
		*	que é quando a saída é encontrada ou quando ela não existe (nesse último caso, nenhum modo pe feito).
		*	Caso a variável "progressivo" seja verdadeira, ele entrará no modo progressivo e, se tiver
		*	um valor falso, partirá para o modo regressivo.
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
		*		O método modoProgressivo() testa as posições disponíveis, atualiza as variáveis
		*	e escreve o caracter "*" nas posições do labirinto até achar a saída, o caracter 'S',
		*	usando as as coordenadas X e Y atuais.
		*
		*	@retorn nada quando o programa parar de ser executado, ou seja,
		*	quando a saída for encontrada, para sair do método.
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
			*		O método modoRegressivo() verifica se há uma saída no labirinto, caso não,
			*	ele retorna uma exeção e termina o programa. Em uma situação oposta, ele atualiza as
			*	variáveis do programa, v erifica se a fila não está vazia, ocorre uma segunda atualização de
			*	variáveis e ele compara os dados das cordenadas X e Y da posição atual, vendo se seus dados não
			*	são a saída para remarcar um asterisco e retornar ao modo progressivo do programa.
			*
			*	@throws Exception caso não haja o caracter 'S', ou seja, a saída do labirinto.
			*
	*/

	protected void modoRegressivo() throws Exception{
		if (possibilidades.isVazia()){
			terminou = true;
			throw new Exception("Não há saída para o labirinto!");
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
		*	O objeto isCompleto() é do tipo boolean.
		*
		*	@return uma variável boolean (nomeada "terminou") que, caso
		*	ela teja o valor true, o labirinto estará completo, porém, se for false, estará incompleto.
	*/

	public boolean isCompleto() {
		return terminou;
	}

	/**
				*		A String caminho() possui um nome quase auto-explicativo, quando o programa chega
				*	no fim do labirinto, ou seja, no momento em que acabar os procedimentos
				*	de percorrer o labirinto, ele exibirá as coordenadas do trajeto
				*	percorrido a partir de uma pilha de coordenadas chamada "inverso",
				*	nome dado por conta seus dados estarem na ordem oposta aos do caminho, faendo uma
				*	exibição de dados na ordem correta.
				*
				*	@return a String "ret" com todo o percurso no final da execução.
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