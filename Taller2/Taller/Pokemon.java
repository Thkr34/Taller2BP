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
	}

	public String toString() {
		return "Pokemon [nombre=" + nombre + ", habitat=" + habitat + ", tipo=" + tipo + ", porcentajeAparicion="
				+ porcentajeAparicion + ", vida=" + vida + ", ataque=" + ataque + ", defensa=" + defensa
				+ ", ataqueEspecial=" + ataqueEspecial + ", defensaEspecial=" + defensaEspecial + ", velocidad="
				+ velocidad + "]";
	}
	public int statsTotales() {
		return (this.vida + this.ataque + this.defensa + this.ataqueEspecial + this.defensaEspecial + this.velocidad);
	}
}
