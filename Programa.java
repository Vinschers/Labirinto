/**
*	Classe executável para resolver o labirinto. Todas as suas partes estão encapsuladas em métodos.
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
				PrintStream resultado = new PrintStream(new File(arq + ".res.txt"));
				resultado.println(lab.labirinto());
				resultado.print(lab.caminho());
				resultado.close();
				System.out.print("Sucesso! ");
				System.out.println("Verifique o arquivo " + arq + ".res.txt.");
			}
		}
		catch(Exception e){System.err.println(e.getMessage());}
	}
}