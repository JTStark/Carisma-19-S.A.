package implementations.combate;

/**
 * @author - Ot�vio Vansetti Miranda e Lucca Maia Bollani
 * 
 * Sistema de Combate para RPG Cyberpunk, DeltaNexus
 * 
 * Classe principal/controladora do combate
 * 
 */

import java.util.ArrayList;

import implementations.personagens.AbsPersonagem;
import implementations.inventario.*;

import java.util.Scanner;
import java.util.Random;
import java.lang.reflect.Field;

public class CRodada {
	
	

	static int numRodada=0; //variavel global para que o antigo, deprecated BUFFS possa acompanhar a passagem de rodadas
	
	static Scanner scanner = new Scanner(System.in); //scanner para pegar a escolha
	Inventario inventario = Inventario.getInstancia();
	static int contI;
	public static int EXP;

	public static AbsPersonagem getVez () { //recebe ArrayList de herois e viloes ordenados
		AbsPersonagem actual = new PersonGenerico();
		
		if (contI < CEngine.listaI.size() && CEngine.listaH.isEmpty() == false && CEngine.listaV.isEmpty() == false) {
			
			actual = CEngine.listaI.get(contI);
			
			// subtrai danos por sangramento ou veneno
			if (actual.buffBleedRounds > 0) {
				actual.hp -= actual.hp * 0.1;
				actual.buffBleedRounds--;
			}
			if (actual.buffPoisonRounds > 0) {
				actual.hp -= actual.maxHP * 0.1;
				actual.buffPoisonRounds--;
			}
			
			// Remove duracao de 1 round dos buffs
			if (actual.buffForcaRounds > 0) actual.buffForcaRounds--;
			if (actual.buffPercepcaoRounds > 0) actual.buffPercepcaoRounds--;
			if (actual.buffResistenciaRounds > 0) actual.buffResistenciaRounds--;
			if (actual.buffCarismaRounds > 0) actual.buffCarismaRounds--;
			if (actual.buffInteligenciaRounds > 0) actual.buffInteligenciaRounds--;
			if (actual.buffAgilidadeRounds > 0) actual.buffAgilidadeRounds--;
			if (actual.buffSorteRounds > 0) actual.buffSorteRounds--;
			if (actual.buffArmaduraRounds > 0) actual.buffArmaduraRounds--;
			if (actual.buffEsquivaRounds > 0) actual.buffEsquivaRounds--;
			if (actual.buffCriticoRounds > 0) actual.buffCriticoRounds--;
			
			// Remove os buff cujos rounds acabaram
			if (actual.buffForcaRounds == 0) actual.buffForcaValor=1;
			if (actual.buffPercepcaoRounds == 0) actual.buffPercepcaoValor=1;
			if (actual.buffResistenciaRounds == 0) actual.buffResistenciaValor=1;
			if (actual.buffCarismaRounds == 0) actual.buffCarismaValor=1;
			if (actual.buffInteligenciaRounds == 0) actual.buffInteligenciaValor=1;
			if (actual.buffAgilidadeRounds == 0) actual.buffAgilidadeValor=1;
			if (actual.buffSorteRounds == 0) actual.buffSorteValor=1;
			if (actual.buffArmaduraRounds == 0) actual.buffArmaduraValor=1;
			if (actual.buffEsquivaRounds == 0) actual.buffArmaduraValor=1;
			if (actual.buffCriticoRounds == 0) actual.buffCriticoValor=1;		
		
			contI++;
			if (contI >= CEngine.listaI.size()) contI = 0;
			
			return actual;
		
		} else {
			System.out.println("Fim de Combate");
			return null;			
		}
	}
	
	public static int GetRepDist (AbsPersonagem Jogador) {
		//Retorna a distancia que um jogador pode se mecher ao se reposicionar
		return ((int)(Jogador.agilidade * Jogador.buffAgilidadeValor / 25) + 1);
	}
	
	public static void Reposition (ArrayList <AbsPersonagem> Jogadores, int posAtual, int posDestino) { // recebe o vetor de jogadores apropriado e a posicao do jogador atual
		AbsPersonagem temp = new PersonGenerico();		
		
		// reposiciona o jogador para a posicao escolhida
		temp = Jogadores.get(posAtual);
		Jogadores.remove(posAtual);
		Jogadores.add(posDestino, temp);
	}
	
	public static String getAlvos (int skill, AbsPersonagem ator) {
		
		switch (skill) {
			
			case 0:
				if (ator.skill0.tipoAlvo == 1) {
					if (ator.tipo == 1) return "melee";
					else return "ranged";
				}
				else if (ator.skill0.tipoAlvo == 2) return "amigo";
				else if (ator.skill0.tipoAlvo == 3) return "semAlvo";
			
			case 1:
				if (ator.skill1.tipoAlvo == 1) {
					if (ator.tipo == 1) return "melee";
					else return "ranged";
				}
				else if (ator.skill1.tipoAlvo == 2) return "amigo";
				else if (ator.skill1.tipoAlvo == 3) return "semAlvo";
			
			case 2:
				if (ator.skill2.tipoAlvo == 1) {
					if (ator.tipo == 1) return "melee";
					else return "ranged";
				}
				else if (ator.skill2.tipoAlvo == 2) return "amigo";
				else if (ator.skill2.tipoAlvo == 3) return "semAlvo";
				
			case 3:
				if (ator.skill3.tipoAlvo == 1) {
					if (ator.tipo == 1) return "melee";
					else return "ranged";
				}
				else if (ator.skill3.tipoAlvo == 2) return "amigo";
				else if (ator.skill3.tipoAlvo == 3) return "semAlvo";
		
		}
		
		return null;
		
	}
	
	public static String atacar (int trgt, int acao, AbsPersonagem ator) {
		double weaponDam;
		String retorno = "";
		
		
		if (ator.tipo == 1) weaponDam = /*ator.danoArma*/30*(1 + (ator.forca*ator.buffForcaValor)/50)+(0.96+(ator.level/25))*0.5; //com melhor arma 100 dano, 100 for�a/percep, lvl 50: 250/3 (min) - 250 (medio) - 500 (max) - 1000 (crit)
		else weaponDam = /*ator.danoArma*/10*(1 + (ator.percepcao*ator.buffPercepcaoValor)/50)+(0.96+(ator.level/25))*0.5; //com pior arma 4 dano, 15 for�a/percep, lvl 1: 1 (min) - 4 (medio) - 8 max - 16 (crit)
		
		switch (acao) {
			
			case 0:
				retorno = ator.skill0.execute(CEngine.listaV, CEngine.listaH, weaponDam, trgt, ator);
				break;	
			
			case 1:
				retorno = ator.skill1.execute(CEngine.listaV, CEngine.listaH, weaponDam, trgt, ator);
				break;	
			
			case 2:
				retorno = ator.skill2.execute(CEngine.listaV, CEngine.listaH, weaponDam, trgt, ator);
				break;	
				
			case 3:
				retorno = ator.skill3.execute(CEngine.listaV, CEngine.listaH, weaponDam, trgt, ator);
				break;		
		}
		
		if (CEngine.listaV.get(trgt-1).hp <= 0) {
			CEngine.listaI.remove(CEngine.listaV.get(trgt-1));
			CEngine.listaV.remove(trgt-1);
		}
		
		return retorno;
	}
	
	@Deprecated
	public static boolean attack (AbsPersonagem Heroi, ArrayList <AbsPersonagem> Viloes, int posHeroi) {
		Random random = new Random(); // Gerador de numeros randomicos
		String chc; // String guarda escolha do jogador
		int trgt = 1, dano, resistencia; // Guardam respectivamente: alvo do jogador, dano final, resistencia final
		double weaponDam, armadura, fator; // Guardam respectivamente: dano da arma ponderado, armadura ponderada, fator randomico ponderado
		boolean choiceFlag1, choiceFlag2, noAtk = false; // Flags para parar os loops de escolha de acao. noAtk permite voltar ao menu de "Jogada" sem perder a vez
		
		//Melee usa forca, ranged usa percepcao. dano = (dano arma * (1+(forca/50)+(fator de nivel)) /2 ). Fator nivel eh 1 no nivel 1 e sobe pra 2 no nivel 50
		if (Heroi.tipo == 1) weaponDam = Heroi.danoArma*(1 + (Heroi.forca*Heroi.buffForcaValor)/50)+(0.96+(Heroi.level/25))*0.5; //com melhor arma 100 dano, 100 for�a/percep, lvl 50: 250/3 (min) - 250 (medio) - 500 (max) - 1000 (crit)
		else weaponDam = Heroi.danoArma*(1 + (Heroi.percepcao*Heroi.buffPercepcaoValor)/50)+(0.96+(Heroi.level/25))*0.5; //com pior arma 4 dano, 15 for�a/percep, lvl 1: 1 (min) - 4 (medio) - 8 max - 16 (crit)
		
		choiceFlag1 = true;
		while (choiceFlag1) {
			System.out.println("Selecione seu ataque: ");
			System.out.println("ataque Basico (B)");
			System.out.println("Habilidade (1): " + Heroi.nSkill1);
			System.out.println("Habilidade (2): " + Heroi.nSkill2);
			System.out.println("Habilidade (3): " + Heroi.nSkill3);
			System.out.println("Cancelar (C)");
			
			chc = scanner.nextLine();
			
			if ((chc.equalsIgnoreCase("B"))) {

				if((Heroi.tipo==1 && posHeroi < 2) || Heroi.tipo!=1) { // Se heroi for melee, fora da frente (1� e 2� posi��es), nao pode usar ataque basico
					
					choiceFlag2 = true;
					
					while (choiceFlag2) {
						
						System.out.println("Selecione seu alvo (1-6)");
						trgt = scanner.nextInt();
						
						if((Heroi.tipo == 1 && trgt <=2) || Heroi.tipo != 1) { // Se heroi for melee, nao pode acertar alvos fora da frente (1� e 2� posi��es)
							if (trgt >= 1 && trgt <= 6) {
								
								// dano vai de 1/3*esperado a 2*esperado. Maximo de redu��o eh (dano/2,5 - 80), com 60 armadura, lvl 50 e 100 de resistencia
								armadura = (1 - ((Viloes.get(trgt-1).armadura*Viloes.get(trgt-1).buffArmaduraValor)/100));
								if (armadura < 0.1) armadura = 0.1; // evita armadura acima de 90% por buffs
								
								// Resistencia(com buffs)/5 * fator de nivel
								resistencia = (int)(((Viloes.get(trgt-1).resistencia*Viloes.get(trgt-1).buffResistenciaValor)/5)*(0.96 + (Viloes.get(trgt-1).level/15)));
								
								fator = random.nextInt(6)+1; //o fator � dividido por 3, assim 1 = 1/3 dano, 2 = 2/3 dano, 3 = dano, 4 = 4/3 dano, 5 = 5/3 dano e 6 = 2 dano. A media � o dano esperado da arma

								dano = ((int)((weaponDam * (fator/3)) * armadura)) - resistencia; // Dano final
								if (dano <= 0) dano = 1; // Dano minimo � 1
								
								if ((int)(Heroi.critico * Heroi.buffCriticoValor)+random.nextInt(100)+1 >= 100) { // Soma a chance de critico com random 1-100. Se passar de 100 crita
									dano *= 2;
									Viloes.get(trgt-1).hp -= dano;
									System.out.println("Voce atingiu " + Viloes.get(trgt-1).nome + " com um golpe critico! " + dano + " de dano!");
								}
								else if ((int)(Viloes.get(trgt-1).esquiva*Viloes.get(trgt-1).buffEsquivaValor)+random.nextInt(100)+1 < 100) { // Igual ao critico
									Viloes.get(trgt-1).hp -= dano;
									System.out.println(Viloes.get(trgt-1).nome + " atingido! " + dano + " de dano!");
								}
								else
									System.out.println(Viloes.get(trgt-1).nome + " desviou!");
								choiceFlag2 = false;
								choiceFlag1 = false;
							}
							else
								System.out.println("Alvo invalido! Tente novamente");
						}
						else {
							System.out.println("Alvo muito distante para combate a curta distancia! Escolha um alvo mais proximo.");
							choiceFlag2 = false;
						}
					}
			
				}
				else {
					System.out.println("Alvo muito distante para combate a curta distancia! Aproxime-se antes de atacar ou escolha outra habilidade.");
					choiceFlag2 = false;
				}				
					
			}
			
			// Usa as skills
			/*else if ((chc.equalsIgnoreCase("1")))
				noAtk = Heroi.Skill1(Viloes, weaponDam, trgt);
			else if ((chc.equalsIgnoreCase("2")))
				noAtk = Heroi.Skill2(Viloes, weaponDam, trgt);
			else if ((chc.equalsIgnoreCase("3")))
				noAtk = Heroi.Skill3(Viloes, weaponDam, trgt);*/
			else if ((chc.equalsIgnoreCase("c")))
				noAtk = true;
			else
				System.out.println("Ataque invalido: Tente denovo");
		}
		
		// Se morrer, tira do vetor de posi��o (e por tabela do view/GUI
		if (Viloes.get(trgt-1).hp <= 0) {
			System.out.println(Viloes.get(trgt-1).nome + " foi morto!");
			Viloes.remove(trgt-1);
		}
		
		// Retorna essa flag positiva caso o ataque tenha sido cancelado, permitindo outra a��o no turno
		return noAtk;
	}
	
	
	
	
	
	public static int endBattle (ArrayList <AbsPersonagem> Herois, ArrayList <AbsPersonagem> Lista) {
		int retorno;
		if (Herois.isEmpty()) { // Se todo mundo morreu, fudeu
			//n sei o que fazer pra rodar um gameover
			System.out.println("GAME OVER, MWAHAHAHAHA");
			
			retorno = 0;
		}
		
		else {
			// Roda o vetor de jogada. Adiciona XP equivalente ao nivel de cada vilao morto � pilha de XP
			for (AbsPersonagem P: Lista) {
				if ((P.vilao) && (P.hp <= 0))
					EXP += P.level;
			}
			
			retorno = 1;
		}
		
		CEngine.listaI.clear();
		CEngine.listaV.clear();
		
		contI = 0;
		EXP = 0;
		
		return retorno;
		
	}	
	
	public static boolean useItem (int contI, ArrayList <AbsPersonagem> Lista) {
		//Colocar essas paradas dentro de um metodo
		Inventario inventario = Inventario.getInstancia();
		String nome_item;
		String tipo_item;
		int bonus_item;
		Item usable;
		int itemselecionado;
		String chc;
		
		
		System.out.println("Escolha o item da mochila, ou escreva cancela para sair");
		inventario.getMochila();
		nome_item = "";
		tipo_item = ""; //so foi declarado valor para essas variaveis pq o java eh uma putinha reclamona
		bonus_item = 1;
		itemselecionado = 0;
		while(itemselecionado==0){
			chc = scanner.nextLine();
			usable = new Item(chc);
			if(usable.nomeEncontrado){
				usable.nomeEncontrado = false; // sera q dara erro de depois q encontrar uma vez, sempre encontrar mesmo q nao exista ?
				itemselecionado = 1;
				nome_item = usable.getName();
				tipo_item = usable.getType();
				bonus_item = usable.getBonus();
			}
			else if(chc.equalsIgnoreCase("cancela")) {
				itemselecionado = 2;
				System.out.println("Operacao cancelada");
			}
			else System.out.println("Este item nao existe, escolha outro ou digite 'cancela' ");
		}
		if(itemselecionado == 1){
			switch (tipo_item) {
			case "HP":
				Lista.get(contI).hp += (int)(Lista.get(contI).hp * (bonus_item*0.01));
				inventario.remover_item(nome_item);
				return false;
			case "STR":
				Lista.get(contI).buffForcaRounds += 3;
				Lista.get(contI).buffForcaValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			case "PER":
				Lista.get(contI).buffPercepcaoRounds += 3;
				Lista.get(contI).buffPercepcaoValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			case "END":
				Lista.get(contI).buffResistenciaRounds += 3;
				Lista.get(contI).buffResistenciaValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			case "CHA":
				Lista.get(contI).buffCarismaRounds += 3;
				Lista.get(contI).buffCarismaValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			case "INT":
				Lista.get(contI).buffInteligenciaRounds += 3;
				Lista.get(contI).buffInteligenciaValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			case "AGI":
				Lista.get(contI).buffAgilidadeRounds += 3;
				Lista.get(contI).buffAgilidadeValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			case "LCK":
				Lista.get(contI).buffSorteRounds += 3;
				Lista.get(contI).buffSorteValor = 1 + bonus_item*0.01;
				inventario.remover_item(nome_item);
				return false;
			default:
				System.out.println("Este item nao pode ser usado");
			}
		}
	return true;
	}
	
	// O buff correto devera incrementar apenas .Buff___Rounds
	@Deprecated
	public static void BUFF (AbsPersonagem alvo, String atributo, int stat, int time) {
		Class<AbsPersonagem> classe = AbsPersonagem.class;
		Field att = null;
		
		try {
			
			att = classe.getDeclaredField(atributo); // Pega o atributo certo da classe
			
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		if (!atributo.equalsIgnoreCase("bleed") && !atributo.equalsIgnoreCase("poison") && !atributo.equalsIgnoreCase("stun")) {
			try {
				
				att.set(alvo, (att.getInt(alvo) + stat)); // Aumenta o atributo
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		while(numRodada < numRodada+time); // Passa o numero de turnos necess�rio
		
		if (!atributo.equalsIgnoreCase("bleed") && !atributo.equalsIgnoreCase("poison") && !atributo.equalsIgnoreCase("stun")) {
			try {
				
				att.set(alvo, (att.getInt(alvo) - stat)); // Diminui o atributo
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String AI (ArrayList <AbsPersonagem> Herois, ArrayList <AbsPersonagem> Viloes,  ArrayList <AbsPersonagem> Lista, AbsPersonagem vilao) {
		int atk, trgt = 1, contP, contI;
		double weaponDam;
		boolean flag = true;
		Random random = new Random(); // gerador de numeros randomicos
		AbsPersonagem temp = new PersonGenerico();
		String retorno = "nao fez nada";
		
		for (contI = 0; Lista.get(contI) != vilao; contI++);
		
		for (contP = 0; Viloes.get(contP) != vilao; contP++);
		
		System.out.println("IMPRIMINDOOOOOOO: " + "P " + contP +"i " + contI);
		
		System.out.println(Lista.get(contI).nome + " " + Lista.get(contI).iniciativa);
			
		// compara se a escolha eh compativel com alguma opcao vailda e roda a funcao apropriada
		/*if ((Lista.get(contI).tipo != 1) && (contP < 2)) {
			
			if (Viloes.size() > contP) {
				temp = Viloes.get(contP);
				Viloes.remove(contP);
				Viloes.add(contP+1, temp);
				
				retorno = (Lista.get(contI).nome + " se reposicionou");
				
				flag = false;
			}				
		}
		
		if ((Lista.get(contI).tipo == 1) && (contP > 1)) {
			
			temp = Viloes.get(contP);
			Viloes.remove(contP);
			Viloes.add(contP-1, temp);
			
			flag = false;
		}*/
		
		if (flag) {
			atk = random.nextInt(100) + 1;
			trgt = random.nextInt(6)+1;
			
			if (Viloes.get(contP).tipo == 1) weaponDam = Viloes.get(contP).danoArma*(1 + (Viloes.get(contP).forca*Viloes.get(contP).buffForcaValor)/50)+(0.96+(Viloes.get(contP).level/25))*0.5;
			else weaponDam = Viloes.get(contP).danoArma *(1 + (Viloes.get(contP).percepcao*Viloes.get(contP).buffPercepcaoValor)/50)+(0.96+(Viloes.get(contP).level/25))*0.5;
			
			if (atk <= 50) retorno = Viloes.get(contP).skill0.execute(Herois, Viloes, weaponDam, trgt, Viloes.get(contP));
			else if ((atk > 50) && (atk <= 75)) retorno = Viloes.get(contP).skill1.execute(Herois, Viloes, weaponDam, trgt, Viloes.get(contP));
			else if ((atk > 75) && (atk <= 90)) retorno = Viloes.get(contP).skill2.execute(Herois, Viloes, weaponDam, trgt, Viloes.get(contP));
			else if ((atk > 90) && (atk <= 100)) retorno = Viloes.get(contP).skill3.execute(Herois, Viloes, weaponDam, trgt, Viloes.get(contP));
				
		}
		
		if (Herois.get(trgt-1).hp <= 0) {
			System.out.println(Herois.get(trgt-1).nome + " foi nocauteado!");
			Herois.remove(trgt-1);
		}
		return retorno;	
	}
	
	public static String exp (int EXP, AbsPersonagem ganhador) {
		String nome;
		ganhador.CountXP(EXP);
		nome = ganhador.nome;
		CEngine.listaH.remove(ganhador);
		return (nome + " ganhou " + EXP + " pontos de experiencia!");
	}
}
