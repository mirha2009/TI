
import java.util.Calendar;
import java.util.Scanner;

/**
 * ��zen� prodejn�ho automatu na MHD j�zdenky
 * @author Miroslav Havl��ek, Patrik Jaro�
 */

public class TI_MHD_Automat {

	static Scanner sc = new Scanner(System.in);
	
	/* Hodnoty v�ech minc� v K�. */
	static int hodnota_minci[] = { 1, 2, 5, 10, 20, 50 };
	
	/* Hlavn� z�sobn�k minc�. Ka�d� index ud�v� po�et minc� dan� hodnoty. */
	static int pocet_minci[] = { 500, 250, 100, 50, 25, 10 };
	
	/**
	 * Ceny j�zdn�ho
	 * index 0 a� 3: ceny plnocenn�ch j�zdenek (30 minut, 60 minut, 180 minut a 24 hodin)
	 * index 4 a� 7: ceny zlevn�n�ch j�zdenek (ve stejn�m po�ad�)
	 */
	static int ceny_jizdneho[] = { 16, 20, 34, 60, 8, 10, 17, 30 };
	
	/* Vstupn� z�sobn�k minc�. Ud�v� po�et minc� dan� hodnoty ve vstupn�m z�sobn�ku. */
	static int vstup[] = { 0, 0, 0, 0, 0, 0 };
	
	/* V�stupn� z�sobn�k minc�. Ud�v� po�et minc� dan� hodnoty ve v�stupn�m z�sobn�ku. */
	static int vystup[] = { 0, 0, 0, 0, 0, 0 };
	
	/* 3 pole ud�vaj�c� �asov� hodnoty pro jednotliv� typy j�zdn�ho v minut�ch, hodin�ch a ve dnech. */
	static int platnost_min[] = { 30, 0, 0, 0 };
	static int platnost_hod[] = { 0, 1, 3, 0 };
	static int platnost_dny[] = { 0, 0, 0, 1 };
	
	/* Prom�nn� ud�vaj�c� po�et zb�vaj�c�ch v�tisk� j�zdenek. */
	static int vytisky = 4;
	
	/**
	 * @index uchov�v� index vybran� j�zdenky. (0 a� 7)
	 * @zbyva uchov�v� hodnotu kter� zb�v� do zaplacen� (kladn�), nebo kterou je t�eba po transakci vr�tit (z�porn�)
	 */
	static int index, zbyva;
	
	
	/**
	 * Stav KA - ��slo 1
	 * Prov�d� kontrolu minc� a zb�vaj�c�ch v�t�sk� automatu.
	 * Pokud je n��eho nedostatek, metoda p�ejde do stavu (�. 2 nebo 3), ve kter�m je mo�n� danou v�c doplnit, nebo automat vypnout.
	 * Pokud je v�e v po��dku, metoda p�ejde do stavu �. 4, v�b�ru j�zdenek.
	 */
	public static int kontrolaAutomatu() {
		//System.out.println("Probiha kontrola minci...");
		for (int i = 0; i < pocet_minci.length; i++) {
			if (pocet_minci[i] < 3) {
				System.out.println("Nedostatek minci v automatu.");
				return 2;
			}
		}
		
		//System.out.println("Probiha kontrola stavu papiru a toneru...");
		if (vytisky == 0) {
			System.out.println("Nedostatek papiru a toneru v automatu.");
			return 3;
		}
		
		System.out.println("Automat je v provozu.");
		return 4;
	}

	/**
	 * Stav DM - ��slo 2
	 * V p��pad� nedostatku minc� v automatu lze mince doplnit, nebo automat vypnout.
	 * Pokud dojde k dopln�n� minc�, automat se vr�t� do stavu �. 1.
	 */
	public static int doplneniMinci() {
		System.out.println("Automat mimo provoz. Prosim doplnte mince.");
		
		while (true) {
			System.out.println("Pro doplneni stisknete 'd', pro vypnuti automatu stisknete 'e' a potvr�te stiskem kl�vesnice 'Enter'. ");
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
	 * Stav DV - ��slo 3
	 * V p��pad� nedostatku materi�lu na tisk j�zdenek v automatu, lze pap�r a toner doplnit, nebo automat vypnout.
	 * Pokud dojde k dopln�n� materi�lu, automat se vr�t� do stavu �. 1.
	 */
	public static int doplneniVytisku() {
		System.out.println("Automat mimo provoz. Prosim doplnte papir a toner.");
		
		while (true) {
			System.out.println("Pro doplneni stisknete 'd', pro vypnuti automatu stisknete 'e' a potvr�te stiskem kl�vesnice 'Enter'. ");
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
	 * Stav VJ - ��slo 4
	 * V tomto stavu si klient zvol� j�zdn� (plnocenn� nebo zlevn�n�). Podle toho automat p�ejde do n�sleduj�c�ho stavu.
	 */
	public static int vyberJizdenku() {
		while (true) {
			System.out.println("\nPro plnocenne jizdne stisknete 'p', pro zlevnene stisknete 'z' a potvr�te stiskem kl�vesnice 'Enter'.");
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
	 * Stav PJ - ��slo 5
	 * V tomto stavu si klient vybere ze 4 typ� plnocenn�ho j�zdn�ho, podle toho se nastav� glob�ln� prom�nn� a automat p�ejde do dal��ho stavu.
	 * V tomto stavu m��e klient tak� celou operaci stornovat.
	 */
	public static int plnocenneJizdne() {
		while (true) {
			System.out.println("\nPro 30 minutovou jizdenku stisknete '1',");
			System.out.println("Pro 60 minutovou jizdenku stisknete '2',");
			System.out.println("Pro 180 minutovou jizdenku stisknete '3',");
			System.out.println("Pro 24 hodinovou jizdenku stisknete '4' a potvr�te stiskem kl�vesnice 'Enter'.");
			System.out.println("Pro stornovani objednavky stisknete 's' a potvr�te stiskem kl�vesnice 'Enter'.");
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
				System.out.println("Prob�hlo storno objedn�vky.");
				return 1;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 7;
	}
	
	/**
	 * Stav ZJ - ��slo 6
	 * V tomto stavu si klient vybere ze 4 typ� zlevn�n�ho j�zdn�ho, podle toho se nastav� glob�ln� prom�nn� a automat p�ejde do dal��ho stavu.
	 * V tomto stavu m��e klient tak� celou operaci stornovat.
	 */
	public static int zlevneneJizdne() {
		while (true) {
			System.out.println("\nPro 30 minutovou jizdenku stisknete '1',");
			System.out.println("Pro 60 minutovou jizdenku stisknete '2',");
			System.out.println("Pro 180 minutovou jizdenku stisknete '3',");
			System.out.println("Pro 24 hodinovou jizdenku stisknete '4' a potvr�te stiskem kl�vesnice 'Enter'.");
			System.out.println("Pro stornovani objednavky stisknete 's' a potvr�te stiskem kl�vesnice 'Enter'.");
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
				System.out.println("Prob�hlo storno objedn�vky.");
				return 1;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 7;
	}
	
	/**
	 * Stav TC - ��slo 7
	 * V tomto stavu prob�hne v�pis ceny zvolen�ho j�zdn�ho, n�sledn� automat p�ejde do dal��ho stavu.
	 */
	public static int tiskCeny() {
		System.out.println("\nCena jizdenky je " + ceny_jizdneho[index]	+ " kc.");
		return 8;
	}
	
	/**
	 * Stav VM - ��slo 8
	 * Zde prob�h� zpracov�n� vhazovan�ch minc� klientem, po vlo�en� po�adovan�, nebo v�t�� ��stky automat p�ejde do dal��ho stavu.
	 * Klient m��e b�hem vhazov�n� minc� celou operaci stornovat, v takov�m p��pad� automat p�ejde do stavu vr�cen� vhozen�ch minc�.
	 */
	public static int vhozeniMinci() {
		zbyva = ceny_jizdneho[index];
		System.out.println("Napiste hodnotu vhozene mince.");

		while (zbyva > 0) {
			System.out.println("Zbyva vhodit " + zbyva + " kc. Pro stornovani objednavky stisknete 's' a potvr�te stiskem kl�vesnice 'Enter'.");
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
	 * Stav VPS - ��slo 9
	 * Prov�d� vr�cen� minc� v p��pad� stornov�n� objedn�vky. Pot� p�ejde do stavu kontroly.
	 */
	public static int vraceniPenezStorno() {
		System.out.println("Probehlo storno objedn�vky, pockejte na vraceni vhozenych minci...");
		for (int i = 0; i < vstup.length; i++) {
			if (vstup[i] != 0)
				System.out.println("Vraceno " + vstup[i] + " x " + hodnota_minci[i] + " kc.");
		}
		return 1;
	}
	
	/**
	 * Stav TJ - ��slo 10
	 * Tento stav zaji��uje tisk vybran� a ji� zaplacen� j�zdenky s datem a �asem vypr�en� platnosti j�zdenky.
	 * Po vyti�t�n� automat p�ejde do n�sleduj�c�ho stavu.
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
	 * Stav PM - ��slo 11
	 * V tomto stavu doch�z� k p�ijet� vhozen�ch minc� do hlavn�ho z�sobn�ku automatu a vypr�zdn�n� vstupn�ho z�sobn�ku.
	 * V p��pad� �e klientovi automat je�t� n�co mus� vr�tit, p�ejde do dal��ho stavu. V opa�n�m p��pad� p�ejde do stavu kontroly.
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
	 * Stav VZ - ��slo 12
	 * Tento stav zaji��uje vr�cen� p��padn�ho p�ebytku pen�z, v p��pad� �e klient vhodil v�t�� ��stku ne� bylo pot�eba.
	 * Automat se sna�� vracet co mo�n� nejmen�� po�et minc�. Po vr�cen� p�ejde do stavu kontroly.
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

		return 1;
	}
	
	/**
	 * Hlavn� metoda, kter� spou�t� program. Funguje na principu kone�n�ho automatu.
	 * @param args
	 */
	public static void main(String[] args) {
		int stav = 1, novyStav;
		
		while(true){
			switch (stav) {
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
				default:novyStav = kontrolaAutomatu();
						break;
			}			
			stav = novyStav;
		}
	}

}

