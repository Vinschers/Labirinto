import java.io.*;
public class Labirinto{
	static private int rows = 0, columns = 0;
	static private char[][] labirinto;
	static private Coordenada atual;
	public static void main(String[] args) {
		try {
			readFile();
			int dimensao = rows * columns;
			Pilha<Coordenada> caminho = new Pilha<Coordenada>(dimensao);
			Pilha<Fila<Coordenada>> possibilidades = new Pilha<Fila<Coordenada>>(dimensao);
			findE();
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
				System.out.println(line);
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
		for (int i = 0; i < columns; i++) {
			if (labirinto[0][i] == 'E')
				atual = new Coordenada(0, i);
			else if (labirinto[rows - 1][i] == 'E')
				atual = new Coordenada(rows - 1, i);
		}
		for (int i = 0; i < rows; i++) {
			if (labirinto[i][0] == 'E')
				atual = new Coordenada(i, 0);
			else if (labirinto[i][columns - 1] == 'E')
				atual = new Coordenada(i, columns - 1);
		}
		if (atual == null)
			throw new Exception("Entrada do labirinto não encontrada!");
	}
}