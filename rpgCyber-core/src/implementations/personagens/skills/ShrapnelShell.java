package implementations.personagens.skills;

import implementations.personagens.AbsPersonagem;

import java.util.ArrayList;
import java.util.Random;

public class ShrapnelShell implements Skill {

	public static int tipoAlvo = 1;	
	public static int tipoSkill = 3;
	
	@Override
	public String execute(ArrayList<AbsPersonagem> Viloes, ArrayList<AbsPersonagem> heroiAtacantes, double dam, int trgt, AbsPersonagem heroiAtacante) {
		int danoFinal, resistencia, countHits=0;
		double armadura, fator;
		Random random = new Random();
		
		for (int i = 0; i < 2; i++) {
			if (trgt + i < Viloes.size()) trgt += i;
			
			armadura = (1 - ((Viloes.get(trgt-1).armadura*Viloes.get(trgt-1).buffArmaduraValor)/100));
			if (armadura < 0.1) armadura = 0.1; // evita armadura acima de 90% por buffs
			
			// Resistencia(com buffs)/5 * fator de nivel
			resistencia = (int)(((Viloes.get(trgt-1).resistencia*Viloes.get(trgt-1).buffResistenciaValor)/5)*(0.96 + (Viloes.get(trgt-1).level/15)));
			
			fator = random.nextInt(6)+1; //o fator � dividido por 3, assim 1 = 1/3 danoFinal, 2 = 2/3 danoFinal, 3 = danoFinal, 4 = 4/3 danoFinal, 5 = 5/3 danoFinal e 6 = 2 danoFinal. A media � o danoFinal esperado da arma
	
			danoFinal = ((int)((dam * (fator/3)) * armadura)) - resistencia; // danoFinal final
			
			//SKILL
			danoFinal = (int) (danoFinal * 0.2);
			//ENDSKILL
			
			if (danoFinal <= 0) danoFinal = 1; // danoFinal minimo � 1
			
			if ((int)(heroiAtacante.critico * heroiAtacante.buffCriticoValor)+random.nextInt(100)+1 >= 100) { // Soma a chance de critico com random 1-100. Se passar de 100 crita
				danoFinal *= 2;
				//SKILL
				if ((15 + random.nextInt(100)+1 + (heroiAtacante.percepcao/4)) > 100)	Viloes.get(trgt).buffBleedRounds = 3;
				//ENDSKILL
				Viloes.get(trgt-1).hp -= danoFinal;
				countHits++;
			}
			else if ((int)(Viloes.get(trgt-1).esquiva*Viloes.get(trgt-1).buffEsquivaValor)+random.nextInt(100)+1 < 100) { // Igual ao critico
				Viloes.get(trgt-1).hp -= danoFinal;
				//SKILL
				if ((15 + random.nextInt(100)+1 + (heroiAtacante.percepcao/4)) > 100)	Viloes.get(trgt).buffBleedRounds = 3;
				//ENDSKILL
				countHits++;
			}
		}
		return ("Voce acertou "+ countHits+" inimigos");
	}

}
