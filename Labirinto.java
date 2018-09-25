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
			readFile();
			dimensao = rows * columns;
			caminho = new Pilha<Coordenada>(dimensao);
			possibilidades = new Pilha<Fila<Coordenada>>(dimensao);
			findE();
			if (!terminou)
				resolver();
		}
		catch (Exception error) {System.err.println(error.getMessage());}
	}
	protected static void readFile() throws Exception{
		try
		{
			System.out.print("Digite aqui o nome do arquivo que será lido: ");
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			String nomeArquivo = keyboard.readLine();
			BufferedReader file = new BufferedReader(new FileReader(nomeArquivo));
			rows = Integer.parseInt(file.readLine());
			columns = Integer.parseInt(file.readLine());
			labirinto = new char[rows][columns];
			System.out.println();
			System.out.println();
			String line = "";
			for (int i = 0; i < rows; i++){
				line = file.readLine();
				for (int k = 0; k < columns; k++)
					labirinto[i][k] = line.charAt(k);
			}
			System.out.println();
			System.out.println();
		}
		catch (Exception error)
		{System.err.println(error.getMessage()); readFile();}
	}

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
			throw new Exception("Entrada do labirinto não encontrada!");
	}

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

	protected static void atualizarVariaveis() {
		try{
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
				//atualizacao de variaveis no modo regressivo
			}
		}
		catch (Exception erro) {System.err.println(erro.getMessage());}
	}

	protected static void resolver() {
		try {
			while (!terminou) {
				while (progressivo && !terminou)
					modoProgressivo();
				while (!progressivo && !terminou)
					modoRegressivo();
			}
		}
		catch (Exception erro) {System.err.println(erro.getMessage());}
	}

	protected static void modoProgressivo() {
		try {
			testarPosicoes();
			atualizarVariaveis();
			if (labirinto[atual.getX()][atual.getY()] == 'S'){
				ganhar();
				terminou = true;
				return;
			}
			labirinto[atual.getX()][atual.getY()] = '*';
		}
		catch (Exception erro) {System.err.println(erro.getMessage());}
	}

	protected static void modoRegressivo() throws Exception{
		try {
				atual = caminho.getUmItem();
				caminho.jogueForaUmItem();
				labirinto[atual.getX][atual.getY] = " ";
				fila = possibilidades.getUmItem();
				possibilidades.jogueForaUmItem();
				if(fila.isVazia())
					progressivo = false;
				else
				{
					atual = fila.getUmItem();
					fila.jogueForaUmItem();
					labirinto[atual.getX][atual.getY] = "*";
					caminho.guarde(atual);
					progressivo = true;
				}
		}
		catch (Exception erro) {System.err.println(erro.getMessage());}
	}

	protected static void desenhar() {
		for (int i = 0; i < rows; i++){
			for (int k = 0; k < columns; k++)
				if (k < columns - 1)
					System.out.print(labirinto[i][k]);
				else
					System.out.println(labirinto[i][k]);
		}
	}

	protected static void ganhar() {
		try {
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
		catch (Exception erro) {}
	}
}