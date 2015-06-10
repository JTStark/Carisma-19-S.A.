package implementations.combate;

/**
 * @author - Ot�vio Vansetti Miranda e Lucca Maia Bollani
 * 
 * Sistema de Combate para RPG Cyberpunk, DeltaNexus
 * 
 * Classe de personagem gen�rico, para teste
 * 
 */

import implementations.personagens.AbsPersonagem;

public class PersonGenerico extends AbsPersonagem {

	public PersonGenerico () {
		this.esquiva = (this.agilidade)/2.5 + (this.sorte)/5;
		this.critico = (this.agilidade)/5 + (this.sorte)/2.5;
	}
}
