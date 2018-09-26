import Fila.*;
import Pilha.*;
import Coordenada.*;
import java.io.*;
public class Labirinto{
	static protected int rows = 0, columns = 0, dimensao = 0;
	static protected char[][] labirinto;
	static protected Coordenada atual;
	static protected Fila<Coordenada> fila;
	static protected Pilha<Fila<Coordenada>> possibilidades;
	static protected Pilha<Coordenada> caminho;
	static protected boolean progressivo = true, terminou = false;
	public static void main(String[] args) {
		try {
			readFile(lerTeclado());
			initialize();
			resolver();
		}
		catch (Exception error) {
			System.err.println(error.getMessage());
		}
	}

	/**
	*		O m�todo lerTeclado() pede para o usu�rio digitar o nome
	*	do arquivo com o labirinto, o verifica a validade dele.
	*	@return o nome do arquivo
	*/
	private static String lerTeclado() throws Exception {
		String ret = "";
		try {
			System.out.print("Digite aqui o nome do arquivo que ser� lido: ");
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			ret = keyboard.readLine();
		} catch(Exception e) {System.err.println(e.getMessage());}
		return ret;
	}


	/**
		*		O m�todo readFile() l� o labirinto e atribui � matriz
		*	labirinto as colunas e linhas, a entrada e a sa�da, verificando
		*	a exist�ncia de 'E' e 'S'.
		*
		*	@throws  Exception se n�o houver sa�da do labirinto
	*/
	protected static void readFile(String nomeArquivo) throws Exception{
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
		*	chamando o m�todo E. A aplica��o desse m�todo � realizada no come�o da execu��o do programa.
	*/

	protected static void initialize() throws Exception{
		dimensao = rows * columns;
		caminho = new Pilha<Coordenada>(dimensao);
		possibilidades = new Pilha<Fila<Coordenada>>(dimensao);
		findE();
	}

	/**

		*		O m�todo findE(), como surgere o seu pr�rio nome, tem a fun��o
		*	de encontrar o caracter 'E' no labirinto, que, na realidade, � a entrada
		*	do trageto a ser percorrido.
		*
		*	@return nada para sair da busca quando achar 'E', pois � desnecess�rio contiu�-la.
		*	@throws Exception caso a entrada n�o seja encontrada.
	*/

	protected static void findE() throws Exception{
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
			*		O m�todo testarPosicoes() delclara uma fila de coordenadas e testa
			*		as possibilidades, caso sejam v�lidas (diferentes de '*'), as armazena.
	*/

	protected static void testarPosicoes() throws Exception {
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
				*		dispon�veis, por�m, caso a fila esteja vazia, nada ocorrer�. Todos os passos descritos
				*		anteriormente s� ocorrer�o caso o movimento esteja normal, mas, se ele for regressivo,
				*		atual receber� uma coordenada do caminho j� percorrido e marcar� a posi��o atual no labirinto
				*		com um espa�o em branco.
	*/

	protected static void atualizarVariaveis() throws Exception{
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
			fila = possibilidades.getUmItem();
			possibilidades.jogueForaUmItem();
			labirinto[atual.getX()][atual.getY()] = ' ';
		}
	}

	protected static void resolver() throws Exception{
		while (!terminou) {
			while (progressivo && !terminou)
				modoProgressivo();
			while (!progressivo && !terminou)
				modoRegressivo();
		}
	}

	protected static void modoProgressivo() throws Exception{
		testarPosicoes();
		atualizarVariaveis();
		if (labirinto[atual.getX()][atual.getY()] == 'S'){
			ganhar();
			terminou = true;
			return;
		}
		labirinto[atual.getX()][atual.getY()] = '*';
	}

	protected static void modoRegressivo() throws Exception{
		if (possibilidades.isVazia()){
			terminou = true;
			throw new Exception("N�o h� sa�da para o labirinto!");
		}
		atualizarVariaveis();
		if (!fila.isVazia()) {
			progressivo = true;
			atual = fila.getUmItem();
			if (labirinto[atual.getX()][atual.getY()] != 'S')
				labirinto[atual.getX()][atual.getY()] = '*';
		}
	}

	private static void desenhar() {
		for (int i = 0; i < rows; i++){
			for (int k = 0; k < columns; k++)
				if (k < columns - 1)
					System.out.print(labirinto[i][k]);
				else
					System.out.println(labirinto[i][k]);
		}
	}

	protected static void ganhar() throws Exception{
		Pilha<Coordenada> inverso = new Pilha<Coordenada>(dimensao);
		desenhar();
		while (!caminho.isVazia()) {
			inverso.guarde(caminho.getUmItem());
			caminho.jogueForaUmItem();
		}
		System.out.println("Caminho que foi percorrido: ");
		while (!inverso.isVazia()) {
			System.out.println(inverso.getUmItem());
			inverso.jogueForaUmItem();
		}
	}
}