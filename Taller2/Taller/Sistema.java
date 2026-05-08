package Taller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Sistema {
	static Scanner sc = new Scanner(System.in);
	static String nombre;
	static ArrayList<String> medallas = new ArrayList<String>();
	static ArrayList<Pokemon> capturados = new ArrayList<Pokemon>();
	static ArrayList<Pokemon> equipoActual = new ArrayList<Pokemon>();
	static ArrayList<String> habitats = new ArrayList<String>();
	static ArrayList<Pokemon> pokedex = new ArrayList<Pokemon>();
	static ArrayList<Gimnasios> gyms = new ArrayList<Gimnasios>();
	static ArrayList<AltoMando> altoMando = new ArrayList<AltoMando>();
	static Pokemon pokeActual;

	public static void main(String[] args) {
		// Bastián Felipe Perines Flores
		// 22.386.978-5
		// ICCI
		cargarHabitats(habitats);
		cargarPokemons(pokedex);
		cargarGyms();
		cargarAltoMando();
		int opcion = menuPrincipal();
		switch (opcion) {
		case (1):
			cargarPartida(pokedex, gyms);
			pokeActual = equipoActual.get(0);
			break;
		case (2):
			System.out.print("Ingrese Apodo:\r\n> ");
			nombre = sc.nextLine();
			System.out.println("Bienvenido " + nombre + "!");
			break;
		case (3):
			System.out.println("bye!");
			System.exit(0);
		}
		int accion;
		do {
			accion = menuPartida();
			switch (accion) {
			case (1):
				System.out.println("Equipo Actual:");
				if (equipoActual.size() == 0) {
					System.out.println("no tienes ningun pokemon en el equipo!");
					break;
				}
				for (int i = 0; i < equipoActual.size(); i++) {
					System.out.println((i + 1) + ") " + equipoActual.get(i).getNombre() + "|"
							+ equipoActual.get(i).getTipo() + "|Stats totales: " + equipoActual.get(i).statsTotales());
				}
				break;
			case (2):
				break;
			case (3):
				if (capturados.size() == 0 || equipoActual.size() < 6) {
					System.out.println("primero debes completar tu equipo y tener algun pokemon en el pc!");
					break;
				}
				do {
					for (int i = 0; i < capturados.size(); i++) {
						System.out.println((i + 1) + ") " + capturados.get(i).getNombre() + "|"
								+ capturados.get(i).getTipo() + "|Stats totales: " + capturados.get(i).statsTotales());
					}
					accion = menuPC();
					switch (accion) {
					case (1):
						int indice1 = menuCambio();
						menuCambio(indice1);
						break;
					case (2):
						break;
					}
				} while (accion != 2);
				break;
			case (4):
				if (menuSiguienteGym()) {
					break;
				}
				if (perdiste()) {
					System.out.println("todos tus pokes estan debilitados!, no puedes combatir asi!");
					break;
				}
				Gimnasios siguienteGymSinDerrotar = retarGym();
				if (siguienteGymSinDerrotar == null) {
					System.out.println("Ya derrotaste a todos los gimnasios!");
				} else {
					combatirGym(siguienteGymSinDerrotar);
				}
				break;
			case (5):
				if (perdiste()) {
					System.out.println("todos tus pokes estan debilitados!, no puedes combatir asi!");
					break;
				}
				if (!todosLosGymsDerrotados()) {
					System.out
							.println("Para desafiar al Alto Mando debes haber derrotado a todos los gimnasios antes!");
					break;
				}
				boolean perdiste = false;
				for (AltoMando AM : altoMando) {
					combatirAltoMando(AM);
					for (Pokemon poke : equipoActual) {
						if (!poke.isDebilitado()) {
							perdiste = true;
							break;
						}
					}
					if (perdiste) {
						break;
					}
				}
				for (Pokemon poke : equipoActual) {
					if (!poke.isDebilitado()) {
						System.out.println("Felicidades! eres el nuevo campeon! (solo decorativo :P)");
						break;
					}
				}
				break;
			case (6):
				for (Pokemon poke : pokedex) {
					poke.curarPokemon();
				}
				System.out.println("todos los pokemones se recuperaron!");
				break;
			case (7):
				guardarPartida();
				break;
			case (8):
				guardarPartida();
				System.out.println("bye!");
				System.exit(0);
				break;
			}
		} while (accion != 8);
	}

	public static int menuPrincipal() {
		boolean entradaValida = true;

		do {
			try {
				System.out.print("1) Continuar.\r\n" + "2) Nueva Partida.\r\n" + "3) Salir.\r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < 4) {
					entradaValida = false;
					return opcion;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return 3;
	}

	public static int menuPartida() {
		boolean entradaValida = true;
		do {
			try {
				System.out.print(nombre + ", que deseas hacer?\r\n" + "\r\n" + "1) Revisar equipo.\r\n"
						+ "2) Salir a capturar.\r\n" + "3) Acceso al PC (cambiar Pokémon del equipo).\r\n"
						+ "4) Retar un gimnasio.\r\n" + "5) Desafío al Alto Mando.\r\n" + "6) Curar Pokémon.\r\n"
						+ "7) Guardar.\r\n" + "8) Guardar y Salir. \r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < 9) {
					entradaValida = false;
					return opcion;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return 0;
	}

	public static int menuPC() {
		boolean entradaValida = true;
		do {
			try {
				System.out
						.print(nombre + ", que deseas hacer?\r\n" + "1) Cambiar Pokemon.\r\n" + "2) Salir." + "\r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < 3) {
					entradaValida = false;
					return opcion;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return 0;
	}

	public static int menuCambio() {
		boolean entradaValida = true;
		do {
			try {
				System.out.print("cual deseas cambiar?" + "\r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < (capturados.size() + 1)) {
					entradaValida = false;
					return opcion - 1;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return 0;
	}

	public static int menuCambio(int indiceCambio) {
		boolean entradaValida = true;
		for (int j = 0; j < equipoActual.size(); j++) {
			System.out.println((j + 1) + ") " + equipoActual.get(j).getNombre() + "|" + equipoActual.get(j).getTipo()
					+ "|Stats totales: " + equipoActual.get(j).statsTotales());
		}
		do {
			try {
				System.out.print("con cual deseas cambiarlo?" + "\r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < 7) {
					entradaValida = false;
					cambioPokemons(indiceCambio, (opcion - 1));
					return opcion - 1;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return 0;
	}

	public static void cambioPokemons(int indiceCapturado, int indiceEquipo) {
		Pokemon aux = equipoActual.get(indiceEquipo);
		equipoActual.set(indiceEquipo, capturados.get(indiceCapturado));
		capturados.set(indiceCapturado, aux);
	}

	public static void cargarPartida(ArrayList<Pokemon> pokedex, ArrayList<Gimnasios> gyms) {
		String linea;
		boolean primeraLinea = false;
		File registros = new File("Registros.txt");
		try (Scanner leerArchivo = new Scanner(registros)) {
			if (!leerArchivo.hasNextLine()) {
				System.out.println("no se encontro una partida anterior, inicie una nueva.");
				return;
			}
			do {
				linea = leerArchivo.nextLine();
				if (primeraLinea) {
					String[] partes = linea.split(";");
					if (equipoActual.size() < 6) {
						for (Pokemon poke : pokedex) {
							if (partes[0].equals(poke.getNombre())) {
								if (poke.isCapturado()) {
									break;
								}
								equipoActual.add(poke);
								poke.capturado();
								if (partes[1].equals("Debilitado")) {
									poke.debilitarPokemon();
								} else {
									poke.curarPokemon();
								}
								break;
							}
						}
					} else {
						for (Pokemon poke : pokedex) {
							if (partes[0].equals(poke.getNombre())) {
								if (poke.isCapturado()) {
									break;
								}
								capturados.add(poke);
								poke.capturado();
								if (partes[1].equals("Debilitado")) {
									poke.debilitarPokemon();
								} else {
									poke.curarPokemon();
								}
								break;
							}
						}
					}

				} else {
					String[] partes = linea.split(";");
					nombre = partes[0];
					if (partes.length < 2) {
						primeraLinea = true;
						continue;
					}
					for (int i = 0; i < (partes.length - 1); i++) {
						for (Gimnasios gym : gyms) {
							if (partes[1 + i].equals(gym.getLider())) {
								gym.derrotado();
								break;
							}
						}
					}
					primeraLinea = true;
				}
			} while (leerArchivo.hasNextLine());
			System.out.println("la partida se cargo correctamente!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
		}
	}

	public static void guardarPartida() {
		try (BufferedWriter sobreEscribirRegistros = new BufferedWriter(new FileWriter("Registros.txt"))) {
			String linea;
			int i = 0;
			linea = nombre;
			for (Gimnasios gym : gyms) {
				if (gym.isDerrotado()) {
					linea += ";" + gym.getLider();
				}
			}
			sobreEscribirRegistros.write(linea);
			sobreEscribirRegistros.newLine();
			for (Pokemon poke : equipoActual) {

				linea = poke.getNombre();
				if (poke.isDebilitado()) {
					linea += ";Debilitado";
				} else {
					linea += ";Vivo";
				}
				sobreEscribirRegistros.write(linea);
				i++;
				if (i < 6) {
					sobreEscribirRegistros.newLine();
				}
			}
			for (int j = 0; j < capturados.size(); j++) {
				if (j < capturados.size()) {
					sobreEscribirRegistros.newLine();
				}
				linea = capturados.get(j).getNombre();
				if (capturados.get(j).isDebilitado()) {
					linea += ";Debilitado";
				} else {
					linea += ";Vivo";
				}
				sobreEscribirRegistros.write(linea);
			}
			System.out.println("la partida se guardo correctamente!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro donde guardar la partida :(");
			return;
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}

	public static void cargarHabitats(ArrayList<String> lista) {
		String linea;
		File archivo = new File("Habitats.txt");
		try (Scanner leerHabitats = new Scanner(archivo)) {
			while (leerHabitats.hasNextLine()) {
				linea = leerHabitats.nextLine();
				lista.add(linea);
			}
			System.out.println("los habitats se cargaron correctamente!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
			return;
		}
	}

	public static void cargarPokemons(ArrayList<Pokemon> lista) {
		String linea;
		File archivo = new File("Pokedex.txt");
		try (Scanner leerPokemons = new Scanner(archivo)) {
			while (leerPokemons.hasNextLine()) {
				// pokemon;habitat;porcentajeAparicion;vida;ataque;defensa;ataqueEspecial;defensaEspecial;velocidad;Tipo
				linea = leerPokemons.nextLine();
				String[] partes = linea.split(";");
				String nombre = partes[0];
				String habitat = partes[1];
				double porcentajeAparicion = Double.parseDouble(partes[2]);
				int vida = Integer.parseInt(partes[3]);
				int ataque = Integer.parseInt(partes[4]);
				int defensa = Integer.parseInt(partes[5]);
				int ataqueEspecial = Integer.parseInt(partes[6]);
				int defensaEspecial = Integer.parseInt(partes[7]);
				int velocidad = Integer.parseInt(partes[8]);
				String tipo = partes[9];
				lista.add(new Pokemon(nombre, habitat, porcentajeAparicion, vida, ataque, defensa, ataqueEspecial,
						defensaEspecial, velocidad, tipo));
			}
			System.out.println("se cargo correctamente la pokedex!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
			return;
		}
	}

	public static void cargarGyms() {
		String linea;
		File archivo = new File("Gimnasios.txt");
		try (Scanner leerGyms = new Scanner(archivo)) {
			while (leerGyms.hasNextLine()) {
				// N°Gimnasio;Lider;Estado;cantPokemons;Pokemons....
				linea = leerGyms.nextLine();
				String[] partes = linea.split(";");
				int numGym = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				int cantPokemons = Integer.parseInt(partes[3]);
				Gimnasios gym = new Gimnasios(numGym, nombre, cantPokemons);
				for (int i = 0; i < cantPokemons; i++) {
					for (Pokemon poke : pokedex) {
						if (partes[4 + i].equals(poke.getNombre())) {
							gym.añadirPokemon(new Pokemon(poke));
							break;
						}
					}
				}
				gyms.add(gym);
			}
			System.out.println("se cargaron correctamente los gimnasios!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
			return;
		}
	}

	public static Gimnasios retarGym() {
		for (Gimnasios gym : gyms) {
			if (!gym.isDerrotado()) {
				return gym;
			}
		}
		return null;
	}

	public static void cargarAltoMando() {
		String linea;
		File archivo = new File("Alto Mando.txt");
		try (Scanner leerAltoMando = new Scanner(archivo)) {
			while (leerAltoMando.hasNextLine()) {
				// N°AltoMando;Nombre;Pokemons....
				linea = leerAltoMando.nextLine();
				String[] partes = linea.split(";");
				int numAltoMando = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				AltoMando AM = new AltoMando(numAltoMando, nombre);
				for (int i = 0; i < 6; i++) {
					for (Pokemon poke : pokedex) {
						if (partes[2 + i].equals(poke.getNombre())) {
							AM.añadirPokemon(new Pokemon(poke));
							break;
						}
					}
				}
				altoMando.add(AM);
			}
			System.out.println("se cargaron correctamente los altos mandos!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
			return;
		}
	}

	public static boolean todosLosGymsDerrotados() {
		for (Gimnasios gym : gyms) {
			if (!gym.isDerrotado()) {
				return false;
			}
		}
		return true;
	}

	public static void combatirAltoMando(AltoMando enemigo) {
		if (pokeActual.isDebilitado()) {
			for (Pokemon poke : equipoActual) {
				if (!poke.isDebilitado()) {
					pokeActual = poke;
					break;
				}
			}
		}
		int accion;
		do { // menuCambio() returna indice del pokemon que quieres poner en combate
			Pokemon pokeEnemigo = enemigo.getSiguientePokemon();
			System.out.println(nombre + " saca a " + pokeActual.getNombre() + "!");
			System.out.println(enemigo.getNombre() + " saca a " + pokeEnemigo.getNombre() + "!");
			switch (accion = menuCombate()) {
			case 1:
				peleaPokes(pokeEnemigo, pokeActual);
				if (pokeActual.isDebilitado() && !perdiste()) {
					pokeActual = menuCambio(pokeActual);
				} else if (perdiste()) {
					return;
				}
				break;
			case 2:
				menuCambio(pokeActual);
				break;
			case 3:
				System.out.println("Vuelve a intentarlo!");
				return;
			}
		} while ((enemigo.getSiguientePokemon() != null) || perdiste());
		if (!perdiste()) {
			System.out.println("Venciste a " + enemigo.getNombre() + "!");
		} else {
			System.out.println("Te has quedado sin pokemons en el equipo!\nIntentalo de nuevo :(");
		}
	}

	public static void combatirGym(Gimnasios enemigo) {
		if (pokeActual.isDebilitado()) {
			for (Pokemon poke : equipoActual) {
				if (!poke.isDebilitado()) {
					pokeActual = poke;
					break;
				}
			}
		}
		int accion;
		do { // menuCambio() returna indice del pokemon que quieres poner en combate
			Pokemon pokeEnemigo = enemigo.getSiguientePokemon();
			System.out.println(nombre + " saca a " + pokeActual.getNombre() + "!");
			System.out.println(enemigo.getLider() + " saca a " + pokeEnemigo.getNombre() + "!");
			switch (accion = menuCombate()) {
			case 1:
				peleaPokes(pokeEnemigo, pokeActual);
				if (pokeActual.isDebilitado() && !perdiste()) {
					pokeActual = menuCambio(pokeActual);
				} else if (perdiste()) {
					return;
				}
				break;
			case 2:
				pokeActual = menuCambio(pokeActual);
				break;
			case 3:
				System.out.println("Vuelve a intentarlo!");
				return;
			}
		} while ((enemigo.getSiguientePokemon() != null) || perdiste());
		if (!perdiste()) {
			enemigo.derrotado();
			System.out.println("Felicidades! has ganado!, se añadio la medalla de " + enemigo.getLider());
		} else {
			System.out.println("Te has quedado sin pokemons en el equipo!\nIntentalo de nuevo :(");
		}
	}

	public static Pokemon menuCambio(Pokemon pokeActual) {
		boolean entradaValida = true;
		for (int j = 0; j < equipoActual.size(); j++) {
			if (equipoActual.get(j).isDebilitado()) {
				System.out
						.println((j + 1) + ") " + equipoActual.get(j).getNombre() + "|" + equipoActual.get(j).getTipo()
								+ "|Stats totales: " + equipoActual.get(j).statsTotales() + "|Debilitado");
			} else {
				System.out
						.println((j + 1) + ") " + equipoActual.get(j).getNombre() + "|" + equipoActual.get(j).getTipo()
								+ "|Stats totales: " + equipoActual.get(j).statsTotales() + "|Vivo");
			}
		}
		do {
			try {
				System.out.print("con cual deseas cambiarlo?" + "\r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < 7) {
					if (equipoActual.get(opcion - 1).isDebilitado()) {
						System.out.println(equipoActual.get(opcion - 1).getNombre()
								+ " esta debilitado!, elije otro pokemon para continuar el combate!");
						continue;
					} else {
						entradaValida = false;
						return equipoActual.get(opcion - 1);
					}
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return null;
	}

	public static int menuCombate() {
		boolean entradaValida = true;
		do {
			try {
				System.out.print("Que deseas hacer?" + "\r\n1) Atacar" + "\r\n2) Cambiar de Pokemon" + "\r\n3) Rendirse"
						+ "\r\n> ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < 4) {
					entradaValida = false;
					return opcion;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		return 0;
	}

	public static void peleaPokes(Pokemon enemigo, Pokemon actual) {
		System.out.println(actual.getNombre() + " -> " + actual.statsTotales() + " puntos.");
		System.out.println(enemigo.getNombre() + " -> " + enemigo.statsTotales() + " puntos.");
		if (calcularEfectividad(actual, enemigo) == 0.0) {
			System.out.println(enemigo.getNombre() + " es inmune al tipo " + actual.getTipo() + "!\nNuevo Puntaje:");
			System.out.println(actual.getNombre() + " -> " + actual.statsTotales() * 0 + " puntos.");
			System.out.println(enemigo.getNombre() + " -> " + enemigo.statsTotales() + " puntos.");
			actual.debilitarPokemon();
			System.out.println(actual.getNombre() + " ha sido derrotado! ");
		} else if (calcularEfectividad(actual, enemigo) == 0.5) {
			System.out
					.println(enemigo.getNombre() + " es resistente al tipo " + actual.getTipo() + "!\nNuevo Puntaje:");
			System.out.println(actual.getNombre() + " -> " + actual.statsTotales() * 0.5 + " puntos.");
			System.out.println(enemigo.getNombre() + " -> " + enemigo.statsTotales() + " puntos.");
			if (enemigo.statsTotales() > (actual.statsTotales() * 0.5)) {
				actual.debilitarPokemon();
				System.out.println(actual.getNombre() + " ha sido derrotado!");
			} else if (enemigo.statsTotales() == (actual.statsTotales() * 0.5)) {
				System.out.println("Empate!, Deberias cambiar de pokemon.");
			} else if (enemigo.statsTotales() < (actual.statsTotales() * 0.5)) {
				enemigo.debilitarPokemon();
				System.out.println(actual.getNombre() + " ha ganado!");
			}

		} else if (calcularEfectividad(actual, enemigo) == 1.0) {
			System.out.println(enemigo.getNombre() + " recibe daño normal contra el tipo " + actual.getTipo()
					+ "!\nNuevo Puntaje:");
			System.out.println(actual.getNombre() + " -> " + actual.statsTotales() + " puntos.");
			System.out.println(enemigo.getNombre() + " -> " + enemigo.statsTotales() + " puntos.");
			if (enemigo.statsTotales() > (actual.statsTotales())) {
				actual.debilitarPokemon();
				System.out.println(actual.getNombre() + " ha sido derrotado!");
			} else if (enemigo.statsTotales() == (actual.statsTotales())) {
				System.out.println("Empate!, Deberias cambiar de pokemon.");
			} else if (enemigo.statsTotales() < (actual.statsTotales())) {
				enemigo.debilitarPokemon();
				System.out.println(actual.getNombre() + " ha ganado!");
			}
		} else if (calcularEfectividad(actual, enemigo) == 2.0) {
			System.out.println(enemigo.getNombre() + " es debil al tipo " + actual.getTipo() + "!\nNuevo Puntaje:");
			System.out.println(actual.getNombre() + " -> " + actual.statsTotales() * 2.0 + " puntos.");
			System.out.println(enemigo.getNombre() + " -> " + enemigo.statsTotales() + " puntos.");
			if (enemigo.statsTotales() > (actual.statsTotales() * 2.0)) {
				actual.debilitarPokemon();
				System.out.println(actual.getNombre() + " ha sido derrotado!");
			} else if (enemigo.statsTotales() == (actual.statsTotales() * 2.0)) {
				System.out.println("Empate!, Deberias cambiar de pokemon.");
			} else if (enemigo.statsTotales() < (actual.statsTotales() * 2.0)) {
				enemigo.debilitarPokemon();
				System.out.println(actual.getNombre() + " ha ganado!");
			}
		}
	}

	public static boolean perdiste() {
		for (Pokemon poke : equipoActual) {
			if (!poke.isDebilitado()) {
				return false;
			}
		}
		return true;
	}

	public static boolean menuSiguienteGym() {
		System.out.println("A cual lider deseas desafiar?:\n");
		for (int i = 0; i < gyms.size() + 1; i++) {
			if (i == gyms.size()){
				System.out.println((i + 1) + ") Volver al menu");
				break;
			}
			if (gyms.get(i).isDerrotado()) {
				System.out.println((i + 1) + ") " + gyms.get(i).getLider() + " - Estado: Derrotado");
			} else {
				System.out.println((i + 1) + ") " + gyms.get(i).getLider() + " - Estado: Sin derrotar");
			}
		}
		int opcion = 0;
		boolean entradaValida = true;
		do {
			try {
				System.out.print("> ");
				opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < (gyms.size()+1)) {
					entradaValida = false;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		if (opcion == (gyms.size()+1)) {
			return true;
		} else if ((retarGym() != gyms.get(opcion-1))&&(gyms.get(opcion-1).isDerrotado())) {
			System.out.println("Ya derrotaste este gimnasio!");
			return true;
		} else if (((retarGym() != gyms.get(opcion-1))&&(!gyms.get(opcion-1).isDerrotado()))) {
			System.out.println("Debes derrotar a los gyms anteriores para poder desafiar a " + gyms.get(opcion-1).getLider() +"!");
			return true;
		} else if (retarGym() == gyms.get(opcion-1)) {
			return false;
		}
		return true;
	}

	public static double calcularEfectividad(Pokemon pokeUsuario, Pokemon pokeEnemigo) {
		// Matriz de efectividad
		double[][] EFECTIVIDAD = {
				// NOR FUE AGU PLA ELE HIE LUC VEN TIE VOL PSI BIC ROC FAN DRA ACE SIN HAD
				{ 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0.0, 1.0, 0.5, 1.0, 1.0 }, // NORMAL
				{ 1.0, 0.5, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0 }, // FUEGO
				{ 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0 }, // AGUA
				{ 1.0, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 0.5, 0.5, 1.0, 1.0 }, // PLANTA
				{ 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0 }, // ELECTRICO
				{ 1.0, 0.5, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0 }, // HIELO
				{ 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 0.5, 0.5, 0.5, 2.0, 0.0, 1.0, 2.0, 2.0, 0.5 }, // LUCHA
				{ 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 0.0, 1.0, 2.0 }, // VENENO
				{ 1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 0.5, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0 }, // TIERRA
				{ 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 0.5, 1.0, 1.0 }, // VOLADOR
				{ 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 0.0, 1.0 }, // PSIQUICO
				{ 1.0, 0.5, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 0.5, 1.0, 0.5, 2.0, 0.5 }, // BICHO
				{ 1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0 }, // ROCA
				{ 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0 }, // FANTASMA
				{ 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.0 }, // DRAGON
				{ 1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0, 2.0 }, // ACERO
				{ 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5 }, // SINIESTRO
				{ 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 2.0, 1.0 } // HADA
		};
		return EFECTIVIDAD[pokeUsuario.obtenerIndiceTablaDeTipos()][pokeEnemigo.obtenerIndiceTablaDeTipos()];
	}
}
