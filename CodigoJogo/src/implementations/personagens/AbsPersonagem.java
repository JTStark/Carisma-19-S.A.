package implementations.personagens;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Classe abstrata para os personagens
 * 
 * @author Johnny Stark
 *
 */

public abstract class AbsPersonagem {
	public int forca, percepcao, resistencia, carisma, inteligencia, agilidade, sorte;
	public int buffforcarounds, buffpercepcaorounds, buffcarismarounds, buffresistenciarounds, buffinteligenciarounds, buffagilidaderounds, buffsorterounds;
	public double buffforcavalor=1, buffpercepcaovalor=1, buffcarismavalor=1, buffresistenciavalor=1, buffinteligenciavalor=1, buffagilidadevalor=1, buffsortevalor=1;
	public double esquiva, critico;
	public int hp, maxHP, xp, level, armadura, iniciativa, tipo /*1 = melee, 3 = ranged, 5 = support*/;
	public boolean bleed = false, poison = false, stun = false, vilao = false;
	public String nome, nSkill1, nSkill2, nSkill3;
	/*Inventario inventario;*/
	
	/*m�todo que define os valores iniciais dos atributos do personagem*/
	public void SetAtributos (){
		
	}
	
	public void Skill1 (ArrayList <AbsPersonagem> Viloes, double dam) { // nos viloes tem que adicionar tambem o alvo ja escolhido
		
	}
	
	public void Skill2 (ArrayList <AbsPersonagem> Viloes, double dam) {
		
	}
	
	public void Skill3 (ArrayList <AbsPersonagem> Viloes, double dam) {
		
	}

	/*m�todo que renderiza o personagem*/
	public void Render (){
		
	}
	
	/*m�todo que altera a vida do personagem*/
	public void Damage_Heal (int modHP){
		this.hp += modHP;
		
		if (this.hp > this.maxHP)
			this.hp = this.maxHP;
		
		else if (this.hp <= 0)
			System.out.println("Game Over"); /*chamar fun��o de game over; isso provis�rio*/
	}
	
	/*m�todo que altera o xp do personagem*/
	public void CountXP (int modXP){
		this.xp += modXP;
		
		/*daqui pra baixo tamb�m � provis�rio*/
		if (this.xp >= this.level*10)		
			this.LevelUp();
	}
	
	/*m�todo que upa o personagem*/
	public void LevelUp (){
		String atributo;
		
		this.xp -= this.level*10;
		this.level++;
		
		// vida vai de 100 a 500 (min)/3433(max)
		this.maxHP += this.resistencia*2/3;
		this.hp += this.resistencia*2/3;
				
		// 105 pontos de atributo no nivel 1 (15 em cada) + 5 atributos por nivel (49 niveis) = 350 no total ao nivel 50
		for(int i = 5; i > 0; i--){
			atributo = new Scanner(System.in).nextLine();
			
			if (atributo.equalsIgnoreCase("forca"))
				this.forca++;
			
			else if (atributo.equalsIgnoreCase("percepcao"))
				this.percepcao++;
			
			else if (atributo.equalsIgnoreCase("resistencia"))
				this.resistencia++;
			
			else if (atributo.equalsIgnoreCase("carisma"))
				this.carisma++;
			
			else if (atributo.equalsIgnoreCase("inteligencia"))
				this.inteligencia++;
			
			else if (atributo.equalsIgnoreCase("agilidade"))
				this.agilidade++;
			
			else if (atributo.equalsIgnoreCase("sorte"))
				this.sorte++;
			
			else {
				System.out.println("Entrada Invalida");
				i++;
			}
		}
		
	}


}
