package hulkSmag;
import robocode.*;
import java.awt.Color;
import java.util.Random;


// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * HulkSmag - a robot by (Emanoel Rainey. Edenilson Gomes. Samara, Christy, Gisele)
 * version 1.3
 * Implantação do nivel para berserk
 */
public class HulkSmag extends AdvancedRobot
{


	boolean randomValues = false;
	double coefGunPower = 397.1;
	double distanceDefault = 102.5;
	double coefPrecision = 0;
	double addRotation =0;


	/**
	 * run: HulkSmag's default behavior
	*/

	public void run() {

		setColors(new Color(80,164,57),new Color(2,28,2),Color.green); 
		setTurnGunRight(0);

		while(true) {

			turnRadarRight(360);
			setAhead(distanceDefault);
			execute();

		}
	}
	 
	 /* ----------------------------------------------------------------------------------------------------------- //	
	 // onScannedRobot: Quando o radar encontra um outro robo
	 // getHeading() = a direção do HulkSmag em relação ao terreno
	 // e.getBearing() = o angulo entre a direção do HulkSmag e o robo encontrado
	 // getGunHeading() = ao angulo do canhão do HulkSmag e relação ao robo
	 // turnRight(double) = virar o robo a direira em uma quantidade de angulo em relação ao terreno
	 // turnGunRight(double) = vira o canhão para um algulo especifico em relação a frente do robo
	 // fire(double [0.1 - 3.0]) = atira com uma força baseada no intervalo 
	 // ----------------------------------------------------------------------------------------------------------- */
	 public void onScannedRobot(ScannedRobotEvent e) {

        double turnAngle = getHeading() + e.getBearing() - getGunHeading();    // calculo do angulo para o robo inimigo
        
		if (turnAngle<0) setTurnRight(turnAngle - coefPrecision);
		setTurnRight(turnAngle + coefPrecision);                                                 // vira o corpo do robo em direção ao robo encontrado
        //incrementPrecision();
                                                 
		execute();

		fire(getGunPower(e.getDistance()));	  								   // atira baseado em cálculo da distancia	
		//LeagueOfLegends.fireBullet();                                          // incrementa o número de disparos

		double enemyEnergy = e.getEnergy();
		double myEnergy = getEnergy();

		double radarTurn = getHeading() - getRadarHeading() + e.getBearing();
        setTurnRadarRight(radarTurn);
		setAhead(distanceDefault);
        execute();                           
    }


	/* ----------------------------------------------------------------------------------------------------------- //	
	 // getGunPower: Método para cálculo da força do tiro do robo
	 // distance = distancia do alvo
	 // coefGunPower = melhor valor encontrado nos testes
	 // ----------------------------------------------------------------------------------------------------------- */	
	private double getGunPower(double distance){
		return 3 - (distance / (400+coefGunPower)); //0.1-3.0 *3
	}//3-(800/400+150)0,3 =2,7 1,55



	private void incrementPrecision(){
		if (coefPrecision < 15)
			coefPrecision += addRotation;
		else
			coefPrecision =0;
	}

	private void decrementPrecision(){
		if (coefPrecision < 15)
			coefPrecision += addRotation;
		else
			coefPrecision =0;
	}
}