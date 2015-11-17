import java.util.*;

public class TI_SP_2015 {

	static Scanner sc = new Scanner(System.in);
	static int vstup[] = new int[6];
	static int vystup[] = new int[6];
	static int pocet_minci[] = { 500, 250, 100, 50, 25, 10 };
	static int hodnota_minci[] = { 1, 2, 5, 10, 20, 50 };
	static int vytisky = 3;
	static int ceny_jizdneho[] = { 16, 20, 34, 60, 8, 10, 17, 30 };

	public static void doplneniMinci() {
		System.out.println("Automat mimo provoz. Prosim doplnte mince");
		while (true) {
			System.out.println("Pro doplneni stisknete 'd', pro vypnuti automatu stisknete 'e' ");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("d")) {
				System.out.println("Probiha doplneni minci...");
				for (int i = 0; i < pocet_minci.length; i++) {
					pocet_minci[i] = (500 / hodnota_minci[i]);
				}
				break;
			} else if (reakce.equalsIgnoreCase("e")) {
				System.out.println("Probiha vypnuti automatu...");
				System.out.println("Automat je vypnut");
				System.exit(0);
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
				continue;
			}
		}
		kontrolaAutomatu();
	}

	public static void doplneniVytisku() {
		System.out.println("Automat mimo provoz. Prosim doplnte papir a toner");
		while (true) {
			System.out
					.println("Pro doplneni stisknete 'd', pro vypnuti automatu stisknete 'e' ");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("d")) {
				System.out.println("Probiha doplneni papiru a toneru...");
				vytisky = 3;
				break;
			} else if (reakce.equalsIgnoreCase("e")) {
				System.out.println("Probiha vypnuti automatu...");
				System.out.println("Automat je vypnut");
				System.exit(0);
			} else {
				System.out.println("Chybny vstup. Zadejte prosim platny vstup.");
				continue;
			}
		}
		kontrolaAutomatu();
	}

	public static void kontrolaAutomatu() {
		System.out.println("Probiha kontrola minci...");
		for (int i = 0; i < pocet_minci.length; i++) {
			if (pocet_minci[i] < 3) {
				System.out.println("Nedostatek minci v automatu.");
				doplneniMinci();
			}
		}
		System.out.println("Probiha kontrola stavu papiru a toneru...");
		if (vytisky == 0) {
			System.out.println("Nedostatek papiru a toneru v automatu.");
			doplneniVytisku();
		}
		System.out.println("Automat je v provozu.");
		vyberJizdenku();
	}

	public static void vyberJizdenku() {

		System.out
				.println("\nPro plnocenne jizdne stisknete 'p' a pro zlevnene stisknete 'z'");
		while (true) {
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("p")) {
				plnocenneJizdne();
				break;
			} else if (reakce.equalsIgnoreCase("z")) {
				zlevneneJizdne();
				break;
			} else {
				System.out
						.println("Chybny vstup. Zadejte prosim platny vstup.");
				vyberJizdenku();
				break;
			}
		}
	}

	public static void plnocenneJizdne() {

		while (true) {
			System.out.println("\nPro 30-ti minutovou jizdenku stisknete '1',");
			System.out.println("Pro 60-ti minutovou jizdenku stisknete '2',");
			System.out.println("Pro 180 minutovou jizdenku stisknete '3',");
			System.out.println("Pro 24 hodinovou jizdenku stisknete '4'.");
			System.out.println("Pro stornovani objednavky stisknete 's'.");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("1")) {
				tiskCeny(0);
				break;
			} else if (reakce.equalsIgnoreCase("2")) {
				tiskCeny(1);
				break;
			} else if (reakce.equalsIgnoreCase("3")) {
				tiskCeny(2);
				break;
			} else if (reakce.equalsIgnoreCase("4")) {
				tiskCeny(3);
				break;
			} else if (reakce.equalsIgnoreCase("s")) {
				kontrolaAutomatu();
			} else {
				System.out
						.println("Chybny vstup. Zadejte prosim platny vstup.");
				continue;
			}
		}
	}

	public static void zlevneneJizdne() {
		while (true) {
			System.out.println("\nPro 30-ti minutovou jizdenku stisknete '1',");
			System.out.println("Pro 60-ti minutovou jizdenku stisknete '2',");
			System.out.println("Pro 180 minutovou jizdenku stisknete '3',");
			System.out.println("Pro 24 hodinovou jizdenku stisknete '4'.");
			System.out.println("Pro stornovani objednavky stisknete 's'.");
			String reakce = sc.nextLine();
			if (reakce.equalsIgnoreCase("1")) {
				tiskCeny(4);
				break;
			} else if (reakce.equalsIgnoreCase("2")) {
				tiskCeny(5);
				break;
			} else if (reakce.equalsIgnoreCase("3")) {
				tiskCeny(6);
				break;
			} else if (reakce.equalsIgnoreCase("4")) {
				tiskCeny(7);
				break;
			} else if (reakce.equalsIgnoreCase("s")) {
				kontrolaAutomatu();
			} else {
				System.out
						.println("Chybny vstup. Zadejte prosim platny vstup.");
				continue;
			}
		}
	}

	public static void tiskCeny(int index) {
		System.out.println("\nCena jizdenky je " + ceny_jizdneho[index]
				+ " kc.");
		vhozeniMinci(index);
	}
	
	/*
	  public static void vhozeniMinci(int index){
	 
		int zbyva = ceny_jizdneho[index];		
		int suma = 0;
		
		while(suma <= ceny_jizdneho[index] ){			
			suma = 0;
			for(int i = 0; i < vstup.length; i++){
				suma = suma + vstup[i]*hodnota_minci[i];
				System.out.println("ef"+vstup[i]*hodnota_minci[i]);
			}
			zbyva = ceny_jizdneho[index] - suma;			
			String vhozeno = sc.nextLine();
			if (vhozeno.equalsIgnoreCase("1")) {
				vstup[0]++;
			} else if (vhozeno.equalsIgnoreCase("2")) {
				vstup[1]++;
			} else if (vhozeno.equalsIgnoreCase("5")) {
				vstup[2]++;
			} else if (vhozeno.equalsIgnoreCase("10")) {
				vstup[3]++;
			} else if (vhozeno.equalsIgnoreCase("20")) {
				vstup[4]++;
			} else if (vhozeno.equalsIgnoreCase("50")) {
				vstup[5]++;
			} else if (vhozeno.equalsIgnoreCase("s")) {
				vraceniPenezStorno();
			} else {
				System.out
						.println("Chybny vstup. Zadejte prosim platny vstup.");
				continue;
			}
			System.out.println("Napiste hodnotu vhozene mince. Zbyva " + zbyva + " d" + suma + " kc \n" +
					"Pro stornovani objednavky stisknete 's'.");
		}
		tiskCeny(index);
		
	}
	*/
	///*
	
	 public static void vhozeniMinci(int index){
	 
		int zbyva = ceny_jizdneho[index];
		while(zbyva > 0 ){
			System.out.println("Napiste hodnotu vhozene mince. Zbyva " + zbyva + " kc \n" +
					"Pro stornovani objednavky stisknete 's'.");
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
				vraceniPenezStorno();
			} else {
				System.out
						.println("Chybny vstup. Zadejte prosim platny vstup.");
				continue;
			}			
		}
		//sc.nextLine();
		tiskCeny(index);
		
	}
	//*/
	public static void vraceniPenezStorno(){
		for(int i = 0; i<vstup.length;i++){
			if(vstup[i]!=0)
				System.out.println("Vraceno " + vstup[i] + " x "+hodnota_minci[i] + " kc");
		}
		kontrolaAutomatu();
	}

	public static void main(String[] args) {
		for (int i = 0; i < hodnota_minci.length; i++) {
			System.out.println("hodnota mince je " + hodnota_minci[i]);
		}
		kontrolaAutomatu();
	}

}
