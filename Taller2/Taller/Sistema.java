package Taller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sistema {

	public static void main(String[] args) {
		// Bastián Felipe Perines Flores
		// 22.386.978-5
		// ICCI
		ArrayList<String> habitats = new ArrayList<String>();
		ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
		cargarHabitats(habitats);
		cargarPokemons(pokemons);
		for (Pokemon pokemon : pokemons) {
			System.out.println(pokemon.statsTotales());
		}

	}

	public static void cargarPartida() {
		try (BufferedReader leerArchivo = new BufferedReader(new FileReader("Registros.txt"))) {
			if (leerArchivo.readLine() == null) {
				System.out.println("no se encontro una partida anterior, inicie una nueva.");
				return;
			}
			// aca se procesa la informacion de la partida pasada.
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
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
				String [] partes = linea.split(";");
				//pokemon;habitat;porcentajeAparicion;vida;ataque;defensa;ataqueEspecial;defensaEspecial;velocidad;Tipo
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
				lista.add(new Pokemon(nombre,habitat,porcentajeAparicion,vida,ataque,defensa,ataqueEspecial,defensaEspecial,velocidad,tipo));
			}
			System.out.println("se cargo correctamente la pokedex!");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro una partida anterior.");
			return;
		} catch (IOException e) {
			System.out.println("Ocurrio un error.");
		}
	}
}
