import java.util.Calendar;
import java.util.Scanner;

public class TI_SP_2015_final {

	static Scanner sc = new Scanner(System.in);
	static int hodnota_minci[] = { 1, 2, 5, 10, 20, 50 };
	static int pocet_minci[] = { 500, 250, 100, 50, 25, 10 };
	static int ceny_jizdneho[] = { 16, 20, 34, 60, 8, 10, 17, 30 };
	static int vstup[] = { 0, 0, 0, 0, 0, 0 };
	static int vystup[] = { 0, 0, 0, 0, 0, 0 };
	static int platnost_min[] = { 30, 0, 0, 0 };
	static int platnost_hod[] = { 0, 1, 3, 0 };
	static int platnost_dny[] = { 0, 0, 0, 1 };
	static int vytisky = 3;
	static int index, zbyva;
	
	
/**
 * KA stav èíslo 1
 */
	public static int kontrolaAutomatu() {
		System.out.println("Probiha kontrola minci...");
		for (int i = 0; i < pocet_minci.length; i++) {
			if (pocet_minci[i] < 3) {
				System.out.println("Nedostatek minci v automatu.");
				return 2;
			}
		}
		
		System.out.println("Probiha kontrola stavu papiru a toneru...");
		if (vytisky == 0) {
			System.out.println("Nedostatek papiru a toneru v automatu.");
			return 3;
		}
		
		System.out.println("Automat je v provozu.");
		return 4;
	}

/**
 * 	DM stav èíslo 2
 */
	public static int doplneniMinci() {
		System.out.println("Automat mimo provoz. Prosim doplnte mince.");
		
		while (true) {
			System.out.println("Pro doplneni stisknete 'd', pro vypnuti automatu stisknete 'e' a potvrïte stiskem klávesnice 'Enter'. ");
			String reakce = sc.nextLine();			
			if (reakce.equalsIgnoreCase("d")) {
				System.out.println("Probiha doplneni minci...\n");
				for (int i = 0; i < pocet_minci.length; i++) {
					pocet_minci[i] = (500 / hodnota_minci[i]);
				}
				break;
			} else if (reakce.equalsIgnoreCase("e")) {
				System.out.println("Probiha vypnuti automatu...");
				System.out.println("Automat je vypnut.");
				System.exit(0);
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		
		return 1;
	}
/**
 * DV stav èíslo 3
 */
	public static int doplneniVytisku() {
		System.out.println("Automat mimo provoz. Prosim doplnte papir a toner.");
		
		while (true) {
			System.out.println("Pro doplneni stisknete 'd', pro vypnuti automatu stisknete 'e' a potvrïte stiskem klávesnice 'Enter'. ");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("d")) {
				System.out.println("Probiha doplneni papiru a toneru...\n");
				vytisky = 3;
				break;
			} else if (reakce.equalsIgnoreCase("e")) {
				System.out.println("Probiha vypnuti automatu...");
				System.out.println("Automat je vypnut.");
				System.exit(0);
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		
		return 1;
	}
/**
 * VJ stav èíslo 4
 */
	public static int vyberJizdenku() {
		while (true) {
			System.out.println("\nPro plnocenne jizdne stisknete 'p', pro zlevnene stisknete 'z' a potvrïte stiskem klávesnice 'Enter'.");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("p")) {
				return 5;
			} else if (reakce.equalsIgnoreCase("z")) {
				return 6;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
	}
/**
 * PJ stav èíslo 5
 */
	public static int plnocenneJizdne() {
		while (true) {
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
				return 1;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 7;
	}
/**
 * ZJ stav èíslo 6
 */
	public static int zlevneneJizdne() {
		while (true) {
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
				return 1;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 7;
	}
/**
 * TC stav èíslo 7
 */
	public static int tiskCeny() {
		System.out.println("\nCena jizdenky je " + ceny_jizdneho[index]	+ " kc.");
		return 8;
	}
/**
 * VM stav èíslo 8
 */
	public static int vhozeniMinci() {
		zbyva = ceny_jizdneho[index];
		System.out.println("Napiste hodnotu vhozene mince.");

		while (zbyva > 0) {
			System.out.println("Zbyva vhodit " + zbyva + " kc. Pro stornovani objednavky stisknete 's' a potvrïte stiskem klávesnice 'Enter'.");
			String vhozeno = sc.nextLine();
			if (vhozeno.equalsIgnoreCase("1")) {
				vstup[0]++;
				zbyva -= 1;
			} else if (vhozeno.equalsIgnoreCase("2")) {
				vstup[1]++;
				zbyva -= 2;
			} else if (vhozeno.equalsIgnoreCase("5")) {
				vstup[2]++;
				zbyva -= 5;
			} else if (vhozeno.equalsIgnoreCase("10")) {
				vstup[3]++;
				zbyva -= 10;
			} else if (vhozeno.equalsIgnoreCase("20")) {
				vstup[4]++;
				zbyva -= 20;
			} else if (vhozeno.equalsIgnoreCase("50")) {
				vstup[5]++;
				zbyva -= 50;
			} else if (vhozeno.equalsIgnoreCase("s")) {
				return 9;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}

		System.out.println("Jizdenka zaplacena, pockejte prosim na jeji vytisteni...");
		return 10;
	}
/**
 * VPS stav èíslo 9
 */
	public static int vraceniPenezStorno() {
		for (int i = 0; i < vstup.length; i++) {
			if (vstup[i] != 0)
				System.out.println("Vraceno " + vstup[i] + " x " + hodnota_minci[i] + " kc.");
		}
		return 1;
	}
/**
 * TJ stav èíslo 10
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
						System.out.printf("= Platnost:%02d/%02d/%02d %02d:%02d:%02d =\n",	day + 1, month + 1, year, hour + 1 - 24, minute + 30 - 60, second);
					
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
		vytisky--;

		return 11;
	}
/**
 * PM stav èíslo 11
 */
	public static int prijetiMinci() {
		for (int i = 0; i < vstup.length; i++) {
			pocet_minci[i] += vstup[i];
			vstup[i] = 0;
		}
		if (zbyva != 0) {
			zbyva = (zbyva * -1);
			return 12;
		} else {
			return 1;
		}
	}
/**
 * VZ stav èíslo 12
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
				System.out.println("Vraceno " + vystup[i] + " x "
						+ hodnota_minci[i] + " kc.");
				vystup[i] = 0;
			}
		}

		return 1;
	}
/**
 * 
 * @param args
 */
	public static void main(String[] args) {
		int stav = 1, novyStav;
		
		while(true){
			switch (stav) {
				case 1:	novyStav = kontrolaAutomatu();
						break;
				case 2:	novyStav = doplneniMinci();
						break;
				case 3: novyStav = doplneniVytisku();
						break;
				case 4: novyStav = vyberJizdenku();
						break;
				case 5:	novyStav = plnocenneJizdne();
						break;
				case 6: novyStav = zlevneneJizdne();
						break;
				case 7: novyStav = tiskCeny();
						break;
				case 8: novyStav = vhozeniMinci();
						break;
				case 9: novyStav = vraceniPenezStorno();
						break;
				case 10:novyStav = tiskJizdenky();
						break;
				case 11:novyStav = prijetiMinci();
						break;
				case 12:novyStav = vraceniZbytku();
						break;
				default:novyStav = 0; // nutne, jinak ma problem :(
						System.exit(0);
			}			
			stav = novyStav;
		}
		
	}

}

