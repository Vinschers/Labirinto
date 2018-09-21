import java.io.*;
public class Labirinto{
	static private int rows = 0, columns = 0, dimensao = 0;
	static private char[][] labirinto;
	static private Coordenada atual;
	static private Fila<Coordenada> fila;
	static private Pilha<Fila<Coordenada>> possibilidades;
	static private Pilha<Coordenada> caminho;
	public static void main(String[] args) {
		try {
			readFile();
			dimensao = rows * columns;
			caminho = new Pilha<Coordenada>(dimensao);
			possibilidades = new Pilha<Fila<Coordenada>>(dimensao);
			findE();
			while (true) {
				testarPosicoes();
				atualizarVariaveis();
				if (labirinto[atual.getX()][atual.getY()] == 'S'){
					ganhar();
					break;
				}
				labirinto[atual.getX()][atual.getY()] = '*';
			}
		}
		catch (Exception error) {System.err.println(error.getMessage());}
	}
	private static void readFile() throws Exception{
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

	private static void findE() throws Exception{
		for (int i = 0; i < columns; i++) {			//bordas horizontais
			if (labirinto[0][i] == 'E')		//cima
				atual = new Coordenada(0, i);
			else if (labirinto[rows - 1][i] == 'E') //baixo
				atual = new Coordenada(rows - 1, i);
		}
		for (int i = 0; i < rows; i++) {			//bordas verticais
			if (labirinto[i][0] == 'E')	//esqueda
				atual = new Coordenada(i, 0);
			else if (labirinto[i][columns - 1] == 'E') //direita
				atual = new Coordenada(i, columns - 1);
		}
		if (atual == null)
			throw new Exception("Entrada do labirinto não encontrada!");
	}

	private static void testarPosicoes() throws Exception {
		fila = new Fila<Coordenada>(3);
		int row = atual.getX();
		int column = atual.getY();
		if ((column + 1 < columns) && (labirinto[row][column + 1] == ' ' || labirinto[row][column + 1] == 'S'))
			fila.guarde(new Coordenada(row, column + 1));
		if ((column - 1 >= 0) && (labirinto[row][column - 1] == ' ' || labirinto[row][column - 1] == 'S'))
			fila.guarde(new Coordenada(row, column - 1));
		if ((row + 1 < rows + 1) && (labirinto[row + 1][column] == ' ' || labirinto[row + 1][column] == 'S'))
			fila.guarde(new Coordenada(row + 1, column));
		if ((row - 1 >= 0) && (labirinto[row - 1][column] == ' ' || labirinto[row - 1][column] == 'S'))
			fila.guarde(new Coordenada(row - 1, column));
	}

	private static void atualizarVariaveis() {
		try{
			atual = fila.getUmItem();
			fila.jogueForaUmItem();
			caminho.guarde(atual);
			possibilidades.guarde(fila);
		}
		catch (Exception erro) {System.err.println(erro.getMessage());}
	}

	private static void ganhar() {
		try {
			Pilha<Coordenada> inverso = new Pilha<Coordenada>(dimensao);
			for (int i = 0; i < rows; i++){
				for (int k = 0; k < columns; k++)
					if (k < columns - 1)
						System.out.print(labirinto[i][k]);
					else
						System.out.println(labirinto[i][k]);
			}
			while (!caminho.isVazia()) {
				inverso.guarde(caminho.getUmItem());
				caminho.jogueForaUmItem();
				System.out.println(inverso.getUmItem());
			}
			//for (int i = 0; i < dimensao; i++)
				//System.out.print(inverso.getUmItem().toString());
			System.out.println("ganhou yay");
		}
		catch (Exception erro) {}
	}
}