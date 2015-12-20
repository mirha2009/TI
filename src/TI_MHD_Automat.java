import java.util.*;

/**
 * Rizeni prodejniho automatu na MHD jizdenky
 * @author Miroslav Havlicek, Patrik Jaros
 */

public class TI_MHD_Automat {

	static Scanner sc = new Scanner(System.in);
	
	/* Zaklopka na vhazovani minci (false - zavrena, true - otevrena) */
	static boolean vhazovaniPovoleno = false;
	
	/* Napajeni (true - napajeni ze site, false - napajeni z baterie) */
	static boolean napajeni = true;
	
	/* Hodnoty vsech minci v Kc */
	static int hodnota_minci[] = { 1, 2, 5, 10, 20, 50 };
	
	/* Hlavni zasobnik minci, kazdy index udava pocet minci dane hodnoty */
	static int pocet_minci[] = { 500, 250, 100, 50, 25, 10 };
	
	/**
	 * Ceny jizdneho
	 * index 0 az 3: ceny plnocennych jizdenek (30 minut, 60 minut, 180 minut a 24 hodin)
	 * index 4 az 7: ceny zlevnenych jizdenek (ve stejném poradi)
	 */
	static int ceny_jizdneho[] = { 16, 20, 34, 60, 8, 10, 17, 30 };
	
	/* Vstupni zasobnik minci, udava pocet minci dane hodnoty ve vstupnim zasobniku */
	static int vstupZ[] = { 0, 0, 0, 0, 0, 0 };
	
	/* Vystupni zasobnik minci, udava pocet minci dane hodnoty ve vystupnim zasobniku */
	static int vystup[] = { 0, 0, 0, 0, 0, 0 };
	
	/* 3 pole udavajici casove hodnoty pro jednotlive typy jizdneho v minutach, hodinach a ve dnech */
	static int platnost_min[] = { 30, 0, 0, 0 };
	static int platnost_hod[] = { 0, 1, 3, 0 };
	static int platnost_dny[] = { 0, 0, 0, 1 };
	
	/* Promenna uchovavajici pocet zbyvajicich vytisku jizdenek */
	static int vytisky = 4;
	
	/**
	 * @index uchovava index vybrane jizdenky. (0 az 7)
	 * @zbyva uchovava hodnotu ktera zbyva do zaplaceni (kladna), nebo kterou je treba po transakci vratit (zaporna)
	 */
	static int index, zbyva;
	
	/**
	 * Vypne automat v pripade poruchy.
	 */
	public static void ukonceniAutomatu(){
		System.out.println("Automat byl z duvodu poruchy vypnut.");
		System.exit(0);
	}
	
	/**
	 * Vrati pripadne jiz vhozene mince od klienta, pred vypnutim automatu.
	 */
	public static void vraceniPenezKontrola() {
		System.out.println("Automat mimo provoz, pockejte na vraceni vhozenych minci...");
		for (int i = 0; i < vstupZ.length; i++) {
			if (vstupZ[i] != 0)
				System.out.println("Vraceno " + vstupZ[i] + " x " + hodnota_minci[i] + " kc.");
		}
	}
	
	/**
	 * Provadi kontrolu cidel v prubehu programu
	 */
	public static int kontrolaCidel() {
		for (int i = 0; i < pocet_minci.length; i++) {
			if (pocet_minci[i] < 3){
				if(zbyva!=0){ 
					vraceniPenezKontrola();
				}
				return 11;
			}
		}		
		if (vytisky == 0) {
			if(zbyva!=0){				
				vraceniPenezKontrola();
			}
			return 11;				
		}
		if (!napajeni){
			if(zbyva!=0){				
				vraceniPenezKontrola();
			}
			return 11;
		}
		return 1;		
	}
	
	/**
	 * Provadi kontrolu minci a zbyvajicich vytisku automatu.
	 */
	public static int kontrolaAutomatu() {
		if (vytisky == 0) {
			System.out.println("Nedostatek papiru a toneru v automatu.");
			return 2;
		}
		
		for (int i = 0; i < pocet_minci.length; i++) {
			if (pocet_minci[i] < 3) {
				System.out.println("Nedostatek minci v automatu.");
				return 2;
			}
		}
		
		System.out.println("Automat je v provozu.");
		return 1;
	}

	/**
	 * Doplni mince, nebo papir a toner na tisk jizdenky v pripade jejich nedostatku.
	 */
	public static int doplneni() {
		System.out.println("Automat mimo provoz. Prosim doplnte mince, papir a toner.");		
		while (true) {
			System.out.println("Pro doplneni stisknete 'd' a potvrdte stiskem klavesy 'Enter'. ");
			String reakce = sc.nextLine();			
			if (reakce.equalsIgnoreCase("d")) {
				System.out.println("Probiha doplneni minci...\n");
				for (int i = 0; i < pocet_minci.length; i++) {
					pocet_minci[i] = (500 / hodnota_minci[i]);
				}
				System.out.println("Probiha doplneni papiru a toneru...\n");
				vytisky = 4;
				break;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		
		if(kontrolaCidel() == 11){ 
			return 11;			
		}
		
		return 0;
	}
	
	/**
	 * Zde si klient vybere druh jizdneho, bud plnocenne, nebo zlevnene.
	 */
	public static int vyberJizdenku() {
		while (true) {
			if(kontrolaCidel() == 13) return 13;
			
			System.out.println("\nPro plnocenne jizdne stisknete 'p', pro zlevnene stisknete 'z' a potvrdte stiskem klavesy 'Enter'.");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("p")) {
				return 3;
			} else if (reakce.equalsIgnoreCase("z")) {
				return 4;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
	}
	
	/**
	 * Zde si klient vybere ze 4 typu plnocenneho jizdneho, podle toho se nastavi globalni promenna index.
	 * Klient muze take celou operaci stornovat.
	 */
	public static int plnocenneJizdne() {
		while (true) {
			if(kontrolaCidel() == 11) return 11;
			System.out.println("\nPro 30 minutovou jizdenku stisknete '1',");
			System.out.println("Pro 60 minutovou jizdenku stisknete '2',");
			System.out.println("Pro 180 minutovou jizdenku stisknete '3',");
			System.out.println("Pro 24 hodinovou jizdenku stisknete '4' a potvrïte stiskem klávesnice 'Enter'.");
			System.out.println("Pro stornovani objednavky stisknete 's' a potvrïte stiskem klávesnice 'Enter'.");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("1")) {
				index = 0;
				break;
			} else if (reakce.equalsIgnoreCase("2")) {
				index = 1;
				break;
			} else if (reakce.equalsIgnoreCase("3")) {
				index = 2;
				break;
			} else if (reakce.equalsIgnoreCase("4")) {
				index = 3;
				break;
			} else if (reakce.equalsIgnoreCase("s")) {
				System.out.println("Probìhlo storno objednávky.");
				return 6;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 5;
	}
	
	/**
	 * Zde si klient vybere ze 4 typu zlevneneho jizdneho, podle toho se nastavi globalni promenna index.
	 * Klient muze take celou operaci stornovat.
	 */
	public static int zlevneneJizdne() {
		while (true) {
			if(kontrolaCidel() == 11) return 11;
			System.out.println("\nPro 30 minutovou jizdenku stisknete '1',");
			System.out.println("Pro 60 minutovou jizdenku stisknete '2',");
			System.out.println("Pro 180 minutovou jizdenku stisknete '3',");
			System.out.println("Pro 24 hodinovou jizdenku stisknete '4' a potvrïte stiskem klávesnice 'Enter'.");
			System.out.println("Pro stornovani objednavky stisknete 's' a potvrïte stiskem klávesnice 'Enter'.");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("1")) {
				index = 4;
				break;
			} else if (reakce.equalsIgnoreCase("2")) {
				index = 5;
				break;
			} else if (reakce.equalsIgnoreCase("3")) {
				index = 6;
				break;
			} else if (reakce.equalsIgnoreCase("4")) {
				index = 7;
				break;
			} else if (reakce.equalsIgnoreCase("s")) {
				System.out.println("Probìhlo storno objednávky.");
				return 6;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 5;
	}
	
	/**
	 * Metoda pro pripad stornovani transakce. Dojde k pripadnemu vraceni jiz vhozenych minci.
	 */
	public static void stornoObjednavky(){
		System.out.println("Objednavka byla stornovana...");
		for (int i = 0; i < vstupZ.length; i++) {
			if (vstupZ[i] != 0)
				System.out.println("Vraceno " + vstupZ[i] + " x " + hodnota_minci[i] + " kc.");
		}
	}	
	
	/**
	 * Metoda slouzi k vypisu ceny zvolene jizdenky.
	 */
	public static int tiskCeny() {
		if(kontrolaCidel() == 11) return 11;
		System.out.println("\nCena jizdenky je " + ceny_jizdneho[index]	+ " kc.");
		return 7;
	}
	
	/**
	 * Zde probiha zpracovani vhazovanych minci klientem. Po vlozeni pozadovane, nebo vetsi castky je jizdenka zaplacena.
	 * Klient take muze behem vhazovani minci celou operaci stornovat, v takovem pripade jsou mu nasledne jiz vhozene mince vraceny
	 */
	public static int vhozeniMinci() {
		zbyva = ceny_jizdneho[index];
		vhazovaniPovoleno = true;
		System.out.println("Napiste hodnotu vhozene mince.");
		while (zbyva > 0) {			
			if(kontrolaCidel() == 11){
				vhazovaniPovoleno = false;
				return 11;
			} 
			System.out.println("Zbyva vhodit " + zbyva + " kc. Pro stornovani objednavky stisknete 's' a potvrïte stiskem klávesnice 'Enter'.");
			String vhozeno = sc.nextLine();
			if (vhozeno.equalsIgnoreCase("1")) {
				vstupZ[0]++;
				zbyva -= 1;
			} else if (vhozeno.equalsIgnoreCase("2")) {
				vstupZ[1]++;
				zbyva -= 2;
			} else if (vhozeno.equalsIgnoreCase("5")) {
				vstupZ[2]++;
				zbyva -= 5;
			} else if (vhozeno.equalsIgnoreCase("10")) {
				vstupZ[3]++;
				zbyva -= 10;
			} else if (vhozeno.equalsIgnoreCase("20")) {
				vstupZ[4]++;
				zbyva -= 20;
			} else if (vhozeno.equalsIgnoreCase("50")) {
				vstupZ[5]++;
				zbyva -= 50;
			} else if (vhozeno.equalsIgnoreCase("s")) {
				vhazovaniPovoleno = false;
				return 6;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		vhazovaniPovoleno = false;
		System.out.println("Jizdenka zaplacena, pockejte prosim na jeji vytisteni...");
		return 8;
	}
	
	/**
	 * Prijeti vhozenych minci do hlavniho zasobniku automatu ze vstupniho zasobniku.
	 */
	public static int prijetiMinci() {
		for (int i = 0; i < vstupZ.length; i++) {
			pocet_minci[i] += vstupZ[i];
			vstupZ[i] = 0;
		}
		if (zbyva != 0) {
			zbyva = (zbyva * -1);
			return 9;
		} else {
			return 10;
		}
	}
	
	/**
	 * Vraci pripadny preplatek od klienta, v pripade ze klient vhodil vetsi castku nez bylo potreba.
	 * Automat se snazi vracet co mozna nejmensi pocet minci.
	 */
	public static int vraceniZbytku() {
		int pom;
		
		for (int i = 5; i >= 0; i--) {
			pom = (zbyva / hodnota_minci[i]);
			if (pom > 0) {
				pocet_minci[i] -= pom;
				vystup[i] = pom;
				zbyva -= (pom * hodnota_minci[i]);
			}
		}

		for (int i = 0; i < vystup.length; i++) {
			if (vystup[i] != 0) {
				System.out.println("Vraceno " + vystup[i] + " x " + hodnota_minci[i] + " kc.");
				vystup[i] = 0;
			}
		}

		return 10;
	}
	
	/**
	 * Vytisteni vybrane a jiz zaplacene jizdenky s datem a casem vyprseni jeji platnosti.
	 */
	public static int tiskJizdenky() {
				
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		System.out.println("\n================================");
		if (index <= 3) {
			System.out.println("=       Plnocenne jizdne       =");
			switch (index) {
				case 0:
					System.out.println("=      Platnost: 30 minut      =");
					if (minute < 30)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour, minute + 30, second);
					
					else if (hour == 23)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour + 1 - 24, minute + 30 - 60, second);
					
					else
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour + 1, minute + 30 - 60,	second);
					break;
				case 1:
					System.out.println("=      Platnost: 60 minut      =");
					if (hour < 23)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour + 1, minute, second);
					
					else
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour + 1 - 24, minute, second);
					break;
				case 2:
					System.out.println("=      Platnost: 180 minut     =");
					if (hour != 23)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour + 3, minute, second);
					
					else
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour + 3 - 24, minute, second);
					break;
				case 3:
					System.out.println("=      Platnost: 24 hodin      =");
					System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour, minute, second);
					break;
			}
			System.out.format("=         Cena: %2d kc          =\n", ceny_jizdneho[index]);
		
		} else {
			
			System.out.println("=        Zlevnene jizdne       =");
			
			switch (index) {
				case 4:
					System.out.println("=      Platnost: 30 minut      =");
					
					if (minute < 30)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour, minute + 30, second);
					
					else if (hour == 23)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour + 1 - 24, minute + 30 - 60, second);
					
					else
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour + 1, minute + 30 - 60,	second);
					break;
				case 5:
					System.out.println("=      Platnost: 60 minut      =");
					if (hour < 23)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour + 1, minute, second);
					
					else
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour + 1 - 24, minute, second);
					break;
				case 6:
					System.out.println("=      Platnost: 180 minut     =");
					if (hour < 21)
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day, month + 1, year, hour + 3, minute, second);
					
					else
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour + 3 - 24, minute, second);
					break;
				case 7:
					System.out.println("=      Platnost: 24 hodin      =");
					System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n", day + 1, month + 1, year, hour, minute, second);
					break;
			}
			System.out.format("=         Cena: %2d kc          =\n", ceny_jizdneho[index]);
		}
		System.out.println("================================\n");
		if(kontrolaCidel() == 11) return 11;
		vytisky--;
		return 12;
	}
	
	/**
	 * Hlavni metoda, ktera spousti konecny automat.
	 * Prepinani ve switchi predstavuje prechody mezi stavy.
	 * @param args
	 */
	public static void main(String[] args) {
		int vstup = 0;
		
		while(true){
			switch (vstup) {
				case 0:	vstup = kontrolaAutomatu();
						break;
				case 1:	vstup = vyberJizdenku();
						break;
				case 2:	vstup = doplneni();
						break;
				case 3:	vstup = plnocenneJizdne();
						break;
				case 4: vstup = zlevneneJizdne();
						break;
				case 5: vstup = tiskCeny();
						break;
				case 6: stornoObjednavky();
						vstup = kontrolaAutomatu();
						break;
				case 7: vstup = vhozeniMinci();
						break;
				case 8:	vstup = prijetiMinci();
						break;
				case 9: vstup = vraceniZbytku();
						break;
				case 10:vstup = tiskJizdenky();
						break;
				case 11:System.out.println("Porucha cidel.");
						ukonceniAutomatu();
						break;
				case 12:vstup = kontrolaAutomatu();
						break;
				default:System.out.println("Systemova porucha.");
						ukonceniAutomatu();
			}
		}
	}
}

