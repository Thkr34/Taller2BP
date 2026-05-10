package Taller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Sistema {
	// listas y demas que se usan en varias funciones, mas que nada para no ponerlas
	// de parametros.
	static ArrayList<Double> porcentajeAcumulado = new ArrayList<Double>();
	static Random rand = new Random();
	static Scanner sc = new Scanner(System.in);
	static String nombre;
	static ArrayList<String> medallas = new ArrayList<String>();
	static ArrayList<Pokemon> capturados = new ArrayList<Pokemon>();
	static ArrayList<Pokemon> equipoActual = new ArrayList<Pokemon>();
	static ArrayList<String> habitats = new ArrayList<String>();
	static ArrayList<Pokemon> pokedex = new ArrayList<Pokemon>();
	static ArrayList<Gimnasios> gyms = new ArrayList<Gimnasios>();
	static ArrayList<AltoMando> altoMando = new ArrayList<AltoMando>();
	static ArrayList<Pokemon> pokesEnHabitatX = new ArrayList<Pokemon>();
	static Pokemon pokeActual;
	static String habitatActual = null;

	public static void main(String[] args) {
		// Bastián Felipe Perines Flores
		// 22.386.978-5
		// ICCI
		cargarHabitats(habitats);
		cargarPokemons(pokedex);
		cargarGyms();
		cargarAltoMando();
		int opcion;
		// do while con un switch que tiene 3 casos, 1: cargar partida, 2: nueva
		// partida, 3: salir. el ciclo se rompe solo si introduces un nombre (o cargas
		// uno de una
		// partida anterior, si es que existe) o si seleccionas salir.
		do {
			opcion = menuPrincipal();
			switch (opcion) {
			case (1):
				if (!cargarPartida(pokedex, gyms)) {
					System.out.println("No se encontro una partida anterior! inicie una nueva :)");
					break;
				}
				if (equipoActual.size() > 0) {
					pokeActual = equipoActual.get(0);
				}
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
		} while (nombre == null);
		int accion;
		// do while con un switch que tiene 8 casos.
		// 1: revisar equipo, que muestra el equipo.
		// 2: salir a capturar, esta opcion te lleva a un sub-menu (sub-programa) donde
		// elijes el habitat al cual quieres ir a capturar pokemons, apariendo alguno de
		// ese bioma son cierto % de aparicion, si tienes menos de 6 pokemons en tu
		// equipo, cualquier captura se agregara a este, en caso de tener mas van al pc.
		// 3: Acceso al PC, te deja acceder al pc donde estan tus pokemon que no estan
		// actual mente en tu equipo, dandote la opcion de cambiar alguno del pc por uno
		// de tu equipo.
		// 4: retar un gimnasio, te deja ver todos los gimnasios disponibles y si ya
		// derrotaste o no cada uno, dandote la opcion de retar alguno, aunque solo te
		// dejara si ya derrotaste al anterior del que seleccionaste (a menos que sea el
		// primero).
		// 5: Desafio al alto mando, te deja luchar contra el alto mando solo si ya
		// derrotaste a todos los gimnasios, a este caso y al anterior no se puede
		// acceder si: no tienes pokemons en el equipo o si estan todos debilitados,
		// ademas, al entrar siempre empezaras con tu primer pokemon (osea del 1er al
		// 6to) que no este debilitado.
		// 6: curar pokemon, cura a todos los pokemons, cura a todos los que estan en la
		// lista "pokedex" ya que estos mismos se usan en las otras listas (arraylists)
		// equipoActual, Capturados, usando referencias, los pokemons de los gimnasios y
		// altomando son copias, osea, otro objeto diferente con copias de los mismos
		// parametros (se guardan en listas propias de la clase Gimnasios).
		// 7: guardar, guarda tu progreso en el archivo: "Registros.txt", siguiendo una
		// estructura de: //nombreUsuario;medalla1;medalla2;...;medallaN°X
		// pokemon1;Vivo (vivo significa no debilitado al momento de su creacion cuando
		// se cargue partida)
		// pokemon2;Debilitado (debilitado es lo contrario a vivo, tomara true en su
		// parametro "debilitado" al momento de su creacion)
		// ...
		// pokemonN°X;Estado
		// los primeros 6 pokemones en este archivo seran el equipo que se guardara en
		// la lista equipoActual, al guardar, los 6 de tu equipo (pueden ser menos)
		// quedaran en el mismo orden que este la lista, osea, linea1: nombre y
		// medallas,
		// linea 2 a la 7 (en caso de tener 6 pokemons, de lo contrario seran menos) los
		// pokemons que estan en equipoActual al momento de guardar.
		// 8: guardar y salir. encapsula al caso anterior, guardando la partida y
		// terminando el ciclo do while.
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
				int indiceHabitatActual = menuHabitats();
				if (indiceHabitatActual == -1) {
					System.out.println("volviendo!...");
					break;
				} else {
					habitatActual = habitats.get(indiceHabitatActual);
				}
				cargarPokesHabitatX(habitatActual);
				Pokemon pokeSpawneado = spawnearPokemon();
				System.out.println("Ha aparecido un " + pokeSpawneado.getNombre() + " salvaje!");
				accion = menuCapturar();
				switch (accion) {
				case (1):
					if (pokeSpawneado.isCapturado()) {
						System.out.println("Ya tienes este Pokemon!");
						break;
					} else if (equipoActual.size() < 6) {
						equipoActual.add(pokeSpawneado);
						pokeSpawneado.capturado();
						System.out.println(pokeSpawneado.getNombre() + " ha sido capturado y añadido al equipo!");
					} else if (equipoActual.size() == 6) {
						capturados.add(pokeSpawneado);
						pokeSpawneado.capturado();
						System.out.println(pokeSpawneado.getNombre() + " ha sido capturado y enviado al PC!");
					}
					break;
				case (2):
					System.out.println("Corriendo de vuelta al menu principal!...");
					break;
				}
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
				if (equipoActual.size() == 0) {
					System.out.println("No puedes combatir sin pokemons en tu equipo!");
					break;
				}
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
				if (equipoActual.size() == 0) {
					System.out.println("No puedes combatir sin pokemons en tu equipo!");
					break;
				}
				if (perdiste()) {
					System.out.println("todos tus pokes estan debilitados!, no puedes combatir asi!");
					break;
				}
				if (!todosLosGymsDerrotados()) {
					System.out
							.println("Para desafiar al Alto Mando debes haber derrotado a todos los gimnasios antes!");
					break;
				}
				for (AltoMando AM : altoMando) {
					combatirAltoMando(AM);
					if (perdiste()) {
						break;
					}
				}
				for (Pokemon poke : equipoActual) {
					if (!poke.isDebilitado()) {
						System.out.println("Felicidades! eres el nuevo campeon! (solo decorativo :P)");
						break;
					}
				}
				System.out.println("Has sido derrotado!,  volviendo al menu...");
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

	// menuPartida muestra el menu que se vera principalmente al iniciar o cargar
	// partida, dando a elejir entre las 8 opcion vistas en un comentario anterior,
	// devuelve un entero que sera la opcion elegida.
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

	// similar a la funcion anterior, pero con el caso 3 del menu partida, que seria
	// acceso al pc. (tambien devuelve un entero)
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

	// mas de lo mismo, pero al elegir cambiar en el menuPC.
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

	// este es diferente, se usa sobrecarga de metodo y otra funcion llamada
	// cambioPokemons, esta funcion intercambia el pokemon seleccionado con otro
	// pokemon seleccionado anteriormente en la otra version de esta misma funcion,
	// cambiandolos de lugar, osea, pokemon de equipo reemplazado por el que
	// selecciono del PC.
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

	// intercambia la referencias entre las listas "capturados", (que seria el PC en
	// realidad) con la lista "equipoActual" que su nombre dice que es.
	public static void cambioPokemons(int indiceCapturado, int indiceEquipo) {
		Pokemon aux = equipoActual.get(indiceEquipo);
		equipoActual.set(indiceEquipo, capturados.get(indiceCapturado));
		capturados.set(indiceCapturado, aux);
	}

	// carga partida (si el archivo no esta vacio, de lo contrario, returna false,
	// si efectivamente se cargo la partida, returna true), tomando el archivo
	// mencionado anteriormente, cambiando los valores de nombre (nombre del usuario
	// ingresado), los estados de los gimnasios (derrotados o no dependiendo si
	// tienes o no sus medallas), y finalmente añadiendo los pokemons guardados en
	// sus respectivas listas (mayores detalles dados anteriormente).
	public static boolean cargarPartida(ArrayList<Pokemon> pokedex, ArrayList<Gimnasios> gyms) {
		String linea;
		boolean primeraLinea = false;
		File registros = new File("Registros.txt");
		try (Scanner leerArchivo = new Scanner(registros)) {
			if (!leerArchivo.hasNextLine()) {
				return false;
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
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	// guardarPartida, guarda partida en el formato antes mencionado, simplemente.
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

	// leer y guarda los habitats leidos en el archivo "Habitats.txt".
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

	// cargarPokemons, lee el archivo "Pokedex.txt", creando y guardando cada uno en
	// la lista pokedex.
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

	// cargarGyms, lee el archivo "Gimnasios.txt", creando cada uno y guardandolo en
	// la lista gyms.
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

	// returna el gimnasio no derrotado mas cerca al principio de la lista.
	public static Gimnasios retarGym() {
		for (Gimnasios gym : gyms) {
			if (!gym.isDerrotado()) {
				return gym;
			}
		}
		return null;
	}

	// hace lo mismo que las funciones llamadas cargarXcosa, leyendo el archivo
	// "Alto Mando.txt".
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

	// returna true si derrotaste a todos los gimnasios, de lo contrario returna
	// false.
	public static boolean todosLosGymsDerrotados() {
		for (Gimnasios gym : gyms) {
			if (!gym.isDerrotado()) {
				return false;
			}
		}
		return true;
	}

	// combatirAltoMando, esta funcion simula la pelea con el alto mando (peleas
	// consecutivas) usando la misma logica de los sub-menus antes vistos, tambien
	// usando la funcion peleaPokes, para procesar la pelea entre dos pokemons.
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
		do {
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

	// combatirGym, se encarga de combatir un gimnasio en especifico, distinto de
	// combatirAltoMando, ya que esa funcion son peleas consecutivas, esta solo dura
	// un combate con el gym seleccionado. (tambien usando sub-menus como peleaPokes
	// y demas.)
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
		do {
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

	// esta funcion muestra los pokemon del equipo actual, tambien mostrando su
	// estado, stats totales y su tipo, obligandote a seleccionar uno que no este
	// debilitado, ya que se llama esta funcion cuando uno de tus pokemons es
	// derrotado.
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

	// similar a los otros menus, este orientado al combate de pokemons.
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

	// esta funcion se encarga de simular la pelea de entre pokemons, muestra sus
	// stats, luego, usando la funcion "calcularEfectividad", se aplica un
	// multiplicador al pokemon del usuario, y finalmente, se comparan las stats, si
	// el pokemon del usuario tiene menos que el rival, pierde y se debilita, caso
	// contrario, gana y el poke enemigo se debilita, en caso de empate, no pasa
	// nada. (xd)
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

	// devuelve true si todos tus pokemones en tu equipo actual se debilitan, si
	// queda alguno vivo, returna false, se usa como parametro para los do while en
	// combates.
	public static boolean perdiste() {
		for (Pokemon poke : equipoActual) {
			if (!poke.isDebilitado()) {
				return false;
			}
		}
		return true;
	}

	// otro menu, usado para seleccionar al gimnasio que quieras desafiar.
	public static boolean menuSiguienteGym() {
		System.out.println("A cual lider deseas desafiar?:\n");
		for (int i = 0; i < gyms.size() + 1; i++) {
			if (i == gyms.size()) {
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
				if (opcion > 0 && opcion < (gyms.size() + 2)) {
					entradaValida = false;
				} else {
					System.out.println("ingrese una opcion valida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("ingrese una opcion valida.");
				sc.nextLine();
			}
		} while (entradaValida);
		if (opcion == (gyms.size() + 1)) {
			return true;
		} else if ((retarGym() != gyms.get(opcion - 1)) && (gyms.get(opcion - 1).isDerrotado())) {
			System.out.println("Ya derrotaste este gimnasio!");
			return true;
		} else if (((retarGym() != gyms.get(opcion - 1)) && (!gyms.get(opcion - 1).isDerrotado()))) {
			System.out.println("Debes derrotar a los gyms anteriores para poder desafiar a "
					+ gyms.get(opcion - 1).getLider() + "!");
			return true;
		} else if (retarGym() == gyms.get(opcion - 1)) {
			return false;
		}
		return true;
	}

	// devuelve un multiplicador (double) dependiendo de la efectividad del pokemon
	// actual contra el pokemon enemigo.
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

	// se encarga de añadir los pokemons de X habitat en la lista pokesEnHabitatX
	public static void cargarPokesHabitatX(String habitat) {
		pokesEnHabitatX.clear();
		for (Pokemon poke : pokedex) {
			if (poke.getHabitat().equals(habitatActual)) {
				pokesEnHabitatX.add(poke);
			}
		}
	}

	// returna un pokemon (referencia) segun su probabilidad desde la lista
	// pokesEnHabitatX.
	public static Pokemon spawnearPokemon() {
		for (int i = 0; i < pokesEnHabitatX.size(); i++) {
			if (i == 0) {
				porcentajeAcumulado.add(pokesEnHabitatX.get(0).getPorcentajeAparicion());
			} else {
				porcentajeAcumulado
						.add(pokesEnHabitatX.get(i).getPorcentajeAparicion() + porcentajeAcumulado.get(i - 1));
			}
		}
		double num = rand.nextDouble(0, porcentajeAcumulado.get(porcentajeAcumulado.size() - 1));

		for (int i = 0; i < pokesEnHabitatX.size(); i++) {
			if (i == 0) {
				if (num < porcentajeAcumulado.get(0)) {
					return pokesEnHabitatX.get(0);
				}
				continue;
			}
			if (porcentajeAcumulado.get(i - 1) < num && porcentajeAcumulado.get(i) > num) {
				return pokesEnHabitatX.get(i);
			}
		}
		return null;
	}

	// otro menu, ya sabes.
	public static int menuHabitats() {
		int opcion;
		boolean entradaValida = true;
		System.out.println("A cual zona deseas salir a capturar?:\n");
		for (int i = 0; i < habitats.size() + 1; i++) {
			System.out.println((i + 1) + ") " + habitats.get(i));
			if (i + 1 == habitats.size()) {
				System.out.println((i + 2) + ") salir");
				break;
			}
		}
		do {
			try {
				System.out.print("> ");
				opcion = sc.nextInt();
				sc.nextLine();
				if (opcion > 0 && opcion < (habitats.size() + 2)) {
					entradaValida = false;
					if (opcion == habitats.size() + 1) {
						return -1;
					}
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

	// el ultimo.
	public static int menuCapturar() {
		boolean entradaValida = true;
		do {
			try {
				System.out
						.print(nombre + ", que deseas hacer?\r\n" + "1) Capturar Pokemon.\r\n" + "2) Huir." + "\r\n> ");
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
}
