import java.util.*;

/**
 * ��zen� prodejn�ho automatu na MHD j�zdenky
 * @author Miroslav Havl��ek, Patrik Jaro�
 */

public class TI_MHD_Automat {

	static Scanner sc = new Scanner(System.in);
	
	/* Zaklopka na vhazovani minci. */
	static boolean vhazovaniPovoleno = false;
	
	/* Napajeni. */
	static boolean napajeni = true;
	
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
	static int vstupZ[] = { 0, 0, 0, 0, 0, 0 };
	
	/* V�stupn� z�sobn�k minc�. Ud�v� po�et minc� dan� hodnoty ve v�stupn�m z�sobn�ku. */
	static int vystup[] = { 0, 0, 0, 0, 0, 0 };
	
	/* 3 pole ud�vaj�c� �asov� hodnoty pro jednotliv� typy j�zdn�ho v minut�ch, hodin�ch a ve dnech. */
	static int platnost_min[] = { 30, 0, 0, 0 };
	static int platnost_hod[] = { 0, 1, 3, 0 };
	static int platnost_dny[] = { 0, 0, 0, 1 };
	
	/* Promenna uchovavajici pocet zbyvajicich vytisku jizdenek. */
	static int vytisky = 4;
	
	/**
	 * @index uchov�v� index vybran� j�zdenky. (0 a� 7)
	 * @zbyva uchov�v� hodnotu kter� zb�v� do zaplacen� (kladn�), nebo kterou je t�eba po transakci vr�tit (z�porn�)
	 */
	static int index, zbyva;
	
	public static void ukonceniAutomatu(){
		System.out.println("Automat byl z duvodu poruchy vypnut.");
		System.exit(0);
	}
	
	public static void vraceniPenezKontrola() {
		System.out.println("Automat mimo provoz, pockejte na vraceni vhozenych minci...");
		for (int i = 0; i < vstupZ.length; i++) {
			if (vstupZ[i] != 0)
				System.out.println("Vraceno " + vstupZ[i] + " x " + hodnota_minci[i] + " kc.");
		}
	}
	
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
	 * Stav KA - ��slo 1
	 * Prov�d� kontrolu minc� a zb�vaj�c�ch v�t�sk� automatu.
	 * Pokud je n��eho nedostatek, metoda p�ejde do stavu (�. 2 nebo 3), ve kter�m je mo�n� danou v�c doplnit, nebo automat vypnout.
	 * Pokud je v�e v po��dku, metoda p�ejde do stavu �. 4, v�b�ru j�zdenek.
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
	 * Stav DM - ��slo 2
	 * V p��pad� nedostatku minc� v automatu lze mince doplnit, nebo automat vypnout.
	 * Pokud dojde k dopln�n� minc�, automat se vr�t� do stavu �. 1.
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
	 * Stav VJ - ��slo 4
	 * V tomto stavu si klient zvol� j�zdn� (plnocenn� nebo zlevn�n�). Podle toho automat p�ejde do n�sleduj�c�ho stavu.
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
	 * Stav PJ - ��slo 5
	 * V tomto stavu si klient vybere ze 4 typ� plnocenn�ho j�zdn�ho, podle toho se nastav� glob�ln� prom�nn� a automat p�ejde do dal��ho stavu.
	 * V tomto stavu m��e klient tak� celou operaci stornovat.
	 */
	public static int plnocenneJizdne() {
		while (true) {
			if(kontrolaCidel() == 11) return 11;
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
				return 6;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 5;
	}
	
	/**
	 * Stav ZJ - ��slo 6
	 * V tomto stavu si klient vybere ze 4 typ� zlevn�n�ho j�zdn�ho, podle toho se nastav� glob�ln� prom�nn� a automat p�ejde do dal��ho stavu.
	 * V tomto stavu m��e klient tak� celou operaci stornovat.
	 */
	public static int zlevneneJizdne() {
		while (true) {
			if(kontrolaCidel() == 11) return 11;
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
				return 6;
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
			}
		}
		return 5;
	}
	
	
	public static void stornoObjednavky(){
		System.out.println("Objednavka byla stornovana...");
		for (int i = 0; i < vstupZ.length; i++) {
			if (vstupZ[i] != 0)
				System.out.println("Vraceno " + vstupZ[i] + " x " + hodnota_minci[i] + " kc.");
		}
	}	
	
	/**
	 * Stav TC - ��slo 7
	 * V tomto stavu prob�hne v�pis ceny zvolen�ho j�zdn�ho, n�sledn� automat p�ejde do dal��ho stavu.
	 */
	public static int tiskCeny() {
		if(kontrolaCidel() == 11) return 11;
		System.out.println("\nCena jizdenky je " + ceny_jizdneho[index]	+ " kc.");
		return 7;
	}
	
	/**
	 * Stav VM - ��slo 8
	 * Zde prob�h� zpracov�n� vhazovan�ch minc� klientem, po vlo�en� po�adovan�, nebo v�t�� ��stky automat p�ejde do dal��ho stavu.
	 * Klient m��e b�hem vhazov�n� minc� celou operaci stornovat, v takov�m p��pad� automat p�ejde do stavu vr�cen� vhozen�ch minc�.
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
			System.out.println("Zbyva vhodit " + zbyva + " kc. Pro stornovani objednavky stisknete 's' a potvr�te stiskem kl�vesnice 'Enter'.");
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
	 * Stav PM - ��slo 11
	 * V tomto stavu doch�z� k p�ijet� vhozen�ch minc� do hlavn�ho z�sobn�ku automatu a vypr�zdn�n� vstupn�ho z�sobn�ku.
	 * V p��pad� �e klientovi automat je�t� n�co mus� vr�tit, p�ejde do dal��ho stavu. V opa�n�m p��pad� p�ejde do stavu kontroly.
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

		return 10;
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
				default:System.out.println("System error.");
						ukonceniAutomatu();
			}
		}
	}
}

