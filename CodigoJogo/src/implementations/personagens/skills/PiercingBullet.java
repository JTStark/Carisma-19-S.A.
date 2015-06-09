package implementations.personagens.skills;

import implementations.personagens.AbsPersonagem;

import java.util.ArrayList;
import java.util.Random;

public class PiercingBullet implements Skill {

	@Override
	public boolean execute(ArrayList<AbsPersonagem> Viloes, ArrayList<AbsPersonagem> heroiAtacantes, double dam, int trgt, AbsPersonagem heroiAtacante) {
		int danoFinal, resistencia, n;
		double armadura, fator;
		Random random = new Random();
		
		//SKILL
		n = (int)heroiAtacante.percepcao/25;
		if(n >= Viloes.size() - trgt)
			n = Viloes.size() - 1;
		//ENDSKILL
		
		for(int i = trgt-1; i < n; i++){
			armadura = (1 - ((Viloes.get(i).armadura*Viloes.get(i).buffArmaduraValor)/100));
			if (armadura < 0.1) armadura = 0.1; // evita armadura acima de 90% por buffs
			
			// Resistencia(com buffs)/5 * fator de nivel
			resistencia = (int)(((Viloes.get(i).resistencia*Viloes.get(i).buffResistenciaValor)/5)*(0.96 + (Viloes.get(i).level/15)));
			
			fator = random.nextInt(6)+1; //o fator � dividido por 3, assim 1 = 1/3 danoFinal, 2 = 2/3 danoFinal, 3 = danoFinal, 4 = 4/3 danoFinal, 5 = 5/3 danoFinal e 6 = 2 danoFinal. A media � o danoFinal esperado da arma

			danoFinal = ((int)((dam * (fator/3)) * armadura)) - resistencia; // danoFinal final
			
			//SKILL
			danoFinal -= (int)danoFinal * 0.65;
			//ENDSKILL
			
			if (danoFinal <= 0) danoFinal = 1; // danoFinal minimo � 1
			
			if ((int)(heroiAtacante.critico * heroiAtacante.buffCriticoValor)+random.nextInt(100)+1 >= 100) { // Soma a chance de critico com random 1-100. Se passar de 100 crita
				danoFinal *= 2;
				Viloes.get(i).hp -= danoFinal;
				System.out.println("Voce atingiu " + Viloes.get(i).nome + " com um golpe critico! " + danoFinal + " de danoFinal!");
			}
			else if ((int)(Viloes.get(trgt-1).esquiva*Viloes.get(i).buffEsquivaValor)+random.nextInt(100)+1 < 100) { // Igual ao critico
				Viloes.get(i).hp -= danoFinal;
				System.out.println(Viloes.get(i).nome + " atingido! " + danoFinal + " de danoFinal!");
			}
			else
				System.out.println(Viloes.get(i).nome + " desviou!");
		}
		return false;
	}

}
