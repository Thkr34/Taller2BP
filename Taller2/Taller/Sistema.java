package Taller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
				break;
			case (5):
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
		try (BufferedReader leerArchivo = new BufferedReader(new FileReader("Registros.txt"))) {
			if ((linea = leerArchivo.readLine()) == null) {
				System.out.println("no se encontro una partida anterior, inicie una nueva.");
				return;
			}
			do {
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
			} while ((linea = leerArchivo.readLine()) != null);
			System.out.println("la partida se cargo correctamente!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}

	public static void guardarPartida() {
		try (BufferedWriter sobreEscribirRegistros = new BufferedWriter(new FileWriter("Registros.txt"))) {
			String linea;
			int i = 0;
			linea = nombre;
			for (Gimnasios gym : gyms) {
				System.out.println(gym.isDerrotado());
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
		try (BufferedReader leerHabitats = new BufferedReader(new FileReader("Habitats.txt"))) {
			while ((linea = leerHabitats.readLine()) != null) {
				lista.add(linea);
			}
			System.out.println("los habitats se cargaron correctamente!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
			return;
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}

	public static void cargarPokemons(ArrayList<Pokemon> lista) {
		String linea;
		try (BufferedReader leerPokemons = new BufferedReader(new FileReader("Pokedex.txt"))) {
			while ((linea = leerPokemons.readLine()) != null) {
				String[] partes = linea.split(";");
				// pokemon;habitat;porcentajeAparicion;vida;ataque;defensa;ataqueEspecial;defensaEspecial;velocidad;Tipo
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
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}

	public static void cargarGyms() {
		String linea;
		try (BufferedReader leerGyms = new BufferedReader(new FileReader("Gimnasios.txt"))) {
			while ((linea = leerGyms.readLine()) != null) {
				// N°Gimnasio;Lider;Estado;cantPokemons;Pokemons....
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
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}

	public static void cargarAltoMando() {
		String linea;
		try (BufferedReader leerAltoMando = new BufferedReader(new FileReader("Alto Mando.txt"))) {
			while ((linea = leerAltoMando.readLine()) != null) {
				// N°AltoMando;Nombre;Pokemons....
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
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}
}
