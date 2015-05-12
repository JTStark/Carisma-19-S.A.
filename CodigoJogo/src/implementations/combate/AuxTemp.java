package implementations.combate;

/*
 * Autores: Ot�vio Vansetti Miranda e Lucca Maia Bollani
 * 
 * Sistema de Combate para RPG Cyberpunk, DeltaNexus
 * 
 * Classe de teste
 * 
 */
import java.util.ArrayList;

public class AuxTemp {
	
	public static void main(String[] args){
		PersonGenerico hFirst = new PersonGenerico();
		PersonGenerico hSecond = new PersonGenerico();
		PersonGenerico vFirst = new PersonGenerico();
		PersonGenerico vSecond = new PersonGenerico();
		
		ArrayList<PersonGenerico> vetorHer = new ArrayList<PersonGenerico> ();
		hFirst.agilidade = 99;
		hFirst.sorte = 99;
		hFirst.nome = "hFirst";
		hFirst.tipo = 1;
		hSecond.agilidade = 1;
		hSecond.sorte = 1;
		hSecond.nome = "hSecond";
		hSecond.tipo = 2;
		vetorHer.add(0, hFirst);
		vetorHer.add(1, hSecond);
		
		ArrayList<PersonGenerico> vetorVil = new ArrayList<PersonGenerico> ();
		vFirst.agilidade = 50;
		vFirst.sorte = 50;
		vFirst.nome = "vFirst";
		vFirst.tipo = 1;
		vSecond.agilidade = 1;
		vSecond.sorte = 1;
		vSecond.nome = "vSecond";
		vSecond.tipo = 2;
		vetorVil.add(0, hFirst);
		vetorVil.add(1, hSecond);
		
		CEngine.CombatEngine(vetorHer, vetorVil);
	}
	
}
