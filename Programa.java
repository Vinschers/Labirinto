/**
*	Classe executável para resolver o labirinto. Ela utiliza uma instância da classe Labirinto para resolver o problema.
*	@author Felipe Scherer Vicentin & Guilherme Salim de Barros
*	@since 2018
*/
import Labirinto.*;
import java.io.*;
public class Programa {
	public static void main(String[] args) {
		try {
			String arq = "";
			System.out.print("Digite aqui o nome do arquivo que será lido: ");
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			arq = keyboard.readLine();
			Labirinto lab = new Labirinto(arq);
			lab.resolver();
			if (lab.isCompleto()) {
				String arqFinal = arq.substring(arq.lastIndexOf('\\') + 1, arq.length() - 4) + ".res.txt";
				PrintStream resultado = new PrintStream(arqFinal);
				resultado.println(lab.toString());
				resultado.println("Caminho percorrido: ");
				resultado.print(lab.caminho());
				resultado.close();
				System.out.print("Sucesso! ");
				System.out.println("Verifique o arquivo " + arqFinal + ".");
			}
		}
		catch(Exception e){System.err.println(e.getMessage());}
	}
}