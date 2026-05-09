package Taller;

public class Pokemon {
	// pokemon;habitat;porcentajeAparicion;vida;ataque;defensa;ataqueEspecial;defensaEspecial;velocidad;Tipo
	private String nombre;
	private String habitat;
	private String tipo;
	private double porcentajeAparicion;
	private int vida;
	private int ataque;
	private int defensa;
	private int ataqueEspecial;
	private int defensaEspecial;
	private int velocidad;
	private boolean debilitado;
	private boolean capturado;

	public Pokemon(String nombre, String habitat, double porcentajeAparicion, int vida, int ataque, int defensa,
			int ataqueEspecial, int defensaEspecial, int velocidad, String tipo) {
		this.nombre = nombre;
		this.habitat = habitat;
		this.tipo = tipo;
		this.porcentajeAparicion = porcentajeAparicion;
		this.vida = vida;
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataqueEspecial = ataqueEspecial;
		this.defensaEspecial = defensaEspecial;
		this.velocidad = velocidad;
		this.debilitado = false;
		this.capturado = false;
	}

	public Pokemon(Pokemon poke) {
		this.nombre = poke.nombre;
		this.habitat = poke.habitat;
		this.tipo = poke.tipo;
		this.porcentajeAparicion = poke.porcentajeAparicion;
		this.vida = poke.vida;
		this.ataque = poke.ataque;
		this.defensa = poke.defensa;
		this.ataqueEspecial = poke.ataqueEspecial;
		this.defensaEspecial = poke.defensaEspecial;
		this.velocidad = poke.velocidad;
		this.debilitado = false;
		this.capturado = false;
	}

	public int statsTotales() {
		return (this.vida + this.ataque + this.defensa + this.ataqueEspecial + this.defensaEspecial + this.velocidad);
	}

	public boolean isCapturado() {
		return capturado;
	}

	public String getNombre() {
		return nombre;
	}

	public String getHabitat() {
		return habitat;
	}
	public double getPorcentajeAparicion() {
		return porcentajeAparicion;
	}
	public String getTipo() {
		return tipo;
	}

	public boolean isDebilitado() {
		return debilitado;
	}

	public String toString() {
		return nombre + ", esta capturado: " + capturado;
	}

	public void debilitarPokemon() {
		this.debilitado = true;
	}

	public void curarPokemon() {
		this.debilitado = false;
	}

	public void capturado() {
		this.capturado = true;
	}

	// NOR FUE AGU PLA ELE HIE LUC VEN TIE VOL PSI BIC ROC FAN DRA ACE SIN HAD
	public int obtenerIndiceTablaDeTipos() {
		if (tipo.equals("Normal")) {
			return 0;
		} else if (tipo.equals("Fuego")) {
			return 1;
		} else if (tipo.equals("Agua")) {
			return 2;
		} else if (tipo.equals("Planta")) {
			return 3;
		} else if (tipo.equals("Electrico")) {
			return 4;
		} else if (tipo.equals("Hielo")) {
			return 5;
		} else if (tipo.equals("Lucha")) {
			return 6;
		} else if (tipo.equals("Veneno")) {
			return 7;
		} else if (tipo.equals("Tierra")) {
			return 8;
		} else if (tipo.equals("Volador")) {
			return 9;
		} else if (tipo.equals("Psiquico")) {
			return 10;
		} else if (tipo.equals("Bicho")) {
			return 11;
		} else if (tipo.equals("Roca")) {
			return 12;
		} else if (tipo.equals("Fantasma")) {
			return 13;
		} else if (tipo.equals("Dragon")) {
			return 14;
		} else if (tipo.equals("Acero")) {
			return 15;
		} else if (tipo.equals("Siniestro")) {
			return 16;
		} else if (tipo.equals("Hada")) {
			return 17;
		}
		return 1;
	}
}
