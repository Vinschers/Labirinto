import java.io.*;
public class Labirinto{
	static private int rows = 0, columns = 0;
	static private char[][] labirinto;
	public static void main(String[] args) {
		readFile();
	}
	private static void readFile() {
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
}